package providers;
import data.UserDataFactory;
import org.testng.annotations.DataProvider;

public class UserDataProvider {
    @DataProvider(name="userData")
    public static Object[][] userData() {
        return new Object[][]{
                {UserDataFactory.createValidUser(), 201},
                {UserDataFactory.createUserWithJob(null), 201},
                {UserDataFactory.createUserWithName(null), 201}

        };
    }
}
