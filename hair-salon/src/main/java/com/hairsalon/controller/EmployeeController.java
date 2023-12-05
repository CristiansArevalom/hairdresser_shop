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

import com.hairsalon.dto.EmployeeDTO;
import com.hairsalon.model.Employee;
import com.hairsalon.service.IEmployeeService;

import lombok.RequiredArgsConstructor;

/**
 * Controlador que maneja las operaciones relacionadas con las posiciones.
 */
@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final IEmployeeService service;
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
    public ResponseEntity<List<EntityModel<EmployeeDTO>>> findAll() throws Exception {
        List<EntityModel<EmployeeDTO>> resources = new ArrayList<>();
        List<EmployeeDTO> lst = service.readAll().stream()
                .map(obj -> (convertToDto(obj)))
                .collect(Collectors.toList());
        for (EmployeeDTO dto : lst) {
            EntityModel<EmployeeDTO> resource = EntityModel.of(dto);
            WebMvcLinkBuilder link1 = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(dto.getIdEmployee()));

            resource.add(link1.withRel("Employee-info1"));
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
    public EntityModel<EmployeeDTO> findById(@PathVariable("id") Integer id) throws Exception {
        EntityModel<EmployeeDTO> resource = EntityModel.of(convertToDto(service.readById(id)));
        // localhost:8080/patients/1
        WebMvcLinkBuilder link1 = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(id));
        resource.add(link1.withRel("Employee-info"));
        return resource;
    }

    /**
     * Guarda un nuevo empleado.
     *
     * @param dto El objeto EmployeeDTO que contiene la información de la
     *            empleado a ser guardada.
     * @return ResponseEntity con el recurso de la empleado creada y el estado
     *         HTTP CREATED si es exitoso.
     * @throws Exception si ocurre algún error durante la operación de guardado.
     */
    @PostMapping
    public ResponseEntity<EntityModel<EmployeeDTO>> save(@RequestBody EmployeeDTO dto) throws Exception {
        Employee obj = service.save(convertToEntity(dto));
        EmployeeDTO createdDto = convertToDto(obj);
        WebMvcLinkBuilder selfLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(createdDto.getIdEmployee()));
        EntityModel<EmployeeDTO> resource = EntityModel.of(createdDto, selfLink.withSelfRel());

        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    /**
     * Actualiza la información de una empleado existente.
     *
     * @param dto El objeto EmployeeDTO que contiene la información actualizada del
     *            empleado.
     * @param id  El identificador del empleado que se va a actualizar.
     * @return ResponseEntity con el recurso del empleado actualizada y el estado
     *         HTTP OK si es exitoso.
     * @throws Exception si ocurre algún error durante la operación de
     *                   actualización.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<EmployeeDTO>> update(@RequestBody EmployeeDTO dto,
            @PathVariable("id") Integer id)
            throws Exception {
        dto.setIdEmployee(id);
        Employee obj = service.update(convertToEntity(dto), id);
        EmployeeDTO updatedDto = convertToDto(obj);
        WebMvcLinkBuilder selfLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(updatedDto.getIdEmployee()));
        EntityModel<EmployeeDTO> resource = EntityModel.of(updatedDto, selfLink.withSelfRel());

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
    public ResponseEntity<EntityModel<EmployeeDTO>> disable(@PathVariable("id") Integer id) throws Exception {
        Employee obj = service.disable(id);
        EmployeeDTO updatedDto = convertToDto(obj);
        WebMvcLinkBuilder selfLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(updatedDto.getIdEmployee()));
        EntityModel<EmployeeDTO> resource = EntityModel.of(updatedDto, selfLink.withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    /**
     * Convierte un objeto de tipo Employee a un objeto de tipo EmployeeDTO
     * utilizando el mapeo de ModelMapper.
     *
     * @param obj El objeto de tipo Employee que se va a convertir a EmployeeDTO.
     * @return Un objeto de tipo EmployeeDTO con la información mapeada desde el
     *         objeto Employee original.
     */
    private EmployeeDTO convertToDto(Employee obj) {
        return mapper.map(obj, EmployeeDTO.class);
    }

    /**
     * Convierte un objeto de tipo EmployeeDTO a un objeto de tipo Employee
     * utilizando el mapeo de ModelMapper.
     *
     * @param dto El objeto de tipo EmployeeDTO que se va a convertir a Employee.
     * @return Un objeto de tipo Employee con la información mapeada desde el
     *         objeto EmployeeDTO original.
     */
    private Employee convertToEntity(EmployeeDTO dto) {
        return mapper.map(dto, Employee.class);
    }

}
