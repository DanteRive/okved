package com.example.okved.cache;

import com.example.okved.client.OkvedRemoteClient;
import com.example.okved.model.OkvedEntry;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Кэш списка ОКВЭД в памяти.
 *
 * <p>При обновлении кэш разворачивает иерархическое дерево okved.json
 * в плоский список всех кодов (включая разделы, группы и подгруппы).</p>
 */
@Component
@RequiredArgsConstructor
public class OkvedCache {

    private final OkvedRemoteClient okvedRemoteClient;

    /**
     * Плоский список всех записей ОКВЭД.
     */
    private final AtomicReference<List<OkvedEntry>> cache =
            new AtomicReference<>(Collections.emptyList());

    /**
     * Момент последнего обновления кэша.
     */
    @Getter
    private volatile Instant lastUpdated;

    /**
     * Возвращает актуальный плоский список ОКВЭД.
     * При первом обращении загружает и разворачивает иерархию из okved.json.
     *
     * @return нередактируемый список ОКВЭД
     */
    public List<OkvedEntry> getAll() {
        List<OkvedEntry> current = cache.get();
        if (current.isEmpty()) {
            refresh();
            current = cache.get();
        }
        return current;
    }

    /**
     * Принудительно обновляет кэш из удалённого ресурса.
     * Иерархическая структура дерева разворачивается в плоский список.
     */
    public synchronized void refresh() {
        // Загружаем корневые элементы (дерево)
        List<OkvedEntry> rootEntries = okvedRemoteClient.loadOkved();

        // Разворачиваем дерево в плоский список
        List<OkvedEntry> flat = new ArrayList<>();
        for (OkvedEntry root : rootEntries) {
            collectRecursive(root, flat);
        }

        cache.set(List.copyOf(flat));
        lastUpdated = Instant.now();
    }

    /**
     * Рекурсивно обходит дерево ОКВЭД и добавляет каждый узел в плоский список.
     *
     * @param node текущий узел
     * @param acc  аккумулятор для плоского списка
     */
    private void collectRecursive(OkvedEntry node, List<OkvedEntry> acc) {
        if (node == null) {
            return;
        }
        // Добавляем сам узел
        acc.add(new OkvedEntry(node.getCode(), node.getName(), null));

        // Обходим дочерние элементы
        if (node.getItems() != null) {
            for (OkvedEntry child : node.getItems()) {
                collectRecursive(child, acc);
            }
        }
    }
}