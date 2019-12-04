package utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.gargoylesoftware.htmlunit.javascript.host.ReadableStream;

/**
 * 这是一个作者自己写的selenium工具类
 * 
 * @author QJH
 *
 */
public class SeleniumUtils {
	
	private WebDriver driver = null;
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 工具类设置驱动构造方法
	 * 
	 * @param drivername 驱动名字 --> webdriver.Chrome.driver
	 * @param driverPath 驱动地址 --> H:\\\\chromedriver.exe	驱动下载https://chromedriver.storage.googleapis.com/
	 */
	SeleniumUtils(String driverName,String driverPath){
		//设置驱动
		String _driverPath = System.getProperty("drivername");
		if(_driverPath == null) {
			System.setProperty(driverName, driverPath);
		}
		//新建一个驱动
		this.driver = new ChromeDriver();
	}
	
	SeleniumUtils(String driverName,String driverPath,ChromeOptions options){
		//设置驱动
				//String _driverPath = System.getProperty("drivername");
				//if(_driverPath == null) {
					System.setProperty(driverName, driverPath);
				//}
				//新建一个驱动
				this.driver = new ChromeDriver(options);
	}
	
	public SeleniumUtils(WebDriver driver){
		this.driver = driver;
	}
	
	/**
	 * 工具类设置驱动构造方法
	 * @param f 文件
	 */
	SeleniumUtils(File f){
		
	}
	
	/**
	 * 工具类初始化(并不是单例)
	 * 
	 * @param drivername 驱动
	 * @param driverPath 驱动地址
	 * @return SeleniumUtils对象
	 */
	public static SeleniumUtils init(String driverName,String driverPath) {
		return new SeleniumUtils(driverName, driverPath);
	}
	
	public static SeleniumUtils init(String driverName,String driverPath,ChromeOptions options) {
		return new SeleniumUtils(driverName, driverPath,options);
	}
	
	/**
	 * 获取驱动
	 * 
	 * @return WebDriver对象
	 */
	public WebDriver getDriver() {
		return this.driver;
	}
	
	/**
	 * 浏览器最大化
	 * 
	 * @return SeleniumUtils对象
	 */
	public SeleniumUtils setMaximize() {
		this.driver.manage().window().maximize();
		return this;
	}
	
	/**
	 * 设置selenium页面url地址
	 * 
	 * @param pageUrl 地址
	 * @return SeleniumUtils对象
	 */
	public SeleniumUtils goPage(String pageUrl) {
		this.driver.get(pageUrl);
		return this;
	}
	
	/**
	 * 获得当前url
	 * 
	 * @return 当前url
	 */
	public String getCurrentUrl() {
		return this.driver.getCurrentUrl();
	}
	
	/**
	 * javaScript代码执行器
	 * 
	 * @param jscode javaScript代码
	 * @return SeleniumUtils对象
	 */
	public SeleniumUtils js(StringBuilder jscode){
		StringBuilder log = new StringBuilder();
		String date = df.format(new Date());
		log.append(date);
		log.append("===[jslog]  ");
		log.append(jscode);
		
		((JavascriptExecutor)this.driver).executeScript(jscode.toString());
		
		//打印日志
		//this.log(log);
		
		return this;
	}
	
	/**
	 * 延时执行javaScript代码
	 * 
	 * @param jscode javaScript代码
	 * @param time	延时时间
	 * @return SeleniumUtils对象
	 */
	public SeleniumUtils delayJS(String jscode,int time){
		StringBuilder jsc = new StringBuilder();
		jsc.append("setTimeout(test(),");
		jsc.append(time);
		jsc.append(");function test(){");
		jsc.append(jscode);
		jsc.append("}");
		
		js(jsc);
		
		return this;
	}
	
	/**
	 * 获取dom节点
	 * 
	 * @param querySelector dom节点css表达式
	 * @return dom节点javaScript表达式
	 */
	public String getDOM(String querySelector) {
		StringBuilder jsc = new StringBuilder();
		jsc.append("document.querySelector('");
		jsc.append(querySelector);
		jsc.append("')");
		return jsc.toString();
	}
	
	/**
	 * 点击dom节点
	 * 
	 * @param querySelector dom节点css表达式
	 * @param time 延迟时间
	 * @return SeleniumUtils对象
	 */
	public SeleniumUtils clickDOM(String querySelector,int time){
		this.delayJS(getDOM(querySelector)+".click();",time);
		return this;
	}
	
	/**
	 * 设置dom节点内容(value)
	 * 
	 * @param querySelector dom节点css表达式
	 * @param context dom节点要设置的内容
	 * @param time 延迟时间
	 * @return SeleniumUtils对象
	 */
	public SeleniumUtils setVal(String querySelector,String context,int time){
		this.delayJS(getDOM(querySelector)+".value='"+context+"';",time);
		return this;
	}
	
	/**
	 * 获取dom节点内容(value)
	 * 
	 * @param by dom节点css表达式
	 * @param time 延迟时间
	 * @return SeleniumUtils对象
	 */
	public String getVal(By by,int time) {
		return this.driver.findElement(by).getText();
	}
	
	/**
	 * 获取dom节点内容(Attribute)
	 * 
	 * @param by By对象
	 * @param key Attribute key
	 * @param time 延迟时间
	 * @return
	 */
	public String getAttribute(By by,String key,int time) {
		return this.driver.findElement(by).getAttribute(key);
	}
	
	/**
	 * 获取当前页面句柄
	 * 
	 */
	public void getNowWindowHandle() {
		String handle = this.driver.getWindowHandle();  
        for (String handles : this.driver.getWindowHandles()) {  
            if (handles.equals(handle))  
                continue;  
            this.driver.switchTo().window(handles);  
        }
	}
	
	/**
	 * 日志打印函数
	 * 
	 * @param log 需要打印的StringBuilder
	 */
	private void log(StringBuilder log) {
		System.out.println(log);
	}
	
	/**
	 * 关闭浏览器
	 * 
	 * @return SeleniumUtils对象
	 */
	public SeleniumUtils closeBrowser() {
		//关闭浏览器
		this.driver.quit();
		return this;
	}
}
