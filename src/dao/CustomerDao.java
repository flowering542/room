package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.junit.Test;

import util.Date_String;
import util.EntityManagerUtil;
import entity.Customer;

public class CustomerDao {
	public static void addCustomer(Customer s) throws Exception{
		EntityManager em=EntityManagerUtil.getEntityManager();
		if(getCustomer(s.getCid())!=null){
			throw new Exception("身份证号不能重复！");
		}
		em.getTransaction().begin();
		em.persist(s);
		em.getTransaction().commit();
		em.close();
	}
	
	public static void updateCustomer(Customer s){
		EntityManager em=EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
		em.merge(s);
		em.getTransaction().commit();
		em.close();
	}
	
	public static void delCustomer(String cid){
		EntityManager em=EntityManagerUtil.getEntityManager();
		Customer s=em.find(Customer.class, cid);
		em.getTransaction().begin();
		em.remove(s);
		em.getTransaction().commit();
		em.close();
	}
	
	public static List<Customer> getCustomers(){
		EntityManager em = EntityManagerUtil.getEntityManager();
		Query query = em.createNamedQuery("Customer.findAll");
		List<Customer> Customers = query.getResultList();
		return Customers;
	}
	
	public static List<Customer> getByCname(String cname){
		EntityManager em = EntityManagerUtil.getEntityManager();
		Query query=em.createQuery("SELECT s FROM customer s where s.cname like :cname");
		List<Customer> Customers=query.setParameter("cname", "%"+cname+"%").getResultList();
		//query.getSingleResult();
		em.close();
		return Customers;
	}
	
	public static Customer getCustomerById(String cid){
		EntityManager em = EntityManagerUtil.getEntityManager();
		Query query=em.createQuery("SELECT s FROM Customer s where s.cid = :cid");
		Customer Customer = (Customer)query.setParameter("cid", cid).getSingleResult();
		em.close();
		return Customer;
	}
	
	public static Customer getCustomer(String cid){
		EntityManager em=EntityManagerUtil.getEntityManager();
		Customer Customer = null;
		try{
			Customer = em.find(Customer.class, cid);
		}catch(NoResultException e){
			Customer = null;
		}
		em.close();
		return Customer;
	}
	
	public static List<Map<String,String>> CustomerAll(){
		List<Map<String,String>> tt = new ArrayList<Map<String,String>>();
		List<Customer> stus = getCustomers();
		Map<String,String> cc = null;
		for (Customer s : stus) {
			  cc = new HashMap<String,String>();
			  cc.put("cid", s.getCid());
			  cc.put("cname", s.getCname());
			  cc.put("birthday", Date_String.setStringYmd(s.getBirthday()));
			  cc.put("sex", s.getSex());
			  cc.put("phone", s.getPhone());
			  cc.put("email", s.getEmail());
			  cc.put("mid", String.valueOf(s.getMember().getMid()));
			  tt.add(cc);
		}
		return tt;
	}
	@Test
	public void test(){
		List<Map<String,String>> tt = CustomerAll();
		for (Map<String, String> s : tt) {
			 System.out.println(s);
		}
	}
}
