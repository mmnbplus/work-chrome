package taobao;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import com.csvreader.CsvWriter;
import utils.SeleniumUtils;

public class GetCommodityNameAndKeyWord {
	static SeleniumUtils utils = null;
	static String username;
	static String password;
	static List<String[]> allData;
	
	GetCommodityNameAndKeyWord(){
		ChromeOptions options = new ChromeOptions();
		//options.addArguments("--headless"); 
		utils = SeleniumUtils.init("webdriver.Chrome.driver","H:\\\\chromedriver.exe",options);
		
		utils.setMaximize();
		allData = new ArrayList<String[]>();
	}
	
	public static void login() {
		utils.goPage("https://passport.alibaba.com/icbu_login.htm?origin=login.alibaba.com&flag=1");
		
		//点击扫码document.querySelector("#J_Static2Quick")
		utils.clickDOM("#J_Static2Quick", 2000);
		
		
		//循环判断是否登录成功
		//utils.getDriver().getTitle()
	    //System.out.println(utils.getDriver().getTitle());
	    while(true) {
	    	if(utils.getDriver().getTitle().equals("Alibaba制造商目录——供应商、制造商、出口商和进口商")) {
	    		break;
	    	}else if(utils.getDriver().getTitle().equals("Manufacturers, Suppliers, Exporters & Importers from the world's largest online B2B marketplace-Alibaba.com")) {
	    		break;
	    	}
	    }
	    System.out.println("登录成功");
	}
	
	public static void getCommodityList(int pageIndex) throws InterruptedException {
		String url = "https://hz-productposting.alibaba.com/product/products_manage.htm#/product/all/"+pageIndex+"-50";
		//跳转页面
	    utils.goPage(url);
	    //获取句柄
	    utils.getNowWindowHandle();
	    
	    //String [] commodityData = getCommodity(1);
	    for(int i=1;i<51;i++) {
	    	try {
	    		GetCommodityNameAndKeyWord.onlyCurrentWindow();
	    		allData.add(getCommodity(i));
	    	}catch(Exception e) {
	    		break;
	    	}
	    }
	}
	
	public static String [] getCommodity(int commodityIndex) throws InterruptedException {
		String [] data = {};
		Thread.sleep(3000);
		 //获取句柄
	    utils.getNowWindowHandle();
	    
	    /*
	    String isUp = utils.getDriver().findElement(By.cssSelector("#ballon-container > div > div > div.posting-manage > div > div > div > "
	    		+ "div:nth-child(5) > div:nth-child("+commodityIndex+") > div > div > div:nth-child(8) > div > span")).getAttribute("innerHTML");
	    //document.querySelector("#ballon-container > div > div > div.posting-manage > div > div > div > div:nth-child(5) > div:nth-child(1) > div > div > div:nth-child(7) > div > span")
	    String t = utils.getDriver().findElement(By.cssSelector("#ballon-container > div > div > div.posting-manage > div > div > div > "
	    		+ "div:nth-child(5) > div:nth-child("+commodityIndex+") > div > div > div:nth-child(7) > div > span")).getAttribute("innerHTML");
	    //document.querySelector("#ballon-container > div > div > div.posting-manage > div > div > div > div:nth-child(5) > div:nth-child(1) > div > div > div:nth-child(5) > span")
	    String pepol = utils.getDriver().findElement(By.cssSelector("#ballon-container > div > div > div.posting-manage > div > div >"
	    		+ " div > div:nth-child(5) > div:nth-child("+commodityIndex+") > div > div > div:nth-child(5) > span")).getAttribute("innerHTML");
	    */
	    
		//点击第一个商品编辑document.querySelector("#super-split-button1 > a")
	    utils.clickDOM("#super-split-button"+commodityIndex+" > a", 6000);
	    Thread.sleep(3000);
	    //获取句柄
	    utils.getNowWindowHandle();
	    
	    String name = utils.getDriver().findElement(By.id("productTitle")).getAttribute("value");
	    String key1 = utils.getDriver().findElement(By.cssSelector(".help-padding > div > ul > li:nth-child(1) > span>input")).getAttribute("value");
	    
	    String key2 = utils.getDriver().findElement(By.cssSelector(".help-padding > div > ul > li:nth-child(2) > span>input")).getAttribute("value");
	    
	    String key3 = utils.getDriver().findElement(By.cssSelector(".help-padding > div > ul > li:nth-child(3) > span>input")).getAttribute("value");
	    
	    System.out.println(name + ":"+key1+","+key2+","+key3);
	    //System.out.println(name + ":"+key1+","+key2+","+key3+","+isUp+","+t+","+pepol);
	    
	    /*
	    data = {
	    		name,key1,key2,key3
	    };*/
	    data[0] = name;
	    data[1] = key1;
	    data[2] = key2;
	    data[3] = key3;
	    //data[4] = isUp;
	    //data[5] = t;
	    //data[6] = pepol;
	    
	    return data;
	}
	
	public static void writeCSV(String filePath,List<String[]> data) throws IOException{
		
        CsvWriter csvWriter = new CsvWriter(filePath,',', Charset.forName("GBK"));
            
        for(int i=0;i<data.size();i++) {
            csvWriter.writeRecord(data.get(i));
        }
        csvWriter.close();

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
	
	public static void main(String[] args) throws InterruptedException, IOException {
		GetCommodityNameAndKeyWord c = new GetCommodityNameAndKeyWord();
		
		//登录
		login();
		
		for(int i=1;i<10;i++) {
	    	try {
	    		getCommodityList(i);
	    	}catch(Exception e) {
	    		break;
	    	}
	    }
		
		writeCSV("C:\\Users\\Administrator\\Desktop\\connodityKey.csv",allData);
	}
}
