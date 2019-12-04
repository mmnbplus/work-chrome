package utils;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;

import dao.IMGDAO;

public class Img {
	public static void jointPic(List<File> files, String path) {   
		try {
			Integer allWidth = 0;	// 图片总宽度
			Integer allHeight = 0;	// 图片总高度
			List<BufferedImage> imgs = new ArrayList<>(); 
			for(int i=0; i<files.size(); i++){
				imgs.add(ImageIO.read(files.get(i)));
				
				//竖向
				if (i==0) {
					allWidth = imgs.get(0).getWidth();
				}
				allHeight += imgs.get(i).getHeight();
			}
			System.out.println(allWidth + ","  +allHeight);
			
			
	        BufferedImage combined = new BufferedImage(allWidth, allHeight, BufferedImage.TYPE_INT_RGB);  
	        // paint both images, preserving the alpha channels  
	        Graphics g = combined.getGraphics();
	         // 竖向合成
	        Integer height = 0;
	        for(int i=0; i< imgs.size(); i++){
        		g.drawImage(imgs.get(i), 0, height, null);  
        		height +=  imgs.get(i).getHeight();
	        }
	        ImageIO.write(combined, "jpg", new File(path));  
	        System.out.println("===合成成功====");
		} catch (Exception e) {
			System.out.println("===合成失败====");
			e.printStackTrace();
		}
    }
	
	
	public static void hxjoinPic(List<File> files, String path) {
		System.out.println(files.size());
		try {
			Integer allWidth = 0;	// 图片总宽度
			Integer allHeight = 0;	// 图片总高度
			List<BufferedImage> imgs = new ArrayList<>();
			for(int i=0; i<files.size(); i++){
				imgs.add(ImageIO.read(files.get(i)));
				// 横向
				if (i==0) {
					allHeight = imgs.get(0).getHeight();
				}
				allWidth += imgs.get(i).getWidth();
			}
			
	        BufferedImage combined = new BufferedImage(allWidth, allHeight, BufferedImage.TYPE_INT_RGB);  
	        // paint both images, preserving the alpha channels  
	        Graphics g = combined.getGraphics();
	     // 横向合成
	        Integer width = 0;
	        for(int i=0; i< imgs.size(); i++){
	        	g.drawImage(imgs.get(i), width, 0, null);
	        	width +=  imgs.get(i).getWidth();
	        }
	        ImageIO.write(combined, "jpg", new File(path));  
	        System.out.println("===合成成功====");
		} catch (Exception e) {
			System.out.println("===合成失败====");
			e.printStackTrace();
		}
	}
	
