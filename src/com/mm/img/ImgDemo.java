package com.mm.img;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * 把两张图片合并
 * @version 2018-2-27 上午11:12:09
 *
 */
public class ImgDemo
{
     private Graphics2D g = null;  
      
    /**  
     * 导入本地图片到缓冲区  
     */  
    public BufferedImage loadImageLocal(String imgName) {  
        try {  
            return ImageIO.read(new File(imgName));  
        } catch (IOException e) {  
            System.out.println(e.getMessage());  
        }  
        return null;  
    } 
    
    public BufferedImage modifyImagetogeter(BufferedImage b, BufferedImage d) {  
        
        try {  
            int w = b.getWidth();  
            int h = b.getHeight();  
  
            g = d.createGraphics();  
            g.drawImage(b, 300, -800, w, h, null);  
            g.dispose();  
        } catch (Exception e) {  
            System.out.println(e.getMessage());  
        }  
  
        return d;  
    } 
    
    /**  
     * 生成新图片到本地  
     */  
    public void writeImageLocal(String newImage, BufferedImage img) {  
        if (newImage != null && img != null) {  
            try {  
                File outputfile = new File(newImage);  
                ImageIO.write(img, "jpg", outputfile);  
            } catch (IOException e) {  
                System.out.println(e.getMessage());  
            }  
        }  
    } 
    
    public static byte[] readInputStream(InputStream inStream) throws Exception{  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        //创建一个Buffer字符串  
        byte[] buffer = new byte[1024];  
        //每次读取的字符串长度，如果为-1，代表全部读取完毕  
        int len = 0;  
        //使用一个输入流从buffer里把数据读取出来  
        while( (len=inStream.read(buffer)) != -1 ){  
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度  
            outStream.write(buffer, 0, len);  
        }  
        //关闭输入流  
        inStream.close();  
        //把outStream里的数据写入内存  
        return outStream.toByteArray();  
    }  
        
    public static void main(String[] args) throws Exception {  
        
    	//new一个URL对象  
        URL url = new URL("http://sc02.alicdn.com/kf/HTB1PxjBacfrK1RkSmLyq6xGApXa7/New-women-customized-microfiber-poncho-hooded-bath.jpg_350x350.jpg");  
        //打开链接  
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
        //设置请求方式为"GET"  
        conn.setRequestMethod("GET");  
        //超时响应时间为5秒  
        conn.setConnectTimeout(5 * 1000);  
        //通过输入流获取图片数据  
        InputStream inStream = conn.getInputStream();  
        //得到图片的二进制数据，以二进制封装得到数据，具有通用性  
        byte[] data = readInputStream(inStream);  
        //new一个文件对象用来保存图片，默认保存当前工程根目录  
        File imageFile = new File("C:\\Users\\Administrator\\Desktop\\pic20170419.jpg");  
        //创建输出流  
        FileOutputStream outStream = new FileOutputStream(imageFile);  
        //写入数据  
        outStream.write(data);  
        //关闭输出流  
        outStream.close();   
    	
        ImgDemo tt = new ImgDemo();  	
        
        BufferedImage d = tt.loadImageLocal("C:\\Users\\Administrator\\Desktop\\pic20170419.jpg");  
        BufferedImage b = tt.loadImageLocal("C:\\Users\\Administrator\\Desktop\\112233.jpg");
        
        tt.writeImageLocal("C:/new10.jpg", tt.modifyImagetogeter(b, d));    
        //将多张图片合在一起    
        System.out.println("success");  
    } 
}