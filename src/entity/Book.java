package entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the book database table.
 * 
 */
@Entity
@NamedQuery(name="Book.findAll", query="SELECT b FROM Book b")
public class Book implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BookPK id;

	@Temporal(TemporalType.DATE)
	private Date etime;

	private BigDecimal prepay;

	//bi-directional many-to-one association to Customer
	@ManyToOne
	@JoinColumn(name="cid")
	private Customer customer;

	//bi-directional many-to-one association to Room
	@ManyToOne
	@JoinColumn(name="rno")
	private Room room;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="userid")
	private User user;

	public Book() {
	}

	public BookPK getId() {
		return this.id;
	}

	public void setId(BookPK id) {
		this.id = id;
	}

	public Date getEtime() {
		return this.etime;
	}

	public void setEtime(Date etime) {
		this.etime = etime;
	}

	public BigDecimal getPrepay() {
		return this.prepay;
	}

	public void setPrepay(BigDecimal prepay) {
		this.prepay = prepay;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Room getRoom() {
		return this.room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}