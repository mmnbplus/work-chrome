package taobao;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;

import com.csvreader.CsvWriter;

import db.ConDB;
import utils.SeleniumUtils;

public class Get1688Commodity {
	static SeleniumUtils utils = null;
	static List<String []> datas= null;
	static ConDB conDB= null;
	static Map<String,String> typeData = new HashMap<String,String>();
	
	Get1688Commodity() throws SQLException{
		ChromeOptions options = new ChromeOptions();
		//options.addArguments("--headless"); 
		utils = SeleniumUtils.init("webdriver.Chrome.driver","H:\\\\chromedriver.exe",options);
		utils.setMaximize();
		/*
		datas = new ArrayList<String[]>();
		String [] top = {
				"suk","commodityName","commodityUrl","outerHTML","img1","img2","img3","img4","img5","const"
		};
		datas.add(top);
		*/
		//连接数据库
		conDB = new ConDB();
		
		typeData.put("抱枕", "5");
		typeData.put("沙滩巾", "6");
		typeData.put("瑜伽铺巾", "7");
		typeData.put("毛毯", "43");
		typeData.put("抱枕", "5");
		typeData.put("运动巾", "44");
		typeData.put("冷感毛巾", "45");
		typeData.put("连帽斗篷", "46");
		typeData.put("购物袋", "47");
		typeData.put("帆布袋", "48");
	}
	
	public static void main(String[] args) throws InterruptedException, IOException, SQLException {
		new Get1688Commodity();
		
		//登录1688
		login();
		
		for(int i=1;i<6;i++) {
			try {
				System.out.println("--------------正在执行第"+i+"页-------------");
				Get1688Commodity.commodityList(i);
				System.out.println("--------------正在执行第"+i+"页完成----------");
			}catch(Exception e) {
				return;
			}
		}
		
		/*
		System.out.println("爬取完成,正在写入文件"+"C:\\Users\\Administrator\\Desktop\\connodityDBFor1688.csv");
		System.out.println(datas);
		writeCSV("C:\\Users\\Administrator\\Desktop\\connodityDBFor1688.csv",datas);
		*/
	}
	
