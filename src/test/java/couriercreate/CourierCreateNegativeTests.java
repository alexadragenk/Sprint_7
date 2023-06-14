package couriercreate;

import courier.Courier;
import courier.CourierAPI;
import courier.CourierGenerator;
import courier.Credentials;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class CourierCreateNegativeTests {
    private Courier courier;
    private CourierAPI courierAPI;

    @Before
    public void setUp() {
        courierAPI = new CourierAPI();
    }

    @After
    public void cleanUp() {
        if (courier.getPassword() == null) {
            return;
        }
        ValidatableResponse loginResponse = courierAPI.login(Credentials.from(courier));
        Integer intId = loginResponse.extract().path("id");
        if (intId != null) {
            CourierAPI.delete(intId);
        }
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    public void sameCourierCannotBeCreated() {
        courier = CourierGenerator.getRandom();
        courierAPI.create(courier);
        ValidatableResponse response = courierAPI.create(courier);
        int statusCode = response.extract().statusCode();
        Assert.assertEquals("Статус код не соответствует ожидаемому", 409, statusCode);
        String isMessageCorrect = response.extract().path("message");
        Assert.assertEquals("Сообщение об ошибке не соответствует ожидаемому", "Этот логин уже используется. Попробуйте другой.", isMessageCorrect);
    }

    @Test
    @DisplayName("Нельзя создать курьера, не указав логин")
    public void loginIsRequired() {
        courier = CourierGenerator.getRandomWithoutLogin();
        ValidatableResponse response = courierAPI.create(courier);
        int statusCode = response.extract().statusCode();
        Assert.assertEquals("Статус код не соответствует ожидаемому", 400, statusCode);
        String isMessageCorrect = response.extract().path("message");
        Assert.assertEquals("Сообщение об ошибке не соответствует ожидаемому", "Недостаточно данных для создания учетной записи", isMessageCorrect);
    }

    @Test
    @DisplayName("Нельзя создать курьера, не указав пароль")
    public void passwordIsRequired() {
        courier = CourierGenerator.getRandomWithoutPassword();
        ValidatableResponse response = courierAPI.create(courier);
        int statusCode = response.extract().statusCode();
        Assert.assertEquals("Статус код не соответствует ожидаемому", 400, statusCode);
        String isMessageCorrect = response.extract().path("message");
        Assert.assertEquals("Сообщение об ошибке не соответствует ожидаемому", "Недостаточно данных для создания учетной записи", isMessageCorrect);
    }
}