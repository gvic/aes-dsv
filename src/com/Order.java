package com;

//outline of order calss
public class Order implements Comparable<Order> {
	// will need more instance variables
	private Integer id;
	/**
	 * Customer ID conventions: KEY-NUM: first third letter have a special
	 * meaning, and NUM stands numeric values. STD-XXX: standard customer If the
	 * quantity ordered is greater than 49, a discount of 10% is made VIP-XXX:
	 * special customer 10% off and 5% off for each range of 50 items ordered
	 * (after the first range of 50)
	 */
	private String customerId;
	private int itemId;
	private int quantity;

	public Order(String[] datas) throws Exception {
		if (datas.length != 4) {
			throw new Exception("There is a syntax error in the order file");
		}
		String[] ret = new String[3];
//		try {
//			id = Integer.parseInt(datas[0].trim());
//		} catch (NumberFormatException e) {
//			Exception ee = new Exception(
//					"The order id "+datas[0]+" is wrong. It won't be processed");
//			throw ee;
//		}
//		customerId = datas[1].trim();
//		if (!getCustomerType().equals("STD") && !getCustomerType().equals("VIP")) {
//			throw new Exception("The customer type "+getCustomerType()+" for the order ID " + id
//					+ " is unknown");
//		}
//		String[] a = customerId.split("-");
//
//		try {
//			Integer.parseInt(a[1]);
//		} catch (NumberFormatException e) {
//			Exception ee = new Exception("A customer id format is wrong. It won't be processed");
//			throw ee;
//		}
//
//		try {
//			itemId = Integer.parseInt(datas[2].trim());
//			if(itemId > 999 || itemId < 300){
//				throw new Exception("The order id "+id+" has its item id wrong. It won't be processed");
//			}
//		} catch (NumberFormatException e) {
//			Exception ee = new Exception("The item id "+datas[2]+" of the order id "+id+" is not integer. It won't be processed");
//			throw ee;
//		}
//		try {
//			quantity = Integer.parseInt(datas[3].trim());
//			if(quantity <= 0){
//				throw new Exception("The item quantity "+datas[3]+" for the order ID "+id+" is not valid. It won't be processed");
//			}
//		} catch (NumberFormatException e) {
//			Exception ee = new Exception("The item quantity "+datas[3]+" for the order id "+id+" is not integer. It won't be processed");
//			throw ee;
//		}
		ret = parseInfo(datas);
		id = Integer.parseInt(ret[0]);
		customerId = ret[1];
		itemId = Integer.parseInt(ret[2]);
		quantity = Integer.parseInt(ret[3]);
	}

	public Order(String id) {
		this.id = Integer.parseInt(id.trim());
	}
	
	public String[] parseInfo(String[] datas) throws Exception{
		String[] dataRet = new String[4];
		int tempRes;
		try {
			tempRes = Integer.parseInt(datas[0].trim());
			dataRet[0] = datas[0].trim();
		} catch (NumberFormatException e) {
			Exception ee = new Exception(
					"The order id "+datas[0]+" is wrong. It won't be processed");
			throw ee;
		}
		dataRet[1] = datas[1].trim();
		if (!getCustomerTypewithArg(dataRet[1].toString()).equals("STD") && !getCustomerTypewithArg(dataRet[1].toString()).equals("VIP")) {
			throw new Exception("The customer type "+getCustomerTypewithArg(dataRet[1].toString())+" for the order ID " + dataRet[0].toString()
					+ " is unknown");
		}
		String[] a = dataRet[1].toString().split("-");
		try {
			Integer.parseInt(a[1]);
		} catch (NumberFormatException e) {
			Exception ee = new Exception("A customer id format is wrong. It won't be processed");
			throw ee;
		}

		try {
			tempRes  = Integer.parseInt(datas[2].trim());
			dataRet[2] = datas[2].trim();
			if(tempRes > 999 || tempRes < 300){
				throw new Exception("The order id "+dataRet[0].toString()+" has its item id wrong. It won't be processed");
			}
		} catch (NumberFormatException e) {
			Exception ee = new Exception("The item id "+datas[2]+" of the order id "+dataRet[0].toString()+" is not integer. It won't be processed");
			throw ee;
		}
		try {
			tempRes = Integer.parseInt(datas[3].trim());
			dataRet[3] = datas[3].trim();
			if(tempRes <= 0){
				throw new Exception("The item quantity "+datas[3]+" for the order ID "+dataRet[0].toString()+" is not valid. It won't be processed");
			}
		} catch (NumberFormatException e) {
			Exception ee = new Exception("The item quantity "+datas[3]+" for the order id "+dataRet[0].toString()+" is not integer. It won't be processed");
			throw ee;
		}
		
		return dataRet;
	}

	/**
	 * @return The id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Test for content equality between two objects.
	 * 
	 * @param other
	 *            The object to compare to this one.
	 * @return true if the argument object has same id
	 */
	public boolean equals(Object other) {
		if (other instanceof Order) {
			Order otherItem = (Order) other;
			return id.equals(otherItem.getId());
		} else {
			return false;
		}
	}

	/**
	 * Compare this object against another, for the purpose of sorting. The
	 * fields are compared by id.
	 * 
	 * @param otherDetails
	 *            The details to be compared against.
	 * @return a negative integer if this id comes before the parameter's id,
	 *         zero if they are equal and a positive integer if this comes after
	 *         the other.
	 */

	public int compareTo(Order otherDetails) {
		return id.compareTo(otherDetails.getId());
	}

	public String getCustomerId() {
		return customerId;
	}

	public int getItemId() {
		return itemId;
	}

	public int getQuantity() {
		return quantity;
	}

	public double getDiscountPercent() {
		String key = this.getCustomerType();
		double discount = 0;
		if (key.equals("STD") && quantity >= 50)
			discount = 10;
		if (key.equals("VIP")) {
			discount = 10;
			int n = (int) (Math.floor(quantity / 50) - 1);
			discount += n * 5;
		}

		return discount / 100;
	}

	public String getCustomerType() {
		return customerId.substring(0, 3);
	}
	
	public String getCustomerTypewithArg(String s) {
		return s.substring(0, 3);
	}

	/**
	 * @return A string containing all details.
	 */
	public String toString() {
		String ret = "Order ID..... " + id + "\n";
		ret += "Customer ID.. " + customerId + "\n";
		ret += "Item ID...... " + itemId + "\n";
		ret += "Quantity..... " + quantity + "\n";
		return ret;
	}

}
