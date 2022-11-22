package com.danieljoanol.dontbuypets.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import com.danieljoanol.dontbuypets.assembler.CityAssembler;
import com.danieljoanol.dontbuypets.dto.CityDTO;
import com.danieljoanol.dontbuypets.entity.City;
import com.danieljoanol.dontbuypets.repository.CityRepository;
import com.danieljoanol.dontbuypets.controller.util.URL;

import io.swagger.annotations.Api;

@Controller
@RequestMapping(URL.API_V1_CITY)
@Api(value = "City Controller", description = "Controller with the CRUD methods related to Cities")
@CrossOrigin
public class CityController extends GenericController<City, CityDTO> {
    
    public CityController(CityRepository cityRepository, CityAssembler cityAssembler) {
        super(cityRepository, cityAssembler);
    }

}
