package ownwebsite;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import utils.EmailAndName;
import utils.SeleniumUtils;

/**
   * 输入内容写评论
 * @author Administrator
 *
 */
public class Demo {
	static String name = null;
	static SeleniumUtils utils = null;
	
	
	Demo(){
		utils = SeleniumUtils.init("webdriver.Chrome.driver","H:\\\\chromedriver.exe");
		utils.setMaximize();
	}
	
	
	public static void timer4() {
	    Timer timer = new Timer();
	    timer.scheduleAtFixedRate(new TimerTask() {
	      public void run() {
	        System.out.println("-------设定要指定任务-------------------");
	        timer.cancel();
	      }
	    }, 0, 2000);// 这里设定将延时每天固定执行
	 }
	
	public static List<String> inputTalk() {
		Scanner sc = new Scanner(System.in); 
		List<String> talks = new ArrayList<>();
		
	    System.out.println("请输入商品名字");
	    name = sc.nextLine(); 
	    
	    while(true) {
	    	System.out.println("请输入评论内容,+号键结束");
	    	String talk = sc.nextLine(); 
	    	if(talk.equals("+")) {
	    		return talks;
	    	}else {
	    		talks.add(talk);
	    	}
	    }
	}
	
	
	static int s = 0;
	
	public void runTask(List<String> talks) {
		System.out.println("------------第"+s+"条正在执行---------");
		
		String searchName = name;
		
		utils.goPage("https://galinkltd.com/?s="+searchName.replace(" ","+")+"&post_type=product");
        
        //判断是否是直接搜索
        if(!utils.getCurrentUrl().startsWith("https://galinkltd.com/product/")) {
        	//点击product_thumbnail_background
        	utils.clickDOM(".product_thumbnail_background:nth-child(1)",2000);
        }
        
        //点击reviews_tab
        utils.clickDOM(".reviews_tab> a",2000);
        //点击五颗星
        utils.clickDOM(".star-5",2000);
        //输入内容
        utils.setVal("#comment",talks.get(s-1),2000);
        //输入名字
        utils.setVal("#author",EmailAndName.getName(),2000);
        //输入电子邮箱
        utils.setVal("#email",EmailAndName.getEmail(8, 12),2000); 
        //点击提交
        //clickDOM("#submit",2000);
        
        s--;
	}

	public static void main(String [] args) throws InterruptedException{
		Demo d = new Demo();
		List<String> talks = inputTalk();
		System.out.println("开始执行评论");
		
		Calendar calendar = Calendar.getInstance();
	    calendar.set(Calendar.HOUR_OF_DAY, 8); // 控制时
	    calendar.set(Calendar.MINUTE, 45);    // 控制分
	    calendar.set(Calendar.SECOND, 0);    // 控制秒
	    Date time = calendar.getTime();     // 得出执行任务的时间,此处为今天的12：00：00
		
		s = talks.size();
		
		Timer timer = new Timer();
	    timer.scheduleAtFixedRate(new TimerTask() {
	      public void run() {
	    	  
	    	d.runTask(talks);
	    	
	    	if(s==0) {
	    		timer.cancel();
	    	}
	      }
	    }, time, 7200000);
	}
}
