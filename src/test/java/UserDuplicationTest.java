import accesses.UserAccesses;
import clients.UserClient;
import io.restassured.response.ValidatableResponse;
import org.example.models.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserDuplicationTest {
    private UserClient userClient;
    private static UserClient userClientPredefined;
    private static User userPredefined;
    private static String preToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        userClientPredefined = new UserClient();

    }
    @Test
    public void userNotBeDuplicated(){
        userPredefined = UserAccesses.getRandom();
        ValidatableResponse responsePredefined = userClientPredefined.create(userPredefined);
        preToken = responsePredefined.extract().path("accessToken").toString().substring(6).trim();
        ValidatableResponse responseDuplicate = userClient.create(userPredefined);
        Assert.assertEquals(403, responseDuplicate.extract().statusCode());
    }
    @After
    public void tearDown() throws InterruptedException {
        userClientPredefined.delete(preToken);
        Thread.sleep(300);
    }
}