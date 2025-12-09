package com.example.okved.cache;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Планировщик периодического обновления кэша ОКВЭД.
 */
@Component
@RequiredArgsConstructor
public class OkvedRefresher {

    private static final Logger log = LoggerFactory.getLogger(OkvedRefresher.class);

    private final OkvedCache okvedCache;

    /**
     * Периодически обновляет кэш ОКВЭД.
     * Здесь задано обновление каждый час.
     */
    @Scheduled(cron = "0 0 * * * *")
    public void refreshOkvedCache() {
        log.info("Плановое обновление кэша ОКВЭД...");
        okvedCache.refresh();
        log.info("Кэш ОКВЭД обновлён");
    }
}