package com.s0s0.app.ui;

import javax.swing.DefaultListModel;
import javax.swing.JList;

public class ListModelUpdateThread extends Thread {

	private String value;
	private JList list;
	private DefaultListModel<String> listmodel = null;
	
	public ListModelUpdateThread()
	{
		super();
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public DefaultListModel<String> getListmodel() {
		return listmodel;
	}

	public void setListmodel(DefaultListModel<String> listmodel) {
		this.listmodel = listmodel;
	}

	public JList getList() {
		return list;
	}

	public void setList(JList list) {
		this.list = list;
	}

	public void run()
	{
		listmodel.addElement(this.value);
		list.ensureIndexIsVisible(listmodel.getSize()-1);
	}
}
