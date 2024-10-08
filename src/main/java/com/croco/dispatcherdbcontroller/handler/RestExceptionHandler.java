package com.croco.dispatcherdbcontroller.handler;

import com.croco.dispatcherdbcontroller.dto.ErrorData;
import com.croco.dispatcherdbcontroller.exception.AuthTokenValidationException;
import com.croco.dispatcherdbcontroller.exception.Md5SignatureValidationException;
import com.croco.dispatcherdbcontroller.exception.RequestValidationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RequestValidationException.class)
    protected ResponseEntity<Object> handleHeaderArgumentException(
            RequestValidationException ex, HttpServletRequest httpRequest){
        return buildResponseEntity(HttpStatus.BAD_REQUEST,
                ex.getLocalizedMessage(),
                httpRequest.getRequestURL());
    }

    @ExceptionHandler(AuthTokenValidationException.class)
    protected ResponseEntity<Object> handleHeaderArgumentException(
            AuthTokenValidationException ex, HttpServletRequest httpRequest){
        return buildResponseEntity(HttpStatus.UNAUTHORIZED,
                ex.getLocalizedMessage(),
                httpRequest.getRequestURL());
    }

    @ExceptionHandler(Md5SignatureValidationException.class)
    protected ResponseEntity<Object> handleHeaderArgumentException(
            Md5SignatureValidationException ex, HttpServletRequest httpRequest){
        return buildResponseEntity(HttpStatus.FORBIDDEN,
                ex.getLocalizedMessage(),
                httpRequest.getRequestURL());
    }

    private ResponseEntity<Object> buildResponseEntity(HttpStatus httpStatus, String errorMessage, StringBuffer requestURL) {
        ErrorData errorData = new ErrorData(String.valueOf(httpStatus.value()), httpStatus.name(), errorMessage);
        String url = requestURL.toString().replaceAll("[\n\r\t]", "_");
        //log.error("Error when calling \"{}\": {}", url, errorData);
        return new ResponseEntity<>(errorData, httpStatus);
    }

}
