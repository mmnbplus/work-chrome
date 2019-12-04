package Google;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import taobao.GetAllCommodity;
import utils.SeleniumUtils;

class Email{
	private String email;
	
	private String url;

	public String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		return "Email [email=" + email + ", url=" + url + ", getEmail()=" + getEmail() + ", getUrl()=" + getUrl() + "]";
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}

public class GetMessage {
	public static List<Email> siteData;
	static SeleniumUtils utils = null;
	static List<String[]> data = new ArrayList<>();
	
	GetMessage(){
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		//options.addArguments("--proxy-server=http://144.34.211.135:8840");
        
        Map<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.managed_default_content_settings.images", 2);
        chromePrefs.put("permissions.default.stylesheet",2);

        
        options.setExperimentalOption("prefs", chromePrefs);
		utils = SeleniumUtils.init("webdriver.Chrome.driver","H:\\\\chromedriver.exe",options);
		utils.getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		utils.setMaximize();
	}
	
	public static void main(String[] args) throws InterruptedException, IOException {
		new GetMessage();
		siteData = new ArrayList<>();
		//读取文件
		for(int i=0;i<10;i++) {
			readJson(i);
		}
		
		String [] d = new String[]{
				"facebook","twitter","email","ins","pinterest","youtube","网址"
		};
		data.add(d);
		
		for (int i = 0; i < 200; i++) {
			try {
				getData(i);
			}catch(Exception e){
				System.err.println(e);
				continue;
			}
		}
		
		GetAllCommodity.writeCSV("E:\\谷歌数据\\csv\\day02\\userInfo.csv",data);
	}
	
	public static void getData(int index) throws InterruptedException {
		String url = siteData.get(index).getUrl();
		url = url.replaceAll("https://www.google.com/url?client=internal-uds-cse&cx=002843904829378520769:ljoltd16hzw&q=", "");
		
		Thread.sleep(3000);
		utils.getNowWindowHandle();
		Thread.sleep(3000);
		
		/*
		 // run in a second  
        final long timeInterval = 1000;  
        Runnable runnable = new Runnable() {  
            public void run() {  
                while (true) {  
                    // ------- code for task to run  
                    System.out.println("Hello !!");  
                    // ------- ends here  
                    try {  
                        Thread.sleep(timeInterval);  
                        break;
                    } catch (InterruptedException e) {  
                        e.printStackTrace();  
                    }  
                }  
            }  
        };  
        Thread thread = new Thread(runnable);  
        thread.start();  
        */
		
		
		
		utils.goPage(url);
		
		/*
		//获取句柄
		Thread.sleep(1000);
		utils.getNowWindowHandle();
		Thread.sleep(1000);
		*/
		//定时器
		//window.stop()
		
		
		utils.delayJS("document.documentElement.scrollTop=50000;", 1000);
		//String html = utils.getAttribute(By.cssSelector("body"),"outerHTML", 3000);
		String html = utils.getDriver().getPageSource();
		//System.out.println(html);
		
		//获取https://facebook.com/
		/*
		int fascBookIndex = html.indexOf("https://facebook.com/");
		String fascBook = html.substring(fascBookIndex,fascBookIndex+200);
		int fascBoodEnd = fascBook.indexOf("\"");
		fascBook = fascBook.substring(0, fascBoodEnd);
		*/
		String facebook = getStringYH(html, "https://facebook.com/");
		String twitter = getStringYH(html, "https://twitter.com/");
		String email =siteData.get(index).getEmail();
		String ins = getStringYH(html, "https://www.instagram.com/");
		String pinterest = getStringYH(html, "https://www.pinterest.com/");
		String youtube = getStringYH(html, "https://www.youtube.com/");
		
		//System.out.println(facebook+"   "+twitter+"   "+email);
		String [] d = new String[]{
				facebook,twitter,email,ins,pinterest,youtube,url
		};
		System.out.println(facebook+","+twitter+","+email+","+ins+","+pinterest+","+youtube);
		data.add(d);
	}
	
	public static String getStringYH(String content,String value) {
		int valueIndex = content.indexOf(value);
		if(valueIndex == -1) {
			return null;
		}
		String v = content.substring(valueIndex,content.length());
		int fascBoodEnd = v.indexOf("\"");
		v = v.substring(0,fascBoodEnd);
		return v;
	}
	
	public static void readJson(int s) {
        String charset = "utf-8";
        File file = new File("E:\\谷歌数据\\day02\\json ("+s+").txt");
        long fileByteLength = file.length();
        byte[] content = new byte[(int) fileByteLength];
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String str = null;
        try {
            str = new String(content, charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        
        //去掉/*O_o*/
        //google.search.cse.api5364(
        str = str.substring(34,str.length()-2);
        str = str.substring(1,str.length());
        
        //System.out.println(str);
        
        JSONObject object = (JSONObject) JSONObject.parse(str);
        JSONArray results = JSONArray.parseArray(object.get("results").toString());
        
        
        for(int i=0;i<results.size();i++) {
        	JSONObject json = JSONObject.parseObject(results.get(i).toString());
        	Email e = new Email();
        	e.setUrl(json.getString("clicktrackUrl"));
        	
        	String cc = replaceBlank(json.getString("content"));
        	cc = cc.replaceAll("<b>", "");
        	cc = cc.replaceAll("</b>", "");
        	cc = getEmail(cc);
        	
        	e.setEmail(cc);
        	siteData.add(e);
        	//System.out.println(json.getString("clicktrackUrl"));
        }
        
        //System.out.println(siteData);
        
    }
	
	public static String replaceBlank(String str) {
		String dest ="";

		    if (str != null) {
		        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		        Matcher m = p.matcher(str);
		        dest = m.replaceAll("");


		    }
		    return dest;


	}
	
    public static String getEmail(String fileContent) {
		  //用Pattern编译一个表达式
		  Pattern p=Pattern.compile("[a-zA-Z0-9_]{3,20}@[a-zA-Z0-9]{2,10}[.](com|cn|org)");
		 //通过Pattern对象得到一个Matcher对象
		  Matcher m=p.matcher(fileContent);
		  //搜索符合正则表达式的子串 调用Matcher的find方法 如果找到了匹配的子串，返回真
		  while(m.find()){
		  //取出匹配的子串用group()方法
			 //System.out.print("抓取的邮箱使用group方法:"+m.group()+"  ");
			 //System.out.println("抓取的邮箱使用start end方法:"+fileContent.substring( m.start(), m.end())+"    ");
			 return m.group();
		  }
		return fileContent;
	}
	
}
