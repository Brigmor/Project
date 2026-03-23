package Controller;

import Data.Endpoints;
import io.restassured.response.Response;
import pojo.AuthorizationRequest;
import pojo.RegistrationRequest;

public class RegistrationApi  {

        private static final RequestInterface request = new RequestImpl();
    //Регистрация юзера
    public static Response registerUser(RegistrationRequest user){
        return request.post(Endpoints.singupPoint, user, null);
    }




}
