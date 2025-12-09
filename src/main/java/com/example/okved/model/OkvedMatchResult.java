package com.example.okved.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Доменная модель результата подбора кода ОКВЭД.
 */
@Data
@AllArgsConstructor
public class OkvedMatchResult {

    /**
     * Код ОКВЭД.
     */
    private String code;

    /**
     * Наименование вида деятельности по ОКВЭД.
     */
    private String name;

    /**
     * Длина совпадения по суффиксу между номером телефона и кодом.
     */
    private int matchLength;

    /**
     * Стратегия, по которой был выбран код.
     */
    private MatchStrategy strategy;
}