package com.ai.exam.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/chat")
public class ChatClient {
	
	private static final Logger logger = LoggerFactory.getLogger(ChatClient.class);
	
	 // 서버와의 접속을 위한 socket 객체 선언
	 Socket socket;

	 // 스트림 클래스 선언
	 DataInputStream dis;
	 DataOutputStream dos;

	 Thread listen;

	//////////////////////////////////  화면영역
	
	@RequestMapping(value = "/chat", method = RequestMethod.GET)
	public String chat(Locale locale, Model model) {
		logger.debug("Welcome home! chat {}.");

		return "/chat/chat";
	}
	
	
	//////////////////////////////////  API 영역
	
	@ResponseBody
    @RequestMapping(value = "/shop/getShopListData", method = RequestMethod.POST)
    public Map<String, Object> getShopeduleList (HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("========= getShopListData Controller Start !!!");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
        try {
        	String sendId 	= new String(request.getParameter("sendId").getBytes("ISO-8859-1"), "UTF-8");
        	String sendMsg = new String(request.getParameter("sendMsg").getBytes("ISO-8859-1"), "UTF-8");
//    		String address 	= request.getParameter("address");
//    		int port			= Integer.parseInt( request.getParameter("port") );
    		String address 	= "localhost";
    		int port			= 6666;
    		
  		  	try {
  			   // 서버와 연결
  			   socket = new Socket(address, port);
  			
  			   // 현재 접속한 서버와 스트림 형성
  			   dis = new DataInputStream(socket.getInputStream());
  			   dos = new DataOutputStream(socket.getOutputStream());
  			   
  			   // 서버측으로 데이타 전송
  			   dos.writeUTF(sendId);
  			   dos.writeUTF("Message/" + sendMsg);
  		  	} catch (IOException e) {
  		  		System.out.println("서버가 없습니다.");
  		  		System.exit(0);
  		  	}
    		
		    String recieveId = dis.readUTF(); // 대화명(talkName)을 읽음
		    String recieveMsg = dis.readUTF(); // message를 읽음
    		
            resultMap.put("recieveId", recieveId);
            resultMap.put("recieveMsg", recieveMsg);
            
            resultMap.put("resultCd", "1000");
            resultMap.put("resultMsg", "SUCCESS");
        } catch(Exception e) {
            e.printStackTrace();
            logger.error("Error : {}", e.getMessage());
            
            resultMap.put("resultCd", "9999");
            resultMap.put("resultMsg", e.getMessage());
        } finally {
        	;
        }
        
        return resultMap;
	}
	
}
