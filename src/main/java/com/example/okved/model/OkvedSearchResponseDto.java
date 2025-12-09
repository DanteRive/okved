package com.example.okved.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO ответа с результатом поиска кода ОКВЭД по номеру телефона.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OkvedSearchResponseDto {

    /**
     * Нормализованный номер в формате +79XXXXXXXXX.
     */
    private String normalizedPhone;

    /**
     * Код ОКВЭД, найденный по номеру телефона.
     */
    private String okvedCode;

    /**
     * Наименование вида деятельности по найденному коду ОКВЭД.
     */
    private String okvedName;

    /**
     * Длина совпадения суффикса между номером и кодом ОКВЭД.
     */
    private int matchLength;

    /**
     * Стратегия, по которой был выбран код ОКВЭД.
     */
    private MatchStrategy strategy;
}