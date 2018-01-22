package dao;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.junit.Test;

import util.Date_String;
import util.EntityManagerUtil;
import entity.History;
import entity.HistoryPK;

public class HistoryDao {
	public static History getHistory(String rno,String cid,String btime){
		EntityManager em=EntityManagerUtil.getEntityManager();
		HistoryPK Historypk=new HistoryPK();
		Historypk.setRno(rno);
		Historypk.setCid(cid);
		Historypk.setBtime(Date_String.setDateYmd(btime));
		History History = null;
		try{
			History =em.find(History.class, Historypk);
		}catch(NoResultException e){
			History = null;
		}
		em.close();
		return History;
	}
	
	public static void delHistory(String rno,String cid,String btime){
		EntityManager em=EntityManagerUtil.getEntityManager();
		HistoryPK Historypk=new HistoryPK();
		Historypk.setRno(rno);
		Historypk.setCid(cid);
		Historypk.setBtime(Date_String.setDateYmd(btime));
		History History=em.find(History.class, Historypk);
		em.getTransaction().begin();
		em.remove(History);	
		em.getTransaction().commit();
		em.close();
	}
	
	public static void addHistory(String rno,String cid,String btime,int userid,String etime,double sumpay) throws Exception{
    	EntityManager em=EntityManagerUtil.getEntityManager();
		HistoryPK Historypk=new HistoryPK();
		Historypk.setRno(rno);
		Historypk.setCid(cid);
		Historypk.setBtime(Date_String.setDateYmd(btime));
		History History=new History();
		History.setId(Historypk);
		em.getTransaction().begin();
	    History.setEtime(Date_String.setDateYmd(etime)); 
	    History.setUserid(userid);
	    History.setSumpay(BigDecimal.valueOf(sumpay));
		em.persist(History);
		em.getTransaction().commit();
		em.close();
    }
	
	@Test
	public void test(){ 
		  try {
			System.out.print(HistoryDao.getHistory("s007", "55", "1993-05-05"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
