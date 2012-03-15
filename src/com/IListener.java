package com;

import java.io.IOException;
import java.util.HashMap;

/**
 * This interface declares the methods needed by 
 * the Controller as an observer of the model 
 * @author victorinox
 * 
 */
public interface IListener {

	/**
	 * Method called from within the model to notify the controller
	 * (Controller) to update the view (View.cs) when 
	 */
	void UpdateFields();

	void setView(View window);

	void setModel(IModel manager);
	
	void runModel() throws IOException;

	void pauseModel();

	void initialiseFields(OrderList allOrders, HashMap<Integer, IItem> allItems);

}