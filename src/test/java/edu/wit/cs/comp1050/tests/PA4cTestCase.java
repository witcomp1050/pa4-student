
package edu.wit.cs.comp1050.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.security.Permission;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.*;

import edu.wit.cs.comp1050.PA4c;

@Timeout(1)
public class PA4cTestCase {
	
	@SuppressWarnings("serial")
	private static class ExitException extends SecurityException {}
	
	private static class NoExitSecurityManager extends SecurityManager 
    {
        @Override
        public void checkPermission(Permission perm) {}
        
        @Override
        public void checkPermission(Permission perm, Object context) {}
        
        @Override
        public void checkExit(int status) { super.checkExit(status); throw new ExitException(); }
    }
	
	@BeforeEach
    public void setUp() throws Exception 
    {
        System.setSecurityManager(new NoExitSecurityManager());
    }
	
	@AfterEach
    public void tearDown() throws Exception 
    {
        System.setSecurityManager(null);
    }
	
	private void _test(String[] values, String msg) {
		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		
		final String input = String.join(" ", values);
		final String expected = TestSuite.stringOutput(new String[] {
			"Enter integers: " +
			msg + "%n"
		}, new Object[] {});
		
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		System.setOut(new PrintStream(outContent));
		try {
			PA4c.main(new String[] { "foo" });
		} catch (ExitException e) {}
		
		assertEquals(expected, outContent.toString());
		
		System.setIn(null);
		System.setOut(null);
	}
	
	private void _testMethod(ArrayList<Integer> list, Integer[] expected) {
		try {
			PA4c.removeRepeated(list);
		} catch (ExitException e) {}
		
		Integer[] a = new Integer[list.size()];
		list.toArray(a);
		
		assertArrayEquals(expected, a);
	}
	
	private void _testGood(String[] values, String result) {
		_test(values, String.format("The distinct integer(s): %s", result));
	}
	
	@Test
	public void testMethod() {
		_testMethod(new ArrayList<Integer>(), new Integer[] {});
		
		_testMethod(new ArrayList<Integer>(Arrays.asList(1)), new Integer[] {1});
		_testMethod(new ArrayList<Integer>(Arrays.asList(1, 2, 3)), new Integer[] {1, 2, 3});
		
		_testMethod(new ArrayList<Integer>(Arrays.asList(3, 2, 1, 1, 2, 3)), new Integer[] {3, 2, 1});
		_testMethod(new ArrayList<Integer>(Arrays.asList(2, 2, 1, 101, 2, 3)), new Integer[] {2, 1, 101, 3});
		_testMethod(new ArrayList<Integer>(Arrays.asList(
			212, 17, 1, 2, 1, 101, 2, 3,
			1, 1, 1, 2, 3, 2, 1, 3, 2, 100,
			1, 1, 1, 2, 3, 2, 1, 3, 2, 100,
			1, 1, 1, 2, 3, 2, 1, 3, 2, 100,
			1, 1, 1, 1, 2, 3, 4, 1, 3, 2, 4,
			1, 1, 1, 2, 3, 2, 1, 3, 2, 100,
			1, 1, 1, 2, 3, 2, 1, 3, 2, 100,
			1, 1, 1, 2, 3, 2, 1, 3, 2, 100,
			1, 1, 1, 2, 3, 2, 1, 3, 2, 100,
			1, 1, 1, 1, 2, 3, 4, 1, 3, 2, 4,
			1, 1, 1, 1, 2, 3, 4, 1, 3, 2, 4,
			1, 1, 1, 1, 2, 3, 4, 1, 3, 2, 4,
			1, 1, 1, 1, 2, 3, 4, 1, 3, 2, 4,
			1, 1, 1, 1, 2, 3, 4, 1, 3, 2, 4,
			1, 1, 1, 1, 2, 3, 4, 1, 3, 2, 4,
			212, 17, 1, 2, 1, 101, 2, 3,
			212, 17, 1, 2, 1, 101, 2, 3,
			212, 17, 1, 2, 1, 101, 2, 3,
			212, 17, 1, 2, 1, 101, 2, 3)), 
			new Integer[] {212, 17, 1, 2, 101, 3, 100, 4});
		
		_testMethod(new ArrayList<Integer>(Arrays.asList(
			8, 8, 8, 8, 8, 8, 8, 6, 6, 6, 6, 8, 8, 8, 8, 6, 
			6, 6, 7, 7, 7, 7, 7, 6, 6, 6, 8, 8, 8, 7, 7, 7, 
			5, 8, 8, 7, 6, 6, 6, 6, 7, 5, 5, 3, 3, 8, 7, 6, 
			5, 0, 0, 0, 9, 3, 0, 9, 8, 6, 7, 5)), 
			new Integer[] {8, 6, 7, 5, 3, 0, 9});
	}

