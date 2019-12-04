package Google;

import java.awt.AWTException;
import java.awt.RenderingHints.Key;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeOptions;

import db.ConDB;
import utils.SeleniumUtils;

class Ins{
	public int getIsopen() {
		return isopen;
	}
	public void setIsopen(int isopen) {
		this.isopen = isopen;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Ins [id=" + id + ", url=" + url + ", isopen=" + isopen + ", good=" + good + ", talk=" + talk + "]";
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	private int id;
	private String url;
	private int isopen;
	private int good;
	private int talk;
	public int getGood() {
		return good;
	}
	public void setGood(int good) {
		this.good = good;
	}
	public int getTalk() {
		return talk;
	}
	public void setTalk(int talk) {
		this.talk = talk;
	}
}

public class INSTalk {
	static SeleniumUtils utils = null;
	static ConDB conDB= null;
	static List<Ins> datas = new ArrayList<>();
	static List<String> talkContent = new ArrayList<String>();
	static int ias = 0;
	
	INSTalk(){
		ChromeOptions options = new ChromeOptions();
		//options.addArguments("--headless"); 
		
		utils = SeleniumUtils.init("webdriver.Chrome.driver","H:\\\\chromedriver.exe",options);
		utils.setMaximize();
		
		//talkContent.add("Great");
		//talkContent.add("Hi,I am Sissi,from Galink Textile Industry Co,Ltd.We are a sublimation printing factory with many products,such as beach towels,canvas bags,bath towels,shopping bags etc.");
		//talkContent.add("Hi, I am sissi from China. I have seen your ins and found that your products are quite in line with the current market, so I would like to cooperate with you.");
		//talkContent.add("Wow, your product is beautiful, so I want to cooperate with you");
	}
	
	public static int getRandomNum(int num1, int num2){
		int result = (int) (num1 + Math.random() * (num2 - num1 + 1));
		return result;
	}
	
	public static void login(SeleniumUtils utils) throws InterruptedException, AWTException {
		utils.goPage("https://www.instagram.com/accounts/login/?source=auth_switcher");
		Robot r=new Robot();
		
		//点击用户名
		
		//utils.delayJS("document.querySelector(\"#react-root > section > main > div > article > div > div:nth-child(1) > div > form > div:nth-child(2) > div > label > input\").focus();", 1000);
		
		//获取输入
		//Scanner sc = new Scanner(System.in);
	    //System.out.println("登录成功请输入11,并且把输入法设置为美式键盘");
	    //while (sc.hasNext()) {
	    //    String str = sc.next();
	    //    if(str.equals("11")) {
	        	Thread.sleep(4000);
	        	System.out.println("开始爬取");
	    //    	break;
	    //    }
	    //}
	    
	}
	
	public static void good(int id,String name) throws SQLException, InterruptedException {
		System.out.println("点赞");
		//延时时间
		Thread.sleep(getRandomNum(5,20)*100);
		//#react-root > section > main > div > div > article > div.eo2As > section.ltpMr.Slqrh > span.fr66n > button > span
		utils.clickDOM(".fr66n > button > span", 2000);
		
		//更新数据库
		conDB.upDataOrInsert("update myinsdb set good = 1 where id = ?", id);
	}
	
	private static void talk(int id,String name) throws InterruptedException, AWTException, SQLException {
		System.out.println("评论");
		int num = getRandomNum(0,talkContent.size()-1);
		
		//延时时间
		Thread.sleep(getRandomNum(5,20)*100);
		
		//点击评论
		//#react-root > section > main > div > div > article > div.eo2As > section.ltpMr.Slqrh > span._15y0l > button > span
		utils.clickDOM("._15y0l > button", 1000);
		utils.clickDOM("._15y0l > button", 1000);
		
		//延时时间
		Thread.sleep(getRandomNum(5,20)*100);
		
		Robot r=new Robot();
		//输入内容
		String content = talkContent.get(getRandomNum(0,talkContent.size()-1));
		
		for(int i=0;i<content.length();i++) {
			//r.keyPress(INSTalk.getKeyCode(content.substring(i, i+1)));
			//延时时间
			Thread.sleep(getRandomNum(5,20));
		}
		
		Thread.sleep(2000);
		
		utils.clickDOM(".sH9wk._JgwE > div > form > button", 1000);
		
		//更新数据库
		conDB.upDataOrInsert("update myinsdb set talk = 1,talk_content = ?,talk_time = ? where id = ?", content,new Date(),id);
	}
	
