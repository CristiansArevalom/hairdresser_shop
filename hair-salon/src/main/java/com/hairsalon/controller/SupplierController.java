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

import com.hairsalon.dto.SupplierDTO;
import com.hairsalon.model.Supplier;
import com.hairsalon.service.ISupplierService;

import lombok.RequiredArgsConstructor;

/**
 * Controlador que maneja las operaciones relacionadas con las posiciones.
 */
@RestController
@RequestMapping("/api/v1/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final ISupplierService service;
    @Qualifier("default-mapper")
    private final ModelMapper mapper;

    /**
     * Recupera todas las posiciones.
     *
     * @return ResponseEntity con la lista de recursos de cita y el estado
     *         HTTP OK si es exitoso.
     * @throws Exception si ocurre algún error durante la recuperación.
     */
    @GetMapping
    public ResponseEntity<List<EntityModel<SupplierDTO>>> findAll() throws Exception {
        List<EntityModel<SupplierDTO>> resources = new ArrayList<>();
        List<SupplierDTO> lst = service.readAll().stream()
                .map(obj -> (convertToDto(obj)))
                .collect(Collectors.toList());
        for (SupplierDTO dto : lst) {
            EntityModel<SupplierDTO> resource = EntityModel.of(dto);
            WebMvcLinkBuilder link1 = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(dto.getIdSupplier()));

            resource.add(link1.withRel("Supplier-info1"));
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
    public EntityModel<SupplierDTO> findById(@PathVariable("id") Integer id) throws Exception {
        EntityModel<SupplierDTO> resource = EntityModel.of(convertToDto(service.readById(id)));
        // localhost:8080/patients/1
        WebMvcLinkBuilder link1 = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(id));
        resource.add(link1.withRel("Supplier-info"));
        return resource;
    }

    /**
     * Guarda un nuevo cita.
     *
     * @param dto El objeto SupplierDTO que contiene la información de la
     *            cita a ser guardada.
     * @return ResponseEntity con el recurso de la cita creada y el estado
     *         HTTP CREATED si es exitoso.
     * @throws Exception si ocurre algún error durante la operación de guardado.
     */
    @PostMapping
    public ResponseEntity<EntityModel<SupplierDTO>> save(@RequestBody SupplierDTO dto) throws Exception {
        Supplier obj = service.save(convertToEntity(dto));
        SupplierDTO createdDto = convertToDto(obj);
        WebMvcLinkBuilder selfLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(createdDto.getIdSupplier()));
        EntityModel<SupplierDTO> resource = EntityModel.of(createdDto, selfLink.withSelfRel());

        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    /**
     * Actualiza la información de una cita existente.
     *
     * @param dto El objeto SupplierDTO que contiene la información actualizada del
     *            cita.
     * @param id  El identificador del cita que se va a actualizar.
     * @return ResponseEntity con el recurso del cita actualizada y el estado
     *         HTTP OK si es exitoso.
     * @throws Exception si ocurre algún error durante la operación de
     *                   actualización.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<SupplierDTO>> update(@RequestBody SupplierDTO dto,
            @PathVariable("id") Integer id)
            throws Exception {
        dto.setIdSupplier(id);
        Supplier obj = service.update(convertToEntity(dto), id);
        SupplierDTO updatedDto = convertToDto(obj);
        WebMvcLinkBuilder selfLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(updatedDto.getIdSupplier()));
        EntityModel<SupplierDTO> resource = EntityModel.of(updatedDto, selfLink.withSelfRel());

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    /**
     * Elimina una cita existente por su identificador.
     *
     * @param id El identificador del cita que se va a eliminar.
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
     * Desactiva un cita existente por su identificador.
     *
     * @param id El identificador del cita que se va a desactivar.
     * @return ResponseEntity con el recurso del cita desactivada y el estado
     *         HTTP OK si es exitoso.
     * @throws Exception si ocurre algún error durante la operación de
     *                   desactivación.
     */
    @PutMapping("/{id}/disable")
    public ResponseEntity<EntityModel<SupplierDTO>> disable(@PathVariable("id") Integer id) throws Exception {
        Supplier obj = service.disable(id);
        SupplierDTO updatedDto = convertToDto(obj);
        WebMvcLinkBuilder selfLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(updatedDto.getIdSupplier()));
        EntityModel<SupplierDTO> resource = EntityModel.of(updatedDto, selfLink.withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    /**
     * Convierte un objeto de tipo Supplier a un objeto de tipo SupplierDTO
     * utilizando el mapeo de ModelMapper.
     *
     * @param obj El objeto de tipo Supplier que se va a convertir a SupplierDTO.
     * @return Un objeto de tipo SupplierDTO con la información mapeada desde el
     *         objeto Supplier original.
     */
    private SupplierDTO convertToDto(Supplier obj) {
        return mapper.map(obj, SupplierDTO.class);
    }

    /**
     * Convierte un objeto de tipo SupplierDTO a un objeto de tipo Supplier
     * utilizando el mapeo de ModelMapper.
     *
     * @param dto El objeto de tipo SupplierDTO que se va a convertir a Supplier.
     * @return Un objeto de tipo Supplier con la información mapeada desde el
     *         objeto SupplierDTO original.
     */
    private Supplier convertToEntity(SupplierDTO dto) {
        return mapper.map(dto, Supplier.class);
    }

}
