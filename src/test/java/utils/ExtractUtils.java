package utils;

import io.restassured.response.Response;

public class ExtractUtils {
    public static String extractToken(Response response){
        return response
                .then()
                .extract()
                .path("token");

    }
    public static int extractIdUser(Response response){
        return response.jsonPath().getInt("register_data.id");
    }
    public static String extractPsswordUser(Response response){
        return response.jsonPath().getString("register_data.pass");
    }


}
