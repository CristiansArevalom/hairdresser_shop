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

import com.hairsalon.dto.ScheduleDTO;
import com.hairsalon.model.Schedule;
import com.hairsalon.service.IScheduleService;

import lombok.RequiredArgsConstructor;

/**
 * Controlador que maneja las operaciones relacionadas con las posiciones.
 */
@RestController
@RequestMapping("/api/v1/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final IScheduleService service;
    @Qualifier("default-mapper")
    private final ModelMapper mapper;

    /**TODO: validar maestro detalle de horario y horario asignado
     * Recupera todas las posiciones.
     *
     * @return ResponseEntity con la lista de recursos de horario y el estado
     *         HTTP OK si es exitoso.
     * @throws Exception si ocurre algún error durante la recuperación.
     */
    @GetMapping
    public ResponseEntity<List<EntityModel<ScheduleDTO>>> findAll() throws Exception {
        List<EntityModel<ScheduleDTO>> resources = new ArrayList<>();
        List<ScheduleDTO> lst = service.readAll().stream()
                .map(obj -> (convertToDto(obj)))
                .collect(Collectors.toList());
        for (ScheduleDTO dto : lst) {
            EntityModel<ScheduleDTO> resource = EntityModel.of(dto);
            WebMvcLinkBuilder link1 = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(dto.getIdSchedule()));

            resource.add(link1.withRel("Schedule-info1"));
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
    public EntityModel<ScheduleDTO> findById(@PathVariable("id") Integer id) throws Exception {
        EntityModel<ScheduleDTO> resource = EntityModel.of(convertToDto(service.readById(id)));
        // localhost:8080/patients/1
        WebMvcLinkBuilder link1 = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(id));
        resource.add(link1.withRel("Schedule-info"));
        return resource;
    }

    /**
     * Guarda un nuevo horario.
     *
     * @param dto El objeto ScheduleDTO que contiene la información de la
     *            horario a ser guardada.
     * @return ResponseEntity con el recurso de la horario creada y el estado
     *         HTTP CREATED si es exitoso.
     * @throws Exception si ocurre algún error durante la operación de guardado.
     */
    @PostMapping
    public ResponseEntity<EntityModel<ScheduleDTO>> save(@RequestBody ScheduleDTO dto) throws Exception {
        Schedule obj = service.save(convertToEntity(dto));
        ScheduleDTO createdDto = convertToDto(obj);
        WebMvcLinkBuilder selfLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(createdDto.getIdSchedule()));
        EntityModel<ScheduleDTO> resource = EntityModel.of(createdDto, selfLink.withSelfRel());

        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    /**
     * Actualiza la información de una horario existente.
     *
     * @param dto El objeto ScheduleDTO que contiene la información actualizada del
     *            horario.
     * @param id  El identificador del horario que se va a actualizar.
     * @return ResponseEntity con el recurso del horario actualizada y el estado
     *         HTTP OK si es exitoso.
     * @throws Exception si ocurre algún error durante la operación de
     *                   actualización.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ScheduleDTO>> update(@RequestBody ScheduleDTO dto,
            @PathVariable("id") Integer id)
            throws Exception {
        dto.setIdSchedule(id);
        Schedule obj = service.update(convertToEntity(dto), id);
        ScheduleDTO updatedDto = convertToDto(obj);
        WebMvcLinkBuilder selfLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(updatedDto.getIdSchedule()));
        EntityModel<ScheduleDTO> resource = EntityModel.of(updatedDto, selfLink.withSelfRel());

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    /**
     * Elimina una horario existente por su identificador.
     *
     * @param id El identificador del horario que se va a eliminar.
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
     * Desactiva un horario existente por su identificador.
     *
     * @param id El identificador del horario que se va a desactivar.
     * @return ResponseEntity con el recurso del horario desactivada y el estado
     *         HTTP OK si es exitoso.
     * @throws Exception si ocurre algún error durante la operación de
     *                   desactivación.
     */
    @PutMapping("/{id}/disable")
    public ResponseEntity<EntityModel<ScheduleDTO>> disable(@PathVariable("id") Integer id) throws Exception {
        Schedule obj = service.disable(id);
        ScheduleDTO updatedDto = convertToDto(obj);
        WebMvcLinkBuilder selfLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(updatedDto.getIdSchedule()));
        EntityModel<ScheduleDTO> resource = EntityModel.of(updatedDto, selfLink.withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    /**
     * Convierte un objeto de tipo Schedule a un objeto de tipo ScheduleDTO
     * utilizando el mapeo de ModelMapper.
     *
     * @param obj El objeto de tipo Schedule que se va a convertir a ScheduleDTO.
     * @return Un objeto de tipo ScheduleDTO con la información mapeada desde el
     *         objeto Schedule original.
     */
    private ScheduleDTO convertToDto(Schedule obj) {
        return mapper.map(obj, ScheduleDTO.class);
    }

    /**
     * Convierte un objeto de tipo ScheduleDTO a un objeto de tipo Schedule
     * utilizando el mapeo de ModelMapper.
     *
     * @param dto El objeto de tipo ScheduleDTO que se va a convertir a Schedule.
     * @return Un objeto de tipo Schedule con la información mapeada desde el
     *         objeto ScheduleDTO original.
     */
    private Schedule convertToEntity(ScheduleDTO dto) {
        return mapper.map(dto, Schedule.class);
    }

}
