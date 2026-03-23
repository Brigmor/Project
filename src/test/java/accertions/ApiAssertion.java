package accertions;

import io.restassured.response.Response;
import pojo.RegistrationRequest;

import static org.hamcrest.Matchers.*;

public class ApiAssertion {

    public static void assertRegistration(Response response,RegistrationRequest regUser) {
        response.then()
                .statusCode(201)
                .body("info.status",equalTo("success"))
                .body("info.message",equalTo("User created"))
                .body("register_data.id", notNullValue())
                .body("register_data.login",equalTo(regUser.getLogin()))
                .body("register_data.pass", equalTo(regUser.getPass()));

    }

    public static void assertAuthorization(Response response) {
        response.then()
                .statusCode(200)
                .body("token",notNullValue())
                .body("token", not(emptyString()));

    }

    public static void assertDelete(Response response) {
        response.then()
                .statusCode(200);

    }

    public static void assertUpdatePassUser(Response response){
        response.then()
                .statusCode(200)
                .body("info.status", equalTo("success"))
                .body("info.message",equalTo("User password successfully changed"));
    }

}
