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
package at.tl_photography.jsync.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;

/**
 * The Class Runner.
 */
public class Runner implements Runnable {

	/** The logger. */
	private static Logger logger = Logger.getLogger(Runner.class.getName());
	/** The in. */
	private InputStream in;

	/** The out. */
	private OutputStream out;

	/** The connected. */
	private boolean connected = true;

	/**
	 * Instantiates a new runner.
	 *
	 * @param inputStream
	 *            the input stream
	 * @param outputStream
	 *            the output stream
	 */
	public Runner(InputStream inputStream, OutputStream outputStream) {
		this.in = inputStream;
		this.out = outputStream;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		byte[] buffer = new byte[1024]; // Adjust if you want
		int bytesRead;
		try {
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
			}
		} catch (IOException e) {
			logger.info("disconnect");
			this.connected = false;
		}
		this.connected = false;
	}

	/**
	 * Checks if is connected.
	 *
	 * @return true, if is connected
	 */
	public boolean isConnected() {
		return connected;
	}

	/**
	 * Disconnect.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void disconnect() throws IOException {
		connected = false;
		in.close();
		out.close();
	}

}
