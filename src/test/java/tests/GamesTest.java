package tests;

import Controller.AuthApi;
import Controller.GamesApi;
import Controller.RegistrationApi;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import models.AuthRequest;
import models.GameRequest;
import models.RegistrRequest;
import utils.ExtractUtils;
import utils.GenerationUser;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GamesTest extends BaseTestApi {

    @Test
    public void getGameUser(){
        RegistrRequest regUser = GenerationUser.generateUser();
        RegistrationApi registrationApi =new RegistrationApi();
        Response registrResponse = registrationApi.registerUser(regUser);
        registrResponse.then()
                .statusCode(201);
        AuthRequest authUser = GenerationUser.authGenerateUser();
        Response authResponse = AuthApi.authorizationUser(authUser);
        authResponse.then()
                .statusCode(200);
        String token = ExtractUtils.extractToken(authResponse);
        Response gameUserResponse = GamesApi.GetUserGameList(token);

    }
    @Test
    public void addGameUser(){
        RegistrRequest regUser = GenerationUser.generateUser();
        RegistrationApi registrationApi =new RegistrationApi();
        Response registrResponse = registrationApi.registerUser(regUser);
        registrResponse.then()
                .statusCode(201);
        AuthRequest authUser = GenerationUser.authGenerateUser();
        Response authResponse = AuthApi.authorizationUser(authUser);
        authResponse.then()
                .statusCode(200);
        String token = ExtractUtils.extractToken(authResponse);
        GameRequest gameTitle = new GameRequest();
        gameTitle.setTitle("igra");
        gameTitle.setCompany("asdas");
        gameTitle.setDescription("asdasd");
        gameTitle.setGenre("asda");
        Response addGameResponse = GamesApi.postAddGameUser(gameTitle, token);
        assertEquals(200,addGameResponse.statusCode());

    }
}
