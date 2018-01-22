package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import util.EntityManagerUtil;
import entity.Member;

public class MemberDao {
	public static void addMember(Member s) throws Exception{
		EntityManager em=EntityManagerUtil.getEntityManager();
		if(getByCategory(s.getCategory())!=null){
			throw new Exception("会员类别不能重复！");
		}
		em.getTransaction().begin();
		em.persist(s);
		em.getTransaction().commit();
		em.close();
	}
	
	public static void updateMember(Member s){
		EntityManager em=EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
		em.merge(s);
		em.getTransaction().commit();
		em.close();
	}
	
	public static void delMember(int mid){
		EntityManager em=EntityManagerUtil.getEntityManager();
		Member s=em.find(Member.class, mid);
		em.getTransaction().begin();
		em.remove(s);
		em.getTransaction().commit();
		em.close();
	}
	
	public static List<Member> getMembers(){
		EntityManager em = EntityManagerUtil.getEntityManager();
		Query query = em.createNamedQuery("Member.findAll");
		List<Member> Members = query.getResultList();
		return Members;
	}
	
	public static List<Member> getByCategorys(String category){
		EntityManager em = EntityManagerUtil.getEntityManager();
		Query query=em.createQuery("SELECT s FROM Member s where s.category like :category");
		List<Member> Members=query.setParameter("category", "%"+category+"%").getResultList();
		em.close();
		return Members;
	}
	
	public static Member getByCategory(String category){
		EntityManager em = EntityManagerUtil.getEntityManager();
		Query query=em.createQuery("SELECT s FROM Member s where s.category = :category");
		Member m = null;
		try{
			 m = (Member) query.setParameter("category", category).getSingleResult();
		}catch(NoResultException e){
			m = null;
		}
		em.close();
		return m;
	}
	
	public static Member getMemberById(int mid){
		EntityManager em = EntityManagerUtil.getEntityManager();
		Query query=em.createQuery("SELECT s FROM Member s where s.mid = :mid");
		Member Member = (Member)query.setParameter("mid", mid).getSingleResult();
		em.close();
		return Member;
	}
	
	public static Member getMember(int mid){
		EntityManager em=EntityManagerUtil.getEntityManager();
		Member Member = null;
		try{
			Member = em.find(Member.class, mid);
		}catch(NoResultException e){
			Member = null;
		}
		em.close();
		return Member;
	}
	
	public static List<Map<String,String>> MemberAll(){
		List<Map<String,String>> tt = new ArrayList<Map<String,String>>();
		List<Member> stus = getMembers();
		Map<String,String> cc = null;
		for (Member s : stus) {
			  cc = new HashMap<String,String>();
			  cc.put("mid",String.valueOf(s.getMid()));
			  cc.put("category",s.getCategory());
			  cc.put("rate",String.valueOf(s.getRate()));
			  tt.add(cc);
		}
		return tt;
	}
}
