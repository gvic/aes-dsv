package com;

import java.util.HashMap;

public class Worker {
	private OrderList allOrders;
	private HashMap<Integer, IItem> allItems;
	
	public Worker(OrderList allOrders, HashMap<Integer, IItem> allItems) {
		this.allItems = allItems;
		this.allOrders = allOrders;
	}
	
	public void run() {
		System.out.println("Processing");
		while (allOrders.hasOrder()) {
			processOneOrder();
		}
	}
	
	public void processOneOrder() {
		Order order = allOrders.getNextOrder() ;
		System.out.println(order.getId());
	}

}
