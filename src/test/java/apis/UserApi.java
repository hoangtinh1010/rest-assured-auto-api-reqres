package apis;

import base.BaseTest;
import io.restassured.response.Response;
import net.minidev.json.JSONObject;

import static io.restassured.RestAssured.given;

public class UserApi extends BaseTest {

    public static Response createUser(JSONObject userPayload) {

        return given()
                .when()
                .spec(BaseTest.requestSpec)
                .body(userPayload)
                .post("/users")
                .then()
                .log().all()
                .extract()
                .response();
    }

    public static Response updateUser(int id, JSONObject userPayload) {
        return given()
                .spec(BaseTest.requestSpec)
                .when()
                .body(userPayload)
                .put("/users/" + id)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public static Response getUser(int id) {
        return given()
                .spec(BaseTest.requestSpec)
                .when()
                .get("/users/" + id)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public static Response deleteUser(int id) {
        return given()
                .spec(BaseTest.requestSpec)
                .when()
                .delete("/users/" + id)
                .then()
                .log().all()
                .extract()
                .response();
    }
}
