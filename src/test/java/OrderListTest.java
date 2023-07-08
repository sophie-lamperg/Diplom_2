import clients.OrderClient;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;

public class OrderListTest {
    private OrderClient orderClient;
    @Before
    public void setUp()  {
        orderClient = new OrderClient();
    }

    @Test
    public void getOrdersListWithToken(){
        ValidatableResponse responseCreateAuth = orderClient.ordersListWithToken();
        responseCreateAuth.assertThat().statusCode(200);
        Assert.assertTrue(responseCreateAuth.extract().path("success"));
        responseCreateAuth.assertThat().body("total", instanceOf(Integer.class));
    }

    @Test
    public void getOrdersListWithoutToken(){
        ValidatableResponse responseCreateAuth = orderClient.ordersListWithoutToken();
        responseCreateAuth.assertThat().statusCode(401);
        Assert.assertNull(responseCreateAuth.extract().path("orders"));
    }
    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(300);
    }
}
