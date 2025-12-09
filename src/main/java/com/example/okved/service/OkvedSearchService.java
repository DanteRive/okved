package com.example.okved.service;

import com.example.okved.cache.OkvedCache;
import com.example.okved.exception.PhoneNormalizationException;
import com.example.okved.model.OkvedEntry;
import com.example.okved.model.OkvedMatchResult;
import com.example.okved.model.OkvedSearchResponseDto;
import com.example.okved.util.OkvedMatcher;
import com.example.okved.util.PhoneNormalizer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для поиска кодов ОКВЭД по номеру телефона.
 */
@Service
@RequiredArgsConstructor
public class OkvedSearchService {

    private final OkvedCache okvedCache;

    /**
     * Выполняет поиск кода ОКВЭД по номеру телефона.
     *
     * @param rawPhone произвольная строка с номером телефона
     * @return DTO с результатом поиска
     * @throws PhoneNormalizationException если номер нельзя нормализовать
     */
    public OkvedSearchResponseDto searchByPhoneNumber(String rawPhone) throws PhoneNormalizationException {
        String normalized = PhoneNormalizer.normalizeToRussianMobile(rawPhone);

        List<OkvedEntry> entries = okvedCache.getAll();

        OkvedMatchResult match = OkvedMatcher.matchByPhoneSuffix(normalized, entries);

        return new OkvedSearchResponseDto(
                normalized,
                match.getCode(),
                match.getName(),
                match.getMatchLength(),
                match.getStrategy()
        );
    }
}