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

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import system.Gebruiker;
import system.ReservationWizard2000;

public class GebruikerSelectorWindow
extends JFrame implements ActionListener, ListSelectionListener
{
	private static final long serialVersionUID = 1L;
	final Image RW2000Image = Toolkit.getDefaultToolkit().getImage("pictures/RW2000.png");
	private DelegatedObservable observer;
	private JPanel eigenschappenPanel;
	private JScrollPane gebruikersPane;
	private JList<String> gebruikersList;
	private JButton selecterenButton, annulerenButton, vernieuwenButton;
	private JLabel gebruikerLabel, gebruikernaamLabel, userLevelLabel;
	private ReservationWizard2000 RW2000;
	
	public GebruikerSelectorWindow(ReservationWizard2000 RW2000)
	{
		this.RW2000 = RW2000;
		this.observer = new DelegatedObservable();
		
		this.setLayout(null);
		
		Font font = new Font("Arial", Font.PLAIN, 20);
		
		eigenschappenPanel = new JPanel();
		eigenschappenPanel.setBorder(BorderFactory.createTitledBorder("Eigenschappen"));
		eigenschappenPanel.setBounds(343, 38, 402, 145);
		eigenschappenPanel.setLayout(null);
		
		gebruikersPane = new JScrollPane();
		gebruikersPane.setBounds(12, 46, 232, 372);
		
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
		
		gebruikersList = new JList<String>();
		gebruikersList.setFont(font);
		gebruikersList.setBounds(12, 46, 314, 372);
		
		gebruikerLabel = new JLabel("Gebruikers");
		gebruikerLabel.setFont(font);
		gebruikerLabel.setBounds(121, 17, 98, 25);
		
		gebruikernaamLabel = new JLabel("Naam: ");
		gebruikernaamLabel.setFont(font);
		gebruikernaamLabel.setBounds(8, 22, 385, 25);
		
		userLevelLabel = new JLabel("Permissieniveau: ");
		userLevelLabel.setFont(font);
		userLevelLabel.setBounds(8, 53, 304, 25);
		
		//Set GUI
		this.add(eigenschappenPanel);
		this.add(selecterenButton);
		this.add(annulerenButton);
		this.add(vernieuwenButton);
		this.add(gebruikersPane);
		this.add(gebruikerLabel);
				
		eigenschappenPanel.add(gebruikernaamLabel);
		eigenschappenPanel.add(userLevelLabel);
									
		//Actionlisteners
		selecterenButton.addActionListener(this);
		annulerenButton.addActionListener(this);
		vernieuwenButton.addActionListener(this);
		
		this.initialiseren();
		
		this.gebruikersInladen();
	}
	
	private void initialiseren()
	{
		this.setTitle("Gebruiker selecteren");
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setIconImage(RW2000Image);
		this.setSize(767, 536);
		this.setResizable(false);
		this.setLocation(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width/2 - this.getWidth()/2, GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height/2 - this.getHeight()/2);
		this.setAlwaysOnTop(true);
		this.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
		this.setUndecorated(true);
	}
	
	private void gebruikersInladen()
	{
		this.remove(gebruikersPane);
		ArrayList<Gebruiker> gebruikers = this.RW2000.gebruikersOpvragen();
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		Iterator<Gebruiker> iterator = gebruikers.iterator();
		while(iterator.hasNext())
		{
			Gebruiker gebruiker = iterator.next();
			listModel.addElement(gebruiker.naamOpvragen());
		}
		this.gebruikersList = new JList<String>(listModel);
		this.gebruikersList.setFont(new Font("Arial", Font.PLAIN, 18));
		this.gebruikersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.gebruikersList.setLayoutOrientation(JList.VERTICAL);
		this.gebruikersList.setVisibleRowCount(-1);
		this.gebruikersList.addListSelectionListener(this);
		this.gebruikersPane = new JScrollPane(gebruikersList);
		this.gebruikersPane.setBounds(12, 46, 314, 372);
		this.add(gebruikersPane);
		this.gebruikersList.updateUI();
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e)
	{
		if(e.getValueIsAdjusting() == false)
		{
			if(this.gebruikersList.getSelectedIndex() == -1)
			{
				this.gebruikernaamLabel.setText("Naam: ");
				this.userLevelLabel.setText("Permissieniveau: ");
			}
			else
			{
				String permissieniveau;
				Gebruiker gebruiker = this.RW2000.gebruikersOpvragen().get(this.gebruikersList.getSelectedIndex());
				this.gebruikernaamLabel.setText("Naam: " + gebruiker.naamOpvragen());
				switch(gebruiker.userLevelOpvragen())
				{
					case 1 : permissieniveau = "Gebruiker";
							 break;
					case 2 : permissieniveau = "Loket";
							 break;
					case 3 : permissieniveau = "Admin";
							 break;
					default : permissieniveau = "Gebruiker"; 
							  break;
				}
				this.userLevelLabel.setText("Permissieniveau: " + permissieniveau);
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
			if(this.gebruikersList.isSelectionEmpty() == false)
			{
				this.observer.setChanged();
				this.observer.notifyObservers(this.RW2000.gebruikersOpvragen().get(this.gebruikersList.getSelectedIndex()));
				this.setVisible(false);	
			}
		}
		else if(b == annulerenButton)
		{
			this.setVisible(false);
		}
		else if(b == vernieuwenButton)
		{
			this.gebruikernaamLabel.setText("Naam: ");
			this.userLevelLabel.setText("Permissieniveau: ");
			this.gebruikersInladen();
		}
	}
}
