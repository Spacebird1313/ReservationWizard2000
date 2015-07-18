package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import system.ReservationWizard2000;
import system.Voorstelling;

public class VoorstellingSelectorWindow extends JFrame implements ActionListener, ListSelectionListener
{
	private static final long serialVersionUID = 1L;
	final Image RW2000Image = Toolkit.getDefaultToolkit().getImage("pictures/RW2000.png");
	private DelegatedObservable observer;
	private JPanel eigenschappenPanel;
	private JScrollPane voorstellingenPane;
	private JList<String> voorstellingenList;
	private JButton selecterenButton, annulerenButton, vernieuwenButton;
	private JLabel voorstellingLabel, voorstellingsnaamLabel, locatieLabel, tijdLabel, duurLabel, beschikbaarheidLabel;
	private ReservationWizard2000 RW2000;
	
	public VoorstellingSelectorWindow(ReservationWizard2000 RW2000)
	{
		this.RW2000 = RW2000;
		this.observer = new DelegatedObservable();
		
		this.setLayout(null);
		
		Font font = new Font("Arial", Font.PLAIN, 20);
		
		eigenschappenPanel = new JPanel();
		eigenschappenPanel.setBorder(BorderFactory.createTitledBorder("Eigenschappen"));
		eigenschappenPanel.setBounds(343, 38, 402, 232);
		eigenschappenPanel.setLayout(null);
		
		voorstellingenPane = new JScrollPane();
		voorstellingenPane.setBounds(12, 46, 232, 372);
		
		selecterenButton = new JButton("Selecteren");
		selecterenButton.setFont(font);
		selecterenButton.setBackground(Color.lightGray);
		selecterenButton.setBounds(343, 282, 402, 97);
		
		annulerenButton = new JButton("Annuleren");
		annulerenButton.setFont(font);
		annulerenButton.setBackground(Color.lightGray);
		annulerenButton.setBounds(343, 389, 402, 97);
		
		vernieuwenButton = new JButton("Vernieuwen");
		vernieuwenButton.setFont(font);
		vernieuwenButton.setBackground(Color.lightGray);
		vernieuwenButton.setBounds(12, 423, 314, 63);
		
		voorstellingenList = new JList<String>();
		voorstellingenList.setFont(font);
		voorstellingenList.setBounds(12, 46, 314, 372);
		
		voorstellingLabel = new JLabel("Voorstellingen");
		voorstellingLabel.setFont(font);
		voorstellingLabel.setBounds(109, 17, 124, 25);
		
		voorstellingsnaamLabel = new JLabel("Voorstelling: ");
		voorstellingsnaamLabel.setFont(font);
		voorstellingsnaamLabel.setBounds(13, 27, 389, 25);
		
		locatieLabel = new JLabel("Locatie: ");
		locatieLabel.setFont(font);
		locatieLabel.setBounds(49, 65, 350, 25);
		
		tijdLabel = new JLabel("Tijd: ");
		tijdLabel.setFont(font);
		tijdLabel.setBounds(82, 102, 321, 25);
		
		duurLabel = new JLabel("Duur: ");
		duurLabel.setFont(font);
		duurLabel.setBounds(72, 140, 330, 25);
		
		beschikbaarheidLabel = new JLabel("<html>Beschikbaar: </html>");
		beschikbaarheidLabel.setFont(font);
		beschikbaarheidLabel.setBounds(5, 175, 390, 25);
		
		//Set GUI
		this.add(eigenschappenPanel);
		this.add(selecterenButton);
		this.add(annulerenButton);
		this.add(vernieuwenButton);
		this.add(voorstellingenPane);
		this.add(voorstellingLabel);
				
		eigenschappenPanel.add(voorstellingsnaamLabel);
		eigenschappenPanel.add(locatieLabel);
		eigenschappenPanel.add(tijdLabel);
		eigenschappenPanel.add(duurLabel);
		eigenschappenPanel.add(beschikbaarheidLabel);
									
		//Actionlisteners
		selecterenButton.addActionListener(this);
		annulerenButton.addActionListener(this);
		vernieuwenButton.addActionListener(this);
		
		this.initialiseren();
		
		this.voorstellingenInladen();
	}
	
	private void initialiseren()
	{
		this.setTitle("Voorstelling selecteren");
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setIconImage(RW2000Image);
		this.setSize(767, 536);
		this.setResizable(false);
		this.setLocation(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width/2 - this.getWidth()/2, GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height/2 - this.getHeight()/2);
		this.setAlwaysOnTop(true);
		this.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
		this.setUndecorated(true);
	}
	
	private void voorstellingenInladen()
	{
		this.remove(voorstellingenPane);
		ArrayList<Voorstelling> voorstellingen = this.RW2000.voorstellingenOpvragen();
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		Iterator<Voorstelling> iterator = voorstellingen.iterator();
		while(iterator.hasNext())
		{
			Voorstelling voorstelling = iterator.next();
			listModel.addElement(voorstelling.voorstellingsNaamOpvragen() + " - " + voorstelling.tijdOpvragen());
		}
		this.voorstellingenList = new JList<String>(listModel);
		this.voorstellingenList.setFont(new Font("Arial", Font.PLAIN, 18));
		this.voorstellingenList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.voorstellingenList.setLayoutOrientation(JList.VERTICAL);
		this.voorstellingenList.setVisibleRowCount(-1);
		this.voorstellingenList.addListSelectionListener(this);
		this.voorstellingenPane = new JScrollPane(voorstellingenList);
		this.voorstellingenPane.setBounds(12, 46, 314, 372);
		this.add(voorstellingenPane);
		this.voorstellingenList.updateUI();
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e)
	{
		if(e.getValueIsAdjusting() == false)
		{
			if(this.voorstellingenList.getSelectedIndex() == -1)
			{
				this.voorstellingsnaamLabel.setText("Voorstelling: ");
				this.locatieLabel.setText("Locatie: ");
				this.tijdLabel.setText("Tijd: ");
				this.duurLabel.setText("Duur: ");
				this.beschikbaarheidLabel.setText("<html>Beschikbaar: </html>");
			}
			else
			{
				Voorstelling voorstelling = this.RW2000.voorstellingenOpvragen().get(this.voorstellingenList.getSelectedIndex());
				this.voorstellingsnaamLabel.setText("Voorstelling: " + voorstelling.voorstellingsNaamOpvragen());
				this.locatieLabel.setText("Locatie: " + voorstelling.zaalOpvragen().locatieOpvragen());
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
	}

	public Observable observerableOpvragen()
	{
		return observer;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		JButton b = (JButton)e.getSource();
		if(b == selecterenButton)
		{
			if(this.voorstellingenList.isSelectionEmpty() == false)
			{
				this.observer.setChanged();
				this.observer.notifyObservers(this.RW2000.voorstellingenOpvragen().get(this.voorstellingenList.getSelectedIndex()));
				this.setVisible(false);	
			}
		}
		else if(b == annulerenButton)
		{
			this.setVisible(false);
		}
		else if(b == vernieuwenButton)
		{
			this.voorstellingenInladen();
			this.voorstellingsnaamLabel.setText("Voorstelling: ");
			this.locatieLabel.setText("Locatie: ");
			this.tijdLabel.setText("Tijd: ");
			this.duurLabel.setText("Duur: ");
			this.beschikbaarheidLabel.setText("<html>Beschikbaar: </html>");
		}
	}
}
