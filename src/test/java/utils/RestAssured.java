package utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.DisplayName;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

/**
 * Класс содержит примеры использования различных функций REST Assured для тестирования REST API.
 * Включает примеры HTTP методов, работы с параметрами, заголовками, телом запроса,
 * аутентификации, валидации ответов, работы с JSON/XML, файлами и многого другого.
 */
@DisplayName("REST Assured Examples: Полный справочник по REST Assured")
public class RestAssured {

    public void setup() {
        io.restassured.RestAssured.baseURI = "https://reqres.in";
        io.restassured.RestAssured.basePath = "/api";
    }

    /**
     * Демонстрирует использование различных HTTP методов (GET, POST, PUT, PATCH, DELETE, HEAD, OPTIONS).
     * Включает примеры выполнения запросов с базовыми параметрами и проверкой статус-кодов.
     */
    @DisplayName("HTTP методы: GET, POST, PUT, PATCH, DELETE, HEAD, OPTIONS")
    public void httpMethodsExamples() {
        // GET запрос
        given()
                .when()
                .get("/users")
                .then()
                .statusCode(200);

        // POST запрос
        given()
                .body("""
                        {
                            "name": "John Doe",
                            "job": "QA Engineer"
                        }
                        """)
                .contentType(ContentType.JSON)
                .when()
                .post("/users")
                .then()
                .statusCode(201);

        // PUT запрос (полное обновление)
        given()
                .body("""
                        {
                            "name": "Jane Doe",
                            "job": "Developer"
                        }
                        """)
                .contentType(ContentType.JSON)
                .when()
                .put("/users/2")
                .then()
                .statusCode(200);

        // PATCH запрос (частичное обновление)
        given()
                .body("""
                        {
                            "job": "Senior Developer"
                        }
                        """)
                .contentType(ContentType.JSON)
                .when()
                .patch("/users/2")
                .then()
                .statusCode(200);

        // DELETE запрос
        given()
                .when()
                .delete("/users/2")
                .then()
                .statusCode(204);

        // HEAD запрос (только заголовки, без тела)
        given()
                .when()
                .head("/users")
                .then()
                .statusCode(200);

        // OPTIONS запрос (получение доступных методов)
        given()
                .when()
                .options("/users")
                .then()
                .statusCode(204);
    }

    /**
     * Демонстрирует работу с различными типами параметров в REST Assured.
     * Включает примеры query параметров, path параметров, form параметров,
     * множественных параметров и параметров из Map.
     */
    @DisplayName("Параметры запроса: Query, Path, Form параметры(параметры, которые отправляются в теле запроса в формате HTML-формы, а не в URL)")
    public void parametersExamples() {
        // Query параметры (?page=2)
        given()
                .queryParam("page", 2)
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .body("page", equalTo(2));

        // Множественные query параметры
        given()
                .queryParam("page", 2)
                .queryParam("per_page", 5)
                .when()
                .get("/users")
                .then()
                .statusCode(200);

        // Query параметры из Map
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("page", 2);
        queryParams.put("per_page", 10);

        given()
                .queryParams(queryParams)
                .when()
                .get("/users")
                .then()
                .statusCode(200);

        // Path параметры (/users/{id})
        given()
                .pathParam("userId", 2)
                .when()
                .get("/users/{userId}")
                .then()
                .statusCode(200)
                .body("data.id", equalTo(2));

        // Множественные path параметры
        given()
                .pathParam("resource", "users")
                .pathParam("id", 2)
                .when()
                .get("/{resource}/{id}")
                .then()
                .statusCode(200);

        // Form параметры (application/x-www-form-urlencoded)
        given()
                .formParam("name", "John")
                .formParam("job", "Developer")
                .when()
                .post("/users")
                .then()
                .statusCode(201);

        // Множественные form параметры из Map
        Map<String, Object> formParams = new HashMap<>();
        formParams.put("email", "test@example.com");
        formParams.put("password", "password123");

        given()
                .formParams(formParams)
                .when()
                .post("/login")
                .then()
                .statusCode(anyOf(equalTo(200), equalTo(400)));
    }

