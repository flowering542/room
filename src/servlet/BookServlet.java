package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bpo.AdvanceBpo;
import bpo.BookBpo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import dao.BookDao;
import dao.CustomerDao;
import dao.HistoryDao;
import entity.History;
import entity.Json;
import entity.User;

/**
 * Servlet implementation class BookServler
 */
@WebServlet("/BookServlet")
public class BookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		if(action.equals("search")){
			this.search(request, response);
		}else if(action.equals("drop")){
			this.drop(request, response);
		}else if(action.equals("search1")){
			this.search1(request, response);
		}else if(action.equals("book")){
			this.book(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}
	
	private void drop(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String rno = request.getParameter("rno");
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
	    list = BookBpo.searchList(rno);
		Object jsonArray = JSON.toJSON(list);
		JSONObject result = new JSONObject();
		result.put("rows", jsonArray);
		result.put("total", list.size()); 
		response.getWriter().print(result);
	}
	
	private void search1(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String  cid = request.getParameter("cid");
		String btime = request.getParameter("btime");
		String etime = request.getParameter("etime");
		String rno = request.getParameter("rno");
		Json json = new Json();
		User u = (User)request.getSession().getAttribute("currentUser");
		int userid = u.getUserid();
		try {
			BookBpo.dropBook(rno, cid, userid, btime, etime);
			try{
				History h = HistoryDao.getHistory(rno, cid, btime);
				json.setSuccess(true);
				json.setMsg("房间号："+rno+"<br/>入住时间："+btime+"<br/>退房时间："+etime+"<br/>总费用："+h.getSumpay());
			}catch(Exception e){
				 json.setSuccess(false);
				 json.setMsg(e.getMessage());
				 e.printStackTrace();
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			 json.setSuccess(false);
			 json.setMsg(e.getMessage());
			 e.printStackTrace();
		 }
		 String jsonStr = JSON.toJSONString(json);
	        //将json字符串作为响应内容输出到客户端浏览器。
	     response.getWriter().write(jsonStr);
	}
	
	private void book(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String  cid = request.getParameter("cid");
		String btime = request.getParameter("btime");
		String etime = request.getParameter("etime");
		String rno = request.getParameter("rno");
		double prepay = Double.parseDouble(request.getParameter("prepay"));
		Json json = new Json();
		User u = (User)request.getSession().getAttribute("currentUser");
		int userid = u.getUserid();
		if(CustomerDao.getCustomer(cid) == null){
			 json.setSuccess(false);
			 json.setMsg("用户身份证号不存在，请先添加用户信息");
		}else{
			try {
				BookDao.addBook(rno, cid, btime, userid, etime, prepay);
				json.setSuccess(true);
				json.setMsg("");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				 json.setSuccess(false);
				 json.setMsg(e.getMessage());
				e.printStackTrace();
			 }
		}
		  String jsonStr = JSON.toJSONString(json);
	        //将json字符串作为响应内容输出到客户端浏览器。
	      response.getWriter().write(jsonStr);

     }
	
	private void search(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String standard = request.getParameter("standard");
		String btime = request.getParameter("btime");
		String etime = request.getParameter("etime");
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
	    list = AdvanceBpo.selectList(standard, btime, etime);
		Object jsonArray = JSON.toJSON(list);
		JSONObject result = new JSONObject();
		result.put("rows", jsonArray);
		result.put("total", list.size()); 
		response.getWriter().print(result);
	}

}
