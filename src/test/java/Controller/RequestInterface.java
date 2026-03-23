package Controller;

import io.restassured.response.Response;

public interface RequestInterface {


    Response post(String endpoint, Object body, String token);



}
