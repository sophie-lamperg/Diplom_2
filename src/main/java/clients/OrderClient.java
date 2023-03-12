package clients;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import accesses.CredentialAccesses;
import org.example.models.Order;

import static io.restassured.RestAssured.given;

public class OrderClient extends Client {
    private final UserClient userClient = new UserClient();
    private final ValidatableResponse responseLogin = userClient.login(CredentialAccesses.getDefault());
    private final String accessToken = responseLogin.extract().path("accessToken").toString().substring(6).trim();

    @Step("Order creation without token")
    public ValidatableResponse createWithoutToken(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post("https://stellarburgers.nomoreparties.site/api/orders")
                .then();

    }

    @Step("Create order with token")
    public ValidatableResponse createWithToken(Order order) {
        return given()
                .header("authorization", "bearer " + accessToken)
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post("https://stellarburgers.nomoreparties.site/api/orders")
                .then();
    }

    @Step("orders list without token")
    public ValidatableResponse ordersListWithoutToken() {
        return given()
                .spec(getBaseSpec())
                .body("")
                .when()
                .get("https://stellarburgers.nomoreparties.site/api/orders")
                .then();
    }

    @Step("orders list with token")
    public ValidatableResponse ordersListWithToken() {
        return given()
                .header("authorization", "bearer " + accessToken)
                .spec(getBaseSpec())
                .body("")
                .when()
                .get("https://stellarburgers.nomoreparties.site/api/orders")
                .then();
    }
}