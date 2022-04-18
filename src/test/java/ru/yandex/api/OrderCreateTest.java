package ru.yandex.api;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class OrderCreateTest {

    private String[] color;
    OrderClient orderClient;
    Order order;
    int orderTrack;

    public OrderCreateTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getColor() {
        return new Object[][] {
                {new String[]{""}},
                {new String[]{"BLACK"}},
                {new String[]{"GRAY"}},
                {new String[]{"BLACK", "GRAY"}}
        };
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();

    }
    @Test
    @DisplayName("Создание заказа с различными вариантами цвета")
    public void creatingOrderWithDifferentColorOptions() {
        order = new Order();
        order = order.getRandomOrder();
        order.setColor(color);
        ValidatableResponse orderCreateResponse = orderClient.create(order);
        int statusCode = orderCreateResponse.extract().statusCode();
        orderTrack = orderCreateResponse.extract().path("track");

        Assert.assertEquals(statusCode, 201);
        assertThat("Order track is empty", orderTrack,  is(not(0)));

    }
}

