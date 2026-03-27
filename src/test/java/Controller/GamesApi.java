package Controller;

import Controller.interfaces.RequestImpl;
import Controller.interfaces.RequestInterface;
import io.restassured.response.Response;
import utils.Endpoints;

public class GamesApi {

    private static final RequestInterface request = new RequestImpl();

    public static Response GetUserGameList(String token){
        return request.get(Endpoints.addGetGame,token);

    }
    public static Response postAddGameUser(Object body,String token){
        return request.post(Endpoints.addGetGame,body,token);
    }

}
