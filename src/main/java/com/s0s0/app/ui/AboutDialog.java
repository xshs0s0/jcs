package com.s0s0.app.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AboutDialog extends JDialog {

	public AboutDialog(JFrame parent)
	{
		super(parent, "About Dialog", true);
		
		Box b = Box.createVerticalBox();
	    b.add(Box.createGlue());
	    b.add(new JLabel("      Java Class Searcher V1.0"));
	    b.add(new JLabel("      By xshs0s0@gmail.com"));
	    b.add(Box.createGlue());
	    getContentPane().add(b, "Center");

	    JPanel p2 = new JPanel();
	    JButton ok = new JButton("Ok");
	    p2.add(ok);
	    getContentPane().add(p2, "South");

	    ok.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent evt) {
	        setVisible(false);
	      }
	    });

	    setSize(250, 150);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	}
}
