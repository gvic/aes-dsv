package com;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashSet;


/**
 * Implements the Singleton pattern in order to gather the statistics
 * from all the workers.
 * @author victorinox
 *
 */
public class Statistics {

	private static Statistics instance = new Statistics();

	private double totalIncome;
	private int totalItemSold;
	private HashSet<String> customerSet;
	private String buffer;

	private Statistics() {
	}

	public void addToTotalIncome(double totalIncome) {
		this.totalIncome += totalIncome;
	}

	public void addToTotalItemSold(int totalItemSold) {
		this.totalItemSold += totalItemSold;
	}

	public void addToCustomerSet(String customerId) {
		this.customerSet.add(customerId);
	}

	public void appednToBuffer(String buffer) {
		this.buffer += buffer;
	}

	public static Statistics getInstance() {
		return instance;
	}

	public void initialiseFields() {
		totalIncome = 0;
		totalItemSold = 0;
		customerSet = new HashSet<String>();
		buffer = null;
	}

	public void writeGlobalStatistics() throws IOException {
		BufferedWriter writerOutput = null;
		FileWriter fstream = new FileWriter("global_statistics.txt");
		writerOutput = new BufferedWriter(fstream);
		// Overall summary stats
		DecimalFormat df = new DecimalFormat("#.###");
		writerOutput.write("\n\n\n\nSUMMARY\n\n");
		writerOutput.write("Total income: " + df.format(totalIncome) + "\n");
		writerOutput.write("Total item sold: " + totalItemSold + "\n");
		writerOutput.write("Total customer: " + customerSet.size() + "\n");
		writerOutput.close();
		fstream.close();
	}

}
