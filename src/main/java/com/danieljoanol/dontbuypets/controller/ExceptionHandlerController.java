package com.danieljoanol.dontbuypets.controller;

import java.util.NoSuchElementException;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.danieljoanol.dontbuypets.exception.ActivationException;
import com.danieljoanol.dontbuypets.exception.DuplicatedUserDataException;
import com.danieljoanol.dontbuypets.exception.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@ResponseBody
public class ExceptionHandlerController {

    @ExceptionHandler(value = { EntityNotFoundException.class, NoSuchElementException.class,
            DuplicatedUserDataException.class,
            ActivationException.class })
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleEntityNotFoundEx(
            HttpServletRequest request, Exception ex) {

        ErrorResponse response = getErrorResponse(ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleMethodArgumentNotValidEx(
            HttpServletRequest request, Exception ex) {

        ErrorResponse response = getErrorResponse(ex);
        String message = response.getMessage();
        String[] messageArr = message.split("\\[");
        messageArr = messageArr[messageArr.length -1].split("]");
        response.setMessage(messageArr[0]);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    private ErrorResponse getErrorResponse(Exception exception) {

        log.error(exception.getMessage(), exception);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(exception.getMessage());
        return errorResponse;
    }
}
