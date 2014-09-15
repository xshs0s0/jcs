package com.s0s0.app.util;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class ClassUtilTest extends TestCase {

	public ClassUtilTest(String testName) {
		super(testName);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testGetClassesInPackage()
	{
		String pckgname = "com.s0s0.app.text.language";
		List<String> classnames = new ArrayList<String>();
		try {
			List<Class<?>> classes = ClassUtil.getClassesInPackage(pckgname);
			for (Class<?> cls : classes)
			{
				String packagename = cls.getPackage().getName();
				classnames.add(cls.getName().substring(packagename.length()+1).toLowerCase());
			}
		} catch (Exception e) {
			assertTrue(e.getMessage(),false);
		}
		String[] languages = new String[] {"en"};
		for (String language : languages)
		{
			assertTrue(classnames.contains(language));
		}
	}

}
