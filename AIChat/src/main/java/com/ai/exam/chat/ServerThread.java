package com.ai.exam.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;

 

// 쓰레드를 이용해 모든 클라이언트에게 데이터를 전송해주는 broadcasting을 구현한 클래스
public class ServerThread implements Runnable {

	 // 접속한 클라이언트를 저장하는 vector 객체 생성
	 private static Vector list = new Vector();
	
	 // BroadCastringServer가 넘겨주는 argument를 저장하기 위한 멤버 변수 선언
	 private Socket socket;
	 // private TextArea state;
	 private DataInputStream dis;
	 private DataOutputStream dos;
	
	 // 사용자의 대화명을 저장하는 변수 선언
	 String talkName;

	// 생성자
	// BroadCastingServer에서 넘겨주는 argument를 멤버 변수에 할당
	public ServerThread(Socket socket, /*TextArea state,*/ DataInputStream dis,
							DataOutputStream dos, String talkName) throws IOException {
	
		this.socket = socket;
		//  this.state = state;
		this.dis = dis;
		this.dos = dos;
		this.talkName = talkName;
	}

	
	public void run() {
		// 서버의 TextArea에 현재 접속한 클라이언트를 출력
		//  state.append(talkName + "님이 입장하셨습니다.\n");
		System.out.println(talkName + "님이 입장하셨습니다.\n");

		// 모든 클라이언트들에게 현재 접속한 사용자를 알려줌
		compareState("Login/" + talkName + "님이 입장하셨습니다.\n");

		try {
			// 현재 접속한 사용자를 벡터에 추가
			list.addElement(this);
			
			while (true) {
				String msg = dis.readUTF();
				compareState(msg);
			}  // while end
		
		} catch (IOException ie) {
			// 소켓이 끊어지거나, 스트림이 해제 되었을 경우,
			// Exception이 발생했을 경우는 퇴장한 경우이므로
			//state.append(talkName + "님이 퇴장하셨습니다\n");
			System.out.println(talkName + "님이 퇴장하셨습니다.\n");
			compareState("LogOut/" + talkName + "님이 퇴장하셨습니다 \n");
			
			// 벡터에서 현재 Exception이 난 Object를 제거
			list.removeElement(this);
			
			try {
				socket.close();
			} catch (IOException e) {
				System.out.println("소켓 닫는 중 에러 발생");
			}
		} // catch end
	} // run() end

 
	// 현재 Login을 하려고 하는지, 아니면 대화 내용을 보내는 중인지를 판단하기 위해서 선언한 method
	public void compareState(String message) {
		StringTokenizer st = new StringTokenizer(message, "/");
		String protocol = st.nextToken(); // Login,Logout,Message
		String msg = st.nextToken();
		
		if (protocol.equals("Login") || protocol.equals("LogOut")) {
			broadCasting("NONE", msg);
		} else {
			broadCasting(talkName, msg);
		}
	}  // compareState() end


	// 현재 서버에 접속한 모든 클라이언트에게 메시지를 보내주는 method
	// BroadCasting 기능이 먼저 완료되어서 채팅글이 꼬이지 않도록 한다.
	public void broadCasting(String talkName, String message) {
		synchronized (list) {
			Enumeration e = list.elements();
			
			while (e.hasMoreElements()) {
				ServerThread temp = (ServerThread) e.nextElement();
				
				try {
					temp.dos.writeUTF(talkName);
					temp.dos.writeUTF(message);
					System.out.println("[" + talkName + "] : " + message);
				} catch (IOException ie) {
					System.out.println(ie);
				}
			}  // while end
		}  // synchronized() end
		
	} // broadCasting
 
}