    /**
     * Демонстрирует работу с HTTP заголовками в REST Assured.
     * Включает примеры установки заголовков, работы с Content-Type,
     * Accept, Authorization, кастомных заголовков и заголовков из Map.
     */
    @DisplayName("Заголовки: Headers, Content-Type, Accept, Authorization")
    public void headersExamples() {
        // Установка Content-Type
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/users")
                .then()
                .statusCode(200);

        // Установка Accept заголовка
        given()
                .accept(ContentType.JSON)
                .when()
                .get("/users")
                .then()
                .statusCode(200);

        // Установка одного заголовка
        given()
                .header("User-Agent", "REST-Assured-Tests")
                .when()
                .get("/users")
                .then()
                .statusCode(200);

        // Множественные заголовки
        given()
                .header("X-Custom-Header", "CustomValue")
                .header("X-API-Version", "v1")
                .when()
                .get("/users")
                .then()
                .statusCode(200);

        // Заголовки из Map
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Custom-Header", "Value1");
        headers.put("X-Another-Header", "Value2");

        given()
                .headers(headers)
                .when()
                .get("/users")
                .then()
                .statusCode(200);

        // Использование Headers объекта
        Headers customHeaders = new Headers(
                new Header("X-Header-1", "Value1"),
                new Header("X-Header-2", "Value2")
        );

        given()
                .headers(customHeaders)
                .when()
                .get("/users")
                .then()
                .statusCode(200);

        // Authorization заголовок
        given()
                .header("Authorization", "Bearer token12345")
                .when()
                .get("/users")
                .then()
                .statusCode(200);
    }

    /**
     * Демонстрирует различные способы передачи тела запроса в REST Assured.
     * Включает примеры работы с JSON строками, Java объектами, Map, файлами,
     * XML и text блоками.
     */
    @DisplayName("Тело запроса: JSON, XML, String, Object, Map")
    public void requestBodyExamples() {
        // JSON как строка
        String jsonBody = """
                {
                    "name": "John Doe",
                    "job": "QA Engineer"
                }
                """;

        given()
                .body(jsonBody)
                .contentType(ContentType.JSON)
                .when()
                .post("/users")
                .then()
                .statusCode(201);

        // JSON из Map
        Map<String, Object> user = new HashMap<>();
        user.put("name", "Jane Doe");
        user.put("job", "Developer");

        given()
                .body(user)
                .contentType(ContentType.JSON)
                .when()
                .post("/users")
                .then()
                .statusCode(201);

        // JSON из Java объекта (POJO)
        User userObject = new User("Alice Smith", "Manager");

        given()
                .body(userObject)
                .contentType(ContentType.JSON)
                .when()
                .post("/users")
                .then()
                .statusCode(201);

        // XML как строка
        String xmlBody = """
                <?xml version="1.0" encoding="UTF-8"?>
                <user>
                    <name>John Doe</name>
                    <job>QA Engineer</job>
                </user>
                """;

        given()
                .body(xmlBody)
                .contentType(ContentType.XML)
                .when()
                .post("/users")
                .then()
                .statusCode(anyOf(equalTo(200), equalTo(201)));

        // Тело из файла
        File jsonFile = new File("src/test/resources/data/user.json");

        given()
                .body(jsonFile)
                .contentType(ContentType.JSON)
                .when()
                .post("/users")
                .then()
                .statusCode(201);

        // Plain text
        given()
                .body("Plain text body")
                .contentType(ContentType.TEXT)
                .when()
                .post("/users")
                .then()
                .statusCode(anyOf(equalTo(200), equalTo(201)));
    }

