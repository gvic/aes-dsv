package com;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderList {
	// Storage for an arbitrary number of details.
	// Inappropriate, since we want to check only once
	// an order ID.
	private ArrayList<Order> orderListArr;
	private HashMap<Integer, Order> orderList;

	/**
	 * Perform any initialization .
	 */
	public OrderList() {
		orderList = new HashMap<Integer, Order>();
	}

	/**
	 * Add a new set of details to the list
	 * 
	 * @param details
	 *            The details of the staff
	 * @throws Exception
	 */
	public void addDetails(Order details){
		if (orderList.containsKey(details.getId())) {
			System.out.println("This order ID " + details.getId() + " already exist");
			System.out.println("It won't be processed");
		} else {
			orderList.put(details.getId(), details);
		}
	}

	public boolean hasOrder() {
		return orderListArr.size() != 0;
	}

	public Order getNextOrder() {
		Order order = orderListArr.remove(0);
		while(order.isProcessed())
			order = orderListArr.remove(0);
		return order;

	}

	/**
	 * @return All the details
	 */
	public String listDetails() {
		StringBuffer allEntries = new StringBuffer();
		allEntries.append("<html>");
		for (Order details : orderListArr) {
			allEntries.append(details.getId()+ " - ");
			allEntries.append(details.getCustomerType() + " - ");
			allEntries.append(details.getQuantity());
			allEntries.append("<br>");
		}
		allEntries.append("</html>");
		return allEntries.toString();
	}

	public void generateArrayList() {
		orderListArr = new ArrayList<Order>(orderList.values());
	}

}
