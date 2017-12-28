package com.ai.exam.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatClientThread implements Runnable {
/*
 // 클라이언트의 폼을 구성하는 component
 Frame first, second;
 Panel p;
 TextField ipField;
 TextField idField;
 Button connection;

 TextArea talkScreen;
 TextField tf;
*/
 // 서버와의 접속을 위한 socket 객체 선언
 Socket socket;

 // 스트림 클래스 선언
 DataInputStream dis;
 DataOutputStream dos;

 Thread listen;

 // 생성자. 클라이언트
 public ChatClientThread() {
//	 firstFrame();
 } // 생성자 end
 
 /*
 //GUI 폼을 구성
 public void firstFrame() {
	  first = new Frame("LOGIN");
	  // second = new Frame( "접속 성공" );
	  connection = new Button("Connect");
	
	  // Server의 IP와 대화명을 받아 들이는 TextField 객체 생성
	  ipField = new TextField(15);
	  idField = new TextField(15);
	
	  // Label을 붙인 Panel
	  Panel p1 = new Panel();
	  p1.setLayout(new GridLayout(2, 1, 0, 10));
	  p1.add(new Label("Server", Label.CENTER));
	  p1.add(new Label("ID", Label.CENTER));
	
	  // TextField를 붙인 Panel
	  Panel p2 = new Panel();
	  p2.setLayout(new GridLayout(2, 1, 0, 10));
	  p2.add(ipField);
	  p2.add(idField);
	
	  Panel p3 = new Panel();
	  p3.add(p1);
	  p3.add(p2);
	
	  // 연결 시도 프레임 구성
	  first.setLayout(new BorderLayout(0, 10));
	  first.add(p3, "Center");
	  first.add(connection, "South");
	
	  // Window의 종료 이벤트 처리
	  first.addWindowListener(new WindowAdapter() {
	   public void windowClosing(WindowEvent e) {
	    System.exit(0);
	   }
	  });
	
	  first.setSize(300, 150);
	  first.setLocation(300, 300);
	  first.setVisible(true);
	
	  // 버튼에 ActionEvent를 등록시킴
	  connection.addActionListener(this);
 } 
*/
 
/* 
 // 로그인 프레임이 종료한 후에 대화를 나눌 수 있는 GUI 폼을 구성해 주는 메소드
 public void secondFrame(String id) {
	  second = new Frame(id + " : Client");
	
	  tf = new TextField(15);
	
	  talkScreen = new TextArea("", 30, 50, TextArea.SCROLLBARS_NONE);
	  talkScreen.setEditable(false);
	
	  second.add("Center", talkScreen);
	  second.add("South", tf);
	
	  second.setSize(320, 420);
	  second.setVisible(true);
	
	  // Window의 종료 이벤트 처리
	  second.addWindowListener(new WindowAdapter() {
	   public void windowClosing(WindowEvent e) {
	    System.exit(0);
	   }
	  });
	
	  tf.addActionListener(this);
 } // secondFrame() end
*/
 
 // Server와의 연결을 시도하는 메소드
 public void connectServer(String id, String address, int port) {
	 System.out.println("서버 접속 시도");
	  try {
	   // 서버와 연결
	   socket = new Socket(address, port);
	
	   // 현재 접속한 서버와 스트림 형성
	   dis = new DataInputStream(socket.getInputStream());
	   dos = new DataOutputStream(socket.getOutputStream());
	   dos.writeUTF(id);
	
	  } catch (IOException e) {
	   System.out.println("서버가 없습니다.");
	   System.exit(0);
	  }
	
	  // 클라이언트 자체도 쓰레드로 구동시킴
	  listen = new Thread(this);
	  listen.start(); // run() 메소드 호출
 } // connectServer() end


 public void run() {

  // 서버로 부터 받은 데이터를 자신의 TextArea에 출력시킴.
  try {
   while (true) { // ServerThread.java파일 99,100라인
    String id = dis.readUTF(); // 대화명(talkName)을 읽음
    String line = dis.readUTF(); // message를 읽음

    if (id.equals("NONE")) {  // 입장과 퇴장
//     talkScreen.append(line + "\n");
     System.out.println(line + "\n");
    } else {
//     talkScreen.append("[" + id + "] : " + line + "\n");
     System.out.println("[" + id + "] : " + line + "\n");
    }
    
   }  // while end

  } catch (IOException ex) {
//   talkScreen.append("서버에서 데이터를 읽는 중 에러가 발생");
   System.out.println("서버에서 데이터를 읽는 중 에러가 발생");

   try {
    Thread.sleep(1000); // 1초 동안 block 상태로 만듬
   } catch (InterruptedException e) {
	   e.printStackTrace();
   } // catch end
   System.exit(0);
  }
 }

/* 
 // 텍스트 필드에서 데이터를 입력하고 엔터를 치는 ActionEvent 처리
 public void actionPerformed(ActionEvent av) {

  // TextField에서 Event가 발생했을 경우(TextField에 값 입력후 엔터키)
  if (av.getSource() == tf) {  // 두번째 화면 

   try {
    // 서버측으로 데이타 전송
    dos.writeUTF("Message/" + tf.getText());
    tf.setText(" ");
   } catch (IOException e) {
    System.out.println(e);
   }

   // Button에서 Event가 발생했을 경우(Button 클릭)
  } else if (av.getSource() == connection) {
   String address = ipField.getText();
   String id = idField.getText();

   first.setVisible(false);
   first.dispose();

//   secondFrame(id);
   connectServer(id, address, 6666);
  }
 }
*/
 /*
 public static void main(String[] args) {
  ChatClientThread bcc = new ChatClientThread();
 }
*/
}
