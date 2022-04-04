package ru.yandex.api;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient  extends ScooterRestClient {
    private static final String ORDER_PATH = "api/v1/orders/";

    @Step("Создание заказа")
    public ValidatableResponse create(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then()
                .log().all();
    }

    @Step("Отмена заказа")
    public ValidatableResponse cancel(int ordersTrack) {
        return given()
                .spec(getBaseSpec())
                .body(ordersTrack)
                .when()
                .put(ORDER_PATH + "cancel")
                .then()
                .log().all();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse ordersList() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDER_PATH)
                .then()
                .log().all();
    }

    @Step("Принять заказ")
    public ValidatableResponse acceptOrders(int orderId, int courierId) {
        return given()
                .spec(getBaseSpec())
                .when()
                .queryParam("courierId",courierId)
                .put(ORDER_PATH +"/accept/" +orderId)
                .then()
                .log().all();
    }

}

