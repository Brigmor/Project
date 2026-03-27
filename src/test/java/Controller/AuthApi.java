package Controller;

import Controller.interfaces.RequestImpl;
import Controller.interfaces.RequestInterface;
import io.qameta.allure.Step;
import utils.Endpoints;
import io.restassured.response.Response;
import models.AuthRequest;

import static io.restassured.RestAssured.given;

public class AuthApi {

    private static final RequestInterface request = new RequestImpl();

    @Step("Авторизация пользователя")
    public static Response authorizationUser(AuthRequest auth) {
        return request.post(Endpoints.loginPoint, auth, null);
    }

}
