package br.com.itau.pix.domain.handler;

import br.com.itau.pix.domain.dto.ErroDTO;
import br.com.itau.pix.domain.dto.RespostaErroDTO;
import br.com.itau.pix.domain.exception.ChaveNaoEncontradaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
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

    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ErroDTO> errors = obtemErrosDaValidacao(ex.getBindingResult());
        RespostaErroDTO errorResponse = new RespostaErroDTO(errors);
        logger.warn("Requisição não atende os requisitos obrigatórios:" + errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ChaveNaoEncontradaException.class)
    protected ResponseEntity<Object> handleChaveNaoEncontradaException(ChaveNaoEncontradaException ex, WebRequest request) {
        return getRespostaPadrao(ex.getMessage(),  HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {
        return getRespostaPadrao(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String mensagem = ex.getMessage();
        if (mensagem!=null && mensagem.contains("nested")){
            int limitador = ex.getMessage().indexOf("nested");
            mensagem = mensagem.substring(0, limitador-1);
        }
        return getRespostaPadrao(mensagem, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private ResponseEntity<Object> getRespostaPadrao(String mensagemException, HttpStatus httpStatus) {
        ErroDTO errorObject = new ErroDTO(mensagemException, null, null);
        List<ErroDTO> errors = new ArrayList<>();
        errors.add(errorObject);
        logger.warn("Requisição não atende os requisitos obrigatórios:" + errors);
        return new ResponseEntity<>(new RespostaErroDTO(errors), httpStatus);
    }

    private List<ErroDTO> obtemErrosDaValidacao(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
                .map(error -> new ErroDTO(error.getDefaultMessage(), error.getField(), error.getRejectedValue()))
                .collect(Collectors.toList());
    }
}
