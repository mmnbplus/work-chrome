package Google;

import java.awt.AWTException;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;

import utils.SeleniumUtils;

public class InsNoFlower {
	static SeleniumUtils utils = null;
	static String username = "wsmmhahaha";
	static String password = "wsmmhahaha123";
	static Actions action = null;
	static int timeT = 100;
	static List<String> a = null;
	
	public InsNoFlower() throws InterruptedException {
		//登录
				utils.goPage("https://www.instagram.com/accounts/login");
				utils.getNowWindowHandle();
				Thread.sleep(10000);
				
				//#react-root > section > main > div > article > div > div:nth-child(1) > div > form > div:nth-child(2) > div > label > input
				
				action = new Actions(utils.getDriver());
				
				action.sendKeys(utils.getDriver().findElement(By.cssSelector("#react-root > section > main > article > div > div > div > form > div:nth-child(4) > div > label > input")), username).perform();
				Thread.sleep(1000);
				action.sendKeys(utils.getDriver().findElement(By.cssSelector("#react-root > section > main > article > div > div > div > form > div:nth-child(5) > div > label > input")), password).perform();
				Thread.sleep(1000);
				action.click(utils.getDriver().findElement(By.cssSelector("#react-root > section > main > article > div > div > div > form > div:nth-child(7) > button")));
				Thread.sleep(1000);
				
				//utils.clickDOM("#react-root > section > main > div > article > div > div:nth-child(1) > div > form > div:nth-child(4) > button", 3000);
				
				Thread.sleep(1000);
				utils.getNowWindowHandle();
				Thread.sleep(1000);
				
				
				if(utils.getDriver().getPageSource().indexOf("打开通知")!=-1 ||
						utils.getDriver().getPageSource().indexOf("Turn")!=-1) {//登录成功
					
				}else {	//登录失败
					
					
					Scanner in=new Scanner(System.in);
					System.out.println("-------input 11 login ins-------");
					int number = 0;
					while(true) {
						number=in.nextInt();
						if(number == 11) {
							break;
						}
					}
				}
	}
	
	public static void main(String[] args) throws InterruptedException, AWTException {
		a = new ArrayList<>();
		String os = System.getProperty("os.name"); 
		System.out.println(os);  
		if(os.indexOf("Linux")!=-1) {
			System.setProperty("webdriver.chrome.driver", "chromedriver");
		}else {
			//测试环境
			File file12 = new File("E:\\driver\\chromedriver.exe");
			if (!file12.exists()) {
				//切换为window生产环境
	            System.out.println("测试文件不存在，切换生产环境");
	            System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
	        }else {
	        	System.setProperty("webdriver.chrome.driver", "E:\\driver\\chromedriver.exe");
	        }
		}
		
		System.out.println(System.getProperty("webdriver.chrome.driver"));
		
		ChromeOptions options = new ChromeOptions();
		
		/*
		//在这里选择模式判断是否是headless执行
		Scanner in=new Scanner(System.in);
		System.out.println("-------input 1:no headless 2:headless-------");
		int headless=in.nextInt();
		if(headless == 2) {
			options.addArguments("--headless"); 
		}*/
		//options.addArguments("--no-sandbox"); 
		//options.addArguments("--user-agent=iphone 6 plus");
		//Map<String, Object> chromePrefs = new HashMap<String, Object>();
	    //chromePrefs.put("profile.managed_default_content_settings.images", 2);
	    //chromePrefs.put("permissions.default.stylesheet",2);
		
        
        //driver=new MobileChromeDriver(capabilities);
		
		
		options.addArguments("Accept=text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		options.addArguments("Accept-Encoding=gzip, deflate, sdch");
		options.addArguments("Accept-Language=zh-CN,zh;q=0.8");
		options.addArguments("Connection=keep-alive");
		options.addArguments("Host=activityunion-marketing.meituan.com");
		options.addArguments("Upgrade-Insecure-Requests=1");
		options.addArguments("User-Agent=Mozilla/5.0 (iPhone; CPU iPhone OS 8_0 like Mac OS X) AppleWebKit/600.1.3 (KHTML, like Gecko) Version/8.0 Mobile/12A4345d Safari/600.1.4");
		
		
		
		WebDriver driver = new ChromeDriver(options);
		
		utils = new SeleniumUtils(driver);
		utils.getDriver().manage().window().setSize(new Dimension(400,800));
		
		new InsNoFlower();
		
		utils.goPage("https://www.instagram.com/"+username+"/followers/");
		Thread.sleep(1000);
		utils.getNowWindowHandle();
		Thread.sleep(3000);
		
		//点击粉丝
		//action.click(utils.getDriver().findElement(By.cssSelector("#react-root > section > main > div > header > section > ul > li:nth-child(2) > a")));
		
		//action = new Actions(utils.getDriver());
        //action.moveByOffset(241, 292).perform();
        //Thread.sleep(3000);
        //action.click().perform();
		
		Scanner in=new Scanner(System.in);
		System.out.println("-------input 11 login ins-------");
		int number = 0;
		number=in.nextInt();
		if(number == 11) {
			//#f376a2598943944 > div > div > a
			////*[@id="f376a2598943944"]/div/div/a
		}
	}
}
