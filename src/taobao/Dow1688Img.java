package taobao;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;

import com.csvreader.CsvWriter;

import db.ConDB;
import utils.Img9;
import utils.SeleniumUtils;

public class Dow1688Img {
	static SeleniumUtils utils = null;
	static List<String []> datas= null;
	static ConDB conDB= null;
	
	Dow1688Img() throws SQLException{
		ChromeOptions options = new ChromeOptions();
		//options.addArguments("--headless"); 
		utils = SeleniumUtils.init("webdriver.Chrome.driver","H:\\\\chromedriver.exe",options);
		utils.setMaximize();
		//连接数据库
		conDB = new ConDB();
	}
	
	public static void main(String[] args) throws InterruptedException, IOException, SQLException {
		new Dow1688Img();
		
		//登录1688
		login();
		
		for(int i=1;i<10;i++) {
			try {
				System.out.println("--------------正在执行第"+i+"页-------------");
				Dow1688Img.commodityList(i);
				System.out.println("--------------正在执行第"+i+"页完成----------");
			}catch(Exception e) {
				return;
			}
		}
		
	}
	
	public static void login() {
		utils.goPage("https://login.1688.com/member/signin.htm");
		
		//循环判断是否登录成功
		//utils.getDriver().getTitle()
	    System.out.println(utils.getDriver().getTitle());
	    while(true) {
	    	if(utils.getDriver().getTitle().equals("1688-买家工作台")) {
	    		break;
	    	}
	    }
	    System.out.println("登录成功");
	}
	
	public static void writeCSV(String filePath,List<String[]> data) throws IOException{
		
        CsvWriter csvWriter = new CsvWriter(filePath,',', Charset.forName("GBK"));
            
        for(int i=0;i<data.size();i++) {
            csvWriter.writeRecord(data.get(i));
        }
        csvWriter.close();

    }
	
	public static void commodityList(int pageindex) throws InterruptedException {
		
		//17
		for(int i=1;i<17;i++) {
			try {
				onlyCurrentWindow();
				System.out.println("正在执行第"+i+"个");
				utils.goPage("https://galink.1688.com/page/offerlist.htm?pageNum="+pageindex);
			
				String [] data = Dow1688Img.commodity(i);
				datas.add(data);
				System.out.println("第"+i+"个完成");
			}catch(Exception e) {
				System.err.println(e);
				continue;
			}
		}
	}
	
	public static String [] commodity(int commodityIndex) throws InterruptedException, SQLException {
		utils.delayJS("document.documentElement.scrollTop=1500;", 3000);
		Thread.sleep(4000);
		utils.clickDOM("#search-bar > div.wp-offerlist-windows.should-get-async-price > "
				+ "div > div > div > ul > li:nth-child("+commodityIndex+") > div.image > a", 2000);
		
		//获取句柄
		Thread.sleep(4000);
		utils.getNowWindowHandle();
		Thread.sleep(4000);
		
		//List<String> dataList = new ArrayList<String>();
		
		//点击取消登录document.querySelector("#sufei-dialog-close")
		try {
			utils.clickDOM("#sufei-dialog-close", 2000);
		}catch(Exception e) {
			
		}
		
		
		String commodityName = utils.getVal(By.className("d-title"), 1000);//名字
		String commodityUrl = utils.getDriver().getCurrentUrl();//地址
		
		System.out.println(commodityName+"|"+commodityUrl);

		
		//获取详情页document.querySelector(".offerdetail_w1190_description > div")
		String outerHTML = utils.getDriver().findElement(By.cssSelector(".offerdetail_w1190_description > div")).getAttribute("outerHTML");
		
		Thread.sleep(3000);
		
		//设置suk
		String url = utils.getCurrentUrl();
		String suk = url.replace("https://detail.1688.com/offer/", "").replace(".html", "");
		

		String id = commodityUrl.replaceAll("https://detail.1688.com/offer/", "").replace(".html", "");
		BigInteger commdoityDBId= (BigInteger)BigInteger.valueOf(Long.parseLong(id));
		
		/*
		 * 下载图片
		 */
		for(int s=1;s<6;s++) {
			String imgUrl = utils.getDriver().findElement(
					By.cssSelector("#dt-tab > div > ul > li:nth-child("+s+") > div > a > img"))
					.getAttribute("src")
					.replaceAll(".60x60", "");
			Img9.downloadPicture(imgUrl, "E:\\1688Img\\"+commdoityDBId+"--"+s+".jpg");
		}	
		
		return null;
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
                    //印花沙滩巾_涤棉印花沙滩巾儿童印花斗篷浴巾沙滩巾花型100件可定制 - 阿里巴巴
                    //if (title.equals("Top Sale Products,Drawstring Bag,Blankets direct from CN")) {
                    //System.out.println(title);
                    if(title.equals("沙滩巾;运动毛巾;毛巾定制;冷感毛巾;运动巾;超细纤维毛巾;超细纤维浴巾;圆形沙滩巾;珊瑚绒浴巾;速干毛巾;防晒丝巾沙滩巾;超细纤维沙滩巾;双面绒毛巾;抱枕;胶印热升华")) {
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

