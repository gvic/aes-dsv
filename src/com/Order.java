package com;
//outline of order calss
public class Order implements Comparable <Order>
{
	//will need more instance variables
	private String id;


    public Order(String id)
    {   
        this.id =id.trim();
    }
    
    /**
     * @return The id.
     */    
    public String getId() {
    	return id;
    }
    
 
    /**
     * Test for content equality between two objects.
     * @param other The object to compare to this one.
     * @return true if the argument object has same id
     */
    public boolean equals(Object other)
    {
        if(other instanceof Order) {
            Order otherItem = (Order) other;
            return id.equals(otherItem.getId());
        }
        else {
            return false;
        }
    }

    /**
     * Compare this object against another, for the purpose
     * of sorting. The fields are compared by id.
     * @param otherDetails The details to be compared against.
     * @return a negative integer if this id comes before the parameter's id,
     *         zero if they are equal and a positive integer if this
     *         comes after the other.
     */

    public int compareTo(Order otherDetails)
    {
        return id.compareTo(otherDetails.getId());
    }    

    /**
     * @return A  string containing all details.
     */
    public String toString()
    {
        return id;
    }

}
