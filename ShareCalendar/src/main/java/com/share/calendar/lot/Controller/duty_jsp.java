package com.share.calendar.lot.Controller;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.apache.jasper.runtime.*;
import java.util.*;
import java.sql.*;
import java.sql.*;
import java.util.*;
import java.net.*;

public class duty_jsp extends HttpJspBase {



    public static Connection getConnection() throws Exception{
    	Connection conn = null;
    	String driver = "oracle.jdbc.xa.client.OracleXADataSource"; 
    	String url = "jdbc:oracle:thin:@localhost:1521:orcl";         
    	String oraid = "sche";    
    	String orapwd = "sche";         
     
    	Class.forName(driver);
        conn=DriverManager.getConnection(url,oraid,orapwd);
        
        return conn;      
    }


	//코드데이터 로딩
	public List GetCommonCod(Statement stmt, String master_Cod)
			throws Exception {

		List rsList = new ArrayList();
		String sql = new String();
		sql = " SELECT COD, CODNAM FROM TB_COD_DETAIL WHERE USEYN=1 AND MASTER_COD='"
				+ master_Cod + "'  ORDER BY CORDER ASC ";

		//System.out.println(sql);
		ResultSet rs = stmt.executeQuery(sql);

		while (rs.next()) {
			Map map = new HashMap();
			map.put("COD", rs.getString("COD"));
			map.put("CODNAM", rs.getString("CODNAM"));
			rsList.add(map);
		}

		rs.close();
		return rsList;
	}

	//코드데이터 로딩
	public String GetCommonCod2(Statement stmt, String master_Cod, String value)
			throws Exception {

		String result = "";
		String sql = new String();
		sql = " SELECT COD, CODNAM FROM TB_COD_DETAIL WHERE USEYN=1 AND MASTER_COD='"
				+ master_Cod + "'  ORDER BY CORDER ASC ";

		ResultSet rs = stmt.executeQuery(sql);

		while (rs.next()) {
			String select = "";
			if (value.equals(nvl(rs.getString("COD")))) {
				select = "Selected";
			}

			result += "<option value='" + nvl(rs.getString("COD")) + "' "
					+ select + ">" + nvl(rs.getString("CODNAM")) + "</option>";
		}

		rs.close();
		return result;
	}
	
	//코드데이터 로딩
	public String GetCommonCod2(Statement stmt, String master_Cod,String value, String sep) throws Exception {

		String result = "";
		String sql = new String();
		sql = " SELECT COD, CODNAM FROM TB_COD_DETAIL WHERE USEYN=1 AND MASTER_COD='"
				+ master_Cod + "'  ORDER BY CORDER ASC ";

		ResultSet rs = stmt.executeQuery(sql);

		if (!sep.equals("")) {
			result += "<option value=''>" + sep + "</option>";
		}

		while (rs.next()) {
			String select = "";
			if (value.equals(nvl(rs.getString("COD")))) {
				select = "Selected";
			}

			result += "<option value='" + nvl(rs.getString("COD")) + "' "
					+ select + ">" + nvl(rs.getString("CODNAM")) + "</option>";
		}

		rs.close();

		return result;
	}

	//코드데이터 로딩
	public String GetCommonCod3(Statement stmt, String master_Cod,String value, String sep) throws Exception {

		String result = "";
		String sql = new String();
		sql = " SELECT COD, CODNAM FROM TB_COD_DETAIL WHERE USEYN=1 AND MASTER_COD='"
				+ master_Cod + "'  ORDER BY CORDER ASC ";

		ResultSet rs = stmt.executeQuery(sql);

		if (!sep.equals("")) {
			result += "<option value=''>" + sep + "</option>";
		}

		while (rs.next()) {
			String select = "";
			if (value.equals(nvl(rs.getString("CODNAM")))) {
				select = "Selected";
			}

			result += "<option value='" + nvl(rs.getString("CODNAM")) + "' "
					+ select + ">" + nvl(rs.getString("CODNAM")) + "</option>";
		}

		rs.close();

		return result;
	}

	//게시판 로딩
	public String GetCommonBrdCod(Statement stmt, String str) throws Exception {
		String sql = new String();
		String result = "";

		sql = " Select cod, codNam From TB_COD_DETAIL Where useYN=1 and master_cod='2000' and cod='"
				+ str + "' ";
		//System.out.println(sql);

		ResultSet rs = stmt.executeQuery(sql);

		while (rs.next()) {
			result += rs.getString("codNam");
		}

		rs.close();
		return result;
	}// GetCommonBrdCod()

	//담당자 조회
	public List GetdamdangCod(Statement stmt) throws Exception {
		List rsList = new ArrayList();
		String sql = new String();
		String result = "";

		sql += " Select mem_cod, mem_nam From TB_MEM_MEMBER  Where mem_useYN=1 and mem_auth='officemng' order by mem_nam asc  ";
		//System.out.println(sql);

		ResultSet rs = stmt.executeQuery(sql);

		while (rs.next()) {
			Map map = new HashMap();
			map.put("COD", rs.getString("MEM_COD"));
			map.put("CODNAM", rs.getString("MEM_NAM"));
			rsList.add(map);
		}

		rs.close();
		return rsList;
	}// GetdamdangCod()
	
