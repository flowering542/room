package entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the advance database table.
 * 
 */
@Entity
@NamedQuery(name="Advance.findAll", query="SELECT a FROM Advance a")
public class Advance implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AdvancePK id;

	@Temporal(TemporalType.DATE)
	private Date etime;

	@Temporal(TemporalType.TIMESTAMP)
	private Date otime;

	private String person;

	//bi-directional many-to-one association to Room
	@ManyToOne
	@JoinColumn(name="rno")
	private Room room;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="userid")
	private User user;

	public Advance() {
	}

	public AdvancePK getId() {
		return this.id;
	}

	public void setId(AdvancePK id) {
		this.id = id;
	}

	public Date getEtime() {
		return this.etime;
	}

	public void setEtime(Date etime) {
		this.etime = etime;
	}

	public Date getOtime() {
		return this.otime;
	}

	public void setOtime(Date otime) {
		this.otime = otime;
	}

	public String getPerson() {
		return this.person;
	}

	public void setPerson(String person) {
		this.person = person;
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