package test;

import io.restassured.response.ValidatableResponse;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;

public class CourierLoginPositiveTest {
    private Courier courier;
    private CourierAPI courierAPI;
    @Before
    public void setUp(){
        courierAPI = new CourierAPI();
        courier = CourierGenerator.getRandom();
        ValidatableResponse response = courierAPI.create(courier);
        int statusCode = response.extract().statusCode();
        Assert.assertEquals("Статус код не соответствует ожидаемому",201, statusCode);
    }
    @After
    public void cleanUp(){
        ValidatableResponse loginResponse = courierAPI.login(Credentials.from(courier));
        MatcherAssert.assertThat(loginResponse, notNullValue());
        int intId = loginResponse.extract().path("id");
        boolean status = CourierAPI.delete(intId).extract().path("ok");
        Assert.assertTrue("Тело ответа не соответствует ожидаемому", status);
    }
    @Test
    public void courierCanLogin(){
        ValidatableResponse loginResponse = courierAPI.login(Credentials.from(courier));
        int statusCode = loginResponse.extract().statusCode();
        Assert.assertEquals("Статус код не соответствует ожидаемому",200, statusCode);
        Integer courierId = loginResponse.extract().path("id");
        Assert.assertNotNull("Kлюч id не содержится в респонсе", courierId);
    };
}
