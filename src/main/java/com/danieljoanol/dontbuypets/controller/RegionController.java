package com.danieljoanol.dontbuypets.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import com.danieljoanol.dontbuypets.assembler.RegionAssembler;
import com.danieljoanol.dontbuypets.dto.RegionDTO;
import com.danieljoanol.dontbuypets.entity.Region;
import com.danieljoanol.dontbuypets.repository.RegionRepository;
import com.danieljoanol.dontbuypets.controller.util.URL;

import io.swagger.annotations.Api;

@Controller
@RequestMapping(URL.API_V1_REGION)
@Api(value = "Region Controller", description = "Controller with the CRUD methods related to Region")
@CrossOrigin
public class RegionController extends GenericController<Region, RegionDTO> {

    public RegionController(RegionRepository regionRepository, RegionAssembler regionAssembler) {
        super(regionRepository, regionAssembler);
    }
    
}
