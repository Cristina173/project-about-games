package com.todolistrest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// annotare care permite handle-ul exceptiilor ca si mesaje
@RestControllerAdvice
public class ExceptionController {

    // specificam pentru ce exceptie facem handle
    @ExceptionHandler(value = {ResourceNotFoundException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ExceptionMessage handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        ExceptionMessage exceptionMessage = new ExceptionMessage();
//        List<String> messages = new ArrayList<>();
//        messages.add(resourceNotFoundException.getMessage());
//        exceptionMessage.setMessage(messages);
        exceptionMessage.setMessage(List.of(resourceNotFoundException.getMessage()));
        return exceptionMessage;
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ExceptionMessage handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        // luam erorile de pe exceptie
        BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
        List<FieldError> errors = bindingResult.getFieldErrors();

        // citire erori in chain


        // transformam mesajele intr-un format prietenos pentru utilizator
        List<String> errorMessages = errors.stream().map(error -> {
            return error.getDefaultMessage() + " pentru field-ul " + error.getField();
        }).collect(Collectors.toList());

        // construim un obiect de tipul exception message
        ExceptionMessage exceptionMessage = new ExceptionMessage();

        // setam pe obiectul exception message lista de mesaje de eroare
        exceptionMessage.setMessage(errorMessages);

        return exceptionMessage;
    }

}
