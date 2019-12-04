package demo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
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
 
        Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
        Matcher m_space = p_space.matcher(htmlStr);
        htmlStr = m_space.replaceAll(""); // 过滤空格回车标签
        return htmlStr.trim(); // 返回文本字符串
    }
    
    public static String getTextFromHtml(String htmlStr){
    	htmlStr = delHTMLTag(htmlStr);
    	htmlStr = htmlStr.replaceAll(" ", "");
    	htmlStr = htmlStr.substring(0, htmlStr.indexOf("。")+1);
    	return htmlStr;
    }
	
	public static void main(String [] args) {
		/*
		String reg = "<ul(?:\\s([^>]+=(['\"]).*?\\2)*)?>[^<]+</ul>";
		Pattern pat = Pattern.compile(reg);
		Matcher m = pat.matcher("aa<ul><li><span style='font-size: 14px;'>Many years of experience in operating <span style='color: #ff0000;'><strong>Amazon</strong></span> and serving as <strong><span style='color: #ff0000;'>Amazon's supplier</span></strong>. We can provide you with consulting services that meet Amazon's requirements in packaging,labeling, transportation and other aspects.</span></li><li><span style='font-size: 14px;'> Provide<strong><span style='color: #ff0000;'> customized services</span></strong>, including <strong><span style='color: #ff0000;'>size, style, pattern, logo, labeling</span></strong> and <strong><span style='color: #ff0000;'>packaging</span></strong>.</span></li><li><span style='font-size: 14px;'> Samples will be made within 48 hours after receiving your design, and <strong><span style='color: #ff0000;'>free samples</span> </strong>can be provided for your reference.</span></li><li><span style='font-size: 14px;'><strong><span style='color: #ff0000;'> Eco-friendly</span></strong>, recycling and renewable, achieve any fabric you want. Support <strong><span style='color: #ff0000;'>affiliate marketing</span></strong>, contact customer service for details.</span></li></ul>");
		while (m.find()){
		    System.out.println(m.group(0));
		}*/
		
		//String s="<img src='11' alt='22' /><img src='111' alt='222' /><img src='1111' alt='2222' />";
		//System.out.println(getImgStr(s));
		
		
		String str = "<div style='text-align:center;'> 整治“四风”   清弊除垢<br/><span style='font-size:14px;'> </span><span style='font-size:18px;'>公司召开党的群众路线教育实践活动动员大会</span><br/></div>";
		System.out.println(delHTMLTag(str));
	}
	
	/*
	private static List<String> getImgStr(String htmlStr) {
        List<String> pics = new ArrayList<String>();
        String img = "";
        Pattern p_image;
        Matcher m_image;
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            // 得到<img />数据
            img = m_image.group();
            System.out.println(img);
            
            // 匹配<img>中的src数据
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                pics.add(m.group(1));
            }
            Matcher s = Pattern.compile("alt\\a*=\\a*\"?(.*?)(\"|>|\\a+)").matcher(img);
            while (s.find()) {
                pics.add(s.group(1));
            }
        }
        return pics;
    }*/
	
	private static List<ImgDao> getImgStr(String htmlStr) {
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
            }
        }
        
        
        return pics;
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
