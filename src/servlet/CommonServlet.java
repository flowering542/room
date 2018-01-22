package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import dao.UserDao;
import entity.Json;
import entity.User;

/**
 * Servlet implementation class CommonServlet
 */
@WebServlet("/CommonServlet")
public class CommonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		User u = (User)request.getSession().getAttribute("currentUser");
		Json json = new Json();
		String password = request.getParameter("password");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");
		if(password1.equals(password2)){
			  if(password.equals(u.getPassword())){
				  u.setPassword(password1);
				  UserDao.updateUser(u);
				  json.setSuccess(true);
			  }else{
				  json.setSuccess(false);
				  json.setMsg("输入旧密码不正确！");
			  }
		}else{
			json.setSuccess(false);
			json.setMsg("");
		}
		response.getWriter().print(JSON.toJSONString(json));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
