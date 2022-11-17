package com.danieljoanol.dontbuypets.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;

@Controller
@RequestMapping("/api/v1/populator")
@Api(value = "Pet Controller", description = "Controller with the CRUD methods related to Pets")
@CrossOrigin
public class PetController {
    
}
