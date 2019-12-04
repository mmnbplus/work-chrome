package taobao;

import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import com.csvreader.CsvWriter;
import utils.Img9;
import utils.SeleniumUtils;

public class GetAllCommodity {
	static SeleniumUtils utils = null;
	
	static String all = "<ul><li><span style='font-size: 14px;'>Many years of experience in operating <span style='color: #ff0000;'><strong>Amazon</strong></span> and serving as <strong><span style='color: #ff0000;'>Amazon's supplier</span></strong>. We can provide you with consulting services that meet Amazon's requirements in packaging,labeling, transportation and other aspects.</span></li><li><span style='font-size: 14px;'> Provide<strong><span style='color: #ff0000;'> customized services</span></strong>, including <strong><span style='color: #ff0000;'>size, style, pattern, logo, labeling</span></strong> and <strong><span style='color: #ff0000;'>packaging</span></strong>.</span></li><li><span style='font-size: 14px;'> Samples will be made within 48 hours after receiving your design, and <strong><span style='color: #ff0000;'>free samples</span> </strong>can be provided for your reference.</span></li><li><span style='font-size: 14px;'><strong><span style='color: #ff0000;'> Eco-friendly</span></strong>, recycling and renewable, achieve any fabric you want. </span></li><li><span style='font-size: 14px;'>Support <strong><span style='color: #ff0000;'>affiliate marketing</span></strong>, contact customer service for details.</span></li></ul>";
	static String all2 ="<ul><li><span style='font-size: 14px;'>Many years of experience in operating <span style='color: #ff0000;'><strong>Amazon</strong></span> and serving as <strong><span style='color: #ff0000;'>Amazon's supplier</span></strong>. We can provide you with consulting services that meet Amazon's requirements in packaging,labeling, transportation and other aspects.</span></li><li><span style='font-size: 14px;'> Provide<strong><span style='color: #ff0000;'> customized services</span></strong>, including <strong><span style='color: #ff0000;'>size, style, pattern, logo, labeling</span></strong> and <strong><span style='color: #ff0000;'>packaging</span></strong>.</span></li><li><span style='font-size: 14px;'> Samples will be made within 48 hours after receiving your design, and <strong><span style='color: #ff0000;'>free samples</span> </strong>can be provided for your reference.</span></li><li><span style='font-size: 14px;'><strong><span style='color: #ff0000;'> Eco-friendly</span></strong>, recycling and renewable, achieve any fabric you want.</span></li><li><span style='font-size: 14px;'>Support <strong><span style='color: #ff0000;'>affiliate marketing</span></strong>, contact customer service for details.</span></li></ul>'>";
	static String all3 ="<ul><li><span style='font-size: 14px;'>Many years of experience in operating <span style='color: #ff0000;'><strong>Amazon</strong></span> and serving as <strong><span style='color: #ff0000;'>Amazon's supplier</span></strong>. We can provide you with consulting services that meet Amazon's requirements in packaging,labeling, transportation and other aspects.</span></li><li><span style='font-size: 14px;'> Provide<strong><span style='color: #ff0000;'> customized services</span></strong>, including <strong><span style='color: #ff0000;'>size, style, pattern, logo, labeling</span></strong> and <strong><span style='color: #ff0000;'>packaging</span></strong>.</span></li><li><span style='font-size: 14px;'> Samples will be made within 48 hours after receiving your design, and <strong><span style='color: #ff0000;'>free samples</span> </strong>can be provided for your reference.</span></li><li><span style='font-size: 14px;'><strong><span style='color: #ff0000;'> Eco-friendly</span></strong>, recycling and renewable, achieve any fabric you want. Support <strong><span style='color: #ff0000;'>affiliate marketing</span></strong>, contact customer service for details.</span></li></ul>";
	static String all4 ="<ul><li><span style='font-size: 14px;'>Many years of experience in operating <span style='color: #ff0000;'><strong>Amazon</strong></span> and serving as <strong><span style='color: #ff0000;'>Amazon's supplier</span></strong>. We can provide you with consulting services that meet Amazon's requirements in packaging,labeling, transportation and other aspects.</span></li><li><span style='font-size: 14px;'> Provide<strong><span style='color: #ff0000;'> customized services</span></strong>, including <strong><span style='color: #ff0000;'>size, style, pattern, logo, labeling</span></strong> and <strong><span style='color: #ff0000;'>packaging</span></strong>.</span></li><li><span style='font-size: 14px;'> Samples will be made within 48 hours after receiving your design, and <strong><span style='color: #ff0000;'>free samples</span> </strong>can be provided for your reference.</span></li><li><span style='font-size: 14px;'><strong><span style='color: #ff0000;'> Eco-friendly</span></strong>, recycling and renewable, achieve any fabric you want.</span></li><li><span style='font-size: 14px;'>Support <strong><span style='color: #ff0000;'>affiliate marketing</span></strong>, contact customer service for details.</span></li></ul>";
	
