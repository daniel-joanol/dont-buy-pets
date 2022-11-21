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
@Table(name = "moderator")
public class User implements GenericEntity<User> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Roles role;
    private String image;
    private String password;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private Long activationCode;
    private LocalDateTime codeDate;
    private Boolean active = false;

    private String newPassword;
    private Long newPassCode;
    private LocalDateTime newPassDate;
    
    private String newEmail;
    private Long newEmailCode;
    private LocalDateTime newEmailDate;

}
