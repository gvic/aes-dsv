package com;

import java.io.IOException;

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
}
