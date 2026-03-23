package Controller;

import Data.Endpoints;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import specs.RequestSpec;

import static io.restassured.RestAssured.*;


public class RequestImpl implements RequestInterface {

    @Override
    public Response post(String endpoint, Object body, String token) {

        RequestSpecification spec = given()
                .spec(RequestSpec.requestJsonSpec())
                .body(body);

        if (token != null && !token.isEmpty()) {
            spec.header("Authorization", token);
        }

        return spec
                .when()
                .post(endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }
}
