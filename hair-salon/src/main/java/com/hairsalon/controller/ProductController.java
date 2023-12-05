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

import com.hairsalon.dto.ProductDTO;
import com.hairsalon.model.Product;
import com.hairsalon.service.IProductService;

import lombok.RequiredArgsConstructor;

/**
 * Controlador que maneja las operaciones relacionadas con las posiciones.
 */
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService service;
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
    public ResponseEntity<List<EntityModel<ProductDTO>>> findAll() throws Exception {
        List<EntityModel<ProductDTO>> resources = new ArrayList<>();
        List<ProductDTO> lst = service.readAll().stream()
                .map(obj -> (convertToDto(obj)))
                .collect(Collectors.toList());
        for (ProductDTO dto : lst) {
            EntityModel<ProductDTO> resource = EntityModel.of(dto);
            WebMvcLinkBuilder link1 = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(dto.getIdProduct()));

            resource.add(link1.withRel("Product-info1"));
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
    public EntityModel<ProductDTO> findById(@PathVariable("id") Integer id) throws Exception {
        EntityModel<ProductDTO> resource = EntityModel.of(convertToDto(service.readById(id)));
        // localhost:8080/patients/1
        WebMvcLinkBuilder link1 = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(id));
        resource.add(link1.withRel("Product-info"));
        return resource;
    }

    /**
     * Guarda un nuevo empleado.
     *
     * @param dto El objeto ProductDTO que contiene la información de la
     *            empleado a ser guardada.
     * @return ResponseEntity con el recurso de la empleado creada y el estado
     *         HTTP CREATED si es exitoso.
     * @throws Exception si ocurre algún error durante la operación de guardado.
     */
    @PostMapping
    public ResponseEntity<EntityModel<ProductDTO>> save(@RequestBody ProductDTO dto) throws Exception {
        Product obj = service.save(convertToEntity(dto));
        ProductDTO createdDto = convertToDto(obj);
        WebMvcLinkBuilder selfLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(createdDto.getIdProduct()));
        EntityModel<ProductDTO> resource = EntityModel.of(createdDto, selfLink.withSelfRel());

        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    /**
     * Actualiza la información de una empleado existente.
     *
     * @param dto El objeto ProductDTO que contiene la información actualizada del
     *            empleado.
     * @param id  El identificador del empleado que se va a actualizar.
     * @return ResponseEntity con el recurso del empleado actualizada y el estado
     *         HTTP OK si es exitoso.
     * @throws Exception si ocurre algún error durante la operación de
     *                   actualización.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ProductDTO>> update(@RequestBody ProductDTO dto,
            @PathVariable("id") Integer id)
            throws Exception {
        dto.setIdProduct(id);
        Product obj = service.update(convertToEntity(dto), id);
        ProductDTO updatedDto = convertToDto(obj);
        WebMvcLinkBuilder selfLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(updatedDto.getIdProduct()));
        EntityModel<ProductDTO> resource = EntityModel.of(updatedDto, selfLink.withSelfRel());

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
    public ResponseEntity<EntityModel<ProductDTO>> disable(@PathVariable("id") Integer id) throws Exception {
        Product obj = service.disable(id);
        ProductDTO updatedDto = convertToDto(obj);
        WebMvcLinkBuilder selfLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(updatedDto.getIdProduct()));
        EntityModel<ProductDTO> resource = EntityModel.of(updatedDto, selfLink.withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    /**
     * Convierte un objeto de tipo Product a un objeto de tipo ProductDTO
     * utilizando el mapeo de ModelMapper.
     *
     * @param obj El objeto de tipo Product que se va a convertir a ProductDTO.
     * @return Un objeto de tipo ProductDTO con la información mapeada desde el
     *         objeto Product original.
     */
    private ProductDTO convertToDto(Product obj) {
        return mapper.map(obj, ProductDTO.class);
    }

    /**
     * Convierte un objeto de tipo ProductDTO a un objeto de tipo Product
     * utilizando el mapeo de ModelMapper.
     *
     * @param dto El objeto de tipo ProductDTO que se va a convertir a Product.
     * @return Un objeto de tipo Product con la información mapeada desde el
     *         objeto ProductDTO original.
     */
    private Product convertToEntity(ProductDTO dto) {
        return mapper.map(dto, Product.class);
    }

}
