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

import com.hairsalon.dto.AppointmentDTO;
import com.hairsalon.dto.AppointmentListTreatmentDTO;
import com.hairsalon.model.Appointment;
import com.hairsalon.model.Treatment;
import com.hairsalon.service.IAppointmentService;

import lombok.RequiredArgsConstructor;

/**
 * Controlador que maneja las operaciones relacionadas con las posiciones.
 */
@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final IAppointmentService service;
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
    public ResponseEntity<List<EntityModel<AppointmentDTO>>> findAll() throws Exception {
        List<EntityModel<AppointmentDTO>> resources = new ArrayList<>();
        List<AppointmentDTO> lst = service.readAll().stream()
                .map(obj -> (convertToDto(obj)))
                .collect(Collectors.toList());
        for (AppointmentDTO dto : lst) {
            EntityModel<AppointmentDTO> resource = EntityModel.of(dto);
            WebMvcLinkBuilder link1 = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(dto.getIdAppointment()));

            resource.add(link1.withRel("Appointment-info1"));
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
    public EntityModel<AppointmentDTO> findById(@PathVariable("id") Integer id) throws Exception {
        EntityModel<AppointmentDTO> resource = EntityModel.of(convertToDto(service.readById(id)));
        // localhost:8080/patients/1
        WebMvcLinkBuilder link1 = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(id));
        resource.add(link1.withRel("Appointment-info"));
        return resource;
    }

    /**
     * Guarda un nuevo cita.
     *
     * @param dto El objeto AppointmentDTO que contiene la información de la
     *            cita a ser guardada.
     * @return ResponseEntity con el recurso de la cita creada y el estado
     *         HTTP CREATED si es exitoso.
     * @throws Exception si ocurre algún error durante la operación de guardado.
     */
    @PostMapping
    public ResponseEntity<EntityModel<AppointmentDTO>> save(@RequestBody AppointmentListTreatmentDTO dto) throws Exception {
        Appointment appointment = convertToEntity(dto.getAppointment());
        List<Treatment> treatments = dto.getLstTreatments().stream().map(treatment->mapper.map(treatment, Treatment.class)).toList();

        Appointment obj = service.saveTransactional(appointment, treatments);
        //saving on appointment_details for each treatment
        
        AppointmentDTO createdDto = convertToDto(obj);
        WebMvcLinkBuilder selfLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(createdDto.getIdAppointment()));
        EntityModel<AppointmentDTO> resource = EntityModel.of(createdDto, selfLink.withSelfRel());

        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    /**
     * Actualiza la información de una cita existente.
     *
     * @param dto El objeto AppointmentDTO que contiene la información actualizada del
     *            cita.
     * @param id  El identificador del cita que se va a actualizar.
     * @return ResponseEntity con el recurso del cita actualizada y el estado
     *         HTTP OK si es exitoso.
     * @throws Exception si ocurre algún error durante la operación de
     *                   actualización.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<AppointmentDTO>> update(@RequestBody AppointmentDTO dto,
            @PathVariable("id") Integer id)
            throws Exception {
        dto.setIdAppointment(id);
        Appointment obj = service.update(convertToEntity(dto), id);
        AppointmentDTO updatedDto = convertToDto(obj);
        WebMvcLinkBuilder selfLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(updatedDto.getIdAppointment()));
        EntityModel<AppointmentDTO> resource = EntityModel.of(updatedDto, selfLink.withSelfRel());

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
    public ResponseEntity<EntityModel<AppointmentDTO>> disable(@PathVariable("id") Integer id) throws Exception {
        Appointment obj = service.disable(id);
        AppointmentDTO updatedDto = convertToDto(obj);
        WebMvcLinkBuilder selfLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(updatedDto.getIdAppointment()));
        EntityModel<AppointmentDTO> resource = EntityModel.of(updatedDto, selfLink.withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    /**
     * Convierte un objeto de tipo Appointment a un objeto de tipo AppointmentDTO
     * utilizando el mapeo de ModelMapper.
     *
     * @param obj El objeto de tipo Appointment que se va a convertir a AppointmentDTO.
     * @return Un objeto de tipo AppointmentDTO con la información mapeada desde el
     *         objeto Appointment original.
     */
    private AppointmentDTO convertToDto(Appointment obj) {
        return mapper.map(obj, AppointmentDTO.class);
    }

    /**
     * Convierte un objeto de tipo AppointmentDTO a un objeto de tipo Appointment
     * utilizando el mapeo de ModelMapper.
     *
     * @param dto El objeto de tipo AppointmentDTO que se va a convertir a Appointment.
     * @return Un objeto de tipo Appointment con la información mapeada desde el
     *         objeto AppointmentDTO original.
     */
    private Appointment convertToEntity(AppointmentDTO dto) {
        return mapper.map(dto, Appointment.class);
    }

}
