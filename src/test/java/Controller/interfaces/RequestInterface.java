package Controller.interfaces;

import io.restassured.response.Response;

import java.io.File;

public interface RequestInterface {


    Response post(String endpoint, Object body, String token);
    Response put(String endpoint, Object body, String token);
    Response get(String endpoint, String token);
    Response postUpload(String endpoint, File fileUpload);



}
