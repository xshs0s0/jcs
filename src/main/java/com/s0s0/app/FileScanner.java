package com.s0s0.app;

import java.util.AbstractQueue;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.s0s0.app.search.Query;
import com.s0s0.app.search.SearchPath;
import com.s0s0.app.search.SearchResult;

public class FileScanner implements Runnable {
	
	private int threadnumber = 5;
	ExecutorService executor;
	private Query query;
	private List<SearchPath> paths;
	private AbstractQueue<SearchResult> queue;
	private boolean run = true;
	
	public FileScanner()
	{
	}
	
	public void run()
	{
		if (run)
		{
			executor = Executors.newFixedThreadPool(threadnumber);
			for (SearchPath path : paths)
			{
				
			}
			
		}
		while (run)
		{
			
		}
	}
	
	public void shutdown()
	{
		this.run = false;
	}
}