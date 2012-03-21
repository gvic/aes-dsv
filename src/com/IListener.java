package com;

import java.util.HashMap;

/**
 * This interface declares the methods needed by 
 * the Controller as an observer of the model 
 * 
 */
public interface IListener {

	/**
	 * Method called from within the model to notify the controller
	 * (Controller) to update the view (View.cs) when 
	 */
	void updateFields();

	void setView(View window);

	void setModel(IModel manager);
	
	void runModel();

	void pauseModel();

	void initialiseFields(OrderList allOrders, HashMap<Integer, IItem> allItems);

	void updateWorkerBox(Integer idWorker, String digest);

	void updateOrderBox(OrderList allOrders);

	void updateWareHouseBox(HashMap<Integer, IItem> allItems);

	void runWorker(int i);

	void pauseWorker(int i);

	void setWorkerTime(int time);

	void resumeModel();

}