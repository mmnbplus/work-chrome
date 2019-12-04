package taobao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import dao.IMGDAO;
import utils.CSVUtils;
import utils.Img9;

public class GetNewImg9 {
	
	static ArrayList<IMGDAO> list = null;
	
	public static void getAllFileName(String path,ArrayList<String> fileNameList) {
        //ArrayList<String> files = new ArrayList<String>();
        boolean flag = false;
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
//              System.out.println("文     件：" + tempList[i]);
                //fileNameList.add(tempList[i].toString());
                fileNameList.add(tempList[i].getName());
            }
            if (tempList[i].isDirectory()) {
//              System.out.println("文件夹：" + tempList[i]);
                getAllFileName(tempList[i].getAbsolutePath(),fileNameList);
            }
        }
        return;
	}
	
	public static void main(String[] args) throws IOException {
		
		for(int mm=0;mm<50;mm++) {
		//读取csv
		list = CSVUtils.read();
		
		ArrayList<String> okimg = new ArrayList<String>();
		getAllFileName("E:\\图片上传固定文件\\所有完成图片",okimg);
		
		
		//选取图片
		HashSet<String> set = new HashSet<String>();
		ArrayList<String> url = new ArrayList<String>();
		
		while(true) {
			int id = ThreadLocalRandom.current().nextInt(0,okimg.size());
			set.add("E:\\图片上传固定文件\\所有完成图片\\"+okimg.get(id));
			
			for(int i=0;i<list.size();i++) {
				//System.out.println(list.get(i).getId());
				//System.out.println(okimg.get(id));
				if((list.get(i).getId()+".jpg").equals(okimg.get(id))) {
					System.out.println(list.get(i));
					String name = list.get(i).getName().replaceAll(" ", "-");
					String tburl = "https://www.alibaba.com/product-detail/"+name+"_";
					url.add(tburl+list.get(i).getId()+".html");
				}
			}
			//url.add()
			if(set.size()>9) {
				break;
			}
		}
		
		System.out.println(url);
		
		String[] string = set.toArray(new String[set.size()]);
		
		//合成图片
		List<File> files1 = new ArrayList<>();	
		File file1 = new File(string[0].toString());
		File file2 = new File(string[1].toString());
		File file3 = new File(string[2].toString());
		files1.add(file1);
		files1.add(file2);
		files1.add(file3);
		String path = "E:\\图片上传固定文件\\半成品图片\\真实图片哈哈哈\\1.jpg";
		System.out.println(string[0]);
		Img9.jointPic(files1, path);
		
		//合成图片
		List<File> files2 = new ArrayList<>();	
		File file4 = new File(string[3].toString());
		File file5 = new File(string[4].toString());
		File file6 = new File(string[5].toString());
		files2.add(file4);
		files2.add(file5);
		files2.add(file6);
		String path1 = "E:\\图片上传固定文件\\半成品图片\\真实图片哈哈哈\\2.jpg";
		Img9.jointPic(files2, path1);
		
		//合成图片
		List<File> files3 = new ArrayList<>();	
		File file7 = new File(string[6].toString());
		File file8 = new File(string[7].toString());
		File file9 = new File(string[8].toString());
		files3.add(file7);
		files3.add(file8);
		files3.add(file9);
		String path2 = "E:\\图片上传固定文件\\半成品图片\\真实图片哈哈哈\\3.jpg";
		Img9.jointPic(files3, path2);
		
		
		int dir = ThreadLocalRandom.current().nextInt(1,50000);
		
		List<File> files2ok = new ArrayList<>();	
		File file3ok = new File(path);
		File file4ok = new File(path1);
		File file5ok = new File(path2);
		
		files2ok.add(file3ok);
		files2ok.add(file4ok);
		files2ok.add(file5ok);
		
		String path1ok = "E:\\图片上传固定文件\\完成图片\\day05\\"+dir+".jpg";
		Img9.hxjoinPic(files2ok, path1ok);
		System.out.println("工序:图片合成第二步结束");
		
		Img9.delate(path);
		Img9.delate(path1);
		Img9.delate(path2);
		
		
		System.out.println("转换数据");
		//转换成数据
		String data = "<div style='width:750px;'>" + 
				"	<img src='真实图片3.jpg' border='0' usemap='#planetmap' alt='Planets' />" + 
				"	<map name='planetmap' id='planetmap'>" + 
				"	  <area shape='circle' coords='150,150,150' href ='"+url.get(0)+"' alt='Venus' />" + 
				"	  <area shape='circle' coords='450,150,150' href ='"+url.get(1)+"' alt='Mercury' />" + 
				"	  <area shape='circle' coords='750,150,150' href ='"+url.get(2)+"' alt='Mercury' />" + 
				
				"	  <area shape='circle' coords='150,450,150' href ='"+url.get(3)+"' alt='Sun' />" + 
				"	  <area shape='circle' coords='450,450,150' href ='"+url.get(4)+"' alt='Sun' />" + 
				"	  <area shape='circle' coords='750,450,150' href ='"+url.get(5)+"' alt='Sun' />" + 
	
				"	  <area shape='circle' coords='150,750,150' href ='"+url.get(6)+"' alt='Sun' />" + 
				"	  <area shape='circle' coords='450,750,150' href ='"+url.get(7)+"' alt='Sun' />" + 
				"	  <area shape='circle' coords='750,750,150' href ='"+url.get(8)+"' alt='Sun' />" + 
				"	</map>" + 
				"</div>";
		System.out.println("输入文件");
		//创建一个txt文件保存数据
		File file =new File("E:\\图片上传固定文件\\完成图片\\day05\\"+dir+".txt");
		Writer out = null;
		
			out = new FileWriter(file);
			out.write(data);
			out.close();
		
		System.out.println("完成");
		}
	}
	
}
