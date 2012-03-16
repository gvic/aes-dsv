package com;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class Manager implements IModel {

	private File ordersFile;
	private File itemsFile;
	private OrderList allOrders;
	private HashMap<Integer, IItem> allItems;
	private IListener controller;
	private HashSet<Worker> workers;
	private int maximumWorkers;

	public Manager(File fi, File fo) {
		itemsFile = fi;
		ordersFile = fo;
		allOrders = new OrderList();
		allItems = new HashMap<Integer, IItem>();
		controller = new Controller();
		maximumWorkers = 3;
		this.workers = new HashSet<Worker>();
		// By default only one worker
		workers.add(new Worker(allOrders, allItems));
		workers.add(new Worker(allOrders, allItems));
	}

	public void loadOrders() throws IOException {
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
	}

	public void loadItems() throws IOException {
		FileInputStream fstream = new FileInputStream(ordersFile);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine = null;
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

	}

	// initialises list of orders, processes them
	public void run() throws IOException {
		this.process();
	}

	// alter this method
	// initialises list of orders and also loads allItems
	public void initialise() throws IOException {
		this.loadItems();
		this.loadOrders();
		controller.initialiseFields(allOrders, allItems);

	}
	

	// the worker works through the orders
	public void process() {
		Iterator<Worker> it = workers.iterator();
		while (it.hasNext()) {
			Worker w = it.next();
			(new Thread(w)).start();
		}
	}

	@Override
	public void setController(IListener controller) {
		this.controller = controller;
		Iterator<Worker> it = workers.iterator();
		while (it.hasNext())
			it.next().setController(controller);

	}

	public int getMaximumWorkers() {
		return maximumWorkers;
	}

	public void setMaximumWorkers(int maximumWorkers) {
		this.maximumWorkers = maximumWorkers;
	}

	@Override
	public void stop() {
		Iterator<Worker> it = workers.iterator();
		while (it.hasNext()){			
			Worker w = it.next();
			w.stop();
		}
	}

}
