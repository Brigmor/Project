package utils;

import com.github.javafaker.Faker;
import models.AuthRequest;
import models.RegistrRequest;

public class GenerationUser {

    private static RegistrRequest lastUser;
    private static final Faker faker = new Faker();


    public static RegistrRequest generateUser(){
        String login = "user_" + faker.name().lastName();
        String pass = faker.internet().password(8, 12);

        RegistrRequest user = new RegistrRequest();
        user.setLogin(login);
        user.setPass(pass);

        lastUser=user;

        return user;
    }

    public static AuthRequest authGenerateUser(){
        AuthRequest authUser = new AuthRequest();
        authUser.setUsername(lastUser.getLogin());
        authUser.setPassword(lastUser.getPass());
        return authUser;
    }


}
