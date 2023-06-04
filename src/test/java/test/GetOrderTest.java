package test;

import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Test;

public class GetOrderTest {
    OrderAPI orderAPI = new OrderAPI();
    @Test
    public void getOrderTest(){
        ValidatableResponse getorderResponse = orderAPI.getOrder();
        int statusCode = getorderResponse.extract().statusCode();
        Assert.assertEquals("Статус код не соответствует ожидаемому",200, statusCode);
        Object orders = getorderResponse.extract().path("orders");
        Assert.assertNotNull("Kлюч orders не содержится в респонсе", orders);
    };

}
