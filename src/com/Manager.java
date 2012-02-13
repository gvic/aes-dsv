package com;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Manager
{
	
	private File ordersFile;
	private File itemsFile;
	private Worker worker;   
    private OrderList allOrders;
	private HashMap<Integer, IItem> allItems;

   
    public Manager(File fi, File fo)
    {
    	itemsFile = fi;
		ordersFile = fo;
    	allOrders = new  OrderList();
		allItems = new HashMap<Integer, IItem>();
    }
    
    //initialises list of orders, processes them
    public void run(){
    	this.initialise();
    	this.process();
    }

    //alter this method 
    //initialises list of orders and also loads allItems
    public void initialise() {
    	try {
    		Scanner scanner = new Scanner (ordersFile);
        	while(scanner.hasNext()){  
            	String inputLine = scanner.nextLine();
            	String[] datas = inputLine.split(";");
            	//probably need to do something with the line here
        		Order order= new Order (datas);
        		//add to list
                allOrders.addDetails(order);
        	}
        	
        	scanner = new Scanner(itemsFile);
        	while(scanner.hasNext()){  
            	String inputLine = scanner.nextLine();
            	String[] datas = inputLine.split(";");
            	IItem i = new BookItem(datas);
            	allItems.put(i.getId(), i);
        	}
            	
    	}
    	catch (FileNotFoundException e) {
    		//do something here
    	}
    	
    }
    
    //the worker works through the orders
    public void process() {
    	worker = new Worker (allOrders,allItems);
    	worker.run();
    }
    
    

    
    //start the program
    public static void main (String args[]) {
    	if (args.length != 2) {
			System.out.println("USAGE");
			System.out.println("java com.Manager <items_file_name> <orders_file_name>");
			System.exit(0);
		}
    	
    	File fi = new File(args[0]);
    	File fo = new File(args[1]);
    	
//    	if(!fi.exists() || !fo.exists()){
//    		System.out.println("One or both of the specified files does not exist");
//			System.exit(0);
//    	}
    		
    	Manager manager = new Manager(fi, fo);
    	manager.run();
    }
}

