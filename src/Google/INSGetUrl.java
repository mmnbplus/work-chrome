package Google;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.chrome.ChromeOptions;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import db.ConDB;
import utils.SeleniumUtils;

public class INSGetUrl {
	public static List<Email> siteData;
	static SeleniumUtils utils = null;
	static ConDB conDB= null;
	
	INSGetUrl(){
		ChromeOptions options = new ChromeOptions();
		//options.addArguments("--headless"); 
		
		// 2.新的下载地址为桌面（可以弄成某个文件夹路径而不要直接弄成死的静态路径）
		String downloadPath = "E:\\谷歌数据\\day03";
		// 3.HashMap 中保存下载地址信息
		HashMap<String, Object> hashMap = new HashMap<>();
		hashMap.put("download.default_directory", downloadPath);
		options.setExperimentalOption("prefs", hashMap);
		utils = SeleniumUtils.init("webdriver.Chrome.driver","H:\\\\chromedriver.exe",options);
		utils.setMaximize();
		
	}
	
	public static void main(String[] args) throws InterruptedException, SQLException {
		
		/*
		new INSTalk();		
		
		for(int i=0;i<20;i++) {
			
			String url = "https://cse.google.com/cse/element/v1?rsz=filtered_cse&num=10&hl=en&source=gcsc&gss=.com&start="+i+"0&cselibv=b5752d27691147d6&cx=002843904829378520769:xh9ipo79duc&q=beach%20towel&safe=off&cse_tok=AKaTTZgNhUrKDQ761hdG34GxHVyQ:1572503915374&gl=us&sort=&exp=csqr,cc&callback=google.search.cse.api16737";
			System.out.println(url);
			utils.goPage(url);
		}
		*/
		
		//读取数据存入数据库
		conDB = new ConDB();
		for(int i=0;i<20;i++) {
			readJson(i);
		}
	}
	
	public static void readJson(int s) throws SQLException {
        String charset = "utf-8";
        System.out.println(s);
        File file = new File("E:\\谷歌数据\\day03\\json ("+s+").txt");
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
        	String url = json.getString("url");
        	
        	System.out.println(url);
        	
        	ResultSet rs = conDB.select("select id from myINSdb where url = ?", url);
        	
        	if(!rs.next()) {	//创建数据
        		conDB.upDataOrInsert("insert into myINSdb values (?,?,?,?,?,?,?)",null,url,2,2,null,null,null);
    		}
        }
    }
	
}
