package com.s0s0.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.s0s0.app.exception.JcsException;
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
import com.s0s0.app.ui.AboutDialog;
import com.s0s0.app.ui.JcsContext;
import com.s0s0.app.ui.SearchConsumerThread;
import com.s0s0.app.ui.SearchProfile;
import com.s0s0.app.ui.SearchProfileFile;

public class MainApp extends JFrame implements ActionListener, MouseListener {

	private static final int TOP_PANEL_HEIGHT = 80;
	
	private static ConcurrentLinkedQueue<SearchResult> resultqueue = new ConcurrentLinkedQueue<SearchResult>();
	private static AtomicBoolean complete = new AtomicBoolean(false);
	
	private JMenuBar menubar = new JMenuBar();
	
	private JMenu file_menu = new JMenu("File");
	private JMenu help_menu = new JMenu("Help");
	
	private JMenuItem loadprofile_menuitem = new JMenuItem("Load Profile");
	private JMenuItem saveprofile_menuitem = new JMenuItem("Save Profile");
	private JMenuItem saveasprofile_menuitem = new JMenuItem("Save Profile As");
	private JMenuItem resetprofile_menuitem = new JMenuItem("Reset Profile");
	private JMenuItem exit_menuitem = new JMenuItem("Exit");
	private JMenuItem about_menuitem = new JMenuItem("About");
	
	private JPanel top_panel = new JPanel();
	private JPanel center_panel = new JPanel();
	private JPanel bottom_panel = new JPanel();
	
	private DefaultListModel<String> path_listmodel = new DefaultListModel<String>();
	private JList path_list = new JList(path_listmodel);
	private JFileChooser profile_filechooser = new JFileChooser();
	private JFileChooser path_filechooser = new JFileChooser();
	
	private JButton addpath_btn = new JButton("Add");
	private JButton removepath_btn = new JButton("Remove");
	
	private JTextField query_field = new JTextField("");
	JButton search_btn = new JButton("Search");
	private JCheckBox regex_checkbox = new JCheckBox("Regex");
	private JCheckBox casesensitive_checkbox = new JCheckBox("Case Sensitive");
	JButton clear_btn = new JButton("Clear");
	private DefaultListModel<String> searchresult_listmodel = new DefaultListModel<String>();
	private JList searchresult_list = new JList(searchresult_listmodel);
	private JPopupMenu searchresult_popupmenu = new JPopupMenu();
	private JMenuItem open_menuitem = new JMenuItem("Open File Location");
	private JMenuItem copy_menuitem = new JMenuItem("Copy File Location");

	private JLabel status_label = new JLabel("status:");
	
	private JcsContext context = new JcsContext();
	private Searcher searcher = null;
	
	public MainApp()
	{
		profile_filechooser.setAcceptAllFileFilterUsed(false);
		profile_filechooser.addChoosableFileFilter(new FileNameExtensionFilter("Jcs Profile file", "jcs"));
		path_filechooser.setAcceptAllFileFilterUsed(false);
		path_filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
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
		file_menu.add(resetprofile_menuitem);
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
		resetprofile_menuitem.setMnemonic(KeyEvent.VK_R);
		resetprofile_menuitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		resetprofile_menuitem.addActionListener(this);
		
		exit_menuitem.addActionListener(this);
		about_menuitem.addActionListener(this);
		
		this.getContentPane().setLayout(new BorderLayout());
		//top_panel.setBackground(Color.YELLOW);
		//center_panel.setBackground(Color.CYAN);
		//bottom_panel.setBackground(Color.WHITE);
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
		JScrollPane path_scrollpane = new JScrollPane(path_list);
		path_scrollpane.setPreferredSize(new Dimension(200, TOP_PANEL_HEIGHT));
		top_panel.add(path_scrollpane,c);
		
		c.gridx = 2;
		c.gridy = 0;
		JPanel pathbuttonPanel = new JPanel();
		pathbuttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		pathbuttonPanel.add(addpath_btn);
		pathbuttonPanel.add(removepath_btn);
		pathbuttonPanel.setPreferredSize(new Dimension(90, TOP_PANEL_HEIGHT));
		top_panel.add(pathbuttonPanel, c);
		addpath_btn.addActionListener(this);
		removepath_btn.addActionListener(this);

		c.gridx = 3;
		c.gridy = 0;
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		searchPanel.setPreferredSize(new Dimension(250, TOP_PANEL_HEIGHT));
		//searchPanel.setBackground(Color.RED);
		query_field.setPreferredSize(new Dimension(160,24));
		search_btn.addActionListener(this);
		clear_btn.addActionListener(this);
		searchPanel.add(query_field);
		searchPanel.add(search_btn);
		searchPanel.add(regex_checkbox);
		searchPanel.add(casesensitive_checkbox);
		searchPanel.add(clear_btn);
		top_panel.add(searchPanel, c);
		
		// fill the rest area
		c.gridx = 4;
		c.gridy = 0;
		c.weightx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		top_panel.add(new JPanel(), c);
		
		center_panel.setLayout(new BorderLayout());
		center_panel.add(new JScrollPane(searchresult_list));
		searchresult_list.addMouseListener(this);
		searchresult_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		searchresult_popupmenu.add(open_menuitem);
		searchresult_popupmenu.add(copy_menuitem);
		open_menuitem.addActionListener(this);
		copy_menuitem.addActionListener(this);
		searchresult_list.setComponentPopupMenu(searchresult_popupmenu);
		
		bottom_panel.setLayout(new BorderLayout());
		bottom_panel.add(status_label, BorderLayout.CENTER);
	}
	
