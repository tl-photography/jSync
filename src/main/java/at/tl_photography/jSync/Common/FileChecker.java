package at.tl_photography.jSync.Common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

import org.apache.commons.codec.digest.DigestUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class FileChecker.
 */
public class FileChecker {

	/**
	 * Generate m d5.
	 *
	 * @param path
	 *            the path
	 * @return the byte[]
	 */
	public static String generateMD5(Path path) {
		FileInputStream fis;
		try {
			fis = new FileInputStream(path.toFile());
			return DigestUtils.md5Hex(fis);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

}