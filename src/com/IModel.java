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
	
	void run() throws IOException;
	
	public void initialise() throws IOException;
}
