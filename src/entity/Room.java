package entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the room database table.
 * 
 */
@Entity
@NamedQuery(name="Room.findAll", query="SELECT r FROM Room r")
public class Room implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String rno;

	private BigDecimal cost;

	private String fno;

	private String position;

	private String standard;

	//bi-directional many-to-one association to Advance
	@OneToMany(mappedBy="room")
	private List<Advance> advances;

	//bi-directional many-to-one association to Book
	@OneToMany(mappedBy="room")
	private List<Book> books;

	//bi-directional many-to-one association to Increase
	@OneToMany(mappedBy="room")
	private List<Increase> increases;

	public Room() {
	}

	public String getRno() {
		return this.rno;
	}

	public void setRno(String rno) {
		this.rno = rno;
	}

	public BigDecimal getCost() {
		return this.cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public String getFno() {
		return this.fno;
	}

	public void setFno(String fno) {
		this.fno = fno;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getStandard() {
		return this.standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public List<Advance> getAdvances() {
		return this.advances;
	}

	public void setAdvances(List<Advance> advances) {
		this.advances = advances;
	}

	public Advance addAdvance(Advance advance) {
		getAdvances().add(advance);
		advance.setRoom(this);

		return advance;
	}

	public Advance removeAdvance(Advance advance) {
		getAdvances().remove(advance);
		advance.setRoom(null);

		return advance;
	}

	public List<Book> getBooks() {
		return this.books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public Book addBook(Book book) {
		getBooks().add(book);
		book.setRoom(this);

		return book;
	}

	public Book removeBook(Book book) {
		getBooks().remove(book);
		book.setRoom(null);

		return book;
	}

	public List<Increase> getIncreases() {
		return this.increases;
	}

	public void setIncreases(List<Increase> increases) {
		this.increases = increases;
	}

	public Increase addIncreas(Increase increas) {
		getIncreases().add(increas);
		increas.setRoom(this);

		return increas;
	}

	public Increase removeIncreas(Increase increas) {
		getIncreases().remove(increas);
		increas.setRoom(null);

		return increas;
	}

}