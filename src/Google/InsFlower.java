package Google;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import db.ConDB;
import utils.SeleniumUtils;

public class InsFlower {
	static SeleniumUtils utils = null;
	static String username = "";
	static String password = "";
	static String key = "https://www.instagram.com/explore/tags/java";
	static Actions action = null;
	static ConDB conDB= null;
	static int timeT = 100;
	static int gailv = 1;
	static int time = 1000;
	
	
	public InsFlower() throws InterruptedException, SQLException {
		//获取数据库配置
		
		conDB = new ConDB();
		
		//从数据库读取配置文件
		ResultSet rs1 = conDB.select("select * from ins_follow where id=?", 1);
		while(rs1.next()) {
			gailv = rs1.getInt("gailv");
			time = rs1.getInt("time");
			username = rs1.getString("username");
			password = rs1.getString("password");
			timeT = rs1.getInt("count");
		}
		
		
		
		//登录
		utils.goPage("https://www.instagram.com/accounts/login");
		utils.getNowWindowHandle();
		Thread.sleep(10000);
		
		//#react-root > section > main > div > article > div > div:nth-child(1) > div > form > div:nth-child(2) > div > label > input
		
		action = new Actions(utils.getDriver());
		
		action.sendKeys(utils.getDriver().findElement(By.cssSelector("#react-root > section > main > div > "
				+ "article > div > div:nth-child(1) > div > form > div:nth-child(2) > div > label > input")), username).perform();
		Thread.sleep(1000);
		action.sendKeys(utils.getDriver().findElement(By.cssSelector("#react-root > section > main > div >"
				+ " article > div > div:nth-child(1) > div > form > div:nth-child(3) > div > label > input")), password).perform();
		Thread.sleep(1000);
		action.click(utils.getDriver().findElement(By.cssSelector("#react-root > section > main > div > article > div "
				+ "> div:nth-child(1) > div > form > div:nth-child(4) > button")));
		Thread.sleep(1000);
		
		utils.clickDOM("#react-root > section > main > div > article > div > div:nth-child(1) > div > form > div:nth-child(4) > button", 3000);
		
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
	
	public static void getData() throws SQLException, InterruptedException, AWTException {
		Thread.sleep(InsGetUserInfo.getRandomNum(40,80)*100);
		utils.getNowWindowHandle();
		Thread.sleep(InsGetUserInfo.getRandomNum(40,80)*100);
		String value = utils.getAttribute(By.cssSelector("body > div._2dDPU.vCf6V > div.zZYga > div > article > header > div.o-MQd > div.PQo_0 > div.bY2yH > button"), "innerText", 3000);
		
		System.out.println(value);
		Thread.sleep(InsGetUserInfo.getRandomNum(40,80)*100);
		
		Thread.sleep(time);
		
		
		int iscz = InsGetUserInfo.getRandomNum(0,gailv);
		
		if(value.equals("关注")) {
			//点击关注
			if(iscz == 0) {
				utils.clickDOM("body > div > div > div > article > header > div > div > div > button",1000);
			}
		}
		Thread.sleep(InsGetUserInfo.getRandomNum(40,80)*100);
		
				//点击下一个
				try {
					//document.querySelector("body > div._2dDPU.vCf6V > div.EfHg9 > div > div > a.HBoOv.coreSpriteRightPaginationArrow")
					//utils.clickDOM("body > div > div > div > div > a", 1000);
					
					//action.click(utils.getDriver().findElement(By.cssSelector("body > div._2dDPU.vCf6V > div.EfHg9 > div > div > a.HBoOv.coreSpriteRightPaginationArrow"))).perform();
					utils.clickDOM("body > div._2dDPU.vCf6V > div.EfHg9 > div > div > a.HBoOv.coreSpriteRightPaginationArrow", 1000);
					//action.keyDown(c);
					//action.keyDown(Keys.RIGHT).perform();
					//action.sendKeys(utils.getDriver().findElement(By.cssSelector("body")),"123").perform();
					utils.getDriver().findElement(By.cssSelector("body")).sendKeys(Keys.ARROW_RIGHT);
					
					Thread.sleep(InsGetUserInfo.getRandomNum(40,80)*100);
				}catch(Exception e) {
					System.out.println(e);
					System.out.println("------is end can`t next ------");
				}
	}
	
	/*
	public static void fy() throws AWTException, InterruptedException {

	}*/
	
	public static void main(String[] args) throws InterruptedException, AWTException, SQLException {
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
		options.addArguments("--no-sandbox"); 
		//Map<String, Object> chromePrefs = new HashMap<String, Object>();
	    //chromePrefs.put("profile.managed_default_content_settings.images", 2);
	    //chromePrefs.put("permissions.default.stylesheet",2);
	    //options.setExperimentalOption("prefs", chromePrefs);
		WebDriver driver = new ChromeDriver(options);
		
		utils = new SeleniumUtils(driver);	
		
		new InsFlower();
		
		utils.goPage(key);
		
		//fy();
		
		
		System.out.println("----start new get data-----");
		//点击第一个
		utils.clickDOM("#react-root > section > main > article > div:nth-child(3) >"
							+"div > div:nth-child(1) > div:nth-child(1) > a", 10);
		System.out.println(timeT);
		
		try {
			for(int h=1;h<timeT;h++) {
				getData();
			}
			utils.getDriver().close();
		}catch(Exception e) {
			//更新错误状态
			System.out.println(e);
		}
	}
}
