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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;

import dao.IMGDAO;

public class Img9 {
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
    public static void downloadPicture(String urlList,String path) {
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
    //private static BufferedImage img;
    private static String ext;
    private static int width;
    private static int height;
    /*
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
    }*/
    
    public static void picUtils(String srcFile,int width, int height) throws IOException {
        //得到最后一个.的位置
        int indexs = srcFile.lastIndexOf(".");
        //获取被缩放的图片的格式
        String exts = srcFile.substring(indexs + 1);
        //获取目标路径(和原始图片路径相同,在文件名后添加了一个_s)
        String destFiles = srcFile.substring(0, indexs) + "." + exts;
        //读取图片,返回一个BufferedImage对象
        BufferedImage imgs = ImageIO.read(new File(srcFile));
        //获取图片的长和宽
        //width = img.getWidth();
        //height = img.getHeight();
        int widths = imgs.getWidth();
        int heights = imgs.getHeight();
        
        
        //与按比例缩放的不同只在于,不需要获取新的长和宽,其余相同.
        Image _img = imgs.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.drawImage(_img, 0, 0, null);
        graphics.dispose();
        OutputStream out = new FileOutputStream(destFiles);
        ImageIO.write(image, exts, out);
        out.close();
    }

    static String [] imgOver = {
    	"easy-washing-rectangle-microfiber-cat-beach-towel",
    	"Reusable-foldable-custom-grocery-shopping-bag-with",
    	"For-Baby-Warm-Custom-Sublimation-Printed-Blankets",
    	"printed-polyester-canvas-cover-cushion-with-custom",
    	"printed-amazing-microfiber-beach-towels-with-custom",
    	"good-quality-blank-hand-towel-15-25inch",
    	"Eco-nylon-reusable-plastic-foldable-custom-shopping",
    	"china-top-ten-selling-quick-dry-soft",
    	"customized-wholesale-low-price-cheap-round-beach",
    	"china-supplier-customized-beach-chair-towel-cotton",
    	"China-Supplier-provide-Summer-custom-print-microfiber",
    	"China-Supplier-provide-Customized-Beach-Towel-with.jpg",
    	"oversize-compressed-romantic-patterned-couples-beach-towels",
    	"customized-large-fashion-printed-never-faded-round",
    	"high-quality-custom-print-fitted-microfiber-beach",
    };

	
	public static boolean hcImg(String img1,String img2,String img3,String img4,String img5,
			String img6,String img7,String img8,String img9,int dir) throws Exception{
		
		String [] imgarray= {
			img1,img2,img3,img4,img5,img6,img7,img8,img9
		};
		
		for(int i=0;i<imgarray.length;i++) {
			int aa = 0;
			for(int j=0;j<imgarray.length;j++) {
				if(imgarray[i].equals(imgarray[j])) {
					aa++;
				}
				if(aa==2) {
					System.out.println(imgarray[i]+"----"+imgarray[j]);
					return false;
				}
				
				
			}
			
			for(int a=0;a<imgOver.length;a++) {
				if(imgarray[i].equals(imgOver[a])) {
					return false;
				}
				if(imgarray[i].indexOf(imgOver[a]) != -1) {
					return false;
				}
			}
		}
		
		
		System.out.println("工序:下载图片");
		//下载图片
		String imgurl1 = img1;
        String imgpath1 ="E:\\图片上传固定文件\\图片下载文件\\"+ThreadLocalRandom.current().nextInt(1,50000)+".jpg";
        downloadPicture(imgurl1,imgpath1);
        String imgurl2 = img2;
        String imgpath2 ="E:\\图片上传固定文件\\图片下载文件\\"+ThreadLocalRandom.current().nextInt(1,50000)+".jpg";
        downloadPicture(imgurl2,imgpath2);
        String imgurl3 = img3;
        String imgpath3 ="E:\\图片上传固定文件\\图片下载文件\\"+ThreadLocalRandom.current().nextInt(1,50000)+".jpg";
        downloadPicture(imgurl3,imgpath3);
        String imgurl4 = img4;
        String imgpath4 ="E:\\图片上传固定文件\\图片下载文件\\"+ThreadLocalRandom.current().nextInt(1,50000)+".jpg";
        downloadPicture(imgurl4,imgpath4);
        String imgurl5 = img5;
        String imgpath5 ="E:\\图片上传固定文件\\图片下载文件\\"+ThreadLocalRandom.current().nextInt(1,50000)+".jpg";
        downloadPicture(imgurl5,imgpath5);
        String imgurl6 = img6;
        String imgpath6 ="E:\\图片上传固定文件\\图片下载文件\\"+ThreadLocalRandom.current().nextInt(1,50000)+".jpg";
        downloadPicture(imgurl6,imgpath6);
        String imgurl7 = img7;
        String imgpath7 ="E:\\图片上传固定文件\\图片下载文件\\"+ThreadLocalRandom.current().nextInt(1,50000)+".jpg";
        downloadPicture(imgurl7,imgpath7);
        String imgurl8 = img8;
        String imgpath8 ="E:\\图片上传固定文件\\图片下载文件\\"+ThreadLocalRandom.current().nextInt(1,50000)+".jpg";
        downloadPicture(imgurl8,imgpath8);
        String imgurl9 = img9;
        String imgpath9 ="E:\\图片上传固定文件\\图片下载文件\\"+ThreadLocalRandom.current().nextInt(1,50000)+".jpg";
        downloadPicture(imgurl9,imgpath9);
        System.out.println("工序:下载图片完成");
        
        
        
        System.out.println("工序:图片裁剪开始");
        //编辑大小
        picUtils(imgpath1,300,300);
        //zoomBySize(300, 300);
        new ImgAddBorder().addBorderToImage(imgpath1);
        //编辑大小
        picUtils(imgpath2,300,300);
        //zoomBySize(300, 300);
        new ImgAddBorder().addBorderToImage(imgpath2);
        //编辑大小
        picUtils(imgpath3,300,300);
        //zoomBySize(300, 300);
        new ImgAddBorder().addBorderToImage(imgpath3);
        //编辑大小
        picUtils(imgpath4,300,300);
        //zoomBySize(300, 300);
        new ImgAddBorder().addBorderToImage(imgpath4);
      //编辑大小
        picUtils(imgpath5,300,300);
        //zoomBySize(300, 300);
        new ImgAddBorder().addBorderToImage(imgpath5);
      //编辑大小
        picUtils(imgpath6,300,300);
        //zoomBySize(300, 300);
        new ImgAddBorder().addBorderToImage(imgpath6);
      //编辑大小
        picUtils(imgpath7,300,300);
        //zoomBySize(300, 300);
        new ImgAddBorder().addBorderToImage(imgpath7);
      //编辑大小
        picUtils(imgpath8,300,300);
        //zoomBySize(300, 300);
        new ImgAddBorder().addBorderToImage(imgpath8);
      //编辑大小
        picUtils(imgpath9,300,300);
        //zoomBySize(300, 300);
        new ImgAddBorder().addBorderToImage(imgpath9);
        
        
        Thread.sleep(7000);
        System.out.println("工序:图片裁剪完成");
		
        System.out.println("工序:图片合成第一步开始");
		//合成图片
		List<File> files1 = new ArrayList<>();	
		File file1 = new File(imgpath1);
		File file2 = new File(imgpath2);
		File file3 = new File(imgpath3);
		files1.add(file1);
		files1.add(file2);
		files1.add(file3);
		String path = "E:\\图片上传固定文件\\半成品图片\\真实图片"+ThreadLocalRandom.current().nextInt(4,50000)+".jpg";
		jointPic(files1, path);
		
		//合成图片
		List<File> files2 = new ArrayList<>();	
		File file4 = new File(imgpath4);
		File file5 = new File(imgpath5);
		File file6 = new File(imgpath6);
		files2.add(file4);
		files2.add(file5);
		files2.add(file6);
		String path1 = "E:\\图片上传固定文件\\半成品图片\\真实图片"+ThreadLocalRandom.current().nextInt(4,50000)+".jpg";
		jointPic(files2, path1);
		
		//合成图片
		List<File> files3 = new ArrayList<>();	
		File file7 = new File(imgpath7);
		File file8 = new File(imgpath8);
		File file9 = new File(imgpath9);
		files3.add(file7);
		files3.add(file8);
		files3.add(file9);
		String path2 = "E:\\图片上传固定文件\\半成品图片\\真实图片"+ThreadLocalRandom.current().nextInt(4,50000)+".jpg";
		jointPic(files3, path2);
		
		System.out.println("工序:图片合成第一步完成");
		
		//全部合成
		//hb(dir);
		
		System.out.println("工序:图片合成第二步开始");
		List<File> files2ok = new ArrayList<>();	
		File file3ok = new File(path);
		File file4ok = new File(path1);
		File file5ok = new File(path2);
		
		files2ok.add(file3ok);
		files2ok.add(file4ok);
		files2ok.add(file5ok);
		
		String path1ok = "E:\\图片上传固定文件\\完成图片\\day04\\"+dir+".jpg";
		hxjoinPic(files2ok, path1ok);
		System.out.println("工序:图片合成第二步结束");
		
		
		//删除生成的没用图片
		delate(imgpath1);
		delate(imgpath2);
		delate(imgpath3);
		
		delate(imgpath4);
		delate(imgpath5);
		delate(imgpath6);
		
		delate(imgpath7);
		delate(imgpath8);
		delate(imgpath9);
		
		Thread.sleep(3000);
		
		delate(path);
		delate(path1);
		delate(path2);
		return true;
	}
	
	public static void delate(String imgsrc) {
		try{
            File file = new File(imgsrc);
            if(file.delete()){
                System.out.println(file.getName() + " 文件已被删除！");
            }else{
                System.out.println("文件删除失败！");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
	}
	
	static int getRan(int s,int e) {
		return (int)(1+Math.random()*(e-s+s));
	}
	
	static int i=0;
	
	public static void main(String[] args) throws Exception {
		
		//多线程执行
		ExecutorService executor = Executors.newFixedThreadPool(10);
        for (i = 0; i < 2000; i++) {
            executor.submit(() -> {
                //System.out.println("thread id is: " + Thread.currentThread().getId());
		
		//for(int i=1;i<2001;i++) {
			//随机生成产品名字
			int imgDir = ThreadLocalRandom.current().nextInt(1,50000);
			
			System.out.println("---------------------------------------------------");
			System.out.println("正在处理 "+imgDir+" 图片");
			
			//读取csv
			ArrayList<IMGDAO> list = null;
			try {
				list = CSVUtils.read();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			IMGDAO d1 = list.get(getRan(0, list.size()-1));
			IMGDAO d2 = list.get(getRan(0, list.size()-1));
			IMGDAO d3 = list.get(getRan(0, list.size()-1));
			IMGDAO d4 = list.get(getRan(0, list.size()-1));
			IMGDAO d5 = list.get(getRan(0, list.size()-1));
			IMGDAO d6 = list.get(getRan(0, list.size()-1));
			IMGDAO d7 = list.get(getRan(0, list.size()-1));
			IMGDAO d8 = list.get(getRan(0, list.size()-1));
			IMGDAO d9 = list.get(getRan(0, list.size()-1));
			
			//合成数据
			boolean aa = false;
			try {
				aa = hcImg(d1.getUrl(), d2.getUrl(), d3.getUrl(), d4.getUrl(),d5.getUrl(),d6.getUrl(),d7.getUrl(),d8.getUrl(),d9.getUrl(),imgDir);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if(aa == false) {
				System.err.println("有相同的");
				return;
			}
			
			//https://www.alibaba.com/product-detail/China-Supply-Customization-Colorful-Promotional-Canvas_60769276405.html
			String url = "https://www.alibaba.com/product-detail/China-Supply-Customization-Colorful-Promotional-Canvas_";
			d1.setName(url+d1.getId()+".html");
			d2.setName(url+d2.getId()+".html");
			d3.setName(url+d3.getId()+".html");
			d4.setName(url+d4.getId()+".html");
			d5.setName(url+d5.getId()+".html");
			d6.setName(url+d6.getId()+".html");
			d7.setName(url+d7.getId()+".html");
			d8.setName(url+d8.getId()+".html");
			d9.setName(url+d9.getId()+".html");
			
			System.out.println("转换数据");
			//转换成数据
			String data = "<div style='width:750px;'>" + 
					"	<img src='真实图片3.jpg' border='0' usemap='#planetmap' alt='Planets' />" + 
					"	<map name='planetmap' id='planetmap'>" + 
					"	  <area shape='circle' coords='150,150,150' href ='"+d1.getName()+"' alt='Venus' />" + 
					"	  <area shape='circle' coords='450,150,150' href ='"+d4.getName()+"' alt='Mercury' />" + 
					"	  <area shape='circle' coords='750,150,150' href ='"+d7.getName()+"' alt='Mercury' />" + 
					
					"	  <area shape='circle' coords='150,450,150' href ='"+d2.getName()+"' alt='Sun' />" + 
					"	  <area shape='circle' coords='450,450,150' href ='"+d5.getName()+"' alt='Sun' />" + 
					"	  <area shape='circle' coords='750,450,150' href ='"+d8.getName()+"' alt='Sun' />" + 
		
					"	  <area shape='circle' coords='150,750,150' href ='"+d3.getName()+"' alt='Sun' />" + 
					"	  <area shape='circle' coords='450,750,150' href ='"+d6.getName()+"' alt='Sun' />" + 
					"	  <area shape='circle' coords='750,750,150' href ='"+d9.getName()+"' alt='Sun' />" + 
					"	</map>" + 
					"</div>";
			System.out.println("输入文件");
			//创建一个txt文件保存数据
			File file =new File("E:\\图片上传固定文件\\完成图片\\day04\\"+imgDir+".txt");
			Writer out = null;
			try {
				out = new FileWriter(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				out.write(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("完成");
			System.out.println("---------------------------------------------------");
			//System.out.println(data);
		//}
            });
        }
	}
	
	
	
	static void hb(int imgDir) throws Exception {
		System.out.println("工序:图片合成第二步开始");
		List<File> files2 = new ArrayList<>();	
		File file3 = new File("E:\\图片上传固定文件\\半成品图片\\真实图片1.jpg");
		File file4 = new File("E:\\图片上传固定文件\\半成品图片\\真实图片2.jpg");
		File file5 = new File("E:\\图片上传固定文件\\半成品图片\\真实图片3.jpg");
		
		files2.add(file3);
		files2.add(file4);
		files2.add(file5);
		
		String path1 = "E:\\图片上传固定文件\\完成图片\\day04\\"+imgDir+".jpg";
		hxjoinPic(files2, path1);
		System.out.println("工序:图片合成第二步结束");
	}
}	

