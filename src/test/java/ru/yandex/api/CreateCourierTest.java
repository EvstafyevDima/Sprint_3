package ru.yandex.api;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CreateCourierTest {

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

    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Создание логина")
    public void registrationOfCredentials() {
        ValidatableResponse registrationOfCredentials = courierClient.create(courier);
        int statusCode = registrationOfCredentials.extract().statusCode();
        boolean ok = registrationOfCredentials.extract().path("ok");


        Assert.assertEquals(statusCode, 201);
        Assert.assertEquals(ok, true);
    }

    @Test
    @DisplayName("Создание использованного логина")
    public void registrationOfTheLoginUsed() {
        courierClient.create(courier);
        ValidatableResponse registerResponse = loginCourier.login(credentials);
        courierId = registerResponse.extract().path("id");
        ValidatableResponse LoginUsed = courierClient.create(courier);
        int statusCode = LoginUsed.extract().statusCode();
        String message = LoginUsed.extract().path("message");
        int code = LoginUsed.extract().path("code");


        Assert.assertEquals(statusCode, 409);
        Assert.assertEquals(code, 409);
        Assert.assertEquals(message, "Этот логин уже используется. Попробуйте другой.");
    }

    @Test
    @DisplayName("Создание логина без заполненного поля login")
    public void registrationLoginWithAnEmptyLoginField() {
        Courier EmptyLoginField = new Courier("", courier.getPassword(), courier.getFirstName());
        ValidatableResponse loginResponse = courierClient.create(EmptyLoginField);
        int statusCode = loginResponse.extract().statusCode();
        int code = loginResponse.extract().path("code");
        String message = loginResponse.extract().path("message");

        Assert.assertEquals(statusCode, 400);
        Assert.assertEquals(code, 400);
        Assert.assertEquals(message, "Недостаточно данных для создания учетной записи");

    }

    @Test
    @DisplayName("Создание логина без заполненного поля password")
    public void registrationLoginWithAnEmptyPasswordField() {
        Courier EmptyPasswordField = new Courier(courier.getLogin(),"",courier.getFirstName());
        ValidatableResponse loginResponse = courierClient.create(EmptyPasswordField);
        int statusCode = loginResponse.extract().statusCode();
        int code = loginResponse.extract().path("code");
        String message = loginResponse.extract().path("message");

        Assert.assertEquals(statusCode, 400);
        Assert.assertEquals(code, 400);
        Assert.assertEquals(message, "Недостаточно данных для создания учетной записи");

    }

    @Test
    @DisplayName("Создание логина без заполненного поля firstName")
    public void registrationLoginWithAnEmptyFirstNameField() {
        Courier EmptyFirstNameField = new Courier(courier.getLogin(), courier.getPassword(), "");
        ValidatableResponse loginResponse = courierClient.create(EmptyFirstNameField);
        int statusCode = loginResponse.extract().statusCode();
        String code = loginResponse.extract().path("code");
        String message = loginResponse.extract().path("message");

        Assert.assertEquals(statusCode, 400);
        Assert.assertEquals(code, "400");
        Assert.assertEquals(message, "Недостаточно данных для создания учетной записи");

    }

}