    public static void main( String[] args )
    {
		MainApp mainapp = new MainApp();
		mainapp.pack();
		mainapp.setVisible(true);
	}
    
    private void updateUI(SearchProfile profile)
    {
    	path_listmodel.clear();
    	for (String path : profile.getPaths())
    	{
    		path_listmodel.addElement(path);
    	}
    	query_field.setText(profile.getQuery());
    	regex_checkbox.setSelected(profile.isRegex());
    	casesensitive_checkbox.setSelected(profile.isCasesensitive());
    }
    
    private SearchProfile getSearchProfile()
    {
    	SearchProfile profile = new SearchProfile();
    	profile.setQuery(query_field.getText().trim());
    	profile.setCasesensitive(casesensitive_checkbox.isSelected());
    	profile.setRegex(regex_checkbox.isSelected());
    	profile.setPaths(new ArrayList<String>());
    	for (int i=0; i<path_listmodel.size(); i++)
    	{
    		profile.getPaths().add(path_listmodel.elementAt(i));
    	}
		return profile;
    }
    
    private Searcher initSearcher()
    {
    	Query query = new Query();
    	query.setRf(new ArrayList<String>(Arrays.asList("filename","path","classname")));
    	if (casesensitive_checkbox.isSelected())
    	{
    		if (regex_checkbox.isSelected())
    			query.setType(QueryTypeEnum.CASE_SENSITIVE_REGEX_MATCH);
    		else
    			query.setType(QueryTypeEnum.CASE_SENSITIVE_TEXT_MATCH);
    	} else
    	{
    		if (regex_checkbox.isSelected())
    			query.setType(QueryTypeEnum.CASE_INSENSITIVE_REGEX_MATCH);
    		else
    			query.setType(QueryTypeEnum.CASE_INSENSITIVE_TEXT_MATCH);
    	}
    	query.setValue(query_field.getText().trim());
    	
    	QueuedSearchCallback callback = new QueuedSearchCallback();
    	callback.setQueue(resultqueue);
    	
    	SearchCompleteCallback completecallback = new SearchCompleteCallback();
    	completecallback.setComplete(complete);
    	String[] paths = new String[path_listmodel.size()];
    	for (int i=0; i<path_listmodel.size(); i++)
    	{
    		paths[i] = path_listmodel.elementAt(i);
    	}
    	List<String> extensions = new ArrayList<String>(Arrays.asList("class","jar","java"));
    	List<SearchHandlerInterface> handlers = new ArrayList<SearchHandlerInterface>(Arrays.asList(new ClassFileSearchHandler(), new ZipFormatFileSearchHandler()));
    	LogManager logger = null;
    	Searcher searcher = new Searcher(query, callback, completecallback, paths, extensions, handlers, logger);
    	return searcher;
    }
    
    private void search()
    {
    	searcher = initSearcher();
    	searcher.start();
		SearchConsumerThread t = new SearchConsumerThread();
		t.setResultqueue(resultqueue);
		t.setComplete(complete);
		t.setList(searchresult_list);
		t.setListmodel(searchresult_listmodel);
		t.setSearch_btn(search_btn);
		t.setStatus_label(status_label);
		t.start();
    }
    
