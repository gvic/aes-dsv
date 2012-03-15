package com;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Manager implements IModel{

	private File ordersFile;
	private File itemsFile;
	private Worker worker;
	private OrderList allOrders;
	private HashMap<Integer, IItem> allItems;
	private IListener controller;

	public Manager(File fi, File fo) {
		itemsFile = fi;
		ordersFile = fo;
		allOrders = new OrderList();
		allItems = new HashMap<Integer, IItem>();
		controller = new Controller();
	}

	// initialises list of orders, processes them
	public void run() throws IOException {
		this.initialise();
		this.process();
	}

	// alter this method
	// initialises list of orders and also loads allItems
	public void initialise() throws IOException {
		FileInputStream fstream = new FileInputStream(ordersFile);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine = null;
		while ((strLine = br.readLine()) != null) {
			String[] datas = strLine.split(";");
			Order order = null;
			try {
				order = new Order(datas);
				allOrders.addDetails(order);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		allOrders.generateArrayList();

		fstream.close();
		in.close();
		br.close();
		strLine = null;
		fstream = new FileInputStream(itemsFile);
		in = new DataInputStream(fstream);
		br = new BufferedReader(new InputStreamReader(in));
		try {
			while ((strLine = br.readLine()) != null) {
				String[] datas = strLine.split(";");
				IItem i = new BookItem(datas);
				allItems.put(i.getId(), i);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		controller.initialiseFields(allOrders,allItems);

	}

	// the worker works through the orders
	public void process() {
		worker = new Worker(allOrders, allItems);
		worker.run();
	}

	// start the program
	public static void main(String args[]) {
		if (args.length != 2) {
			System.out.println("USAGE");
			System.out
					.println("java com.Manager <items_file_name> <orders_file_name>");
			System.exit(0);
		}

		File fi = new File(args[0]);
		File fo = new File(args[1]);
		


		if (!fi.exists()) {
			System.out.println(fi.toString() + " does not exist");
			System.exit(0);
		}
		if (!fo.exists()) {
			System.out.println(fi.toString() + " does not exist");
			System.exit(0);
		}

		Manager manager = new Manager(fi, fo);
		try {
			manager.run();
		} catch (IOException e) {
			System.out
					.println("An error occured while the program tried to read the files.");
			System.exit(0);
		}
	}

	@Override
	public void setController(IListener controller) {
        this.controller = controller;
    }
}
