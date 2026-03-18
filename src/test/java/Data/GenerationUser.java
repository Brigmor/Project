package Data;

import com.github.javafaker.Faker;
import pojo.AuthorizationRequest;
import pojo.RegistrationRequest;

public class GenerationUser {

    private static  RegistrationRequest lastUser;
    private static final Faker faker = new Faker();


    public static RegistrationRequest generateUser(){
        String login = "user_" + faker.name().lastName();
        String pass = faker.internet().password(8, 12);

        RegistrationRequest user = new RegistrationRequest();
        user.setLogin(login);
        user.setPass(pass);

        lastUser=user;

        return user;
    }

    public static AuthorizationRequest authGenerateUser(){
        AuthorizationRequest authUser = new AuthorizationRequest();
        authUser.setUsername(lastUser.getLogin());
        authUser.setPassword(lastUser.getPass());
        return authUser;
    }


}
