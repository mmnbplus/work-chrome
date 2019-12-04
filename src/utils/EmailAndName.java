package utils;

import com.lu.sn.NameType;
import com.lu.sn.RandomNameTool;

public class EmailAndName {
	public static String base = "abcdefghijklmnopqrstuvwxyz0123456789"; 
	public static final String[] email_suffix="@gmail.com,@yahoo.com,@msn.com,@hotmail.com,@aol.com,@ask.com,@live.com,@qq.com,@0355.net,@163.com,@163.net,@263.net,@3721.net,@yeah.net,@googlemail.com,@126.com,@sina.com,@sohu.com,@yahoo.com.cn".split(",");  

	public static int getNum(int start,int end) {    
        return (int)(Math.random()*(end-start+1)+start);    
    }
	
	public static String getEmail(int lMin,int lMax) {    
         int length=getNum(lMin,lMax);    
         StringBuffer sb = new StringBuffer();         
         for (int i = 0; i < length; i++) {         
             int number = (int)(Math.random()*base.length());    
             sb.append(base.charAt(number));         
         }    
         sb.append(email_suffix[(int)(Math.random()*email_suffix.length)]);    
         return sb.toString();       
     }
	
	
	
	public static String getName() {
         String chineseName = RandomNameTool.getName(com.lu.sn.Language.en,NameType.FULL_NAME);
         return chineseName;
    }
}
