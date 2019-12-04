package Google;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.seleniumhq.jetty9.server.HttpChannelState.Action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import db.ConDB;
import utils.FileToJSON;
import utils.SeleniumUtils;


public class InsGetUserInfo {
	
	//机器Id
	static int machineID = 1;
	
	static SeleniumUtils utils = null;
	static ConDB conDB= null;
	static Actions action;
	static List<String> talkContent = new ArrayList<String>();
	static List<String> iAOList = new ArrayList<String>();
	static String key = null;
	static int timeT = 0;
	static int gailv = 0;
	static int time = 1000;
	static String inputcssselector = "";
	
	
	static String username = "";
	static String password = "";
	static int model = 1;
	
	
	static Instant instant = null;
	static Date aTimeS = null;
	
	InsGetUserInfo() throws SQLException, InterruptedException, AWTException{
		action = new Actions(utils.getDriver());
		
		//INSTalk.login(utils);
		
		//从数据库读取配置文件
		ResultSet rs1 = conDB.select("select * from machine_config where id=?", machineID);
		while(rs1.next()) {
			key = rs1.getString("searchurl");
			gailv = rs1.getInt("gailv");
			time = rs1.getInt("time");
			username = rs1.getString("username");
			password = rs1.getString("password");
			model = rs1.getInt("model");
			timeT = rs1.getInt("timeT");
			inputcssselector = rs1.getString("inputcssselector");
		}
		//从数据库读取评论内容
		ResultSet talkRs = conDB.select("select content from talk_content where isforbidden = 2");
		while(talkRs.next()) {
			talkContent.add(talkRs.getString("content"));
		}
		//从数据库读取黑名单
		ResultSet iAORs = conDB.select("select name from exclude_name");
		while(iAORs.next()) {
			iAOList.add(iAORs.getString("name"));
		}
		//登录
		utils.goPage("https://www.instagram.com/accounts/login");
		utils.getNowWindowHandle();
		Thread.sleep(10000);
		
		//#react-root > section > main > div > article > div > div:nth-child(1) > div > form > div:nth-child(2) > div > label > input
		
		Actions action = new Actions(utils.getDriver());
		
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
			
			//更新登录成功状态
			conDB.upDataOrInsert("update machine_config set state=4 where id=?", machineID);
			
		}else {	//登录失败
			
			//更新登录失败状态
			conDB.upDataOrInsert("update machine_config set state=5 where id=?", machineID);
			
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
		
		System.out.println(iAOList);
		System.out.println(talkContent);
		
	}
	
	public static int getRandomNum(int num1, int num2){
		int result = (int) (num1 + Math.random() * (num2 - num1 + 1));
		return result;
	}
	
	public static void good(int id,String name) throws SQLException, InterruptedException {
		System.out.println("-----good----");
		//延时时间
		Thread.sleep(getRandomNum(5,20)*100);
		//#react-root > section > main > div > div > article > div.eo2As > section.ltpMr.Slqrh > span.fr66n > button > span
		utils.clickDOM(".fr66n > button > span", 2000);
		
		if(id == 0) {
			return;
		}
		
		//更新数据库
		conDB.upDataOrInsert("update myinsdb set good = 1 where id = ?", id);
	}
	
	private static String talk(int id,String name) throws InterruptedException, AWTException, SQLException {
		System.out.println("----talk----");
		//int num = getRandomNum(0,talkContent.size()-1);
		
		//延时时间
		Thread.sleep(getRandomNum(50,200)*10);
		
		//点击评论
		//#react-root > section > main > div > div > article > div.eo2As > section.ltpMr.Slqrh > span._15y0l > button > span
		utils.clickDOM("._15y0l > button", 1000);
		//utils.clickDOM("._15y0l > button", 1000);
		
		Thread.sleep(getRandomNum(50,200)*10);
		
		//点击关注
		//utils.clickDOM("body > div > div > div > article > header > div > div > div > button",1000);
		
		//延时时间
		Thread.sleep(getRandomNum(100,200)*10);
		
		String content = "";
			
		Actions action = new Actions(utils.getDriver());
		content = talkContent.get(getRandomNum(0,talkContent.size()-1));
		
		//#react-root > section > main > div > div > article > div.eo2As > section.sH9wk._JgwE > div > form > textarea
		//body > div._2dDPU.vCf6V > div.zZYga > div > article > div.eo2As > section.sH9wk._JgwE > div > form > textarea
		//document.querySelector("#react-root > section > main > div > div > article > div.eo2As > section.sH9wk._JgwE > div > form > textarea")
		//输入框输入内容
		action.sendKeys(utils.getDriver().findElement(By.cssSelector(inputcssselector)), content).perform();
		
		
		Thread.sleep(getRandomNum(40,80)*10);
		
		Thread.sleep(2000);
		
		//utils.clickDOM(".sH9wk._JgwE > div > form > button", 1000);
		//输入换行发送
		utils.getDriver().findElement(By.cssSelector("body")).sendKeys(Keys.ENTER);
		
		
		Thread.sleep(getRandomNum(40,80)*10);
		
		if(id == 0) {
			return content;
		}
		
		//更新数据库
		conDB.upDataOrInsert("update myinsdb set talk = 1,talk_content = ?,talk_time = now() where id = ?", content,id);
		
		return "";
	}
	
