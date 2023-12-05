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

import com.hairsalon.dto.OrderDTO;
import com.hairsalon.model.Inventory;
import com.hairsalon.model.Order;
import com.hairsalon.model.OrderDetail;
import com.hairsalon.service.IOrderService;

import lombok.RequiredArgsConstructor;

/**
 * Controlador que maneja las operaciones relacionadas con las posiciones.
 */
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService service;
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
    public ResponseEntity<List<EntityModel<OrderDTO>>> findAll() throws Exception {
        List<EntityModel<OrderDTO>> resources = new ArrayList<>();
        List<OrderDTO> lst = service.readAll().stream()
                .map(obj -> (convertToDto(obj)))
                .collect(Collectors.toList());
        for (OrderDTO dto : lst) {
            EntityModel<OrderDTO> resource = EntityModel.of(dto);
            WebMvcLinkBuilder link1 = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(dto.getIdOrder()));

            resource.add(link1.withRel("Order-info1"));
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
    public EntityModel<OrderDTO> findById(@PathVariable("id") Integer id) throws Exception {
        EntityModel<OrderDTO> resource = EntityModel.of(convertToDto(service.readById(id)));
        // localhost:8080/patients/1
        WebMvcLinkBuilder link1 = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(id));
        resource.add(link1.withRel("Order-info"));
        return resource;
    }

    /**
     * Guarda un nuevo empleado.
     *
     * @param dto El objeto OrderDTO que contiene la información de la
     *            empleado a ser guardada.
     * @return ResponseEntity con el recurso de la empleado creada y el estado
     *         HTTP CREATED si es exitoso.
     * @throws Exception si ocurre algún error durante la operación de guardado.
     */
    @PostMapping
    public ResponseEntity<EntityModel<OrderDTO>> save(@RequestBody OrderDTO dto) throws Exception {
        Order obj = service.save(convertToEntity(dto));
        OrderDTO createdDto = convertToDto(obj);
        WebMvcLinkBuilder selfLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(createdDto.getIdOrder()));
        EntityModel<OrderDTO> resource = EntityModel.of(createdDto, selfLink.withSelfRel());

        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    /**
     * Actualiza la información de una empleado existente.
     *
     * @param dto El objeto OrderDTO que contiene la información actualizada del
     *            empleado.
     * @param id  El identificador del empleado que se va a actualizar.
     * @return ResponseEntity con el recurso del empleado actualizada y el estado
     *         HTTP OK si es exitoso.
     * @throws Exception si ocurre algún error durante la operación de
     *                   actualización.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<OrderDTO>> update(@RequestBody OrderDTO dto,
            @PathVariable("id") Integer id)
            throws Exception {
        dto.setIdOrder(id);
        Order obj = service.update(convertToEntity(dto), id);
        OrderDTO updatedDto = convertToDto(obj);
        WebMvcLinkBuilder selfLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(updatedDto.getIdOrder()));
        EntityModel<OrderDTO> resource = EntityModel.of(updatedDto, selfLink.withSelfRel());

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
    public ResponseEntity<EntityModel<OrderDTO>> disable(@PathVariable("id") Integer id) throws Exception {
        Order obj = service.disable(id);
        OrderDTO updatedDto = convertToDto(obj);
        WebMvcLinkBuilder selfLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(updatedDto.getIdOrder()));
        EntityModel<OrderDTO> resource = EntityModel.of(updatedDto, selfLink.withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }



    /**
     * Convierte un objeto de tipo Order a un objeto de tipo OrderDTO
     * utilizando el mapeo de ModelMapper.
     *
     * @param obj El objeto de tipo Order que se va a convertir a OrderDTO.
     * @return Un objeto de tipo OrderDTO con la información mapeada desde el
     *         objeto Order original.
     */
    private OrderDTO convertToDto(Order obj) {
        return mapper.map(obj, OrderDTO.class);
    }

    /**
     * Convierte un objeto de tipo OrderDTO a un objeto de tipo Order
     * utilizando el mapeo de ModelMapper.
     *
     * @param dto El objeto de tipo OrderDTO que se va a convertir a Order.
     * @return Un objeto de tipo Order con la información mapeada desde el
     *         objeto OrderDTO original.
     */
    private Order convertToEntity(OrderDTO dto) {
        return mapper.map(dto, Order.class);
    }

}
