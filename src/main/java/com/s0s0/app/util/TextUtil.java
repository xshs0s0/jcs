package com.s0s0.app.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import com.s0s0.app.text.TextCode;
import com.s0s0.app.text.TextRepoInterface;

public class TextUtil {
	
	public static String ExtractFileName(String filepath)
	{
		String seperator = "/";
		if (filepath == null)
			return filepath;
		filepath = filepath.replaceAll("\\\\", seperator).trim();
		while (!filepath.isEmpty()&&filepath.endsWith(seperator))
		{
			return "";
		}
		String[] nameparts = filepath.split(seperator);
		if ((nameparts != null)&&(nameparts.length>0))
		{
			return nameparts[nameparts.length-1];
		}
		return filepath;
	}
	
	public static String GetFileExtension(String filename)
	{
		if ((filename==null) || (filename.isEmpty()))
		{
			return null;
		} else
		{
			filename = filename.trim();
			int idx = filename.lastIndexOf(".");
			if ((idx < 0)||(idx==(filename.length()-1)))
			{
				return "";
			} else
			{
				return filename.substring(idx+1);
			}
		}
	}
	
	public static String FirstLetterUpCase(String in)
	{
		if ((in==null) || (in.isEmpty()))
		{
			return in;
		} else
		{
			return in.substring(0, 1).toUpperCase() + in.substring(1);
		}
	}
	
	public static List<String> getStaticStringFieldNames(Class cls)
	{
		List<String> fieldnames = new ArrayList<String>();
		Field[] fields = cls.getDeclaredFields();
		for (Field f : fields) {
		    if (Modifier.isStatic(f.getModifiers()) && (String.class == f.getType())) {
		        fieldnames.add(f.getName());
		    } 
		}
		return fieldnames;
	}
	
	public static boolean isTextRepoValid(TextRepoInterface tri)
	{
		if (tri==null)
		{
			return false;
		} else {
			for (String textcode : getStaticStringFieldNames(TextCode.class))
			{
				if (!tri.getTextMapping().containsKey(textcode))
				{
					return false;
				}
			}
			return true;
		}
	}
}