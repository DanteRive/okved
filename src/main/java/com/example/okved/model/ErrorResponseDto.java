package com.example.okved.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO ответа об ошибке для REST API.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDto {

    /**
     * Признак успешности операции.
     */
    private boolean success;

    /**
     * Код ошибки, пригодный для машинной обработки.
     */
    private String error;

    /**
     * Человекочитаемое сообщение об ошибке.
     */
    private String message;
}