package utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;

import com.csvreader.CsvReader;

import dao.IMGDAO;

public class CSVUtils {
	public static ArrayList<IMGDAO> read() throws IOException {
		CsvReader csvReader = new CsvReader("E:\\爬取商品\\connodityDB.csv", ',', Charset.forName("UTF-8"));

        // 如果你的文件没有表头，这行不用执行
        // 这行不要是为了从表头的下一行读，也就是过滤表头
        csvReader.readHeaders();
        ArrayList<IMGDAO> list = new ArrayList<>();
        // 读取每行的内容
        while (csvReader.readRecord()) {
            //System.out.print(csvReader.get(0));
        	IMGDAO dao = new IMGDAO();
        	dao.setId(csvReader.get("SKU"));
        	dao.setName(csvReader.get("Name"));
        	dao.setUrl(csvReader.get("Images"));
           list.add(dao);
        }
        
        return list;
	}
	
	
	public static ArrayList<IMGDAO> read(String path) throws IOException {
		CsvReader csvReader = new CsvReader(path, ',', Charset.forName("UTF-8"));

        // 如果你的文件没有表头，这行不用执行
        // 这行不要是为了从表头的下一行读，也就是过滤表头
        csvReader.readHeaders();
        ArrayList<IMGDAO> list = new ArrayList<>();
        // 读取每行的内容
        while (csvReader.readRecord()) {
            //System.out.print(csvReader.get(0));
        	IMGDAO dao = new IMGDAO();
        	dao.setId(csvReader.get("SKU"));
        	dao.setName(csvReader.get("Name"));
        	dao.setUrl(csvReader.get("Images"));
           list.add(dao);
        }
        
        return list;
	}
	
	public static void main(String[] args) throws IOException {
		CSVUtils.read();
	}
}
