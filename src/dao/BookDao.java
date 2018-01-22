package dao;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.junit.Test;

import util.Date_String;
import util.EntityManagerUtil;
import entity.Book;
import entity.BookPK;
import entity.Customer;
import entity.Room;
import entity.User;

public class BookDao {
	public static Book getBook(String rno,String cid,String btime){
		EntityManager em=EntityManagerUtil.getEntityManager();
		BookPK Bookpk=new BookPK();
		Bookpk.setRno(rno);
		Bookpk.setCid(cid);
		Bookpk.setBtime(Date_String.setDateYmd(btime));
		Book Book = null;
		try{
			Book =em.find(Book.class, Bookpk);
		}catch(NoResultException e){
			Book = null;
		}
		em.close();
		return Book;
	}
	
	public static void delBook(String rno,String cid,String btime){
		EntityManager em=EntityManagerUtil.getEntityManager();
		BookPK Bookpk=new BookPK();
		Bookpk.setRno(rno);
		Bookpk.setCid(cid);
		Bookpk.setBtime(Date_String.setDateYmd(btime));
		Book Book=em.find(Book.class, Bookpk);
		em.getTransaction().begin();
		em.remove(Book);	
		em.getTransaction().commit();
		em.close();
	}
	
	public static void addBook(String rno,String cid,String btime,int userid,String etime,double prepay) throws Exception{
    	EntityManager em=EntityManagerUtil.getEntityManager();
		if(getBook(rno,cid,btime)!=null){
			throw new Exception("房间号为<"+rno+">已被入住!");
		}
		BookPK Bookpk=new BookPK();
		Bookpk.setRno(rno);
		Bookpk.setCid(cid);
		Bookpk.setBtime(Date_String.setDateYmd(btime));
		Book Book=new Book();
		Book.setId(Bookpk);
		em.getTransaction().begin();
		Room room=em.find(Room.class, rno);
		User user=em.find(User.class, userid);
		Customer customer = em.find(Customer.class, cid);
		Book.setRoom(room);
		Book.setUser(user);
		Book.setCustomer(customer);
		Book.setEtime(Date_String.setDateYmd(etime));
		Book.setPrepay(BigDecimal.valueOf(prepay));
		em.persist(Book);
		em.refresh(room);
		em.refresh(customer);
		em.getTransaction().commit();
		em.close();
    }
	
	@Test
	public void test(){ 
		  try {
			BookDao.addBook("r007", "430523199605054338", "2018-01-11", 1, "2018-1-12", 100.0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
