package demo;

import java.util.List;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import utils.CommodityTalk;

public class Demo3 {
	
	
	public static void main(String[] args) throws InterruptedException {
		CommodityTalk.talk("11");
		
		System.setProperty("webdriver.chrome.driver", "C:\\\\Program Files (x86)\\\\Google\\\\Chrome\\\\Application\\\\chromedriver.exe");
		
		WebDriver driver = new ChromeDriver();
		driver.get("https://galinkltd.com/my-account-2/");
		Thread.sleep(3000);
		//js������ײ�
		//((JavascriptExecutor) driver).executeScript("document.documentElement.scrollTop=10000;"); 
		//�����¼spk-icon spk-icon-user-account
		//WebElement login = driver.findElement(By.className("spk-icon spk-icon-user-account"));
		//login.click();
		
		//��¼����username,password
		//((JavascriptExecutor) driver).executeScript("document.getElementById('username').value='jiahao'"); 
		//Thread.sleep(300);
		//((JavascriptExecutor) driver).executeScript("document.getElementById('password').value='Bhu89ijnmko0'");
		//Thread.sleep(300);
		//WebElement login = driver.findElement(By.name("login"));
		//login.click();
		//���site-logo
		WebElement siteLogo = driver.findElement(By.className("site-logo"));
		siteLogo.click();
		Thread.sleep(800);
		//�����ƷcssSelector
		//WebElement commodity = driver.findElement(By.xpath("img[@class='attachment-shop_catalog size-shop_catalog wp-post-image' and @src='https://i1.wp.com/galinkltd.com/wp-content/uploads/2018/11/HTB1E1u7frwrBKNjSZPcq6xpapXa0.jpg?resize=300%2C300&ssl=1']"));
		//commodity.click();
		WebElement commodity = driver.findElement(By.cssSelector(".product_thumbnail_background:nth-child(1)"));
		commodity.click();
		Thread.sleep(800);
		//reviews_tab active
		WebElement reviewsTabActive = driver.findElement(By.cssSelector(".reviews_tab"));
		reviewsTabActive.click();
		
		
		//����comment
		((JavascriptExecutor) driver).executeScript("document.getElementById('comment').value='�����Ʒ�ǳ���1111'"); 
		Thread.sleep(800);
		System.out.println(reviewsTabActive);
		//�������star-5
		((JavascriptExecutor) driver).executeScript("document.querySelector(\"#commentform > div > p > span > a.star-5\").click()"); 
		//*[@id="commentform"]/div/p/span/a[5]
		//document.querySelector("#submit")
		((JavascriptExecutor) driver).executeScript("document.querySelector(\"#submit\").click()"); 
				
		Thread.sleep(3000);
		
	}
}
