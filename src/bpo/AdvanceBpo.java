package bpo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Test;

import entity.Room;
import util.Date_String;
import util.EntityManagerUtil;

public class AdvanceBpo {
    /*
     * 能够根据标准和日期查询客房空闲情况；能够为指定客户预订指定的客房，
     * 并记录预订开始时间、预订结束时间、联系人、联系电话、前台操作员、操作时间等信息。
     * */
	public static List<Room> roomAdanvce(String standard,String btime,String etime){
		EntityManager em = EntityManagerUtil.getEntityManager();
		String sql = null;
		if(standard.equals("") || standard == null){
			   sql = "SELECT * FROM room r WHERE r.rno not in (SELECT rno from advance a WHERE a.btime between '"+btime+"' and  '"+etime+"' UNION SELECT rno from book b WHERE b.btime between '"+btime+"' and  '"+etime+"')";
		}else{ sql = "SELECT * FROM room r WHERE r.rno not in (SELECT rno from advance a WHERE a.btime between '"+btime+"' and  '"+etime+"' UNION SELECT rno from book b WHERE b.btime between '"+btime+"' and  '"+etime+"') and r.standard='"+standard+"'";}
		Query query=em.createNativeQuery(sql, Room.class);
		List<Room> Rooms = query.getResultList();
		em.close();
		return Rooms;
	}
	
	public static List<Map<String,String>> selectList(String standard,String btime,String etime){
		List<Map<String,String>> tt = new ArrayList<Map<String,String>>();
		List<Room> Rooms = roomAdanvce(standard, btime, etime);
		Map<String,String> cc = null;
		for (Room user: Rooms) {
			 cc = new HashMap<String,String>();
			 cc.put("rno", user.getRno());
			 cc.put("fno", user.getFno());
			 cc.put("standard", user.getStandard());
			 cc.put("cost", String.valueOf(user.getCost()));
			 cc.put("position", user.getPosition());
			 tt.add(cc);
		}
		return tt;
	}
	 /*
     * 能够根据客户名或联系电话查询其预订的客房信息，其中，一个客户可以预订多个客房；
     * 能够根据楼层号、房间号查询客房的预订详细信息。
     * */
	public static List<Room> searchAdanvce(String person,String phone){
		EntityManager em = EntityManagerUtil.getEntityManager();
		String sql = null;
	    sql="SELECT * FROM room r WHERE r.rno in (SELECT a.rno FROM advance a WHERE a.person LIKE '%"+person+"%' AND a.phone LIKE '%"+phone+"%')";
		Query query=em.createNativeQuery(sql, Room.class);
		List<Room> Rooms = query.getResultList();
		em.close();
		return Rooms;
	}
	
	public static List<Map<String,String>> searchList(String person,String phone){
		List<Map<String,String>> tt = new ArrayList<Map<String,String>>();
		List<Room> Rooms = searchAdanvce(person, phone);
		Map<String,String> cc = null;
		for (Room user: Rooms) {
			 cc = new HashMap<String,String>();
			 cc.put("rno", user.getRno());
			 cc.put("fno", user.getFno());
			 cc.put("standard", user.getStandard());
			 cc.put("cost", String.valueOf(user.getCost()));
			 cc.put("position", user.getPosition());
			 cc.put("person",user.getAdvances().get(0).getPerson());
			 cc.put("phone",user.getAdvances().get(0).getId().getPhone());
			 cc.put("btime",Date_String.setStringYmd(user.getAdvances().get(0).getId().getBtime()));
			 cc.put("etime",Date_String.setStringYmd(user.getAdvances().get(0).getEtime()));
			 tt.add(cc);
		}
		return tt;
	}
	
	public static void addBook(String cid,String rno,String phone,int userid,String btime,String etime,double prepay){
		EntityManager em = EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
		Query query = em.createNativeQuery("{CALL post_room(?,?,?,?,?,?,?)}");
		query.setParameter(1, cid);
		query.setParameter(2, rno);
		query.setParameter(3, phone);
		query.setParameter(4, new Integer(userid));
		query.setParameter(5, btime);
		query.setParameter(6, etime);
		query.setParameter(7, new Double(prepay));
		query.executeUpdate();
		em.getTransaction().commit();
		em.close();
	}
	
	public static void addBook1(String rno,String cid,int userid,String btime,String etime){
		EntityManager em = EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
		Query query = em.createNativeQuery("{CALL out_room(?,?,?,?,?)}");
		query.setParameter(1, cid);
		query.setParameter(2, rno);
		query.setParameter(3, new Integer(userid));
		query.setParameter(4, btime);
		query.setParameter(5, etime);
		query.setFirstResult(6);
		String result = query.getSingleResult().toString();
		em.getTransaction().commit();
		System.out.print(result);
		em.close();
	}
	@Test
	public void test(){
		addBook("430523199605054338","r006","17864159289",1,"2018-01-10","2018-01-12",100.0);
	}
	
}
