package pinpoint;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class RegistrationPage {
    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://pinpoint.idearise.co/");
        driver.manage().window().maximize();
        driver.findElement(By.xpath("//a[text()='Login']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//a[@href='/auth/register']")).click();
        driver.findElement(By.xpath("//input[@placeholder='Name']")).sendKeys("Archana Mhantati");
        driver.findElement(By.xpath("//input[@placeholder='Company']")).sendKeys("ABC");
        driver.findElement(By.xpath("//input[@placeholder='Email']")).sendKeys("mhantatiarchana@gmail.com");
        driver.findElement(By.xpath("//button[text()='Create Account']")).click();

        WebElement successfulRegisterMessage = driver.findElement(By.xpath("//h2[@class='complete-header']"));

        String actualTitle = successfulRegisterMessage.getText();
        String expectedTitle = "A temporary password has been sent to your registered email. Click";
        Assert.assertEquals(actualTitle, expectedTitle);
        driver.close();
    }

}
