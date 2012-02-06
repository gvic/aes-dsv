package com;

//outline of order calss
public class Order implements Comparable<Order> {
	// will need more instance variables
	private String id;
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

	public Order(String[] datas) {
		id = datas[0].trim();
		customerId = datas[1].trim();
		itemId = Integer.parseInt(datas[2].trim());
		quantity = Integer.parseInt(datas[3].trim());
	}

	public Order(String id) {
		this.id = id.trim();
	}

	/**
	 * @return The id.
	 */
	public String getId() {
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

	public int getDiscountPercent() {
		String key = customerId.substring(0, 2);
		int discount = 0;
		if (key == "STD" && quantity >= 50)
			discount = 10;
		if (key == "VIP") {
			discount = 10;
			int n = (int) (Math.floor(quantity / 50) - 1);
			discount += n * 5;
		}
		return discount;
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
