package com.share.calendar.common.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;

import org.springframework.stereotype.Repository;

@Repository
public class commonUtil {
	
	// 오늘날짜 시간을  '년-월-일 시:분:초'  형식으로 출력
	public String getCurrentDateTimeStringByHyphen() {
		Calendar date = Calendar.getInstance();
		String CurrentDateTimeStringByHyphen = 
					date.get(Calendar.YEAR) + "-"
						+(date.get(Calendar.MONTH)+1) + "-"
						+(date.get(Calendar.DATE)) + "-" + " "
						+(date.get(Calendar.HOUR))+":"
						+(date.get(Calendar.MINUTE))+":"
						+(date.get(Calendar.SECOND));
		
		return CurrentDateTimeStringByHyphen;
	}
	
	
	// 파일 존재여부 확인
	public boolean exists(String filepath) {
		File f = new File(filepath);
		boolean result = true;
		
		if(f.isFile()) {
			result =  true;
		} else {
			result = false;
		}
		
		return result;
	}
	
	
	// 파일을 지정한 위치로 move
	public void moveFileToDirectory(String dir1, String fileName, String dir2) {
		try{
			File srcDir = new File(dir1);
			File destDir = new File(dir2);
			
			if(!destDir.exists()) {
				destDir.mkdirs();                                     
			}
			
			for (File file : srcDir.listFiles()) {
				if (file.getName().startsWith(fileName)) {
					file.renameTo(new File(dir2 + "/" + file.getName()));
				}
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
}