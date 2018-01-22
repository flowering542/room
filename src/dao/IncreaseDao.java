package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import util.Date_String;
import util.EntityManagerUtil;
import entity.Increase;

public class IncreaseDao {
	public static void addIncrease(Increase s) throws Exception{
		EntityManager em=EntityManagerUtil.getEntityManager();
		if(getByItype(s.getItype())!=null){
			throw new Exception("消费类型不能重复！");
		}
		em.getTransaction().begin();
		em.persist(s);
		em.getTransaction().commit();
		em.close();
	}
	
	public static void updateIncrease(Increase s){
		EntityManager em=EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
		em.merge(s);
		em.getTransaction().commit();
		em.close();
	}
	
	public static void delIncrease(int ino){
		EntityManager em=EntityManagerUtil.getEntityManager();
		Increase s=em.find(Increase.class, ino);
		em.getTransaction().begin();
		em.remove(s);
		em.getTransaction().commit();
		em.close();
	}
	
	public static List<Increase> getIncreases(){
		EntityManager em = EntityManagerUtil.getEntityManager();
		Query query = em.createNamedQuery("Increase.findAll");
		List<Increase> Increases = query.getResultList();
		return Increases;
	}
	
	public static List<Increase> getByItypes(String itype){
		EntityManager em = EntityManagerUtil.getEntityManager();
		Query query=em.createQuery("SELECT s FROM Increase s where s.itype like :itype");
		List<Increase> Increases=query.setParameter("itype", "%"+itype+"%").getResultList();
		em.close();
		return Increases;
	}
	
	public static Increase getByItype(String itype){
		EntityManager em = EntityManagerUtil.getEntityManager();
		Query query=em.createQuery("SELECT s FROM Increase s where s.itype = :itype");
		Increase increase = null;
		try{
			increase = (Increase)query.setParameter("itype", itype).getSingleResult();
		}catch(NoResultException e){
			increase = null;
		}
		em.close();
		return increase;
	}
	
	public static Increase getIncreaseById(int ino){
		EntityManager em = EntityManagerUtil.getEntityManager();
		Query query=em.createQuery("SELECT s FROM Increase s where s.ino = :ino");
		Increase Increase = (Increase)query.setParameter("ino", ino).getSingleResult();
		em.close();
		return Increase;
	}
	
	public static Increase getIncrease(int ino){
		EntityManager em=EntityManagerUtil.getEntityManager();
		Increase Increase = null;
		try{
			Increase = em.find(Increase.class, ino);
		}catch(NoResultException e){
			Increase = null;
		}
		em.close();
		return Increase;
	}
	
	public static List<Map<String,String>> IncreaseAll(){
		List<Map<String,String>> tt = new ArrayList<Map<String,String>>();
		List<Increase> stus = getIncreases();
		Map<String,String> cc = null;
		for (Increase s : stus) {
			  cc = new HashMap<String,String>();
			  cc.put("ino",String.valueOf(s.getIno()));
			  cc.put("rno",s.getRoom().getRno());
			  cc.put("itype",s.getItype());
			  cc.put("icost",String.valueOf(s.getIcost()));
			  cc.put("itime",Date_String.setStringYmd(s.getItime()));
			  cc.put("bz",s.getBz());
			  tt.add(cc);
		}
		return tt;
	}
}
