/**
 * 
 */
package com;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author victorinox
 *
 */
public class Controller implements IListener {
	
	private View gui;
	private IModel model;
	
	
	/**
	 * @see com.IListener#updateFields()
	 */
	@Override
	public void updateFields() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setView(View window) {
		this.gui = window;
	}

	@Override
	public void setModel(IModel manager) {
		this.model = manager;
	}

	@Override
	public void runModel() throws IOException {
		this.model.run();
	}

	@Override
	public void stopModel() {
		this.model.stop();
	}

	@Override
	public void initialiseFields(OrderList allOrders,
			HashMap<Integer, IItem> allItems) {
		this.gui.initialiseOrdersBox(allOrders);
		this.gui.initialiseItemsBox(allItems);
	}

	@Override
	public void updateWorkerBox(Integer idWorker, String digest) {
		this.gui.updateWorkerBox(idWorker, digest);
	}

	@Override
	public void updateOrderBox(OrderList allOrders) {
		this.gui.updateOrdersBox(allOrders);
	}

	@Override
	public void updateWareHouseBox(HashMap<Integer, IItem> allItems) {
		this.gui.updateWareHouseBox(allItems);
	}

}
