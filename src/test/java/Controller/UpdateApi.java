package Controller;

import Controller.interfaces.RequestImpl;
import Controller.interfaces.RequestInterface;
import io.restassured.response.Response;
import models.UpdateRequest;
import utils.Endpoints;

public class UpdateApi {

    private static final RequestInterface request = new RequestImpl();

    public static Response updatePassUser(UpdateRequest user, String token){
        return request.put(Endpoints.userPoint,user,token);

    }
}
