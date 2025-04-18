package saucedmoProject.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

@Listeners(ExtentReportManager.class)
public class LoginTest extends BaseTest{


    @DataProvider(name = "loginData")
    public Object[][] loginDataProvider() {
        return ExcelUtils.getLoginData("F:\\Project\\src\\main\\java\\saucedmoProject\\Data.xlsx", "Sheet1");
    }

    @Test(priority = 1, dataProvider = "loginData")
    public void verifyLogin(String username, String password) {
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();

        // Assertion: Check if the homepage loaded
        boolean isDisplayed = driver.findElement(By.className("product_label")).isDisplayed();
        Assert.assertTrue(isDisplayed, "Homepage not loaded for: " + username);
    }

    @Test(priority = 2)
    public void testProductFilteringLowToHigh() {
        verifyLogin("standard_user", "secret_sauce");

        Select sortDropdown = new Select(driver.findElement(By.className("product_sort_container")));
        sortDropdown.selectByValue("lohi");

        List<WebElement> products = driver.findElements(By.className("inventory_item_name"));
        System.out.println("Products sorted by Low to High:");
        for (WebElement product : products) {
            System.out.println(product.getText());
        }
    }

    @Test(priority = 3)
    public void testAddToCart() throws InterruptedException {
        verifyLogin("standard_user", "secret_sauce");

        Select sortDropdown = new Select(driver.findElement(By.className("product_sort_container")));
        sortDropdown.selectByValue("lohi");

        List<WebElement> productNames = driver.findElements(By.xpath("//div[@class='inventory_item_name']"));
        String actualProductName = productNames.get(0).getText();

        List<WebElement> addToCartButtons = driver.findElements(By.xpath("//button[text()='ADD TO CART']"));
        addToCartButtons.get(0).click();

        WebElement cartBadge1 = driver.findElement(By.xpath("//span[@class='fa-layers-counter shopping_cart_badge']"));

        String actualCartBadgeText = cartBadge1.getText();
        String expected = "1";
        Assert.assertEquals(actualCartBadgeText, expected);
        WebElement cartBadge = driver.findElement(By.xpath("//div[@id='shopping_cart_container']"));
        cartBadge.click();

        driver.findElement(By.xpath("//a[@href=\"./cart.html\"]")).click();

        String expectedProductName = "Sauce Labs Onesie";

        Assert.assertEquals(actualProductName, expectedProductName);

    }

    @Test(priority = 4)
    public void testCheckoutProcess() {
        verifyLogin("standard_user", "secret_sauce");

        List<WebElement> addToCartButtons = driver.findElements(By.xpath("//button[text()='ADD TO CART']"));
        addToCartButtons.get(0).click();
        driver.findElement(By.xpath("//a[@href=\"./cart.html\"]")).click();
        driver.findElement(By.xpath("//a[contains(text(),'CHECKOUT')]")).click();

        driver.findElement(By.id("first-name")).sendKeys("Test");
        driver.findElement(By.id("last-name")).sendKeys("User");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        driver.findElement(By.xpath("//input[@value='CONTINUE']")).click();
        driver.findElement(By.xpath("//a[contains(text(),'FINISH')]")).click();

        String confirmation = driver.findElement(By.className("complete-header")).getText();
        Assert.assertTrue(confirmation.contains("THANK YOU"), "Checkout not successful.");
    }

}



