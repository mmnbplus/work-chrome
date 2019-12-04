package taobao;

import java.io.IOException;
import java.util.ArrayList;

import dao.IMGDAO;
import utils.CSVUtils;
import utils.Img9;
import utils.ImgAddBorder;

public class DomloadAllImg {
	public static void main(String[] args) throws IOException {
		//读取csv
		ArrayList<IMGDAO> list = CSVUtils.read();
		
		for(int i=0;i<list.size();i++) {
			String imghPath = "E:\\图片上传固定文件\\所有完成图片\\"+list.get(i).getId()+".jpg";
			Img9.downloadPicture(list.get(i).getUrl(), imghPath);
			Img9.picUtils(imghPath,300,300);
			new ImgAddBorder().addBorderToImage(imghPath);
		}
	}
}