	public static void login() throws InterruptedException {
		utils.goPage("https://login.1688.com/member/signin.htm");
		
		//循环判断是否登录成功
		//utils.getDriver().getTitle()
	    System.out.println(utils.getDriver().getTitle());
	    while(true) {
	    	//获取句柄
			Thread.sleep(4000);
			utils.getNowWindowHandle();
			Thread.sleep(4000);
	    	if(utils.getDriver().getTitle().equals("1688-卖家工作台")) {
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
	
	public static void commodityList(int pageindex) throws InterruptedException, SQLException {
		
		//17
		for(int i=1;i<17;i++) {
			try {
				onlyCurrentWindow();
				System.out.println("正在执行第"+i+"个");
				utils.goPage("https://galink.1688.com/page/offerlist.htm?pageNum="+pageindex);
			
				String [] data = Get1688Commodity.commodity(i);
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
		
		/*
		for(int i=0;i<1000;i++) {
        	utils.delayJS("document.documentElement.scrollTop="+i+"0;", 3000);
        	if(i==400 || i==799 ||i==549 ||i==200 || i==899) {
        		Thread.sleep(3000);
        	}
        }*/
		
		for(int i=0;i<400;i++) {
        	utils.delayJS("document.documentElement.scrollTop="+i+"0;", 3000);
        	if(i==200 || i==399) {
        		Thread.sleep(3000);
        	}
        }
		
		//获取详情页document.querySelector(".offerdetail_w1190_description > div")
		String outerHTML = utils.getDriver().findElement(By.cssSelector(".offerdetail_w1190_description > div")).getAttribute("outerHTML");
		
		//设置suk
		String url = utils.getCurrentUrl();
		String suk = url.replace("https://detail.1688.com/offer/", "").replace(".html", "");
		
		/*
		 * 保存图片
		for(int s=1;s<6;s++) {
			dataList.add(utils.getDriver().findElement(
					By.cssSelector("#dt-tab > div > ul > li:nth-child("+s+") > div > a > img"))
					.getAttribute("src")
					.replaceAll(".60x60", "")
			);
			
		}
		*/
		
		/*
		dataList.add(suk);
		dataList.add(commodityName);
		dataList.add(commodityUrl);
		dataList.add(outerHTML);
		*/
		
		//设置图片document.querySelector("#dt-tab > div > ul > li:nth-child(1) > div > a > img")
		
		/*
		for(int s=1;s<6;s++) {
			dataList.add(utils.getDriver().findElement(By.cssSelector("#dt-tab > div > ul > li:nth-child("+s+") > div > a > img")).getAttribute("src").replaceAll(".60x60", ""));
		}
		*/
		//设置价格document.querySelector("#mod-detail-price > div > table > tbody > tr.price > td.ladder-1-1 > div > span.value")
		//     document.querySelector("#mod-detail-price > div > table > tbody > tr.price > td.ladder-1-1 > span.value.price-length-5")
		//dataList.add(utils.getDriver().findElement(By.cssSelector(".price")).getAttribute("outerHTML"));
		
		//爬取商品属性
		//document.querySelector(".obj-content > table > tbody > tr:nth-child(1) > td:nth-child(1)")
		//document.querySelector(".obj-content > table > tbody > tr:nth-child(1) > td:nth-child(2)")
		
		/*
			for(int t = 1;t<10;t++) {
				for(int s = 1;s<7;s++) {
					try {
						dataList.add(utils.getDriver().findElement(By.cssSelector(".obj-content > table > tbody > "
								+ "tr:nth-child("+t+") > td:nth-child("+s+")")).getAttribute("innerText"));
					}catch(Exception e) {
						continue;
					}
				}
			}
		*/
		
		//获取商品属性document.querySelector(".obj-content > table > tbody > tr:nth-child(1) > td:nth-child(1)")
		//document.querySelector(".obj-content > table > tbody > tr:nth-child(1) > td:nth-child(2)")
		/*
		for(int i=1;i<4;i++) {
			data[data.length] = utils.getDriver().findElement(By.cssSelector(".offerdetail_w1190_description > div")).getAttribute("");
		}*/
		
			//dataList.toArray();
		/*
		int size=dataList.size();  
		String[] array = (String[])dataList.toArray(new String[size]);  
		*/
		
		
		//获取id
		//1:清楚https://detail.1688.com/offer/和.html
		String id = commodityUrl.replaceAll("https://detail.1688.com/offer/", "").replace(".html", "");
		BigInteger commdoityDBId= (BigInteger)BigInteger.valueOf(Long.parseLong(id));
		
		ResultSet rs = conDB.select("select id from galinkcn_product where 1688_id = ?", id);
		
		System.out.println(commdoityDBId);
		
		/**
		 * 获取商品属性
		 */
		List<String> commodityOptionList = new ArrayList<String>();
		for(int t = 1;t<10;t++) {
			for(int s = 1;s<7;s++) {
				try {
					commodityOptionList.add(utils.getDriver().findElement(By.cssSelector(".obj-content > table > tbody > "
							+ "tr:nth-child("+t+") > td:nth-child("+s+")")).getAttribute("innerText"));
				}catch(Exception e) {
					//System.err.println(e);
					continue;
				}
			}
		}
		
		System.out.println(commodityOptionList);
		
		String property1 = commodityOptionList.get(0);
		String property2 = commodityOptionList.get(1);
		String property3 = commodityOptionList.get(2);
		String property4 = "";
		String typeId = "1";
		
		//设置商品类型typeData
		Iterator<?> iter = typeData.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String) entry.getKey();
			String val = (String) entry.getValue();
			
			if(commodityName.indexOf(key) != -1) {
				typeId = val;
			}
		}
		
		/*
		//设置详情页--保留图片
		List<ImgDao> imgList = GetAllCommodity.getImgStr(outerHTML);
		//组成html
		String html = "";
		for(int i=0;i<4;i++) {
			html+="<img src='"+imgList.get(i).getSrc()+"' style='width:100%;margin-top:20px;' alt='"+imgList.get(i).getAlt()+"' />";
		}*/
		
		//设置详情页--只有表格
		String html = "";
		html = outerHTML;
		
		//查找</table>
		String back= html.substring(html.indexOf("<table"),html.indexOf("</table>")+8);
		//&nbsp;
		back = back.replaceAll("&nbsp;", "");
		back = back.replaceAll("<br>", "");
		//width: 772.0px;
		back = back.replaceAll("width", "");
		//height: 369.0px;
		back = back.replaceAll("height", "");
		
		
		System.out.println(back);
		
		
		/*
		id	int	
		name	varchar	
		title	varchar	
		url	varchar	
		keywords	varchar	
		description	varchar	
		contents	text	
		pid	int	
		bid	int	
		photo	varchar	
		thumb	varchar	
		property1	varchar	
		property2	varchar	
		property3	varchar	
		property4	varchar	
		sort	int	11	0	
		featured	tinyint	
	*/
		String title = commodityName;
		String commodityDBUrl = "commodity-" + commdoityDBId;
		String keywords = title;
		String description = title;
		String contents = back;
		String photo = commdoityDBId+"--1.jpg,"+commdoityDBId+"--2.jpg,"+commdoityDBId+"--3.jpg,"+commdoityDBId+"--4.jpg,"+commdoityDBId+"--5.jpg";
		String thumb = commdoityDBId+"--1.jpg";
		
		if(rs.next()) {	//如果存在就更新
			System.out.println("db "+rs.getInt("id"));
			conDB.upDataOrInsert("UPDATE galinkcn_product SET name = ?,"
					+ "title=?,url=?,keywords=?,description=?,contents=?,pid=?,bid=?,photo=?,"
					+ "thumb=?,property1=?,property2=?,property3=?,property4=?,sort=?,featured=? WHERE 1688_id = ?", 
					commodityName, title, commodityDBUrl, keywords, description, contents,typeId,1,photo
					,thumb,property1,property2,property3,property4,2,2,commdoityDBId);
		}else { //如果不存在就创建
			conDB.upDataOrInsert("insert into galinkcn_product values (null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", 
					commodityName, title, commodityDBUrl, keywords, description, contents,typeId,1,photo
					,thumb,property1,property2,property3,property4,2,2,commdoityDBId);
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
