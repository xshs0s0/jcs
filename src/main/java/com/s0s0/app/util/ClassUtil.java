package com.s0s0.app.util;

import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import sun.net.www.protocol.file.FileURLConnection;

import com.s0s0.app.exception.JcsException;
import com.s0s0.app.text.ApplicationError;

public class ClassUtil {

	private static void checkDirectory(File directory, String pckgname,
			ArrayList<Class<?>> classes) throws ClassNotFoundException {
		if (directory.exists() && directory.isDirectory()) {
			final String[] files = directory.list();

			for (final String file : files) {
				if (file.endsWith(".class")) {
					try {
						classes.add(Class.forName(pckgname + '.'
								+ file.substring(0, file.length() - 6)));
					} catch (final NoClassDefFoundError e) {
						// do nothing. this class hasn't been found by the
						// loader, and we don't care.
					}
				} else {
					// do nothing
					// only check direct descendant classes
				}
			}
		}
	}

	/*
	 * Get direct descendant classes of a package, the package should belong to a jar file found in classpath
	 */
	@SuppressWarnings("restriction")
	public static List<Class<?>> getClassesInPackage(String pckgname)
			throws JcsException {
		final ArrayList<Class<?>> classes = new ArrayList<Class<?>>();

		try {
			final ClassLoader cld = Thread.currentThread()
					.getContextClassLoader();

			final Enumeration<URL> resources = cld.getResources(pckgname
					.replace('.', '/'));
			URLConnection connection = null;
			for (URL url = null; resources.hasMoreElements()
					&& ((url = resources.nextElement()) != null);) {
				connection = url.openConnection();

				if (connection instanceof FileURLConnection) {
					checkDirectory(
							new File(URLDecoder.decode(url.getPath(), "UTF-8")),
							pckgname, classes);
				}
			}
		} catch (Exception e) {
			throw new JcsException(ApplicationError.GET_CLASSES_IN_PACKAGE_ERROR, e);
		}
		return classes;
	}
}
