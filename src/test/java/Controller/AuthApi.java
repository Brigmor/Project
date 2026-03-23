package Controller;

import Data.Endpoints;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import pojo.AuthorizationRequest;

import static Data.Endpoints.*;
import static io.restassured.RestAssured.given;

public class AuthApi {

    private static final RequestInterface request = new RequestImpl();

    //Авторизация юзера
    public static Response authorizationUser(AuthorizationRequest auth) {
        return request.post(Endpoints.loginPoint, auth, null);
    }

}
