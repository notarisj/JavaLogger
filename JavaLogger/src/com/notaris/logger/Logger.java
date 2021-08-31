package com.notaris.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

// ---------- USAGE ----------
// Logger myLogger = new Logger();
// myLogger.path = "C:\\Users\\username\\Desktop\\test.log";
// myLogger.console = true;
// myLogger.info("test 123 info log");
// myLogger.debug("test 123 debug log");
// myLogger.error("test 123 error log");

public class Logger {

	public String path = "";
	public Boolean console = true;

	public void info(String message) {
		writeToFile(" " + message, 0);
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

			File f = new File(path);
			if (f.exists() && !f.isDirectory()) {
				BufferedWriter out = new BufferedWriter(new FileWriter(path, true));
				out.write(sdf.format(new Date()) + " [" + classInfo[1] + "] " + mode + " (" + classInfo[0] + ".java:"
						+ classInfo[2] + ") - " + message + "\n");
				out.close();
				if (console) {
					System.out.println(sdf.format(new Date()) + " [" + classInfo[1] + "] " + mode + " (" + classInfo[0]
							+ ".java:" + classInfo[2] + ") - " + message);
				}
			} else {
				FileWriter myWriter = new FileWriter(path);
				myWriter.write(sdf.format(new Date()) + " [" + classInfo[1] + "] " + mode + " (" + classInfo[0]
						+ ".java:" + classInfo[2] + ") - " + message + "\n");
				myWriter.close();
				if (console) {
					System.out.println(sdf.format(new Date()) + " [" + classInfo[1] + "] " + mode + " (" + classInfo[0]
							+ ".java:" + classInfo[2] + ") - " + message);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private String[] getClassInfo() {
		StackTraceElement[] currTrace = Thread.currentThread().getStackTrace();
		StackTraceElement lineTrace = currTrace[4];

		// Reverse String to find first ocurance of "." take subsstring and reverse back
		// so you dont get the full package
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
