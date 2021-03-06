package tests;

import helpers.DriverUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

public class SberInsure extends TestBase{

    @Test
    @DisplayName("Проверка наличия title на странице главной странице")
    void titleTest() {
        step("Страница должна содержать текст 'СберCтрахование: оформить страховку онлайн для частных лиц и корпоративных клиентов'", () -> {
            String expectedTitle = "СберCтрахование: оформить страховку онлайн для частных лиц и корпоративных клиентов";
            String actualTitle = title();

            assertThat(actualTitle).isEqualTo(expectedTitle);
        });
    }

    @Test
    @DisplayName("Проверка ошибок в Console log")
    void consoleShouldNotHaveErrorsTest() {

        step("Проверяем, что консоль не содержит  ошибок 'SEVERE'", () -> {
            String consoleLogs = DriverUtils.getConsoleLogs();
            String errorText = "SEVERE";

            assertThat(consoleLogs).doesNotContain(errorText);
        });
    }

    @Test
    @DisplayName("Проверка наличия title на странице Автострахование")
    void checkTitleAutoInsurance() {

        step("Клик по кнопке 'Автострахование'", () -> {
            $x("//a[text()='Автострахование']").shouldBe(visible).click();
        });

        step("Страница 'Автострахование' загрузилась", () -> {
            $x("//h1[text()='Автострахование']").shouldBe(visible);
        });

        step("title страницы должен быть: 'Автострахование • Страхование автомобиля, стоимость страховки авто'", () -> {
            String expectedTitle = "Автострахование • Страхование автомобиля, стоимость страховки авто";
            String actualTitle = title();

            assertThat(actualTitle).isEqualTo(expectedTitle);
        });
    }

    @Test
    @DisplayName("Проверка алерта при входе в личный кабинет с пустым телефоном")
    void checkAlertVoidPhone() {

        step("Клик по кнопке 'Личный кабинет'", () -> {
            $x("//*[@class='s-header__actions-item s-header__profile']").click();
        });

        step("Проверка загрузки страницы личного кабинета", () -> {
            switchTo().window(1);
            $x("//*[@id='kc-login']").shouldBe(visible);
        });

        step("Кликнуть на кнопку 'Дальше'", () -> {
            $x("//*[@id='kc-login']").click();
        });

        step("Проверка алерта телефона", () -> {
            $x("//*[@id='phone-validation-msg']").shouldHave(text("Введите номер телефона."));
        });
    }

    @Test
    @DisplayName("Проверка алерта при входе в личный кабинет с телефоном короче 10 цифр")
    void checkAlertIncompletePhone() {

        step("Клик по кнопке 'Личный кабинет'", () -> {
            $x("//*[@class='s-header__actions-item s-header__profile']").click();
        });

        step("Проверка загрузки страницы личного кабинета", () -> {
            switchTo().window(1);
            $x("//*[@id='kc-login']").shouldBe(visible);
        });

        step("Ввести номер из 9 цифр", () -> {
            $x("//input[@id='mobilePhone']").setValue("111111111");
        });

        step("Кликнуть на кнопку 'Дальше'", () -> {
            $x("//*[@id='kc-login']").click();
        });

        step("Проверка алерта телефона", () -> {
            $x("//*[@id='phone-validation-msg']").shouldHave(text("Неверно указан номер телефона."));
        });
    }
}
