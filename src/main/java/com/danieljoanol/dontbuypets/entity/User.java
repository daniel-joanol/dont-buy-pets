package com.danieljoanol.dontbuypets.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.danieljoanol.dontbuypets.enumarator.Roles;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User implements GenericEntity<User> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Roles role;
    private String image;
    private String password;
    private Boolean active = false;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private String activationCode;
    private LocalDateTime codeDate;
    
    private String newPassword;
    private String newPassCode;
    private LocalDateTime newPassDate;
    
    private String newEmail;
    private String newEmailCode;
    private LocalDateTime newEmailDate;

}
