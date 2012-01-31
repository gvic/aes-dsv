package com;

public class Worker {
	OrderList allOrders;
	
	public Worker(OrderList allOrders) {
		
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
