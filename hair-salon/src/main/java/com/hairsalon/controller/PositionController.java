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

import com.hairsalon.dto.PositionDTO;
import com.hairsalon.dto.PositionScheduleDTO;
import com.hairsalon.model.Position;
import com.hairsalon.model.Schedule;
import com.hairsalon.service.IPositionService;

import lombok.RequiredArgsConstructor;

/**
 * Controlador que maneja las operaciones relacionadas con las posiciones.
 */
@RestController
@RequestMapping("/api/v1/positions")
@RequiredArgsConstructor
public class PositionController {

    private final IPositionService service;
    @Qualifier("default-mapper")
    private final ModelMapper mapper;


    /**
     * Recupera todas las posiciones.
     *
     * @return ResponseEntity con la lista de recursos de posición y el estado HTTP OK si es exitoso.
     * @throws Exception si ocurre algún error durante la recuperación.
     */
    @GetMapping
    public ResponseEntity<List<EntityModel<PositionDTO>>> findAll() throws Exception {
        List<EntityModel<PositionDTO>> resources = new ArrayList<>();
        List<PositionDTO> lst = service.readAll().stream()
                .map(obj -> (convertToDto(obj)))
                .collect(Collectors.toList());
        for (PositionDTO dto : lst) {
            EntityModel<PositionDTO> resource = EntityModel.of(dto);
            WebMvcLinkBuilder link1 = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(dto.getIdPosition()));

            resource.add(link1.withRel("position-info1"));
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
    public EntityModel<PositionDTO> findById(@PathVariable("id") Integer id) throws Exception {
        EntityModel<PositionDTO> resource = EntityModel.of(convertToDto(service.readById(id)));
        // localhost:8080/patients/1
        WebMvcLinkBuilder link1 = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(id));
        resource.add(link1.withRel("position-info"));
        return resource;
    }

    /**
 * Guarda una nueva posición.
    *
    * @param dto El objeto PositionDTO que contiene la información de la posición a ser guardada.
    * @return ResponseEntity con el recurso de la posición creada y el estado HTTP CREATED si es exitoso.
    * @throws Exception si ocurre algún error durante la operación de guardado.
 */
    @PostMapping
    public ResponseEntity<EntityModel<PositionDTO>> save(@RequestBody PositionScheduleDTO dto) throws Exception {
        Position position = service.save(convertToEntity(dto.getPosition()));
        List<Schedule> schedules = dto.getLstSchedules().stream().map(schedule -> mapper.map(schedule, Schedule.class)).collect(Collectors.toList());
        Position obj = service.saveTransactional(position, schedules, dto.getDay());

        PositionDTO createdDto = convertToDto(obj);
        WebMvcLinkBuilder selfLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(createdDto.getIdPosition()));
        EntityModel<PositionDTO> resource = EntityModel.of(createdDto, selfLink.withSelfRel());

        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    /**
     * Actualiza la información de una posición existente.
     *
     * @param dto El objeto PositionDTO que contiene la información actualizada de la posición.
     * @param id  El identificador de la posición que se va a actualizar.
     * @return ResponseEntity con el recurso de la posición actualizada y el estado HTTP OK si es exitoso.
     * @throws Exception si ocurre algún error durante la operación de actualización.
 */
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<PositionDTO>> update(@RequestBody PositionDTO dto, @PathVariable("id") Integer id)
            throws Exception {
        dto.setIdPosition(id);
        Position obj = service.update(convertToEntity(dto), id);
        PositionDTO updatedDto = convertToDto(obj);
        WebMvcLinkBuilder selfLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(updatedDto.getIdPosition()));
        EntityModel<PositionDTO> resource = EntityModel.of(updatedDto, selfLink.withSelfRel());

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
    /**
     * Elimina una posición existente por su identificador.
     *
     * @param id El identificador de la posición que se va a eliminar.
     * @return ResponseEntity con el estado HTTP NO_CONTENT si la eliminación es exitosa.
     * @throws Exception si ocurre algún error durante la operación de eliminación.
     * 
     * TODO: Solo rol adm puede acceder a esta opcion
 */

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Desactiva una posición existente por su identificador.
     *
     * @param id El identificador de la posición que se va a desactivar.
     * @return ResponseEntity con el recurso de la posición desactivada y el estado HTTP OK si es exitoso.
     * @throws Exception si ocurre algún error durante la operación de desactivación.
 */
    @PutMapping("/{id}/disable")
    public ResponseEntity<EntityModel<PositionDTO>> disable(@PathVariable("id") Integer id) throws Exception {
        Position obj = service.disable(id);
        PositionDTO updatedDto = convertToDto(obj);
        WebMvcLinkBuilder selfLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(updatedDto.getIdPosition()));
        EntityModel<PositionDTO> resource = EntityModel.of(updatedDto, selfLink.withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    private PositionDTO convertToDto(Position obj) {
        return mapper.map(obj, PositionDTO.class);
    }

    private Position convertToEntity(PositionDTO dto) {
        return mapper.map(dto, Position.class);
    }

}
