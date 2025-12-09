OKVED Search Service

Краткое описание
- Небольшой сервис на Spring Boot, подбирающий код ОКВЭД по номеру телефона.
- Данные ОКВЭД загружаются из удалённого JSON и кэшируются; кэш обновляется раз в час.

Требования
- Java 25
- Maven Wrapper (в репозитории есть `mvnw`, `mvnw.cmd`)

Установка и запуск (Windows)
1) Запуск из исходников:
   - `./mvnw.cmd spring-boot:run`
2) Сборка и запуск JAR:
   - `./mvnw.cmd clean package`
   - `java -jar target/okved-0.0.1-SNAPSHOT.jar`

Использование контроллера поиска
- Эндпоинт: `POST /api/v1/okved/search`
- Тело запроса (JSON):
  ```json
  {
    "phoneNumber": "+7 (999) 123-45-67"
  }
  ```
- Пример успешного ответа:
  ```json
  {
    "normalizedPhone": "+79991234567",
    "okvedCode": "01.11.11",
    "okvedName": "Выращивание зерновых культур",
    "matchLength": 3,
    "strategy": "BEST_SUFFIX"
  }
  ```
- Ошибка (HTTP 400), если номер нельзя нормализовать:
  ```json
  {
    "success": false,
    "error": "INVALID_PHONE",
    "message": "Номер не соответствует формату российского мобильного +79XXXXXXXXX"
  }
  ```

Пример вызова (curl)
```bash
curl -X POST http://localhost:8080/api/v1/okved/search \
  -H "Content-Type: application/json" \
  -d '{"phoneNumber":"+7 (999) 123-45-67"}'
```