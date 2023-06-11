package order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private final String[] color;
    OrderAPI orderAPI = new OrderAPI();
    Integer trackNumber;

    public CreateOrderTest(ArrayWrapper color) {
        this.color = color.arr;
    }

    @Parameterized.Parameters(name = "Цвет самоката. Тестовые данные: {0}")
    public static Object[][] colorTestData() {
        return new Object[][]{
                {new ArrayWrapper(new String[]{"BLACK", "GRAY"})},
                {new ArrayWrapper(new String[]{"BLACK"})},
                {new ArrayWrapper(new String[]{"GRAY"})},
                {new ArrayWrapper(new String[]{})}
        };
    }

    @Test
    @DisplayName("Создание заказа")
    public void orderIsCreated() {
        Order order = new Order(color);
        ValidatableResponse createdOrder = orderAPI.createOrder(order);
        int statusCode = createdOrder.extract().statusCode();
        Assert.assertEquals("Статус код не соответствует ожидаемому", 201, statusCode);
        trackNumber = createdOrder.extract().path("track");
        Assert.assertNotNull("Заказ не создан, трэк номер отсутствует", trackNumber);
    }

    private static class ArrayWrapper {
        public final String[] arr;

        public ArrayWrapper(String[] arr) {
            this.arr = arr;
        }

        @Override
        public String toString() {
            return Arrays.toString(arr);
        }
    }
}
