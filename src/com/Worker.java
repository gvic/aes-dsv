package com;

public class Worker {
	private OrderList allOrders;
	
	public Worker(OrderList allOrders) {
		// PUT THE ITEMS LIST IN ORDER TO UPDATE IT 
		// WHEN AN ORDER IS PROCESSED IN THE
		// processOneOrder METHOD ?
		
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
		//process the order here
		
		//then print (just for testing at start)
		System.out.println(order.getId());
	}

}
