package com;

public class BookItem extends AItem {
	
	private String title;
	private String author;
	private String category;
	
	public BookItem(String[] datas) {
		// The first 3 array cells stands for id, unit price, and quantity 
		super(datas);
		title = datas[3];
		author = datas[4];
		category = datas[5];
	}
	
	public String getTitle() {
		return title;
	}
	public String getAuthor() {
		return author;
	}
	public String getCategory() {
		return category;
	}
}
