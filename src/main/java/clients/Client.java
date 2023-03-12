package clients;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Client {
    public static RequestSpecification getBaseSpec() {
        return new RequestSpecBuilder()
                .setBaseUri("https://stellarburgers.nomoreparties.site/api")
                .setContentType(ContentType.JSON)
                .build();
    }
}