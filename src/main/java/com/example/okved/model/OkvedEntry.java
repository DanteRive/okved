package com.example.okved.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Модель записи ОКВЭД, соответствующая структуре okved.json.
 *
 * <p>Структура иерархическая: каждая запись может содержать дочерние элементы в поле {@code items}.</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OkvedEntry {

    /**
     * Код ОКВЭД или группировки (например, "01.11.11" или "Раздел A").
     */
    private String code;

    /**
     * Наименование вида деятельности или раздела.
     */
    private String name;

    /**
     * Дочерние элементы (подвиды деятельности, подгруппы и т.п.).
     */
    private List<OkvedEntry> items;
}