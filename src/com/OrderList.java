package com;

import java.util.ArrayList;

public class OrderList
{
    // Storage for an arbitrary number of details.
    private ArrayList <Order> orderList;
    
    /**
     * Perform any initialization .
     */
    public OrderList()
    {
    	orderList =new ArrayList<Order>() ;
    }
    
    /**
     * Add a new set of details to the list
     * @param details The details of the staff
     */
    public void addDetails(Order details) 
    {
    	orderList.add(details);
    }
    
    public boolean hasOrder() {
    	return orderList.size() != 0;
    }
    
    public Order getNextOrder() {
    	Order order =  orderList.remove(0);
    	return order;

    }

    /**
     * @return All the details
     */
    public String listDetails()
    {
    	StringBuffer allEntries = new StringBuffer();
    	for (Order details : orderList) {
            allEntries.append(details.getId() );
            allEntries.append('\n');
        }
        return allEntries.toString();
    }
    

}
