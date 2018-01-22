package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import entity.Room;
import util.EntityManagerUtil;

public class RoomDao {
	public static void addRoom(Room s) throws Exception{
		EntityManager em=EntityManagerUtil.getEntityManager();
		if(getRoom(s.getRno())!=null){
			throw new Exception("客房号不能重复！");
		}
		em.getTransaction().begin();
		em.persist(s);
		em.getTransaction().commit();
		em.close();
	}
	
	public static void updateRoom(Room s){
		EntityManager em=EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
		em.merge(s);
		em.getTransaction().commit();
		em.close();
	}
	
	public static void delRoom(String rno){
		EntityManager em=EntityManagerUtil.getEntityManager();
		Room s=em.find(Room.class, rno);
		em.getTransaction().begin();
		em.remove(s);
		em.getTransaction().commit();
		em.close();
	}
	
	public static List<Room> getRooms(){
		EntityManager em = EntityManagerUtil.getEntityManager();
		Query query = em.createNamedQuery("Room.findAll");
		List<Room> Rooms = query.getResultList();
		return Rooms;
	}
	
	public static List<Room> getByStandard(String standard){
		EntityManager em = EntityManagerUtil.getEntityManager();
		Query query=em.createQuery("SELECT s FROM Room s where s.standard like :standard");
		List<Room> Rooms=query.setParameter("standard", "%"+standard+"%").getResultList();
		//query.getSingleResult();
		em.close();
		return Rooms;
	}
	
	public static Room getRoomById(String rno){
		EntityManager em = EntityManagerUtil.getEntityManager();
		Query query=em.createQuery("SELECT s FROM Room s where s.rno = :rno");
		Room Room = (Room)query.setParameter("rno", rno).getSingleResult();
		em.close();
		return Room;
	}
	
	public static Room getRoom(String rno){
		EntityManager em=EntityManagerUtil.getEntityManager();
		Room Room = null;
		try{
			Room = em.find(Room.class, rno);
		}catch(NoResultException e){
			Room = null;
		}
		em.close();
		return Room;
	}
	
	public static List<Map<String,String>> RoomAll(){
		List<Map<String,String>> tt = new ArrayList<Map<String,String>>();
		List<Room> stus = getRooms();
		Map<String,String> cc = null;
		for (Room s : stus) {
			  cc = new HashMap<String,String>();
			  cc.put("rno", s.getRno());
			  cc.put("fno", s.getFno());
			  cc.put("standard", s.getStandard());
			  cc.put("cost", String.valueOf(s.getCost()));
			  cc.put("position", s.getPosition());
			  tt.add(cc);
		}
		return tt;
	}
	
	public static List<Map<String,String>> RnoAll(){
		List<Map<String,String>> tt = new ArrayList<Map<String,String>>();
		List<Room> stus = getRooms();
		Map<String,String> cc = null;
		for (Room s : stus) {
			  cc = new HashMap<String,String>();
			  cc.put("id", s.getRno());
			  cc.put("text", s.getRno());
			  tt.add(cc);
		}
		return tt;
	}
}