	GetAllCommodity(){
		ChromeOptions options = new ChromeOptions();
		//options.addArguments("-headless");
		//options.addArguments("--disable-gpu");
		//options.addArguments("--remote-debugging-port=9222");
		
		utils = SeleniumUtils.init("webdriver.Chrome.driver","E:\\driver\\chromedriver.exe",options);
		utils.setMaximize();
	}
	
	public static void main(String[] args) throws InterruptedException, IOException {
		
		
		GetAllCommodity geta = new GetAllCommodity();
		//进入所有产品
		utils.goPage("https://galinkltd.en.alibaba.com/productlist.html?sortType=modified-desc");
		
		int i=1;
		int h=1;
		List<String[]> data = new ArrayList<>();
		
        data.add(addTop());
		
        
		for(int a=1;a<4;a++) {
			System.out.println("正在执行第"+a+"页");
			while(true) {
				try {
					while(true) {
						try {
							System.out.println("正在执行第"+i+"列----"+h+"行");
							GetAllCommodity.onlyCurrentWindow();
							//进入所有产品
							utils.goPage("https://galinkltd.en.alibaba.com/productlist-"+a+".html?sortType=modified-desc");
							data.add(clickCommodity(h,i));
							System.out.println("完成");
							i++;
						}catch(Exception e) {
							System.out.println(e);
							break;
						}
					}
					i=1;
					if(h>4) {
						break;
					}
					h++;
				}catch(Exception e) {
					break;
				}
			}
			i=1;
			h=1;
		}
		
		System.out.println(data.size());
		
		writeCSV("C:\\Users\\Administrator\\Desktop\\connodityDB.csv",data);
			//return;
			 
	}
	
