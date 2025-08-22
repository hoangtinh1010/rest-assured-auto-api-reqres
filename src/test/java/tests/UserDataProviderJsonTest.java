package tests;
import apis.UserApi;
import io.restassured.response.Response;
import net.minidev.json.JSONObject;
import providers.UserDataProviderJson;

import base.BaseTest;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class UserDataProviderJsonTest extends BaseTest {

    @Test(dataProvider = "userDataJson", dataProviderClass = UserDataProviderJson.class)
    public void testCreateUserWithJsonData(String name, String job, int expectedStatusCode) {
        // Tạo payload JSON từ dữ liệu cung cấp
        JSONObject userPayload = new JSONObject();
        userPayload.put("name", name);
        userPayload.put("job", job);

        Response response = UserApi.createUser((JSONObject) userPayload);

        // Kiểm tra mã trạng thái trả về
        response.then().statusCode(expectedStatusCode);
        // Thêm các kiểm tra khác nếu cần
        if (expectedStatusCode == 201) {
            // Nếu tạo thành công, kiểm tra các trường trong phản hồi
            response.then().assertThat()
                    .body("name", equalTo(name))
                    .body("job", equalTo(job));
        } else {
            // Nếu không thành công, có thể kiểm tra thông báo lỗi hoặc các trường khác
            response.then().assertThat()
                    .body("error", notNullValue());
        }
    }

}
