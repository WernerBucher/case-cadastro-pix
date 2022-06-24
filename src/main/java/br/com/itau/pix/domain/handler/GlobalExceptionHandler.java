package br.com.itau.pix.domain.handler;

import br.com.itau.pix.domain.dto.ErrorObjectDTO;
import br.com.itau.pix.domain.dto.ErrorResponseDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ErrorObjectDTO> errors = getErrors(ex);
        ErrorResponseDTO errorResponse = getErrorResponse(errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return getRespostaPadrao(ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {
        return getRespostaPadrao(ex.getMessage());
    }

    private ResponseEntity<Object> getRespostaPadrao(String ex) {
        ErrorObjectDTO errorObject = new ErrorObjectDTO(ex, null, null);
        List<ErrorObjectDTO> errors = new ArrayList<>();
        errors.add(errorObject);
        return new ResponseEntity<>(new ErrorResponseDTO(errors), HttpStatus.UNPROCESSABLE_ENTITY);
    }


    private ErrorResponseDTO getErrorResponse(List<ErrorObjectDTO> errors) {
        return new ErrorResponseDTO(errors);
    }

    private List<ErrorObjectDTO> getErrors(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new ErrorObjectDTO(error.getDefaultMessage(), error.getField(), error.getRejectedValue()))
                .collect(Collectors.toList());
    }
}
