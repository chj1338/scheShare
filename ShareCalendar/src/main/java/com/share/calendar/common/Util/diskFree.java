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
	 * Disk Free 용량 계산 및 파일로 떨구기
	 */
	public static void diskFree() {
		System.out.println(log_date + "================ 1. diskFree 시작");

        String content = sdf.format(date) + "\n";
        String fileName = "D:/test.out";
        File file = new File(fileName);

        try
        {
        	// 실행할 명령어 (윈도우에서는 cmd.exe로 실행)
        	// Runtime.exec 에서는 파라메터를 배열로 구분
        	// String[] cmdArr = {"cmd.exe", "/y", "/c", "실행할 명령어"};		// 윈도우
        	String[] cmdArr = {"cmd.exe", "/y", "/c", "DIR /A:D /-C D:\\ "};		// 윈도우
//          String[] cmdArr = {"df -hT"};		// 리눅스

        	// 명령어 실행후 출력되는 내용을 Process 변수로 저장
        	Process proc = Runtime.getRuntime().exec(cmdArr);
        	proc.waitFor();
        	
            InputStream os = proc.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(os)); 
            
            String line = null;
            int cnt = 0;
            
            while((line=br.readLine())!=null){ //라인단위 읽기
            	content += line + "\n"; 
            	cnt++;
//	            	System.out.println("================ " + cnt);
            }
            
            System.out.println(log_date + "================ 처리라인 : " + cnt);

            // 파일로 출력
			FileWriter fw = new FileWriter(file);
			fw.write(content);			
			fw.close();
			
			System.out.println(log_date + "================ 정상종료");
        }
        catch (Exception e) {
               System.out.println(log_date + "---------------- IO Exceiption!");
               e.printStackTrace();
        }
	}

	/**
	 * 파일에서 Disk Free 용량 읽기
	 */
	public static void diskFreeRead() {
		System.out.println("\n" + log_date + "================ 2. diskFreeRead 시작");
		
	    String content = new String();     // 내용         
	    String fileName = "D:/test.out";

	    // ==== 파일내용 읽어오기
	    try{
	        // 파일읽기
	        FileReader fr = new FileReader(fileName); //파일읽기객체생성
	        BufferedReader br = new BufferedReader(fr); //버퍼리더객체생성

	        String line = new String();
	        String lastLine = new String();
	        String out_msg = new String();
	        
	        int cnt = 0;
	        while( (line=br.readLine()) != null){ //라인단위 읽기
	        	if(cnt == 0) {
	        		out_msg += line + "\n";
	        	}
	        	
	        	lastLine = line;
	        	cnt++;
//	        	System.out.println(cnt + " : " + line);
	        }
	        
	        // 용량을 byte -> GB로 변환
	        String[] result = lastLine.split(" ");
	        float freeDisk_temp = Float.parseFloat(result[20]);
	        int freeDisk = (int)(freeDisk_temp / 1024 / 1024 / 1024);
	        
	        out_msg += freeDisk + "GB";
	        
	        System.out.println(log_date + "================ input 내용");
		    System.out.println(out_msg);
	        
	        br.close();
	        
	        System.out.println(log_date + "================ 정상종료");
	    }catch (IOException e) { 
	      System.out.println(log_date + "================ 파일읽기 오류 \n " + e.getMessage());
	      e.printStackTrace();
	    }        
	}
	
}