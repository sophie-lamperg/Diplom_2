package clients;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.models.Credentials;
import org.example.models.User;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class UserClient extends Client {
    private static final String URL = "api/auth/";


    @Step("Create user")
    public ValidatableResponse create(User user) {
        return given().spec(getBaseSpec()).body(user).when().post("https://stellarburgers.nomoreparties.site/api/auth/register").then();
    }
    @Step("Delete user")
    public ValidatableResponse delete(String token) {
        return given()
                .header("authorization", "bearer " + token)
                .spec(getBaseSpec())
                .when()
                .delete(" https://stellarburgers.nomoreparties.site/api/auth/user")
                .then();
    }
    @Step("Authorization")
    public ValidatableResponse authorization(User user, String token) {
        return given().header("authorization", "bearer " + token)
                .spec(getBaseSpec())
                .body(user)
                .when()
                .patch("https://stellarburgers.nomoreparties.site/api/auth/user ")
                .then();
    }
    @Step("Unauthorized")
    public ValidatableResponse unauthorization(User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .patch("https://stellarburgers.nomoreparties.site/api/auth/user ")
                .then();
    }
    @Step("Login")
    public ValidatableResponse login(Credentials credentials) {
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post("https://stellarburgers.nomoreparties.site/api/auth/login ")
                .then();
    }
}
