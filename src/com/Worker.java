package com;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;

public class Worker {
	private OrderList allOrders;
	private HashMap<Integer, IItem> allItems;
	private BufferedWriter writerOutput;

	public Worker(OrderList allOrders, HashMap<Integer, IItem> allItems) {
		this.allItems = allItems;
		this.allOrders = allOrders;
		try {
			FileWriter fstream = new FileWriter("output.txt");
			writerOutput = new BufferedWriter(fstream);
		} catch (IOException e) {
			System.out
					.println("Error while trying to open the output.txt file.");
			e.printStackTrace();
		}
	}

	public void run() {
		System.out.println("Processing");

		try {
			while (allOrders.hasOrder()) {
				processOneOrder();
			}
			outputSummary();
			this.writerOutput.close();
		} catch (IOException e) {
			System.out.println("Error while handling the output.txt file");
			e.printStackTrace();
		}
	}

	private void outputSummary() {
		
		
	}

	public void processOneOrder() throws IOException {
		Order order = allOrders.getNextOrder();
		int itemId = order.getItemId();
		IItem book = allItems.get(itemId);

		String output = this.ouputOrder(order, book);
		if (book.getQuantity() >= order.getQuantity()) {
			book.setQuantity(book.getQuantity() - order.getQuantity());
		}

		this.writerOutput.write(output);
	}

	private String ouputOrder(Order o, IItem i) {
		DecimalFormat df = new DecimalFormat("#.###");
		double percent = o.getDiscountPercent();
		double fullPrice = o.getQuantity() * i.getUnitPrice();
		String cost = df.format(fullPrice - fullPrice * percent);
		String output = "ORDER ID : " + o.getId() + "      ITEM ID : "
				+ i.getId() + "      QUANTITY ORDERED: " + o.getQuantity()
				+ "\n";
		output += "CUSTOMER TYPE : "+ o.getCustomerType()+"\n";
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
	
	
}
