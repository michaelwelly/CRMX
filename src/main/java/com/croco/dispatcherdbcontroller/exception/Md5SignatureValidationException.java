package com.croco.dispatcherdbcontroller.exception;

//Проверка входных параметров запроса
public class Md5SignatureValidationException extends IllegalArgumentException {
    public Md5SignatureValidationException(String message){
        super(message);
    }
}