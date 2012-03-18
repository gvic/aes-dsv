package com;

import java.io.IOException;
import java.util.HashSet;

/**
 * MVC's Model interface
 * 
 * @author victorinox
 * 
 */
public interface IModel {

	/**
	 * @param IListener: The controller which wants 
	 * to listen to the model
	 */
	void setController(IListener controller);
	
	public int getMaximumWorkers();

	public void setMaximumWorkers(int maximumWorkers);
	
	void run() throws IOException;
	
	public void initialise() throws IOException;

	void pause();
	
	public HashSet<Worker> getWorkers();

	public void setWorkerTime(int time);
}
