package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;

import system.Voorstelling;

public class VoorstellingPropertiesWindow extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	final Image RW2000Image = Toolkit.getDefaultToolkit().getImage("pictures/RW2000.png");
	private JPanel eigenschappenPanel;
	private JButton sluitButton;
	private JLabel voorstellingsnaamLabel, locatieLabel, zaalLabel, tijdLabel, duurLabel, beschikbaarheidLabel;
	
	public VoorstellingPropertiesWindow()
	{
		this.setLayout(null);
		
		Font font = new Font("Arial", Font.PLAIN, 20);
		
		eigenschappenPanel = new JPanel();
		eigenschappenPanel.setBorder(BorderFactory.createTitledBorder("Eigenschappen"));
		eigenschappenPanel.setBounds(12, 12, 496, 281);
		eigenschappenPanel.setLayout(null);
		
		sluitButton = new JButton("Sluiten");
		sluitButton.setFont(font);
		sluitButton.setBackground(Color.lightGray);
		sluitButton.setBounds(134, 299, 247, 45);
		
		voorstellingsnaamLabel = new JLabel("Voorstelling: ");
		voorstellingsnaamLabel.setFont(font);
		voorstellingsnaamLabel.setBounds(25, 28, 389, 25);
		
		locatieLabel = new JLabel("Locatie: ");
		locatieLabel.setFont(font);
		locatieLabel.setBounds(62, 70, 350, 25);
		
		zaalLabel = new JLabel("Zaal: ");
		zaalLabel.setFont(font);
		zaalLabel.setBounds(88, 109, 350, 25);
		
		tijdLabel = new JLabel("Tijd: ");
		tijdLabel.setFont(font);
		tijdLabel.setBounds(94, 150, 321, 25);
		
		duurLabel = new JLabel("Duur: ");
		duurLabel.setFont(font);
		duurLabel.setBounds(82, 190, 330, 25);
		
		beschikbaarheidLabel = new JLabel("<html>Beschikbaar: </html>");
		beschikbaarheidLabel.setFont(font);
		beschikbaarheidLabel.setBounds(16, 231, 390, 25);
		
		//Set GUI
		this.add(eigenschappenPanel);
		this.add(sluitButton);
		
		eigenschappenPanel.add(voorstellingsnaamLabel);
		eigenschappenPanel.add(locatieLabel);
		eigenschappenPanel.add(zaalLabel);
		eigenschappenPanel.add(tijdLabel);
		eigenschappenPanel.add(duurLabel);
		eigenschappenPanel.add(beschikbaarheidLabel);
		
		//Actionlisteners
		sluitButton.addActionListener(this);
		
		this.initialiseren();
	}
	
	private void initialiseren()
	{
		this.setTitle("Voorstellingseigenschappen");
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setIconImage(RW2000Image);
		this.setSize(530, 389);
		this.setResizable(false);
		this.setLocation(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width/2 - this.getWidth()/2, GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height/2 - this.getHeight()/2);
		this.setAlwaysOnTop(true);
		this.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
		this.setUndecorated(true);
	}
	
	public void voorstellingLaden(Voorstelling voorstelling)
	{
		if(voorstelling != null)
		{
			this.voorstellingsnaamLabel.setText("Voorstelling: " + voorstelling.voorstellingsNaamOpvragen());
			this.locatieLabel.setText("Locatie: " + voorstelling.zaalOpvragen().locatieOpvragen());
			this.zaalLabel.setText("Zaal: " + voorstelling.zaalOpvragen().zaalNaamOpvragen());
			this.tijdLabel.setText("Tijd: " + voorstelling.tijdOpvragen());
			this.duurLabel.setText("Duur: " + voorstelling.duurOpvragen());
			if(voorstelling.zaalOpvragen().beschikbaarheidOpvragen() == 0)
			{
				this.beschikbaarheidLabel.setText("<html>Beschikbaar: <font color=\"red\">Volzet</html>");
			}
			else
			{
			this.beschikbaarheidLabel.setText("<html>Beschikbaar: <font color=\"green\">" + voorstelling.zaalOpvragen().beschikbaarheidOpvragen() + "</html>");
			}	
		}
	}

	public void actionPerformed(ActionEvent e)
	{
		JButton b = (JButton)e.getSource();
		if(b == sluitButton)
		{
			this.setVisible(false);
		}
	}
}
