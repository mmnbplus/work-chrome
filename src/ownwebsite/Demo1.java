package ownwebsite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import dao.TalkDAO;
import utils.EmailAndName;
import utils.SeleniumUtils;

/**
   *  读取csv写评论
 * @author Administrator
 *
 */
public class Demo1 {
	
	static String name = null;
	static SeleniumUtils utils = null;
	
	Demo1(){
		utils = SeleniumUtils.init("webdriver.Chrome.driver","H:\\\\chromedriver.exe");
		utils.setMaximize();
	}
	
	public static void main(String [] args) {
		//读取csv文件
		List<String> dataList = importCsv(new File("C:\\Users\\Administrator\\Desktop\\mmdemo.csv")); 
        if (dataList != null && !dataList.isEmpty()) { 
            for (String data : dataList) { 
                System.out.println(data); 
            } 
        } 
        
       List<TalkDAO> comm = new ArrayList<>();
        for (String data : dataList) { 
        	String [] d = data.split("\\,");
        	
        	TalkDAO dao = new TalkDAO();
        	dao.setName(d[0]);
        	dao.setTalk(d[1]);
        	comm.add(dao);
        } 
        
        System.err.println(comm);
        
        Demo1.timer4(comm);
	}
	
	static int s = 0;
	
	public static void timer4(List<TalkDAO> comm) {
		Demo1 d = new Demo1();
		Timer timer = new Timer();
		Calendar calendar = Calendar.getInstance();
	    calendar.set(Calendar.HOUR_OF_DAY, 9); // 控制时
	    calendar.set(Calendar.MINUTE, 58);    // 控制分
	    calendar.set(Calendar.SECOND, 0);    // 控制秒
	    Date time = calendar.getTime();     // 得出执行任务的时间,此处为今天的12：00：00
	    
	    s = comm.size();
	    
	    timer.scheduleAtFixedRate(new TimerTask() {
	      public void run() {
	    	  d.runTask(comm,s);
	    	  s--;
	    	  if(s==0) {
	    		System.out.println("完成退出定时任务");
		    	timer.cancel();
		      }
	      }
	    }, time, 50000);
	 }
	
	
	public void runTask(List<TalkDAO> talk,int s) {
		System.out.println("------------第"+s+"条正在执行---------");
		
		String commUrl = talk.get(s-1).getName().replace(" ","+");
		
		System.err.println(commUrl);
		
		utils.goPage("https://galinkltd.com/?s="+commUrl+"&post_type=product");
        
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
        utils.setVal("#comment",talk.get(s-1).getTalk(),2000);
        //输入名字
        utils.setVal("#author",EmailAndName.getName(),2000);
        //输入电子邮箱
        utils.setVal("#email",EmailAndName.getEmail(8, 12),2000); 
        //点击提交
        //clickDOM("#submit",2000);
	}
	
	public static List<String> importCsv(File file) { 
        List<String> dataList = new ArrayList<String>(); 
   
        BufferedReader br = null; 
        try { 
            br = new BufferedReader(new FileReader(file)); 
            String line = ""; 
            while ((line = br.readLine()) != null) { 
                dataList.add(line); 
            } 
        } catch (Exception e) { 
        } finally { 
            if (br != null) { 
                try { 
                    br.close(); 
                    br = null; 
                } catch (IOException e) { 
                    e.printStackTrace(); 
                } 
            } 
        } 
   
        return dataList; 
    } 
}
