import accesses.UserAccesses;
import clients.UserClient;
import io.restassured.response.ValidatableResponse;
import org.example.models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserCreationTest {
    private UserClient userClient;
    private User user;
    private String accessToken;

    @Before
    public void setUp(){
        userClient = new UserClient();
        user = UserAccesses.getRandom();
    }
    @Test
    public void userBeCreated(){
        ValidatableResponse responseCreate = userClient.create(user);
        responseCreate.assertThat().statusCode(200);
        accessToken = responseCreate.extract().path("accessToken").toString().substring(6).trim();
        userClient.delete(accessToken);
    }
    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(300);
    }
}
