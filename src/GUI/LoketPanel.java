package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import system.*;

public class LoketPanel extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	final Image RW2000Image = Toolkit.getDefaultToolkit().getImage("pictures/RW2000.png");
	private VoorstellingSelectorWindow voorstellingSelectorWindow;
	private VoorstellingPropertiesWindow voorstellingPropertiesWindow;
	private ReservatieZoekWindow reservatieZoekWindow;
	private ReservatieMakerWindow reservatieMakerWindow;
	private ReservatieVerwijderenWindow reservatieVerwijderenWindow;
	private JPanel infoPanel, reservatiePanel, beschikbaarheidPanel, zaalPreviewPanel, zaalPanel;
	private JButton voorstellingWijzigenButton, voorstellingseigenschappenButton, inschrijvenButton, uitschrijvenButton, reservatieWijzigenButton;
	private JLabel voorstellingLabel, locatieLabel, tijdLabel, beschikbaarLabel, zaalgrootteLabel, bezetLabel, geenVoorstellingLabel;
	private ReservationWizard2000 RW2000;
	private Voorstelling huidigeVoorstelling;
	
	public LoketPanel(ReservationWizard2000 RW2000)
	{
		this.RW2000 = RW2000;
		
		this.setLayout(null);
			
		Font font = new Font("Arial", Font.PLAIN, 20);
		
		infoPanel = new JPanel();
		infoPanel.setBorder(BorderFactory.createTitledBorder("Voorstelling"));
		infoPanel.setBounds(12, 12, 528, 91);
		infoPanel.setLayout(null);
		
		reservatiePanel = new JPanel();
		reservatiePanel.setBorder(BorderFactory.createTitledBorder("Reservatie"));
		reservatiePanel.setBounds(12, 109, 528, 428);
		reservatiePanel.setLayout(null);
		
		beschikbaarheidPanel = new JPanel();
		beschikbaarheidPanel.setBorder(BorderFactory.createTitledBorder("Beschikbaarheid"));
		beschikbaarheidPanel.setBounds(554, 109, 546, 129);
		beschikbaarheidPanel.setLayout(null);
		
		zaalPreviewPanel = new JPanel();
		zaalPreviewPanel.setBorder(BorderFactory.createTitledBorder("Zaalpreview"));
		zaalPreviewPanel.setBounds(554, 244, 546, 423);
		zaalPreviewPanel.setOpaque(true);
		zaalPreviewPanel.setLayout(null);
		
		zaalPanel = new JPanel();
		zaalPanel.setVisible(false);
		
		reservatieWijzigenButton = new JButton("Zoeken/Wijzigen");
		reservatieWijzigenButton.setFont(font);
		reservatieWijzigenButton.setBackground(Color.lightGray);
		reservatieWijzigenButton.setBounds(14, 285, 502, 124);
		
		inschrijvenButton = new JButton("Inschrijven");
		inschrijvenButton.setFont(font);
		inschrijvenButton.setBackground(Color.lightGray);
		inschrijvenButton.setBounds(14, 25, 502, 124);
		
		uitschrijvenButton = new JButton("Uitschrijven");
		uitschrijvenButton.setFont(font);
		uitschrijvenButton.setBackground(Color.lightGray);
		uitschrijvenButton.setBounds(14, 155, 502, 124);
		
		voorstellingWijzigenButton = new JButton("Wijzig voorstelling");
		voorstellingWijzigenButton.setFont(font);
		voorstellingWijzigenButton.setBackground(Color.lightGray);
		voorstellingWijzigenButton.setBounds(550, 28, 271, 66);
		
		voorstellingseigenschappenButton = new JButton("Voorstellingseigenschappen");
		voorstellingseigenschappenButton.setFont(new Font("Arial", Font.PLAIN, 18));
		voorstellingseigenschappenButton.setBackground(Color.lightGray);
		voorstellingseigenschappenButton.setBounds(827, 28, 271, 66);
		
		voorstellingLabel = new JLabel("Voorstelling: ");
		voorstellingLabel.setFont(font);
		voorstellingLabel.setBounds(9, 22, 502, 25);
		
		locatieLabel = new JLabel("Locatie: ");
		locatieLabel.setFont(font);
		locatieLabel.setBounds(9, 57, 264, 25);
		
		tijdLabel = new JLabel("Tijd: ");
		tijdLabel.setFont(font);
		tijdLabel.setBounds(284, 57, 227, 25);
		
		beschikbaarLabel = new JLabel("Beschikbaar: ");
		beschikbaarLabel.setFont(font);
		beschikbaarLabel.setBounds(9, 37, 295, 25);
		
		zaalgrootteLabel = new JLabel("Zaalgrootte: ");
		zaalgrootteLabel.setFont(font);
		zaalgrootteLabel.setBounds(16, 82, 295, 25);
		
		bezetLabel = new JLabel("Bezet: ");
		bezetLabel.setFont(font);
		bezetLabel.setBounds(314, 37, 295, 25);
		
		geenVoorstellingLabel = new JLabel("Geen voorstelling geselecteerd.");
		geenVoorstellingLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		geenVoorstellingLabel.setHorizontalAlignment(JLabel.CENTER);
		geenVoorstellingLabel.setBounds(84, 192, 390, 32);
		geenVoorstellingLabel.setVisible(true);
						
		//Set GUI
		this.add(infoPanel);
		this.add(reservatiePanel);
		this.add(beschikbaarheidPanel);
		this.add(zaalPreviewPanel);
		this.add(voorstellingWijzigenButton);
		this.add(voorstellingseigenschappenButton);
		
		reservatiePanel.add(inschrijvenButton);
		
		if(this.RW2000.getCurrentLogedIn().userLevelOpvragen() != 1)
		{
			reservatiePanel.add(uitschrijvenButton);
			reservatiePanel.add(reservatieWijzigenButton);
		}
		
		infoPanel.add(voorstellingLabel);
		infoPanel.add(locatieLabel);
		infoPanel.add(tijdLabel);
		
		beschikbaarheidPanel.add(beschikbaarLabel);
		beschikbaarheidPanel.add(bezetLabel);
		beschikbaarheidPanel.add(zaalgrootteLabel);
		
		zaalPreviewPanel.add(geenVoorstellingLabel);
							
		//Actionlisteners
		voorstellingWijzigenButton.addActionListener(this);
		voorstellingseigenschappenButton.addActionListener(this);
		inschrijvenButton.addActionListener(this);
		uitschrijvenButton.addActionListener(this);
		reservatieWijzigenButton.addActionListener(this);

		this.initialiseren();
		
		this.RW2000.saveLoadObserverOpvragen().addObserver(new Observer()
		{
			public void update(Observable source, Object object)
			{
				voorstellingenGewijzigd();
			}
		});
	}
		
		
	private void initialiseren()
	{
		this.setTitle("Reservation Wizard 2000 - Loket");
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setIconImage(RW2000Image);
		this.setSize(1121, 717);
		this.setResizable(false);
		this.setLocation(0, 0);
		this.setAlwaysOnTop(true);
		this.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
		this.setUndecorated(true);
		this.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				hideAllWindows();
			}
		});	
	}
	
	private void voorstellingLaden(Voorstelling voorstelling)
	{
		if(voorstelling != null)
		{
			this.huidigeVoorstelling = voorstelling;
			this.voorstellingLabel.setText("Voorstelling: " + voorstelling.voorstellingsNaamOpvragen());
			this.locatieLabel.setText("Locatie: " + voorstelling.zaalOpvragen().locatieOpvragen());
			this.tijdLabel.setText("Tijd: " + voorstelling.tijdOpvragen());
		}
		else
		{
			this.huidigeVoorstelling = null;
			this.voorstellingLabel.setText("Voorstelling: ");
			this.locatieLabel.setText("Locatie: ");
			this.tijdLabel.setText("Tijd: ");
		}
		this.zaalPreviewGenereren(voorstelling);
	}
		
	private void zaalPreviewGenereren(Voorstelling voorstelling)
	{
		if(voorstelling != null)
		{
			int rijBreedte = voorstelling.zaalOpvragen().plaatsenPerRij();
			int rijAantal = voorstelling.zaalOpvragen().rijenOpvragen();
			this.zaalPreviewPanel.removeAll();
			this.zaalPanel = new JPanel(new GridLayout(rijAantal, rijBreedte));
			this.zaalPanel.setOpaque(true);
			this.zaalPanel.setBounds(5, 15, this.zaalPreviewPanel.getWidth() - 10, this.zaalPreviewPanel.getHeight() - 20);
			ImageIcon stoelBeschikbaarImage = new ImageIcon((new ImageIcon("pictures/StoelBeschikbaar.png").getImage().getScaledInstance(this.zaalPanel.getWidth()/rijBreedte, this.zaalPanel.getHeight()/rijAantal, Image.SCALE_SMOOTH)));
			ImageIcon stoelBezetImage = new ImageIcon((new ImageIcon("pictures/StoelBezet.png").getImage().getScaledInstance(zaalPanel.getWidth()/rijBreedte, zaalPanel.getHeight()/rijAantal, Image.SCALE_SMOOTH)));
			Plaats plaats = null;
			int vrijePlaatsen = 0;
			int bezettePlaatsen = 0;
			for (int i = 0; i < rijAantal; i++)
			{
				for	(int j = 0; j < rijBreedte; j++)
				{	
					plaats = voorstelling.zaalOpvragen().plaatsOpvragen(i, j);
					if (plaats != null)
					{							
						switch(plaats.beschikbaarheidOpvragen())
						{
							case 0:
								zaalPanel.add(new JLabel(""));
								break;
							case 1:
								zaalPanel.add(new JLabel(stoelBeschikbaarImage));
								vrijePlaatsen++;
								break;
							case 2:
								zaalPanel.add(new JLabel(stoelBezetImage));
								bezettePlaatsen++;
								break;
							default:
								zaalPanel.add(new JLabel(""));	
								break;
						}
					}
				}
			}
			this.beschikbaarLabel.setText("<html>Beschikbaar: <font color=\"green\">" + vrijePlaatsen + "</html>");
			this.zaalgrootteLabel.setText("Zaalgrootte: " + (vrijePlaatsen + bezettePlaatsen));
			this.bezetLabel.setText("<html>Bezet: <font color=\"red\">" + bezettePlaatsen + "</html>");
			this.zaalPreviewPanel.add(zaalPanel);
			this.zaalPreviewPanel.updateUI();
		}
		else
		{
			this.beschikbaarLabel.setText("<html>Beschikbaar: <font color=\"green\">" + "</html>");
			this.zaalgrootteLabel.setText("Zaalgrootte: ");
			this.bezetLabel.setText("<html>Bezet: <font color=\"red\">" + "</html>");
			this.zaalPreviewPanel.removeAll();
			this.zaalPreviewPanel.add(this.geenVoorstellingLabel);
			this.zaalPreviewPanel.updateUI();
		}
	}
	
	public void hideAllWindows()
	{
		if(reservatieZoekWindow != null)
		{
			reservatieZoekWindow.hideAllWindows();
			reservatieZoekWindow.setVisible(false);
		}
		if(voorstellingSelectorWindow != null)
		{
			voorstellingSelectorWindow.setVisible(false);
		}
		if(voorstellingPropertiesWindow != null)
		{
			voorstellingPropertiesWindow.setVisible(false);
		}
		if(reservatieMakerWindow != null)
		{
			reservatieMakerWindow.hideAllWindows();
			reservatieMakerWindow.setVisible(false);
		}
		if(reservatieVerwijderenWindow != null)
		{
			reservatieVerwijderenWindow.setVisible(false);
		}
	}
	
	private void resetVoorstellingSpecifiekeWindows()
	{
		this.reservatieMakerWindow = null;
		this.reservatieZoekWindow = null;
		this.reservatieVerwijderenWindow = null;
	}

	public void actionPerformed(ActionEvent e)
	{
		JButton b = (JButton)e.getSource();
		if(b == voorstellingWijzigenButton)
		{
			if(voorstellingSelectorWindow == null)
			{
				this.voorstellingSelectorWindow = new VoorstellingSelectorWindow(RW2000);
			}
			else
			{
				this.voorstellingSelectorWindow.dispose();
				this.voorstellingSelectorWindow = new VoorstellingSelectorWindow(RW2000);
			}
			this.voorstellingSelectorWindow.observerableOpvragen().addObserver(new Observer()
			{
				public void update(Observable source, Object object)
				{
					voorstellingLaden((Voorstelling)object);
				}
			});
			this.hideAllWindows();
			this.resetVoorstellingSpecifiekeWindows();
			this.voorstellingSelectorWindow.setVisible(true);
		}
		else if(b == voorstellingseigenschappenButton)
		{
			if(voorstellingPropertiesWindow == null)
			{
				this.voorstellingPropertiesWindow = new VoorstellingPropertiesWindow();
			}
			this.voorstellingPropertiesWindow.voorstellingLaden(this.huidigeVoorstelling);
			this.voorstellingPropertiesWindow.setVisible(true);
		}
		else if(b == inschrijvenButton)
		{
			if(this.huidigeVoorstelling != null)
			{
				if(this.huidigeVoorstelling.zaalOpvragen().beschikbaarheidOpvragen() != 0)
				{
					if(this.reservatieMakerWindow == null)
					{
						this.reservatieMakerWindow = new ReservatieMakerWindow(RW2000, this.huidigeVoorstelling, this);
					}
					else
					{
						this.reservatieMakerWindow.dispose();
						this.reservatieMakerWindow = new ReservatieMakerWindow(RW2000, this.huidigeVoorstelling, this);
					}
					this.hideAllWindows();
					this.reservatieMakerWindow.setVisible(true);
				}
			}
		}
		else if(b == uitschrijvenButton)
		{
			if(this.huidigeVoorstelling != null)
			{
				if(this.reservatieVerwijderenWindow == null)
				{
					this.reservatieVerwijderenWindow = new ReservatieVerwijderenWindow(RW2000, this, this.huidigeVoorstelling);
				}
				else
				{
					this.reservatieVerwijderenWindow.dispose();
					this.reservatieVerwijderenWindow = new ReservatieVerwijderenWindow(RW2000, this, this.huidigeVoorstelling);
				}
				this.hideAllWindows();
				reservatieVerwijderenWindow.setVisible(true);
			}
		}
		else if(b == reservatieWijzigenButton)
		{
			if(this.huidigeVoorstelling != null)
			{
				if(this.reservatieZoekWindow == null)
				{
					this.reservatieZoekWindow = new ReservatieZoekWindow(RW2000, this, this.huidigeVoorstelling);
				}
				else
				{
					this.reservatieZoekWindow.dispose();
					this.reservatieZoekWindow = new ReservatieZoekWindow(RW2000, this, this.huidigeVoorstelling);
				}
				reservatieZoekWindow.setVisible(true);
			}
		}
	}
	
	private void voorstellingenGewijzigd()
	{
		if(huidigeVoorstelling != null)
		{
			voorstellingLaden(huidigeVoorstelling);
		}
	}
}
