package tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import specs.RequestSpec;

public class BaseTest {

    @BeforeAll
    public static void setUp(){

        RestAssured.requestSpecification = RequestSpec.requestJsonSpec();
    }
}
