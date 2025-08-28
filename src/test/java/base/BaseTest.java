package base;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public abstract class BaseTest {

    @BeforeClass
    public static void globalSetup() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        RestAssured.filters(new AllureRestAssured()); // один раз на все тесты
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @AfterClass
    public static void globalTearDown() {
        RestAssured.reset(); // на всякий, чтобы фильтры не копились
    }
}
