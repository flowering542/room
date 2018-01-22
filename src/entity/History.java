package entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the history database table.
 * 
 */
@Entity
@NamedQuery(name="History.findAll", query="SELECT h FROM History h")
public class History implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private HistoryPK id;

	@Temporal(TemporalType.DATE)
	private Date etime;

	private BigDecimal sumpay;

	private int userid;

	public History() {
	}

	public HistoryPK getId() {
		return this.id;
	}

	public void setId(HistoryPK id) {
		this.id = id;
	}

	public Date getEtime() {
		return this.etime;
	}

	public void setEtime(Date etime) {
		this.etime = etime;
	}

	public BigDecimal getSumpay() {
		return this.sumpay;
	}

	public void setSumpay(BigDecimal sumpay) {
		this.sumpay = sumpay;
	}

	public int getUserid() {
		return this.userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

}