    private String extractFilePath(String searchresult)
    {
    	if (searchresult != null)
    	{
    		String[] parts = searchresult.split(" : ");
        	if (parts.length>0)
        	{
        		return parts[0].trim();
        	}
    	}
    	return "";
    }

	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == loadprofile_menuitem )
		{
			int retrunVal = profile_filechooser.showOpenDialog(this);
			if (retrunVal == JFileChooser.APPROVE_OPTION)
			{
				File file = profile_filechooser.getSelectedFile();
				context.setProfilefilepath(file.getAbsolutePath());
				SearchProfile profile = null;
				try {
					profile = SearchProfileFile.load(file.getAbsolutePath());
				} catch (JcsException e1) {
					JOptionPane.showMessageDialog(this, "Load profile file failed.");
				}
				if (profile != null)
				{
					updateUI(profile);
					status_label.setText("status: profile loaded, filepath = " + file.getAbsolutePath());
				}
			}
		} else if (e.getSource() == saveprofile_menuitem )
		{
			if (context.getProfilefilepath()!=null)
			{
				try {
					
					SearchProfileFile.save(getSearchProfile(), context.getProfilefilepath());
					status_label.setText("status: profile saved, filepath = " + context.getProfilefilepath());
				} catch (JcsException e1) {
					JOptionPane.showMessageDialog(this, "Save profile file failed.");
				}
			} else
			{
				int retrunVal = profile_filechooser.showSaveDialog(this);
				String filepath = null; 
				if (retrunVal == JFileChooser.APPROVE_OPTION)
				{
					File file = profile_filechooser.getSelectedFile();
					filepath = file.getAbsolutePath();
					if (!filepath.endsWith(".jcs"))
					{
						filepath += ".jcs";
					}
					try {
						SearchProfileFile.save(getSearchProfile(), filepath);
					} catch (JcsException e1) {
						JOptionPane.showMessageDialog(this, "Save profile file failed.");
					}
					if (filepath!=null)
					{
						context.setProfilefilepath(filepath);
						status_label.setText("status: profile saved, filepath = " + context.getProfilefilepath());
					}
				}
			}
		} else if (e.getSource() == saveasprofile_menuitem )
		{
			int retrunVal = profile_filechooser.showSaveDialog(this);
			String filepath = null; 
			if (retrunVal == JFileChooser.APPROVE_OPTION)
			{
				File file = profile_filechooser.getSelectedFile();
				filepath = file.getAbsolutePath();
				if (!filepath.endsWith(".jcs"))
				{
					filepath += ".jcs";
				}
				if (filepath.equals(context.getProfilefilepath()))
				{
					int n = JOptionPane.showConfirmDialog(this, "The file already exists, do you want to overwrite?", "Confirm", JOptionPane.YES_NO_OPTION);
					if (n == JOptionPane.NO_OPTION)
					{
						return;
					}
				}
				try {
					SearchProfileFile.save(getSearchProfile(), filepath);
				} catch (JcsException e1) {
					JOptionPane.showMessageDialog(this, "Save profile file failed.");
				}
				if (filepath!=null)
				{
					context.setProfilefilepath(filepath);
					status_label.setText("status: profile saved, filepath = " + context.getProfilefilepath());
				}
			}
		} else if (e.getSource() == resetprofile_menuitem )
		{
			context.setProfilefilepath(null);
			path_listmodel.clear();
			query_field.setText("");
			regex_checkbox.setSelected(false);
			casesensitive_checkbox.setSelected(false);
			status_label.setText("status: reset completed.");
		} else if (e.getSource() == exit_menuitem )
		{
			this.setVisible(false);
			this.dispose();
			System.exit(1);
		} else if (e.getSource() == about_menuitem )
		{
			AboutDialog dialog = new AboutDialog(this);
			dialog.setVisible(true);
		} else if (e.getSource() == addpath_btn )
		{
			int retrunVal = path_filechooser.showOpenDialog(this);
			if (retrunVal == JFileChooser.APPROVE_OPTION)
			{
				File dir = path_filechooser.getSelectedFile();
				if (!path_listmodel.contains(dir.getAbsolutePath()))
				{
					path_listmodel.addElement(dir.getAbsolutePath());	
				}
			}
		} else if (e.getSource() == removepath_btn )
		{
			int selected = path_list.getSelectedIndex();
			if (selected != -1)
			{
				path_listmodel.remove(selected);			
			}
		} else if (e.getSource() == clear_btn )
		{
			searchresult_listmodel.clear();
			status_label.setText("status: clear search results");
		} else if (e.getActionCommand().equals("Search"))
		{
			if (path_listmodel.size()>0)
	    	{
				if (!query_field.getText().trim().equals(""))
				{
					status_label.setText("status: search in progress...");
					search_btn.setText("Stop");
					searchresult_listmodel.clear();
					search();
				} else
				{
					JOptionPane.showMessageDialog(this, "Please enter a valid query.");
				}
	    	} else
	    	{
	    		JOptionPane.showMessageDialog(this, "No search path selected.");
	    	}
		} else if (e.getActionCommand().equals("Stop"))
		{
			if (searcher != null)
				searcher.shutdown();
			search_btn.setText("Search");
		} else if (e.getSource() == open_menuitem)
		{
			String filepath = extractFilePath((String)searchresult_list.getSelectedValue());
			if (!filepath.equals(""))
			{
				try {
					Desktop.getDesktop().open(new File(filepath));
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(this, "Path '" + filepath + "' can not be opened");
				}
			}
		} else if (e.getSource() == copy_menuitem)
		{
			String filepath = extractFilePath((String)searchresult_list.getSelectedValue());
			if (!filepath.equals(""))
			{
				StringSelection stringSelection = new StringSelection (filepath);
				Clipboard clpbrd = Toolkit.getDefaultToolkit ().getSystemClipboard ();
				clpbrd.setContents(stringSelection, null);
			}
		}
	}
	
	public void mousePressed(MouseEvent e) {
		if (e.getModifiers() == InputEvent.META_MASK)
		{
			if (searchresult_listmodel.size()>0)
			{
				int idx = searchresult_list.locationToIndex(e.getPoint());
				if (idx >= 0)
				{
					searchresult_list.setSelectedIndex(idx);
				}
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}
}