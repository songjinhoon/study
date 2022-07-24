package com.study.demoinflearnrestapi.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    // Controller 에 Errors 객체를 추가하면서 MethodArgumentNotValidException 처리를 바로 처리해주지 않음. 의미가 없어짐
    @ExceptionHandler({/*MethodArgumentNotValidException.class,*/ HttpMessageNotReadableException.class})
    public ResponseEntity methodArgumentNotValidException(Exception e) {
        e.printStackTrace();
        return ResponseEntity.internalServerError().build();
    }

}
