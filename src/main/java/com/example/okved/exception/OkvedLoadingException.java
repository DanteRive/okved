package com.example.okved.exception;

/**
 * Исключение, возникающее при ошибках загрузки или парсинга okved.json.
 */
public class OkvedLoadingException extends RuntimeException {

    /**
     * Создаёт новое исключение с сообщением и причиной.
     *
     * @param message текст сообщения об ошибке
     * @param cause   первопричина ошибки
     */
    public OkvedLoadingException(String message, Throwable cause) {
        super(message, cause);
    }
}