import accesses.UserAccesses;
import clients.UserClient;
import io.restassured.response.ValidatableResponse;
import org.example.models.Credentials;
import org.example.models.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class UserChangeTest {

    private User newUser;
    private int statusCode;
    private String accessToken;
    private static String token;
    private boolean isSuccess;
    private String message;
    @Before
    public void setUp() {
    }

    public UserChangeTest(String accessToken, User newUser,int statusCode,boolean isSuccess, String message){
        this.newUser = newUser;
        this.accessToken = accessToken;
        this.statusCode = statusCode;
        this.isSuccess = isSuccess;

    }
    @Parameterized.Parameters
    public static Object[][] getTestData(){
        UserClient userClient = new UserClient();
        User user = UserAccesses.getRandom();
        String login = user.getEmail();
        String password = user.getPassword();
        String name = user.getName();
        ValidatableResponse responseCreate = userClient.create(user);
        responseCreate.assertThat().statusCode(200);
        ValidatableResponse responseLogin = userClient.login(Credentials.from(user));
        responseLogin.assertThat().statusCode(200);
        token = responseLogin.extract().path("accessToken").toString().substring(6).trim();

        return new Object[][]{
                {token, new User(user.getEmail() + "1",user.getPassword() + "1", user.getName() + "1" ), 200, true, null},
                {token, new User(login,password + "1", name+"1" ), 403, false, "User with such email already exists"},
                {"token", new User(user.getEmail() + "1",user.getPassword() + "1", user.getName() + "1" ), 403, false, "jwt malformed"}
        };
    }

    @Test
    public void userCanBeModified(){
        ValidatableResponse responseModify = new UserClient().authorization(newUser, accessToken);
        Assert.assertEquals(statusCode, responseModify.extract().statusCode());
        Assert.assertEquals(isSuccess, responseModify.extract().path("success"));
        new UserClient().delete(accessToken);
    }
    @After
    public void cleanUp() throws InterruptedException {
        new UserClient().delete(token);
        Thread.sleep(400);
    }
}