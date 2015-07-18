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

import system.ReservationWizard2000;
import system.Zaal;

public class ZaalSelectorWindow extends JFrame implements ActionListener, ListSelectionListener
{
	private static final long serialVersionUID = 1L;
	final Image RW2000Image = Toolkit.getDefaultToolkit().getImage("pictures/RW2000.png");
	private DelegatedObservable observer;
	private JPanel eigenschappenPanel;
	private JScrollPane zalenPane;
	private JList<String> zalenList;
	private JButton selecterenButton, annulerenButton, vernieuwenButton;
	private JLabel zaalLabel, zaalnaamLabel, locatieLabel, capaciteitLabel;
	private ReservationWizard2000 RW2000;
	
	public ZaalSelectorWindow(ReservationWizard2000 RW2000)
	{
		this.RW2000 = RW2000;
		this.observer = new DelegatedObservable();
		
		this.setLayout(null);
		
		Font font = new Font("Arial", Font.PLAIN, 20);
		
		eigenschappenPanel = new JPanel();
		eigenschappenPanel.setBorder(BorderFactory.createTitledBorder("Eigenschappen"));
		eigenschappenPanel.setBounds(343, 38, 402, 145);
		eigenschappenPanel.setLayout(null);
		
		zalenPane = new JScrollPane();
		zalenPane.setBounds(12, 46, 232, 372);
		
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
		
		zalenList = new JList<String>();
		zalenList.setFont(font);
		zalenList.setBounds(12, 46, 314, 372);
		
		zaalLabel = new JLabel("Zalen");
		zaalLabel.setFont(font);
		zaalLabel.setBounds(141, 17, 57, 25);
		
		zaalnaamLabel = new JLabel("Zaal: ");
		zaalnaamLabel.setFont(font);
		zaalnaamLabel.setBounds(62, 27, 389, 25);
		
		locatieLabel = new JLabel("Locatie: ");
		locatieLabel.setFont(font);
		locatieLabel.setBounds(36, 65, 350, 25);
		
		capaciteitLabel = new JLabel("Capaciteit: ");
		capaciteitLabel.setFont(font);
		capaciteitLabel.setBounds(13, 102, 321, 25);
		
		//Set GUI
		this.add(eigenschappenPanel);
		this.add(selecterenButton);
		this.add(annulerenButton);
		this.add(vernieuwenButton);
		this.add(zalenPane);
		this.add(zaalLabel);
				
		eigenschappenPanel.add(zaalnaamLabel);
		eigenschappenPanel.add(locatieLabel);
		eigenschappenPanel.add(capaciteitLabel);
									
		//Actionlisteners
		selecterenButton.addActionListener(this);
		annulerenButton.addActionListener(this);
		vernieuwenButton.addActionListener(this);
		
		this.initialiseren();
		
		this.zalenInladen();
	}
	
	private void initialiseren()
	{
		this.setTitle("Zaal selecteren");
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setIconImage(RW2000Image);
		this.setSize(767, 536);
		this.setResizable(false);
		this.setLocation(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width/2 - this.getWidth()/2, GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height/2 - this.getHeight()/2);
		this.setAlwaysOnTop(true);
		this.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
		this.setUndecorated(true);
	}
	
	private void zalenInladen()
	{
		this.remove(zalenPane);
		ArrayList<Zaal> zalen = this.RW2000.zalenOpvragen();
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		Iterator<Zaal> iterator = zalen.iterator();
		while(iterator.hasNext())
		{
			Zaal zaal = iterator.next();
			listModel.addElement(zaal.zaalNaamOpvragen() + " - " + zaal.locatieOpvragen());
		}
		this.zalenList = new JList<String>(listModel);
		this.zalenList.setFont(new Font("Arial", Font.PLAIN, 18));
		this.zalenList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.zalenList.setLayoutOrientation(JList.VERTICAL);
		this.zalenList.setVisibleRowCount(-1);
		this.zalenList.addListSelectionListener(this);
		this.zalenPane = new JScrollPane(zalenList);
		this.zalenPane.setBounds(12, 46, 314, 372);
		this.add(zalenPane);
		this.zalenList.updateUI();
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e)
	{
		if(e.getValueIsAdjusting() == false)
		{
			if(this.zalenList.getSelectedIndex() == -1)
			{
				this.zaalnaamLabel.setText("Zaal: ");
				this.locatieLabel.setText("Locatie: ");
				this.capaciteitLabel.setText("Capaciteit: ");
			}
			else
			{
				Zaal zaal = this.RW2000.zalenOpvragen().get(this.zalenList.getSelectedIndex());
				this.zaalnaamLabel.setText("Zaal: " + zaal.zaalNaamOpvragen());
				this.locatieLabel.setText("Locatie: " + zaal.locatieOpvragen());
				this.capaciteitLabel.setText("Capaciteit: " + zaal.aantalPlaatsenOpvragen());
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
			if(this.zalenList.isSelectionEmpty() == false)
			{
				this.observer.setChanged();
				this.observer.notifyObservers(this.RW2000.zalenOpvragen().get(this.zalenList.getSelectedIndex()));
				this.setVisible(false);	
			}
		}
		else if(b == annulerenButton)
		{
			this.setVisible(false);
		}
		else if(b == vernieuwenButton)
		{
			this.zalenInladen();
			this.zaalnaamLabel.setText("Zaal: ");
			this.locatieLabel.setText("Locatie: ");
			this.capaciteitLabel.setText("Capaciteit: ");
		}
	}
}
