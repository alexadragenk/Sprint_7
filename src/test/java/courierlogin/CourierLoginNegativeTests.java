package courierlogin;

import courier.Courier;
import courier.CourierAPI;
import courier.CourierGenerator;
import courier.Credentials;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;

public class CourierLoginNegativeTests {
    private Courier courier;
    private CourierAPI courierAPI;

    @Before
    public void setUp() {
        courierAPI = new CourierAPI();
        courier = CourierGenerator.getRandom();
        ValidatableResponse response = courierAPI.create(courier);
        int statusCode = response.extract().statusCode();
        Assert.assertEquals("Статус код не соответствует ожидаемому", 201, statusCode);
    }

    @After
    public void cleanUp() {
        ValidatableResponse loginResponse = courierAPI.login(Credentials.from(courier));
        MatcherAssert.assertThat(loginResponse, notNullValue());
        int intId = loginResponse.extract().path("id");
        boolean status = CourierAPI.delete(intId).extract().path("ok");
        Assert.assertTrue("Тело ответа не соответствует ожидаемому", status);
    }

    @Test
    @DisplayName("Авторизация курьера невозможна без пароля")
    public void loginWithoutPassword() {
        Courier newCourier = new Courier(courier.getLogin(), "", courier.getFirstName());
        ValidatableResponse loginResponse = courierAPI.login(Credentials.from(newCourier));
        int statusCode = loginResponse.extract().statusCode();
        Assert.assertEquals("Статус код не соответствует ожидаемому", 400, statusCode);
        String isMessageCorrect = loginResponse.extract().path("message");
        Assert.assertEquals("Сообщение об ошибке не соответствует ожидаемому", "Недостаточно данных для входа", isMessageCorrect);
    }

    @Test
    @DisplayName("Авторизация курьера невозможна без логина")
    public void loginWithoutLogin() {
        Courier newCourier = new Courier("", courier.getPassword(), courier.getFirstName());
        ValidatableResponse loginResponse = courierAPI.login(Credentials.from(newCourier));
        int statusCode = loginResponse.extract().statusCode();
        Assert.assertEquals("Статус код не соответствует ожидаемому", 400, statusCode);
        String isMessageCorrect = loginResponse.extract().path("message");
        Assert.assertEquals("Сообщение об ошибке не соответствует ожидаемому", "Недостаточно данных для входа", isMessageCorrect);
    }

    @Test
    @DisplayName("Авторизация курьера невозможна с неправильным паролем")
    public void loginWithWrongPassword() {
        Courier newCourier = new Courier(courier.getLogin(), courier.getPassword(), courier.getFirstName());
        newCourier.setPassword("WrongPassword");
        ValidatableResponse loginResponse = courierAPI.login(Credentials.from(newCourier));
        int statusCode = loginResponse.extract().statusCode();
        Assert.assertEquals("Статус код не соответствует ожидаемому", 404, statusCode);
        String isMessageCorrect = loginResponse.extract().path("message");
        Assert.assertEquals("Сообщение об ошибке не соответствует ожидаемому", "Учетная запись не найдена", isMessageCorrect);
    }

    @Test
    @DisplayName("Авторизация курьера невозможна с неправильным логином")
    public void loginWithWrongLogin() {
        Courier newCourier = new Courier(courier.getLogin(), courier.getPassword(), courier.getFirstName());
        newCourier.setLogin("WrongLogin");
        ValidatableResponse loginResponse = courierAPI.login(Credentials.from(newCourier));
        int statusCode = loginResponse.extract().statusCode();
        Assert.assertEquals("Статус код не соответствует ожидаемому", 404, statusCode);
        String isMessageCorrect = loginResponse.extract().path("message");
        Assert.assertEquals("Сообщение об ошибке не соответствует ожидаемому", "Учетная запись не найдена", isMessageCorrect);
    }
}
