package entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the advance database table.
 * 
 */
@Embeddable
public class AdvancePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private String rno;

	@Temporal(TemporalType.DATE)
	private java.util.Date btime;

	private String phone;

	public AdvancePK() {
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
	public String getPhone() {
		return this.phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AdvancePK)) {
			return false;
		}
		AdvancePK castOther = (AdvancePK)other;
		return 
			this.rno.equals(castOther.rno)
			&& this.btime.equals(castOther.btime)
			&& this.phone.equals(castOther.phone);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.rno.hashCode();
		hash = hash * prime + this.btime.hashCode();
		hash = hash * prime + this.phone.hashCode();
		
		return hash;
	}
}