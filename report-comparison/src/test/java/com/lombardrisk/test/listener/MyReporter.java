package com.lombardrisk.test.listener;

import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.xml.XmlSuite;

import java.util.List;

public class MyReporter implements IReporter{

	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,
			String outputDirectory) {
		outputDirectory = "D:/CollineReport/";
		
	}



}
