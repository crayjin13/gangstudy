package com.jts.gangstudy.domain;

public class Item {
	private String item_name;
	private int quantity;
	private int total_amount;
	
	public Item(String item_name, int quantity, int total_amount) {
		this.item_name = item_name;
		this.quantity = quantity;
		this.total_amount = total_amount;
	}
	
	public String getItem_name() {
		return item_name;
	}
	public int getQuantity() {
		return quantity;
	}
	public int getTotal_amount() {
		return total_amount;
	}
}
