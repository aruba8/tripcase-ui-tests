package tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class LoginPage {

    @BeforeMethod
    @Parameters({"url"})
    public void openUrl(String url) {
        open(url);
    }

    //just a quickest way to logout
    public void logout() {
        open("https://www.tripcase.com/logout");
    }

    @Test
    @Parameters({"email", "password", "firstName", "lastName"})
    public void loginTest(String email, String password, String firstName, String lastName) {
        $("#email").setValue(email);
        $("#password").setValue(password);
        $("#sign-in-submit").click();
        $("#subnav-name").shouldHave(text(firstName + " " + lastName));
        logout();
    }

    @Test
    @Parameters({"email", "password", "firstName", "lastName"})
    public void loginUsingRememberMeTest(String email, String password, String firstName, String lastName) {
        $("#email").setValue(email);
        $("#password").setValue(password);
        $(By.xpath("//input[@name='remember_me']")).click();
        $("#sign-in-submit").click();
        $("#subnav-name").shouldHave(text(firstName + " " + lastName));
        logout();
    }

    @Test
    public void wrongPasswordTest() {
        $("#password").setValue("qqqqq");
        $("#sign-in-submit").click();
        $(By.xpath("//form//div[contains(@class, 'flash-danger')]//li")).shouldHave(text("Incorrect email and/or password"));
    }

    @Test
    public void wrongEmailTest() {
        $("#email").setValue("qqqq@qqqq.com");
        $("#sign-in-submit").click();
        $(By.xpath("//form//div[contains(@class, 'flash-danger')]//li")).shouldHave(text("Incorrect email and/or password"));
    }

    @Test
    public void forgotPasswordTest() {
        $("#forgot-password-link").click();
        $(By.xpath("//form/h2")).shouldHave(text("Forgot your password?"));
        $("#email").shouldBe(visible);
        Assert.assertEquals(getWebDriver().getCurrentUrl(), "https://www.tripcase.com/users/forgot_password");
    }

    @Test
    public void facebookButtonTest() {
        $("#facebook").shouldBe(exist);
        $("#facebook").shouldBe(visible);
    }

    @Test
    public void signUpPresenceTest() {
        $("#sign-up-link").shouldBe(exist);
        $("#sign-up-link").shouldBe(visible);
        $("#sign-up-link").click();
        Assert.assertEquals(getWebDriver().getCurrentUrl(), "https://www.tripcase.com/users/new");
    }


}
