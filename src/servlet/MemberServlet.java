package servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import dao.MemberDao;
import entity.Json;
import entity.Member;

/**
 * Servlet implementation class MemberServlet
 */
@WebServlet("/MemberServlet")
public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
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
				  MemberDao.delMember(Integer.valueOf(a[i]));
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
		  String category = request.getParameter("category");
		  String rate = request.getParameter("rate");
		  Member m = new Member();
		  Json json = new Json();
		  m.setCategory(category);
		  m.setRate(BigDecimal.valueOf(Double.valueOf(rate)));
		  try {
			MemberDao.updateMember(m);
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
		  String mid = request.getParameter("id");
		  String category = request.getParameter("category");
		  String rate = request.getParameter("rate");
		  Member m = new Member();
		  Json json = new Json();
		  m.setMid(Integer.valueOf(mid));
		  m.setCategory(category);
		  m.setRate(BigDecimal.valueOf(Double.valueOf(rate)));
		  try {
			MemberDao.updateMember(m);
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
	    list = MemberDao.MemberAll();
		Object jsonArray = JSON.toJSON(list);
		JSONObject result = new JSONObject();
		result.put("rows", jsonArray);
		result.put("total", list.size()); 
		response.getWriter().print(result);
	}	
}

