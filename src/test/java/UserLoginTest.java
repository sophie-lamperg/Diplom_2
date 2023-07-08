import accesses.UserAccesses;
import clients.UserClient;
import io.restassured.response.ValidatableResponse;
import org.example.models.Credentials;
import org.example.models.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserLoginTest {
    private UserClient userClient;
    private User user;
    private String accessToken;

    @Before
    public void setUp(){
        userClient = new UserClient();
        user = UserAccesses.getRandom();
    }
    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(300);
    }

    @Test
    public void userLogin(){
        ValidatableResponse responseCreate = userClient.create(user);
        responseCreate.assertThat().statusCode(200);
        ValidatableResponse responseLogin = userClient.login(Credentials.from(user));
        int statusCode = responseLogin.extract().statusCode();
        accessToken = responseLogin.extract().path("accessToken").toString().substring(6).trim();
        Assert.assertEquals(200, statusCode);
        userClient.delete(accessToken);
    }
}