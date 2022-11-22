package com.danieljoanol.dontbuypets.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.danieljoanol.dontbuypets.controller.request.ActivateUserDTO;
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
    public ResponseEntity<String> create(@Valid @RequestBody UserDTO dto) 
            throws DuplicatedUserDataException, SparkPostException {
        User entity = userAssembler.convertFromDTO(dto);
        String response = userService.createUser(entity);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Activate user", httpMethod = "POST")
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 500, message = "System error")})
    @PostMapping("/activate/account")
    public ResponseEntity<UserDTO> activateUser(@Valid @RequestBody ActivateUserDTO activateUser) 
            throws SparkPostException, ActivationException {
        User entity = userService.activateUser(activateUser);
        UserDTO response = userAssembler.convertToDTO(entity);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Get new activation code by email", httpMethod = "POST")
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 500, message = "System error")})
    @PostMapping("/activate/account/newCode")
    public ResponseEntity<String> getNewActivationCode(@Valid @RequestBody ActivateUserDTO activateUser) 
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
    public ResponseEntity<String> updateUser(@RequestBody UserDTO dto) 
            throws SparkPostException, ActivationException, DuplicatedUserDataException {
        User entity = userAssembler.convertFromDTO(dto);
        String response = userService.updateUser(entity);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Activate new password", httpMethod = "POST")
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 500, message = "System error")})
    @PostMapping("/activate/password")
    public ResponseEntity<UserDTO> activateNewPassword(@Valid @RequestBody ActivateUserDTO activateUser) 
            throws SparkPostException, ActivationException {
        User entity = userService.activateNewPassword(activateUser);
        UserDTO response = userAssembler.convertToDTO(entity);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Activate new email", httpMethod = "POST")
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 500, message = "System error")})
    @PostMapping("/activate/email")
    public ResponseEntity<UserDTO> activateNewEmail(@Valid @RequestBody ActivateUserDTO activateUser) 
            throws SparkPostException, ActivationException {
        User entity = userService.activateNewEmail(activateUser);
        UserDTO response = userAssembler.convertToDTO(entity);
        return ResponseEntity.ok(response);
    }
    
    @ApiOperation(value = "Get User by Id", httpMethod = "GET")
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 500, message = "System error")})
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getOne(@PathVariable Long id) {
        User entity = userService.get(id);
        UserDTO response = userAssembler.convertToDTO(entity);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Get all Users", httpMethod = "GET")
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 500, message = "System error")})
    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAll() {
        List<User> entities = userService.getAll();
        List<UserDTO> response = userAssembler.convertToDTO(entities);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Delete User", httpMethod = "DELETE")
    @ApiResponses(value = { //
            @ApiResponse(code = 204, message = "NO CONTENT"),
            @ApiResponse(code = 500, message = "System error")})
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
