package com.example.okved.util;

import com.example.okved.model.MatchStrategy;
import com.example.okved.model.OkvedEntry;
import com.example.okved.model.OkvedMatchResult;
import lombok.experimental.UtilityClass;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Утилита для подбора кода ОКВЭД по суффиксу номера телефона.
 */
@UtilityClass
public class OkvedMatcher {

    /**
     * Ищет код ОКВЭД с максимальным совпадением по окончанию нормализованного номера телефона.
     *
     * @param normalizedPhone нормализованный номер телефона (+79XXXXXXXXX)
     * @param entries         список всех доступных кодов ОКВЭД
     * @return доменная модель результата подбора
     */
    public OkvedMatchResult matchByPhoneSuffix(String normalizedPhone, List<OkvedEntry> entries) {
        Objects.requireNonNull(normalizedPhone, "normalizedPhone must not be null");
        Objects.requireNonNull(entries, "entries must not be null");

        if (entries.isEmpty()) {
            return new OkvedMatchResult(null, null, 0, MatchStrategy.NO_DATA);
        }

        var best = entries.stream()
                .filter(e -> e.getCode() != null && !e.getCode().isBlank())
                .map(e -> new TempMatch(e, commonSuffixLength(normalizedPhone, e.getCode())))
                .max(Comparator.comparingInt(TempMatch::matchLength))
                .orElse(null);

        if (best != null && best.matchLength > 0) {
            return new OkvedMatchResult(
                    best.entry.getCode(),
                    best.entry.getName(),
                    best.matchLength,
                    MatchStrategy.BEST_SUFFIX
            );
        }

        int idx = Math.floorMod(normalizedPhone.hashCode(), entries.size());
        OkvedEntry fallback = entries.get(idx);

        return new OkvedMatchResult(
                fallback.getCode(),
                fallback.getName(),
                0,
                MatchStrategy.FALLBACK_HASH
        );
    }

    private record TempMatch(OkvedEntry entry, int matchLength) { }

    private int commonSuffixLength(String a, String b) {
        int i = a.length() - 1;
        int j = b.length() - 1;
        int len = 0;
        while (i >= 0 && j >= 0 && a.charAt(i) == b.charAt(j)) {
            len++;
            i--;
            j--;
        }
        return len;
    }
}