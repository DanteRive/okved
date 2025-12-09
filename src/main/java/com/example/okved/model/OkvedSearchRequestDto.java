package com.example.okved.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO запроса на поиск ОКВЭД по номеру телефона.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OkvedSearchRequestDto {

    /**
     * Номер телефона в произвольном формате, как введён пользователем.
     */
    private String phoneNumber;
}