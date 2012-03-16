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
	private IListener controller;
	private static int workerIdCount;
	private Integer id;

	public Worker(OrderList allOrders, HashMap<Integer, IItem> allItems) {
		this.allItems = allItems;
		this.allOrders = allOrders;
		this.customerSet = new HashSet<String>();
		this.totalIncome = 0;
		this.totalItemSold = 0;
		this.id = new Integer(workerIdCount++);
		try {
			FileWriter fstream = new FileWriter("output.txt");
			writerOutput = new BufferedWriter(fstream);
		} catch (IOException e) {
			System.out
					.println("Error while trying to open the output.txt file.");
			e.printStackTrace();
			System.exit(0);
		}
	}

	public int getId() {
		return id;
	}

	public void setController(IListener controller) {
		this.controller = controller;
	}

	public void run() {
		try {
			while (allOrders.hasOrder()) {
				processOneOrder();
			}
			outputSummary();
			this.writerOutput.close();

		} catch (IOException e) {
			System.out.println("Error while handling the output.txt file");
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void processOneOrder() throws IOException, InterruptedException {
		Order order = allOrders.getNextOrder();
		if (!order.isProcessed()) {
			int itemId = order.getItemId();
			IItem item = allItems.get(itemId);
			System.out.println(order);

			String output = this.ouputOrder(order, item);
			if (item.getQuantity() >= order.getQuantity()) {
				item.setQuantity(item.getQuantity() - order.getQuantity());
				allItems.put(itemId, item);
				this.totalItemSold += order.getQuantity();
			}
			this.customerSet.add(order.getCustomerId());
			this.controller.updateWorkerBox(id, digest(order, item));
			this.controller.updateOrderBox(allOrders);
			this.controller.updateWareHouseBox(allItems);
			double r = Math.random() + 1;
			int time = (int) (1000 * r);
			Thread.sleep(time);
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
		this.totalIncome += Double.parseDouble(cost);
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
