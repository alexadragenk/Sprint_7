package couriercreate;

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

public class CourierCreatePositiveTest {
    private Courier courier;
    private CourierAPI courierAPI;

    @Before
    public void setUp() {
        courierAPI = new CourierAPI();
        courier = CourierGenerator.getRandom();
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
    @DisplayName("Создание курьера")
    public void courierCanBeCreated() {
        ValidatableResponse response = courierAPI.create(courier);
        int statusCode = response.extract().statusCode();
        Assert.assertEquals("Статус код не соответствует ожидаемому", 201, statusCode);
        boolean isCourierTrue = response.extract().path("ok");
        Assert.assertTrue("Тело ответа не соответствует ожидаемому", isCourierTrue);
    }
}