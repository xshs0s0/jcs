package com.s0s0.app.search;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.s0s0.app.exception.JcsException;
import com.s0s0.app.util.TextUtil;

public class ZipFormatFileSearchHandler implements SearchHandlerInterface {
	
	private static final List<String> formats = new ArrayList<String>() {{
		add("zip");
		add("war");
		add("ear");
		add("jar");
	}};
	
	public void handle(File file, Query query, SearchCallbackInterface callback, List<String> extensions)
			throws JcsException {
		ZipInputStream zis = null;
		try {
			zis = new ZipInputStream(new FileInputStream(file));
			ZipEntry ze = zis.getNextEntry();
			while (ze != null)
			{
				handleZipEntry(ze, file, query, callback, extensions);
				ze = zis.getNextEntry();
			}
			zis.closeEntry();
			zis.close();
		} catch (Exception e) {
			if (zis!=null)
			{
				try {
					zis.closeEntry();
					zis.close();
				} catch (IOException e1) {
					// do nothing
				}
			}
			throw new JcsException(e);
		}
	}
	
	private void handleZipEntry(ZipEntry ze, File file, Query query, SearchCallbackInterface callback, List<String> extensions) throws JcsException
	{
		SearchResult result = new SearchResult();
		List<ResultField> resultfields = new ArrayList<ResultField>();
		QueryTypeEnum type = query.getType();
		TextMatcherInterface textmatcher = null;
		boolean casesensitive = true;
		if (type==QueryTypeEnum.CASE_INSENSITIVE_TEXT_MATCH)
		{
			textmatcher = new StandardTextMatcher();
			casesensitive = false;
		} else if (type==QueryTypeEnum.CASE_SENSITIVE_TEXT_MATCH)
		{
			textmatcher = new StandardTextMatcher();
		} else if (type==QueryTypeEnum.CASE_INSENSITIVE_REGEX_MATCH)
		{
			casesensitive = false;
			textmatcher = new RegexTextMatcher();
		} else if (type==QueryTypeEnum.CASE_SENSITIVE_REGEX_MATCH)
		{
			textmatcher = new RegexTextMatcher();
		}
		if (textmatcher!=null)
		{
			String zipentryname = TextUtil.ExtractFileName(ze.getName());
			String extention = TextUtil.GetFileExtension(zipentryname);
			if (extensions.contains(extention))
			{
				if (textmatcher.match(query.getValue(), zipentryname, casesensitive)) {
					List<String> rf = query.getRf();
					if ((rf==null)|| rf.contains("path"))
					{
						resultfields.add(new StringResultField("path",file.getParent()));
					}
					if ((rf==null)|| rf.contains("filename"))
					{
						resultfields.add(new StringResultField("filename",file.getName()));
					}
					if ((rf==null)|| rf.contains("classname"))
					{
						resultfields.add(new StringResultField("classname",ze.getName()));
					}
					result.setFields(resultfields);
					callback.callback(result);
				}
			}
		}
	}

	public boolean isHandlble(String extension) throws JcsException {
		if (formats.contains(extension))
		{
			return true;
		}
		return false;
	}

}
