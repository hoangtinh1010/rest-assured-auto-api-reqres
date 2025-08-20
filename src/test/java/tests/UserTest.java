package tests;


import base.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

import static io.restassured.RestAssured.given;

public class UserTest extends BaseTest {

    //save created user ID for further tests
    private static int createdUserId;

    // Test method to verify user creation
    @Test(priority = 1)
    public void testCreateUser() {
        String payload = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";
        Response response = given()
                .spec(requestSpec)
                .body(payload)
                .when()
                .post("/users")
                .then().statusCode(201)
                .extract().response();
         createdUserId = response.jsonPath().getInt("id");
         assertTrue(createdUserId > 0, "Created user ID should be greater than 0");
         System.out.println("Response: " + response.asPrettyString());
         assertNotNull(createdUserId, "Created user ID should not be null");
    }

    @Test (priority = 2,dependsOnMethods = "testCreateUser")
    public void testGetUser() {
        Response response = given()
                .spec(requestSpec)
                .when()
                .get("/users/" + createdUserId)
                .then().statusCode(200)
                .extract().response();

        String name = response.jsonPath().getString("data.first_name");
        String job = response.jsonPath().getString("data.job");
        int id = response.jsonPath().getInt("data.id");
        assertEquals(name, "morpheus", "Name should be morpheus");
        assertEquals(job, "leader", "Job should be leader");
        assertTrue(id == createdUserId, "id should match created user ID");

        System.out.println("Resonse: " + response.asPrettyString());
    }

    @Test (priority = 3, dependsOnMethods = "testCreateUser")
//     Test method to verify user update
    public void testUpdateUser() {
        String payload = "{ \"name\": \"morpheus\", \"job\": \"zion resident\" }";
        Response response = given()
                .spec(requestSpec)
                .body(payload)
                .when()
                .put("/users/" + createdUserId)
                .then().statusCode(200)
                .extract().response();

        String name = response.jsonPath().getString("name");
        String job = response.jsonPath().getString("job");
        assertEquals(name, "morpheus", "Name should be morpheus");
        assertEquals(job, "zion resident", "Job should be zion resident");

        System.out.println("Resonse: " + response.asPrettyString());
    }

    @Test (priority = 4, dependsOnMethods = "testCreateUser")
    public void testDeleteUser() {
        given()
                .spec(requestSpec)
                .when()
                .delete("/users/" + createdUserId)
                .then().statusCode(204);

        // Verify that the user is deleted
        given()
                .spec(requestSpec)
                .when()
                .get("/users/" + createdUserId)
                .then().statusCode(404);

        System.out.println("User with ID " + createdUserId + " has been deleted successfully.");
    }



}