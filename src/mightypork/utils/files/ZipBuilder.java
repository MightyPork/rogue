package mightypork.utils.files;

import java.io.*;
import java.util.HashSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import mightypork.utils.logging.Log;


/**
 * Class for building a zip file
 * 
 * @author MightyPork
 */
public class ZipBuilder {
	
	private ZipOutputStream out;
	private HashSet<String> included = new HashSet<String>();
	
	
	/**
	 * @param target target zip file
	 * @throws FileNotFoundException if the file is directory or cannot be
	 *             created
	 */
	public ZipBuilder(File target) throws FileNotFoundException {
		target.getParentFile().mkdirs();
		
		FileOutputStream dest = new FileOutputStream(target);
		out = new ZipOutputStream(new BufferedOutputStream(dest));
	}
	
	
	/**
	 * Add stream to a path
	 * 
	 * @param path path
	 * @param in stream
	 * @throws IOException
	 */
	public void addStream(String path, InputStream in) throws IOException
	{
		path = preparePath(path);
		if (included.contains(path)) {
			Log.f3("Zip already contains file " + path + ", skipping.");
			return; // ignore
		}
		included.add(path);
		
		out.putNextEntry(new ZipEntry(path));
		
		FileUtils.copyStream(in, out);
	}
	
	
	/**
	 * Add string as a file
	 * 
	 * @param path path
	 * @param text text to write
	 * @throws IOException
	 */
	public void addString(String path, String text) throws IOException
	{
		path = preparePath(path);
		if (included.contains(path)) return; // ignore
		included.add(path);
		
		out.putNextEntry(new ZipEntry(path));
		
		InputStream in = FileUtils.stringToStream(text);
		FileUtils.copyStream(in, out);
	}
	
	
	/**
	 * Add resource obtained via FileUtils.getResource()
	 * 
	 * @param path path
	 * @param resPath resource path
	 * @throws IOException
	 */
	public void addResource(String path, String resPath) throws IOException
	{
		path = preparePath(path);
		if (included.contains(path)) return; // ignore
		included.add(path);
		
		out.putNextEntry(new ZipEntry(path));
		
		InputStream in = FileUtils.getResource(resPath);
		FileUtils.copyStream(in, out);
	}
	
	
	/**
	 * Normalize path
	 * 
	 * @param path original path
	 * @return normalized path
	 */
	private String preparePath(String path)
	{
		path = path.replace("\\", "/");
		
		if (path.charAt(0) == '/') path = path.substring(1);
		
		return path;
	}
	
	
	/**
	 * Close the zip stream
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException
	{
		out.close();
	}
}
