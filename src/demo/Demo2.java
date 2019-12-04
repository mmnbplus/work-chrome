package demo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class Demo2 {
	static char separator = ','; 
	   
    public static void main(String[] args) throws Exception { 
   
        // ���Ե��� 
    	/*
        String filePath = "D:/test.csv"; 
        List<String[]> dataList = new ArrayList<String[]>(); 
        for (int i = 0; i < 10; i++) { 
            dataList.add(new String[] { "0" + i, "С��" + i, "java" + i }); 
        } 
        exportCsv(dataList, filePath); */
   
    	String filePath = "C:/Users/Administrator/Desktop/demo.csv"; 
        // ���Ե��� 
        List<String[]> datas = importCsv(filePath); 
        for (String[] strings : datas) { 
            System.out.println(strings[0]); 
        } 
    } 
   
    /**
     * java����csv�ļ�
     * 
     * @param filePath
     *            ����·��
     * @return
     * @throws Exception
     */ 
    public static List<String[]> importCsv(String filePath) throws Exception { 
        CsvReader reader = null; 
        List<String[]> dataList = new ArrayList<String[]>(); 
        try { 
            reader = new CsvReader(filePath, separator, Charset.forName("GBK")); 
   
            // ��ȡ��ͷ  ������һ���ǲ����ͷ���ݴӵڶ��п�ʼȡ
            reader.readHeaders(); 
            // ������ȡ��¼��ֱ������ 
            while (reader.readRecord()) { 
                dataList.add(reader.getRawRecord().split(",")); 
                // // �����Ǽ������õķ��� 
                // ��ȡһ����¼ 
                System.out.println(reader.getRawRecord()); 
                // ��������ȡ������¼��ֵ 
                System.out.println(reader.get(0)); 
                System.out.println(reader.get(1)); 
                System.out.println(reader.get(2)); 
                System.out.println(reader.get(3)); 
            } 
        } catch (Exception e) { 
            System.out.println("��ȡCSV����..." + e); 
            throw e; 
        } finally { 
            if (null != reader) { 
                reader.close(); 
            } 
        } 
   
        return dataList; 
    } 
   
    /**
     * java����cvs�ļ�
     * 
     * @param dataList
     *            ���ݼ�
     * @param filePath
     *            ����·��
     * @return
     * @throws Exception
     */ 
    public static boolean exportCsv(List<String[]> dataList, String filePath) throws Exception { 
        boolean isSuccess = false; 
        CsvWriter writer = null; 
        FileOutputStream out = null; 
        try { 
            out = new FileOutputStream(filePath, true); 
            writer = new CsvWriter(out, separator, Charset.forName("GBK")); 
            for (String[] strs : dataList) { 
                writer.writeRecord(strs); 
            } 
   
            isSuccess = true; 
        } catch (Exception e) { 
            System.out.println("����CSV����..." + e); 
            throw e; 
        } finally { 
            if (null != writer) { 
                writer.close(); 
            } 
            if (null != out) { 
                try { 
                    out.close(); 
                } catch (IOException e) { 
                    System.out.println("exportCsv close Exception: " + e); 
                    throw e; 
                } 
            } 
        } 
   
   
        return isSuccess; 
    } 
}
