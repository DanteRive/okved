package com.example.okved.util;

import com.example.okved.exception.PhoneNormalizationException;
import lombok.experimental.UtilityClass;

/**
 * Утилитный класс для нормализации российских мобильных номеров
 * к формату +79XXXXXXXXX.
 */
@UtilityClass
public class PhoneNormalizer {

    private static final String RUS_MOBILE_PATTERN = "\\+79\\d{9}";

    /**
     * Нормализует произвольную строку с номером телефона к формату +79XXXXXXXXX.
     *
     * @param rawPhone строка с номером телефона в произвольном формате
     * @return нормализованный номер в формате +79XXXXXXXXX
     * @throws PhoneNormalizationException если нормализация невозможна
     */
    public String normalizeToRussianMobile(String rawPhone) throws PhoneNormalizationException {
        if (rawPhone == null || rawPhone.isBlank()) {
            throw new PhoneNormalizationException("Номер телефона не задан");
        }

        String digits = extractDigits(rawPhone);
        String e164 = normalizeToE164Russian(digits);

        if (!e164.matches(RUS_MOBILE_PATTERN)) {
            throw new PhoneNormalizationException(
                    "Номер не соответствует формату российского мобильного +79XXXXXXXXX");
        }

        return e164;
    }

    private String extractDigits(String rawPhone) throws PhoneNormalizationException {
        String digits = rawPhone.chars()
                .filter(Character::isDigit)
                .collect(StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append)
                .toString();

        if (digits.length() < 10 || digits.length() > 11) {
            throw new PhoneNormalizationException("Неверная длина номера телефона");
        }
        return digits;
    }

    private String normalizeToE164Russian(String digits) throws PhoneNormalizationException {
        if (digits.length() == 11) {
            char first = digits.charAt(0);
            String tail = digits.substring(1);

            if (first == '7' || first == '8') {
                return "+7" + tail;
            }
            throw new PhoneNormalizationException("Номер не является российским мобильным");
        }

        if (digits.charAt(0) != '9') {
            throw new PhoneNormalizationException("Ожидался мобильный номер формата 9XXXXXXXXX");
        }
        return "+7" + digits;
    }
}