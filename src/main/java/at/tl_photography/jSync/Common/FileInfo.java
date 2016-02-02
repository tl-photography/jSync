package at.tl_photography.jSync.Common;

import java.io.Serializable;

public class FileInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2025504367114342220L;
	private String path;
	private String md5;

	public FileInfo(String relative, String md5) {
		this.path = relative;
		this.md5 = md5;
	}

	public String getPath() {
		return path;
	}

	@Override
	public String toString() {
		return "File: " + path;
	}

	public String getMD5() {
		return md5;
	}

}