	public static List<ImgDao> getImgStr(String htmlStr) {
        List<ImgDao> pics = new ArrayList<ImgDao>();
        String img = "";
        Pattern p_image;
        Matcher m_image;
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            // 得到<img />数据
            img = m_image.group();
            // 匹配<img>中的src数据
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
            	ImgDao dao = new ImgDao();
            	dao.setSrc(m.group(1));
            	pics.add(dao);
            }
        }
        
        
        p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        
        
        while (m_image.find()) {
            // 得到<img />数据
            img = m_image.group();
            // 匹配<img>中的src数
            Matcher s = Pattern.compile("alt\\a*=\\a*\"?(.*?)(\"|>|\\a+)").matcher(img);
            int i=0;
            while (s.find()) {
            	pics.get(i).setAlt(s.group(1));
            	i++;
            	if(i>pics.size()-1) {
            		break;
            	}
            }
        }
        
        //删除ClickhereforCustomization!!!之前的
        //htmlStr = htmlStr.substring(0, htmlStr.indexOf("Click here for Customization!!!"));
        if(htmlStr.indexOf("YOU MAY LIKE") != -1) {
        	htmlStr = htmlStr.substring(0, htmlStr.indexOf("YOU MAY LIKE")-90);
        }
        
        //System.out.println(delHTMLTag(htmlStr));
        
        //System.err.println(pics);
        
        
        return pics;
    }
	
	public static String[] clickCommodity(int h,int l) throws InterruptedException, IOException {
		//点击商品  document.querySelector("#\\35 031604775 > div > div.component-product-list > div > div > div > a > div > img")
		utils.clickDOM(".component-product-list > div > div:nth-child("+h+") > div:nth-child("+l+") > div > div.title.clamped > a", 300);
		
		//获取句柄
		utils.getNowWindowHandle();
		Thread.sleep(4000);
		//延迟架在页面
		for(int i=0;i<800;i++) {
        	utils.delayJS("document.documentElement.scrollTop="+i+"0;", 3000);
        	if(i==400 || i==799 ||i==549 ||i==200) {
        		Thread.sleep(4000);
        	}
        }
		Thread.sleep(4000);
		//获取名字
		String commodityName = utils.getVal(By.xpath("//*[@id=\"J-ls-grid-action\"]/div[2]/div/div[1]/div/div[1]/h1"), 3000);
		
		//获取html
		String commodityDelHTML = utils.getDriver().findElement(By.id("J-rich-text-description")).getAttribute("outerHTML");
		//替换html
		commodityDelHTML = changeHtml(commodityDelHTML);
		
		//判断详情是否有WHY CHOOSE US
		if(commodityDelHTML.indexOf("WHY CHOOSE US") == -1) {
			System.err.println("老产品");
			return null;
		}
		
		
		//获取图片src
		String image = null;
		
		try {
			image = utils.getDriver().findElement(By.cssSelector(".scc-wrapper.detail-module.module-detailBoothImage > "
					+ "div > ul > li:nth-child(1) > div > a > img")).getAttribute("src");
			if(image.indexOf("video") != -1) {
				System.out.println("----此商品第一张图片是视频----");
				throw new Exception();
			}
		}catch(Exception e) {
			image = utils.getDriver().findElement(By.cssSelector(".scc-wrapper.detail-module.module-detailBoothImage > "
					+ "div > ul > li:nth-child(2) > div > a > img")).getAttribute("src");
		}
		
		//删除_50x50.jpg
		image = image.replace("_50x50.jpg","");
		//删除_50x50.png
		image = image.replace("_50x50.png","");
		//System.out.println(image);
		
		//返回数组
		List<String> dataout = new ArrayList<>();
		
		//获取商品价格document.querySelector(".ma-ref-price > span > font > font")
		//String price = utils.getDriver().findElement(By.cssSelector(".ma-ref-price > span > font > font")).getAttribute("innerText");
		//设置商品价格
		//System.out.println(price);
		
		//获取商品阿里巴巴Id
		//获取url
		String url = utils.getDriver().getCurrentUrl();
		System.out.println(url);
		Matcher m = Pattern.compile("[^0-9]").matcher(url); 
		//设置商品Id
		dataout.add(m.replaceAll("").trim().toString().substring(0,11));
		//设置名字
		dataout.add(commodityName);
		
		
		//设置详情
		/*
		commodityDelHTML = commodityDelHTML.replaceAll("\r|\n", "");
		commodityDelHTML = commodityDelHTML.replaceAll("\"", "'");
		//替换&amp;
		commodityDelHTML = commodityDelHTML.replaceAll("&amp;", " ");
		//替换style='margin-left: -28%;width:160%'
		commodityDelHTML = commodityDelHTML.replaceAll("style='margin-left: -28%;width:160%'", " ");
		//替换data-width='689' data-height='234.' style='width: 724px; max-width: 750px;' width='724' height='234.'
		commodityDelHTML = commodityDelHTML.replaceAll("data-width='689' data-height='234.' style='width: 724px; max-width: 750px;' width='724' height='234.'", "style='width:100%;'");
		//替换style="max-width: 750px;"
		commodityDelHTML = commodityDelHTML.replaceAll("style='max-width: 750px; width: 750px; height: 357px;'", "style='width:100%;'");
		//替换style="display: flex; margin-top: 20px;"
		commodityDelHTML = commodityDelHTML.replaceAll("style='display: flex; margin-top: 20px;'", " ");
		//替换style="width: 50%; margin-top: 100px; padding: 10px;"
		commodityDelHTML = commodityDelHTML.replaceAll("style='width: 50%; margin-top: 100px; padding: 10px;'", " ");
		//替换style='max-width: 750px;'
		commodityDelHTML = commodityDelHTML.replaceAll("style='max-width: 750px;'", " ");
		//替换下半
		//commodityDelHTML = commodityDelHTML.substring(0,commodityDelHTML.length()-11000);
		
		//删除九宫格
		//String back= commodityDelHTML.substring(commodityDelHTML.indexOf("YOU MAY LIKE")+1);
		//System.out.println(commodityDelHTML.replaceAll(back, " "));
		
		//查找ul
		 int uli = commodityDelHTML.indexOf("</ul>");
		 //System.out.println("</ul>"+uli);
		 
		 commodityDelHTML = commodityDelHTML.substring(uli+5, commodityDelHTML.length());
		 
		//System.out.println("完成："+commodityDelHTML);
		
		
		//ommodityDelHTML = commodityDelHTML.replaceAll(back, " ");
		commodityDelHTML = commodityDelHTML.substring(0, commodityDelHTML.indexOf("YOU MAY LIKE")-90);
		
		//<div id="ali-title-AliPostDhMb-skur5" style="padding: 8px 0; border-bottom: 1px solid #ddd;"><span style="background-color: #ddd; color: #333; font-weight: bold; padding: 8px 10px; line-height: 12px;">Company Information</span></div>
		commodityDelHTML = commodityDelHTML.replaceAll("<div id='ali-title-AliPostDhMb-skur5' style='padding: 8px 0; border-bottom: 1px solid #ddd;'><span style='background-color: #ddd; color: #333; font-weight: bold; padding: 8px 10px; line-height: 12px;'>Company Information</span></div>", " ");
		//<div style="background-color: #f5f5f5; color: #000000; font-family: Arial; font-size: 12px; font-style: normal; font-weight: 400; padding-top: 8px;">
		commodityDelHTML = commodityDelHTML.replaceAll("<div style='background-color: #f5f5f5; color: #000000; font-family: Arial; font-size: 12px; font-style: normal; font-weight: 400; padding-top: 8px;'>", " ");
		//style="width: 700.996px;"
		commodityDelHTML = commodityDelHTML.replaceAll("style='max-width: 50px;'", "style='width: 100%;'");
		commodityDelHTML += "</div>";
		
		//all
		commodityDelHTML = commodityDelHTML.replaceAll(all, " ");
		commodityDelHTML = commodityDelHTML.replaceAll(all2, " ");
		commodityDelHTML = commodityDelHTML.replaceAll(all4, " ");
		
		//data-width='350' data-height='350' style='width: 320px; max-width: 750px; height: 350px;' width='320' height='350' 
		
		//https://sc01.alicdn.com/kf/Ha13786694c654f42a6642ac74dffa273M/231311121/Ha13786694c654f42a6642ac74dffa273M.jpg
		commodityDelHTML = commodityDelHTML.replaceAll("data-width='350' data-height='350' style='width: 320px; max-width: 750px; height: 350px;' width='320' height='350' ", "style='width: 100%;'");	
		
		//&nbsp;
		commodityDelHTML = commodityDelHTML.replaceAll("&nbsp;", " ");
		
		//href
		commodityDelHTML = commodityDelHTML.replaceAll("href", " ");
		
		commodityDelHTML = commodityDelHTML.replaceAll("width:", "width: 100% !important;");
		//commodityDelHTML = commodityDelHTML.replaceAll("width: 320px;", "width: 100%;");
		//commodityDelHTML = commodityDelHTML.replaceAll("width: 670px;", "width: 100%;");
		//commodityDelHTML = commodityDelHTML.replaceAll("width: 724px;", "width: 100%;");
		commodityDelHTML = commodityDelHTML.replaceAll("WHY CHOOSE US", " ");
		commodityDelHTML = commodityDelHTML.replaceAll("display: flex; ", " ");
		commodityDelHTML = commodityDelHTML.replaceAll("float:left", " ");
		commodityDelHTML = commodityDelHTML.replaceAll("float:right", " ");
		
		//System.out.println("over==="+commodityDelHTML);
		
		commodityDelHTML +=""
						 + "<img src='https://sc01.alicdn.com/kf/HTB1XnzsadjvK1RjSspiq6AEqXXaZ/231311121/HTB1XnzsadjvK1RjSspiq6AEqXXaZ.jpg' style='width: 100%;'>";
		commodityDelHTML +="<img src='https://sc02.alicdn.com/kf/HTB1r549ffiSBuNkSnhJ762DcpXaS/231311121/HTB1r549ffiSBuNkSnhJ762DcpXaS.png' style='width: 100%;'>";
		commodityDelHTML +="<img src='https://sc01.alicdn.com/kf/HTB1P69.ddcnBKNjSZR0q6AFqFXap/231311121/HTB1P69.ddcnBKNjSZR0q6AFqFXap.jpg' style='width: 100%;'>";
		commodityDelHTML +="<img src='https://sc02.alicdn.com/kf/HTB1dIA0jYsTMeJjy1zeq6AOCVXaN/231311121/HTB1dIA0jYsTMeJjy1zeq6AOCVXaN.jpg' style='width: 100%;'>" + 
				"";
		
		//System.out.println("有几个div"+divsize);
		
		commodityDelHTML = "<div style='width:100%;'>" +commodityDelHTML;
		
		
		//<div style='margin-top: 20px; clea
		commodityDelHTML = commodityDelHTML.replaceAll("</div></div><div style='margin-top: 20px; clea</div>", " ");
		//</div></div></div><div style='margin-top: 20px; clea</div>
		commodityDelHTML = commodityDelHTML.replaceAll("</div></div></div><div style='margin-top: 20px; clea</div>", " ");
		
		//</span></div> 
		commodityDelHTML = commodityDelHTML.replaceAll("</span></div>", "</span>");
		
		//sc02.alicdn.com/kf/HTB1dIA0jYsTMeJjy1zeq6AOCVXaN/231311121/HTB1dIA0jYsTMeJjy1zeq6AOCVXaN.jpg_.webp
		//替换图片
		
		//查找字符串里面有几个<div>
				int divsize = search(commodityDelHTML, "<div");
				int xdivsize = search(commodityDelHTML, "</div");
				int nowdivs = divsize - xdivsize;
				
				System.out.println(divsize+","+xdivsize+","+nowdivs);
				
				for(int i=0;i<nowdivs;i++) {
					commodityDelHTML += "</div>";
				}
		
		//System.out.println(commodityDelHTML);
		 * *
		 */
		List<ImgDao> imgList = getImgStr(commodityDelHTML);
		//组成html
		String html = "";
		for(int i=0;i<4;i++) {
			html+="<img src='"+imgList.get(i).getSrc()+"' style='width:100%;margin-top:20px;' alt='"+imgList.get(i).getAlt()+"' />";
		}
		System.out.println(html);
		
		dataout.add(html);
		//设置图片
		dataout.add(image);
		//设置简述
		dataout.add(all);
		
		//获取商品信息 do-entry-list
		String doEntryList = utils.getDriver().findElement(By.cssSelector(".do-entry-list")).getAttribute("outerHTML");
		Document doc = Jsoup.parse(doEntryList);
	    Elements elements = doc.select("dl[class=do-entry-item]");
	    
	    for( Element element : elements ){
	    	String key = element.select("span[class=attr-name J-attr-name]").text();
	    	String value = element.select("div[class=ellipsis]").text();
	    
	    	dataout.add(key);
	    	dataout.add(value);
	    }
	    
	    
	    int size=dataout.size();  
        String[] array=new String[size];  
        for(int i=0;i<dataout.size();i++){  
            array[i]=(String)dataout.get(i);  
        }  
        
        //System.out.println(array.length);
		
        //writeTxtCSV("C:\\Users\\Administrator\\Desktop\\connodityDBdemo.csv", array);
        writeTxtCSV("C:\\Users\\Administrator\\Desktop\\connodityDBdemo.csv", array);
		
		return array;
	}
	
	public static String changeHtml(String s) {
		//替换_.webp
		s = s.replace("_.webp","");
				
		//替换图片
		s = s.replace("//sc02.alicdn.com/kf/HTB1r549ffiSBuNkSnhJ762DcpXaS/231311121/HTB1r549ffiSBuNkSnhJ762DcpXaS.png","https://sc02.alicdn.com/kf/HTB1r549ffiSBuNkSnhJ762DcpXaS/231311121/HTB1r549ffiSBuNkSnhJ762DcpXaS.png");
		s = s.replace("//sc01.alicdn.com/kf/HTB1P69.ddcnBKNjSZR0q6AFqFXap/231311121/HTB1P69.ddcnBKNjSZR0q6AFqFXap.jpg","https://sc01.alicdn.com/kf/HTB1P69.ddcnBKNjSZR0q6AFqFXap/231311121/HTB1P69.ddcnBKNjSZR0q6AFqFXap.jpg");
		s = s.replace("//sc02.alicdn.com/kf/HTB1dIA0jYsTMeJjy1zeq6AOCVXaN/231311121/HTB1dIA0jYsTMeJjy1zeq6AOCVXaN.jpg","https://sc02.alicdn.com/kf/HTB1dIA0jYsTMeJjy1zeq6AOCVXaN/231311121/HTB1dIA0jYsTMeJjy1zeq6AOCVXaN.jpg");
				
		//替换掉
		s = s.replaceAll("<noscript>.*?</noscript>", " ");
				
		//替换J-rich-text-description
		s = s.replace("<div id=\"J-rich-text-description\" class=\"richtext richtext-detail rich-text-description\">","<div id=\"J-rich-text-description\" class=\"richtext richtext-detail rich-text-description\" style=\"margin-left: -28%;width:160%\">");
		return s;
	}
	
	public static void writeCSV(String filePath,List<String[]> data) throws IOException{
		
        CsvWriter csvWriter = new CsvWriter(filePath,',', Charset.forName("GBK"));
            
        for(int i=0;i<data.size();i++) {
            csvWriter.writeRecord(data.get(i));
        }
        csvWriter.close();

    }
	
	public static void writeTxtCSV(String filePath,String [] data) throws IOException{
		
		FileWriter fw = null;
	    //如果文件存在，则追加内容；如果文件不存在，则创建文件
	    File f=new File(filePath);
	    fw = new FileWriter(f, true);
	 
	    PrintWriter pw = new PrintWriter(fw);
	    
	    
	    
	    StringBuffer buf = new StringBuffer();
	    
	    for (int i = 0; i < data.length; i++) {
	        buf.append(data[i]).append("|");
	    }
	    
	    buf.substring(0, buf.length()-1);
	    
	    pw.println(buf);
	    
	    pw.flush();
	    fw.flush();
	    pw.close();
	    fw.close();
	
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
                    if (title.equals("Top Sale Products,Drawstring Bag,Blankets direct from CN")) {
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
	
	public static String[] addTop() throws IOException {
		List<String> top = new ArrayList<>();
        
		top.add("SKU");
        top.add("Name");
        top.add("Description");
        top.add("Images");
        //填写简单描述
        top.add("Short description");
        
        for(int ss=1;ss<50;ss++) {
        	top.add("Attribute "+ss+" name");
        	top.add("Attribute "+ss+" value(s)");
        }
        
        int size=top.size();  
        String[] array=new String[size]; 
        
        for(int aa=0;aa<top.size();aa++){  
            array[aa]=top.get(aa);  
        }
        
        System.out.println(array.length);
        
        Img9.delate("C:\\Users\\Administrator\\Desktop\\connodityDB.csv");
        Img9.delate("C:\\Users\\Administrator\\Desktop\\connodityDBdemo.csv");
        writeTxtCSV("C:\\Users\\Administrator\\Desktop\\connodityDBdemo.csv", array);
        
        return array;
	}
	
	public static int search(String str,String strRes) {//查找字符串里与指定字符串相同的个数
        int n=0;//计数器
//      for(int i = 0;i<str.length();i++) {
//         
//      }
        while(str.indexOf(strRes)!=-1) {
            int i = str.indexOf(strRes);
            n++;
            str = str.substring(i+1);
        }
        return n;
    }
	
	private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
    private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
    private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
    private static final String regEx_space = "\\s*|\t|\r|\n";//定义空格回车换行符
	
	/**
     * @param htmlStr
     * @return
     *  删除Html标签
     */
    public static String delHTMLTag(String htmlStr) {
        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); // 过滤script标签
 
        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); // 过滤style标签
 
        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); // 过滤html标签
 
        
        return htmlStr.trim(); // 返回文本字符串
    }
    
    public static String getTextFromHtml(String htmlStr){
    	htmlStr = delHTMLTag(htmlStr);
    	htmlStr = htmlStr.replaceAll(" ", "");
    	htmlStr = htmlStr.substring(0, htmlStr.indexOf("。")+1);
    	return htmlStr;
    }
}	


class ImgDao{
	@Override
	public String toString() {
		return "ImgDao [src=" + src + ", alt=" + alt + "]";
	}

	private String src;
	
	private String alt;

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}
}