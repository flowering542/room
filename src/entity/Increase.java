package entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the increase database table.
 * 
 */
@Entity
@NamedQuery(name="Increase.findAll", query="SELECT i FROM Increase i")
public class Increase implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int ino;

	private String bz;

	private BigDecimal icost;

	@Temporal(TemporalType.TIMESTAMP)
	private Date itime;

	private String itype;

	//bi-directional many-to-one association to Room
	@ManyToOne
	@JoinColumn(name="rno")
	private Room room;

	public Increase() {
	}

	public int getIno() {
		return this.ino;
	}

	public void setIno(int ino) {
		this.ino = ino;
	}

	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public BigDecimal getIcost() {
		return this.icost;
	}

	public void setIcost(BigDecimal icost) {
		this.icost = icost;
	}

	public Date getItime() {
		return this.itime;
	}

	public void setItime(Date itime) {
		this.itime = itime;
	}

	public String getItype() {
		return this.itype;
	}

	public void setItype(String itype) {
		this.itype = itype;
	}

	public Room getRoom() {
		return this.room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

}