package tests;

import Data.GenerationUser;
import api.AuthApi;
import api.RegistrationApi;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import pojo.AuthorizationRequest;
import pojo.RegistrationRequest;
import utils.AuthUtil;

public class AuthorizationTests {


    @Test
    public void testRegisterAndAuth() {

        RegistrationRequest newUser = GenerationUser.generateUser();
        Response responseReg = RegistrationApi.registrationUser(newUser);
        responseReg.then()
                .statusCode(201)
                .log().all();
        AuthorizationRequest authUser = new AuthorizationRequest();
        authUser.setUsername(newUser.getLogin());
        authUser.setPassword(newUser.getPass());
        Response response = AuthApi.AuthorizationApi(authUser);
        AuthUtil.extractToken(response);

    }
}
