package com;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;

public class Worker implements Runnable {
	private OrderList allOrders;
	private HashMap<Integer, IItem> allItems;
	private BufferedWriter writerOutput;
	private double totalIncome;
	private int totalItemSold;
	private HashSet<String> customerSet;
	private Statistics stats;
	private IListener controller;
	private static int workerIdCount;
	private Integer id;
	private final Object lock = new Object();
	private volatile boolean suspend = false, stopped = false;
	private volatile int time;

	public Worker(OrderList allOrders, HashMap<Integer, IItem> allItems) {
		this.allItems = allItems;
		this.allOrders = allOrders;
		this.customerSet = new HashSet<String>();
		this.totalIncome = 0;
		this.totalItemSold = 0;
		this.id = new Integer(workerIdCount++);
		// Singleton instantiation.
		stats = Statistics.getInstance();
		time = 1000;
	}

	public int getId() {
		return id;
	}

	public void setController(IListener controller) {
		this.controller = controller;
	}

	public void run() {
		try {
			FileWriter fstream = new FileWriter("worker-output" + id + ".txt");
			writerOutput = new BufferedWriter(fstream);
			while (!stopped && allOrders.hasOrder()) {
				while (!suspend && allOrders.hasOrder()) {
					processOneOrder();
					Thread.sleep(time);
				}
			}
			outputSummary();
			this.writerOutput.close();
		} catch (IOException e) {
			System.out
					.println("Error while trying to open the output file.");
			e.printStackTrace();
			System.exit(0);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return;
		}

	}

	public void suspend() {
		suspend = true;
	}

	public void stop() {
		suspend = true;
		stopped = true;
		synchronized (lock) {
			lock.notifyAll();
		}
	}

	public void resume() {
		suspend = false;
		synchronized (lock) {
			lock.notifyAll();
		}
	}
	
	public void setTime(int value){
		time = value;
	}

	public void processOneOrder() throws IOException {
		Order order = allOrders.getNextOrder();
		if (!order.isProcessed()) {
			int itemId = order.getItemId();
			IItem item = allItems.get(itemId);
			System.out.println(order);

			String output = this.ouputOrder(order, item);
			stats.appednToBuffer(output);
			if (item.getQuantity() >= order.getQuantity()) {
				item.setQuantity(item.getQuantity() - order.getQuantity());
				allItems.put(itemId, item);
				this.totalItemSold += order.getQuantity();
				stats.addToTotalItemSold(order.getQuantity());
			}
			this.customerSet.add(order.getCustomerId());
			stats.addToCustomerSet(order.getCustomerId());
			this.controller.updateWorkerBox(id, digest(order, item));
			this.controller.updateOrderBox(allOrders);
			this.controller.updateWareHouseBox(allItems);
			this.writerOutput.write(output);
		}
	}

	private String digest(Order order, IItem item) {
		DecimalFormat df = new DecimalFormat("#.###");
		double percent = order.getDiscountPercent();
		double fullPrice = order.getQuantity() * item.getUnitPrice();
		String cost = df.format(fullPrice - fullPrice * percent);
		String str = "<html>";
		str += "Order " + order.getId() + " Customer " + order.getCustomerId()
				+ "<br>";
		str += order.getQuantity() + " * " + item + " = " + cost + "<br>";
		str += "Successfully processed";
		return str;

	}

	private String ouputOrder(Order o, IItem i) {
		DecimalFormat df = new DecimalFormat("#.###");
		double percent = o.getDiscountPercent();
		double fullPrice = o.getQuantity() * i.getUnitPrice();
		String cost = df.format(fullPrice - fullPrice * percent);
		String output = "ORDER ID : " + o.getId() + "      ITEM ID : "
				+ i.getId() + "      QUANTITY ORDERED: " + o.getQuantity()
				+ "\n";
		output += "CUSTOMER TYPE : " + o.getCustomerType() + "\n";
		output += "ORIGINAL COST : " + df.format(fullPrice) + "\n";
		output += "DISCOUNT COST : " + cost + "\n";

		if (i.getQuantity() < o.getQuantity()) {
			output += "PROCESS STATUS : Unprocessed \n";
			output += "CAUSE : Stock running low\n";

		} else {
			this.totalIncome += Double.parseDouble(cost);
			stats.addToTotalIncome(Double.parseDouble(cost));
			output += "PROCESS STATUS : Processed \n";
		}

		output += "---------------------------------------\n\n";
		return output;
	}

	private void outputSummary() throws IOException {
		DecimalFormat df = new DecimalFormat("#.###");
		writerOutput.write("\n\n\n\nSUMMARY\n\n");
		writerOutput.write("Total income: " + df.format(totalIncome) + "\n");
		writerOutput.write("Total item sold: " + totalItemSold + "\n");
		writerOutput.write("Total customer: " + customerSet.size() + "\n");
	}
}
