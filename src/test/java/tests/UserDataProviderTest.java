package tests;
import apis.UserApi;
import base.BaseTest;
import io.restassured.response.Response;
import net.minidev.json.JSONObject;
import org.testng.annotations.Test;
import providers.UserDataProvider;
public class UserDataProviderTest extends BaseTest {

    @Test(dataProvider = "userData", dataProviderClass = UserDataProvider.class)
    public void testCreateUserWithMultipleData(Object userPayload, int expectedStatusCode) {
        // Gọi API với dữ liệu từ DataProvider
        Response response = UserApi.createUser((JSONObject) userPayload);

        // Kiểm tra mã trạng thái trả về
        response.then().statusCode(expectedStatusCode);

        System.out.println("expectedStatusCode: " + expectedStatusCode);


        // Thêm các kiểm tra khác nếu cần
    }

}
