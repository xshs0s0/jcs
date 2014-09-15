package com.s0s0.app;

import java.util.ArrayList;
import java.util.List;

import com.s0s0.app.exception.JcsException;
import com.s0s0.app.text.TextCode;

public class FileExtensionList {

	List<String> extensions = new ArrayList<String>();
	
	public FileExtensionList()
	{
	}
	
	public List<String> getFileExtensionList()
	{
		return this.extensions;
	}
	
	public void addFileExtension(String extension) throws JcsException
	{
		if ((extension == null) || (extension.isEmpty()))
		{
			throw new JcsException(TextCode.TXT_FEL_01);
		}
		extension = extension.toLowerCase();
		
		if (extensions.contains(extension))
		{
			throw new JcsException(TextCode.TXT_FEL_02);
		} else
		{
			extensions.add(extension);
		}
	}
}
