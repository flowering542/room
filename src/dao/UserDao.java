package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.junit.Test;

import entity.User;
import util.EntityManagerUtil;

public class UserDao {
	/**根据用户名查询用户信息*/
	public static User getUserByName(String username,String password){
		EntityManager em=EntityManagerUtil.getEntityManager();
		Query query=em.createQuery("SELECT u FROM User u where u.username = :username and u.password = :password");
		User user=null;
		try{
			user=(User)query.setParameter("username",username).setParameter("password", password).getSingleResult();
		}catch(NoResultException e){
			user=null;
		}
		return user;
	}
	
	public static void addUser(User u) throws Exception{
		EntityManager em=EntityManagerUtil.getEntityManager();
		if(getByName(u.getUsername())!=null){
			throw new Exception("用户名不能重复添加！");
		}
		em.getTransaction().begin();
		em.persist(u);
		em.getTransaction().commit();
		em.close();
	}
	
	public static void updateUser(User u){
		EntityManager em=EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
		em.merge(u);
		em.getTransaction().commit();
		em.close();
	}
	
	public static void delUser(int userid){
		EntityManager em=EntityManagerUtil.getEntityManager();
		User s=em.find(User.class, userid);
		em.getTransaction().begin();
		em.remove(s);
		em.getTransaction().commit();
		em.close();
	}
	
	public static List<User> getUsers(){
		EntityManager em = EntityManagerUtil.getEntityManager();
		Query query = em.createNamedQuery("User.findAll");
		List<User> users = query.getResultList();
		return users;
	}
	
	public static List<User> getUsersByname(String username){
		EntityManager em = EntityManagerUtil.getEntityManager();
		Query query=em.createQuery("SELECT s FROM User s where s.username like :username");
		List<User> Users=query.setParameter("username", "%"+username+"%").getResultList();
		//query.getSingleResult();
		em.close();
		return Users;
	}
	
	public static User getByName(String username){
		EntityManager em = EntityManagerUtil.getEntityManager();
		Query query=em.createQuery("SELECT s FROM User s where s.username = :username");
		User user=null;
		try{
			user = (User)query.setParameter("username", username).getSingleResult();
		}catch(NoResultException e){
			user=null;
		} 
		em.close();
		return user;
	}
	
	public static User getUser(int userid){
		EntityManager em=EntityManagerUtil.getEntityManager();
		User user=em.find(User.class, userid);
		em.close();
		return user;
	}
	
	public static List<Map<String,String>> UserAll(){
		List<Map<String,String>> tt = new ArrayList<Map<String,String>>();
		List<User> stus = getUsers();
		Map<String,String> cc = null;
		for (User s : stus) {
			  cc = new HashMap<String,String>();
			  cc.put("userid", String.valueOf(s.getUserid()));
			  cc.put("username", s.getUsername());
			  cc.put("realname", s.getRealname());
			  cc.put("password", s.getPassword());
			  cc.put("usertype", s.getUsertype());
			  cc.put("sex", s.getSex());
			  cc.put("age", String.valueOf(s.getAge()));
			  tt.add(cc);
		}
		return tt;
	}
	@Test
	public void test(){
		  User user = new User();
		  user.setUsername("sand");
		  user.setUsertype("1");
		  user.setPassword("123456");
		  try {
			UserDao.addUser(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
