package com.example.okved.client;

import com.example.okved.exception.OkvedLoadingException;
import com.example.okved.model.OkvedEntry;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

/**
 * Клиент для загрузки файла okved.json по HTTPS из удалённого источника.
 */
@Component
@RequiredArgsConstructor
public class OkvedRemoteClient {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${okved.remote-url}")
    private String okvedUrl;

    @Value("${okved.http.connect-timeout:5000}")
    private long connectTimeoutMs;

    @Value("${okved.http.read-timeout:5000}")
    private long readTimeoutMs;

    private HttpClient httpClient;

    /**
     * Загружает и парсит файл okved.json.
     *
     * @return список записей ОКВЭД
     */
    public List<OkvedEntry> loadOkved() {
        ensureClientInitialized();

        try {
            Duration readTimeout = Duration.ofMillis(readTimeoutMs);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(okvedUrl))
                    .timeout(readTimeout)
                    .GET()
                    .build();

            HttpResponse<String> response =
                    httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new OkvedLoadingException(
                        "Не удалось загрузить okved.json, статус: " + response.statusCode(), null);
            }

            String body = response.body();

            return objectMapper.readValue(body, new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new OkvedLoadingException("Ошибка при загрузке или разборе okved.json", e);
        }
    }

    private synchronized void ensureClientInitialized() {
        if (httpClient == null) {
            httpClient = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofMillis(connectTimeoutMs))
                    .build();
        }
    }
}