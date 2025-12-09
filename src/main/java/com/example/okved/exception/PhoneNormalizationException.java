package com.example.okved.exception;

/**
 * Исключение, сигнализирующее о невозможности нормализовать номер телефона
 * к целевому формату +79XXXXXXXXX.
 */
public class PhoneNormalizationException extends Exception {

    /**
     * Создаёт новое исключение с сообщением.
     *
     * @param message текст сообщения об ошибке
     */
    public PhoneNormalizationException(String message) {
        super(message);
    }
}