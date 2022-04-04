package ru.yandex.api;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class DeleteCourierTest {

    CourierClient courierClient;
    Courier courier;
    int courierId;
    CourierCredentials credentials;
    LoginCourierClient loginCourier;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = courier.getRandomCourier();
        credentials = new CourierCredentials(courier.getLogin(), courier.getPassword());
        loginCourier = new LoginCourierClient();
    }

    @Test
    @DisplayName("Удаление созданного логина")
    public void deleteCreatedLogin() {
        courierClient.create(courier);
        ValidatableResponse registerResponse = loginCourier.login(credentials);
        courierId = registerResponse.extract().path("id");
        ValidatableResponse delete = courierClient.delete(courierId);
        int statusCode = delete.extract().statusCode();
        boolean ok = delete.extract().path("ok");


        Assert.assertEquals(statusCode, 200);
        Assert.assertEquals(ok, true);
    }

    @Test
    @DisplayName("Удаление не созданного логина")
    public void deleteNotCreatedLogin() {
        courierId = 0;
        ValidatableResponse delete = courierClient.delete(courierId);
        int statusCode = delete.extract().statusCode();
        int code = delete.extract().path("code");
        String message = delete.extract().path("message");

        Assert.assertEquals(statusCode, 404);
        Assert.assertEquals(code, 404);
        Assert.assertEquals(message, "Курьера с таким id нет.");

    }

    @Test
    @DisplayName("Удаление логина без передачи id")
    public void deleteRequestWithoutId() {

        courierClient.create(courier);
        ValidatableResponse registerResponse = loginCourier.login(credentials);
        courierId = registerResponse.extract().path("id");
        ValidatableResponse delete = courierClient.deleteNotCourierId();
        int statusCode = delete.extract().statusCode();
        int code = delete.extract().path("code");
        String message = delete.extract().path("message");


        Assert.assertEquals(statusCode, 400);
        Assert.assertEquals(code, 400);
        Assert.assertEquals(message, "Недостаточно данных для удаления курьера");
    }

    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }

}

