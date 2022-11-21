package com.danieljoanol.dontbuypets.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.danieljoanol.dontbuypets.assembler.UserAssembler;
import com.danieljoanol.dontbuypets.dto.UserDTO;
import com.danieljoanol.dontbuypets.entity.User;
import com.danieljoanol.dontbuypets.exception.ActivationException;
import com.danieljoanol.dontbuypets.exception.DuplicatedUserDataException;
import com.danieljoanol.dontbuypets.exception.EmptyImageException;
import com.danieljoanol.dontbuypets.exception.InvalidImageFormatException;
import com.danieljoanol.dontbuypets.service.user.UserService;
import com.sparkpost.exception.SparkPostException;
import com.danieljoanol.dontbuypets.controller.request.ActivateUser;
import com.danieljoanol.dontbuypets.controller.util.URL;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@Controller
@RequestMapping(URL.API_V1_USER)
@Api(value = "User Controller", description = "Controller with the CRUD methods related to Users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserAssembler userAssembler;

    @ApiOperation(value = "Create user", httpMethod = "POST")
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 500, message = "System error")})
    @PostMapping("/")
    public ResponseEntity<String> create(@RequestBody UserDTO dto) 
            throws DuplicatedUserDataException, SparkPostException {
        User entity = userAssembler.convertFromDTO(dto);
        String response = userService.createUser(entity);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Activate user", httpMethod = "POST")
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 500, message = "System error")})
    @PostMapping("/activateUser")
    public ResponseEntity<UserDTO> activateUser(@RequestBody ActivateUser activateUser) 
            throws SparkPostException, ActivationException {
        User entity = userService.activateUser(activateUser);
        UserDTO response = userAssembler.convertToDTO(entity);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Activate user", httpMethod = "GET")
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 500, message = "System error")})
    @GetMapping("/activationCode")
    public ResponseEntity<String> getActivationCode(@RequestBody ActivateUser activateUser) 
            throws SparkPostException, ActivationException {
        String response = userService.newActivationCode(activateUser);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Adds an image", httpMethod = "POST")
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 500, message = "System error")})
    @PostMapping("/image/{id}")
    public ResponseEntity<String> addPhoto(@PathVariable Long id, MultipartFile image) 
            throws EmptyImageException, InvalidImageFormatException, IOException {
        String URL = userService.addImage(id, image);
        return ResponseEntity.ok(URL);
    }

    @ApiOperation(value = "Update user", httpMethod = "PUT")
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 500, message = "System error")})
    @PutMapping("/")
    public ResponseEntity<String> activateUser(@RequestBody UserDTO dto) 
            throws SparkPostException, ActivationException, DuplicatedUserDataException {
        User entity = userAssembler.convertFromDTO(dto);
        String response = userService.updateUser(entity);
        return ResponseEntity.ok(response);
    }

    //TODO: endpoint to confirm the change of the email and password
    
}
