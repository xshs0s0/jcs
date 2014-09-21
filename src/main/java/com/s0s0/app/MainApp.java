package com.s0s0.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import com.s0s0.app.search.SearchResult;
import com.s0s0.app.ui.AboutDialog;

public class MainApp extends JFrame implements ActionListener {

	private static final int TOP_PANEL_HEIGHT = 80;
	
	private static ConcurrentLinkedQueue<SearchResult> resultqueue = new ConcurrentLinkedQueue<SearchResult>();
	private static AtomicBoolean complete = new AtomicBoolean(false);
	
	private JMenuBar menubar = new JMenuBar();
	
	private JMenu file_menu = new JMenu("File");
	private JMenu help_menu = new JMenu("Help");
	
	private JMenuItem loadprofile_menuitem = new JMenuItem("Load Profile");
	private JMenuItem saveprofile_menuitem = new JMenuItem("Save Profile");
	private JMenuItem saveasprofile_menuitem = new JMenuItem("Save Profile As");
	private JMenuItem exit_menuitem = new JMenuItem("Exit");
	private JMenuItem about_menuitem = new JMenuItem("About");
	
	private JPanel top_panel = new JPanel();
	private JPanel center_panel = new JPanel();
	private JPanel bottom_panel = new JPanel();
	
	private DefaultListModel<String> path_listmodel = new DefaultListModel<String>();
	private JList path_list = new JList(path_listmodel);
	
	public MainApp()
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Java Class Searcher");
		this.setMinimumSize(new Dimension(660, 600));
		this.setSize(660, 600);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		this.setJMenuBar(menubar);
		menubar.add(file_menu);
		menubar.add(help_menu);
		file_menu.add(loadprofile_menuitem);
		file_menu.add(saveprofile_menuitem);
		file_menu.add(saveasprofile_menuitem);
		file_menu.addSeparator();
		file_menu.add(exit_menuitem);
		help_menu.add(about_menuitem);

		loadprofile_menuitem.setMnemonic(KeyEvent.VK_L);
		loadprofile_menuitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
		loadprofile_menuitem.addActionListener(this);
		saveprofile_menuitem.setMnemonic(KeyEvent.VK_S);
		saveprofile_menuitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		saveprofile_menuitem.addActionListener(this);
		saveasprofile_menuitem.setMnemonic(KeyEvent.VK_A);
		saveasprofile_menuitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.ALT_MASK));
		saveasprofile_menuitem.addActionListener(this);
		exit_menuitem.addActionListener(this);
		about_menuitem.addActionListener(this);
		
		this.getContentPane().setLayout(new BorderLayout());
		//top_panel.setBackground(Color.YELLOW);
		center_panel.setBackground(Color.CYAN);
		bottom_panel.setBackground(Color.WHITE);
		this.getContentPane().add(top_panel, BorderLayout.NORTH);
		this.getContentPane().add(center_panel, BorderLayout.CENTER);
		this.getContentPane().add(bottom_panel, BorderLayout.SOUTH);
		
		top_panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.VERTICAL;
		c.anchor = GridBagConstraints.WEST;
		c.weightx = 0;
		c.insets = new Insets(10, 2, 0, 6);
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new BorderLayout());
		labelPanel.add(new JLabel(" Search Paths: "), BorderLayout.NORTH);
		top_panel.add(labelPanel,c);
		
		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 0, 0);
		path_listmodel.addElement("path 1");
		path_listmodel.addElement("path 2");
		path_listmodel.addElement("path 3");
		JScrollPane path_scrollpane = new JScrollPane(path_list);
		path_scrollpane.setPreferredSize(new Dimension(200, TOP_PANEL_HEIGHT));
		top_panel.add(path_scrollpane,c);
		
		c.gridx = 2;
		c.gridy = 0;
		JPanel pathbuttonPanel = new JPanel();
		pathbuttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JButton addpath_btn = new JButton("Add");
		JButton removepath_btn = new JButton("Remove");
		pathbuttonPanel.add(addpath_btn);
		pathbuttonPanel.add(removepath_btn);
		pathbuttonPanel.setPreferredSize(new Dimension(90, TOP_PANEL_HEIGHT));
		top_panel.add(pathbuttonPanel, c);

		c.gridx = 3;
		c.gridy = 0;
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		searchPanel.setPreferredSize(new Dimension(250, TOP_PANEL_HEIGHT));
		//searchPanel.setBackground(Color.RED);
		JTextField query_field = new JTextField("");
		query_field.setPreferredSize(new Dimension(160,24));
		JButton search_btn = new JButton("search");
		JCheckBox regex_checkbox = new JCheckBox("Regex");
		JCheckBox casesensitive_checkbox = new JCheckBox("Case Sensitive");
		searchPanel.add(query_field);
		searchPanel.add(search_btn);
		searchPanel.add(regex_checkbox);
		searchPanel.add(casesensitive_checkbox);
		top_panel.add(searchPanel, c);
		
		// fill the rest area
		c.gridx = 4;
		c.gridy = 0;
		c.weightx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		top_panel.add(new JPanel(), c);
	}
	
    public static void main( String[] args )
    {
		MainApp mainapp = new MainApp();
		mainapp.pack();
		mainapp.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == loadprofile_menuitem )
		{
			System.out.println("load profile");
		} else if (e.getSource() == saveprofile_menuitem )
		{
			System.out.println("save profile");
		} else if (e.getSource() == saveasprofile_menuitem )
		{
			System.out.println("save profile as");
		} else if (e.getSource() == exit_menuitem )
		{
			this.setVisible(false);
			this.dispose();
			System.exit(1);
		} else if (e.getSource() == about_menuitem )
		{
			AboutDialog dialog = new AboutDialog(this);
			System.out.println("22");
			dialog.setVisible(true);
		}
	}
}