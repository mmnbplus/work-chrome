package utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;


import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class CommodityTalk {
	private static String commodityname = null;
	
	CommodityTalk(){
		System.setProperty("webdriver.chrome.driver", "C:\\\\Program Files (x86)\\\\Google\\\\Chrome\\\\Application\\\\chromedriver.exe");
	}
	
	
	public static List<String> getUserTalk() {
		List<String> talk = new ArrayList<>();
		System.out.println("��������Ʒ����:");
		Scanner sc= new Scanner(System.in);
		String name = sc.nextLine();//��ȡ������Ϣ
		commodityname = name;
		
		while(true) {
			System.out.println("��������Ʒ����(��+��ȫ������):");
			String st = sc.nextLine();//��ȡ������Ϣ
			if(st.equals("+")) {
				break;
			}
			talk.add(st);
		}
		return talk;
	}
	
	public static void talk(String commodityName) throws InterruptedException {
		List<String> s = getUserTalk();
		
		for(int i=0;i<s.size();i++) {
			//urlʾ��
			//https://galinkltd.com/?s=custom+printed+microfiber+beach+towels+for+beach&post_type=product
			//String cn = "custom printed microfiber beach towels for beach";
			String cn = commodityname;
			
			//��ȡdriver
			WebDriver driver = new ChromeDriver();
			//���
			driver.manage().window().maximize();
			driver.get("https://galinkltd.com/?s="+cn.replace(" ", "+")+"&post_type=product");
			Thread.sleep(400);
			
			//�����Ʒ
			//[[ChromeDriver: chrome on XP (a24712f0ee5b53dba15ade50afb24f17)] -> xpath: //*[@id="products-grid"]/li[1]/div[1]/div[1]/a/img]
			WebElement commodityImg = driver.findElement(By.xpath("//*[@id=\"products-grid\"]/li[1]/div[1]/div[1]/a/img"));
			System.out.println(commodityImg);
			
			
			commodityImg.click();
				/*
				for(int i1=0;i1<1000;i1++) {
					((JavascriptExecutor) driver).executeScript("window.scrollTo(document.body.scrollHeight,"+(i1*100)+")");
					WebElement reviewsTabActive = driver.findElement(By.cssSelector(".reviews_tab"));
					reviewsTabActive.click();
				}
				*/
			((JavascriptExecutor) driver).executeScript("window.scrollTo(document.body.scrollHeight,1000)");
			WebElement reviewsTabActive = driver.findElement(By.cssSelector(".reviews_tab"));
			reviewsTabActive.click();
				//try {
				//Object aa = ((JavascriptExecutor) driver).executeScript("window.scrollTo(document.body.scrollHeight,document.body.scrollHeight)");
				String ss = "";
				
				//}catch() {
					
				//}
				
				
				//System.out.println(reviewsTabActive);
				//reviewsTabActive.click();
				
			//((JavascriptExecutor) driver).executeScript("window.scrollTo(document.body.scrollHeight,1000)");
			//reviews_tab active
			//WebElement reviewsTabActive = driver.findElement(By.cssSelector(".reviews_tab"));
			
			//((JavascriptExecutor) driver).executeScript("window.scrollTo(document.body.scrollHeight,1300)");
			
			
			
			
			
			
			
			//����comment
			((JavascriptExecutor) driver).executeScript("document.getElementById('comment').value='"+s.get(i)+"'"); 
			
			//��������
			((JavascriptExecutor) driver).executeScript("document.getElementById('email').value='"+EmailAndName.getEmail(10, 20)+"'"); 
			//��������
			//((JavascriptExecutor) driver).executeScript("document.getElementById('author').value='"+EmailAndName.test()+"'"); 
			//�������star-5
			Object aaa = ((JavascriptExecutor) driver).executeScript("document.querySelector(\"#commentform > div > p > span > a.star-5\").click()"); 
			//Object aaa = ((JavascriptExecutor) driver).executeScript("document.querySelector(\"#commentform > div > p > span a.star-5\").click()"); 
			System.out.println(aaa);
			//*[@id="commentform"]/div/p/span/a[5]
			((JavascriptExecutor) driver).executeScript("window.scrollTo(document.body.scrollHeight,1600)");
			
			/*
			//�ύdocument.querySelector("#submit")
			((JavascriptExecutor) driver).executeScript("document.querySelector(\"#submit\").click()"); 
			*/
		}
	}
}
