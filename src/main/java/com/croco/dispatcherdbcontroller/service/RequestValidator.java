package com.croco.dispatcherdbcontroller.service;

import com.croco.dispatcherdbcontroller.exception.AuthTokenValidationException;
import com.croco.dispatcherdbcontroller.exception.Md5SignatureValidationException;
import com.croco.dispatcherdbcontroller.exception.RequestValidationException;
import org.springframework.stereotype.Service;

@Service
public class RequestValidator {

    public void validate(String version, String authToken, String md5Signature) {
        if (!isValidVersion(version)) {
            throw new RequestValidationException("Ошибка проверки поля Version");
        }
        if (!isValidAuthToken(authToken)) {
            throw new AuthTokenValidationException("Ошибка проверки поля AuthToken");
        }
        if (!isValidMd5Signature(md5Signature)) {
            throw new Md5SignatureValidationException("Ошибка проверки поля Md5Signature");
        }
    }

    private boolean isValidVersion(String version) {
        // TODO Логика проверки версии
        if (!version.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isValidAuthToken(String authToken) {
        // TODO Логика проверки токена аутентификации
        if (!authToken.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isValidMd5Signature(String md5Signature) {
        // TODO Логика проверки MD5 подписи
        if (!md5Signature.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}
