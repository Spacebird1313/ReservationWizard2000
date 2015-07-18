package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class BackgroundFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	public BackgroundFrame()
	{
		this.initialiseren();
	}
	private void initialiseren()
	{
		Image RW2000Image = Toolkit.getDefaultToolkit().getImage("pictures/RW2000.png");
		this.setTitle("Reservation Wizard 2000 - The Ultimate Reservation Experience");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setIconImage(RW2000Image);
		this.setSize(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width, GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height);
		this.setLocation(0, 0);
		this.setUndecorated(true);
		this.setResizable(false);
		this.setType(JFrame.Type.UTILITY);
		this.setLayout(new BorderLayout());		
		JLabel background = new JLabel(new ImageIcon("pictures/BGRW2000.png"));
		JLabel titelLabel = new JLabel("Reservation Wizard 2000");
		titelLabel.setFont(new Font("Arial", Font.PLAIN, 40));
		titelLabel.setForeground(Color.lightGray);
		titelLabel.setHorizontalAlignment(JLabel.CENTER);
		this.add(background, BorderLayout.CENTER);
		this.add(titelLabel, BorderLayout.PAGE_START);
		this.add(new JLabel("Arthur Janssens & Thomas Huybrechts - Copyright © 2013"), BorderLayout.PAGE_END);
	}
}
