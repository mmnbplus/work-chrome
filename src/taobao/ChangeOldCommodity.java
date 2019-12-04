package taobao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.openqa.selenium.By;

import utils.SeleniumUtils;

public class ChangeOldCommodity {
	static SeleniumUtils utils = null;
	static String username;
	static String password;
	
	ChangeOldCommodity(){
		utils = SeleniumUtils.init("webdriver.Chrome.driver","H:\\\\chromedriver.exe");
		utils.setMaximize();
	}
	
	public static void login() {
		utils.goPage("https://passport.alibaba.com/icbu_login.htm?origin=login.alibaba.com&flag=1");
		
		Scanner sc = new Scanner(System.in); 
		List<String> talks = new ArrayList<>();
		
		//点击扫码document.querySelector("#J_Static2Quick")
		utils.clickDOM("#J_Static2Quick", 2000);
		
		
		//循环判断是否登录成功
		//utils.getDriver().getTitle()
	    System.out.println(utils.getDriver().getTitle());
	    while(true) {
	    	if(utils.getDriver().getTitle().equals("Alibaba制造商目录——供应商、制造商、出口商和进口商")) {
	    		break;
	    	}else if(utils.getDriver().getTitle().equals("Manufacturers, Suppliers, Exporters & Importers from the world's largest online B2B marketplace-Alibaba.com")) {
	    		break;
	    	}
	    }
	    System.out.println("登录成功");
	}
	
	public static void makeData(String url,int index) throws InterruptedException {
		//跳转页面
	    utils.goPage(url);
	    //获取句柄
	    utils.getNowWindowHandle();
	    Thread.sleep(5000);
	    //点击第一个商品编辑document.querySelector("#super-split-button1 > a")
	    utils.clickDOM("#super-split-button"+index+" > a", 3000);
	    
	    //获取句柄
	    utils.getNowWindowHandle();
	    for(int i=0;i<53;i++) {
	    	utils.delayJS("document.documentElement.scrollTop="+i+"00", 1000);
	    }
	    Thread.sleep(20000);
	    System.out.println("等待加载");
	    //点击源码document.querySelector("#mceu_0 > button")
	    utils.clickDOM("#mceu_0 > button", 0);
	    Thread.sleep(20000);
	    System.out.println("等待加载");
	    String value = utils.getDriver().findElement(By.xpath("//*[@id=\"mceu_46\"]/textarea")).getAttribute("value");
		//System.out.println("----:"+value);
		if(value.indexOf("Many years of experience")!= -1) {
			System.out.println("此商品不需要改");
		}else {
			//更新document.querySelector("#mceu_46 > textarea")
			//System.out.println(CommodityMBData.data1);
			String js = "document.querySelector('#mceu_46 > textarea').value = \""+CommodityMBData.data1+"\"";
			System.out.println(js);
			utils.delayJS(js, 1000);
			
			//获取当前页面url
			String nowUrl = utils.getDriver().getCurrentUrl();
			System.out.println(nowUrl);
			utils.clickDOM("#mceu_0 > button", 0);
			Thread.sleep(5000);
			//document.querySelector("#draftBtn")
			utils.clickDOM("#draftBtn", 1000);
			
			/*
			while(true) {
				utils.getDriver().get
		    }*/
			//utils.setVal("#mceu_46 > textarea", CommodityMBData.data, 0);
		}
	    
	}
	
	public static void main(String[] args) throws InterruptedException {
		ChangeOldCommodity c = new ChangeOldCommodity();
		
		login();
		
		/*
		for(int i=1;i<40;i++) {
			//try {
				onlyCurrentWindow();
				model1(1,807093678,i);
			//}catch()"https://hz-productposting.alibaba.com/product/products_manage.htm#/product/all/"+pageIndex+"-50/ydtState=&groupId="+groupId
		}*/
		
		//readMb()
		
		model2("Custom logo gym print sublimation microfiber beach towel");
	}
	
	public static void model1(int pageIndex,int groupId,int index) throws InterruptedException {
		String url = "https://hz-productposting.alibaba.com/product/products_manage.htm#/product/all/"+pageIndex+"-50/ydtState=&groupId="+groupId;
		makeData(url,index);
	}
	
	public static void model2(String name) throws InterruptedException {
		upDataImg();
		/*
		String url = "https://hz-productposting.alibaba.com/product/products_manage.htm?spm=a2700.7756200.0.0.6dce71d2X7h8OQ#/product/sketch/1-10/ydtState=&productKeyword="+name;
		makeData(url,1);
		*/
	}
	
	public static void upDataImg() throws InterruptedException {	//上传图片
		utils.goPage("https://photobank.alibaba.com/home/index.htm?spm=a2747.manage.0.0.256e71d2TufN0u#default");
		utils.getNowWindowHandle();
		
		//点击上传图片
		utils.clickDOM("#upload-action-button", 2000);
		
		Thread.sleep(20000);
		utils.getNowWindowHandle();
		
		//点击上传图片document.querySelector("#container > div > div > div.photobank-uploader-wrapper > div.photobank-uploader-dragupload > div > div.next-upload > div > div > span > input[type=file]")
		//utils.clickDOM(".next-upload > div > div > span", 2000);
		
		//设置图片
		utils.getDriver().findElement(By.xpath("//*[@id=\"container\"]/div/div/div[2]/div[2]/div/div[1]/div/div/span/input")).sendKeys("C:\\Users\\Administrator\\Desktop\\真实图片3.jpg");
		
		//点击确认上传
		utils.clickDOM("#confirm", 2000);
	}
	
	
	public static String onlyCurrentWindow(){
        String title = null;
        Set<String> windows =  utils.getDriver().getWindowHandles();
        String currentHandle = utils.getDriver().getWindowHandle();

        try {
            if (windows.size() > 1) {
                for (String window : windows) {
                	utils.getDriver().switchTo().window(window);
                    //this.sleep();
                    title = utils.getDriver().getTitle();
                    if (title.equals("Alibaba Manufacturer Directory - Suppliers, Manufacturers, Exporters & Importers")) {
                    	utils.getDriver().close();
                    }else{
                        currentHandle = window;
                    }
                }
            }
            utils.getDriver().switchTo().window(currentHandle);// 切换到任务列表
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  currentHandle;
    }
}
