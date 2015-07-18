package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import system.Plaats;
import system.Reservatie;
import system.ReservationWizard2000;
import system.Voorstelling;

public class ReservatieWindow extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	final Image RW2000Image = Toolkit.getDefaultToolkit().getImage("pictures/RW2000.png");
	private JPanel infoPanel, reservatiePanel, beschikbaarheidPanel, zaalPreviewPanel, zaalPanel;
	private JScrollPane stoelenPane;
	private JList<String> stoelenList;
	private JButton annulerenButton, bewerkenButton;
	private JLabel voorstellingLabel, locatieLabel, tijdLabel, beschikbaarLabel, bezetLabel, reservatieNaamLabel, reservatieNummerLabel, aantalPlaatsenLabel, bedragLabel, stoelenLabel;
	private ReservationWizard2000 RW2000;
	private ReservatieWijzigWindow reservatieWijzigWindow;
	private JFrame parent;
	private Reservatie huidigeReservatie;
	private Voorstelling huidigeVoorstelling;
	
	public ReservatieWindow(ReservationWizard2000 RW2000, JFrame parent)
	{
		this.RW2000 = RW2000;
		this.parent = parent;
		
		this.setLayout(null);
		
		Font font = new Font("Arial", Font.PLAIN, 20);
		
		infoPanel = new JPanel();
		infoPanel.setBorder(BorderFactory.createTitledBorder("Voorstelling"));
		infoPanel.setBounds(12, 12, 528, 91);
		infoPanel.setLayout(null);
		
		reservatiePanel = new JPanel();
		reservatiePanel.setBorder(BorderFactory.createTitledBorder("Reservatieinfo"));
		reservatiePanel.setBounds(12, 109, 528, 311);
		reservatiePanel.setLayout(null);
		
		beschikbaarheidPanel = new JPanel();
		beschikbaarheidPanel.setBorder(BorderFactory.createTitledBorder("Beschikbaarheid"));
		beschikbaarheidPanel.setBounds(12, 546, 528, 62);
		beschikbaarheidPanel.setLayout(null);
		
		zaalPreviewPanel = new JPanel();
		zaalPreviewPanel.setBorder(BorderFactory.createTitledBorder("Zaalindeling"));
		zaalPreviewPanel.setBounds(544, 12, 715, 596);
		zaalPreviewPanel.setOpaque(true);
		zaalPreviewPanel.setLayout(null);
		
		zaalPanel = new JPanel();
		zaalPanel.setVisible(false);
		
		stoelenPane = new JScrollPane();
		stoelenPane.setBounds(160, 105, 361, 84);
		
		stoelenList = new JList<String>();
		stoelenList.setFont(font);
		stoelenList.setBounds(0, 0, 361, 84);
		
		annulerenButton = new JButton("Annuleren");
		annulerenButton.setFont(font);
		annulerenButton.setBackground(Color.lightGray);
		annulerenButton.setBounds(15, 436, 252, 91);
		
		bewerkenButton = new JButton("Bewerken");
		bewerkenButton.setFont(font);
		bewerkenButton.setBackground(Color.lightGray);
		bewerkenButton.setBounds(286, 436, 252, 91);

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
		beschikbaarLabel.setBounds(9, 22, 265, 25);
		
		bezetLabel = new JLabel("Bezet: ");
		bezetLabel.setFont(font);
		bezetLabel.setBounds(294, 22, 227, 25);
		
		reservatieNaamLabel = new JLabel("Naam: ");
		reservatieNaamLabel.setFont(font);
		reservatieNaamLabel.setBounds(9, 22, 505, 25);
		
		reservatieNummerLabel = new JLabel("Reservatienummer: ");
		reservatieNummerLabel.setFont(font);
		reservatieNummerLabel.setBounds(9, 63, 505, 25);
		
		stoelenLabel = new JLabel("Plaatsnummers: ");
		stoelenLabel.setFont(font);
		stoelenLabel.setBounds(9, 102, 158, 25);
		
		aantalPlaatsenLabel = new JLabel("Plaatsen gereserveerd: "); 
		aantalPlaatsenLabel.setFont(font);
		aantalPlaatsenLabel.setBounds(9, 205, 505, 25);
		
		bedragLabel = new JLabel("Te betalen bedrag: ");
		bedragLabel.setFont(font);
		bedragLabel.setBounds(9, 264, 505, 25);
		
		//Set GUI
		this.add(infoPanel);
		this.add(reservatiePanel);
		this.add(beschikbaarheidPanel);
		this.add(zaalPreviewPanel);
		this.add(annulerenButton);
		this.add(bewerkenButton);
		
		infoPanel.add(voorstellingLabel);
		infoPanel.add(locatieLabel);
		infoPanel.add(tijdLabel);
		
		reservatiePanel.add(reservatieNaamLabel);
		reservatiePanel.add(reservatieNummerLabel);
		reservatiePanel.add(stoelenPane);
		reservatiePanel.add(stoelenLabel);
		reservatiePanel.add(aantalPlaatsenLabel);
		reservatiePanel.add(bedragLabel);
		
		beschikbaarheidPanel.add(beschikbaarLabel);
		beschikbaarheidPanel.add(bezetLabel);

		//Actionlisteners
		annulerenButton.addActionListener(this);
		bewerkenButton.addActionListener(this);
		
		
		this.initialiseren();
	}
	
	private void initialiseren()
	{
		this.setTitle("Reservatie");
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setIconImage(RW2000Image);
		this.setSize(1280, 658);
		this.setResizable(false);
		this.setLocation(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width/2 - this.getWidth()/2, GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height/2 - this.getHeight()/2);
		this.setAlwaysOnTop(true);
		this.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
		this.setUndecorated(true);
		this.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				parent.setVisible(true);
			}
		});	
		
		this.addComponentListener(new ComponentAdapter()
		{			
			public void componentShown(ComponentEvent e)
			{
				parent.setVisible(false);
			}
		});
	}

	public void hideAllWindows()
	{
		if(this.reservatieWijzigWindow != null)
		{
			reservatieWijzigWindow.hideAllWindows();
			reservatieWijzigWindow.setVisible(false);
		}
	}
	
	public void reservatieLaden(Voorstelling voorstelling, int reservatieIndex)
	{
		if(voorstelling != null && voorstelling.reservatieOpvragen().get(reservatieIndex) != null)
		{
			this.huidigeVoorstelling = voorstelling;
			this.huidigeReservatie = voorstelling.reservatieOpvragen().get(reservatieIndex);
			this.voorstellingLabel.setText("Voorstelling: " + voorstelling.voorstellingsNaamOpvragen());
			this.locatieLabel.setText("Locatie: " + voorstelling.zaalOpvragen().locatieOpvragen());
			this.tijdLabel.setText("Tijd: " + voorstelling.tijdOpvragen());
			this.reservatieNaamLabel.setText("Naam: " + huidigeReservatie.reservatieNaamOpvragen());
			this.reservatieNummerLabel.setText("Reservatienummer: " + huidigeReservatie.reservatieNummerOpvragen());
			this.aantalPlaatsenLabel.setText("Plaatsen gereserveerd: " + huidigeReservatie.plaatsenOpvragen().size());
			if(huidigeReservatie.betaalStatusOpvragen() == false)
			{
				this.bedragLabel.setText("Te betalen bedrag: € " + huidigeReservatie.rekeningOpvragen());
			}
			else
			{
				this.bedragLabel.setText("Te betalen bedrag: € 0.00 - Betaald");
			}
			this.stoelenLijstGenereren(voorstelling);
		}
		else
		{
			this.huidigeReservatie = null;
			this.voorstellingLabel.setText("Voorstelling: ");
			this.locatieLabel.setText("Locatie: ");
			this.tijdLabel.setText("Tijd: ");
			this.reservatieNaamLabel.setText("Naam: ");
			this.reservatieNummerLabel.setText("Reservatienummer: ");
			this.aantalPlaatsenLabel.setText("Plaatsen gereserveerd: ");
		}
		this.zaalPreviewGenereren(voorstelling);
	}
	
	private void stoelenLijstGenereren(Voorstelling voorstelling)
	{
		this.reservatiePanel.remove(stoelenPane);
		ArrayList<Plaats> plaatsen = this.huidigeReservatie.plaatsenOpvragen();
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		int i = 1;
		Iterator<Plaats> iterator = plaatsen.iterator();
		while(iterator.hasNext())
		{
			Plaats plaats = iterator.next();
			listModel.addElement("Plaats " + i + " - Rij " + (voorstelling.zaalOpvragen().rijenOpvragen() - plaats.rijOpvragen()) + " Plaats " + (plaats.plaatsOpvragen() + 1) + " - Prijs: € " + plaats.prijsOpvragen());
			i++;
		}
		this.stoelenList = new JList<String>(listModel);
		this.stoelenList.setFont(new Font("Arial", Font.PLAIN, 18));
		this.stoelenList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.stoelenList.setLayoutOrientation(JList.VERTICAL);
		this.stoelenList.setVisibleRowCount(-1);
		this.stoelenPane = new JScrollPane(stoelenList);
		this.stoelenPane.setBounds(160, 105, 361, 84);
		this.reservatiePanel.add(stoelenPane);
		this.stoelenList.updateUI();
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
			ImageIcon stoelGeselecteerdImage = new ImageIcon((new ImageIcon("pictures/StoelSelecteren.png").getImage().getScaledInstance(this.zaalPanel.getWidth()/rijBreedte, this.zaalPanel.getHeight()/rijAantal, Image.SCALE_SMOOTH)));
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
								int gereserveerd = 0;
								Iterator<Plaats> iterator = this.huidigeReservatie.plaatsenOpvragen().iterator();
								while(iterator.hasNext() && gereserveerd == 0)
								{
									Plaats reservatiePlaats = iterator.next();
									if(plaats == reservatiePlaats)
									{
										gereserveerd = 1;
									}
								}
								if(gereserveerd == 1)
								{
									zaalPanel.add(new JLabel(stoelGeselecteerdImage));
								}
								else
								{
									zaalPanel.add(new JLabel(stoelBezetImage));
									bezettePlaatsen++;
								}
								break;
							default:
								zaalPanel.add(new JLabel(""));	
								break;
						}
					}
				}
			}
			this.beschikbaarLabel.setText("<html>Beschikbaar: <font color=\"green\">" + vrijePlaatsen + "</html>");
			this.bezetLabel.setText("<html>Bezet: <font color=\"red\">" + bezettePlaatsen + "</html>");
			this.zaalPreviewPanel.add(zaalPanel);
			this.zaalPreviewPanel.updateUI();
		}
		else
		{
			this.beschikbaarLabel.setText("<html>Beschikbaar: <font color=\"green\">" + "</html>");
			this.bezetLabel.setText("<html>Bezet: <font color=\"red\">" + "</html>");
			this.zaalPreviewPanel.removeAll();
			this.zaalPreviewPanel.updateUI();
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		JButton b = (JButton)e.getSource();
		if(b == annulerenButton)
		{
			this.parent.setVisible(true);
			this.setVisible(false);
		}
		else if(b == bewerkenButton)
		{
			if(this.reservatieWijzigWindow == null)
			{
				this.reservatieWijzigWindow = new ReservatieWijzigWindow(this.RW2000, this.huidigeVoorstelling, this.huidigeReservatie, this.huidigeReservatie.plaatsenOpvragen().size(), this.parent);
				this.reservatieWijzigWindow.setVisible(true);
				this.setVisible(false);
			}
			else
			{
				this.reservatieWijzigWindow.dispose();
				this.reservatieWijzigWindow = new ReservatieWijzigWindow(this.RW2000, this.huidigeVoorstelling, this.huidigeReservatie, this.huidigeReservatie.plaatsenOpvragen().size(), this.parent);
				this.reservatieWijzigWindow.setVisible(true);
				this.setVisible(false);
			}
		}
	}
}
