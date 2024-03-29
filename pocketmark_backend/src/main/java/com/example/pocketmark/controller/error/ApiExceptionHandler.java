package com.example.pocketmark.controller.error;

import com.example.pocketmark.constant.ErrorCode;
import com.example.pocketmark.dto.common.ApiErrorResponse;
import com.example.pocketmark.dto.common.ValidError;
import com.example.pocketmark.exception.GeneralException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice(annotations = {RestController.class})
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> validation(ConstraintViolationException e, WebRequest request) {
        return handleExceptionInternal(e, ErrorCode.VALIDATION_ERROR, request);
    }

    @ExceptionHandler
    public ResponseEntity<Object> validation(DataIntegrityViolationException e, WebRequest request) {
        return handleExceptionInternal(e, ErrorCode.EMAIL_OR_NICKNAME_EXIST, request);
    }



    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request
    ) {
        List<ValidError> errorList = new ArrayList<>();
        BindingResult bindingResult = e.getBindingResult();

        bindingResult.getAllErrors().forEach(
                error->{
                    FieldError field = (FieldError) error;
                    errorList.add(
                            ValidError.builder()
                                    .field(field.getField())
                                    .message(field.getDefaultMessage())
                                    .invalidValue(field.getRejectedValue().toString())
                                    .build()
                    );
                }
        );

        ErrorCode errorCode = ErrorCode.VALIDATION_ERROR;

        return super.handleExceptionInternal(
                e,
                ApiErrorResponse.of(false, errorCode.getCode(), errorCode.getMessage(errorList.toString())),
                headers,
                status,
                request
        );

    }

    @ExceptionHandler
    public ResponseEntity<Object> general(GeneralException e, WebRequest request) {
        return handleExceptionInternal(e, e.getErrorCode(), request);
    }

    @ExceptionHandler
    public ResponseEntity<Object> exception(Exception e, WebRequest request) {
        return handleExceptionInternal(e, ErrorCode.INTERNAL_ERROR, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, ErrorCode.valueOf(status), headers, status, request);
    }


    private ResponseEntity<Object> handleExceptionInternal(Exception e, ErrorCode errorCode, WebRequest request) {
        return handleExceptionInternal(e, errorCode, HttpHeaders.EMPTY, errorCode.getHttpStatus(), request);
    }

    // private ResponseEntity<Object> handleExceptionInternal(Exception e, ErrorCode errorCode, ApiDataResponse<UserDto.signUpResponse> body) {
    //     return ResponseEntity.status(errorCode.getHttpStatus()).body(body);
    // }

    private ResponseEntity<Object> handleExceptionInternal(Exception e, ErrorCode errorCode, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return super.handleExceptionInternal(
                e,
                ApiErrorResponse.of(false, errorCode.getCode(), errorCode.getMessage(e)),
                headers,
                status,
                request
        );

    }



}