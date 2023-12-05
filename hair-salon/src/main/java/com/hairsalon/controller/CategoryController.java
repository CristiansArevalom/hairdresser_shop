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

import com.hairsalon.dto.CategoryDTO;
import com.hairsalon.model.Category;
import com.hairsalon.service.ICategoryService;

import lombok.RequiredArgsConstructor;

/**
 * Controlador que maneja las operaciones relacionadas con las posiciones.
 */
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryService service;
    @Qualifier("default-mapper")
    private final ModelMapper mapper;

    /**
     * Recupera todas las posiciones.
     *
     * @return ResponseEntity con la lista de recursos de categoria y el estado
     *         HTTP OK si es exitoso.
     * @throws Exception si ocurre algún error durante la recuperación.
     */
    @GetMapping
    public ResponseEntity<List<EntityModel<CategoryDTO>>> findAll() throws Exception {
        List<EntityModel<CategoryDTO>> resources = new ArrayList<>();
        List<CategoryDTO> lst = service.readAll().stream()
                .map(obj -> (convertToDto(obj)))
                .collect(Collectors.toList());
        for (CategoryDTO dto : lst) {
            EntityModel<CategoryDTO> resource = EntityModel.of(dto);
            WebMvcLinkBuilder link1 = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(dto.getIdCategory()));

            resource.add(link1.withRel("Category-info1"));
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
    public EntityModel<CategoryDTO> findById(@PathVariable("id") Integer id) throws Exception {
        EntityModel<CategoryDTO> resource = EntityModel.of(convertToDto(service.readById(id)));
        // localhost:8080/patients/1
        WebMvcLinkBuilder link1 = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(id));
        resource.add(link1.withRel("Category-info"));
        return resource;
    }

    /**
     * Guarda un nuevo categoria.
     *
     * @param dto El objeto CategoryDTO que contiene la información de la
     *            categoria a ser guardada.
     * @return ResponseEntity con el recurso de la categoria creada y el estado
     *         HTTP CREATED si es exitoso.
     * @throws Exception si ocurre algún error durante la operación de guardado.
     */
    @PostMapping
    public ResponseEntity<EntityModel<CategoryDTO>> save(@RequestBody CategoryDTO dto) throws Exception {
        Category obj = service.save(convertToEntity(dto));
        CategoryDTO createdDto = convertToDto(obj);
        WebMvcLinkBuilder selfLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(createdDto.getIdCategory()));
        EntityModel<CategoryDTO> resource = EntityModel.of(createdDto, selfLink.withSelfRel());

        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    /**
     * Actualiza la información de una categoria existente.
     *
     * @param dto El objeto CategoryDTO que contiene la información actualizada del
     *            categoria.
     * @param id  El identificador de la categoria que se va a actualizar.
     * @return ResponseEntity con el recurso de la categoria actualizada y el estado
     *         HTTP OK si es exitoso.
     * @throws Exception si ocurre algún error durante la operación de
     *                   actualización.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<CategoryDTO>> update(@RequestBody CategoryDTO dto,
            @PathVariable("id") Integer id)
            throws Exception {
        dto.setIdCategory(id);
        Category obj = service.update(convertToEntity(dto), id);
        CategoryDTO updatedDto = convertToDto(obj);
        WebMvcLinkBuilder selfLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(updatedDto.getIdCategory()));
        EntityModel<CategoryDTO> resource = EntityModel.of(updatedDto, selfLink.withSelfRel());

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    /**
     * Elimina una categoria existente por su identificador.
     *
     * @param id El identificador de la categoria que se va a eliminar.
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
     * Desactiva un categoria existente por su identificador.
     *
     * @param id El identificador de la categoria que se va a desactivar.
     * @return ResponseEntity con el recurso de la categoria desactivada y el estado
     *         HTTP OK si es exitoso.
     * @throws Exception si ocurre algún error durante la operación de
     *                   desactivación.
     */
    @PutMapping("/{id}/disable")
    public ResponseEntity<EntityModel<CategoryDTO>> disable(@PathVariable("id") Integer id) throws Exception {
        Category obj = service.disable(id);
        CategoryDTO updatedDto = convertToDto(obj);
        WebMvcLinkBuilder selfLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(updatedDto.getIdCategory()));
        EntityModel<CategoryDTO> resource = EntityModel.of(updatedDto, selfLink.withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    /**
     * Convierte un objeto de tipo Category a un objeto de tipo CategoryDTO
     * utilizando el mapeo de ModelMapper.
     *
     * @param obj El objeto de tipo Category que se va a convertir a CategoryDTO.
     * @return Un objeto de tipo CategoryDTO con la información mapeada desde el
     *         objeto Category original.
     */
    private CategoryDTO convertToDto(Category obj) {
        return mapper.map(obj, CategoryDTO.class);
    }

    /**
     * Convierte un objeto de tipo CategoryDTO a un objeto de tipo Category
     * utilizando el mapeo de ModelMapper.
     *
     * @param dto El objeto de tipo CategoryDTO que se va a convertir a Category.
     * @return Un objeto de tipo Category con la información mapeada desde el
     *         objeto CategoryDTO original.
     */
    private Category convertToEntity(CategoryDTO dto) {
        return mapper.map(dto, Category.class);
    }

}
