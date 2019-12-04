package demo;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import utils.CommodityTalk;

public class Demo4 {
	
	Demo4(){
		System.setProperty("webdriver.chrome.driver", "C:\\\\Program Files (x86)\\\\Google\\\\Chrome\\\\Application\\\\chromedriver.exe");
	}
	
	public static void main(String[] args) throws InterruptedException {
		//获取driver
		WebDriver driver = new ChromeDriver();
		//最大化
		driver.manage().window().maximize();
		String cn = "custom printed microfiber beach towels for beach";
		driver.get("https://galinkltd.com/?s="+cn.replace(" ", "+")+"&post_type=product");
		Thread.sleep(5000);
		((JavascriptExecutor) driver).executeScript("var aa = document.querySelector(\"#products-grid > li.column.delay-1.product.type-product.post-1590.status-publish.first.instock.product_cat-beach-towel.product_cat-quick-dry-beach-towel.has-post-thumbnail.sale.shipping-taxable.purchasable.product-type-simple.animate > div.product_thumbnail_wrapper > div.product_thumbnail.with_second_image.second_image_loaded > a > img\");if(aa != null){aa.click();}else{console.log(1)}");
		//((JavascriptExecutor) driver).executeScript("if(aa != null){aa.click();}");
	}
}
