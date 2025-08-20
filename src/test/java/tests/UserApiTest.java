package tests;

import base.BaseTest;
import data.UserDataFactory;
import io.restassured.response.Response;
import net.minidev.json.JSONObject;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static apis.UserApi.*;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class UserApiTest extends BaseTest {
    private static int createdUserId;
    private static String createdUserName;
    private static String createdUserJob;


    @Test(priority = 1)
    public void testCreateUser_valid() {
        JSONObject userPayload = UserDataFactory.createValidUser();
        Response response = createUser(userPayload);

        // Verify status code
        assertEquals(response.statusCode(), 201, "Status code should be 201");

        // Verify response schema
        response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/create_user_schema.json"));


        // Extract user details from response
        createdUserId = response.jsonPath().getInt("id");
        createdUserName = response.jsonPath().getString("name");
        createdUserJob = response.jsonPath().getString("job");

        // Verify user ID is greater than 0
        assertTrue(createdUserId > 0, "User ID should be greater than 0");


    }

    @Test(priority = 2)
    public void testCreateUser_with_name() {
        JSONObject userPayload = UserDataFactory.createUserWithName(null);

        Response response = createUser(userPayload);
        assertEquals(response.statusCode(), 201, "Status code should be 201");
        createdUserId = response.jsonPath().getInt("id");
        createdUserName = response.jsonPath().getString("name");
        createdUserJob = response.jsonPath().getString("job");
        assertTrue(createdUserId > 0, "User ID should be greater than 0");
        assertEquals(createdUserName, null, "User name should match the payload");
        assertEquals(createdUserJob, userPayload.get("job"), "User job should match the payload");
    }

    @Test(priority = 3)
    public void testCreateUser_with_job() {
        JSONObject userPayload = UserDataFactory.createUserWithJob(null);

        Response response = createUser(userPayload);
        assertEquals(response.statusCode(), 201, "Status code should be 201");
        createdUserId = response.jsonPath().getInt("id");
    }

    @Test(priority = 4, dependsOnMethods = "testCreateUser_valid")
    public void testGetUser() {

        Response response = getUser(createdUserId);

        String name = response.jsonPath().getString("data.name");
        String job = response.jsonPath().getString("data.job");

        // Verify status code
        assertEquals(response.statusCode(), 200, "Status code should be 200");
        int id = response.jsonPath().getInt("data.id");

        // Verify response schema
        response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/getUserResponseSchema.json"));

        // Verify response body
        assertEquals(name, createdUserName, "Name should match created user name");
        assertEquals(job, createdUserJob, "Job should match created user job");
        assertTrue(id == createdUserId, "ID should match created user ID");
    }

    @Test(priority = 5, dependsOnMethods = "testCreateUser_valid")
    public void testUpdateUser() {
        JSONObject userPayload = UserDataFactory.createUserWithName("Updated Name");

        Response response = updateUser(createdUserId, userPayload);

        assertEquals(response.statusCode(), 200, "Status code should be 200");

        // Verify response schema
        response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/update_user_schema.json"));

        String name = response.jsonPath().getString("name");
        String job = response.jsonPath().getString("job");



       //Verify response body
        assertEquals(name, "Updated Name", "Name should be updated");
        assertEquals(job, createdUserJob, "Job should remain unchanged");

        System.out.println("Updated user: " + response.asString());

    }

    @Test(priority = 6, dependsOnMethods = "testCreateUser_valid")
    public void testDeleteUser() {
        given()
                .spec(requestSpec)
                .when()
                .delete("/users/" + createdUserId)
                .then()
                .statusCode(204);

        // Verify user is deleted
        given()
                .spec(requestSpec)
                .when()
                .get("/users/" + createdUserId)
                .then()
                .statusCode(404);
    }

}

