import accesses.OrderAccesses;
import clients.OrderClient;
import io.restassured.response.ValidatableResponse;
import org.example.models.Order;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class OrderWithoutTokenTest {
    private Order order;
    private int statusCode;
    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    public OrderWithoutTokenTest(Order order, int statusCode) {
        this.order = order;
        this.statusCode = statusCode;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {OrderAccesses.getDefault(), 200},
                {OrderAccesses.getEmpty(), 400},
                {OrderAccesses.getWithInvalidHash(), 500}
        };
    }

    @Test
    public void orderBeCreated() {
        ValidatableResponse responseCreate = orderClient.createWithoutToken(order);
        int actualCode = responseCreate.extract().statusCode();
        Assert.assertEquals(statusCode, actualCode);
    }
    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(300);
    }
}