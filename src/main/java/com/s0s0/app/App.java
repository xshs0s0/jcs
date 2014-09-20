package com.s0s0.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import com.s0s0.app.log.LogManager;
import com.s0s0.app.search.ClassFileSearchHandler;
import com.s0s0.app.search.Query;
import com.s0s0.app.search.QueryTypeEnum;
import com.s0s0.app.search.QueuedSearchCallback;
import com.s0s0.app.search.ResultField;
import com.s0s0.app.search.SearchCompleteCallback;
import com.s0s0.app.search.SearchHandlerInterface;
import com.s0s0.app.search.SearchResult;
import com.s0s0.app.search.Searcher;
import com.s0s0.app.search.StringResultField;
import com.s0s0.app.search.ZipFormatFileSearchHandler;

/**
 * Hello world!
 *
 */
public class App 
{
	private static ConcurrentLinkedQueue<SearchResult> resultqueue = new ConcurrentLinkedQueue<SearchResult>();
	private static AtomicBoolean complete = new AtomicBoolean(false);
	
    public static void main( String[] args )
    {
    	 Thread t = new Thread(initSearcher());
    	 t.start();
    	 long starttime = System.currentTimeMillis();
    	 long runtime = 0;
    	 while (!complete.get()) // 30 seconds
    	 {
    		 SearchResult sr = null;
			//try {
				sr = resultqueue.poll();
			//} catch (InterruptedException e1) {
			//	e1.printStackTrace();
			//}
    		 if (sr != null)
    		 {
        		 List<ResultField> resultfields = sr.getFields();
        		 for (ResultField rf : resultfields)
        		 {
        			 System.out.println("OUTPUT:" + rf.getName());
        			 if (rf instanceof StringResultField)
        			 {
        				 System.out.println("OUTPUT:" + ((StringResultField) rf).getValue());
        			 }
        		 }
    		 } else
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
    		 runtime = System.currentTimeMillis() - starttime;
    	 }
		 System.out.println("OUTPUT: runtime = " + runtime);
    }
    
    public static Searcher initSearcher()
    {
    	Query query = new Query();
    	query.setRf(new ArrayList<String>(Arrays.asList("filename","path","classname")));
    	query.setType(QueryTypeEnum.CASE_INSENSITIVE_TEXT_MATCH);
    	query.setValue("callback");
    	
    	QueuedSearchCallback callback = new QueuedSearchCallback();
    	callback.setQueue(resultqueue);
    	
    	SearchCompleteCallback completecallback = new SearchCompleteCallback();
    	completecallback.setComplete(complete);
    	
    	String[] paths = new String[]{"C:\\hobby\\it\\jcs","c:\\work"};
    	List<String> extensions = new ArrayList<String>(Arrays.asList("class","jar","java"));
    	List<SearchHandlerInterface> handlers = new ArrayList<SearchHandlerInterface>(Arrays.asList(new ClassFileSearchHandler(), new ZipFormatFileSearchHandler()));
    	LogManager logger = null;
    	Searcher searcher = new Searcher(query, callback, completecallback, paths, extensions, handlers, logger);
    	return searcher;
    }
}
