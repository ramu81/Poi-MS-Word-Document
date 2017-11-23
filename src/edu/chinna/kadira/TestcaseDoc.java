package edu.chinna.kadira;

import java.io.Serializable;

public class TestcaseDoc implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9076646725777746435L;

	private String scenarioId = null;

	private String parentTestCaseId = null;

	private String testScriptHeadLine = null;

	private String testScenario = null;

	private String outputResults = null;

	private double workRequestNum = 0;

	public String getScenarioId() {
		return scenarioId;
	}

	public void setScenarioId(String scenarioId) {
		this.scenarioId = scenarioId;
	}

	public String getParentTestCaseId() {
		return parentTestCaseId;
	}

	public void setParentTestCaseId(String parentTestCaseId) {
		this.parentTestCaseId = parentTestCaseId;
	}

	public String getTestScriptHeadLine() {
		return testScriptHeadLine;
	}

	public void setTestScriptHeadLine(String testScriptHeadLine) {
		this.testScriptHeadLine = testScriptHeadLine;
	}

	public String getTestScenario() {
		return testScenario;
	}

	public void setTestScenario(String testScenario) {
		this.testScenario = testScenario;
	}

	public String getOutputResults() {
		return outputResults;
	}

	public void setOutputResults(String outputResults) {
		this.outputResults = outputResults;
	}

	public double getWorkRequestNum() {
		return workRequestNum;
	}

	public void setWorkRequestNum(double workRequestNum) {
		this.workRequestNum = workRequestNum;
	}

	@Override
	public String toString() {
		return "TestcaseDoc [scenarioId=" + scenarioId + ", parentTestCaseId=" + parentTestCaseId
				+ ", testScriptHeadLine=" + testScriptHeadLine + ", testScenario=" + testScenario + ", outputResults="
				+ outputResults + ", workRequestNum=" + workRequestNum + "]";
	}

}