	//链接url下载图片
    private static void downloadPicture(String urlList,String path) {
        URL url = null;
        try {
            url = new URL(urlList);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());
 
            FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
 
            byte[] buffer = new byte[1024];
            int length;
 
            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    private static String destFile;
    private static BufferedImage img;
    private static String ext;
    private static int width;
    private static int height;
    
    public static void zoomBySize(int width, int height) throws IOException {
        //与按比例缩放的不同只在于,不需要获取新的长和宽,其余相同.
        Image _img = img.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.drawImage(_img, 0, 0, null);
        graphics.dispose();
        OutputStream out = new FileOutputStream(destFile);
        ImageIO.write(image, ext, out);
        out.close();
    }
    
    public static void picUtils(String srcFile) throws IOException {
        //得到最后一个.的位置
        int index = srcFile.lastIndexOf(".");
        //获取被缩放的图片的格式
        ext = srcFile.substring(index + 1);
        //获取目标路径(和原始图片路径相同,在文件名后添加了一个_s)
        //destFile = srcFile.substring(0, index) + "_s." + ext;
        destFile = srcFile.substring(0, index) + "." + ext;
        //读取图片,返回一个BufferedImage对象
        img = ImageIO.read(new File(srcFile));
        //获取图片的长和宽
        //width = img.getWidth();
        //height = img.getHeight();
        width = img.getWidth();
        height = img.getHeight();
    }


	
	public static void hcImg(String img1,String img2,String img3,String img4,int dir) throws Exception{
		//下载图片
		String imgurl1 = img1;
        String imgpath1 ="E:\\图片上传固定文件\\图片下载文件\\1.jpg";
        downloadPicture(imgurl1,imgpath1);
        String imgurl2 = img2;
        String imgpath2 ="E:\\图片上传固定文件\\图片下载文件\\2.jpg";
        downloadPicture(imgurl2,imgpath2);
        String imgurl3 = img3;
        String imgpath3 ="E:\\图片上传固定文件\\图片下载文件\\3.jpg";
        downloadPicture(imgurl3,imgpath3);
        String imgurl4 = img4;
        String imgpath4 ="E:\\图片上传固定文件\\图片下载文件\\4.jpg";
        downloadPicture(imgurl4,imgpath4);
        
        //编辑大小
        picUtils(imgpath1);
        zoomBySize(300, 300);
        //编辑大小
        picUtils(imgpath2);
        zoomBySize(300, 300);
        //编辑大小
        picUtils(imgpath3);
        zoomBySize(300, 300);
        //编辑大小
        picUtils(imgpath4);
        zoomBySize(300, 300);
		
        
		//合成图片
		List<File> files1 = new ArrayList<>();	
		File file1 = new File("E:\\图片上传固定文件\\图片下载文件\\1.jpg");
		File file2 = new File("E:\\图片上传固定文件\\图片下载文件\\2.jpg");
		files1.add(file1);
		files1.add(file2);
		String path = "E:\\图片上传固定文件\\半成品图片\\真实图片1.jpg";
		jointPic(files1, path);
		
		//合成图片
		List<File> files2 = new ArrayList<>();	
		File file3 = new File("E:\\图片上传固定文件\\图片下载文件\\3.jpg");
		File file4 = new File("E:\\图片上传固定文件\\图片下载文件\\4.jpg");
		files2.add(file3);
		files2.add(file4);
		String path1 = "E:\\图片上传固定文件\\半成品图片\\真实图片2.jpg";
		jointPic(files2, path1);
		
		//全部合成
		hb(dir);
	}
	
	static int getRan(int s,int e) {
		return (int)(1+Math.random()*(e-s+s));
	}
	
	public static void main(String[] args) throws Exception {
		
		
		for(int i=1;i<200;i++) {
			
			int imgDir = i;
		
		//读取csv
		ArrayList<IMGDAO> list = CSVUtils.read();
		System.out.println(list.size());
		
		IMGDAO d1 = list.get(getRan(0, list.size()-1));
		IMGDAO d2 = list.get(getRan(0, list.size()-1));
		IMGDAO d3 = list.get(getRan(0, list.size()-1));
		IMGDAO d4 = list.get(getRan(0, list.size()-1));
		
		//合成数据
		hcImg(d1.getUrl(), d2.getUrl(), d3.getUrl(), d4.getUrl(),imgDir);
		//https://www.alibaba.com/product-detail/China-Supply-Customization-Colorful-Promotional-Canvas_60769276405.html
		String url = "https://www.alibaba.com/product-detail/China-Supply-Customization-Colorful-Promotional-Canvas_";
		d1.setName(url+d1.getId()+".html");
		d2.setName(url+d2.getId()+".html");
		d3.setName(url+d3.getId()+".html");
		d4.setName(url+d4.getId()+".html");
		
		System.out.println(d1.getName());
		
		
		//转换成数据
		String data = "<div>" + 
				"	<img src='真实图片3.jpg' border='0' usemap='#planetmap' alt='Planets' />" + 
				"	<map name='planetmap' id='planetmap'>" + 
				"	  <area shape='circle' coords='150,150,150' href ='"+d1.getName()+"' alt='Venus' />" + 
				"	  <area shape='circle' coords='450,150,150' href ='"+d3.getName()+"' alt='Mercury' />" + 
				"	  <area shape='circle' coords='150,450,150' href ='"+d2.getName()+"' alt='Sun' />" + 
				"	  <area shape='circle' coords='450,450,150' href ='"+d4.getName()+"' alt='Sun' />" + 
				"	</map>" + 
				"</div>";
		
		//创建一个txt文件保存数据
		File file =new File("E:\\图片上传固定文件\\完成图片\\day01\\"+imgDir+".txt");
		Writer out =new FileWriter(file);
		out.write(data);
		out.close();
		
		System.out.println(data);
		}
	}
	
	
	
	static void hb(int imgDir) throws Exception {
		List<File> files2 = new ArrayList<>();	
		File file3 = new File("E:\\图片上传固定文件\\半成品图片\\真实图片1.jpg");
		File file4 = new File("E:\\图片上传固定文件\\半成品图片\\真实图片2.jpg");
		
		files2.add(file3);
		files2.add(file4);
		
		//File file=new File("E:\\图片上传固定文件\\完成图片\\"+imgDir);
		//if(!file.exists()){//如果文件夹不存在
			//file.mkdir();//创建文件夹
		//}else {
		//	throw new Exception();
		//}
		
		
		String path1 = "E:\\图片上传固定文件\\完成图片\\day01\\"+imgDir+".jpg";
		hxjoinPic(files2, path1);
	}
}	

