import accesses.CredentialAccesses;
import clients.UserClient;
import io.restassured.response.ValidatableResponse;
import org.example.models.Credentials;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class UserLoginNegativeTest {
    private Credentials credentials;
    private String message;
    private int statusCode;
    private UserClient userClient;

    @Before
    public void setUp() {
        userClient = new UserClient();
    }
    public UserLoginNegativeTest(Credentials credentials, int statusCode, String message) {
        this.credentials = credentials;
        this.statusCode = statusCode;
        this.message = message;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData(){
        return new Object[][]{
                {CredentialAccesses.getWithInvalidPassword(), 401, "email or password are incorrect"},
                {CredentialAccesses.getWithInvalidLogin(), 401, "email or password are incorrect"},
                {CredentialAccesses.getWithEmptyCreds(), 401, "email or password are incorrect"},
        };
    }

    @Test
    public void userLogin(){
        ValidatableResponse responseLogin = userClient.login(credentials);
        String actualMessage = responseLogin.extract().path("message").toString();
        int actualStatusCode = responseLogin.extract().statusCode();
        Assert.assertEquals(statusCode, actualStatusCode);
        Assert.assertEquals(message, actualMessage);
    }
    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(300);
    }
}