    @Test
	public void testProgram() {
		_test(new String[] {}, "No values entered.");
		
		_testGood(new String[] {"1"}, "1");
		_testGood(new String[] {"1", "2", "3"}, "1 2 3");
		
		_testGood(new String[] {
			"1", "2", "3", "1", "1", "1", "1", "1", "1", "1", 
			"1", "1", "2", "2", "2", "2", "2", "2", "2", "2", 
			"3", "3", "3", "3", "3", "3", "3", "1", "1", "1", 
			"1", "1", "1", "1", "1", "1", "2", "2", "2", "2", 
			"2", "2", "2", "2", "3", "3", "3", "3", "3", "3", 
			"3", "1", "1", "1", "1", "1", "1", "1", "1", "1", 
			"2", "2", "2", "2", "2", "2", "2", "2", "3", "3", 
			"3", "3", "3", "3", "3", "1", "1", "1", "1", "1", 
			"1", "1", "1", "1", "2", "2", "2", "2", "2", "2", 
			"2", "2", "3", "3", "3", "3", "3", "3", "3", "1", 
			"1", "1", "1", "1", "1", "1", "1", "1", "2", "2", 
			"2", "2", "2", "2", "2", "2", "3", "3", "3", "3", 
			"3", "3", "3", "1", "1", "1", "1", "1", "1", "1", 
			"1", "1", "2", "2", "2", "2", "2", "2", "2", "2", 
			"3", "3", "3", "3", "3", "3", "3", "1", "1", "1", 
			"1", "1", "1", "1", "1", "1", "2", "2", "2", "2", 
			"2", "2", "2", "2", "3", "3", "3", "3", "3", "3", 
			"3"}, "1 2 3");
		
		_testGood(new String[] {
			"3", "2", "3", "1", "1", "1", "1", "1", "1", "1", 
			"1", "1", "2", "2", "2", "2", "2", "2", "2", "2", 
			"3", "3", "3", "3", "3", "3", "3", "1", "1", "1", 
			"1", "1", "1", "1", "1", "1", "2", "2", "2", "2", 
			"2", "2", "2", "2", "3", "3", "3", "3", "3", "3", 
			"3", "1", "1", "1", "1", "1", "1", "1", "1", "1", 
			"2", "2", "2", "2", "2", "2", "2", "2", "3", "3", 
			"3", "3", "3", "3", "3", "1", "1", "1", "1", "1", 
			"1", "1", "1", "1", "2", "2", "2", "2", "2", "2", 
			"2", "2", "3", "3", "3", "3", "3", "3", "3", "1", 
			"1", "1", "1", "1", "1", "1", "1", "1", "2", "2", 
			"2", "2", "2", "2", "2", "2", "3", "3", "3", "3", 
			"3", "3", "3", "1", "1", "1", "1", "1", "1", "1", 
			"1", "1", "2", "2", "2", "2", "2", "2", "2", "2", 
			"3", "3", "3", "3", "3", "3", "3", "1", "1", "1", 
			"1", "1", "1", "1", "1", "1", "2", "2", "2", "2", 
			"2", "2", "2", "2", "3", "3", "3", "3", "3", "3", 
			"3"}, "3 2 1");
		
		_testGood(new String[] {
			"2", "2", "3", "1", "1", "1", "1", "1", "1", "1", 
			"1", "1", "2", "2", "2", "2", "2", "2", "2", "2", 
			"3", "3", "3", "3", "3", "3", "3", "1", "1", "1", 
			"1", "1", "1", "1", "1", "1", "2", "2", "2", "2", 
			"200", "2", "2", "2", "3", "3", "3", "3", "3", "3", 
			"3", "1", "1", "1", "1", "1", "1", "1", "1", "1", 
			"2", "2", "2", "2", "2", "2", "2", "2", "3", "3", 
			"3", "3", "3", "3", "3", "1", "1", "1", "1", "1", 
			"1", "1", "1", "1", "2", "2", "2", "2", "2", "2", 
			"2", "2", "3", "3", "3", "3", "3", "3", "3", "1", 
			"1", "1", "1", "1", "1", "1", "1", "1", "2", "2", 
			"2", "2", "2", "2", "2", "2", "3", "3", "3", "3", 
			"3", "3", "3", "1", "101", "1", "1", "1", "1", "1",
			"200", "2", "2", "2", "3", "3", "3", "3", "3", "3", 
			"3", "1", "1", "1", "1", "1", "1", "1", "1", "1", 
			"2", "2", "2", "2", "2", "2", "2", "2", "3", "3", 
			"3", "3", "3", "3", "3", "1", "1", "1", "1", "1", 
			"1", "1", "1", "1", "22", "2", "2", "2", "2", "2", 
			"2", "2", "3", "3", "3", "3", "3", "3", "3", "1", 
			"1", "1", "1", "1", "1", "1", "1", "1", "2", "2", 
			"2", "2", "2", "2", "2", "2", "3", "3", "3", "3", 
			"3", "3", "3", "1", "101", "1", "1", "1", "1", "1",
			"1", "1", "2", "2", "2", "2", "2", "2", "2", "2", 
			"1", "1", "1", "1", "22", "2", "2", "2", "2", "2", 
			"2", "2", "3", "3", "3", "3", "3", "3", "3", "1", 
			"1", "1", "1", "1", "1", "1", "1", "1", "2", "2", 
			"2", "2", "2", "2", "2", "2", "3", "3", "3", "3", 
			"3", "3", "3", "1", "101", "1", "1", "1", "1", "1",
			"1", "1", "2", "2", "2", "2", "2", "2", "2", "2", 
			"1", "1", "1", "1", "22", "2", "2", "2", "2", "2", 
			"2", "2", "3", "3", "3", "3", "3", "3", "3", "1", 
			"1", "1", "1", "1", "1", "1", "1", "1", "2", "2", 
			"2", "2", "2", "2", "2", "2", "3", "3", "3", "3", 
			"3", "3", "3", "1", "101", "1", "1", "1", "1", "1",
			"1", "1", "2", "2", "2", "2", "2", "2", "2", "2", 
			"1", "1", "1", "1", "22", "2", "2", "2", "2", "2", 
			"2", "2", "3", "3", "3", "3", "3", "3", "3", "1", 
			"1", "1", "1", "1", "1", "1", "1", "1", "2", "2", 
			"2", "2", "2", "2", "2", "2", "3", "3", "3", "3", 
			"3", "3", "3", "1", "101", "1", "1", "1", "1", "1",
			"1", "1", "2", "2", "2", "2", "2", "2", "2", "2", 
			"1", "1", "1", "1", "22", "2", "2", "2", "2", "2", 
			"2", "2", "3", "3", "3", "3", "3", "3", "3", "1", 
			"1", "1", "1", "1", "1", "1", "1", "1", "2", "2", 
			"2", "2", "2", "2", "2", "2", "3", "3", "3", "3", 
			"3", "3", "3", "1", "101", "1", "1", "1", "1", "1",
			"1", "1", "2", "2", "2", "2", "2", "2", "2", "2", 
			"3", "3", "3", "3", "3", "3", "3", "1", "1", "1", 
			"1", "1", "1", "1", "1", "1", "2", "2", "2", "2", 
			"2", "2", "2", "2", "3", "3", "3", "3", "3", "3", 
			"3"}, "2 3 1 200 101 22");
		
		_testGood(new String[] {
			"8", "8", "8", "8", "8", "8", "8", "6", "6", "6", "6", "8", "8", "8", "8", "6", 
			"6", "6", "7", "7", "7", "7", "7", "6", "6", "6", "8", "8", "8", "7", "7", "7", 
			"5", "8", "8", "7", "6", "6", "6", "6", "7", "5", "5", "3", "3", "8", "7", "6", 
			"5", "0", "0", "0", "9", "3", "0", "9", "8", "6", "7", "5"}, 
			"8 6 7 5 3 0 9");
	}
	
}
