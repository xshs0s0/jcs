package com.s0s0.app.ui;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingUtilities;

import com.s0s0.app.search.ResultField;
import com.s0s0.app.search.SearchResult;
import com.s0s0.app.search.StringResultField;

public class SearchConsumerThread extends Thread {

	private ConcurrentLinkedQueue<SearchResult> resultqueue = null;
	private AtomicBoolean complete = null;
	private JList list = null;
	private DefaultListModel<String> listmodel = null;
	private JButton search_btn = null;
	private JLabel status_label = null;

	public SearchConsumerThread() {
		super();
	}

	public ConcurrentLinkedQueue<SearchResult> getResultqueue() {
		return resultqueue;
	}

	public void setResultqueue(ConcurrentLinkedQueue<SearchResult> resultqueue) {
		this.resultqueue = resultqueue;
	}

	public AtomicBoolean getComplete() {
		return complete;
	}

	public void setComplete(AtomicBoolean complete) {
		this.complete = complete;
	}

	public DefaultListModel<String> getListmodel() {
		return listmodel;
	}

	public void setListmodel(DefaultListModel<String> listmodel) {
		this.listmodel = listmodel;
	}

	public JButton getSearch_btn() {
		return search_btn;
	}

	public void setSearch_btn(JButton search_btn) {
		this.search_btn = search_btn;
	}

	public JLabel getStatus_label() {
		return status_label;
	}

	public void setStatus_label(JLabel status_label) {
		this.status_label = status_label;
	}

	public JList getList() {
		return list;
	}

	public void setList(JList list) {
		this.list = list;
	}

	public void run() {
		long starttime = System.currentTimeMillis();
		long runtime = 0;
		while (!complete.get()) // 30 seconds
		{
			SearchResult sr = null;
			sr = resultqueue.poll();
			if (sr != null) {
				String path = "";
				String filename = "";
				String classname = "";
				List<ResultField> resultfields = sr.getFields();
				for (ResultField rf : resultfields) {
					if (rf instanceof StringResultField) {
						if (rf.getName().equals("path")) {
							path = ((StringResultField) rf).getValue();
						} else if (rf.getName().equals("filename")) {
							filename = ((StringResultField) rf).getValue();
						} else if (rf.getName().equals("classname")) {
							classname = ((StringResultField) rf).getValue();
						}
					}
				}
				ListModelUpdateThread lt = new ListModelUpdateThread();
				lt.setValue(path + " : " + filename + " : " + classname);
				lt.setListmodel(listmodel);
				lt.setList(list);
				SwingUtilities.invokeLater(lt);
				//System.out.println(path + " : " + filename + " : " + classname);
				//listmodel.addElement(path + " : " + filename + " : " + classname);
			} else {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		runtime = System.currentTimeMillis() - starttime;
		search_btn.setText("Search");
		status_label.setText("status: search completed, spend " + runtime
				/ 1000 + " seconds");
		resultqueue.clear();
		complete.compareAndSet(true, false);
	}
}