    /**
     * Демонстрирует различные способы валидации HTTP ответов в REST Assured.
     * Включает проверку статус-кодов, тела ответа, заголовков, времени ответа.
     */
    @DisplayName("Валидация ответа: Status Code, Body, Headers, Response Time")
    public void responseValidationExamples() {
        // Проверка статус-кода
        given()
                .when()
                .get("/users/2")
                .then()
                .statusCode(200);

        // Проверка нескольких возможных статус-кодов
        given()
                .when()
                .get("/users")
                .then()
                .statusCode(anyOf(equalTo(200), equalTo(201)));

        // Проверка Content-Type заголовка
        given()
                .when()
                .get("/users")
                .then()
                .contentType(ContentType.JSON);

        // Проверка конкретного заголовка
        given()
                .when()
                .get("/users")
                .then()
                .header("Content-Type", containsString("application/json"));

        // Проверка времени ответа
        given()
                .when()
                .get("/users")
                .then()
                .time(lessThan(5000L), TimeUnit.MILLISECONDS);

        // Проверка статус-линии
        given()
                .when()
                .get("/users")
                .then()
                .statusLine(containsString("200 OK"));
    }

    /**
     * Демонстрирует работу с JSON Path для извлечения и проверки JSON данных.
     * Включает примеры проверки полей, массивов, вложенных объектов,
     * использование Hamcrest matchers для сложных проверок.
     */
    @DisplayName("JSON Path: Извлечение и валидация JSON данных")
    public void jsonPathExamples() {
        // Проверка простого поля
        given()
                .when()
                .get("/users/2")
                .then()
                .body("data.id", equalTo(2))
                .body("data.email", containsString("@"))
                .body("data.first_name", notNullValue());

        // Проверка вложенных полей
        given()
                .when()
                .get("/users/2")
                .then()
                .body("data.id", equalTo(2))
                .body("data.first_name", equalTo("Janet"))
                .body("data.last_name", equalTo("Weaver"));

        // Проверка массива
        given()
                .when()
                .get("/users")
                .then()
                .body("data", hasSize(greaterThan(0)))
                .body("data[0].id", notNullValue())
                .body("data[0].email", notNullValue());

        // Проверка размера массива
        given()
                .when()
                .get("/users")
                .then()
                .body("data.size()", greaterThan(0));

        // Проверка всех элементов массива
        given()
                .when()
                .get("/users")
                .then()
                .body("data.id", everyItem(notNullValue()))
                .body("data.email", everyItem(containsString("@")));

        // Фильтрация массива
        given()
                .when()
                .get("/users")
                .then()
                .body("data.findAll { it.id > 1 }.size()", greaterThan(0));

        // Извлечение значений из массива
        given()
                .when()
                .get("/users")
                .then()
                .body("data.collect { it.email }", hasItems(containsString("@")));

        // Проверка наличия элемента с конкретным значением
        given()
                .when()
                .get("/users")
                .then()
                .body("data.find { it.id == 2 }.first_name", notNullValue());

        // Множественные проверки
        given()
                .when()
                .get("/users/2")
                .then()
                .body("data.id", equalTo(2),
                        "data.email", notNullValue(),
                        "data.first_name", is(String.class));
    }

    /**
     * Демонстрирует извлечение данных из HTTP ответа для дальнейшего использования.
     * Включает примеры извлечения значений, заголовков, cookies, всего Response объекта.
     */
    @DisplayName("Извлечение данных: Response, Extract, JsonPath, Headers")
    public void extractionExamples() {
        // Извлечение всего Response объекта
        Response response = given()
                .when()
                .get("/users/2")
                .then()
                .statusCode(200)
                .extract().response();

        System.out.println("Response body: " + response.asString());
        System.out.println("Status code: " + response.getStatusCode());

        // Извлечение конкретного значения из JSON
        int userId = given()
                .when()
                .get("/users/2")
                .then()
                .extract()
                .path("data.id");

        System.out.println("User ID: " + userId);

        // Извлечение в переменную с типом
        String email = given()
                .when()
                .get("/users/2")
                .then()
                .extract()
                .path("data.email");

        System.out.println("Email: " + email);

        // Извлечение списка
        List<Integer> userIds = given()
                .when()
                .get("/users")
                .then()
                .extract()
                .path("data.id");

        System.out.println("User IDs: " + userIds);

        // Извлечение в POJO
        User userData = given()
                .when()
                .get("/users/2")
                .then()
                .extract()
                .path("data");

        System.out.println("User: " + userData);

        // Извлечение заголовка
        String contentType = given()
                .when()
                .get("/users")
                .then()
                .extract()
                .header("Content-Type");

        System.out.println("Content-Type: " + contentType);

        // Извлечение всех заголовков
        Headers headers = given()
                .when()
                .get("/users")
                .then()
                .extract()
                .headers();

        System.out.println("Headers: " + headers);

        // Использование JsonPath
        io.restassured.path.json.JsonPath jsonPath = given()
                .when()
                .get("/users/2")
                .then()
                .extract()
                .jsonPath();

        String firstName = jsonPath.getString("data.first_name");
        System.out.println("First name: " + firstName);
    }

