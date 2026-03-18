package api;

import io.restassured.response.Response;
import pojo.AuthorizationRequest;
import specs.RequestSpec;

import static Data.Endpoints.*;
import static io.restassured.RestAssured.given;

public class AuthApi {


    public static Response AuthorizationApi(AuthorizationRequest body){
        return given()
                .spec(RequestSpec.requestJsonSpec())
                .body(body)
                .log().all()
                .when()
                .post(loginPoint)
                .then()
                .extract().path("token");
    }

}
