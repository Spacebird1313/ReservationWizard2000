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
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import system.Plaats;
import system.Reservatie;
import system.ReservationWizard2000;
import system.Voorstelling;

public class ReservatieWijzigWindow extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	final Image RW2000Image = Toolkit.getDefaultToolkit().getImage("pictures/RW2000.png");
	private ZaalweergavenWindow zaalweergavenWindow;
	private JPanel infoPanel, reservatiePanel, beschikbaarheidPanel;
	private JScrollPane stoelenPane;
	private JList<String> stoelenList;
	private JButton annulerenButton, bevestigButton;
	private JLabel voorstellingLabel, locatieLabel, tijdLabel, beschikbaarLabel, bezetLabel, reservatieNaamLabel, reservatieNummerLabel, aantalPlaatsenLabel, bedragLabel, stoelenLabel;
	private JTextField reservatieNaamTField;
	private ReservationWizard2000 RW2000;
	private JFrame parent;
	private Voorstelling huidigeVoorstelling;
	private Reservatie huidigeReservatie;
	private int aantalPlaatsen;
	private ArrayList<Plaats> geselecteerdePlaatsen;
	
	@SuppressWarnings("unchecked")
	public ReservatieWijzigWindow(ReservationWizard2000 RW2000, Voorstelling voorstelling, Reservatie reservatie, int aantalPlaatsen, JFrame parent)
	{
		this.RW2000 = RW2000;
		this.parent = parent;
		new DelegatedObservable();
		this.huidigeVoorstelling = voorstelling;
		this.huidigeReservatie = reservatie;
		this.aantalPlaatsen = aantalPlaatsen;
		this.geselecteerdePlaatsen = (ArrayList<Plaats>)reservatie.plaatsenOpvragen().clone();
		
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
		
		stoelenList = new JList<String>();
		stoelenList.setFont(font);
		stoelenList.setBounds(0, 0, 361, 84);
		
		stoelenPane = new JScrollPane(stoelenList);
		stoelenPane.setBounds(160, 105, 361, 84);
		
		annulerenButton = new JButton("Annuleren");
		annulerenButton.setFont(font);
		annulerenButton.setBackground(Color.lightGray);
		annulerenButton.setBounds(15, 436, 252, 91);
		
		bevestigButton = new JButton("Bevestigen");
		bevestigButton.setFont(font);
		bevestigButton.setBackground(Color.lightGray);
		bevestigButton.setBounds(286, 436, 252, 91);
		
		reservatieNaamTField = new JTextField(this.huidigeReservatie.reservatieNaamOpvragen());
		reservatieNaamTField.setFont(font);
		reservatieNaamTField.setBounds(74, 22, 445, 26);

		voorstellingLabel = new JLabel("Voorstelling: " + this.huidigeVoorstelling.voorstellingsNaamOpvragen());
		voorstellingLabel.setFont(font);
		voorstellingLabel.setBounds(9, 22, 502, 25);
		
		locatieLabel = new JLabel("Locatie: " + this.huidigeVoorstelling.zaalOpvragen().locatieOpvragen());
		locatieLabel.setFont(font);
		locatieLabel.setBounds(9, 57, 264, 25);
		
		tijdLabel = new JLabel("Tijd: " + this.huidigeVoorstelling.tijdOpvragen());
		tijdLabel.setFont(font);
		tijdLabel.setBounds(284, 57, 227, 25);
		
		beschikbaarLabel = new JLabel("<html>Beschikbaar: <font color=\"green\">" + (this.huidigeVoorstelling.zaalOpvragen().beschikbaarheidOpvragen() + this.huidigeReservatie.plaatsenOpvragen().size()) + "</html>");
		beschikbaarLabel.setFont(font);
		beschikbaarLabel.setBounds(9, 22, 265, 25);
		
		bezetLabel = new JLabel("<html>Bezet: <font color=\"red\">" + (this.huidigeVoorstelling.zaalOpvragen().aantalPlaatsenOpvragen() - this.huidigeVoorstelling.zaalOpvragen().beschikbaarheidOpvragen() + this.huidigeReservatie.plaatsenOpvragen().size()) + "</html>");
		bezetLabel.setFont(font);
		bezetLabel.setBounds(294, 22, 227, 25);
		
		reservatieNaamLabel = new JLabel("Naam: ");
		reservatieNaamLabel.setFont(font);
		reservatieNaamLabel.setBounds(9, 22, 67, 25);
		
		reservatieNummerLabel = new JLabel("Reservatienummer: " + this.huidigeReservatie.reservatieNummerOpvragen());
		reservatieNummerLabel.setFont(font);
		reservatieNummerLabel.setBounds(9, 63, 505, 25);
		
		stoelenLabel = new JLabel("Plaatsnummers: ");
		stoelenLabel.setFont(font);
		stoelenLabel.setBounds(9, 102, 158, 25);
		
		aantalPlaatsenLabel = new JLabel("<html>Plaatsen gereserveerd: " + this.huidigeReservatie.plaatsenOpvragen().size() + " van " + this.aantalPlaatsen + "</html>"); 
		aantalPlaatsenLabel.setFont(font);
		aantalPlaatsenLabel.setBounds(9, 205, 505, 25);
		
		if(this.huidigeReservatie.betaalStatusOpvragen())
		{
			bedragLabel = new JLabel("Te betalen bedrag: € 0.0 - Betaald");
		}
		else
		{
			bedragLabel = new JLabel("Te betalen bedrag: € " + this.huidigeReservatie.rekeningOpvragen());
		}
		bedragLabel.setFont(font);
		bedragLabel.setBounds(9, 264, 505, 25);
		
		//Set GUI
		this.add(infoPanel);
		this.add(reservatiePanel);
		this.add(beschikbaarheidPanel);
		this.add(annulerenButton);
		this.add(bevestigButton);
		
		infoPanel.add(voorstellingLabel);
		infoPanel.add(locatieLabel);
		infoPanel.add(tijdLabel);
		
		reservatiePanel.add(reservatieNaamLabel);
		reservatiePanel.add(reservatieNaamTField);
		reservatiePanel.add(reservatieNummerLabel);
		reservatiePanel.add(stoelenPane);
		reservatiePanel.add(stoelenLabel);
		reservatiePanel.add(aantalPlaatsenLabel);
		reservatiePanel.add(bedragLabel);
		
		beschikbaarheidPanel.add(beschikbaarLabel);
		beschikbaarheidPanel.add(bezetLabel);

		//Actionlisteners
		annulerenButton.addActionListener(this);
		bevestigButton.addActionListener(this);
		
		this.initialiseren();
		
		this.updatePlaatsenLijst();
		
		this.zaalweergavenWindow.zaalPreviewGenereren(this.huidigeVoorstelling, this.huidigeReservatie, false);
		this.zaalweergavenWindow.observerableOpvragen().addObserver(new Observer()
		{
			public void update(Observable source, Object object)
			{
				selectieWijzigen((int[])object);
			}
		});
	}
	
	private void initialiseren()
	{
		this.zaalweergavenWindow = new ZaalweergavenWindow(RW2000);
		this.setTitle("Reservatie wijzigen");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setIconImage(RW2000Image);
		this.setSize(567, 658);
		this.setResizable(false);
		this.setLocation(0, GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height - this.getHeight());
		this.setAlwaysOnTop(true);
		this.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
		this.setUndecorated(true);
		this.parent.setVisible(false);
		this.zaalweergavenWindow.setVisible(true);
	}
	
	public int aantalPlaatsen()
	{
		return this.aantalPlaatsen;
	}
	
	private void selectieWijzigen(int[] nieuweIndex)
	{
		int i = 0;
		int geselecteerd = 0;
		while(i < this.geselecteerdePlaatsen.size() && geselecteerd == 0)
		{
			Plaats plaats = this.geselecteerdePlaatsen.get(i);
			if(plaats == this.huidigeVoorstelling.zaalOpvragen().plaatsOpvragen(nieuweIndex[0], nieuweIndex[1]))
			{
				geselecteerd = 1;
			}
			else
			{
				i++;
			}
		}
		if(geselecteerd == 1)
		{
			this.plaatsDeselecteren(this.huidigeVoorstelling.zaalOpvragen().plaatsOpvragen(nieuweIndex[0], nieuweIndex[1]));
		}
		else
		{
			if(this.geselecteerdePlaatsen.size() != this.aantalPlaatsen)
			{
				this.plaatsSelecteren(this.huidigeVoorstelling.zaalOpvragen().plaatsOpvragen(nieuweIndex[0], nieuweIndex[1]));
			}
			else
			{
				this.aantalPlaatsenLabel.setText("<html>Plaatsen gereserveerd: <font color=\"green\">" + this.geselecteerdePlaatsen.size() + "<font color=\"black\"> van " + this.aantalPlaatsen + "</html>");
			}
		}
	}
	
	private void plaatsSelecteren(Plaats plaats)
	{
		this.geselecteerdePlaatsen.add(plaats);
		this.zaalweergavenWindow.selectieToestandWijzigen(3);
		this.updatePlaatsenLijst();
	}
	
	private void plaatsDeselecteren(Plaats plaats)
	{
		this.geselecteerdePlaatsen.remove(plaats);
		this.zaalweergavenWindow.selectieToestandWijzigen(1);
		this.updatePlaatsenLijst();
	}
	
	private void updatePlaatsenLijst()
	{
		double totaalPrijs = 0;
		this.reservatiePanel.remove(stoelenPane);
		ArrayList<Plaats> plaatsen = this.geselecteerdePlaatsen;
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		int i = 1;
		Iterator<Plaats> iterator = plaatsen.iterator();
		while(iterator.hasNext())
		{
			Plaats plaats = iterator.next();
			listModel.addElement("Plaats " + i + " - Rij " + (this.huidigeVoorstelling.zaalOpvragen().rijenOpvragen() - plaats.rijOpvragen()) + " Plaats " + (plaats.plaatsOpvragen() + 1) + " - Prijs: € " + plaats.prijsOpvragen());
			totaalPrijs = totaalPrijs + plaats.prijsOpvragen();
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
		this.aantalPlaatsenLabel.setText("<html>Plaatsen gereserveerd: " + this.geselecteerdePlaatsen.size() + " van " + this.aantalPlaatsen + "</html>");
		this.bedragLabel.setText("Te betalen bedrag: € " + totaalPrijs);
	}
	
	private void reservatieBevestigd()
	{
		for(int i = 0; i < this.aantalPlaatsen; i++)
		{
			Plaats plaats = this.huidigeReservatie.plaatsenOpvragen().get(0);
			this.huidigeReservatie.plaatsVerwijderen(plaats);
		}
		
		Iterator<Plaats> iterator = this.geselecteerdePlaatsen.iterator();
		while(iterator.hasNext())
		{
			Plaats plaats = iterator.next();
			this.huidigeReservatie.voegPlaatsToe(plaats);
		}
		this.huidigeReservatie.reservatieNaamAanpassen(this.reservatieNaamTField.getText());
		this.RW2000.saveReservaties();
	}
	
	public void hideAllWindows()
	{
		this.zaalweergavenWindow.setVisible(false);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		JButton b = (JButton)e.getSource();
		if(b == annulerenButton)
		{
			JOptionPane pane = new JOptionPane("Wenst u de wijzigingen te annuleren?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
			JDialog dialog = pane.createDialog("Reservatie annuleren");
			dialog.setIconImage(RW2000Image);	
			dialog.setAlwaysOnTop(true);
			dialog.setVisible(true);
			if(pane.getValue() != null)
			{
				if(((Integer)pane.getValue()).intValue() == JOptionPane.YES_OPTION)
				{
					this.zaalweergavenWindow.setVisible(false);
					this.parent.setVisible(true);
					this.setVisible(false);
				}
			}
		}
		else if(b == bevestigButton)
		{
			if(this.geselecteerdePlaatsen.size() == this.aantalPlaatsen())
			{
				if(this.reservatieNaamTField.getText().equals(""))
				{
					this.reservatieNaamTField.requestFocus(true);
				}
				else
				{
					JOptionPane pane = new JOptionPane("Weet u zeker de reservatie: \"" + this.huidigeReservatie.reservatieNaamOpvragen() + "\" te wijzigen?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
					JDialog dialog = pane.createDialog("Reservatie wijzigen");
					dialog.setIconImage(RW2000Image);	
					dialog.setAlwaysOnTop(true);
					dialog.setVisible(true);
					if(pane.getValue() != null)
					{
						if(((Integer)pane.getValue()).intValue() == JOptionPane.YES_OPTION)
						{
							reservatieBevestigd();
							if(this.huidigeReservatie.betaalStatusOpvragen() == false)
							{
								JOptionPane betalenPane = new JOptionPane("Wenst u deze reservatie te betalen?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
								JDialog betalenDialog = betalenPane.createDialog("Reservatie betalen");
								betalenDialog.setIconImage(RW2000Image);	
								betalenDialog.setAlwaysOnTop(true);
								betalenDialog.setVisible(true);
								if(betalenPane.getValue() != null)
								{
									if(((Integer)betalenPane.getValue()).intValue() == JOptionPane.YES_OPTION)
									{
										JOptionPane betalingsTerminalpane = new JOptionPane("Het bedrag wordt overgemaakt... Klik 'OK' om voort te gaan.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION);
										JDialog betalingsTerminaldialog = betalingsTerminalpane.createDialog("Betalingsterminal");
										betalingsTerminaldialog.setIconImage(RW2000Image);	
										betalingsTerminaldialog.setAlwaysOnTop(true);
										betalingsTerminaldialog.setVisible(true);
										this.RW2000.betalen(huidigeReservatie, huidigeReservatie.rekeningOpvragen());
										this.RW2000.rekeningAfdrukken(huidigeVoorstelling, huidigeReservatie);
									}
								}
							}
							this.zaalweergavenWindow.setVisible(false);
							this.parent.setVisible(true);
							this.setVisible(false);
						}
					}
				}
			}
			else
			{
				this.aantalPlaatsenLabel.setText("<html>Plaatsen gereserveerd: <font color=\"red\">" + this.geselecteerdePlaatsen.size() + "<font color=\"black\"> van " + this.aantalPlaatsen + "</html>");
			}
		}
	}
}