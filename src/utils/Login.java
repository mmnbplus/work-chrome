package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Login {
	Login(){
		System.setProperty("webdriver.chrome.driver", "C:\\\\Program Files (x86)\\\\Google\\\\Chrome\\\\Application\\\\chromedriver.exe");
	}
	public static WebDriver login() throws InterruptedException {
		WebDriver driver = new ChromeDriver();
		driver.get("https://galinkltd.com/my-account-2/");
		Thread.sleep(800);
		((JavascriptExecutor) driver).executeScript("document.getElementById('username').value='jiahao'"); 
		((JavascriptExecutor) driver).executeScript("document.getElementById('password').value='Bhu89ijnmko0'");
		WebElement login = driver.findElement(By.name("login"));
		login.click();
		return driver;
	}
}
