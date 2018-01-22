package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Date_String;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import dao.CustomerDao;
import entity.Customer;
import entity.Json;
import entity.Member;

/**
 * Servlet implementation class CustomerServlet
 */
@WebServlet("/CustomerServlet")
public class CustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		if(action.equals("query")){
			this.query(request, response);
		}else if(action.equals("add")){
			this.add(request, response);
		}else if(action.equals("save")){
			this.save(request, response);
		}else if(action.equals("delete")){
			this.delete(request, response);
		}else{
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}
	
	private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException{
		  String ids = request.getParameter("ids");
		  String a[]  = ids.split(",");
		  Json json = new Json();
		  try{
			  for(int i=0,length=a.length;i<length;i++){
				  CustomerDao.delCustomer(a[i]);
			  }
			json.setSuccess(true);
			json.setMsg("");
		  }catch (Exception e) {
				// TODO Auto-generated catch block
				 json.setSuccess(false);
				 json.setMsg(e.getMessage());
				e.printStackTrace();
			 }
		    String jsonStr = JSON.toJSONString(json);
	        //将json字符串作为响应内容输出到客户端浏览器。
	        response.getWriter().write(jsonStr);
	}
	
	private void add(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String cid = request.getParameter("cid");
		  String cname = request.getParameter("cname");
		  String sex = request.getParameter("sex");
		  String phone = request.getParameter("phone");
		  Date birthday = Date_String.setDateYmd(request.getParameter("birthday"));
		  String email = request.getParameter("email");
		  String mid = request.getParameter("mid");
		  Customer stu = new Customer();
		  Member member = new Member();
		  member.setMid(Integer.valueOf(mid));
		  Json json = new Json();
		  stu.setCid(cid);
		  stu.setCname(cname);
		  stu.setMember(member);
		  stu.setSex(sex);
		  stu.setPhone(phone);
		  stu.setEmail(email);
		  stu.setBirthday(new java.sql.Date(birthday.getTime()));
		  try {
			CustomerDao.addCustomer(stu);
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
	
	private void save(HttpServletRequest request, HttpServletResponse response) throws IOException{
		  String cid = request.getParameter("cid");
		  String cname = request.getParameter("cname");
		  String sex = request.getParameter("sex");
		  String phone = request.getParameter("phone");
		  Date birthday = Date_String.setDateYmd(request.getParameter("birthday"));
		  String email = request.getParameter("email");
		  String mid = request.getParameter("mid");
		  Customer stu = new Customer();
		  Member member = new Member();
		  member.setMid(Integer.valueOf(mid));
		  Json json = new Json();
		  stu.setCid(cid);
		  stu.setCname(cname);
		  stu.setMember(member);
		  stu.setSex(sex);
		  stu.setPhone(phone);
		  stu.setEmail(email);
		  stu.setBirthday(new java.sql.Date(birthday.getTime()));
		  try {
			CustomerDao.updateCustomer(stu);
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
	
	private void query(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
	    list = CustomerDao.CustomerAll();
		Object jsonArray = JSON.toJSON(list);
		JSONObject result = new JSONObject();
		result.put("rows", jsonArray);
		result.put("total", list.size()); 
		response.getWriter().print(result);
	}	
}
