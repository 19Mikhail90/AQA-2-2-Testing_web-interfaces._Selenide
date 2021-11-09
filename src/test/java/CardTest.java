import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;


class CardDeliveryTest {

    @AfterEach
    public void closeDriver() {
        Selenide.closeWebDriver();
    }

    @Test
    void shouldCalendarTest() {//позитивный тест с выбором в календаре и двумя буквами в поле город
        LocalDate today = LocalDate.now().plusDays(7);
        String formedDate = today.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("То");
        $$("[class=menu-item__control").findBy(text("Томск")).click();
        $("[data-test-id=date] input").click();


        $("[data-test-id=name] input").setValue("Агутин Леонид");
        $("[data-test-id=phone] input").setValue("+79617418596");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $(byText("Встреча успешно забронирована на")).shouldBe(visible);
    }

    @Test
    void shouldIncorrectDateTest() { //негативный тест с фактической датой
        LocalDate today = LocalDate.now();
        String formedDate = today.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Ека");
        $$("[class=menu-item__control").findBy(text("Екатеринбург")).click();
        $("[data-test-id=date] input").doubleClick().sendKeys(formedDate);
        $("[data-test-id=name] input").setValue("Николай Расторгуев");
        $("[data-test-id=phone] input").setValue("+79617418596");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $(byText("Заказ на выбранную дату невозможен")).shouldBe(visible, Duration.ofSeconds(15));
    }
    @Test
    void shouldPositiveTest() {//позитивный тест с датой больше на 4 дня от фактической
        LocalDate today = LocalDate.now().plusDays(4);
        String formedDate = today.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        open("http://localhost:9999");

        $("[data-test-id=city] input").setValue("То");
        $$("[class=menu-item__control").findBy(text("Томск")).click();

        $("[data-test-id=date] input").doubleClick().sendKeys(formedDate);

        $("[data-test-id=name] input").setValue("Агутин Леонид");
        $("[data-test-id=phone] input").setValue("+79617418596");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $(byText("Встреча успешно забронирована на")).shouldBe(visible);
    }
}
