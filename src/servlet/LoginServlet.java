package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;

import dao.UserDao;
import entity.Json;
import entity.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		if(action.equals("login")){
		     this.login(request,response);
		}else if(action.equals("logout")){
			this.logout(request, response);
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
	
	private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession session = request.getSession();
		session.invalidate();
		response.sendRedirect(request.getContextPath()+"/login.jsp");
	}
	
	private void login(HttpServletRequest request, HttpServletResponse response) throws IOException{
	    String username = request.getParameter("username");
        String password = request.getParameter("password");
        String usertype = request.getParameter("usertype");
        Json json = new Json();
        User u = UserDao.getUserByName(username, password);
        if(u!=null && u.getUsertype().equals(usertype)){
       	    request.getSession().setAttribute("currentUser",u);
       	    json.setMsg("登录成功");
	            json.setSuccess(true);
	            if(usertype.equals("1")){
	            	json.setUrl("/operator/index.jsp");
	            }else if(usertype.equals("2")){
	            	json.setUrl("/admin/index.jsp");
	            }else{
	            	json.setUrl("/error.jsp");
	            }
        }else{
       	  json.setMsg("用户名或密码错误，登录失败！");
	          json.setSuccess(false);
        }
        String jsonStr = JSON.toJSONString(json);
	        //将json字符串作为响应内容输出到客户端浏览器。
	     response.getWriter().write(jsonStr);
	}


}
