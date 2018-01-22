package util;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Date_String {
	
	/**  
     * 获取现在时间  
     *  
     * @return返回字符串格式 yyyyMMddHHmmss  
     */ 
	 public static String getNowYmdHms(){   
	       Date currentTime = new Date();   
	       SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
	       String dateString = formatter.format(currentTime);   
	       //System.out.println("TIME:::"+dateString);   
	       return dateString;   
	 }
	 public static String getNowYmd() {   
	       Date currentTime = new Date();   
	       SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");   
	       String dateString = formatter.format(currentTime);   
	       //System.out.println("TIME:::"+dateString);   
	       return dateString;   
	 }
	  /**  
	     * java时间date 
	     *  
	     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss  
	     */ 
	 public static String setStringYmdHms(Date date1) {   
	       if(date1==null)  return "";
		 	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
	       String dateString = formatter.format(date1);   
	       //System.out.println("TIME:::"+dateString);   
	       return dateString;   
	 }
	  /**  
	     * java时间date 
	     *  
	     * @return返回字符串格式 yyyy-MM-dd  
	     */ 
	 public static String setStringYmd(Date date1) {   
	       if(date1==null) return "";
		 	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");   
	       String dateString = formatter.format(date1);   
	       //System.out.println("TIME:::"+dateString);   
	       return dateString;   
	 }
	 /**  
	     * java时间date 
	     *  
	     * @return 设置日期格式 yyyy-MM-dd  
	     */ 
	 public static Date setDateYmd(String date1) {   
	       if(date1==null) return null;
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        Date b = null;
	        try {
	            b = simpleDateFormat.parse(date1);
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }   
	       return b;   
	 }
	 
	 public static Date setDateYmdHis(String date1) {   
	       if(date1==null) return null;
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        Date b = null;
	        try {
	            b = simpleDateFormat.parse(date1);
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }   
	       return b;   
	 }
}