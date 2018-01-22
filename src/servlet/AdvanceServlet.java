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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import dao.AdvanceDao;
import dao.BookDao;
import dao.CustomerDao;
import entity.Json;
import entity.User;

/**
 * Servlet implementation class AdvanceServlet
 */
@WebServlet("/AdvanceServlet")
public class AdvanceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		if(action.equals("search")){
			this.search(request, response);
		}else if(action.equals("advance")){
			this.advance(request, response);
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
	
    private void search1(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	String person = request.getParameter("person");
		String phone = request.getParameter("phone");
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
	    list = AdvanceBpo.searchList(person, phone);
		Object jsonArray = JSON.toJSON(list);
		JSONObject result = new JSONObject();
		result.put("rows", jsonArray);
		result.put("total", list.size()); 
		response.getWriter().print(result);
	}
	
	private void book(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String  cid = request.getParameter("cid");
		String btime = request.getParameter("btime");
		String etime = request.getParameter("etime");
		String phone = request.getParameter("phone");
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
				AdvanceDao.delAdvance(rno, phone, btime);
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
	
	private void advance(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String rno = request.getParameter("rno");
		String btime = request.getParameter("btime");
		String etime = request.getParameter("etime");
		String phone = request.getParameter("phone");
		String person = request.getParameter("person");
		Json json = new Json();
		User u = (User)request.getSession().getAttribute("currentUser");
		int userid = u.getUserid();
		try {
			AdvanceDao.addAdvance(rno, phone, btime, userid, etime, person);
			json.setSuccess(true);
			json.setMsg("");
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

}
