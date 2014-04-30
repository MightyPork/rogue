package mightypork.gamecore.util.files;


import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;


/**
 * Instance lock (avoid running twice)
 * 
 * @author MightyPork
 */
public class InstanceLock {
	
	@SuppressWarnings("resource")
	public static boolean onFile(final File lockFile)
	{
		try {
			final RandomAccessFile randomAccessFile = new RandomAccessFile(lockFile, "rw");
			
			final FileLock fileLock = randomAccessFile.getChannel().tryLock();
			if (fileLock != null) {
				
				Runtime.getRuntime().addShutdownHook(new Thread() {
					
					@Override
					public void run()
					{
						try {
							fileLock.release();
							randomAccessFile.close();
							if (!lockFile.delete()) throw new IOException();
						} catch (final Exception e) {
							System.err.println("Unable to remove lock file.");
							e.printStackTrace();
						}
					}
				});
				
				return true;
			}
			
			return false;
		} catch (final IOException e) {
			return false;
		}
	}
	
}
