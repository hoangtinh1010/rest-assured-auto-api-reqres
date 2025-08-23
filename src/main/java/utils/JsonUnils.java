package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;

public class JsonUnils {

    public static <T> T readJsonFromFile(String filePath, Class<T> clazz) {
        try {
//            if (!isFileExists(filePath)) {
//                throw new IOException("File not found: " + filePath);
//            }
            // In ra classpath để debug
            System.out.println("Classpath root: " + Thread.currentThread().getContextClassLoader().getResource(""));
//
//            // Thử load tuyệt đối trước
//            InputStream inputStream = Thread.currentThread()
//                    .getContextClassLoader()
//                    .getResourceAsStream(filePath.startsWith("/") ? filePath.substring(1) : filePath);
//
//            if (inputStream == null) {
//                throw new IOException("Resource not found in classpath: " + filePath);
//            }
            System.out.println(filePath);
            InputStream inputStream = clazz.getResourceAsStream(filePath);

            ObjectMapper mapper = new ObjectMapper();


            return mapper.readValue(inputStream, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to read JSON from file: " + filePath, e);
        }
    }

    private static boolean isFileExists(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.isFile();
    }
}
