package org.alexwoo.phoneapi.entrypoint;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class BaseEntryPoint {


    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<String> handleConstraintViolationException(ConstraintViolationException e) {
        log.error("Request failed due to ConstraintViolationException", e);
        return List.of(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Request failed due to MethodArgumentNotValidException", e);
        return e.getFieldErrors().stream().map(FieldError::getField).collect(Collectors.toList());
    }
}
