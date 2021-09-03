package com.notaris.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

	public String path = "";
	public Boolean console = true;

	public void info(String message) {
		writeToFile(message, 0);
	}

	public void debug(String message) {
		writeToFile(message, 1);
	}

	public void error(String message) {
		writeToFile(message, 2);
	}

	private void writeToFile(String message, int type) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");

		String mode = "";
		if (type == 0) {
			mode = "INFO";
		} else if (type == 1) {
			mode = "DEBUG";
		} else if (type == 2) {
			mode = "ERROR";
		}

		try {

			String[] classInfo = getClassInfo();
			String log = sdf.format(new Date()) + " [" + classInfo[1] + "] " + mode + " (" + classInfo[0] + ".java:"
					+ classInfo[2] + ") - " + message;

			File f = new File(path);
			if (f.exists() && !f.isDirectory()) {
				BufferedWriter out = new BufferedWriter(new FileWriter(path, true));
				out.write(log + "\n");
				out.close();
				if (console) {
					System.out.println(log);
				}
			} else {
				if (!path.equals("")) {
					FileWriter myWriter = new FileWriter(path);
					myWriter.write(log + "\n");
					myWriter.close();
					if (console) {
						System.out.println(log);
					}
				} else {
					System.out.println("You must specify a path to write logs!");
				}
			}
		} catch (IOException e) {
			// e.printStackTrace();
			System.out.println(e.getMessage());
		}

	}

	private String[] getClassInfo() {
		StackTraceElement[] currTrace = Thread.currentThread().getStackTrace();
		StackTraceElement lineTrace = currTrace[4];

		/**
		 * Reverse String to find first occurrence of "." take substring and reverse
		 * back so you get the last part of the package i.e. the class name.
		 */
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
