package entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int userid;

	private byte age;

	private String password;

	private String realname;

	private String sex;

	private String username;

	private String usertype;

	//bi-directional many-to-one association to Advance
	@OneToMany(mappedBy="user")
	private List<Advance> advances;

	//bi-directional many-to-one association to Book
	@OneToMany(mappedBy="user")
	private List<Book> books;

	public User() {
	}

	public int getUserid() {
		return this.userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public byte getAge() {
		return this.age;
	}

	public void setAge(byte age) {
		this.age = age;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealname() {
		return this.realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsertype() {
		return this.usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public List<Advance> getAdvances() {
		return this.advances;
	}

	public void setAdvances(List<Advance> advances) {
		this.advances = advances;
	}

	public Advance addAdvance(Advance advance) {
		getAdvances().add(advance);
		advance.setUser(this);

		return advance;
	}

	public Advance removeAdvance(Advance advance) {
		getAdvances().remove(advance);
		advance.setUser(null);

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
		book.setUser(this);

		return book;
	}

	public Book removeBook(Book book) {
		getBooks().remove(book);
		book.setUser(null);

		return book;
	}

}