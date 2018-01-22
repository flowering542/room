package dao;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.junit.Test;

import entity.Advance;
import entity.AdvancePK;
import entity.Room;
import entity.User;
import util.Date_String;
import util.EntityManagerUtil;

public class AdvanceDao {
	public static Advance getAdvance(String rno,String phone,String btime){
		EntityManager em=EntityManagerUtil.getEntityManager();
		AdvancePK Advancepk=new AdvancePK();
		Advancepk.setRno(rno);
		Advancepk.setPhone(phone);
		Advancepk.setBtime(Date_String.setDateYmd(btime));
		Advance Advance = null;
		try{
			Advance =em.find(Advance.class, Advancepk);
		}catch(NoResultException e){
			Advance = null;
		}
		em.close();
		return Advance;
	}
	
	public static Advance getAdvance(String rno,String btime){
		EntityManager em=EntityManagerUtil.getEntityManager();
		AdvancePK Advancepk=new AdvancePK();
		Advancepk.setRno(rno);
		Advancepk.setBtime(Date_String.setDateYmd(btime));
		Advance Advance = null;
		try{
			Advance =em.find(Advance.class, Advancepk);
		}catch(NoResultException e){
			Advance = null;
		}
		em.close();
		return Advance;
	}
	
	public static void delAdvance(String rno,String phone,String btime){
		EntityManager em=EntityManagerUtil.getEntityManager();
		AdvancePK Advancepk=new AdvancePK();
		Advancepk.setRno(rno);
		Advancepk.setPhone(phone);
		Advancepk.setBtime(Date_String.setDateYmd(btime));
		Advance Advance=em.find(Advance.class, Advancepk);
		em.getTransaction().begin();
		em.remove(Advance);	
		em.getTransaction().commit();
		em.close();
	}
	
	public static void addAdvance(String rno,String phone,String btime,int userid,String etime,String person) throws Exception{
    	EntityManager em=EntityManagerUtil.getEntityManager();
		if(getAdvance(rno,phone,btime)!=null){
			throw new Exception("房间号为<"+rno+">已被预定!");
		}
		AdvancePK Advancepk=new AdvancePK();
		Advancepk.setRno(rno);
		Advancepk.setPhone(phone);
		Advancepk.setBtime(Date_String.setDateYmd(btime));
		Advance Advance=new Advance();
		Advance.setId(Advancepk);
		em.getTransaction().begin();
		Room room=em.find(Room.class, rno);
		User user=em.find(User.class, userid);
		Advance.setRoom(room);
		Advance.setUser(user);
		Advance.setEtime(Date_String.setDateYmd(etime));
		Advance.setPerson(person);
		Advance.setOtime(Date_String.setDateYmdHis(Date_String.getNowYmdHms()));
		em.persist(Advance);
		em.refresh(room);
		em.refresh(user);
		em.getTransaction().commit();
		em.close();
    }
	@Test
	public void test(){ 
		  try {
			System.out.print(AdvanceDao.getAdvance("r005","2018-01-11"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
