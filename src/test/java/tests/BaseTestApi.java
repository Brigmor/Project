package tests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import specs.RequestSpec;

import static io.restassured.RestAssured.given;

public class BaseTestApi {

    protected static final String BASE_URL="http://85.192.34.140:8080/";
    @BeforeEach
    public void setUp(){
        RestAssured.baseURI =BASE_URL;
    }


}
