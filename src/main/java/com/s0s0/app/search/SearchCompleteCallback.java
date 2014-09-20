package com.s0s0.app.search;

import java.util.concurrent.atomic.AtomicBoolean;

public class SearchCompleteCallback {
	private AtomicBoolean complete;

	public void complete(){
		this.complete.compareAndSet(false, true);
	}

	public AtomicBoolean getComplete() {
		return complete;
	}

	public void setComplete(AtomicBoolean complete) {
		this.complete = complete;
	}
}