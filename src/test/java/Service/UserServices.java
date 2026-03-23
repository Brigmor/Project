package Service;

import Controller.RegistrationApi;
import Data.GenerationUser;
import accertions.ApiAssertion;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojo.RegistrationRequest;

public class UserServices {
    @Step("Регистрация нового пользователя")
    public static RegistrationRequest registerUser(){
        RegistrationRequest user = GenerationUser.generateUser();
        Response regResp = RegistrationApi.registerUser(user);
        ApiAssertion.assertRegistration(regResp,user);
        return user;
    }
}
