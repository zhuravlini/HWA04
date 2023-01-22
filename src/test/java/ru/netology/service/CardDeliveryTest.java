package ru.netology.service;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

class CardDeliveryTest {
    private String stringDate;

    @BeforeEach
    public void setUp() {
        LocalDate today = LocalDate.now();
        LocalDate dateToBeSet = today.plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        stringDate = dateToBeSet.format(formatter);

    }


    @Test
    public void shouldTest() {
        Configuration.holdBrowserOpen = true;

        open("http://localhost:9999/");
        $("[data-test-id=city] input.input__control").setValue("Москва");
        $("[data-test-id=date] input.input__control").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input.input__control").setValue(stringDate);
        $("[data-test-id=name] input.input__control").setValue("Иванов-Петров Иван");
        $("[data-test-id=phone] input.input__control").setValue("+79854443322");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + stringDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);

    }
}