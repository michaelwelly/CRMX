package com.croco.dispatcherdbcontroller.exception;

//Проверка входных параметров запроса
public class AuthTokenValidationException extends IllegalArgumentException {
    public AuthTokenValidationException(String message){
        super(message);
    }
}