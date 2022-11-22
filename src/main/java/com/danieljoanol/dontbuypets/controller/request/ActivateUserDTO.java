package com.danieljoanol.dontbuypets.controller.request;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ActivateUserDTO {
    
    @NotBlank(message = "Username can't be empty")
    private String username;
    private String email;
    private String activationCode;

}
