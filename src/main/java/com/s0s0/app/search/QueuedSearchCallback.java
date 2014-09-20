package com.s0s0.app.search;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.s0s0.app.exception.JcsException;

public class QueuedSearchCallback implements SearchCallbackInterface {

	private ConcurrentLinkedQueue<SearchResult> queue;
	
	public QueuedSearchCallback()
	{
	}
	
	public void callback(SearchResult result) throws JcsException {
		queue.add(result);
	}

	public ConcurrentLinkedQueue<SearchResult> getQueue() {
		return queue;
	}

	public void setQueue(ConcurrentLinkedQueue<SearchResult> queue) {
		this.queue = queue;
	}
}