	//담당자 조회
	public String GetdamdangCod2(Statement stmt, String value, String sep) throws Exception {
		String sql = new String();
		String result = "";

		sql += " Select mem_cod, mem_nam From TB_MEM_MEMBER  Where mem_useYN=1 and mem_auth='officemng' order by mem_nam asc  ";
		//System.out.println(sql);

		ResultSet rs = stmt.executeQuery(sql);
		
		if (!sep.equals("")) {
			result += "<option value=''>" + sep + "</option>";
		}
		while (rs.next()) {
			String select = "";
			
			if (value.equals(nvl(rs.getString("mem_cod")))) {
				select = "Selected";
			}
			result += "\n" + "<option value='" + nvl(rs.getString("mem_cod")) + "' " + select + ">" + nvl(rs.getString("mem_nam")) + "</option>";
		}

		rs.close();
		return result;
	}// GetdamdangCod()

	//NULL값을 공백으로 대체한다.
	public String nvl(String str) {
		if (isNull(str))  return "";
		else              return str.trim();
	}// nvl()

	public String nvl(String str, String val) {
		if (isNull(str))  return val;
		else              return str;
	}// nvl()

	public boolean isNull(String str) {
		if (str == null || str.equalsIgnoreCase("null")) return true;
		else                                             return false;
	}// isNull()

	public String inputword(String str) {
		str = str.replaceAll("'", "''");
		str = str.replaceAll("&", "&amp;");
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");

		return str;
	}// nvl()

	public String lpad(String str, int totLength, String sep) {

		int strLength = str.length();
		String returnStr = "";

		for (int i = 0; i < totLength - strLength; i++) {
			returnStr += sep;
		}

		return returnStr + str;
	}

	//현재일자를  yyyymmdd로 리턴한다.
	public String date() {
		Calendar today = Calendar.getInstance();
		return today.get(today.YEAR)
				+ lpad((today.get(today.MONTH) + 1) + "", 2, "0")
				+ lpad(today.get(today.DATE) + "", 2, "0");
	}
	
	//입력받은 문자열을 날짜포맷으로 리턴한다.
	public String formatDate(String yyyymmdd, String sep) {
		String yyyy = yyyymmdd.substring(0,4);
		String mm   = yyyymmdd.substring(4,6);
		String dd   = yyyymmdd.substring(6,8);
		
		return yyyy + sep + mm + sep + dd;
	}

	//현재시간를  hh24로 리턴한다.
	public String getHour() {
		Calendar today = Calendar.getInstance();
		return today.get(today.AM_PM) == 1 ? lpad((today.get(today.HOUR) + 12)
				+ "", 2, "0") : lpad(today.get(today.HOUR) + "", 2, "0");
	}

	//현재분를  mm으로 리턴한다.
	public String getMinute() {
		Calendar today = Calendar.getInstance();
		return lpad(today.get(today.MINUTE) + "", 2, "0");
	}

	//입력받은 값이 날짜형식인지 확인한다.
	public boolean isDate(String date) {

		date = getNumber(date);
		
		if (date.length() < 8)
			return false;

		int month[] = { 1, 3, 5, 7, 8, 10, 12 };
		int yyyy    = Integer.parseInt(date.substring(0, 4));
		int MM      = Integer.parseInt(date.substring(4, 6));
		int dd      = Integer.parseInt(date.substring(6, 8));
		
		if (MM < 1 || MM > 12) return false;
		
		int gbnMM = 0; //월구분(0:30일, 1:31일)
		for (int i = 0; i < month.length; i++) {
			if (MM == month[i]) { gbnMM = 1;break;} 			
		}
		
		//2월인 경우 윤달인경우 1~29일을 체크, 아닌 경우 1~28일을 체크한다.
		if (MM == 2) {
			if (yyyy % 4 == 0) {
				if(dd < 1 || dd > 29) {
					return false;
				}
			} else {
				if(dd < 1 || dd > 28) {
					return false;
				}
			}			
		} 
		else if(gbnMM == 1 && (dd < 1 || dd > 31)) {return false;} //일자가 1~31일 사이인지 체크한다.
		else if(gbnMM == 0 && (dd < 1 || dd > 30)) {return false;} //일자가 1~30일 사이인지 체크한다.

		//시분초가 있는 경우
		if (date.length() > 8) {
			int hh = 99, mm = 99, ss = 99;

			if (date.length() == 10)
				hh = Integer.parseInt(date.substring(8, 10));
			if (date.length() == 12)
				mm = Integer.parseInt(date.substring(10, 12));
			if (date.length() == 14)
				mm = Integer.parseInt(date.substring(12, 14));

			//시간은 0~ 24시
			if (hh != 99 && (hh < 0 || hh > 24))
				return false;

			//분은 0~60분
			if (mm != 99 && (mm < 0 || mm > 60))
				return false;

			//초는 0~60초
			if (ss != 99 && (ss < 0 || ss > 60))
				return false;
		}

		return true;
	}

