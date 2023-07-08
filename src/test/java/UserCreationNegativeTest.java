import accesses.CredentialAccesses;
import accesses.UserAccesses;
import clients.UserClient;
import io.restassured.response.ValidatableResponse;
import org.example.models.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class UserCreationNegativeTest {
    private User user;
    private String message;
    private int statusCode;
    private int actualStatusCode;
    private UserClient userClient;
    private String accessToken;
    private ValidatableResponse responseCreate;

    @Before
    public void setUp() {
        userClient = new UserClient();
    }
    @After
    public void tearDown() throws InterruptedException {
        if (actualStatusCode == 200){
            accessToken = responseCreate.extract().path("accessToken").toString().substring(6).trim();
            userClient.delete(accessToken);
        }
        Thread.sleep(300);
    }
    public UserCreationNegativeTest(User user, int statusCode, String message) {
        this.user = user;
        this.statusCode = statusCode;
        this.message = message;
    }
    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {UserAccesses.getWithoutEmail(), 403, "Email, password and name are required fields"},
                {UserAccesses.getWithoutPassword(), 403, "Email, password and name are required fields"},
                {UserAccesses.getWithoutName(), 403, "Email, password and name are required fields"},
                {UserAccesses.getEmpty(), 403, "Email, password and name are required fields"},
                {new User(CredentialAccesses.getDefault().getEmail(), CredentialAccesses.getDefault().getPassword(), "Bob"), 403, "User already exists"}
        };
    }

    @Test
    public void userBeCreated(){
        responseCreate = userClient.create(user);
        String actualMessage = responseCreate.extract().path("message").toString();
        actualStatusCode = responseCreate.extract().statusCode();
        Assert.assertEquals(statusCode, actualStatusCode);
        Assert.assertEquals(message, actualMessage);
    }
}