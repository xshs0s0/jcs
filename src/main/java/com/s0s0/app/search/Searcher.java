package com.s0s0.app.search;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

import com.s0s0.app.exception.JcsException;
import com.s0s0.app.log.LogManager;
import com.s0s0.app.util.TextUtil;

public class Searcher implements Runnable {

	private Query query;
	private SearchCallbackInterface callback;
	private SearchCompleteCallback completecallback;
	private String[] paths;
	private List<String> extensions;
	private List<SearchHandlerInterface> handlers;
	private LogManager logger;
	private boolean run = true;
	
	public Searcher()
	{
	}
	
	public Searcher(Query query, SearchCallbackInterface callback, SearchCompleteCallback completecallback, String[] paths, List<String> extensions, List<SearchHandlerInterface> handlers, LogManager logger)
	{
		this.query = query;
		this.callback = callback;
		this.completecallback = completecallback;
		this.paths = paths;
		this.extensions = extensions;
		this.handlers = handlers;
		this.logger = logger;
	}
	
	public void run()
	{
		try {
			for (String path : paths)
			{
				if (!run)
					return;
				File pathfile = new File(path);
				if (pathfile.exists())
				{
					if (pathfile.isDirectory())
					{
						searchDirectory(pathfile);
					} else
					{
						searchFile(pathfile, true);
					}
				}
			}
		} catch (JcsException e) {
			//logger.error("Search Failed!");
			//logger.error(e.getMessage());
			e.printStackTrace();
		}
		completecallback.complete();
	}
	
	private void searchDirectory(File directory) throws JcsException
	{
		FileFilter filter = new FileFilter()
		{
			public boolean accept(File f)
			{
				String ext = TextUtil.GetFileExtension(f.getName());
				if (extensions.contains(ext))
					return true;
				return false;
			}
		};
		File[] files = directory.listFiles(filter);
		for (File file : files)
		{
			if (!run)
				return;
			searchFile(file, false);
		}
		File[] subdirs = directory.listFiles(new FileFilter() {
			public boolean accept(File f)
			{
				return f.isDirectory();
			}
		});
		for (File subdir : subdirs)
		{
			if (!run)
				return;
			searchDirectory(subdir);
		}
	}
	
	private void searchFile(File file, boolean checkext) throws JcsException
	{
		String filename = file.getName();
		String extension = TextUtil.GetFileExtension(filename);
		if (checkext&&!extensions.contains(extension))
		{
			return;
		}
		for (SearchHandlerInterface handler :  handlers)
		{
			if (handler.isHandlble(extension))
			{
				handler.handle(file, query, callback, extensions);
			}
		}
	}
	
	public void stop()
	{
		this.run = false;
	}
}