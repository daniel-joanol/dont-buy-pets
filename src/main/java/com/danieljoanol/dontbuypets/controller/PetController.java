package com.danieljoanol.dontbuypets.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.danieljoanol.dontbuypets.assembler.PetAssembler;
import com.danieljoanol.dontbuypets.dto.PetDTO;
import com.danieljoanol.dontbuypets.entity.Pet;
import com.danieljoanol.dontbuypets.exception.EmptyImageException;
import com.danieljoanol.dontbuypets.exception.InvalidImageFormatException;
import com.danieljoanol.dontbuypets.repository.PetRepository;
import com.danieljoanol.dontbuypets.service.pet.PetService;
import com.danieljoanol.dontbuypets.controller.util.URL;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@Controller
@RequestMapping(URL.API_V1_PET)
@Api(value = "Pet Controller", description = "Controller with the CRUD methods related to Pets")
@CrossOrigin
public class PetController extends GenericController<Pet, PetDTO> {
    
    @Autowired
    private PetService petService;

    public PetController(PetRepository petRepository, PetAssembler petAssembler) {
        super(petRepository, petAssembler);
    }

    @ApiOperation(value = "Adds an image", httpMethod = "POST")
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 500, message = "System error")})
    @PostMapping("/image/{id}")
    public ResponseEntity<String> addPhoto(@PathVariable Long id, MultipartFile image) 
            throws EmptyImageException, InvalidImageFormatException, IOException {
        String URL = petService.addImage(id, image);
        return ResponseEntity.ok(URL);
    }

    @ApiOperation(value = "Adds a list of images", httpMethod = "POST")
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 500, message = "System error")})
    @PostMapping("/image/list/{id}")
    public ResponseEntity<List<String>> addPhotos(@PathVariable Long id, MultipartFile[] images) 
            throws EmptyImageException, InvalidImageFormatException, IOException {
        List<String> URLs = petService.addImages(id, images);
        return ResponseEntity.ok(URLs);
    }

}
