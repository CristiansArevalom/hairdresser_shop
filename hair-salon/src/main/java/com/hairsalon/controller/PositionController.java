package com.hairsalon.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hairsalon.dto.PositionDTO;
import com.hairsalon.model.Position;
import com.hairsalon.service.IPositionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/position")
@RequiredArgsConstructor
public class PositionController {

    private final IPositionService service;
    @Qualifier("default-mapper")
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<PositionDTO>> findAll() throws Exception {
        List<EntityModel<PositionDTO>> resources;
        List<PositionDTO> lst = service.readAll().stream()
                .map(obj -> (convertToDto(obj)))
                    .collect(Collectors.toList());
        for(PositionDTO dto : lst){
            EntityModel<PositionDTO> resource = EntityModel.of(dto);
            //localhost:8080/patients/1
            WebMvcLinkBuilder link1 = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(dto.getIdPosition()));
            resource.add(link1);
        }


    return new ResponseEntity<>(lst,HttpStatus.OK);
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<PositionDTO> findById(@PathVariable("id") Integer id) throws Exception{
        EntityModel<PositionDTO> resource = EntityModel.of(convertToDto(service.readById(id)));
        //localhost:8080/patients/1
        WebMvcLinkBuilder link1 = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(id));

        resource.add(link1.withRel("patient-info1"));
        return resource;
    } 

    private PositionDTO convertToDto(Position obj){
        return mapper.map(obj, PositionDTO.class);
    }

    private Position convertToEntity(PositionDTO dto){
        return mapper.map(dto, Position.class);
    }

}