    /**
     * Демонстрирует различные методы аутентификации в REST Assured.
     * Включает примеры Basic Auth, OAuth2, Bearer Token, API Key аутентификации.
     */
    @DisplayName("Аутентификация: Basic, OAuth2, Bearer Token, API Key")
    public void authenticationExamples() {
        // Basic Authentication
        given()
                .auth().basic("username", "password")
                .when()
                .get("/users")
                .then()
                .statusCode(anyOf(equalTo(200), equalTo(401)));

        // Preemptive Basic Authentication (отправляет credentials сразу)
        given()
                .auth().preemptive().basic("username", "password")
                .when()
                .get("/users")
                .then()
                .statusCode(anyOf(equalTo(200), equalTo(401)));

        // Bearer Token Authentication
        given()
                .auth().oauth2("your_access_token_here")
                .when()
                .get("/users")
                .then()
                .statusCode(anyOf(equalTo(200), equalTo(401)));

        // Bearer Token через заголовок (альтернатива)
        given()
                .header("Authorization", "Bearer your_token_here")
                .when()
                .get("/users")
                .then()
                .statusCode(anyOf(equalTo(200), equalTo(401)));

        // API Key аутентификация (через header)
        given()
                .header("X-API-Key", "your_api_key_here")
                .when()
                .get("/users")
                .then()
                .statusCode(anyOf(equalTo(200), equalTo(401)));

        // API Key аутентификация (через query параметр)
        given()
                .queryParam("api_key", "your_api_key_here")
                .when()
                .get("/users")
                .then()
                .statusCode(anyOf(equalTo(200), equalTo(401)));

        // Digest Authentication
        given()
                .auth().digest("username", "password")
                .when()
                .get("/users")
                .then()
                .statusCode(anyOf(equalTo(200), equalTo(401)));

        // Form Authentication
        given()
                .auth().form("username", "password")
                .when()
                .get("/users")
                .then()
                .statusCode(anyOf(equalTo(200), equalTo(302)));
    }

    /**
     * Демонстрирует работу с HTTP cookies в REST Assured.
     * Включает примеры установки, извлечения и валидации cookies.
     */
    @DisplayName("Cookies: Установка, извлечение и проверка")
    public void cookiesExamples() {
        // Установка одного cookie
        given()
                .cookie("session_id", "abc123")
                .when()
                .get("/users")
                .then()
                .statusCode(200);

        // Установка нескольких cookies
        given()
                .cookie("session_id", "abc123")
                .cookie("user_token", "xyz789")
                .when()
                .get("/users")
                .then()
                .statusCode(200);

        // Установка Cookie объекта
        Cookie sessionCookie = new Cookie.Builder("session_id", "abc123")
                .setDomain("reqres.in")
                .setPath("/")
                .setSecured(true)
                .setHttpOnly(true)
                .build();

        given()
                .cookie(sessionCookie)
                .when()
                .get("/users")
                .then()
                .statusCode(200);

        // Установка нескольких Cookies объектов
        Cookies cookies = new Cookies(
                new Cookie.Builder("cookie1", "value1").build(),
                new Cookie.Builder("cookie2", "value2").build()
        );

        given()
                .cookies(cookies)
                .when()
                .get("/users")
                .then()
                .statusCode(200);

        // Извлечение cookie из ответа
        String cookieValue = given()
                .when()
                .get("/users")
                .then()
                .extract()
                .cookie("session_id");

        // Проверка наличия cookie
        given()
                .when()
                .get("/users")
                .then()
                .cookie("session_id", notNullValue());

        // Извлечение всех cookies
        Map<String, String> allCookies = given()
                .when()
                .get("/users")
                .then()
                .extract()
                .cookies();

        System.out.println("All cookies: " + allCookies);
    }

