package Controller.interfaces;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import specs.RequestSpec;

import java.io.File;

import static io.restassured.RestAssured.*;



public class RequestImpl implements RequestInterface {

    @Override
    public Response post(String endpoint, Object body, String token) {

        RequestSpecification spec = given()
                .spec(RequestSpec.requestJsonSpec())
                .body(body);

        if (token != null && !token.isEmpty()) {
            spec.header("Authorization","Bearer "+token);
        }

        return spec
                .when()
                .post(endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }

    @Override
    public Response put(String endpoint,Object body,String token) {
          return given()
                .spec(RequestSpec.requestJsonSpec())
                .body(body)
                .header("Authorization","Bearer "+token)
                .when()
                .put(endpoint)
                .then()
                .log().all()
                .extract().response();
    }
    @Override
    public Response get(String endpoint, String token){

        RequestSpecification spec = given()
                .spec(RequestSpec.requestJsonSpec());

        if (token != null && !token.isBlank()) {
            spec.header("Authorization","Bearer "+ token);
        }

        return spec
                .when()
                .get(endpoint)
                .then()
                .log().all()
                .extract()
                .response();
    }

    @Override
    public Response postUpload(String endpoint, File fileUpload){
        return given()
                .multiPart("file",fileUpload)
                .when()
                .post(endpoint)
                .then()
                .log().all()
                .extract().response();
    }

}
