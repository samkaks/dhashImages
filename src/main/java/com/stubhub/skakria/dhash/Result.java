package com.stubhub.skakria.dhash;

import java.net.URL;

public class Result {

	String fileName;
	String filePath;
	Double similarity;
	URL url;

	public Result() {
		
	}
	
	public Result(String filename, String filePath, Double similarity, URL url) {
		this.fileName = filename;
		this.filePath = filePath;
		this.similarity = similarity;
		this.url = url;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Double getSimilarity() {
		return similarity;
	}

	public void setSimilarity(Double similarity) {
		this.similarity = similarity;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

}
