package utils;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.charset.StandardCharsets;

import static io.qameta.allure.Allure.parameter;
import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.*;
import static io.qameta.allure.model.Status.FAILED;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Объединённый класс для демонстрации использования JUnit 5 и Allure в тестах.
 */
@Epic("Демонстрация возможностей Allure с JUnit 5")
public class AllureJUnit {

    private static final String GLOBAL_PARAMETER = "глобальное значение";

    /**
     * Метод, выполняемый перед всеми тестами.
     */
    @BeforeAll
    public static void beforeAll() {
        step("Метод @BeforeAll: выполняется перед всеми тестами");
    }

    /**
     * Метод, выполняемый перед каждым тестом.
     */
    @BeforeEach
    public void beforeEach() {
        step("Метод @BeforeEach: выполняется перед каждым тестом");
    }

    /**
     * Метод, выполняемый после каждого теста.
     */
    @AfterEach
    public void afterEach() {
        step("Метод @AfterEach: выполняется после каждого теста");
    }

    /**
     * Метод, выполняемый после всех тестов.
     */
    @AfterAll
    public static void afterAll() {
        step("Метод @AfterAll: выполняется после всех тестов");
    }

    /**
     * Простой тест с использованием шагов Allure.
     */
    @DisplayName("Простой тест с шагами Allure")
    @Description("Демонстрация использования простых шагов в Allure")
    @Severity(NORMAL)
    @Feature("Шаги")
    private void allureSimpleTest() {
        step("Шаг 1: Простой шаг");
        step("Шаг 2: Простой шаг со статусом FAILED", FAILED);
        step("Шаг 3: Простой шаг с использованием лямбда-выражения", () -> {
            step("Вложенный шаг внутри лямбда-шаг");
        });
        simpleTestMethod("пример параметра");
    }

    /**
     * Простой метод с аннотацией @Step.
     *
     * @param param параметр метода
     */
    @Step("Простой тестовый метод с параметром [{param}]")
    private void simpleTestMethod(String param) {
        step("Внутри метода simpleTestMethod с параметром: " + param);
        // Дополнительная логика метода при необходимости
    }

    /**
     * Первый тест с использованием фикстур и шагов Allure.
     */
    @DisplayName("Первый тест с фикстурами Allure")
    @Severity(MINOR)
    @Feature("Фикстуры")
    private void firstAllureFixtureTest() {
        step("Шаг внутри firstAllureFixtureTest");
        System.out.println("Это первый тест");
        assertTrue(true, "Проверка должна быть успешной");
    }

    /**
     * Второй тест с использованием фикстур и шагов Allure.
     */
    @DisplayName("Второй тест с фикстурами Allure")
    @Severity(MINOR)
    @Feature("Фикстуры")
    private void secondAllureFixtureTest() {
        step("Шаг внутри secondAllureFixtureTest");
        System.out.println("Это второй тест");
        assertTrue(true, "Проверка должна быть успешной");
    }

    /**
     * Параметризованный тест с пользовательскими метками и шагами.
     *
     * @param input входные данные для теста
     */
    @ParameterizedTest(name = "Проверка функциональности с параметром: {0}")
    @ValueSource(strings = {"Тест1", "Тест2"})
    @Feature("Параметризованные тесты")
    @Story("Использование пользовательских меток в параметризованных тестах")
    @Severity(MINOR)
    private void parameterizedTestWithCustomLabels(String input) {
        Allure.label("customLabel", "CustomValue");
        step("Выполнение шага с входными данными: " + input);
    }

    /**
     * Параметризованный тест с использованием Allure.
     *
     * @param name имя для тестирования
     */
    @ParameterizedTest(name = "Параметризованный тест с параметром: {0}")
    @ValueSource(strings = {"John", "Mike"})
    @DisplayName("Параметризованный тест с Allure")
    @Description("Демонстрация параметризованного теста с использованием Allure")
    @Severity(CRITICAL)
    @Feature("Параметризованные тесты")
    private void allureParameterizedTest(String name) {
        parameter("Name", name);
        step("Тестирование с именем: " + name);
    }

    /**
     * Тест с параметрами без реальной параметризации.
     */
    @DisplayName("Фиктивный параметризованный тест с Allure")
    @Description("Демонстрация фиктивного параметризованного теста")
    @Severity(TRIVIAL)
    @Feature("Параметризованные тесты")
    private void allureFakeParameterizedTest() {
        parameter("fakeParam", "fakeValue");
        step("Шаг внутри фиктивного параметризованного теста");
    }

    /**
     * Тест с использованием аннотированных шагов.
     */
    @Description("Тест с аннотированными шагами")
    @DisplayName("Тест с аннотированными шагами")
    @Severity(CRITICAL)
    @Feature("Шаги")
    private void annotatedStepTest() {
        annotatedStep("локальное значение");
    }

