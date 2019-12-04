package taobao;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import utils.SeleniumUtils;

public class Demo {
	
	static SeleniumUtils utils = null;
	
	Demo(){
		utils = SeleniumUtils.init("webdriver.Chrome.driver","H:\\\\chromedriver.exe");
		utils.setMaximize();
	}
	
	public static void main(String [] args) throws InterruptedException, IOException{
		Demo d = new Demo();
		
		String name = "Promotional customized microfiber poncho sand free hooded bath towel for adults";
		
		//打开网页https://galinkltd.en.alibaba.com/
		utils.goPage("https://galinkltd.en.alibaba.com/search/product?SearchText="+name.replace(" ","%20"));
	
		//点击商品  document.querySelector("#\\35 031604775 > div > div.component-product-list > div > div > div > a > div > img")
		utils.clickDOM("div.component-product-list > div > div > div > div > div.title.clamped > a > span:nth-child(1)", 300);
		
		
		String handle = utils.getDriver().getWindowHandle();  
        // 获取所有页面的句柄，并循环判断不是当前的句柄 
        for (String handles : utils.getDriver().getWindowHandles()) {  
            if (handles.equals(handle))  
                continue;  
            utils.getDriver().switchTo().window(handles);  
        }
        
        Thread.sleep(3000);
        
        System.out.println(utils.getVal(By.xpath("//*[@id=\"J-ls-grid-action\"]/div[2]/div/div[1]/div/div[1]/h1"), 3000));
        
		
		//utils.delayJS("alert(1);", 300);
        
        System.out.println(utils.getVal(By.id("detail-title-tags-before-title"), 3000));
        
        
        for(int i=0;i<400;i++) {
        	utils.delayJS("document.documentElement.scrollTop="+i+"0;", 3000);
        }
        
        //延迟
        Thread.sleep(1000);
        
        for(int i=400;i<800;i++) {
        	utils.delayJS("document.documentElement.scrollTop="+i+"0;", 3000);
        }
        
        //延迟
        Thread.sleep(1000);
        
        //utils.delayJS("alert('正在输入文件---')", 3000);
        System.err.println("正在输入文件---");
        
        
		//获取html
		String s = utils.getDriver().findElement(By.id("J-rich-text-description")).getAttribute("outerHTML");
		//System.err.println(s);
		
		//获取突破src
		//document.querySelector(".scc-wrapper.detail-module.module-detailBoothImage > div > ul > li:nth-child(2) > div > a > img")
		String image = utils.getDriver().findElement(By.cssSelector(".scc-wrapper.detail-module.module-detailBoothImage > div > ul > li:nth-child(2) > div > a > img")).getAttribute("src");
		//删除_50x50.jpg
		image = image.replace("_50x50.jpg","");
		
		//获取地址
		String url = utils.getDriver().getCurrentUrl();
		System.out.println(url);
		Matcher m = Pattern.compile("[^0-9]").matcher(url);  
		System.out.println( m.replaceAll("").trim());
		
		//获取商品价格document.querySelector(".ma-ref-price > span > font > font")
				String price = utils.getDriver().findElement(By.cssSelector(".ma-ref-price > span > font > font")).getAttribute("outerHTML");
				//设置商品价格
				System.out.println(price);
		
		
		//获取商品信息 do-entry-list
		String doEntryList = utils.getDriver().findElement(By.cssSelector(".do-entry-list")).getAttribute("outerHTML");
		
		//第一步，将字符内容解析成一个Document类
	    Document doc = Jsoup.parse(doEntryList);
	    Elements elements = doc.select("dl[class=do-entry-item]");
	    for( Element element : elements ){
	        //String title = element.text();
	        //System.err.println(title);
	        //titles.add(title);
	        //urls.add(element.select("a").attr("href"));
	    	String key = element.select("span[class=attr-name J-attr-name]").text();
	    	String value = element.select("div[class=ellipsis]").text();
	    	
	    	System.err.println(key+"----"+value);
	     }
	    
	    /*
	    //ellipsis
	    Elements elements2 = doc.select("div[class=ellipsis]");
	    for( Element element2 : elements ){
	        String title = element2.text();
	        System.err.println(title);
	    }*/
		
		
		//System.out.println(image);
		
		
		//替换_.webp
		s = s.replace("_.webp","");
		
		s = s.replace("//sc02.alicdn.com/kf/HTB1r549ffiSBuNkSnhJ762DcpXaS/231311121/HTB1r549ffiSBuNkSnhJ762DcpXaS.png","https://sc02.alicdn.com/kf/HTB1r549ffiSBuNkSnhJ762DcpXaS/231311121/HTB1r549ffiSBuNkSnhJ762DcpXaS.png");
		s = s.replace("//sc01.alicdn.com/kf/HTB1P69.ddcnBKNjSZR0q6AFqFXap/231311121/HTB1P69.ddcnBKNjSZR0q6AFqFXap.jpg","https://sc01.alicdn.com/kf/HTB1P69.ddcnBKNjSZR0q6AFqFXap/231311121/HTB1P69.ddcnBKNjSZR0q6AFqFXap.jpg");
		s = s.replace("//sc02.alicdn.com/kf/HTB1dIA0jYsTMeJjy1zeq6AOCVXaN/231311121/HTB1dIA0jYsTMeJjy1zeq6AOCVXaN.jpg","https://sc02.alicdn.com/kf/HTB1dIA0jYsTMeJjy1zeq6AOCVXaN/231311121/HTB1dIA0jYsTMeJjy1zeq6AOCVXaN.jpg");
		
		//替换掉
		s = s.replaceAll("<noscript>.*?</noscript>", " ");
		
		//替换J-rich-text-description
		s = s.replace("<div id=\"J-rich-text-description\" class=\"richtext richtext-detail rich-text-description\">","<div id=\"J-rich-text-description\" class=\"richtext richtext-detail rich-text-description\" style=\"margin-left: -28%;width:160%\">");
		
		FileOutputStream fileWriter=new FileOutputStream("E:/stream.html");
		OutputStreamWriter writer=new OutputStreamWriter(fileWriter, "UTF-8");
		try {
			writer.write("<!DOCTYPE html>\r\n" + 
					"<html>\r\n" + 
					"<head>\r\n" + 
					"	<title></title>\r\n" + 
					"</head>\r\n" + 
					"<body>"+s+"</body>\r\n" + 
							"</html>");
		} catch (Exception e) {
			
		}finally{
			writer.close();
		}
		
	}
}
