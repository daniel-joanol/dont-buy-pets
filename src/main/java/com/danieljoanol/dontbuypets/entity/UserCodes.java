package com.danieljoanol.dontbuypets.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "userCodes")
public class UserCodes implements GenericEntity<UserCodes> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;

    private String activationCode;
    private LocalDateTime activationCodeDate;
    
    private String newPassword;
    private String newPassCode;
    private LocalDateTime newPassCodeDate;
    
    private String newEmail;
    private String newEmailCode;
    private LocalDateTime newEmailCodeDate;
}
