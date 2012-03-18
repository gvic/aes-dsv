package com;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
	private boolean needInitialisation;

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
		allOrders.clear();
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
		allItems.clear();
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
		br.close();
		in.close();
		fstream.close();
	}

	// initialises list of orders, processes them
	public void run() throws IOException {
		if (this.needInitialisation) {
			this.initialise();
		}
		this.process();
		this.needInitialisation = true;
	}

	// alter this method
	// initialises list of orders and also loads allItems
	public void initialise() throws IOException {
		Statistics stats = Statistics.getInstance();
		stats.initialiseFields();
		this.loadItems();
		this.loadOrders();
		controller.initialiseFields(allOrders, allItems);

	}

	// the worker works through the orders
	public void process() throws IOException {
		Iterator<Worker> it = workers.iterator();
		ArrayList<Thread> threads = new ArrayList<Thread>(maximumWorkers);
		while (it.hasNext()) {
			Worker w = it.next();
			Thread t = new Thread(w);
			t.start();
			w.resume();
			threads.add(t);
		}
		final Iterator<Thread> itt = threads.iterator();

		// In order to wait for the end of all worker work,
		// but without blocking the view interactivity,
		// we need to call the join method on all the worker thread
		// into another thread.
		(new Thread() {
			public void run() {
				while (itt.hasNext()) {
					try {
						itt.next().join();
					} catch (InterruptedException e) {
						e.printStackTrace();
						return;
					}
				}
				Statistics stats = Statistics.getInstance();
				try {
					stats.writeGlobalStatistics();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();

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
	public void pause() {
		Iterator<Worker> it = workers.iterator();
		while (it.hasNext()) {
			Worker w = it.next();
			w.suspend();
		}
	}

	public HashSet<Worker> getWorkers() {
		return workers;
	}

	@Override
	public void setWorkerTime(int time) {
		Iterator<Worker> it = workers.iterator();
		while(it.hasNext()){
			it.next().setTime(time);
		}		
	}

}
