package edu.chinna.kadira;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class FileObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6205099610016748635L;

	private String fileName = null;
	private String fileStatus = null;
	private List<File> fileList = null;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}

	public List<File> getFileList() {
		return fileList;
	}

	public void setFileList(List<File> fileList) {
		this.fileList = fileList;
	}

	@Override
	public String toString() {
		return "FileObject [fileName=" + fileName + ", fileStatus=" + fileStatus + ", fileList=" + fileList + "]";
	}

}
