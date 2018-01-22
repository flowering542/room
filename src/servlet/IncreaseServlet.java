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

import util.Date_String;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import dao.IncreaseDao;
import dao.RoomDao;
import entity.Json;
import entity.Increase;
import entity.Room;

/**
 * Servlet implementation class IncreaseServlet
 */
@WebServlet("/IncreaseServlet")
public class IncreaseServlet extends HttpServlet {
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
		}else if(action.equals("option")){
			this.option(request, response);
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
	
	//显示选择的课程
	private void option(HttpServletRequest request, HttpServletResponse response) throws IOException{
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		list = RoomDao.RnoAll();
		Object jsonArray = JSON.toJSON(list);
		response.getWriter().print(jsonArray);
	}
		
	private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException{
		  String ids = request.getParameter("ids");
		  String a[]  = ids.split(",");
		  Json json = new Json();
		  try{
			  for(int i=0,length=a.length;i<length;i++){
				  IncreaseDao.delIncrease(Integer.valueOf(a[i]));
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
		  String rno = request.getParameter("rno");
		  String itype = request.getParameter("itype");
		  String icost = request.getParameter("icost");
		  String itime = request.getParameter("itime");
		  String bz = request.getParameter("bz");
		  Increase Increase = new Increase();
		  Room room = new Room();
		  room.setRno(rno);
		  Json json = new Json();
		  Increase.setItype(itype);
		  Increase.setBz(bz);
		  Increase.setIcost(BigDecimal.valueOf(Double.valueOf(icost)));
		  Increase.setBz(bz);
		  Increase.setItime(Date_String.setDateYmd(itime));
		  Increase.setRoom(room);
		  try {
			IncreaseDao.addIncrease(Increase);
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
		  String rno = request.getParameter("rno");
		  String ino = request.getParameter("id");
		  String itype = request.getParameter("itype");
		  String icost = request.getParameter("icost");
		  String itime = request.getParameter("itime");
		  String bz = request.getParameter("bz");
		  Increase Increase = new Increase();
		  Room room = new Room();
		  room.setRno(rno);
		  Json json = new Json();
		  Increase.setIno(Integer.valueOf(ino));
		  Increase.setItype(itype);
		  Increase.setBz(bz);
		  Increase.setIcost(BigDecimal.valueOf(Double.valueOf(icost)));
		  Increase.setBz(bz);
		  Increase.setItime(Date_String.setDateYmd(itime));
		  Increase.setRoom(room);
		  try {
			IncreaseDao.updateIncrease(Increase);
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
	    list = IncreaseDao.IncreaseAll();
		Object jsonArray = JSON.toJSON(list);
		JSONObject result = new JSONObject();
		result.put("rows", jsonArray);
		result.put("total", list.size()); 
		response.getWriter().print(result);
	}	
}