package Controller;

import Controller.interfaces.RequestImpl;
import Controller.interfaces.RequestInterface;
import io.qameta.allure.Step;
import utils.Endpoints;
import io.restassured.response.Response;
import models.RegistrRequest;

public class RegistrationApi  {

        private static final RequestInterface request = new RequestImpl();

    @Step("Регистрация пользователя")
    public  Response registerUser(RegistrRequest user){
        return request.post(Endpoints.singupPoint, user, null);
    }



}