	//입력받은 문자값중 숫자만 반환한다.
	public String getNumber(String str) {
		String rtnStr = "";
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) > 47 && str.charAt(i) < 58)
				rtnStr += str.charAt(i);
		}
		return rtnStr;
	}
	
	//[접수일/완료일 체크] 업무 보고주내로 접수일/완료일을 입력하였는지 확인 , 파라미터(일, 시, 분, 필드명)  ex)'2008-10-10', '12', '35', '접수일'
	public String DateValidition(String param1, String param2, String param3, String param4){
		String   rtnMsg     = "";
		Calendar jobCal     = Calendar.getInstance(); //현재시간 +3시간   
		Calendar upmuNSdate = Calendar.getInstance(); //업무시작일
		Calendar upmuNEdate = Calendar.getInstance(); //업무종료일
		
		int wNum            = jobCal.get(jobCal.DAY_OF_WEEK);
		int hour            = jobCal.get(jobCal.HOUR);
		// 월요일 12시 이전이면 시작 일자가 오늘이 아닌 전 주 월요일로 셋팅
		if(wNum == 1 && hour < 12){
			wNum = 8;
		}
		
		jobCal.add(Calendar.MINUTE, 60*3);
		Calendar cal = createCalendar(param1, param2, param3); //접수일 또는 완료일
		
		//접수일 이나 완료일이 미래시간이 될수는 없음.(3시간)이후시간이면 미래시간으로 봄
		if(cal.getTimeInMillis() > jobCal.getTimeInMillis()) {
			String paramDate = formatDate(param1,"-") + " " + param2 + ":" + param3;
			rtnMsg  = "\\n[" + param4 +" 오류] ";
			rtnMsg += "\\n\\n입력된 " + param4 + ": [" + paramDate + "] 이 현재시간 " + formatDate(date(),"-") + " " + getHour() + ":" + getMinute() + " 이후가 될 수 없습니다. ";
			rtnMsg += "\\n\\n["+ param4 +"]을 확인하시고 다시 입력하여 주세요.";
			return ErrorAlert(rtnMsg);
		}
		
		//목요일부터 수요일까지로 업무기간 변경(2010.01.13)
		//월요일(11:59:59) 부터 금주 월요일(12:00:00)까지 입니다.(2012.06.15)
		//현재일 기준으로 업무시작일(upmuNSdate),  업무종료일(upmuNEdate)을 구한다.
		if(wNum == 1) {
			upmuNSdate.set(upmuNSdate.DATE, upmuNSdate.get(upmuNSdate.DATE) - 7);
			upmuNEdate.add(upmuNEdate.DATE, 0);
		} else if(wNum == 2) {
			upmuNSdate.set(upmuNSdate.DATE, upmuNSdate.get(upmuNSdate.DATE) - 1);
			upmuNEdate.add(upmuNEdate.DATE, 6);
		} else if(wNum == 3) {
			upmuNSdate.set(upmuNSdate.DATE, upmuNSdate.get(upmuNSdate.DATE) - 2);
			upmuNEdate.add(upmuNEdate.DATE, 5);
		} else if(wNum == 4) {
			upmuNSdate.set(upmuNSdate.DATE, upmuNSdate.get(upmuNSdate.DATE) - 3);
			upmuNEdate.add(upmuNEdate.DATE, 4);
		} else if(wNum == 5) {
			upmuNSdate.set(upmuNSdate.DATE, upmuNSdate.get(upmuNSdate.DATE) - 4);
			upmuNEdate.add(upmuNEdate.DATE, 3);
		} else if(wNum == 6) {
			upmuNSdate.set(upmuNSdate.DATE, upmuNSdate.get(upmuNSdate.DATE) - 5);
			upmuNEdate.add(upmuNEdate.DATE, 2);
		} else if(wNum == 7) {
			upmuNSdate.set(upmuNSdate.DATE, upmuNSdate.get(upmuNSdate.DATE) - 6);
			upmuNEdate.add(upmuNEdate.DATE, 1);
		}
		// 전 주 월요일 일 경우
		else if(wNum == 8) {
			upmuNSdate.set(upmuNSdate.DATE, upmuNSdate.get(upmuNSdate.DATE) - 8);
			upmuNEdate.add(upmuNEdate.DATE, -1);
		}
		
		upmuNSdate.set(upmuNSdate.HOUR  , 24);
		upmuNSdate.set(upmuNSdate.MINUTE, 00);
		upmuNSdate.set(upmuNSdate.SECOND, 00);
		
		upmuNEdate.set(upmuNSdate.HOUR  , 23);
		upmuNEdate.set(upmuNSdate.MINUTE, 59);
		upmuNEdate.set(upmuNSdate.SECOND, 59);
		
		boolean rErrFlg = false;
		
		System.out.println(upmuNSdate);
		System.out.println(upmuNEdate);
		System.out.println(wNum);
		System.out.println(upmuNSdate.getTime());
		System.out.println(cal.getTime());
		System.out.println(upmuNEdate.getTime());
		if(upmuNSdate.getTimeInMillis() > cal.getTimeInMillis()) {
			rErrFlg = true;
		} else if(upmuNEdate.getTimeInMillis() < cal.getTimeInMillis()) {
			rErrFlg =true;
		}
		
		if(rErrFlg == true) {
			rtnMsg  = "\\n[" + param4 +" 오류] ";
			rtnMsg += "\\n\\n\\n["+ param4 + "]가 해당 업무기간을 벗어납니다.\\n\\n매주 업무기간 기준기간은 금주 월요일 12:00부터 차주 월요일 11:59까지 입니다.\\n\\n["+ param4 +"]를 해당기간 이후로 입력하세요.";
			rtnMsg += "\\n\\n["+ param4 +"]을 확인하시고 다시 입력하여 주세요.";
			return ErrorAlert(rtnMsg);
		}
	
		return rtnMsg;
	}

	//[접수일/완료일 체크] 업무 보고주내로 접수일/완료일을 입력하였는지 확인 , 파라미터(일yyyymmdd, 시hh24, 분mm, 필드명:접수일)
	public String DateValidition2(String param1, String param2, String param3, String param4){
		String rtnMsg = "";
		Calendar jobCal = Calendar.getInstance();     //업무 보고주
		jobCal.add(Calendar.MINUTE, 60*3);
		
		Calendar cal = createCalendar(param1, param2, param3); //접수일 또는 완료일
		
		//접수일 이나 완료일이 미래시간이 될수는 없음.(3시간) 이후시간이면 미래시간으로 봄
		if(cal.getTime().compareTo(jobCal.getTime()) == 1) {
			String paramDate = formatDate(param1,"-") + " " + param2 + ":" + param3;
			
			rtnMsg += "\\n[" + param4 +" 오류] ";
			rtnMsg += "\\n\\n입력된 " + param4 + ": [" + paramDate + "] 이 현재시간 " + formatDate(date(),"-") + " " + getHour() + ":" + getMinute() + " 이후가 될 수 없습니다. ";
			rtnMsg += "\\n\\n["+ param4 +"]을 확인하시고 다시 입력하여 주세요.";
		}
		
		return rtnMsg;
	}
	
	//입력된 정보를 Calendar 객체로 반환한다.
	public Calendar createCalendar(String param1, String param2, String param3) {
		
		int year   = Integer.parseInt(param1.substring(0,4));
		int month  = Integer.parseInt(param1.substring(4,6));
		int date   = Integer.parseInt(param1.substring(6,8));
		int hour   = Integer.parseInt(param2);
		int minute = Integer.parseInt(param3);
		
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(year, month - 1, date, hour, minute);
		return calendar;
	}
	
	public String dateadd(String param1, String field, String diff){
		
		Calendar date = createCalendar(param1, "00","00");
		
		//diff가 음수인 경우
		if(diff.substring(0,1).equals("-")) {
			if(field.equals("d")) {
				date.set(date.DATE, date.get(date.DATE) + (Integer.parseInt(diff.replaceAll("-",""))) * -1); 
			} else if(field.equals("m")) {
				date.set(date.MONTH, date.get(date.MONTH) + (Integer.parseInt(diff.replaceAll("-",""))) * -1);
			} else if(field.equals("y")) {
				date.set(date.YEAR, date.get(date.YEAR) + (Integer.parseInt(diff.replaceAll("-",""))) * -1);
			}
		} 
		//diff가 양수인 경우
		else {
			if(field.equals("d")) {
				date.set(date.DATE, date.get(date.DATE) + (Integer.parseInt(diff))); 
			} else if(field.equals("m")) {
				date.set(date.MONTH, date.get(date.MONTH) + (Integer.parseInt(diff)));
			} else if(field.equals("y")) {
				date.set(date.YEAR, date.get(date.YEAR) + (Integer.parseInt(diff)));
			}
		}
		
		return date.get(date.YEAR)+ lpad((date.get(date.MONTH) + 1) + "", 2, "0")+ lpad(date.get(date.DATE) + "", 2, "0");
	}

	//오류메세지를 alert한다.
	public String ErrorAlert(String strMsg) {
		String rtnMsg = "";
		rtnMsg += "\n" + "<script language='javascript'>";
		rtnMsg += "\n" + "	alert('" + strMsg + "');";
		rtnMsg += "\n" + "	history.back();";
		rtnMsg += "\n" + "</script>";

		return rtnMsg;
	}
	
	//Dhtml 입력폼에서 작성저장하면 -- alert 박스후 History.back()시 Dhtml 내용이 사라지는걸 방지하기 위해 받은값 서브밋 해줌
	public String ErrorDhtmlOnlyAlert(String strMsg, Map param) {
		String rtnMsg = "";
		
		rtnMsg += "\n" + "<SCRIPT LANGUAGE='JavaScript'>";
		rtnMsg += "\n" +"alert('"+strMsg+"');";
		rtnMsg += "\n" +"</SCRIPT>";
		rtnMsg += "\n" +"<form name='form1' method='post' action='hp_stat.jsp'>";
		rtnMsg += "\n" +"	<textarea name='bd_type' cols=1 rows=1 style='width:0;height:0;'>"      + nvl(param.get("bd_type").toString())      +"</textarea>";
		rtnMsg += "\n" +"	<textarea name='bd_seq' cols=1 rows=1 style='width:0;height:0;'>"       + nvl(param.get("bd_seq").toString())       +"</textarea>";
		rtnMsg += "\n" +"	<textarea name='stat_contents' cols=1 rows=1 style='width:0;height:0;'>"+ nvl(param.get("body").toString())         +"</textarea>";
		rtnMsg += "\n" +"	<textarea name='reqType' cols=1 rows=1 style='width:0;height:0;'>"      + nvl(param.get("reqType").toString())      +"</textarea>";
		rtnMsg += "\n" +"	<textarea name='status' cols=1 rows=1 style='width:0;height:0;'>"       + nvl(param.get("status").toString())       +"</textarea>";
		rtnMsg += "\n" +"	<textarea name='damdang' cols=1 rows=1 style='width:0;height:0;'>"      + nvl(param.get("damdang").toString())      +"</textarea>";
		rtnMsg += "\n" +"	<textarea name='title_paper' cols=1 rows=1 style='width:0;height:0;'>"  + nvl(param.get("title_paper").toString())  +"</textarea>";
		//rtnMsg += "\n" +"	<textarea name='file1' cols=1 rows=1 style='width:0;height:0;'>"        + nvl(param.get("realFileName").toString()) +"</textarea>";
		rtnMsg += "\n" +"	<textarea name='mngPlanDate1' cols=1 rows=1 style='width:0;height:0;'>" + nvl(param.get("mngPlanDate1").toString()) +"</textarea>";
		rtnMsg += "\n" +"	<textarea name='mngPlanDate2' cols=1 rows=1 style='width:0;height:0;'>" + nvl(param.get("mngPlanDate2").toString()) +"</textarea>";
		rtnMsg += "\n" +"	<textarea name='mngPlanDate3' cols=1 rows=1 style='width:0;height:0;'>" + nvl(param.get("mngPlanDate3").toString()) +"</textarea>";
		rtnMsg += "\n" +"	<textarea name='givePlanDate1' cols=1 rows=1 style='width:0;height:0;'>"+ nvl(param.get("givePlanDate1").toString())+"</textarea>";
		rtnMsg += "\n" +"	<textarea name='givePlanDate2' cols=1 rows=1 style='width:0;height:0;'>"+ nvl(param.get("givePlanDate2").toString())+"</textarea>";
		rtnMsg += "\n" +"	<textarea name='givePlanDate3' cols=1 rows=1 style='width:0;height:0;'>"+ nvl(param.get("givePlanDate3").toString())+"</textarea>";
		rtnMsg += "\n" +"	<textarea name='completeDate1' cols=1 rows=1 style='width:0;height:0;'>"+ nvl(param.get("completeDate1").toString())+"</textarea>";
		rtnMsg += "\n" +"	<textarea name='completeDate2' cols=1 rows=1 style='width:0;height:0;'>"+ nvl(param.get("completeDate2").toString())+"</textarea>";
		rtnMsg += "\n" +"	<textarea name='completeDate3' cols=1 rows=1 style='width:0;height:0;'>"+ nvl(param.get("completeDate3").toString())+"</textarea>";
		rtnMsg += "\n" +"</form>";
		rtnMsg += "\n" +"<SCRIPT LANGUAGE='JavaScript'>";
		rtnMsg += "\n" +"document.form1.submit();";
		rtnMsg += "\n" +"</SCRIPT>";
		
		System.out.println(rtnMsg);
		
		return rtnMsg;		
	}
	
	//본문 내용에 이미지가 있는 경우 [']로인해 오라클 업데이트 시 문제 발생  ['']로 변경
	public String replaceContents(String contents) {
		String rtnContents = contents;
		boolean isChgStr = true;
		
		do{
			if(rtnContents.indexOf("ContentImgClick('D") > -1) {
				int pos           = rtnContents.indexOf("ContentImgClick('D");
				String replaceStr = rtnContents.substring(pos+16, pos+38);
				rtnContents       = rtnContents.replaceAll(replaceStr, "'" + replaceStr + "'");		
				
				System.out.println(rtnContents);
				isChgStr = true;
			} else {
				System.out.println("2");
				isChgStr = false;
			}
			
		} while(isChgStr);
		
		return rtnContents;
	}
	
	//[']로인해 오라클 업데이트 시 문제 발생  ['']로 변경
	public String replaceQuotation(String contents) {
		
		return contents.replaceAll("''","'").replaceAll("'","''");
	}
	
	public String strReplace(String stro, String s1, String s2) {
      int i = 0;
      String rStr = "";
      while(stro.indexOf(s1) > -1){
              i = stro.indexOf(s1);
              rStr += stro.substring(0,i)+s2;
              stro = stro.substring(i+s1.length());
      }
      return rStr+stro;
}
	


  private static java.util.Vector _jspx_includes;

  static {
    _jspx_includes = new java.util.Vector(3);
    _jspx_includes.add("/common/include/comm_auth.jsp");
    _jspx_includes.add("/common/include/dbcon.jsp");
    _jspx_includes.add("/common/include/comm_util.jsp");
  }

  public java.util.List getIncludes() {
    return _jspx_includes;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    JspFactory _jspxFactory = null;
    javax.servlet.jsp.PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;


    try {
      _jspxFactory = JspFactory.getDefaultFactory();
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n");
                  out.write("\r\n");
 
	request.setCharacterEncoding("UTF-8");

    String ss_mem_cod       = session.getAttribute("mem_cod")      == null ? "" : session.getAttribute("mem_cod").toString().trim();      //ID
    String ss_mem_nam       = session.getAttribute("mem_nam")      == null ? "" : session.getAttribute("mem_nam").toString().trim();      //이름
    String ss_mem_position  = session.getAttribute("mem_position") == null ? "" : session.getAttribute("mem_position").toString().trim(); //직책
    String ss_grp_cod       = session.getAttribute("grp_cod")      == null ? "" : session.getAttribute("grp_cod").toString().trim();      //그룹코드
    String ss_mem_grpNam    = session.getAttribute("mem_grpNam")   == null ? "" : session.getAttribute("mem_grpNam").toString().trim();   // 그룹명
    String ss_mem_auth      = session.getAttribute("mem_auth")     == null ? "" : session.getAttribute("mem_auth").toString().trim();     //권한(sysmng, officemng, officer) 
    String ss_mem_tel       = session.getAttribute("mem_tel")      == null ? "" : session.getAttribute("mem_tel").toString().trim();      //연락처
    String ss_mem_sysCod    = session.getAttribute("mem_sysCod")   == null ? "" : session.getAttribute("mem_sysCod").toString().trim();   //시스템
    String ss_mem_sysCod2   = session.getAttribute("mem_sysCod2")  == null ? "" : session.getAttribute("mem_sysCod2").toString().trim();  //서브시스템
    
    String ss_mem_sysCod1   = "";
	//로그인 사용자의 권한 시스템을 String형식으로 생성한다.(예: 'PI','CE','UA')
	String[] tempSsSysCod = ss_mem_sysCod.split(",");
	
	for(int i=0; i<tempSsSysCod.length; i++) {
		if(i == (tempSsSysCod.length-1)){
			ss_mem_sysCod1 += "'" + tempSsSysCod[i] + "'";
    		
    	} else {
    		ss_mem_sysCod1 += "'" + tempSsSysCod[i] + "'" + ",";
    	}	
	}
	
    if(ss_mem_cod.equals("")) {
    	System.out.println("세션만료");
        session.invalidate();
        out.println("<script language='javascript'> ");
		out.println("alert('세션이 만료 되었습니다.\\n\\n다시 로그인 하여 주세요.');");	
		out.println("location.href='/logout.jsp'");
		out.println("</script>");
    }

      out.write("\r\n");
      out.write("\r\n\r\n");
      out.write("\r\n");
      out.write("\r\n\r\n");
      out.write("  \r\n\r\n<BODY>\r\n");
      
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	String sql;
    
	int totalCnt = 0;
  String holyDay[] = {"11", "29", "210", "211", "31", "55", "517", "66", "815", "918", "919", "920", "103", "109", "1225"};
	
	try {
	    conn = getConnection();
	    conn.setAutoCommit(false);
	    stmt = conn.createStatement();
	    
		
		// Calendar 클래스의 인스턴스 cal 생성
		Calendar cal = new GregorianCalendar();

		
        String thisMonth  = "" + (cal.get(java.util.Calendar.MONTH) + 1);
               
		
		// 년도 정보를 이전 페이지에서 받아와 strYear 변수에 저장
		String strYear  = request.getParameter("year");
		// 월 정보를 이전 페이지에서 받아와 strMonth 변수에 저장
		String strMonth = request.getParameter("month"); 

        if(strYear == null) {
            strYear  = "" + cal.get(java.util.Calendar.YEAR);
        }
        
        if(strMonth == null) {
            strMonth  = "" + cal.get(java.util.Calendar.MONTH);
        }
		
		// 현재 년도를 가져와 year 변수에 저장
		int year  = cal.get(java.util.Calendar.YEAR);
		// 현재 월를 가져와 month 변수에 저장
		int month = cal.get(java.util.Calendar.MONTH);
		// 현재 일를 가져와 date 변수에 저장
		int date  = cal.get(java.util.Calendar.DATE);
		
		
		// 이전 페이지에서 넘어온 파라미터가 있는경우
		if(strYear != null)
		{
		    // 현재 년도를 이전 페이지에서 넘겨받은 년도로 변경
		    year  = Integer.parseInt(strYear);
		} 

		// 이전 페이지에서 넘어온 파라미터가 있는경우
		if(strMonth != null)
		{
		    // 현재 월를 이전 페이지에서 넘겨받은 월로 변경
		    month = Integer.parseInt(strMonth);
		} 
		
		// 파라미터에 의해서 조정된 달력의 년도와 월 정보를 셋팅하고 캘린더 설정
		cal.set(year, month, 1);
		// 달력에서 시작일을 받아와 startDay 변수에 저장
		int startDay = cal.getMinimum(java.util.Calendar.DATE);
		
		// 표시하고자 하는 달의 마지막 날짜를 설정
		int endDay   = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
		// 표시하고자 하는 달의 시작 날짜의 요일을 설정
		int start = cal.get(java.util.Calendar.DAY_OF_WEEK);
		int newLine  = 0;
		
      out.write("\r\n\t\t\r\n\t\t\r\n\t\t<TABLE width=\"700\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n            <TR>\r\n                <TD align=\"center\" colspan=\"3\">           <h3> 행정망 전화 담당자</h3>        </TD>\r\n            </TR>\r\n    \t\t<TR>\r\n\r\n        \t\t<TD align=\"center\" valign=\"bottom\" width=\"100\" height=\"30\">\r\n            \t\t<STRONG>");
                                                                  out.print(year);
      out.write("년 </STRONG>\r\n        \t\t</TD>\r\n        \t\t<TD width=\"450\" align=\"center\" valign=\"bottom\">\r\n            \t\t<DIV align=\"center\">\r\n                    \r\n                    ");
                        if(month - 1 == -1) { 
      out.write("\r\n            \t\t");
      out.write("\r\n            \t\t<a href=\"duty.jsp?year=");
            out.print(year-1);
      out.write("&month=");
      out.print(11);
      out.write("\">◀</a>\r\n                    ");
      } else { 
      out.write("\r\n                    <a href=\"duty.jsp?year=");
            out.print(year);
      out.write("&month=");
      out.print(month - 1);
      out.write("\">◀</a>\r\n                    ");
      } 
      out.write("                    \r\n            \t\t<STRONG>");
            out.print(month + 1);
      out.write("월 </STRONG>\r\n                    \r\n                    ");
      if(month + 1 == 12) { 
      out.write("\r\n            \t\t");
      out.write("\r\n            \t\t<a href=\"duty.jsp?year=");
            out.print(year+1);
      out.write("&month=0\">▶</a>\r\n                    ");
      } else { 
      out.write("\r\n                    <a href=\"duty.jsp?year=");
            out.print(year);
      out.write("&month=");
      out.print(month + 1 );
      out.write("\">▶</a>\r\n                    ");
      } 
      out.write("\r\n\r\n            \t\t</DIV>\r\n        \t\t</TD>\r\n        \t\t<TD width=\"150\" align=\"center\" valign=\"bottom\">\r\n            \t\t<DIV align=\"center\">\r\n            \t\t<STRONG>오늘 : ");
                                    out.print(year + "-" + thisMonth + "-" + date);
      out.write("</STRONG>\r\n            \t\t</DIV>\r\n        \t\t</TD>\r\n    \t\t</TR>\r\n\t\t</TABLE>\r\n\r\n        <BR>\r\n        <TABLE width=\"700\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\">\r\n            <tr>\r\n                <TD width=\"100\" height=\"30\" align=\"center\" bgcolor=\"purple\"><B><font color=\"RED\">일</font></B></TD>\r\n                <TD width=\"100\" align=\"center\" bgcolor=\"purple\"><B>월</B></TD>\r\n                <TD width=\"100\" align=\"center\" bgcolor=\"purple\"><B>화</B></TD>\r\n                <TD width=\"100\" align=\"center\" bgcolor=\"purple\"><B>수</B></TD>\r\n                <TD width=\"100\" align=\"center\" bgcolor=\"purple\"><B>목</B></TD>\r\n                <TD width=\"100\" align=\"center\" bgcolor=\"purple\"><B>금</B></TD>\r\n                <TD width=\"100\" align=\"center\" bgcolor=\"purple\"><font color=\"BLUE\"><B>토</B></font></TD>\r\n            </tr>\r\n        </TABLE>\r\n\r\n\t\t<TABLE width=\"700\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\">\r\n\t\t<tr>\r\n\t\t");
                                                                                                                                                                                                                                                                  

		// 달력을 그려줄때 시작일 이전에는 빈공간으로 표시하기 위한 td 를 생성
		for(int index=1; index<start; index++ )
		{
		    out.print("<TD>&nbsp;</TD>");
		    newLine++;
		}
        
		sql = new String();
	    sql += "select count(*) as cnt from tb_sec_duty where DUTY_MONTH = '"+(month+1)+"' and DUTY_YEAR = '" + year + "'";
	    sql += "  ORDER BY sort_order ASC"; 
	    
		
//		System.out.println(sql);
		rs = stmt.executeQuery(sql);  
        
        if(rs.next()) {
        	totalCnt = Integer.parseInt(rs.getString("CNT"));  
        }
		
//		System.out.println(totalCnt);
        
		rs.close();
        
		if(totalCnt==0){
			out.println("<script language='javascript'>" );
			out.println("alert('담당 순서 미작성');");  
			out.println("history.back();");         	 
			out.println("</script>");
		}else{

			//내용삭제(갱신)
		    sql = new String();
		    sql += " select * from tb_sec_duty where  DUTY_MONTH = '"+(month+1)+"' and DUTY_YEAR = '" + year + "' ORDER BY sort_order ASC";
//		    System.out.println(sql);
		    rs = stmt.executeQuery(sql);

//	        if(!rs.next()) {
        if(totalCnt==0){
            	out.println("<tr>");
				out.println("<td colspan='12' bgcolor='#FFFFFF'>");
				out.println("<p align='center'><font size='2' >입력된 내용이 없습니다.");
				out.println("</td>");
				out.println("</tr>");
				out.println("<tr><td colspan='33' height='1' bgcolor='dddddd'></td></tr>");
	        }else{

                String memList[] = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
                String nowMonth = "";
                
				while(rs.next()) {
					nowMonth = rs.getString("DUTY_MONTH");
					int loc_idx = Integer.parseInt(rs.getString("DUTY_DAY"));
					memList[loc_idx] = nvl(rs.getString("MEM_NAME"));
//                    System.out.println(loc_idx + " : " + memList[loc_idx]);
                }   // while
                
				// 1일 부터 해당월 마지막 날까지 반복
				for(int index=1; index<=endDay; index++)
				{
				    // 새로 개행이 일어나는 경우는 일요일 이므로 빨간색으로
				    // 표시하고 아닌경우 검은색으로 글자색을 표시함
				    String color = (newLine == 0)?"RED":"BLACK";
        				      color = (newLine == 6)?"BLUE":color;
				    // 날짜를 1씩 증가시키면서 테이블을 생성하여 달력을 완성
				    String dateStr = ""+index;
                    
                    String MEM_NAME = memList[index];
                        
                    // 공휴일이면 빨강으로...
                    for(int i=0; i<holyDay.length;i++) {
                        String nowDay = "" + (month + 1) + dateStr;
//                        System.out.println(HolyDay[i] + " : " + nowDay);
                        if(holyDay[i].equals(nowDay)) {
                        	color = "RED";
                        }
                    }
                    
                    // 현재일과 날자가 같으면 글자를 굵게 강조
                    if (index == date && thisMonth.equals(nowMonth))
                    {
                        // 현재 날짜 볼드체 (요구사항에는 없었음 ^^)
                        out.print("<TD align=center width=100 height=80 bgcolor='gray'><font color="+color+"><U><B>"+dateStr+ "   "+MEM_NAME+"</font></B></U></TD>");
                    } else {
                        out.print("<TD align=center width=100 height=80><font color="+color+">"+dateStr+ "   "+MEM_NAME+"</font></TD>");
                    }
                    
				    newLine++;
				    // 일주일이 7일 이므로 newLine 가 7인 경우
				    if (newLine == 7)
				    {
				        // html 에서 테이블상 개행을 하기 위하여 tr 을 닫아줌
				        out.print("</TR>");
				        // index 가 endDay 이면 tr 을 하나 열어줌
				        if( index <= endDay )
				        {
				            out.print("<TR>");
				        }
				        // newLine 값을 0으로
				        newLine=0;
				    }
					    
		   		 }   // for
	        }
			
			// 달력을 출력후 남은 td 를 매우기 위해서 루핑을 돌며 td 을 채워 놓음
			while(newLine > 0 && newLine < 7)
			{
			    out.print("<TD width='100'>&nbsp;</TD>");
			    newLine++;
			}
		}
    
	} catch (Exception e) {
		conn.rollback();
		out.println("<script language='javascript'>" );
		out.println("alert('" +e.getMessage().replaceAll("\n","\\n")+ "');");  
		out.println("history.back();");         	 
		out.println("</script>");
		e.printStackTrace();
	}
	finally {
	    try {
	        conn.setAutoCommit(true);
	        if(rs != null)   {rs.close();}
	        if(stmt != null) {stmt.close();}
	        if(conn != null) {conn.close();}
	    } catch (Exception e){}
	}

	
      out.write("\r\n\t</tr>\r\n\t</TABLE>\r\n    ※ 담당 일정 수정은 정성균대리에게..\r\n\t</BODY>\r\n</HTML>");
                            } catch (Throwable t) {
      out = _jspx_out;
      if (out != null && out.getBufferSize() != 0)
        out.clearBuffer();
      if (pageContext != null) pageContext.handlePageException(t);
    } finally {
      if (_jspxFactory != null) _jspxFactory.releasePageContext(pageContext);
    }
  }
}
