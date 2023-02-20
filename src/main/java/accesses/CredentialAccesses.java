package accesses;

import io.qameta.allure.Step;
import net.datafaker.Faker;
import org.example.models.Credentials;

public class CredentialAccesses {
    @Step("Default creds")
    public static Credentials getDefault() {
        return new Credentials("login@mail.ru", "1111");
    }
    @Step("Invalid password")
    public static Credentials getWithInvalidPassword() {
        return new Credentials("login@mail.ru", "");
    }

    @Step("Invalid login")
    public static Credentials getWithInvalidLogin() {
        return new Credentials("", "1111");
    }

    @Step("Creds is empty")
    public static Credentials getWithEmptyCreds() {
        return new Credentials();
    }
    @Step("Credentials for random user")
    public static Credentials getRandom() {
        Faker faker = new Faker();
        String login = faker.bothify("!!!???!!!!@mail.ru");
        String password = faker.regexify("[a-z1-9]{10}");
        return new Credentials(login, password);
    }
}