	public static boolean iAO(String name){
		
		for(int i=0;i<iAOList.size();i++) {
			if(iAOList.get(i).equals(name)) {
				System.out.println("在黑名单中");
				return false;
			}
		}
		
		return true;
	}
	
	
	public static void getData() throws SQLException, InterruptedException, AWTException {
		
		Calendar cal = Calendar.getInstance(java.util.Locale.CHINA);
		aTimeS = cal.getTime();
		
		
		//延时一会
		Thread.sleep(3000);
		//名字
		//document.querySelector("body > div._2dDPU.vCf6V > div.zZYga > div > article > header > div.o-MQd.z8cbW > div.PQo_0.RqtMr > div.e1e1d > h2 > a")
		String url = utils.getAttribute(By.cssSelector("body > div._2dDPU.vCf6V > div.zZYga >"
						+ " div > article > header > div > div >"
						+ " div > h2 > a"), "href", 3000);
				
		String name = utils.getAttribute(By.cssSelector("body > div._2dDPU.vCf6V > div.zZYga >"
						+ " div > article > header > div > div >"
						+ " div > h2 > a"), "title", 3000);
				
		System.out.println(url+" "+name);
				
		//存储数据
		ResultSet rs = conDB.select("select id,url,good,talk from myINSdb where name = ?", name);
		
		Ins in = new Ins();
		
		//黑名单过滤
		boolean iAOs = iAO(name);
		
		
		System.out.println("----start one----");
		
		if(iAOs == true) {
			
			Thread.sleep(time);
			
			if(model == 2) {
				try {
					utils.getDriver().findElement(By.cssSelector("body")).sendKeys(Keys.ARROW_RIGHT);
					Thread.sleep(getRandomNum(40,80)*10);
				}catch(Exception e) {
					System.out.println("---can`t next-----");
				}
				return;
			}
			
			System.out.println(rs.next());
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String u = rs.getString("url");
				in.setId(id);
				in.setUrl(u);
				in.setGood(rs.getInt("good"));
				in.setTalk(rs.getInt("talk"));
			}
			
			
			if(in.getId() == 0) {	//创建数据
				System.out.println("-----new data------");
			    int iscz = getRandomNum(0,gailv);
			    
			    if(iscz == 0) {
				    int goodOrTalk = getRandomNum(0,1);
					if(goodOrTalk == 0) {
						good(0,name);
						conDB.upDataOrInsert("insert into myINSdb values (null,?,1,2,?,null,null,now(),null,null,null,?,?)",url,name,machineID,username);
	 				}else {
						//talk(0,name);
						conDB.upDataOrInsert("insert into myINSdb values (null,?,2,1,?,?,now(),now(),null,null,null,?,?)",url,name,talk(0,name),machineID,username);
					}
			    }
			}else {				//留过言
				System.out.println("----save data-----");
				System.out.println(in);
				
				int iscz = getRandomNum(0,gailv);
			    if(iscz == 0) {
					if(in.getGood() == 2 && in.getTalk() == 2) {
						int goodOrTalk = getRandomNum(0,1);
						if(goodOrTalk == 0) {
							good(in.getId(),name);
						}else {
							talk(in.getId(),name);
						}
					}else if(in.getGood() == 2){
						good(in.getId(),name);
					}else if(in.getTalk() == 2) {
						talk(in.getId(),name);
					}
			    }
			}
		}
		//延时一会
		Thread.sleep(getRandomNum(40,80)*10);
		System.out.println("----- go next ------");
		
		
		//假动作
		
		
		//点击下一个
		try {
			//document.querySelector("body > div._2dDPU.vCf6V > div.EfHg9 > div > div > a.HBoOv.coreSpriteRightPaginationArrow")
			//utils.clickDOM("body > div > div > div > div > a", 1000);
			utils.getDriver().findElement(By.cssSelector("body")).sendKeys(Keys.ARROW_RIGHT);
			Thread.sleep(getRandomNum(40,80)*10);
		}catch(Exception e) {
			System.out.println("------is end can`t next ------");
		}
	}
	
	public static void fy() throws AWTException, InterruptedException {
		Robot r=new Robot();
		for(int s =0;s<50;s++) {
			r.mouseWheel(200);
			Thread.sleep(3000);
		}
	}
	
	public static void main(String[] args) throws InterruptedException, SQLException, AWTException {
		System.out.println("-----main method start------");
		conDB = new ConDB();
		//更新开始状态
		conDB.upDataOrInsert("update machine_config s"
				+ "et state=2 where id=?", machineID);
		
		
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
		Map<String, Object> chromePrefs = new HashMap<String, Object>();
	    chromePrefs.put("profile.managed_default_content_settings.images", 2);
	    chromePrefs.put("permissions.default.stylesheet",2);
	    options.setExperimentalOption("prefs", chromePrefs);
	    
	    String [] enable = new String[1];
	    enable[0] = "enable-automation";
	    options.setExperimentalOption("excludeSwitches", enable);
	    
		WebDriver driver = new ChromeDriver(options);
		
		utils = new SeleniumUtils(driver);		
		utils.setMaximize();
		
		new InsGetUserInfo();
		
		
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
			//更新结束状态
			conDB.upDataOrInsert("update machine_config set state=3 where id=?", machineID);
			utils.getDriver().close();
		}catch(Exception e) {
			//更新错误状态
			System.out.println(e);
			conDB.upDataOrInsert("update machine_config set state=6,errorlog=? where id=?", e.toString(),machineID);
		}
		
		
		//utils.getDriver().close();
	}
}