    /**
     * Родительский аннотированный шаг с параметром.
     *
     * @param parameter локальный параметр
     */
    @Step("Родительский аннотированный шаг с параметром [{parameter}]")
    private void annotatedStep(final String parameter) {
        nestedAnnotatedStep();
    }

    /**
     * Вложенный аннотированный шаг с глобальным параметром.
     */
    @Step("Вложенный аннотированный шаг с глобальным параметром [{this.GLOBAL_PARAMETER}]")
    private void nestedAnnotatedStep() {
        step("Выполнение вложенного аннотированного шага");
    }

    /**
     * Тест с использованием шагов-лямбд.
     */
    @Description("Тест с использованием шагов-лямбд")
    @DisplayName("Тест с шагами-лямбдами")
    @Severity(NORMAL)
    @Feature("Шаги")
    private void lambdaStepTest() {
        final String localParameter = "значение параметра";
        step(String.format("Родительский лямбда-шаг с параметром [%s]", localParameter), () -> {
            step("Второй уровень шага", () -> {
                step(String.format("Вложенный лямбда-шаг с глобальным параметром [%s]", GLOBAL_PARAMETER));
            });
        });
    }

    /**
     * Тест с использованием различных меток.
     */
    @Description("Тест с использованием статических меток")
    @DisplayName("Тест с метками")
    @Severity(BLOCKER)
    @Feature("Метки")
    @Story("Использование различных меток")
    @Owner("svedentsov")
    @Link(name = "GitHub", url = "https://github.com")
    private void labelsTest() {
        step("Тест с использованием статических меток");
    }

    /**
     * Тест с динамическими метками.
     */
    @Description("Тест с динамическими метками")
    @DisplayName("Тест с динамическими метками")
    @Severity(MINOR)
    private void dynamicLabelsTest() {
        Allure.epic("Динамический Epic");
        Allure.feature("Динамическая Feature");
        Allure.story("Динамическая Story");
        Allure.label("owner", "Динамический владелец");
        Allure.label("severity", MINOR.value());
        Allure.link("GitHub", "https://github.com");
        step("Тест с использованием динамических меток");
    }

    /**
     * Тест с использованием вложений.
     */
    @Description("Тест с использованием вложений")
    @DisplayName("Тест с вложениями")
    @Severity(NORMAL)
    @Feature("Вложения")
    private void attachmentsTest() {
        textAttachment("Аннотированное", "Содержимое аннотированного вложения");
        Allure.attachment("Динамическое вложение", "Содержимое динамического вложения");
    }

    /**
     * Метод для создания текстового вложения.
     *
     * @param type    тип вложения
     * @param content содержимое вложения
     * @return содержимое вложения в виде массива байт
     */
    @Attachment(value = "Аннотированное вложение [{type}]", type = "text/plain", fileExtension = ".txt")
    public byte[] textAttachment(String type, String content) {
        return content.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Тест с использованием описаний.
     */
    @Description("Статическое описание")
    @DisplayName("Тест с описаниями")
    @Severity(TRIVIAL)
    private void descriptionTest() {
        Allure.description("Динамическое описание");
        step("Тест с использованием описаний");
    }

    /**
     * Тест с параметрами.
     */
    @Description("Тест с параметрами")
    @DisplayName("Тест с параметрами")
    @Severity(NORMAL)
    private void parametersTest() {
        Allure.parameter("Параметр1", "Значение1");
        Allure.parameter("Параметр2", "Значение2");
        step("Тест с параметрами в отчёте");
    }

    /**
     * Тест с использованием ссылок.
     */
    @Description("Тест с использованием ссылок")
    @DisplayName("Тест с ссылками")
    @Severity(TRIVIAL)
    @Link(name = "Документация Allure", url = "https://docs.qameta.io/allure/")
    private void linkTest() {
        Allure.link("Динамическая ссылка", "https://qameta.io");
        step("Выполнение основного шага теста с ссылкой");
    }

    /**
     * Тест с использованием динамического имени.
     */
    @Description("Тест с динамическим именем")
    @DisplayName("Тест с динамическим именем")
    @Severity(MINOR)
    private void dynamicNameTest() {
        final String dynamicPart = "динамический параметр";
        final String dynamicName = String.format("Тест с параметром [%s]", dynamicPart);
        Allure.getLifecycle().updateTestCase(result -> result.setName(dynamicName));
        step("Выполнение теста с динамическим именем");
    }

    /**
     * Тест с использованием пользовательской аннотации для метки.
     */
    @CustomLabel("Значение пользовательской метки")
    @Description("Тест с пользовательской аннотацией")
    @DisplayName("Тест с пользовательской аннотацией")
    @Severity(NORMAL)
    private void testWithCustomAnnotation() {
        step("Тест с пользовательской аннотацией");
    }

    /**
     * Пользовательская аннотация для добавления меток в Allure.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.TYPE})
    @LabelAnnotation(name = "customLabel")
    public @interface CustomLabel {
        String value();
    }
}
