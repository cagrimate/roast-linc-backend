package com.roast.linc.controller;

import com.roast.linc.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Hidden
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Void>> handleRuntimeException(RuntimeException e) {
        return new ResponseEntity<>(
                ApiResponse.error(e.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(Exception e) {
        return new ResponseEntity<>(
                ApiResponse.error("Sistemde beklenmedik bir hata oluştu."),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
