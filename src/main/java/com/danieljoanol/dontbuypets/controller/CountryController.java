package com.danieljoanol.dontbuypets.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import com.danieljoanol.dontbuypets.assembler.CountryAssembler;
import com.danieljoanol.dontbuypets.dto.CountryDTO;
import com.danieljoanol.dontbuypets.entity.Country;
import com.danieljoanol.dontbuypets.repository.CountryRepository;

import io.swagger.annotations.Api;

@Controller
@RequestMapping(URL.API_V1_COUNTRY)
@Api(value = "Country Controller", description = "Controller with the CRUD methods related to Countries")
@CrossOrigin
public class CountryController extends GenericController<Country, CountryDTO> {

    public CountryController(CountryRepository countryRepository, CountryAssembler countryAssembler) {
        super(countryRepository, countryAssembler);
    }
    
}
