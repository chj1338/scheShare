package com.ai.exam.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 클라이언트의 접속을 받아 들여 모든 클라이언트에게 
 * 데이터를 전송시켜 주는 ServerThread 객체를 생성
 */
public class BroadCastingServer  implements Runnable  {

	// 클라이언트와 소켓을 형성하기 위한 클래스 선언
	private ServerSocket server;
	private Socket socket;
	
	// 데이터를 주고받을 수 있도록 Thread로 구현한 클래스 선언
	private ServerThread st;

	// 서버를 실행시키는 메소드
//	public void startServer() throws Exception {
	public void run() {
		System.out.println("========= startServer Start !!!");
		
		try {
			// 대표 포트 7777을 가지고 클라이언트의 접속을 기다리는 ServerSocket 객체 생성
			server = new ServerSocket(6666);
			
			while (true) {
				// 클라이언트와 접속을 성공시켜 소켓 생성
				socket = server.accept();
				
				// 스트림 형성
				DataInputStream dis = new DataInputStream(socket.getInputStream());
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
				
				// 클라이언트가 전송하는 ID 값을 받아 들임
				String name = dis.readUTF();
				System.out.println("========= name : " + name);
				
				// broadcasting을 해주는 ServerThread 객체 생성
				st = new ServerThread(socket, /*state,*/ dis, dos, name);
				Thread t = new Thread(st);				
				t.start();
			}  // while end

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
        	;
        }

	}  // startServer() end

}
