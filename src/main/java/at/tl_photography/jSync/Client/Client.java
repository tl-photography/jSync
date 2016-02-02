package at.tl_photography.jSync.Client;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.logging.Logger;

import at.tl_photography.jSync.Common.FileChecker;
import at.tl_photography.jSync.Common.FileInfo;
import at.tl_photography.jSync.Server.Server;
import sun.dc.pr.PathStroker;

// TODO: Auto-generated Javadoc
/**
 * The Class Client.
 */
public class Client {

	private static Logger logger = Logger.getLogger(Client.class.getName());

	/** The host. */
	public static String HOST = "127.0.0.1";

	/** The port. */
	public static Integer PORT = 81;

	/** The directory. */
	public static String DIRECTORY = "D:/TEMP";

	/** The socket. */
	private static Socket socket;

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		try {
			socket = new Socket(HOST, PORT);

			DIRECTORY = args[1];

			if (args[0].equals("s")) {
				sendFiles();
			} else {
				recieveFiles();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Recieve files.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException
	 */
	private static void recieveFiles() throws IOException, ClassNotFoundException {

		ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

		while (true) {

			FileInfo info = (FileInfo) ois.readObject();
			logger.info(info.toString());

			File file = new File(DIRECTORY + info.getPath());
			if (file.exists()) {
				logger.info("file " + info + " exists");
				if (FileChecker.generateMD5(file.toPath()).equals(info.getMD5())) {
					logger.info("file " + info + " has the same MD5");
					oos.writeBoolean(true);
					oos.flush();
					continue;
				} else {
					logger.info("file " + info + " has not the same MD5, deleting");
					file.delete();
					oos.writeBoolean(false);
					oos.flush();
				}
			} else {
				logger.info("file " + info + " does not extist");
				oos.writeBoolean(false);
				oos.flush();
			}

			System.out.println(file.getParentFile());
			
			file.getParentFile().mkdirs();

			FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());

			byte[] buffer = new byte[1024]; // Adjust if you want
			int bytesRead;

			while ((bytesRead = ois.read(buffer)) != -1) {
				fos.write(buffer, 0, bytesRead);
			}
			fos.flush();
			fos.close();

		}
	}

	/**
	 * Send files.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static void sendFiles() throws IOException {

		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

		while (true) {
			Iterator<Path> iter = Files.walk(Paths.get(DIRECTORY)).filter(Files::isRegularFile).iterator();

			while (iter.hasNext()) {
				Path file = (Path) iter.next();

				logger.info("found file " + file);

				String relative = new File(DIRECTORY).toURI().relativize(file.toFile().toURI()).getPath();
				FileInfo info = new FileInfo(relative, FileChecker.generateMD5(file));
				oos.writeObject(info);
				oos.flush();

				if (ois.readBoolean()) {
					logger.info("file " + file + " exsist on the other side");
				} else {
					logger.info("file " + file + " does not exsist on the other side");
					FileInputStream fis = new FileInputStream(file.toFile());

					byte[] buffer = new byte[1024]; // Adjust if you want
					int bytesRead;

					while ((bytesRead = fis.read(buffer)) != -1) {
						oos.write(buffer, 0, bytesRead);
					}
					oos.flush();
					fis.close();
				}
			}

			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
