package tests;

import Data.GenerationUser;
import api.AuthApi;
import api.RegistrationApi;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import pojo.AuthorizationRequest;
import pojo.RegistrationRequest;
import utils.AuthUtil;

public class RegistrationTests extends BaseTest {

    @Test
    public void test(){
        RegistrationRequest newUser = GenerationUser.generateUser();
        Response response = RegistrationApi.registrationUser(newUser);
        response.then().statusCode(201).log().all();

    }
    @Test
    public void registrationValidData(){
        RegistrationRequest regUser =GenerationUser.generateUser();
        Response regResponse =RegistrationApi.registrationUser(regUser);

        AuthorizationRequest authUser = new AuthorizationRequest();
        authUser.setUsername(regUser.getLogin());
        authUser.setPassword(regUser.getPass());

        Response authRespons = AuthApi.AuthorizationApi(authUser);
        AuthUtil.extractToken(authRespons);






    }


}
