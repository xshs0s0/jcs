package com.s0s0.app.text.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.s0s0.app.exception.JcsException;
import com.s0s0.app.text.TextManagerAbstract;
import com.s0s0.app.text.TextRepoInterface;
import com.s0s0.app.util.ClassUtil;
import com.s0s0.app.util.TextUtil;

public class ClassTextManager extends TextManagerAbstract {

	private static Map<String, TextRepoInterface> trimap = new HashMap<String, TextRepoInterface>();
	private static final String LANGUAGE_PACKAGE_NAME = "com.s0s0.app.text.language";
	
	@Override
	public String getText(String textcode) throws JcsException {
		TextRepoInterface tri = getTextRepo();
		return tri.getText(textcode);
	}

	@Override
	public Map<String, String> getTextMapping() throws JcsException {
		TextRepoInterface tri = getTextRepo();
		return tri.getTextMapping();
	}

	@Override
	public List<String> getSupportedLanguageList() throws JcsException {
		String pkgename = LANGUAGE_PACKAGE_NAME;
		List<String> languagelist = new ArrayList<String>();
		List<Class<?>> classes = ClassUtil.getClassesInPackage(pkgename);
		for (Class cls : classes)
		{
			String packagename = cls.getPackage().getName();
			languagelist.add(cls.getName().substring(packagename.length()+1).toLowerCase());
		}
		return languagelist;
	}

	@Override
	public TextRepoInterface getTextRepo() throws JcsException {
		{
			String language = getLanguage();
			if (!trimap.containsKey(language))
			{
				try {
					String classname = "com.s0s0.app.text.language."
							+ TextUtil.FirstLetterUpCase(language);
					Class<?> cls = Class.forName(classname);
					Object obj = cls.newInstance();
					if (obj instanceof TextRepoInterface) {
						trimap.put(language, (TextRepoInterface) obj);
					}
				} catch (Exception e) {
					throw new JcsException(e);
				}
			}
			return trimap.get(language);
		}
	}
}