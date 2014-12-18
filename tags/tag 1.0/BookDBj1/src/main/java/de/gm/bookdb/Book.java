package de.gm.bookdb;

public class Book {

	private int id;
	private String title;
	private String author;

	public Book() {

	}

	public Book(String title, String author, int id) {
		this.title = title;
		this.author = author;
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", author=" + author
				+ "]";
	}
}