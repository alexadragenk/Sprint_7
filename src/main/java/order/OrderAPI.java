package order;

import common.BaseSettings;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderAPI extends BaseSettings {
    private static final String ORDER_PATH = "/api/v1/orders";
    private static final String CANCEL_ORDER_PATH = "/api/v1/orders/cancel";

    @Step("Создание заказа")
    public ValidatableResponse createOrder(Order order) {
        return given()
                .spec(getBaseSettings())
                .body(order)
                .post(ORDER_PATH)
                .then();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getOrder() {
        return given()
                .spec(getBaseSettings())
                .body("{}")
                .get(ORDER_PATH)
                .then();
    }
}

