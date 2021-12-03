package com.notaris.logger;

public class Utils {

	/**
	 * Reverse String to find first occurrence of "." take substring and reverse
	 * back so you get the last part of the package i.e. the class name.
	 * 
	 * @return
	 */
	public static String[] getClassInfo() {
		StackTraceElement[] currTrace = Thread.currentThread().getStackTrace();
		StackTraceElement lineTrace = currTrace[4];

		int pos = reverseString(lineTrace.getClassName()).indexOf(".");
		String className = reverseString(reverseString(lineTrace.getClassName()).substring(0, pos));

		String methodName = lineTrace.getMethodName();
		int lineNumber = lineTrace.getLineNumber();
		String[] values = { className, methodName, Integer.toString(lineNumber) };
		return values;
	}

	private static String reverseString(String str) {
		StringBuilder sb = new StringBuilder(str);
		sb.reverse();
		return sb.toString();
	}
}
