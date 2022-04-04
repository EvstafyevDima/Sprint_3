package ru.yandex.api;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class LoginCourierTest {

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
    @DisplayName("Вход курьера с действующей учетной записью")
    public void courierCanLoginWithValidCredentials() {
        courierClient.create(courier);
        ValidatableResponse loginResponse = loginCourier.login(credentials);
        int statusCode = loginResponse.extract().statusCode();
        courierId = loginResponse.extract().path("id");

        Assert.assertEquals(statusCode, 200);
        assertThat(courierId, is(not(0)));

    }

    @Test
    @DisplayName("Вход курьера с несуществующей учетной записью")
    public void courierСannotLogInWithFalseData() {
        ValidatableResponse loginResponse = loginCourier.login(credentials);
        int statusCode = loginResponse.extract().statusCode();
        String message = loginResponse.extract().path("message");
        int code = loginResponse.extract().path("code");

        Assert.assertEquals(statusCode, 404);
        Assert.assertEquals(code, 404);
        Assert.assertEquals(message, "Учетная запись не найдена");

    }

    @Test
    @DisplayName("Вход курьера с пустым логином")
    public void courierCannotLogInWithAnEmptylogin() {
        CourierCredentials credentialsWithAnEmptylogin = new CourierCredentials("", courier.getPassword());
        ValidatableResponse loginResponse = loginCourier.login(credentialsWithAnEmptylogin);
        int statusCode = loginResponse.extract().statusCode();
        String message = loginResponse.extract().path("message");
        int code = loginResponse.extract().path("code");

        Assert.assertEquals(statusCode, 400);
        Assert.assertEquals(code, 400);
        Assert.assertEquals(message, "Недостаточно данных для входа");

    }

    @Test
    @DisplayName("Вход курьера с пустым паролем")
    public void courierCannotLogInWithAnEmptyPassword() {
        CourierCredentials credentialsWithAnEmptyPassword = new CourierCredentials(courier.getLogin(), "");
        ValidatableResponse loginResponse = loginCourier.login(credentialsWithAnEmptyPassword);
        int statusCode = loginResponse.extract().statusCode();
        String message = loginResponse.extract().path("message");
        int code = loginResponse.extract().path("code");

        Assert.assertEquals(statusCode, 400);
        Assert.assertEquals(code, 400);
        Assert.assertEquals(message, "Недостаточно данных для входа");

    }

    @Test
    @DisplayName("Вход курьера с пустым паролем и логином")
    public void courierCannotLogInWithoutFillingInTheFields() {
        CourierCredentials LogInWithoutFillingInTheFields = new CourierCredentials("", "");
        ValidatableResponse loginResponse = loginCourier.login(LogInWithoutFillingInTheFields);
        int statusCode = loginResponse.extract().statusCode();
        String message = loginResponse.extract().path("message");
        int code = loginResponse.extract().path("code");

        Assert.assertEquals(statusCode, 400);
        Assert.assertEquals(code, 400);
        Assert.assertEquals(message, "Недостаточно данных для входа");

    }

    @Test
    @DisplayName("Вход курьера с несуществующим логином")
    public void courierCannotLogInWithoutIncorrectlogin() {
        CourierCredentials LogInWithoutIncorrectlogin = new CourierCredentials("Incorrectlogin", courier.getPassword());
        ValidatableResponse loginResponse = loginCourier.login(LogInWithoutIncorrectlogin);
        int statusCode = loginResponse.extract().statusCode();
        String message = loginResponse.extract().path("message");
        int code = loginResponse.extract().path("code");

        Assert.assertEquals(statusCode, 404);
        Assert.assertEquals(code, 404);
        Assert.assertEquals(message, "Учетная запись не найдена");

    }

    @Test
    @DisplayName("Вход курьера с несуществующим паролем")
    public void courierCannotLogInWithoutIncorrectPassword() {
        CourierCredentials LogInWithoutIncorrectPassword = new CourierCredentials(courier.getLogin(), "IncorrectPassword");
        ValidatableResponse loginResponse = loginCourier.login(LogInWithoutIncorrectPassword);
        int statusCode = loginResponse.extract().statusCode();
        String message = loginResponse.extract().path("message");
        int code = loginResponse.extract().path("code");

        Assert.assertEquals(statusCode, 404);
        Assert.assertEquals(code, 404);
        Assert.assertEquals(message, "Учетная запись не найдена");

    }

}

