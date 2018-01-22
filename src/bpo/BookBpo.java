package bpo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Test;

import util.Date_String;
import util.EntityManagerUtil;
import entity.Room;

public class BookBpo {
	 
	/*
	 * 能够根据房间号查询客房信息；能够完成指定客户对指定房间的退房操作（利用存储过程实现），
	 * 即首先增加一条客户实际入住记录，包括：客户身份证编号、客户名、房间号、入住时间、退房时间、
	 * 房费、前台操作员，然后修改客房状态为“空闲”；最后根据会员类别和入住天数计算房费。 
	 * */
	public static List<Room> roomList(String rno){
		EntityManager em = EntityManagerUtil.getEntityManager();
		String sql = null;
		sql = "SELECT * FROM room r WHERE r.rno in (SELECT rno FROM book WHERE book.rno = '"+rno+"')";
		Query query=em.createNativeQuery(sql, Room.class);
		List<Room> Rooms = query.getResultList();
		em.close();
		return Rooms;
	}
	
	public static List<Map<String,String>> searchList(String rno){
		List<Map<String,String>> tt = new ArrayList<Map<String,String>>();
		List<Room> Rooms = roomList(rno);
		Map<String,String> cc = null;
		for (Room user: Rooms) {
			 cc = new HashMap<String,String>();
			 cc.put("rno", user.getRno());
			 cc.put("fno", user.getFno());
			 cc.put("standard", user.getStandard());
			 cc.put("cost", String.valueOf(user.getCost()));
			 cc.put("position", user.getPosition());
			 cc.put("cid",user.getBooks().get(0).getId().getCid());
			 cc.put("btime",Date_String.setStringYmd(user.getBooks().get(0).getId().getBtime()));
			 cc.put("etime",Date_String.setStringYmd(user.getBooks().get(0).getEtime()));
			 tt.add(cc);
		}
		return tt;
	}
	
	public static void dropBook(String rno,String cid,int userid,String btime,String etime){
		EntityManager em = EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
		Query query = em.createNativeQuery("{CALL out_room(?,?,?,?,?)}");
		query.setParameter(1, rno);
		query.setParameter(2, cid);
		query.setParameter(3, new Integer(userid));
		query.setParameter(4, btime);
		query.setParameter(5, etime);
		query.executeUpdate();
		em.getTransaction().commit();
		em.close();
	}
	@Test
	public void test(){
		dropBook("r006","430523199605054338",1,"2018-01-10","2018-01-12");
	}
}
