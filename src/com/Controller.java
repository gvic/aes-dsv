/**
 * 
 */
package com;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

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
	public void runModel() {
		try {
			this.model.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void pauseModel() {
		this.model.pause();
	}

	@Override
	public void initialiseFields(OrderList allOrders,
			HashMap<Integer, IItem> allItems) {
		this.gui.initialiseOrdersBox(allOrders);
		this.updateWareHouseBox(allItems);
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
		Iterator<IItem> it = allItems.values().iterator();
		String output = "<html>";
		while (it.hasNext()) {
			output += it.next().toString()+"<br>";
		}
		output += "</html>";
		this.gui.updateWareHouseBox(output);
	}

	@Override
	public void runWorker(int i) {
		HashSet<Worker> workers = this.model.getWorkers();
		Iterator<Worker> it = workers.iterator();
		boolean found = false;
		int id = -1;
		Worker w = null;
		while (it.hasNext() && !found) {
			w = it.next();
			found = (w.getId() == i);
		}
		if (found) {
			w.resume();
		}
	}

	@Override
	public void pauseWorker(int i) {
		HashSet<Worker> workers = this.model.getWorkers();
		Iterator<Worker> it = workers.iterator();
		boolean found = false;
		int id = -1;
		Worker w = null;
		while (it.hasNext() && !found) {
			w = it.next();
			found = (w.getId() == i);
		}
		if (found) {
			w.suspend();
		}
	}

	@Override
	public void setWorkerTime(int time) {
		this.model.setWorkerTime(time);
	}

	@Override
	public void resumeModel() {
		HashSet<Worker> workers = this.model.getWorkers();
		Iterator<Worker> it = workers.iterator();
		while(it.hasNext()){
			it.next().resume();
		}
	}

}