    /**
     * Демонстрирует загрузку файлов с использованием multipart/form-data.
     * Включает примеры загрузки одного и нескольких файлов, отправки дополнительных данных.
     */
    @DisplayName("Multipart: Загрузка файлов и данных")
    public void multipartExamples() {
        // Загрузка одного файла
        File file = new File("src/test/resources/files/test.txt");

        given()
                .multiPart("file", file)
                .when()
                .post("/upload")
                .then()
                .statusCode(anyOf(equalTo(200), equalTo(201), equalTo(404)));

        // Загрузка файла с указанием MIME типа
        given()
                .multiPart("file", file, "text/plain")
                .when()
                .post("/upload")
                .then()
                .statusCode(anyOf(equalTo(200), equalTo(201), equalTo(404)));

        // Загрузка нескольких файлов
        File file1 = new File("src/test/resources/files/test1.txt");
        File file2 = new File("src/test/resources/files/test2.txt");

        given()
                .multiPart("files", file1)
                .multiPart("files", file2)
                .when()
                .post("/upload")
                .then()
                .statusCode(anyOf(equalTo(200), equalTo(201), equalTo(404)));

        // Загрузка файла с дополнительными полями
        given()
                .multiPart("file", file)
                .multiPart("description", "Test file upload")
                .multiPart("category", "documents")
                .when()
                .post("/upload")
                .then()
                .statusCode(anyOf(equalTo(200), equalTo(201), equalTo(404)));

        // Загрузка байтового массива
        byte[] bytes = "File content".getBytes();

        given()
                .multiPart("file", "filename.txt", bytes)
                .when()
                .post("/upload")
                .then()
                .statusCode(anyOf(equalTo(200), equalTo(201), equalTo(404)));
    }

    /**
     * Демонстрирует использование спецификаций для переиспользования конфигурации.
     * Включает примеры создания и применения Request и Response спецификаций.
     */
    @DisplayName("Specifications: Request и Response спецификации")
    public void specificationsExamples() {
        // Request Specification
        RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://reqres.in")
                .setBasePath("/api")
                .setContentType(ContentType.JSON)
                .addHeader("X-Custom-Header", "CustomValue")
                .addQueryParam("page", 2)
                .build();

        // Использование Request Specification
        given()
                .spec(requestSpec)
                .when()
                .get("/users")
                .then()
                .statusCode(200);

        // Response Specification
        ResponseSpecification responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan(5000L), TimeUnit.MILLISECONDS)
                .build();

        // Использование Response Specification
        given()
                .when()
                .get("/users")
                .then()
                .spec(responseSpec);

        // Глобальная установка Request Specification
        io.restassured.RestAssured.requestSpecification = requestSpec;

        // Теперь все запросы будут использовать эту спецификацию
        when()
                .get("/users")
                .then()
                .statusCode(200);

        // Сброс глобальной спецификации
        io.restassured.RestAssured.requestSpecification = null;
    }

    /**
     * Демонстрирует использование фильтров для логирования и модификации запросов/ответов.
     * Включает примеры логирования, кастомных фильтров.
     */
    @DisplayName("Filters: Логирование запросов и ответов")
    public void filtersExamples() {
        // Логирование запроса и ответа
        given()
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .when()
                .get("/users")
                .then()
                .statusCode(200);

        // Логирование запроса и ответа
        given().log().all()
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .log().all();

        // Логирование только при ошибке
        given()
                .log().ifValidationFails()
                .when()
                .get("/users")
                .then()
                .statusCode(200);
    }

    @Data
    @AllArgsConstructor
    public class User {
        String name;
        String job;
    }
}