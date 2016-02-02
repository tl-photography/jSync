/*
 *	jSync 
 *
 * @author Thomas Leber
 * @version 0.1
 * 
 *
 *                                  Apache License
 *                          Version 2.0, January 2004
 *                      http://www.apache.org/licenses/
 */
package at.tl_photography.jsync.common;

import java.io.Serializable;

/**
 * The Class FileInfo.
 */
public class FileInfo implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2025504367114342220L;

	/** The path. */
	private String path;

	/** The md5. */
	private String md5;

	/**
	 * Instantiates a new file info.
	 *
	 * @param relative
	 *            the relative
	 * @param md5
	 *            the md5
	 */
	public FileInfo(String relative, String md5) {
		this.path = relative;
		this.md5 = md5;
	}

	/**
	 * Gets the path.
	 *
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "File: " + path;
	}

	/**
	 * Gets the m d5.
	 *
	 * @return the m d5
	 */
	public String getMD5() {
		return md5;
	}

}
