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

import com.hairsalon.dto.ShelvingDTO;
import com.hairsalon.model.Shelving;
import com.hairsalon.service.IShelvingService;

import lombok.RequiredArgsConstructor;

/**
 * Controlador que maneja las operaciones relacionadas con las posiciones.
 */
@RestController
@RequestMapping("/api/v1/shelvings")
@RequiredArgsConstructor
public class ShelvingController {

    private final IShelvingService service;
    @Qualifier("default-mapper")
    private final ModelMapper mapper;

    /**
     * Recupera todas las posiciones.
     *
     * @return ResponseEntity con la lista de recursos de tratamiento y el estado
     *         HTTP OK si es exitoso.
     * @throws Exception si ocurre algún error durante la recuperación.
     */
    @GetMapping
    public ResponseEntity<List<EntityModel<ShelvingDTO>>> findAll() throws Exception {
        List<EntityModel<ShelvingDTO>> resources = new ArrayList<>();
        List<ShelvingDTO> lst = service.readAll().stream()
                .map(obj -> (convertToDto(obj)))
                .collect(Collectors.toList());
        for (ShelvingDTO dto : lst) {
            EntityModel<ShelvingDTO> resource = EntityModel.of(dto);
            WebMvcLinkBuilder link1 = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(dto.getIdShelving()));

            resource.add(link1.withRel("Shelving-info1"));
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
    public EntityModel<ShelvingDTO> findById(@PathVariable("id") Integer id) throws Exception {
        EntityModel<ShelvingDTO> resource = EntityModel.of(convertToDto(service.readById(id)));
        // localhost:8080/patients/1
        WebMvcLinkBuilder link1 = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(id));
        resource.add(link1.withRel("Shelving-info"));
        return resource;
    }

    /**
     * Guarda un nuevo tratamiento.
     *
     * @param dto El objeto ShelvingDTO que contiene la información de la
     *            tratamiento a ser guardada.
     * @return ResponseEntity con el recurso de la tratamiento creada y el estado
     *         HTTP CREATED si es exitoso.
     * @throws Exception si ocurre algún error durante la operación de guardado.
     */
    @PostMapping
    public ResponseEntity<EntityModel<ShelvingDTO>> save(@RequestBody ShelvingDTO dto) throws Exception {
        Shelving obj = service.save(convertToEntity(dto));
        ShelvingDTO createdDto = convertToDto(obj);
        WebMvcLinkBuilder selfLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(createdDto.getIdShelving()));
        EntityModel<ShelvingDTO> resource = EntityModel.of(createdDto, selfLink.withSelfRel());

        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    /**
     * Actualiza la información de una tratamiento existente.
     *
     * @param dto El objeto ShelvingDTO que contiene la información actualizada del
     *            tratamiento.
     * @param id  El identificador del tratamiento que se va a actualizar.
     * @return ResponseEntity con el recurso del tratamiento actualizada y el estado
     *         HTTP OK si es exitoso.
     * @throws Exception si ocurre algún error durante la operación de
     *                   actualización.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ShelvingDTO>> update(@RequestBody ShelvingDTO dto,
            @PathVariable("id") Integer id)
            throws Exception {
        dto.setIdShelving(id);
        Shelving obj = service.update(convertToEntity(dto), id);
        ShelvingDTO updatedDto = convertToDto(obj);
        WebMvcLinkBuilder selfLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(updatedDto.getIdShelving()));
        EntityModel<ShelvingDTO> resource = EntityModel.of(updatedDto, selfLink.withSelfRel());

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    /**
     * Elimina una tratamiento existente por su identificador.
     *
     * @param id El identificador del tratamiento que se va a eliminar.
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
     * Desactiva un tratamiento existente por su identificador.
     *
     * @param id El identificador del tratamiento que se va a desactivar.
     * @return ResponseEntity con el recurso del tratamiento desactivada y el estado
     *         HTTP OK si es exitoso.
     * @throws Exception si ocurre algún error durante la operación de
     *                   desactivación.
     */
    @PutMapping("/{id}/disable")
    public ResponseEntity<EntityModel<ShelvingDTO>> disable(@PathVariable("id") Integer id) throws Exception {
        Shelving obj = service.disable(id);
        ShelvingDTO updatedDto = convertToDto(obj);
        WebMvcLinkBuilder selfLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(updatedDto.getIdShelving()));
        EntityModel<ShelvingDTO> resource = EntityModel.of(updatedDto, selfLink.withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    /**
     * Convierte un objeto de tipo Shelving a un objeto de tipo ShelvingDTO
     * utilizando el mapeo de ModelMapper.
     *
     * @param obj El objeto de tipo Shelving que se va a convertir a ShelvingDTO.
     * @return Un objeto de tipo ShelvingDTO con la información mapeada desde el
     *         objeto Shelving original.
     */
    private ShelvingDTO convertToDto(Shelving obj) {
        return mapper.map(obj, ShelvingDTO.class);
    }

    /**
     * Convierte un objeto de tipo ShelvingDTO a un objeto de tipo Shelving
     * utilizando el mapeo de ModelMapper.
     *
     * @param dto El objeto de tipo ShelvingDTO que se va a convertir a Shelving.
     * @return Un objeto de tipo Shelving con la información mapeada desde el
     *         objeto ShelvingDTO original.
     */
    private Shelving convertToEntity(ShelvingDTO dto) {
        return mapper.map(dto, Shelving.class);
    }

}
