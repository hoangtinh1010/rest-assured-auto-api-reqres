package data;

import com.github.javafaker.Faker;
import models.Login;

public class LoginDataFactory {
    private static final Faker faker = new Faker();

    public static Login validLoginPayload() {
        return Login.builder()
                .email("eve.holt@reqres.in")
                .password("cityslicka")
                .build();
    }

    public static Login invalidLoginPayload() {
        return Login.builder()
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .build();
    }
}
