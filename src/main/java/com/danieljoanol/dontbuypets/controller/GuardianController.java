package com.danieljoanol.dontbuypets.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.danieljoanol.dontbuypets.assembler.GuardianAssembler;
import com.danieljoanol.dontbuypets.dto.GuardianDTO;
import com.danieljoanol.dontbuypets.entity.Guardian;
import com.danieljoanol.dontbuypets.exception.EmptyImageException;
import com.danieljoanol.dontbuypets.exception.InvalidImageFormatException;
import com.danieljoanol.dontbuypets.repository.GuardianRepository;
import com.danieljoanol.dontbuypets.service.guardian.GuardianService;
import com.danieljoanol.dontbuypets.controller.util.URL;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@Controller
@RequestMapping(URL.API_V1_GUARDIAN)
@Api(value = "Guardian Controller", description = "Controller with the CRUD methods related to Guardians")
@CrossOrigin
public class GuardianController extends GenericController<Guardian, GuardianDTO> {
    
    @Autowired
    private GuardianService guardianService;

    public GuardianController(GuardianRepository guardianRepository, GuardianAssembler guardianAssembler) {
        super(guardianRepository, guardianAssembler);
    }

    @ApiOperation(value = "Adds an image", httpMethod = "POST")
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 500, message = "System error")})
    @PostMapping("/image/{id}")
    public ResponseEntity<String> addPhoto(@PathVariable Long id, MultipartFile image) 
            throws EmptyImageException, InvalidImageFormatException, IOException {
        String URL = guardianService.addImage(id, image);
        return ResponseEntity.ok(URL);
    }

}