	public static void main(String[] args) throws SQLException, InterruptedException, AWTException {
		new INSTalk();
		
		login(utils);
		
		conDB = new ConDB();
		ResultSet rs = conDB.select("select id,url,good,talk from myinsdb where good != 1 and talk != 1");
		while(rs.next()) {
			int id = rs.getInt("id");
			String url = rs.getString("url");
			
			//如果url里面有tags跳过
			if(url.indexOf("tags") != -1) {
				continue;
			}
			
			Ins i = new Ins();
			i.setId(id);
			i.setUrl(url);
			i.setGood(rs.getInt("good"));
			i.setTalk(rs.getInt("talk"));
			
			datas.add(i);
		}
		
		//延时处理
		final long timeInterval = 60000*2;  //10分钟
        Runnable runnable = new Runnable() {  
            public void run() {  
                while (true) {  
                	
                	boolean isError = true;
                	
                    // ------- code for task to run  
                	try {
                		System.out.println(new Date());
                		System.out.println(ias);
                		isError = start();
						INSTalk.ias++;
						System.out.println(new Date());
					} catch (InterruptedException | SQLException | AWTException e1) {
						// TODO Auto-generated catch block
						//e1.printStackTrace();
						System.out.println(e1);
					}
                    // ------- ends here  
                    try {  
                    	if(isError == true) {
                    		Thread.sleep(timeInterval);  
                    	}
                    } catch (InterruptedException e) {  
                        //e.printStackTrace();  
                    }  
                }  
            }  
        };  
        Thread thread = new Thread(runnable);  
        thread.start();  
	}
	
	static boolean start() throws InterruptedException, SQLException, AWTException {
		
		//生成随机数
		int num = getRandomNum(0,datas.size()-1);
		Ins in = datas.get(num);
		
		if(in.getIsopen() == 1) {
			return false;
		}
		
		utils.goPage(in.getUrl());
		
		//如果页面找不到   找不到页面 • Instagram
		if(utils.getDriver().getTitle().indexOf("找不到页面") != -1) {
			System.out.println("找不到页面"+in.getUrl());
			return false;
		}
		
		//如果页面有 照片和视频
		if(utils.getDriver().getTitle().indexOf("照片和视频") != -1) {
			System.out.println("照片和视频"+in.getUrl());
			return false;
		}
		//#react-root > section > main > div > div > article > header > div.o-MQd > div.PQo_0 > div.e1e1d > h2 > a
        String name = utils.getVal(By.cssSelector("#react-root > section > main > div > div > article > header > div > div > div > h2 > a"), 2000);
		
		//在页面随机停留时间
		Thread.sleep(getRandomNum(0,100)*10);
		System.out.println(in+"   "+name);
		
		//去用户动态晃一晃
		
			//点击人
		
		try {
			utils.clickDOM("#react-root > section > main > div > div > article > header > div.RR-M-.mrq0Z > a", 1000);
			Thread.sleep(getRandomNum(2,10)*100);
			
			Robot r=new Robot();
			for(int s =0;s<20;s++) {
				r.mouseWheel((getRandomNum(2,200)));
				Thread.sleep(getRandomNum(2,10)*1000);
			}
			
			Thread.sleep(getRandomNum(0,10)*100);
			utils.delayJS("history.back(-1)", 1000);
			Thread.sleep(getRandomNum(2,10)*100);
			
			System.out.println("准备回去");
		}catch(Exception e) {
			System.out.println(e);
		}
		
		//good();
		//talk(in.getId(),name);
		
		
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
		}else {
			
		}
		
		//表示看过了
		in.setIsopen(1);
		return true;
	}
	
	
	public static Keys getKeyCode(String s) {
		return Keys.valueOf(s);
	}
}
