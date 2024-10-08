package com.croco.dispatcherdbcontroller.exception;

//Проверка входных параметров запроса
public class RequestValidationException extends IllegalArgumentException {
    public RequestValidationException(String message){
        super(message);
    }
}
