package junitTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import com.*;

public class JunitTest extends TestCase {
	private static String sTest = "1; VIP-007;503;2";
	private static String[] dTest1 = sTest.split(";");
	private static String[] dTest2 = "5; VIP-002;401;150".split(";");
	private static final String itemPath = "/home/victorinox/workspace_java/aes-dsv/bin/Items.txt";
	private static final String orderPath = "/home/victorinox/workspace_java/aes-dsv/bin/Orders.txt";
	private static final String outputPath = "/home/victorinox/workspace_java/aes-dsv/bin/output.txt";
	// private static final String itemPath =
	// "C:/Users/hykth/workspace_galileo/advsoftF/src/Items.txt";
	// private static final String orderPath =
	// "C:/Users/hykth/workspace_galileo/advsoftF/src/Orders.txt";
	// private static final String outputPath =
	// "C:/Users/hykth/workspace_galileo/advsoftF/output.txt";
	private File fItem = new File(itemPath);
	private File fOrder = new File(orderPath);

	public JunitTest(String name) {
		super(name);
	}

	public static void main(String[] args) {
		TestRunner.runAndWait(new TestSuite(JunitTest.class));
	}

	/*
	 * JUnit Test for Discount Calculation VIP Customer & qty = 150
	 */
	public void testDiscountPercent150() throws Exception {
		Order oTest = new Order(dTest2);
		assertEquals(0.2, oTest.getDiscountPercent());
	}

	/*
	 * JUnit Test for Parsing Orders Id (constructor) data = {1; VIP-007;503;2}
	 */
	public void testConstructParseId() throws Exception {
		Order oTest = new Order(dTest1);
		System.err.println(Array.get(oTest.parseInfo(dTest1), 0));
		assertEquals("1", Array.get(oTest.parseInfo(dTest1), 0));
	}

	/*
	 * JUnit Test for Parsing Customer Id (constructor) data = {1;
	 * VIP-007;503;2}
	 */
	public void testConstructParseCustId() throws Exception {
		Order oTest = new Order(dTest1);
		assertEquals("VIP-007", Array.get(oTest.parseInfo(dTest1), 1));
	}

	/*
	 * JUnit Test for Parsing Item Id (constructor) data = {1; VIP-007;503;2}
	 */
	public void testConstructParseItemId() throws Exception {
		Order oTest = new Order(dTest1);
		assertEquals("503", Array.get(oTest.parseInfo(dTest1), 2));
	}

	/*
	 * JUnit Test for Parsing Quantity (constructor) data = {1; VIP-007;503;2}
	 */
	public void testConstructParseQuantity() throws Exception {
		Order oTest = new Order(dTest1);
		assertEquals("2", Array.get(oTest.parseInfo(dTest1), 3));
	}

	/*
	 * JUnit Test to know if the output exists and if the content is empty ->
	 * Delete the existing output file -> Run the application -> Test the new
	 * file
	 */
	public void testOutputExists() throws IOException {
		File fToDelete = new File(outputPath);
		if (fToDelete.exists()) {
			fToDelete.delete();
		}
		Manager m = new Manager(fItem, fOrder);
		m.run();
		assertTrue("Output file found", readFile(outputPath));
	}

	// Aux Function for reading in a file
	private static Boolean readFile(String path) {
		FileInputStream stream = null;
		boolean ret = true;
		try {
			stream = new FileInputStream(new File(path));
			FileChannel fc = stream.getChannel();
			MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0,
					fc.size());
			if (Charset.defaultCharset().decode(bb).toString().equals(""))
				ret = false;

			stream.close();
		} catch (IOException e) {

		}
		return ret;
	}

}
