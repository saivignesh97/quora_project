package com.upgrad.quora.api.ExceptionHandler;

import com.upgrad.quora.api.model.ErrorResponse;
import com.upgrad.quora.service.exception.SignUpRestrictedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(SignUpRestrictedException.class)
    public ResponseEntity<ErrorResponse> signUpRestrictedException(SignUpRestrictedException ex, WebRequest webReq) {
        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse().code(ex.getCode()).message(ex.getErrorMessage()),
                HttpStatus.CONFLICT
        );
    }
}
