package data;

import com.github.javafaker.Faker;
import net.minidev.json.JSONObject;

public class UserDataFactory {
    private static final Faker faker = new Faker();

    public static JSONObject createValidUser() {
        JSONObject user = new JSONObject();

                user.put("name", faker.name().firstName());
                user.put("job", faker.job().title());
                return user;
    }

    public static JSONObject createUserWithName(String name) {
        JSONObject user = new JSONObject();
        user.put("name", name);
        user.put("job", faker.job().title());
        return user;
    }

    public static JSONObject createUserWithJob(String job) {
        JSONObject user = new JSONObject();
        user.put("name", faker.name().firstName());
        user.put("job", job);
        return user;

    }

    public static JSONObject updateUserWithName() {
        JSONObject user = new JSONObject();
        user.put("name", faker.name().firstName());
        return user;


    }
}