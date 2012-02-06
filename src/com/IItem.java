package com;


/**
 * 
 * @author victorinox
 *	
 * Interface gathering common operations available on 
 * goods
 */
public interface IItem {

	/**
	 * Retrieves the ID of a goods
	 * @return int
	 */
	public int getId();
	
	/**
	 * Retrieves unit price of an item
	 * @return double
	 */
	public double getUnitPrice();
	
	/**
	 * Retrieves quantity of a goods
	 * @return int
	 */
	public int getQuantity();

	/**
	 * Setter of the item quantity
	 * @param i
	 */
	public void setQuantity(int i);
	
}
