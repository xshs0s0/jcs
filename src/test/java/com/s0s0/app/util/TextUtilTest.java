package com.s0s0.app.util;

import java.util.List;

import com.s0s0.app.text.TextCode;
import com.s0s0.app.text.language.En;

import junit.framework.TestCase;

public class TextUtilTest extends TestCase {

	public TextUtilTest(String testName) {
		super(testName);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testFirstLetterUpCase()
	{
		String in = "en";
		String exp = "En";
		String out = TextUtil.FirstLetterUpCase(in);
		assertEquals(out, exp);
		in = null;
		out = TextUtil.FirstLetterUpCase(in);
		assertNull(out);
		in = "";
		exp = "";
		out = TextUtil.FirstLetterUpCase(in);
		assertEquals(out, exp);
	}
	

	public void testGetStaticStringFieldNames()
	{
		List<String> fieldnames = TextUtil.getStaticStringFieldNames(TextCode.class);
		assertTrue(fieldnames.contains(TextCode.TXT_FEL_01));
		assertTrue(fieldnames.contains(TextCode.TXT_FEL_02));
	}
	
	public void testIsTextRepoValid()
	{
		En en = new En();
		assertTrue(TextUtil.isTextRepoValid(en));
	}
	
	public void testGetFileExtension()
	{
		assertNull(TextUtil.GetFileExtension(""));
		assertNull(TextUtil.GetFileExtension(null));
		assertTrue(TextUtil.GetFileExtension(".").equals(""));
		assertTrue(TextUtil.GetFileExtension("t.").equals(""));
		assertTrue(TextUtil.GetFileExtension(".t").equals("t"));
		assertTrue(TextUtil.GetFileExtension("f.t").equals("t"));
	}
	
	public void testExtractFileName()
	{
		assertNull(TextUtil.ExtractFileName(null));
		assertTrue(TextUtil.ExtractFileName("").equals(""));
		assertTrue(TextUtil.ExtractFileName("ab").equals("ab"));
		assertTrue(TextUtil.ExtractFileName("ab/cv").equals("cv"));
		assertTrue(TextUtil.ExtractFileName("ab/cd/em.s").equals("em.s"));
		assertTrue(TextUtil.ExtractFileName("ab\\cd\\em.s").equals("em.s"));
		assertTrue(TextUtil.ExtractFileName("ab/cd/em.s/").equals(""));
	}
}