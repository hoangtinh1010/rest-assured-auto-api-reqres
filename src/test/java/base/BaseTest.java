package base;

import config.ConfigManager;
import io.restassured.RestAssured;


import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;

public class BaseTest {
    //static variable to hold the request specification
    // static: truy cập trực tiếp từ lớp
    protected static RequestSpecification requestSpec;

    @BeforeSuite
    public void setUpEnvironment() {
        String env = System.getProperty("env", "dev"); // default dev
//        String baseUrl = RestAssured.baseURI = ConfigManager.get("baseUrl");

        // Tạo file environment.properties cho Allure
        Properties props = new Properties();
        props.setProperty("Environment", env);
//        props.setProperty("Base URL", baseUrl);
        props.setProperty("Tester","Hoàng Tỉnh");

        try {
            File file = new File("target/allure-results/environment.properties");
            file.getParentFile().mkdirs();
            FileOutputStream fos = new FileOutputStream(file);
            props.store(fos, "Allure Environment Properties");
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to create environment.properties for Allure", e);
        }
    }
    // Hàm này sẽ chạy trước khi tất cả các test case được thực thi
        @BeforeClass
        public void setUp () {
            RestAssured.baseURI = ConfigManager.get("baseUrl");
            RestAssured.basePath = ConfigManager.get("basePath");

            requestSpec = RestAssured.given()
                    .headers("Content-Type", "application/json",
                            "x-api-key", ConfigManager.get("apiKey"))
                    .filter(new RequestLoggingFilter(LogDetail.METHOD))
                    .filter(new RequestLoggingFilter(LogDetail.URI))
                    .filter(new RequestLoggingFilter(LogDetail.BODY))
                    .filter(new ResponseLoggingFilter(LogDetail.BODY));

    }
}
