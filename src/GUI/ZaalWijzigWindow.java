package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import system.Plaats;
import system.ReservationWizard2000;
import system.Voorstelling;
import system.Zaal;

public class ZaalWijzigWindow extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	final Image RW2000Image = Toolkit.getDefaultToolkit().getImage("pictures/RW2000.png");
	private ZaalweergavenWindow zaalweergavenWindow;
	private JPanel eigenschappenPanel, zaalPanel, plaatsPanel;
	private JTextField zaalNaamTField, locatieTField;
	private JFormattedTextField zaalPrijsTField, plaatsPrijsTField;
	private JSpinner rijenSpinner, plaatsenSpinner;
	private JButton zaalGenererenButton, beschikbaarButton, onbeschikbaarButton, instellenButton, annulerenButton, bevestigButton;
	private JLabel zaalnaamLabel, locatieLabel, aantalRijenLabel, aantalPlaatsenLabel, capaciteitLabel, zaalPrijsLabel, plaatsLabel, statusLabel, plaatsPrijsLabel;
	private ReservationWizard2000 RW2000;
	private Plaats geselecteerdePlaats;
	private boolean nieuweZaal;
	private boolean zaalGegenereerd;
	private Zaal huidigeZaal;
	private Zaal aangemaakteZaal;
	
	public ZaalWijzigWindow(ReservationWizard2000 RW2000)
	{
		this.RW2000 = RW2000;
		this.nieuweZaal = true;
		this.zaalGegenereerd = false;
		this.huidigeZaal = null;
		this.aangemaakteZaal = null;
		
		this.initialiseren();
	}
	
	public ZaalWijzigWindow(ReservationWizard2000 RW2000, Zaal zaal)
	{
		this.RW2000 = RW2000;
		this.nieuweZaal = false;
		this.zaalGegenereerd = true;
		this.huidigeZaal = zaal;
		this.aangemaakteZaal = zaal;
		
		this.initialiseren();
		this.ingeladenZaalWeergeven();
	}
	
	private void initialiseren()
	{
		this.setTitle("Zaal beheren");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setIconImage(RW2000Image);
		this.setSize(535, 665);
		this.setResizable(false);
		this.setLocation(0, GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height - this.getHeight());
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
		
		this.setLayout(null);
		
		Font font = new Font("Arial", Font.PLAIN, 20);
		
		eigenschappenPanel = new JPanel();
		eigenschappenPanel.setBorder(BorderFactory.createTitledBorder("Eigenschappen"));
		eigenschappenPanel.setBounds(12, 12, 492, 108);
		eigenschappenPanel.setLayout(null);
		
		zaalPanel = new JPanel();
		zaalPanel.setBorder(BorderFactory.createTitledBorder("Zaalgrootte"));
		zaalPanel.setBounds(12, 121, 492, 160);
		zaalPanel.setLayout(null);
		
		plaatsPanel = new JPanel();
		plaatsPanel.setBorder(BorderFactory.createTitledBorder("Geselecteerde plaats"));
		plaatsPanel.setBounds(12, 285, 492, 237);
		plaatsPanel.setLayout(null);
		
		zaalNaamTField = new JTextField("");
		zaalNaamTField.setFont(font);
		zaalNaamTField.setBounds(65, 26, 403, 25);
		
		locatieTField = new JTextField("");
		locatieTField.setFont(font);
		locatieTField.setBounds(88, 67, 380, 25);
		
		zaalPrijsTField = new JFormattedTextField(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("#.00"))));
		zaalPrijsTField.setFont(font);
		zaalPrijsTField.setBounds(304, 70, 180, 25);
		
		plaatsPrijsTField = new JFormattedTextField(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("#.00"))));
		plaatsPrijsTField.setFont(font);
		plaatsPrijsTField.setBounds(99, 118, 180, 25);
		
		SpinnerModel sRijModel = new SpinnerNumberModel(1, 1, 200, 1);
		rijenSpinner = new JSpinner(sRijModel);
		rijenSpinner.setFont(font);
		rijenSpinner.setBounds(123, 25, 95, 25);
		
		SpinnerModel sPlaatsModel = new SpinnerNumberModel(1, 1, 500, 1);
		plaatsenSpinner = new JSpinner(sPlaatsModel);
		plaatsenSpinner.setFont(font);
		plaatsenSpinner.setBounds(373, 25, 95, 25);
		
		zaalGenererenButton = new JButton("Zaal genereren");
		zaalGenererenButton.setFont(font);
		zaalGenererenButton.setBackground(Color.lightGray);
		zaalGenererenButton.setBounds(132, 103, 228, 43);
		
		beschikbaarButton = new JButton("Beschikbaar");
		beschikbaarButton.setFont(font);
		beschikbaarButton.setBackground(Color.lightGray);
		beschikbaarButton.setBounds(88, 57, 191, 50);
		
		onbeschikbaarButton = new JButton("Onbeschikbaar");
		onbeschikbaarButton.setFont(font);
		onbeschikbaarButton.setBackground(Color.lightGray);
		onbeschikbaarButton.setBounds(286, 57, 190, 50);
		
		instellenButton = new JButton("Prijs instellen");
		instellenButton.setFont(font);
		instellenButton.setBackground(Color.lightGray);
		instellenButton.setBounds(123, 152, 243, 73);
		
		annulerenButton = new JButton("Annuleren");
		annulerenButton.setFont(font);
		annulerenButton.setBackground(Color.lightGray);
		annulerenButton.setBounds(12, 530, 242, 91);
		
		bevestigButton = new JButton("Bewerken");
		bevestigButton.setFont(font);
		bevestigButton.setBackground(Color.lightGray);
		bevestigButton.setBounds(261, 530, 242, 91);
		
		zaalnaamLabel = new JLabel("Zaal: ");
		zaalnaamLabel.setFont(font);
		zaalnaamLabel.setBounds(9, 26, 56, 25);
		
		locatieLabel = new JLabel("Locatie: ");
		locatieLabel.setFont(font);
		locatieLabel.setBounds(9, 66, 81, 25);
		
		aantalRijenLabel = new JLabel("Aantal rijen: ");
		aantalRijenLabel.setFont(font);
		aantalRijenLabel.setBounds(9, 24, 115, 25);
		
		aantalPlaatsenLabel = new JLabel("Plaatsen per rij: ");
		aantalPlaatsenLabel.setFont(font);
		aantalPlaatsenLabel.setBounds(229, 24, 146, 25);
		
		capaciteitLabel = new JLabel("Capaciteit: ");
		capaciteitLabel.setFont(font);
		capaciteitLabel.setBounds(9, 70, 208, 25);
		
		zaalPrijsLabel = new JLabel("Prijs: € ");
		zaalPrijsLabel.setFont(font);
		zaalPrijsLabel.setBounds(229, 70, 71, 25);
		
		plaatsLabel = new JLabel("Plaats: ");
		plaatsLabel.setFont(font);
		plaatsLabel.setBounds(9, 22, 473, 25);
		
		statusLabel = new JLabel("Status: ");
		statusLabel.setFont(font);
		statusLabel.setBounds(6, 68, 74, 25);
		
		plaatsPrijsLabel = new JLabel("Prijs: € ");
		plaatsPrijsLabel.setFont(font);
		plaatsPrijsLabel.setBounds(24, 119, 71, 25);
						
		//Set GUI
		this.add(eigenschappenPanel);
		this.add(zaalPanel);
		this.add(plaatsPanel);
		this.add(annulerenButton);
		this.add(bevestigButton);
		
		eigenschappenPanel.add(zaalnaamLabel);
		eigenschappenPanel.add(zaalNaamTField);
		eigenschappenPanel.add(locatieLabel);
		eigenschappenPanel.add(locatieTField);
		
		zaalPanel.add(aantalRijenLabel);
		zaalPanel.add(rijenSpinner);
		zaalPanel.add(aantalPlaatsenLabel);
		zaalPanel.add(plaatsenSpinner);
		zaalPanel.add(capaciteitLabel);
		zaalPanel.add(zaalPrijsLabel);
		zaalPanel.add(zaalPrijsTField);
		zaalPanel.add(zaalGenererenButton);
		
		plaatsPanel.add(plaatsLabel);
		plaatsPanel.add(statusLabel);
		plaatsPanel.add(beschikbaarButton);
		plaatsPanel.add(onbeschikbaarButton);
		plaatsPanel.add(plaatsPrijsLabel);
		plaatsPanel.add(plaatsPrijsTField);
		plaatsPanel.add(instellenButton);
							
		//Actionlisteners
		zaalGenererenButton.addActionListener(this);
		beschikbaarButton.addActionListener(this);
		beschikbaarButton.addActionListener(this);
		onbeschikbaarButton.addActionListener(this);
		instellenButton.addActionListener(this);
		annulerenButton.addActionListener(this);
		bevestigButton.addActionListener(this);
	}
	
	public void hideAllWindows()
	{
		if(this.zaalweergavenWindow != null)
		{
			this.zaalweergavenWindow.setVisible(false);
		}
	}
	
	private void ingeladenZaalWeergeven()
	{
		this.zaalNaamTField.setText(this.huidigeZaal.zaalNaamOpvragen());
		this.locatieTField.setText(this.huidigeZaal.locatieOpvragen());
		this.rijenSpinner.setValue(this.huidigeZaal.rijenOpvragen());
		this.plaatsenSpinner.setValue(this.huidigeZaal.plaatsenPerRij());
		this.capaciteitLabel.setText("Capaciteit: " + this.huidigeZaal.aantalPlaatsenOpvragen());
		this.zaalWeergavenAanmaken();
	}
	
	private void zaalWeergavenAanmaken()
	{
		Voorstelling nieuweVoorstelling = new Voorstelling(this.aangemaakteZaal, "", "", 0);
		if(this.zaalweergavenWindow != null)
		{
			this.zaalweergavenWindow.setVisible(false);
			this.zaalweergavenWindow.dispose();
		}
		this.zaalweergavenWindow = new ZaalweergavenWindow(RW2000);
		this.zaalweergavenWindow.zaalPreviewGenereren(nieuweVoorstelling, null, true);
		this.zaalweergavenWindow.observerableOpvragen().addObserver(new Observer()
		{
			public void update(Observable source, Object object)
			{
				selectieWijzigen((int[])object);
			}
		});
		this.zaalweergavenWindow.setVisible(true);
	}
	
	private void zaalGenereren()
	{
		this.zaalGegenereerd = true;
		this.geselecteerdePlaats = null;
		this.aangemaakteZaal = new Zaal("Nieuwe Zaal", "Nieuwe locatie", (int) this.rijenSpinner.getValue(), (int) this.plaatsenSpinner.getValue(),((Number) this.zaalPrijsTField.getValue()).doubleValue());	
		this.capaciteitLabel.setText("Capaciteit: " + this.aangemaakteZaal.aantalPlaatsenOpvragen());
		this.zaalWeergavenAanmaken();
	}
	
	private void zaalOpslaan()
	{
		this.aangemaakteZaal.zaalEigenschappenWijzigen(this.zaalNaamTField.getText(), this.locatieTField.getText());
		if(this.nieuweZaal == true)
		{
			this.RW2000.zaalToevoegen(this.aangemaakteZaal);
		}
		else
		{
			this.huidigeZaal = this.aangemaakteZaal;
			this.RW2000.saveZalen();	
		}
		this.hideAllWindows();
		this.setVisible(false);
	}

	private void selectieWijzigen(int[] nieuweIndex)
	{
		this.geselecteerdePlaats = this.aangemaakteZaal.plaatsOpvragen(nieuweIndex[0], nieuweIndex[1]);
		this.plaatsLabel.setText("Plaats: rij " + (this.aangemaakteZaal.rijenOpvragen() - this.geselecteerdePlaats.rijOpvragen()) + " Plaats " + (this.geselecteerdePlaats.plaatsOpvragen() + 1));
		this.plaatsPrijsTField.setText(Double.toString(this.geselecteerdePlaats.prijsOpvragen()));
	}

	private void plaatsBeschikbaar(Plaats plaats)
	{
		this.zaalweergavenWindow.selectieToestandWijzigen(1);
		this.geselecteerdePlaats.wijzigBeschikbaarheid(1);
	}
	
	private void plaatsOnbeschikbaar(Plaats plaats)
	{
		this.zaalweergavenWindow.selectieToestandWijzigen(0);
		this.geselecteerdePlaats.wijzigBeschikbaarheid(0);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		JButton b = (JButton)e.getSource();
		if(b == annulerenButton)
		{
			JOptionPane annulerenPane = new JOptionPane("Wenst u de wijzigingen te annuleren?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
			JDialog annulerenDialog = annulerenPane.createDialog("Zaalbewerkingen annuleren");
			annulerenDialog.setIconImage(RW2000Image);	
			annulerenDialog.setAlwaysOnTop(true);
			annulerenDialog.setVisible(true);
			if(annulerenPane.getValue() != null)
			{
				if(((Integer)annulerenPane.getValue()).intValue() == JOptionPane.YES_OPTION)
				{
					this.hideAllWindows();
					this.setVisible(false);
				}
			}
		}
		else if(b == bevestigButton)
		{
			if(this.zaalNaamTField.getText().equals("") == false)
			{
				if(this.locatieTField.getText().equals("") == false)
				{
					if(this.zaalGegenereerd == true)
					{
						if(this.nieuweZaal == true)
						{
							this.zaalOpslaan();
						}
						else
						{
							JOptionPane opmerkingZaalPane = new JOptionPane("Let op! Reeds gekoppelde voorstellingen behouden de oude instellingen van deze zaal.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION);
							JDialog opmerkingZaalDialog = opmerkingZaalPane.createDialog("Zaal gewijzigd");
							opmerkingZaalDialog.setIconImage(RW2000Image);	
							opmerkingZaalDialog.setAlwaysOnTop(true);
							opmerkingZaalDialog.setVisible(true);
							this.zaalOpslaan();
						}
					}
					else
					{
						JOptionPane geenZaalPane = new JOptionPane("Er is geen zaal gegenereerd! Maak eerst een zaal aan.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION);
						JDialog geenZaalDialog = geenZaalPane.createDialog("Geen zaal aangemaakt!");
						geenZaalDialog.setIconImage(RW2000Image);	
						geenZaalDialog.setAlwaysOnTop(true);
						geenZaalDialog.setVisible(true);
					}
				}
				else
				{
					this.locatieTField.requestFocus();
				}
			}
			else
			{
				this.zaalNaamTField.requestFocus();
			}
		}
		else if(b == zaalGenererenButton)
		{
			if(this.zaalPrijsTField.getValue() != null)
			{
				if(this.zaalGegenereerd == true)
				{
					JOptionPane nieuweZaalPane = new JOptionPane("De wijzigingen aan deze zaal zullen niet worden overgenomen in de nieuwe zaal! Wilt u doorgaan?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
					JDialog nieuweZaalDialog = nieuweZaalPane.createDialog("Nieuwe zaal genereren");
					nieuweZaalDialog.setIconImage(RW2000Image);	
					nieuweZaalDialog.setAlwaysOnTop(true);
					nieuweZaalDialog.setVisible(true);
					if(nieuweZaalPane.getValue() != null)
					{
						if(((Integer)nieuweZaalPane.getValue()).intValue() == JOptionPane.YES_OPTION)
						{
							this.zaalGenereren();
						}
					}
				}
				else
				{
					this.zaalGenereren();
				}
			}
			else
			{
				this.zaalPrijsTField.requestFocus();
			}
		}
		else if(b == beschikbaarButton)
		{
			if(this.geselecteerdePlaats != null)
			{
				if(this.geselecteerdePlaats.beschikbaarheidOpvragen() == 0)
				{
					this.plaatsBeschikbaar(this.geselecteerdePlaats);
				}
			}
		}
		else if(b == onbeschikbaarButton)
		{
			if(this.geselecteerdePlaats != null)
			{
				if(this.geselecteerdePlaats.beschikbaarheidOpvragen() != 0)
				{
					this.plaatsOnbeschikbaar(this.geselecteerdePlaats);
				}
			}
		}
		else if(b == instellenButton)
		{
			if(this.plaatsPrijsTField.getValue() != null)
			{
				if(this.geselecteerdePlaats != null)
				{
					this.geselecteerdePlaats.prijsInstellen(((Number) this.plaatsPrijsTField.getValue()).doubleValue());
				}
			}
		}
	}
}
