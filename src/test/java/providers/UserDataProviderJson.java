package providers;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.UserDataFactory;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class UserDataProviderJson {

    @DataProvider(name = "userDataJson")
    public static Object[][] userDataFromJson() throws IOException {
        //Load file JSON
        // This file should contain an array of user objects with "name", "job", and "expectedStatusCode" fields
        InputStream inputStream = UserDataProviderJson.class.getClassLoader().getResourceAsStream("data/user_data.json");

        if (inputStream == null) {
            throw new IOException(" Không tìm thấy file: data/user_data.json. Kiểm tra lại đường dẫn file trong thư mục resources.");
        }

        ObjectMapper mapper = new ObjectMapper();

        // Read JSON file and convert it to List<Map<String, Object>>
        List<Map<String, Object>> userDataList = mapper.readValue(inputStream, new TypeReference<List<Map<String, Object>>>() {});

        //Convert List<Map> thành Object[][] để TestNG dùng

        Object[][] data = new Object[userDataList.size()][3];
        for (int i = 0; i < userDataList.size(); i++) {
            Map<String, Object> userData = userDataList.get(i);
            data[i][0] = userData.get("name");
            data[i][1] = userData.get("job");
            data[i][2] = (int)userData.get("expectedStatus");
            }
        return data;

    }
}
