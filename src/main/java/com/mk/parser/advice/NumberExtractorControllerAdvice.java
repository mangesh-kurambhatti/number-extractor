package com.mk.parser.advice;

import com.mk.parser.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NumberExtractorControllerAdvice {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleBusinessException(BusinessException e){
        return new ResponseEntity<>(e.getMsg(), HttpStatus.NOT_FOUND);
    }
}
