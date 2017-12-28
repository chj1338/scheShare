package com.ai.exam.deamon;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.ai.exam.chat.BroadCastingServer;

public class InitServlet extends HttpServlet {

	private static final long serialVersionUID = -7924626738697967934L;

	// 쓰레드를 시작
    public void start_Thread() {
    	System.out.println("========= Deamon Start !!!");
    	
        try {
            Thread t = new Thread(new BroadCastingServer());
            t.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        System.out.println("========= Deamon Start Complete !!!");
    }

    // 서블릿 초기화
    public void init(ServletConfig config) throws ServletException {
        start_Thread();
    }

}
