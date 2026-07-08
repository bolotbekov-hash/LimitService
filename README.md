# LimitService 

Сервис управления лимитами по банковским картам. Позволяет создавать клиентов и карты, назначать картам лимиты по умолчанию, изменять текущие лимиты (сумму и количество операций) и хранить историю изменений лимитов.

## Стек технологий

- **Java 17**
- **Spring Boot 3.4.0**
  - Spring Web (REST API)
  - Spring Data JPA
  - Spring Validation
- **PostgreSQL** (драйвер `org.postgresql`)
- **Lombok** — уменьшение шаблонного кода (геттеры/сеттеры, билдеры, конструкторы)
- **springdoc-openapi** — автогенерация Swagger UI / OpenAPI документации
- **JUnit / Spring Boot Test** — unit- и интеграционные тесты
- **Maven** — сборка проекта

## Требования

- JDK 17+
- Maven 3.8+
- PostgreSQL 13+ (запущенный локально или в контейнере)

## Настройка базы данных

Перед запуском создайте базу данных PostgreSQL:

```sql
CREATE DATABASE limit_service_db;
```

Параметры подключения заданы в `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/limit_service_db
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
server.port=8080
```

При необходимости замените `username`/`password` на свои. Схема таблиц создаётся/обновляется автоматически Hibernate (`ddl-auto=update`).

## Запуск проекта

```bash
# Сборка
mvn clean install

# Запуск
mvn spring-boot:run
```

Приложение будет доступно на `http://localhost:8080`.

## Документация API (Swagger)

После запуска доступна автоматически сгенерированная документация:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/api-docs`

## Модель данных

| Сущность | Описание |
|---|---|
| **Customer** | Клиент банка: ФИО, тип клиента, телефон, ИНН |
| **Card** | Банковская карта клиента: номер карты, номер счёта, тип, статус (`ACTIVE`/др.), даты открытия/окончания/закрытия |
| **Limit** | Тип лимита (шаблон): тип, сумма и число операций по умолчанию, максимальные значения |
| **CardLimit** | Привязка конкретного лимита к конкретной карте: текущая сумма, текущее число операций, дата последнего обновления |
| **LimitHistory** | История изменений лимита по карте: старое/новое значение суммы и числа операций, дата изменения |

При создании карты (`CardService.createCard`) ей автоматически назначаются все существующие типы лимитов со значениями по умолчанию (`CardLimitService.assignDefaultLimitsToCard`), и это фиксируется в истории.

## REST API

### Клиенты — `/api/v1/customers`

| Метод | Путь | Описание |
|---|---|---|
| `POST` | `/api/v1/customers` | Создать клиента |
| `PUT`  | `/api/v1/customers/update/{id}` | Обновить данные клиента |
| `GET`  | `/api/v1/customers` | Получить список всех клиентов |
| `GET`  | `/api/v1/customers/get/{id}` | Получить клиента по ID |

Пример запроса на создание клиента (`CustomerRequestDto`):

```json
{
  "fullName": "Иванов Иван Иванович",
  "customerType": 1,
  "phoneNumber": "+996700123456",
  "inn": "12345678901234"
}
```

Валидация: `phoneNumber` — формат `+996` и 9 цифр, `inn` — ровно 14 цифр.

### Карты — `/api/v1/cards`

| Метод | Путь | Описание |
|---|---|---|
| `POST` | `/api/v1/cards` | Выпустить карту клиенту (автоматически назначаются лимиты по умолчанию) |
| `PUT`  | `/api/v1/cards/update/{id}` | Обновить данные карты |
| `GET`  | `/api/v1/cards/customer/{customerId}/active` | Получить активные карты клиента |
| `GET`  | `/api/v1/cards/get/{id}` | Получить карту по ID |

Пример запроса на создание карты (`CardRequestDto`):

```json
{
  "customerId": 1,
  "cardNumber": "4400430012345678",
  "accountNumber": "1234567890123456",
  "cardType": "DEBIT"
}
```

### Типы лимитов (шаблоны) — `/api/v1/limits`

| Метод | Путь | Описание |
|---|---|---|
| `POST` | `/api/v1/limits` | Создать тип лимита |
| `PUT`  | `/api/v1/limits/update/{id}` | Обновить тип лимита |
| `GET`  | `/api/v1/limits/get/active` | Получить все типы лимитов |

### Лимиты карты — `/api/v1/card-limits`

| Метод | Путь | Описание |
|---|---|---|
| `PUT`  | `/api/v1/card-limits/update/{cardLimitId}` | Изменить текущий лимит карты (сумма/число операций) |
| `GET`  | `/api/v1/card-limits/get/card/{cardId}` | Получить все лимиты по карте |

Пример запроса на изменение лимита (`UpdateLimitRequestDto`):

```json
{
  "newSum": 50000,
  "newOperationCount": 20
}
```

Бизнес-правила при изменении лимита:
- Карта должна быть в статусе `ACTIVE`, иначе — ошибка.
- Сумма лимита не может быть отрицательной и не может превышать `1 000 000`.
- Количество операций не может быть отрицательным (максимум ограничен валидацией DTO — `100`).
- Каждое изменение фиксируется в истории лимитов.

### История лимитов — `/api/v1/limit-history`

| Метод | Путь | Описание |
|---|---|---|
| `GET` | `/api/v1/limit-history/get/card/{cardId}` | Получить историю изменений лимитов по карте |

## Обработка ошибок

Глобальный обработчик исключений (`GlobalExceptionHandler`) перехватывает ошибки, включая:
- `ResourceNotFoundException` — сущность не найдена (клиент, карта, лимит и т.д.);
- ошибки валидации входных DTO;
- прочие runtime-исключения бизнес-логики (например, попытка изменить лимит заблокированной карты).

## Тестирование

Проект содержит:
- Unit-тесты сервисного слоя (`LimitServiceTest`);
- Интеграционные тесты контроллера (`LimitControllerIT`);
- Базовый тест контекста приложения (`PracticeWork2ApplicationTests`).

Запуск тестов:

```bash
mvn test
```

## Структура проекта


src/main/java/bakaibank/PracticeWork2/
├── controller/     # REST-контроллеры
├── dto/            # Data Transfer Objects (запросы/ответы)
├── entity/         # JPA-сущности
├── exception/       # Обработка исключений
├── mapper/         # Мапперы entity <-> DTO
├── repository/     # Spring Data JPA репозитории
└── service/        # Бизнес-логика
