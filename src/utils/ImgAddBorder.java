package utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;


@SuppressWarnings("restriction")
public class ImgAddBorder {
	
 public static void main(String[] args) {
	 new ImgAddBorder().addBorderToImage("C:\\Users\\Administrator\\Desktop\\1.jpg");
 }

 public void addBorderToImage(String filePath) {
  try {
   File _file = new File(filePath); // 读入文件
   Image src = javax.imageio.ImageIO.read(_file); // 构造Image对象
   int width = src.getWidth(null); // 得到源图宽
   int height = src.getHeight(null); // 得到源图长

   BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
   Graphics graphics = image.getGraphics();
   graphics.drawImage(src, 0, 0, width, height, null); // 绘制图
   // 画边框

   graphics.setColor(Color.white);
   graphics.drawRect(0, 0, width - 1, height - 1);
   graphics.drawRect(1, 1, width - 1, height - 1);
   graphics.drawRect(0, 0, width-2, height- 2); 

   FileOutputStream out = new FileOutputStream(filePath); // 输出到文件流
JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
   encoder.encode(image);
   out.close();
  }
  catch (IOException e) {
   e.printStackTrace();
  }
 }
}

