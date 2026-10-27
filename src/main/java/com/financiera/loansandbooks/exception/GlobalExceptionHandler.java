package com.financiera.loansandbooks.exception;

import com.financiera.loansandbooks.controller.Dtos.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleNotFound(ResourceNotFoundException ex){
        return build(HttpStatus.NOT_FOUND, List.of(ex.getMessage()));
    }

    @ExceptionHandler({
            InactiveReaderException.class,
            NoStockException.class,
            LoanLimitException.class,
            InvalidLoanStateException.class
    })
    public ResponseEntity<ErrorDTO> handleBusiness(RuntimeException ex){
        return build(HttpStatus.CONFLICT, List.of(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleValidation(MethodArgumentNotValidException ex){
        List<String> messages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();
        return build(HttpStatus.BAD_REQUEST, messages);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDTO> handleNotReadable(HttpMessageNotReadableException ex){
        return build(HttpStatus.BAD_REQUEST, List.of("Request body is missing or malformed."));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDTO> handleTypeMismatch(MethodArgumentTypeMismatchException ex){
        return build(HttpStatus.BAD_REQUEST, List.of("Invalid value for parameter " + ex.getName() + "."));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGeneral(Exception ex){
        return build(HttpStatus.INTERNAL_SERVER_ERROR, List.of("Unexpected error."));
    }

    private ResponseEntity<ErrorDTO> build(HttpStatus status, List<String> messages){
        ErrorDTO error = new ErrorDTO(LocalDateTime.now(), status.value(), status.getReasonPhrase(), messages);
        return ResponseEntity.status(status).body(error);
    }
}
