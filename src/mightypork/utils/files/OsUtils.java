package mightypork.utils.files;


import java.io.File;


public class OsUtils {

	public static enum EnumOS
	{
		linux, macos, solaris, unknown, windows;

		public boolean isLinux()
		{
			return this == linux || this == solaris;
		}


		public boolean isMac()
		{
			return this == macos;
		}


		public boolean isWindows()
		{
			return this == windows;
		}
	}

	private static EnumOS cachedOs;


	/**
	 * Get App dir, ensure it exists
	 * 
	 * @param dirname
	 * @return app dir
	 */
	public static File getWorkDir(String dirname)
	{
		return getWorkDir(dirname, true);
	}


	/**
	 * Get App sub-folder
	 * 
	 * @param dirname
	 * @param subfolderName
	 * @param create
	 * @return the folder
	 */
	public static File getWorkDir(String dirname, String subfolderName, boolean create)
	{
		File f = new File(getWorkDir(dirname), subfolderName);

		if (!f.exists() && create) {
			if (!f.mkdirs()) {
				throw new RuntimeException("Could not create.");
			}
		}

		return f;
	}


	/**
	 * Get App sub-folder, create
	 * 
	 * @param dirname
	 * @param subfolderName
	 * @return the folder
	 */
	public static File getWorkDir(String dirname, String subfolderName)
	{
		return getWorkDir(dirname, subfolderName, true);
	}


	public static EnumOS getOs()
	{
		if (cachedOs != null) return cachedOs;

		String s = System.getProperty("os.name").toLowerCase();

		if (s.contains("win")) {
			cachedOs = EnumOS.windows;

		} else if (s.contains("mac")) {
			cachedOs = EnumOS.macos;

		} else if (s.contains("linux") || s.contains("unix")) {
			cachedOs = EnumOS.linux;

		} else if (s.contains("solaris") || s.contains("sunos")) {
			cachedOs = EnumOS.solaris;

		} else {
			cachedOs = EnumOS.unknown;
		}

		return cachedOs;
	}


	private static File getWorkDir(String dirname, boolean create)
	{
		String userhome = System.getProperty("user.home", ".");
		File file;

		switch (getOs()) {
			case linux:
			case solaris:
				file = new File(userhome, "." + dirname + '/');
				break;

			case windows:
				String appdata = System.getenv("APPDATA");

				if (appdata != null) {
					file = new File(appdata, "." + dirname + '/');
				} else {
					file = new File(userhome, "." + dirname + '/');
				}

				break;

			case macos:
				file = new File(userhome, "Library/Application Support/" + dirname);
				break;

			default:
				file = new File(userhome, dirname + "/");
				break;
		}

		if (!file.exists() || !file.isDirectory()) {
			if (create) {
				if (!file.mkdirs()) {
					throw new RuntimeException("Could not create working directory.");
				}
			}
		}

		return file;
	}

}
