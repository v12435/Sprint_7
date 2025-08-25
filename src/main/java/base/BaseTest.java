package base;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.Before;

public abstract class BaseTest {
    protected static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    @Before
    public void init() {
        RestAssured.baseURI = BASE_URL;
        // Логгер Allure для всех запросов
        RestAssured.filters(new AllureRestAssured());
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}
