package tests;

import Service.UserServices;
import org.junit.jupiter.api.Test;

public class Tests extends BaseTestApi {

    @Test
    public void test(){
        UserServices.registerUser();
    }
}
