package edu.chinna.kadira;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.util.Units;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class TestCaseDocument {

	/**
	 * 
	 * @param prop
	 * @param fileMap
	 */
	static void createTestcaseDoc(Properties prop, FileObject fileObject, TestcaseDoc testCaseDoc) {
		try {
			XWPFDocument doc = new XWPFDocument();
			XWPFHeader head = doc.createHeader(HeaderFooterType.DEFAULT);
			head.createParagraph().createRun().setText(prop.getProperty(TestcaseDocEnum.QA_TESTING.name()));
			XWPFParagraph paragraph = doc.createParagraph();
			paragraph.setAlignment(ParagraphAlignment.LEFT);
			String font = prop.getProperty(TestcaseDocEnum.TEST_CASE_FONT.name());
			XWPFRun run = paragraph.createRun();
			run.setText(prop.getProperty(TestcaseDocEnum.QA_TESTING_RESULTS.name()));
			run.setBold(true);
			run.setFontFamily(font);
			run.setFontSize(10);

			run = paragraph.createRun();
			run.setText(prop.getProperty(TestcaseDocEnum.TEST_CASE_SC.name()));
			run.setFontFamily(font);
			run.setFontSize(10);

			run = paragraph.createRun();
			run.setText("    " + testCaseDoc.getParentTestCaseId());
			run.setFontFamily(font);
			run.setFontSize(10);
			run.setBold(true);

			run = paragraph.createRun();
			addTab(run);
			run.setText(prop.getProperty(TestcaseDocEnum.TEST_CASE_STATUS.name()));
			run.setText(fileObject.getFileStatus());
			run.setText(prop.getProperty(TestcaseDocEnum.TEST_CASE_ON.name()));
			if (null != fileObject.getFileList() && null != fileObject.getFileList().get(0)) {
				File file = fileObject.getFileList().get(0);
				BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
				Date creationDate = new Date(attr.creationTime().to(TimeUnit.MILLISECONDS));
				run.setText(" "+DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(creationDate));
			}
			run.setFontFamily(font);
			run.setFontSize(10);
			addparagraph(doc.createParagraph(), prop.getProperty(TestcaseDocEnum.SUMMARY_OF_CHANGE.name()), font, true);
			addparagraph(doc.createParagraph(), testCaseDoc.getTestScriptHeadLine(), font, false);
			addparagraph(doc.createParagraph(), prop.getProperty(TestcaseDocEnum.STEPS_EXECUTED.name()), font, true);
			addparagraph(doc.createParagraph(), testCaseDoc.getTestScenario(), font, false);
			addparagraph(doc.createParagraph(), prop.getProperty(TestcaseDocEnum.TEST_CASE_RESULTS.name()), font, true);
			addparagraph(doc.createParagraph(), testCaseDoc.getOutputResults(), font, false);
			addparagraph(doc.createParagraph(), prop.getProperty(TestcaseDocEnum.TEST_CASE_CASE_NUM.name()), font,
					true);
			addparagraph(doc.createParagraph(), testCaseDoc.getParentTestCaseId(), font, false);
			addparagraph(doc.createParagraph(), prop.getProperty(TestcaseDocEnum.TEST_CASE_LINE_SPEC.name()), font,
					true);
			addparagraph(doc.createParagraph(), prop.getProperty(TestcaseDocEnum.TEST_CASE_DOCUMENTATION.name()), font,
					true);
			addImage(doc.createParagraph(), fileObject);
			XWPFFooter footer = doc.createFooter(HeaderFooterType.DEFAULT);
			paragraph = footer.getParagraphArray(0);
			if (Objects.isNull(paragraph))
				paragraph = footer.createParagraph();
			paragraph.setAlignment(ParagraphAlignment.LEFT);

			run = paragraph.createRun();
			run.setText(prop.getProperty(TestcaseDocEnum.TEST_CASE_QA_DETAILS.name()));
			addTabs(run);
			run = paragraph.createRun();
			run.setText(prop.getProperty(TestcaseDocEnum.TEST_CASE_PAGE.name())
					+ prop.getProperty(TestcaseDocEnum.TEST_CASE_BLANK.name()));
			paragraph.getCTP().addNewFldSimple().setInstr(prop.getProperty(TestcaseDocEnum.TEST_CASE_CURR_PAGE.name()));
			run = paragraph.createRun();
			run.setText(" " + prop.getProperty(TestcaseDocEnum.TEST_CASE_OF.name()));
			paragraph.getCTP().addNewFldSimple().setInstr(prop.getProperty(TestcaseDocEnum.TEST_CASE_NUMPAGES.name()));
			FileOutputStream out = new FileOutputStream(
					TestCaseConstants.OUTPUT_FOLDER + fileObject.getFileName() + TestCaseConstants.FILE_EXT);
			doc.write(out);
			out.close();
			doc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param map
	 * @param fileName
	 */
	static void populateFileMap(Map<String, FileObject> map, String fileName) {
		File imagesDir = new File(fileName);
		for (File file : imagesDir.listFiles()) {
			String[] fileNames = file.getName().split("-");
			if (fileNames.length > 1) {
				String tempFileName = fileNames[0].trim();
				if (map.containsKey(tempFileName)) {
					FileObject fileObject = map.get(tempFileName);
					fileObject.getFileList().add(file);
				} else {
					FileObject fileObject = new FileObject();
					List<File> list = new ArrayList<>();
					list.add(file);
					fileObject.setFileList(list);
					fileObject.setFileName(tempFileName);
					fileObject.setFileStatus(fileNames[1]);
					map.put(tempFileName, fileObject);
				}

			}
		}
	}

	/***
	 * 
	 * @param paragraph
	 * @param text
	 * @param font
	 */
	static void addparagraph(XWPFParagraph paragraph, String text, String font, boolean isBold) {
		XWPFRun run = paragraph.createRun();
		run.setText(text);
		run.setBold(isBold);
		run.setFontFamily(font);
		if ("Testing Documentation".equals(text) || text.startsWith("---"))
			run.setFontSize(14);
		else
			run.setFontSize(10);
	}

	/**
	 * 
	 * @param run
	 */
	static void addTabs(XWPFRun run) {
		run.addTab();
		run.addTab();
		run.addTab();
		run.addTab();
		run.addTab();
		run.addTab();
		run.addTab();
		run.addTab();
	}

	/**
	 * 
	 * @param run
	 */
	static void addTab(XWPFRun run) {
		run.addTab();
		run.addTab();
		run.addTab();
	}

	static void addImage(XWPFParagraph paragraph, FileObject fileObject) throws Exception {
		for (File fileName : fileObject.getFileList()) {
			XWPFRun run = paragraph.createRun();
			run.addBreak();
			run.addPicture(new FileInputStream(fileName), XWPFDocument.PICTURE_TYPE_JPEG, fileObject.getFileName(),
					Units.toEMU(200), Units.toEMU(200));
			run.addBreak();
		}

	}
}
