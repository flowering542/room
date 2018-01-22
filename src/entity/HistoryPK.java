package entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the history database table.
 * 
 */
@Embeddable
public class HistoryPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String cid;

	private String rno;

	@Temporal(TemporalType.DATE)
	private java.util.Date btime;

	public HistoryPK() {
	}
	public String getCid() {
		return this.cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getRno() {
		return this.rno;
	}
	public void setRno(String rno) {
		this.rno = rno;
	}
	public java.util.Date getBtime() {
		return this.btime;
	}
	public void setBtime(java.util.Date btime) {
		this.btime = btime;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof HistoryPK)) {
			return false;
		}
		HistoryPK castOther = (HistoryPK)other;
		return 
			this.cid.equals(castOther.cid)
			&& this.rno.equals(castOther.rno)
			&& this.btime.equals(castOther.btime);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.cid.hashCode();
		hash = hash * prime + this.rno.hashCode();
		hash = hash * prime + this.btime.hashCode();
		
		return hash;
	}
}