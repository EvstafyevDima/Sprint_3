package ru.yandex.api;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class Order {
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;

    public Order() {
    }

    public Order(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
    }

    @Step("Создание рандомного заказа c параметрами")
    public Order getRandomOrder(){

        String firstName = RandomStringUtils.randomAlphabetic(5);
        String lastName = RandomStringUtils.randomAlphabetic(1);
        String address = RandomStringUtils.randomAlphabetic(20);
        String metroStation = RandomStringUtils.randomAlphabetic(15);
        String phone = RandomStringUtils.random(10);
        int rentTime = Integer.parseInt(RandomStringUtils.randomNumeric(3));
        String deliveryDate = "2022-04-03";
        String comment = RandomStringUtils.randomAlphabetic(10);

        Allure.addAttachment("Имя: ", firstName);
        Allure.addAttachment("Фамилия: ", lastName);
        Allure.addAttachment("Адрес: ", address);
        Allure.addAttachment("Станция метро: ", metroStation);
        Allure.addAttachment("Телефон: ", phone);
        Allure.addAttachment("Количество дней аренды: ", String.valueOf(rentTime));
        Allure.addAttachment("Дата доставки: ",deliveryDate);
        Allure.addAttachment("Комментарий: ",comment);

        return new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMetroStation() {
        return metroStation;
    }

    public void setMetroStation(String metroStation) {
        this.metroStation = metroStation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRentTime() {
        return rentTime;
    }

    public void setRentTime(int rentTime) {
        this.rentTime = rentTime;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String[] getColor() {
        return color;
    }

    public void setColor(String[] color) {
        this.color = color;
    }
}

