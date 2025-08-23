package tests;

import base.BaseTest;
import data.LoginDataFactory;
import io.restassured.response.Response;
import models.Login;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertNotNull;

public class LoginTest extends BaseTest {
    private Login loginData;
    @BeforeClass
    public void loadTestData() {
        // doc json content
        Login success = new Login("asd ","asd");
        Login failed = new Login("asd ","asd");

    }

    // Test method to verify successful login
    @Test (priority = 1)
    public void testSuccessfulLogin() {
         Login payload = LoginDataFactory.validLoginPayload();
        Response response = given()
                .spec(requestSpec)
                .body(payload)
                .when()
                .post("/login")
                .then().statusCode(200)
                .extract().response();
        String token = response.jsonPath().getString("token");
        assertNotNull(token, "Token should not be null");

        System.out.println("Login successful, token: " + token);
    }

    @Test (priority = 2)
    // Test method to   verify unsuccessful login
    public void testUnsuccessfulLogin() {
        Login payload = LoginDataFactory.invalidLoginPayload();
        given()
                .spec(requestSpec)
                .body(payload)
                .when()
                .post("/login")
                .then().statusCode(400)
                .log().body();

    }


}
