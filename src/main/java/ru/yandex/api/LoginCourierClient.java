package ru.yandex.api;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;


public class LoginCourierClient extends ScooterRestClient {


    private static final String COURIER_PATH = "api/v1/courier/";

    @Step("Логин курьера в системе")
    public ValidatableResponse login(CourierCredentials credentials) {
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(COURIER_PATH + "login")
                .then()
                .log().all();
    }

}

