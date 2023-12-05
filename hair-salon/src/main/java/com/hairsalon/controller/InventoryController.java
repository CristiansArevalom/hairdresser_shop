package com.hairsalon.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hairsalon.dto.InventoryDTO;
import com.hairsalon.model.Inventory;
import com.hairsalon.service.IInventoryService;

import lombok.RequiredArgsConstructor;

/**
 * Controlador que maneja las operaciones relacionadas con las posiciones.
 */
@RestController
@RequestMapping("/api/v1/inventories")
@RequiredArgsConstructor
public class InventoryController {

    private final IInventoryService service;
    @Qualifier("default-mapper")
    private final ModelMapper mapper;

    /**
     * Recupera todas las posiciones.
     *
     * @return ResponseEntity con la lista de recursos de empleado y el estado
     *         HTTP OK si es exitoso.
     * @throws Exception si ocurre algún error durante la recuperación.
     */
    @GetMapping
    public ResponseEntity<List<EntityModel<InventoryDTO>>> findAll() throws Exception {
        List<EntityModel<InventoryDTO>> resources = new ArrayList<>();
        List<InventoryDTO> lst = service.readAll().stream()
                .map(obj -> (convertToDto(obj)))
                .collect(Collectors.toList());
        for (InventoryDTO dto : lst) {
            EntityModel<InventoryDTO> resource = EntityModel.of(dto);
            WebMvcLinkBuilder link1 = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(dto.getBarcode()));

            resource.add(link1.withRel("Inventory-info1"));
            resources.add(resource);
        }

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    /**
     * Recupera un cargo por su identificador.
     *
     * @param id El identificador del cargo a recuperar.
     * @return Un objeto EntityModel que representa el cargo encontrado.
     * @throws Exception Si ocurre un error durante la recuperación.
     */
    @GetMapping("/{id}")
    public EntityModel<InventoryDTO> findById(@PathVariable("id") Integer id) throws Exception {
        EntityModel<InventoryDTO> resource = EntityModel.of(convertToDto(service.readById(id)));
        // localhost:8080/patients/1
        WebMvcLinkBuilder link1 = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(id));
        resource.add(link1.withRel("Inventory-info"));
        return resource;
    }

    /**
     * Guarda un nuevo empleado.
     *
     * @param dto El objeto InventoryDTO que contiene la información de la
     *            empleado a ser guardada.
     * @return ResponseEntity con el recurso de la empleado creada y el estado
     *         HTTP CREATED si es exitoso.
     * @throws Exception si ocurre algún error durante la operación de guardado.
     */
    @PostMapping
    public ResponseEntity<EntityModel<InventoryDTO>> save(@RequestBody InventoryDTO dto) throws Exception {
        Inventory obj = service.save(convertToEntity(dto));
        InventoryDTO createdDto = convertToDto(obj);
        WebMvcLinkBuilder selfLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(createdDto.getBarcode()));
        EntityModel<InventoryDTO> resource = EntityModel.of(createdDto, selfLink.withSelfRel());

        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    /**
     * Actualiza la información de una empleado existente.
     *
     * @param dto El objeto InventoryDTO que contiene la información actualizada del
     *            empleado.
     * @param id  El identificador del empleado que se va a actualizar.
     * @return ResponseEntity con el recurso del empleado actualizada y el estado
     *         HTTP OK si es exitoso.
     * @throws Exception si ocurre algún error durante la operación de
     *                   actualización.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<InventoryDTO>> update(@RequestBody InventoryDTO dto,
            @PathVariable("id") Integer id)
            throws Exception {
        dto.setBarcode(id);
        Inventory obj = service.update(convertToEntity(dto), id);
        InventoryDTO updatedDto = convertToDto(obj);
        WebMvcLinkBuilder selfLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(updatedDto.getBarcode()));
        EntityModel<InventoryDTO> resource = EntityModel.of(updatedDto, selfLink.withSelfRel());

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    /**
     * Elimina una empleado existente por su identificador.
     *
     * @param id El identificador del empleado que se va a eliminar.
     * @return ResponseEntity con el estado HTTP NO_CONTENT si la eliminación es
     *         exitosa.
     * @throws Exception si ocurre algún error durante la operación de eliminación.
     * 
     *                   TODO: Solo rol adm puede acceder a esta opcion
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Desactiva un empleado existente por su identificador.
     *
     * @param id El identificador del empleado que se va a desactivar.
     * @return ResponseEntity con el recurso del empleado desactivada y el estado
     *         HTTP OK si es exitoso.
     * @throws Exception si ocurre algún error durante la operación de
     *                   desactivación.
     */
    @PutMapping("/{id}/disable")
    public ResponseEntity<EntityModel<InventoryDTO>> disable(@PathVariable("id") Integer id) throws Exception {
        Inventory obj = service.disable(id);
        InventoryDTO updatedDto = convertToDto(obj);
        WebMvcLinkBuilder selfLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(updatedDto.getBarcode()));
        EntityModel<InventoryDTO> resource = EntityModel.of(updatedDto, selfLink.withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    /**
     * Convierte un objeto de tipo Inventory a un objeto de tipo InventoryDTO
     * utilizando el mapeo de ModelMapper.
     *
     * @param obj El objeto de tipo Inventory que se va a convertir a InventoryDTO.
     * @return Un objeto de tipo InventoryDTO con la información mapeada desde el
     *         objeto Inventory original.
     */
    private InventoryDTO convertToDto(Inventory obj) {
        return mapper.map(obj, InventoryDTO.class);
    }

    /**
     * Convierte un objeto de tipo InventoryDTO a un objeto de tipo Inventory
     * utilizando el mapeo de ModelMapper.
     *
     * @param dto El objeto de tipo InventoryDTO que se va a convertir a Inventory.
     * @return Un objeto de tipo Inventory con la información mapeada desde el
     *         objeto InventoryDTO original.
     */
    private Inventory convertToEntity(InventoryDTO dto) {
        return mapper.map(dto, Inventory.class);
    }

}
