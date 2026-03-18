package api;

import io.restassured.response.Response;
import pojo.RegistrationRequest;
import specs.RequestSpec;

import static Data.Endpoints.*;
import static io.restassured.RestAssured.*;

public class RegistrationApi {
    public static Response registrationUser(RegistrationRequest body){
        return given()
                .spec(RequestSpec.requestJsonSpec())
                .body(body)
                .when()
                .post(singupPoint);

    }
}
