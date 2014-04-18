package mightypork.util.files;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

import mightypork.util.logging.Log;
import mightypork.util.string.StringUtils;
import mightypork.util.string.filtering.StringFilter;


public class FileUtils {
	
	/**
	 * Copy directory recursively.
	 * 
	 * @param source source file
	 * @param target target file
	 * @throws IOException on error
	 */
	public static void copyDirectory(File source, File target) throws IOException
	{
		copyDirectory(source, target, null, null);
	}
	
	
	/**
	 * Copy directory recursively - advanced variant.
	 * 
	 * @param source source file
	 * @param target target file
	 * @param filter filter accepting only files and dirs to be copied
	 * @param filesCopied list into which all the target files will be added
	 * @throws IOException on error
	 */
	public static void copyDirectory(File source, File target, FileFilter filter, List<File> filesCopied) throws IOException
	{
		if (!source.exists()) return;
		
		if (source.isDirectory()) {
			if (!target.exists() && !target.mkdir()) {
				throw new IOException("Could not open destination directory.");
			}
			
			final String[] children = source.list();
			for (final String element : children) {
				copyDirectory(new File(source, element), new File(target, element), filter, filesCopied);
			}
			
		} else {
			if (filter != null && !filter.accept(source)) {
				return;
			}
			
			if (filesCopied != null) filesCopied.add(target);
			copyFile(source, target);
		}
	}
	
	
	/**
	 * List directory recursively
	 * 
	 * @param source source file
	 * @param filter filter accepting only files and dirs to be copied (or null)
	 * @param files list of the found files
	 * @throws IOException on error
	 */
	public static void listDirectoryRecursive(File source, StringFilter filter, List<File> files) throws IOException
	{
		if (source.isDirectory()) {
			final String[] children = source.list();
			for (final String element : children) {
				listDirectoryRecursive(new File(source, element), filter, files);
			}
			
		} else {
			if (filter != null && !filter.accept(source.getAbsolutePath())) {
				return;
			}
			
			files.add(source);
		}
	}
	
	
	/**
	 * Copy file using streams. Make sure target directory exists!
	 * 
	 * @param source source file
	 * @param target target file
	 * @throws IOException on error
	 */
	public static void copyFile(File source, File target) throws IOException
	{
		
		try(InputStream in = new FileInputStream(source);
			OutputStream out = new FileOutputStream(target)) {
			
			copyStream(in, out);
		}
	}
	
	
	/**
	 * Copy bytes from input to output stream, leaving out stream open
	 * 
	 * @param in input stream
	 * @param out output stream
	 * @throws IOException on error
	 */
	public static void copyStream(InputStream in, OutputStream out) throws IOException
	{
		if (in == null) {
			throw new NullPointerException("Input stream is null");
		}
		
		if (out == null) {
			throw new NullPointerException("Output stream is null");
		}
		
		final byte[] buf = new byte[2048];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
	}
	
	
	/**
	 * Improved delete
	 * 
	 * @param path deleted path
	 * @param recursive recursive delete
	 * @return success
	 */
	public static boolean delete(File path, boolean recursive)
	{
		if (!path.exists()) {
			return true;
		}
		
		if (!recursive || !path.isDirectory()) return path.delete();
		
		final String[] list = path.list();
		for (int i = 0; i < list.length; i++) {
			if (!delete(new File(path, list[i]), true)) return false;
		}
		
		return path.delete();
	}
	
	
	/**
	 * Read entire file to a string.
	 * 
	 * @param file file
	 * @return file contents
	 * @throws IOException
	 */
	public static String fileToString(File file) throws IOException
	{
		try(FileInputStream fin = new FileInputStream(file)) {
			
			return streamToString(fin);
		}
	}
	
	
	/**
	 * Get files in a folder (create folder if needed)
	 * 
	 * @param dir folder
	 * @return list of files
	 */
	public static List<File> listDirectory(File dir)
	{
		return FileUtils.listDirectory(dir, null);
	}
	
	
	/**
	 * Get files in a folder (create folder if needed)
	 * 
	 * @param dir folder
	 * @param filter file filter
	 * @return list of files
	 */
	public static List<File> listDirectory(File dir, FileFilter filter)
	{
		try {
			dir.mkdir();
		} catch (final RuntimeException e) {
			Log.e("Error creating folder " + dir, e);
		}
		
		final List<File> list = new ArrayList<>();
		
		try {
			for (final File f : dir.listFiles(filter)) {
				list.add(f);
			}
		} catch (final Exception e) {
			Log.e("Error listing folder " + dir, e);
		}
		
		return list;
	}
	
	
	/**
	 * Remove extension.
	 * 
	 * @param file file
	 * @return filename without extension
	 */
	public static String[] getFilenameParts(File file)
	{
		return getFilenameParts(file.getName());
	}
	
	
	public static String getExtension(File file)
	{
		return getExtension(file.getName());
	}
	
	
	public static String getExtension(String file)
	{
		return StringUtils.fromLastChar(file, '.');
	}
	
	
	/**
	 * Remove extension.
	 * 
	 * @param filename
	 * @return filename and extension
	 */
	public static String[] getFilenameParts(String filename)
	{
		String ext, name;
		
		try {
			ext = StringUtils.fromLastDot(filename);
		} catch (final StringIndexOutOfBoundsException e) {
			ext = "";
		}
		
		try {
			name = StringUtils.toLastDot(filename);
		} catch (final StringIndexOutOfBoundsException e) {
			name = "";
			Log.w("Error extracting extension from file " + filename);
		}
		
		return new String[] { name, ext };
	}
	
	
	/**
	 * Read entire input stream to a string, and close it.
	 * 
	 * @param in input stream
	 * @return file contents
	 */
	public static String streamToString(InputStream in)
	{
		return streamToString(in, -1);
	}
	
	
	/**
	 * Read input stream to a string, and close it.
	 * 
	 * @param in input stream
	 * @param lines max number of lines (-1 to disable limit)
	 * @return file contents
	 */
	public static String streamToString(InputStream in, int lines)
	{
		if (in == null) {
			Log.e(new NullPointerException("Null stream to be converted to String."));
			return ""; // to avoid NPE's
		}
		
		BufferedReader br = null;
		final StringBuilder sb = new StringBuilder();
		
		String line;
		try {
			int cnt = 0;
			br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			while ((line = br.readLine()) != null && (cnt < lines || lines <= 0)) {
				sb.append(line + "\n");
				cnt++;
			}
			
			if (cnt == lines && lines > 0) {
				sb.append("--- end of preview ---\n");
			}
			
		} catch (final IOException e) {
			Log.e(e);
		} finally {
			try {
				if (br != null) br.close();
			} catch (final IOException e) {
				// ignore
			}
		}
		
		return sb.toString();
	}
	
	
	public static InputStream stringToStream(String text)
	{
		if (text == null) return null;
		
		try {
			return new ByteArrayInputStream(text.getBytes("UTF-8"));
		} catch (final UnsupportedEncodingException e) {
			Log.e(e);
			return null;
		}
	}
	
	
	public static InputStream getResource(String path)
	{
		final InputStream in = FileUtils.class.getResourceAsStream(path);
		
		if (in != null) return in;
		
		try {
			return new FileInputStream(new File(".", path));
		} catch (final FileNotFoundException e) {
			// error			
			Log.w("Could not open resource stream: " + path);
			return null;
		}
		
	}
	
	
	public static String getResourceAsString(String path)
	{
		return streamToString(FileUtils.class.getResourceAsStream(path));
	}
	
	
	/**
	 * Save string to file
	 * 
	 * @param file file
	 * @param text string
	 * @throws IOException on error
	 */
	public static void stringToFile(File file, String text) throws IOException
	{
		try(PrintStream out = new PrintStream(new FileOutputStream(file), false, "UTF-8")) {
			
			out.print(text);
			
			out.flush();
			
		}
	}
	
	
	public static void deleteEmptyDirs(File base) throws IOException
	{
		for (final File f : listDirectory(base)) {
			if (!f.isDirectory()) continue;
			
			deleteEmptyDirs(f);
			
			final List<File> children = listDirectory(f);
			if (children.size() == 0) {
				if (!f.delete()) throw new IOException("Could not delete a directory: " + f);
				continue;
			}
		}
		
	}
	
	
	/**
	 * Replace special characters with place holders in a filename.
	 * 
	 * @param filestring filename string
	 * @return escaped
	 */
	public static String escapeFileString(String filestring)
	{
		final StringBuilder sb = new StringBuilder();
		
		for (final char c : filestring.toCharArray()) {
			switch (c) {
				case '%':
					sb.append("%%");
					break;
				
				case '.':
					sb.append("%d");
					break;
				
				default:
					sb.append(c);
			}
			
		}
		
		return sb.toString();
	}
	
	
	/**
	 * Unescape filename string obtained by escapeFileString().
	 * 
	 * @param filestring escaped string
	 * @return clean string
	 */
	public static String unescapeFileString(String filestring)
	{
		filestring = filestring.replace("%d", ".");
		filestring = filestring.replace("%%", "%");
		
		return filestring;
	}
	
	
	/**
	 * Escape filename, keeping the same extension
	 * 
	 * @param filename filename
	 * @return escaped
	 */
	public static String escapeFilename(String filename)
	{
		final String[] parts = getFilenameParts(filename);
		
		return escapeFileString(parts[0]) + "." + parts[1];
	}
	
	
	/**
	 * Unescape filename, keeping the same extension
	 * 
	 * @param filename escaped filename
	 * @return unesaped
	 */
	public static String unescapeFilename(String filename)
	{
		final String[] parts = getFilenameParts(filename);
		
		return unescapeFileString(parts[0]) + "." + parts[1];
	}
	
	
	public static String getBasename(String name)
	{
		return StringUtils.toLastChar(StringUtils.fromLastChar(name, '/'), '.');
	}
	
	
	public static String getFilename(String name)
	{
		return StringUtils.fromLastChar(name, '/');
	}
	
	
	/**
	 * Copy resource to file
	 * 
	 * @param resname resource name
	 * @param file out file
	 * @throws IOException
	 */
	public static void resourceToFile(String resname, File file) throws IOException
	{
		try(InputStream in = FileUtils.getResource(resname);
			OutputStream out = new FileOutputStream(file)) {
			
			FileUtils.copyStream(in, out);
		}
		
	}
	
	
	/**
	 * Get resource as string, safely closing streams.
	 * 
	 * @param resname resource name
	 * @return resource as string, empty string on failure
	 * @throws IOException on fail
	 */
	public static String resourceToString(String resname) throws IOException
	{
		try(InputStream in = FileUtils.getResource(resname)) {
			return streamToString(in);
		}
	}
}