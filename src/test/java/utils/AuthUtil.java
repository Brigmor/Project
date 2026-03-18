package utils;

import io.restassured.response.Response;

public class AuthUtil {
    public static String extractToken(Response response){
        return response
                .then()
                .extract()
                .path("token");

    }

}
