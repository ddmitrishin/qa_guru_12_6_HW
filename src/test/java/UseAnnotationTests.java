import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class UseAnnotationTests {


    @BeforeAll
    static void setUp() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1920x1080";
    }

    @Disabled("Bad practice")
    @DisplayName("Not parameterized test")
    @Test
    void fillWithoutParametersTest() {
        open("/text-box");
        $("#userName").setValue("Vasya");
        $("#userEmail").setValue("pochta@google.com");
        $("#permanentAddress").setValue("4632 Clair Street");
        $("#submit").click();

        $("#output").$(byText("Name:")).parent().shouldHave(text("Vasya"));
        $("#output").$(byText("Email:")).parent().shouldHave(text("pochta@google.com"));
        $("#output").$(byText("Permananet Address :")).parent().shouldHave(text("4632 Clair Street"));
    }

    @ValueSource(strings = {
            "pochta@google.com",
            "opa@mail.ru"
    })
    @DisplayName("Parameterized test Value Source")
    @ParameterizedTest(name = "Parameterized test Value Source with email {0}")
    void fillWithParametersValueSourceTest(String testData) {
        open("/text-box");
        $("#userName").setValue("Vasya");
        $("#userEmail").setValue(testData);
        $("#permanentAddress").setValue("4632 Clair Street");
        $("#submit").click();

        $("#output").$(byText("Name:")).parent().shouldHave(text("Vasya"));
        $("#output").$(byText("Email:")).parent().shouldHave(text(testData));
        $("#output").$(byText("Permananet Address :")).parent().shouldHave(text("4632 Clair Street"));
    }

    @CsvSource(value = {
            "Vasya| pochta@google.com| 4632 Clair Street, Memphis",
            "Dima| opa@mail.ru| 100 Lightning Point Drive, Savage"
    },
            delimiter = '|')
    @DisplayName("Parameterized test Csv Source")
    @ParameterizedTest(name = "Parameterized test Csv Source with name {0}, email {1}, address {2}")
    void fillWithParametersCsvSourceTest(String name, String email, String address) {
        open("/text-box");
        $("#userName").setValue(name);
        $("#userEmail").setValue(email);
        $("#permanentAddress").setValue(address);
        $("#submit").click();

        $("#output").$(byText("Name:")).parent().shouldHave(text(name));
        $("#output").$(byText("Email:")).parent().shouldHave(text(email));
        $("#output").$(byText("Permananet Address :")).parent().shouldHave(text(address));
    }


    static Stream<Arguments> fillWithParametersMethodSourceTest() {
        return Stream.of(
                Arguments.of("Vasya", "pochta@google.com", "4632 Clair Street, Memphis"),
                Arguments.of("Dima", "opa@mail.ru", "100 Lightning Point Drive, Savage")
        );
    }

    @MethodSource
    @DisplayName("Parameterized test MethodSource")
    @ParameterizedTest(name = "Parameterized test MethodSource with name {0}, email {1}, address {2}")
    void fillWithParametersMethodSourceTest(String name, String email, String address) {
        open("/text-box");
        $("#userName").setValue(name);
        $("#userEmail").setValue(email);
        $("#permanentAddress").setValue(address);
        $("#submit").click();

        $("#output").$(byText("Name:")).parent().shouldHave(text(name));
        $("#output").$(byText("Email:")).parent().shouldHave(text(email));
        $("#output").$(byText("Permananet Address :")).parent().shouldHave(text(address));
    }

}
