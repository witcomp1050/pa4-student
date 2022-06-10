package edu.wit.cs.comp1050.tests;

public class TestSuite {
	static String stringOutput(String[] lines, Object[] values) {
		return String.format(String.join("", lines), values);
	}
	
	static String terminalOutput(String[] lines) {
		return String.join(String.format("%n"), lines);
	}
}
