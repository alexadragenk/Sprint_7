package test;

import io.restassured.response.ValidatableResponse;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    OrderAPI orderAPI = new OrderAPI();
    private final String[] color;
    Integer trackNumber;

    public CreateOrderTest(String[] color) {
        this.color = color;
    }
    @Parameterized.Parameters
    public static Object[][] colorTestData() {
        return new Object[][] {
                { new String[]{"BLACK","GRAY"}},
                { new String[]{"BLACK"}},
                { new String[]{"GRAY"}},
                { new String[]{}}
        };
    }
    @Test
    public void orderIsCreated(){
        Order order = new Order(color);
        ValidatableResponse createdOrder = orderAPI.createOrder(order);
        int statusCode = createdOrder.extract().statusCode();
        Assert.assertEquals("Статус код не соответствует ожидаемому",201, statusCode);
        trackNumber = createdOrder.extract().path("track");
        Assert.assertNotNull("Заказ не создан, трэк номер отсутствует", trackNumber);
    }
}
