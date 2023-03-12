package accesses;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import org.example.models.User;

public class UserAccesses {
    public static User getDefault() {
        return new User("login@mail.ru", "1111", "Yura");
    }

    @Step("User without email")
    public static User getWithoutEmail() {
        return new User("", "1111", "Yura");
    }

    @Step("User without password")
    public static User getWithoutPassword() {
        return new User("login@mail.ru", "", "Yura");
    }

    @Step("User without name")
    public static User getWithoutName() {
        return new User("login@mail.ru", "1111", "");
    }

    @Step("Without creds")
    public static User getEmpty() {
        return new User();
    }
    @Step("Random user")
    public static User getRandom() {
        Faker faker = new Faker();
        return new User(CredentialAccesses.getRandom().getEmail(), CredentialAccesses.getRandom().getPassword(), faker.name().firstName());
    }
}
