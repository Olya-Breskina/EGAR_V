package ru.podgoretskaya.accounting.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ServiceException {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> newException(HttpMessageNotReadableException e) {
        log.error(e.getMessage(), e);
        String apiError = new String("ошибка заполнения формы");
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
}
