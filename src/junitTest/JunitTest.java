package junitTest;

import java.lang.reflect.Array;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import com.*;

public class JunitTest  extends TestCase{
	private static String sTest = "1; VIP-007;503;2";
	private static String[] dTest1 = sTest.split(";");
	private static String [] dTest2 = "5; VIP-002;401;150".split(";");
	private static final String itemPath = "";
	private static final String orderPath = "";
	public JunitTest (String name) 
	{
		super (name);
	}
	
	public static void main(String[] args) 
	{
		TestRunner.runAndWait(new TestSuite(JunitTest.class));


	}

	/* JUnit Test for Discount Calculation
	 * VIP Customer & qty = 150*/
		public void testDiscountPercent150() throws Exception 
	{
		Order oTest = new Order(dTest2);
	    assertEquals(0.2, oTest.getDiscountPercent());
	}

	/* JUnit Test for Parsing Orders Id (constructor)
	 * data = {1; VIP-007;503;2}*/
		public void testConstructParseId() throws Exception 
	{
		Order oTest = new Order(dTest1);
		System.err.println(Array.get(oTest.parseInfo(dTest1), 0));
	    assertEquals("1", Array.get(oTest.parseInfo(dTest1), 0));
	}			
	/* JUnit Test for Parsing Customer Id (constructor)
	 * data = {1; VIP-007;503;2}*/
		public void testConstructParseCustId() throws Exception 
	{
		Order oTest = new Order(dTest1);
	    assertEquals("VIP-007", Array.get(oTest.parseInfo(dTest1), 1));
	}	
	/* JUnit Test for Parsing Item Id (constructor)
	 * data = {1; VIP-007;503;2}*/
		public void testConstructParseItemId() throws Exception 
	{
		Order oTest = new Order(dTest1);
	    assertEquals("503", Array.get(oTest.parseInfo(dTest1), 2));
	}	
	/* JUnit Test for Parsing Quantity (constructor)
	 * data = {1; VIP-007;503;2}*/
		public void testConstructParseQuantity() throws Exception 
	{
		Order oTest = new Order(dTest1);
	    assertEquals("2", Array.get(oTest.parseInfo(dTest1), 3));
	}
}
