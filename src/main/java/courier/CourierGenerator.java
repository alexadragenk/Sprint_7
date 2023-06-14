package courier;

import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {
    @Step("Генерация данных курьера")
    public static Courier getRandom() {
        final String login = RandomStringUtils.randomAlphabetic(8);
        final String password = RandomStringUtils.randomAlphabetic(8);
        final String firstName = RandomStringUtils.randomAlphabetic(8);
        return new Courier(login, password, firstName);
    }

    @Step("Генерация данных курьера без логина")
    public static Courier getRandomWithoutLogin() {
        final String password = RandomStringUtils.randomAlphabetic(8);
        final String firstName = RandomStringUtils.randomAlphabetic(8);
        return new Courier(null, password, firstName);
    }

    @Step("Генерация данных курьера без пароля")
    public static Courier getRandomWithoutPassword() {
        final String login = RandomStringUtils.randomAlphabetic(8);
        final String firstName = RandomStringUtils.randomAlphabetic(8);
        return new Courier(login, null, firstName);
    }

}
