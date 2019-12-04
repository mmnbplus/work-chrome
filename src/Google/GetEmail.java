package Google;

import java.util.HashMap;

import org.openqa.selenium.chrome.ChromeOptions;

import utils.SeleniumUtils;
 
public class GetEmail {
	
	static SeleniumUtils utils = null;
	
	GetEmail(){
		ChromeOptions options = new ChromeOptions();
		//options.addArguments("--headless"); 
		
		// 2.新的下载地址为桌面（可以弄成某个文件夹路径而不要直接弄成死的静态路径）
		String downloadPath = "E:\\谷歌数据\\day02";
		// 3.HashMap 中保存下载地址信息
		HashMap<String, Object> hashMap = new HashMap<>();
		hashMap.put("download.default_directory", downloadPath);
		options.setExperimentalOption("prefs", hashMap);
		
		utils = SeleniumUtils.init("webdriver.Chrome.driver","H:\\\\chromedriver.exe",options);
		utils.setMaximize();
	}
	
	public static void main(String[] args) throws InterruptedException {
		new GetEmail();
		/*
		utils.goPage("https://cse.google.com/cse?cx=002843904829378520769:ljoltd16hzw");
		//获取句柄
		Thread.sleep(4000);
		utils.getNowWindowHandle();
		Thread.sleep(4000);
		
		//输入内容
		utils.setVal("#gsc-i-id1", "@gmail.com", 2000);
		//点击按钮*/
		String key = "towel";
		String cx = "002843904829378520769:xh9ipo79duc";
		
		
		for(int i=1;i<20;i++) {
			
			String url = "https://cse.google.com/cse/element/v1?rsz=filtered_cse&start="+i+"&num=10&hl=en&source=gcsc&gss=.com&cselibv=b5752d27691147d6&cx=002843904829378520769:xh9ipo79duc&q=towel%20%22%40gmail.com%22&safe=off&cse_tok=AKaTTZjdQQT8zGGoxcmu_vmgajZm:1572491924181&gl=us&sort=&exp=csqr,cc&oq=towel%20%22%40gmail.com%22&gs_l=partner-generic.12...0.0.1.10256.0.0.0.0.0.0.0.0..0.0.gsnos%2Cn%3D13...0.0....34.partner-generic..1.0.0.yu3AjeCXzrg&callback=google.search.cse.api18229";
			System.out.println(url);
			utils.goPage(url);
		}
		
		
	}
	
	/*
       public static void main(String[] args) {
		  String fileContent="Peshtamel Turkish , Robes, Blankets : letstalktowels16@gmail.com" + 
		  		"Holiday Show Schedule ⤵️ letstalktowels.com/pages/holiday-show-&nbsp;...";
		  //用Pattern编译一个表达式
		  Pattern p=Pattern.compile("[a-zA-Z0-9_]{3,20}@[a-zA-Z0-9]{2,10}[.](com|cn|org)");
		 //通过Pattern对象得到一个Matcher对象
		  Matcher m=p.matcher(fileContent);
		  //搜索符合正则表达式的子串 调用Matcher的find方法 如果找到了匹配的子串，返回真
		  while(m.find()){
		  //取出匹配的子串用group()方法
			 System.out.print("抓取的邮箱使用group方法:"+m.group()+"  ");
			 System.out.println("抓取的邮箱使用start end方法:"+fileContent.substring( m.start(), m.end())+"    ");
		  }
	}
	*/
}