package com.share.calendar.common.Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class diskFree {
	
	static Date date = new Date();
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	static String log_date = "[" + sdf.format(date) + "] ";
	
	public static void main(String[] args) {
		diskFree();		
		diskFreeRead();
	}
	
	/**
	 * Disk Free �뷮 ��� �� ���Ϸ� ������
	 */
	public static void diskFree() {
		System.out.println(log_date + "================ 1. diskFree ����");

        String content = sdf.format(date) + "\n";
        String fileName = "D:/test.out";
        File file = new File(fileName);

        try
        {
        	// ������ ��ɾ� (�����쿡���� cmd.exe�� ����)
        	// Runtime.exec ������ �Ķ���͸� �迭�� ����
        	// String[] cmdArr = {"cmd.exe", "/y", "/c", "������ ��ɾ�"};		// ������
        	String[] cmdArr = {"cmd.exe", "/y", "/c", "DIR /A:D /-C D:\\ "};		// ������
//          String[] cmdArr = {"df -hT"};		// ������

        	// ��ɾ� ������ ��µǴ� ������ Process ������ ����
        	Process proc = Runtime.getRuntime().exec(cmdArr);
        	proc.waitFor();
        	
            InputStream os = proc.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(os)); 
            
            String line = null;
            int cnt = 0;
            
            while((line=br.readLine())!=null){ //���δ��� �б�
            	content += line + "\n"; 
            	cnt++;
//	            	System.out.println("================ " + cnt);
            }
            
            System.out.println(log_date + "================ ó������ : " + cnt);

            // ���Ϸ� ���
			FileWriter fw = new FileWriter(file);
			fw.write(content);			
			fw.close();
			
			System.out.println(log_date + "================ ��������");
        }
        catch (Exception e) {
               System.out.println(log_date + "---------------- IO Exceiption!");
               e.printStackTrace();
        }
	}

	/**
	 * ���Ͽ��� Disk Free �뷮 �б�
	 */
	public static void diskFreeRead() {
		System.out.println("\n" + log_date + "================ 2. diskFreeRead ����");
		
	    String content = new String();     // ����         
	    String fileName = "D:/test.out";

	    // ==== ���ϳ��� �о����
	    try{
	        // �����б�
	        FileReader fr = new FileReader(fileName); //�����бⰴü����
	        BufferedReader br = new BufferedReader(fr); //���۸�����ü����

	        String line = new String();
	        String lastLine = new String();
	        String out_msg = new String();
	        
	        int cnt = 0;
	        while( (line=br.readLine()) != null){ //���δ��� �б�
	        	if(cnt == 0) {
	        		out_msg += line + "\n";
	        	}
	        	
	        	lastLine = line;
	        	cnt++;
//	        	System.out.println(cnt + " : " + line);
	        }
	        
	        // �뷮�� byte -> GB�� ��ȯ
	        String[] result = lastLine.split(" ");
	        float freeDisk_temp = Float.parseFloat(result[20]);
	        int freeDisk = (int)(freeDisk_temp / 1024 / 1024 / 1024);
	        
	        out_msg += freeDisk + "GB";
	        
	        System.out.println(log_date + "================ input ����");
		    System.out.println(out_msg);
	        
	        br.close();
	        
	        System.out.println(log_date + "================ ��������");
	    }catch (IOException e) { 
	      System.out.println(log_date + "================ �����б� ���� \n " + e.getMessage());
	      e.printStackTrace();
	    }        
	}
	
}