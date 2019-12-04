package taobao;

import java.io.IOException;
import java.util.ArrayList;

import dao.IMGDAO;
import utils.CSVUtils;
import utils.SeleniumUtils;

/**
 * 官网商品人工检查
 * @author Administrator
 *
 */
public class Examine {
	static SeleniumUtils utils = null;
	
	Examine(){
		utils = SeleniumUtils.init("webdriver.Chrome.driver","H:\\\\chromedriver.exe");
		utils.setMaximize();
	}
	
	public static void main(String [] args) throws IOException, InterruptedException {
		new Examine();
		//读取csv文件
		ArrayList<IMGDAO> list = null;
		list = CSVUtils.read("C:\\Users\\Administrator\\Desktop\\connodityDB.csv");
		
		for(int i=0;i<list.size();i++) {
			//打开浏览器
			utils.goPage("https://galinkltd.com/product/"+list.get(i).getName().replaceAll(" ", "-"));
			
			int inof = utils.getDriver().getTitle().indexOf(list.get(i).getName());
			
			System.out.println(inof);
			
			if(inof != 0) {
				continue;
			}
			
		
			//延迟架在页面
			for(int s=0;s<1000;s++) {
	        	utils.delayJS("document.documentElement.scrollTop="+s+"0;", 3000);
	        	if(s==400 || s==799 ||s==549 ||s==200) {
	        		Thread.sleep(1000);
	        	}
	        }
		}
	}
}
