package edu.chinna.kadira;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CreateTestCaseTemplet {
	public static void main(String[] args) {
		Workbook workbook = null;
		try {
			Properties prop = new Properties();
			Map<String, FileObject> fileMap = new HashMap<>();
			prop.load(new FileInputStream(TestCaseConstants.PROPERTIES_FILE));
			TestCaseDocument.populateFileMap(fileMap, prop.getProperty(TestcaseDocEnum.IMAGES_FOLDER.name()));
			workbook = new XSSFWorkbook(new FileInputStream(new File(TestCaseConstants.EXCEL_FILE_NAME)));
			Sheet datatypeSheet = workbook.getSheet(TestCaseConstants.TEST_SCENARIOS);
			if (Objects.nonNull(datatypeSheet)) {
				for (Row row : datatypeSheet) {
					TestcaseDoc testCaseDoc = getTestcaseDoc(row);
					if (Objects.nonNull(testCaseDoc)) {
						FileObject fileObject = fileMap.get(testCaseDoc.getScenarioId());
						if (Objects.nonNull(fileObject))
							TestCaseDocument.createTestcaseDoc(prop, fileObject, testCaseDoc);
					}
				}
			}

			if (Objects.nonNull(workbook))
				workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param row
	 * @return
	 */
	static TestcaseDoc getTestcaseDoc(Row row) {
		TestcaseDoc doc = null;
		if (Objects.nonNull(row)) {
			if (!TestCaseConstants.SCENARIO_ID.equals(row.getCell(0).getStringCellValue())) {
				doc = new TestcaseDoc();
				doc.setScenarioId(row.getCell(0).getStringCellValue());
				doc.setParentTestCaseId(row.getCell(1).getStringCellValue());
				doc.setTestScriptHeadLine(row.getCell(2).getStringCellValue());
				doc.setTestScenario(row.getCell(3).getStringCellValue());
				doc.setOutputResults(row.getCell(4).getStringCellValue());
				doc.setWorkRequestNum(row.getCell(5).getNumericCellValue());
			}
		}
		return doc;
	}
}
