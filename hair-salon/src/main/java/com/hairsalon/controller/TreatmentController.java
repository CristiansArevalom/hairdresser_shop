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

import com.hairsalon.dto.TreatmentDTO;
import com.hairsalon.model.Treatment;
import com.hairsalon.service.ITreatmentService;

import lombok.RequiredArgsConstructor;

/**
 * Controlador que maneja las operaciones relacionadas con las posiciones.
 */
@RestController
@RequestMapping("/api/v1/treatments")
@RequiredArgsConstructor
public class TreatmentController {

    private final ITreatmentService service;
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
    public ResponseEntity<List<EntityModel<TreatmentDTO>>> findAll() throws Exception {
        List<EntityModel<TreatmentDTO>> resources = new ArrayList<>();
        List<TreatmentDTO> lst = service.readAll().stream()
                .map(obj -> (convertToDto(obj)))
                .collect(Collectors.toList());
        for (TreatmentDTO dto : lst) {
            EntityModel<TreatmentDTO> resource = EntityModel.of(dto);
            WebMvcLinkBuilder link1 = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(dto.getIdTreatment()));

            resource.add(link1.withRel("Treatment-info1"));
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
    public EntityModel<TreatmentDTO> findById(@PathVariable("id") Integer id) throws Exception {
        EntityModel<TreatmentDTO> resource = EntityModel.of(convertToDto(service.readById(id)));
        // localhost:8080/patients/1
        WebMvcLinkBuilder link1 = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(id));
        resource.add(link1.withRel("Treatment-info"));
        return resource;
    }

    /**
     * Guarda un nuevo tratamiento.
     *
     * @param dto El objeto TreatmentDTO que contiene la información de la
     *            tratamiento a ser guardada.
     * @return ResponseEntity con el recurso de la tratamiento creada y el estado
     *         HTTP CREATED si es exitoso.
     * @throws Exception si ocurre algún error durante la operación de guardado.
     */
    @PostMapping
    public ResponseEntity<EntityModel<TreatmentDTO>> save(@RequestBody TreatmentDTO dto) throws Exception {
        Treatment obj = service.save(convertToEntity(dto));
        TreatmentDTO createdDto = convertToDto(obj);
        WebMvcLinkBuilder selfLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(createdDto.getIdTreatment()));
        EntityModel<TreatmentDTO> resource = EntityModel.of(createdDto, selfLink.withSelfRel());

        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    /**
     * Actualiza la información de una tratamiento existente.
     *
     * @param dto El objeto TreatmentDTO que contiene la información actualizada del
     *            tratamiento.
     * @param id  El identificador del tratamiento que se va a actualizar.
     * @return ResponseEntity con el recurso del tratamiento actualizada y el estado
     *         HTTP OK si es exitoso.
     * @throws Exception si ocurre algún error durante la operación de
     *                   actualización.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<TreatmentDTO>> update(@RequestBody TreatmentDTO dto,
            @PathVariable("id") Integer id)
            throws Exception {
        dto.setIdTreatment(id);
        Treatment obj = service.update(convertToEntity(dto), id);
        TreatmentDTO updatedDto = convertToDto(obj);
        WebMvcLinkBuilder selfLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(updatedDto.getIdTreatment()));
        EntityModel<TreatmentDTO> resource = EntityModel.of(updatedDto, selfLink.withSelfRel());

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
    public ResponseEntity<EntityModel<TreatmentDTO>> disable(@PathVariable("id") Integer id) throws Exception {
        Treatment obj = service.disable(id);
        TreatmentDTO updatedDto = convertToDto(obj);
        WebMvcLinkBuilder selfLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(updatedDto.getIdTreatment()));
        EntityModel<TreatmentDTO> resource = EntityModel.of(updatedDto, selfLink.withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    /**
     * Convierte un objeto de tipo Treatment a un objeto de tipo TreatmentDTO
     * utilizando el mapeo de ModelMapper.
     *
     * @param obj El objeto de tipo Treatment que se va a convertir a TreatmentDTO.
     * @return Un objeto de tipo TreatmentDTO con la información mapeada desde el
     *         objeto Treatment original.
     */
    private TreatmentDTO convertToDto(Treatment obj) {
        return mapper.map(obj, TreatmentDTO.class);
    }

    /**
     * Convierte un objeto de tipo TreatmentDTO a un objeto de tipo Treatment
     * utilizando el mapeo de ModelMapper.
     *
     * @param dto El objeto de tipo TreatmentDTO que se va a convertir a Treatment.
     * @return Un objeto de tipo Treatment con la información mapeada desde el
     *         objeto TreatmentDTO original.
     */
    private Treatment convertToEntity(TreatmentDTO dto) {
        return mapper.map(dto, Treatment.class);
    }

}
