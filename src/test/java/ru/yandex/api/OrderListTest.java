package ru.yandex.api;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;;

public class OrderListTest {
    OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Получение списка заказов")
    public void orderListGet () {
        ValidatableResponse ordersListResponse = orderClient.ordersList();
        int statusCode = ordersListResponse.extract().statusCode();
        String response = ordersListResponse.extract().toString();

        Assert.assertEquals(statusCode, 200);
        assertThat("Response body is null", response != null);

    }
}

