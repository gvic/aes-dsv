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
	 * @see com.IListener#UpdateFields()
	 */
	@Override
	public void UpdateFields() {
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
	public void pauseModel() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initialiseFields(OrderList allOrders,
			HashMap<Integer, IItem> allItems) {
		this.gui.initialiseOrdersBox(allOrders);
		this.gui.initialiseItemsBox(allItems);
	}

}
