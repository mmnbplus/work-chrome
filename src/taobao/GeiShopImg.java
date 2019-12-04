package taobao;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import Google.InsGetUserInfo;
import utils.Img9;
import utils.SeleniumUtils;

public class GeiShopImg {
	
	static SeleniumUtils utils = null;
	
	
	public static void download(int i) throws InterruptedException {
		utils.goPage("https://shop1438102315897.1688.com/page/albumdetail_163385621.htm?imageNum="+i);
		
		
		Thread.sleep(3000);
		utils.getNowWindowHandle();
		Thread.sleep(3000);
		
		for(int s=1;s<9;s++) {
			try {
				String src = utils.getAttribute(By.cssSelector("#image-list-container > div > ul > li:nth-child("+s+") > a > img"), "src", 1000);
				src = src.replaceAll(".64x64", "");
				Img9.downloadPicture(src, "C:\\Users\\Administrator\\Desktop\\img\\"+InsGetUserInfo.getRandomNum(1, 909999)+".jpg");
			}catch(Exception e) {
				System.out.println(e);
				continue;
			}
		}
		
		
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "E:\\driver\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		WebDriver driver = new ChromeDriver(options);
		utils = new SeleniumUtils(driver);		
		
		for(int i=0;i<55;i++) {
			try {
				if(i==0) {
					download(1);
				}else {
					download(i*9);
				}
			}catch(Exception e) {
				
			}
		}
	}
	
}
