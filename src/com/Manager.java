package com;

import java.util.Scanner;
import java.io.*;

public class Manager
{
	private Worker worker;   
    private OrderList allOrders;
   
    public Manager()
    {
    	allOrders = new  OrderList();
    }
    
    //initialises list of orders, processes them
    public void run(){
    	this.initialise();
    	this.process();
    }

    //alter this method 
    //initialises list of orders
    public void initialise() {
    	try {
    		Scanner scanner = new Scanner (new File("Orders.txt"));
        	while(scanner.hasNext()){  
            	String inputLine = scanner.nextLine();
            	//probably need to do something with the line here
        		Order order= new Order (inputLine);
        		//add to list
                allOrders.addDetails(order);
        	}
    	}
    	catch (FileNotFoundException e) {
    		//do something here
    	}
    }
    
    //the worker works through the orders
    public void process() {
    	worker = new Worker (allOrders);
    	worker.run();
    }
    
    

    
    //start the program
    public static void main (String args[]) {
    	Manager manager = new Manager();
    	manager.run();
    }
}

