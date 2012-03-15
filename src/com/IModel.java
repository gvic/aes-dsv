package com;

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
	void AddListener(IListener controller);
}
