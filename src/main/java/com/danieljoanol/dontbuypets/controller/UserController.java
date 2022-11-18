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

import com.danieljoanol.dontbuypets.assembler.UserAssembler;
import com.danieljoanol.dontbuypets.dto.UserDTO;
import com.danieljoanol.dontbuypets.entity.User;
import com.danieljoanol.dontbuypets.exception.EmptyImageException;
import com.danieljoanol.dontbuypets.exception.InvalidImageFormatException;
import com.danieljoanol.dontbuypets.repository.UserRepository;
import com.danieljoanol.dontbuypets.service.user.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@Controller
@RequestMapping(URL.API_V1_USER)
@Api(value = "User Controller", description = "Controller with the CRUD methods related to Users")
@CrossOrigin
public class UserController extends GenericController<User, UserDTO> {

    @Autowired
    private UserService userService;

    public UserController(UserRepository userRepository, UserAssembler userAssembler) {
        super(userRepository, userAssembler);
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
    
}
