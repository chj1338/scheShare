package com.share.calendar.article.Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * URL로 지정되는곳에서 TARGET을 찾습니다.
 */

public class SearchTask {

	private String title;
	private String url;
	private String target;
	
	private static final Logger logger = LoggerFactory.getLogger(SearchTask.class);
	
	/**
	 * String url : 검색할 URL
	 * String target : 검색할 대상
	 */

	public SearchTask(String title, String url, String target) {
        try {
	    		this.title = title;
	    		this.url = url;
	    		this.target = target;		
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }

		String s = "";
		String s_old = "";
		String s_new = "";

		String searchArticle = "";
		String searchLink = "";
		
		try {
			logger.debug("========= url : {}", url);
			BufferedReader br = null;			
			URL u = new URL(url);
			
			if( url.indexOf("sportschosun") > 0 || url.indexOf("goodday") > 0 || url.indexOf("stoo") > 0 || url.indexOf("chosun") > 0 ) {
				br = new BufferedReader(new InputStreamReader(u.openStream(), "EUC-KR"));
			} else {
				br = new BufferedReader(new InputStreamReader(u.openStream(), "UTF-8"));
			}			
			
			while ((s = br.readLine()) != null) {
//				logger.debug("========= s : {}", s);
				s_new = s;
				
				// 찾은 기사 태그 제거 및 기사/링크 추출
				if (s.indexOf(target) != -1 && url.indexOf("nate") != -1 ) {
					searchArticle = s.substring(s.indexOf("\">", s.indexOf("href=") )+2, s.indexOf("</a>", s.indexOf("\">")+2 ) );
					searchArticle = lTrim(searchArticle.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", ""));
					searchLink = s.substring(s.indexOf("href=\"")+6, s.indexOf("\"", s.indexOf("href=\"")+6 ) );
					
					// 정제된 기사 파일로 쓰기
					articleWrite(searchArticle, searchLink, url, title);
				}
				else if (s.indexOf(target) > -1 && url.indexOf("naver") > -1 ) {
					
					s = s.replaceAll("&#034;", "").replaceAll("],", "|").replaceAll("\", \"", "^");

					logger.debug("========= s : {}", target + " : " + url + " : " + s);
					
	            	String[] result = s.split("\\|");
	            	
	            	for(int i=0; i<result.length; i++) {
	            		if(result[i].indexOf(target) > 0) {
		            		String[] item = result[i].split("\\^");

		            		searchArticle = item[0].replaceAll("\"", "").replaceAll(",", "").replaceAll("\\[", "");
		            		searchLink = item[1].replaceAll("\"", "").replaceAll(",", "");
		            		
							// 정제된 기사 파일로 쓰기
							articleWrite(searchArticle, searchLink, url, title);
	            		}
	            	}
				}
				else if (s.indexOf(target) != -1 && url.indexOf("daum") != -1 ) {
					searchArticle = s.substring(s.indexOf("\">", s.indexOf("href=") )+2, s.indexOf("</a>", s.indexOf("\">")+2 ) );
					searchArticle = lTrim(searchArticle.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", ""));
					searchLink = s.substring(s.indexOf("href=\"")+6, s.indexOf("\"", s.indexOf("href=\"")+6 ) );
					
					// 정제된 기사 파일로 쓰기
					articleWrite(searchArticle, searchLink, url, title);
				}
				else if (s.indexOf(target) != -1 && url.indexOf("ezday") != -1 ) {
					searchArticle = s.substring(s.indexOf("\">", s.indexOf("href=") )+2, s.indexOf("</a>", s.indexOf("\">")+2 ) );
					searchArticle = lTrim(searchArticle.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", ""));
					searchLink = s.substring(s.indexOf("href=\"")+6, s.indexOf("\"", s.indexOf("href=\"")+6 ) );
					
					// 정제된 기사 파일로 쓰기
					articleWrite(searchArticle, searchLink, url, title);
				}
				else if (s.indexOf(target) != -1 && url.indexOf("stoo") != -1 ) {
					searchArticle = s.substring(s.indexOf("\">", s.indexOf("href=") )+2, s.indexOf("</a>", s.indexOf("\">")+2 ) );
					searchArticle = lTrim(searchArticle.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", ""));
					searchLink = s.substring(s.indexOf("href=\"")+6, s.indexOf("\"", s.indexOf("href=\"")+6 ) );
					
					// 정제된 기사 파일로 쓰기
					articleWrite(searchArticle, searchLink, url, title);
				}
				else if (s.indexOf(target) != -1 && url.indexOf("sportsseoul") != -1 ) {
					s = s_old + s;
					s = s.replace("'", "\"");

					searchArticle = lTrim(s.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", ""));
					searchLink = s.substring(s.indexOf("href=\"")+6, s.indexOf("\"", s.indexOf("href=\"")+6 ) );					
					
					s_old = s_new;
					
					// 정제된 기사 파일로 쓰기
					articleWrite(searchArticle, searchLink, url, title);
				}
				else if (s.indexOf(target) != -1 && url.indexOf("chosun") != -1 ) {
					searchArticle = s.substring(s.indexOf("\">", s.indexOf("href=") )+2, s.indexOf("</a>", s.indexOf("\">")+2 ) );
					searchArticle = lTrim(searchArticle.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", ""));
					searchLink = s.substring(s.indexOf("href=\"")+6, s.indexOf("\"", s.indexOf("href=\"")+6 ) );
					
					// 정제된 기사 파일로 쓰기
					articleWrite(searchArticle, searchLink, url, title);
				}
				else if (s.indexOf(target) != -1 && url.indexOf("joongang") != -1 ) {
					s = s_old + s;
					s = s.replace("'", "\"");

					searchArticle = lTrim(s.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", ""));
					searchLink = s.substring(s.indexOf("href=\"")+6, s.indexOf("\"", s.indexOf("href=\"")+6 ) );					
					
					s_old = s_new;
					
					// 정제된 기사 파일로 쓰기
					articleWrite(searchArticle, searchLink, url, title);
				}
				else if (s.indexOf(target) != -1 && url.indexOf("donga") != -1 ) {
					s = s.replace("'", "\"");

					searchArticle = lTrim(s.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", ""));
					searchArticle = searchArticle.replaceAll("<[^>]*>", "").replaceAll("\r", "").replaceAll("\n", "");
					searchLink = s.substring(s.indexOf("href=\"")+6, s.indexOf("\"", s.indexOf("href=\"")+6 ) );
					
					// 정제된 기사 파일로 쓰기
					articleWrite(searchArticle, searchLink, url, title);
				}
				else if (s.indexOf(target) != -1 && url.indexOf("hani") != -1 ) {
					s = s.replace("'", "\"");

					searchArticle = lTrim(s.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", ""));
					searchArticle = searchArticle.replaceAll("<[^>]*>", "").replaceAll("\r", "").replaceAll("\n", "");
					searchLink = s.substring(s.indexOf("href=\"")+6, s.indexOf("\"", s.indexOf("href=\"")+6 ) );
					
					// 정제된 기사 파일로 쓰기
					articleWrite(searchArticle, searchLink, url, title);
				}

			}	// while
			
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("ERROR ========= {}", title + " : " + url + " : " + s);
		}
		
		logger.debug("========= SearchTask  END !!!");
	}
	
	
	public static void articleWrite(String searchArticle, String searchLink, String url, String title) {
		String filePath = "D:/TEMP/";
		String outputFileName = "articleList.txt";
		String newline = System.getProperty("line.separator");

        FileWriter fw = null; //파일쓰기객체생성		

        try {

			if( !searchArticle.equals("") ) {
				// 링크에 주소가 생략되었을 경우
				if(searchLink.indexOf("http") < 0 && searchLink.indexOf("HTTP") < 0) {
					searchLink = url + searchLink;
				}
/*				
				// 추출한 기사가 태그나 메타, 스크립트 문자열일 경우
				if(searchArticle.indexOf("DOCTYPE") == -1 && searchArticle.indexOf("script") == -1 
					 && searchArticle.indexOf("CDATA") == -1   && searchArticle.indexOf("meta") == -1
					 && searchArticle.indexOf("promotion") == -1   && searchArticle.indexOf("document") == -1
					 && searchArticle.indexOf("stylesheet") == -1 && searchArticle.indexOf("class") == -1)	{

				}
*/
				String content = title + "|" + searchArticle + "|" + searchLink + newline;
				logger.debug("=========content {}", content);
				
				// 폴더가 없을 경우 폴더 생성
				File folderFile = new File(filePath);
				if(folderFile.exists() == false) {
					 folderFile.mkdirs();
				}
	/*			
				// 기존 파일이 존재할 경우 파일 삭제
				File uf = new File(filePath + outputFileName);
				if(uf.exists()) {
					uf.delete();
				}
	*/			
		        fw = new FileWriter(filePath + outputFileName, true); //파일쓰기객체생성
				fw.write(content); //파일 작성
			}

			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	 /**
	  * 스트링 RTRIM
	  * @param Object
	  * @return String
	  */
   public static String rTrim(String src){
   	if(src ==null) return null;
    
   	char [] val = src.toCharArray();
   	int strtpt=0;
   	int len = src.length();
    
   	while(strtpt < len && val[len-1] <= ' '){
   		len--;
   	}

   	return src.substring(0, len);
   }

   
	 /**
	  * 스트링 LTRIM
	  * @param Object
	  * @return String
	  */
   public static String lTrim(String src){
   	if(src ==null) return null;
    
   	char [] val = src.toCharArray();
   	int strtpt=0;
   	int len = src.length();
    
   	while(strtpt < len && val[strtpt] <= ' '){
   		strtpt++;
   	}
    
   	return src.substring(strtpt, len);
   }

}
