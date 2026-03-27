package tests;

import Controller.AuthApi;
import Controller.RegistrationApi;

import Controller.UpdateApi;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import models.AuthRequest;
import models.RegistrRequest;
import models.UpdateRequest;
import utils.ExtractUtils;
import utils.GenerationUser;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.emptyString;
import static org.junit.jupiter.api.Assertions.*;
@Epic("Registration/Avtorization API")
public class Tests extends BaseTestApi {


    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Регистрация и авторизация пользователя")
    @Description("Проверяем,что авторизация зарегистрированного пользователя пройдет")
    public void registrationAndAuthorizationUser(){
        RegistrRequest user = GenerationUser.generateUser();
        RegistrationApi registrationApi = new RegistrationApi();
         Response regResp =  registrationApi.registerUser(user);
        regResp.then()
                .statusCode(201)
                .body("info.status",equalTo("success"))
                .body("info.message",equalTo("User created"))
                .body("register_data.id", notNullValue())
                .body("register_data.login",equalTo(user.getLogin()))
                .body("register_data.pass", equalTo(user.getPass()));

        AuthRequest authUser = GenerationUser.authGenerateUser();
        Response authResp = AuthApi.authorizationUser(authUser);
        authResp.then()
                .statusCode(200)
                .body("token",notNullValue())
                .body("token", not(emptyString()));

    }

    @Test
    public void changePasswordUser(){
        RegistrRequest user = GenerationUser.generateUser();
        RegistrationApi registrationApi = new RegistrationApi();
        Response regResp = registrationApi.registerUser(user);

        AuthRequest authUser = GenerationUser.authGenerateUser();
        Response authResp = AuthApi.authorizationUser(authUser);

        String token = ExtractUtils.extractToken(authResp);

        UpdateRequest upUser = new UpdateRequest();
        upUser.setPassword(authUser.getPassword()+"123");
        Response upResp = UpdateApi.updatePassUser(upUser,token);
        upResp.then()
                .statusCode(200);
        assertEquals("success",upResp.jsonPath().getString("info.status"));
        assertEquals("User password successfully changed",upResp.jsonPath().getString("info.message"));


        AuthRequest newUser = new AuthRequest();
        newUser.setPassword(upUser.getPassword());
        newUser.setUsername(authUser.getUsername());
        Response newUserResp = AuthApi.authorizationUser(newUser);
        newUserResp.then();
        assertNotNull(newUserResp.jsonPath().getString("token"));
    }

    @Test
    public void registrationUserEmptyPassword(){
        RegistrRequest user =GenerationUser.generateUser();
        user.setPass(null);
        RegistrationApi registrationApi = new RegistrationApi();
        Response regResponse =registrationApi.registerUser(user);
        regResponse.then()
                .statusCode(400);
        assertEquals("fail", regResponse.jsonPath().getString("info.status"));
        assertEquals("Missing login or password",regResponse.jsonPath().getString("info.message"));

    }
    @Test
    public void registrationUserEmptyLogin(){
        RegistrRequest user =GenerationUser.generateUser();
        user.setLogin(null);
        RegistrationApi registrationApi = new RegistrationApi();
        Response regResp = registrationApi.registerUser(user);
        Response regResponse =registrationApi.registerUser(user);
        regResponse.then()
                .statusCode(400);
        assertEquals("Missing login or password",regResponse.jsonPath().getString("info.message"));
        assertEquals("fail",regResponse.jsonPath().getString("info.status"));

    }

}
