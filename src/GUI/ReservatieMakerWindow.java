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
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import system.Reservatie;
import system.ReservationWizard2000;
import system.Voorstelling;

public class ReservatieMakerWindow extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	final Image RW2000Image = Toolkit.getDefaultToolkit().getImage("pictures/RW2000.png");
	private JPanel voorstellingPanel, reservatiePanel, plaatsToekenningPanel;
	private JButton annulerenButton, handmatigButton, automatischButton;
	private JLabel reservatieLabel, voorstellingNaamLabel, locatieLabel, tijdLabel, beschikbaarLabel, reservatieNaamLabel, reservatieNummerLabel, aantalPlaatsenLabel;
	private JTextField reservatieNaamTField;
	private JSpinner aantalPlaatsenSpinner;
	private ReservationWizard2000 RW2000;
	private JFrame parent;
	private PlaatsToewijsWindow plaatsToewijsWindow;
	private Voorstelling huidigeVoorstelling;
	
	public ReservatieMakerWindow(ReservationWizard2000 RW2000, Voorstelling voorstelling, JFrame parent)
	{
		this.RW2000 = RW2000;
		this.parent = parent;
		this.huidigeVoorstelling = voorstelling;
		
		this.setLayout(null);
		
		Font font = new Font("Arial", Font.PLAIN, 20);
		
		voorstellingPanel = new JPanel();
		voorstellingPanel.setBorder(BorderFactory.createTitledBorder("Voorstelling"));
		voorstellingPanel.setBounds(12, 46, 539, 100);
		voorstellingPanel.setLayout(null);
		
		reservatiePanel = new JPanel();
		reservatiePanel.setBorder(BorderFactory.createTitledBorder("Reservatie"));
		reservatiePanel.setBounds(12, 152, 539, 160);
		reservatiePanel.setLayout(null);
		
		plaatsToekenningPanel = new JPanel();
		plaatsToekenningPanel.setBorder(BorderFactory.createTitledBorder("Plaatsen selecteren"));
		plaatsToekenningPanel.setBounds(12, 318, 539, 153);
		plaatsToekenningPanel.setLayout(null);
		
		handmatigButton = new JButton("Handmatig selecteren");
		handmatigButton.setFont(font);
		handmatigButton.setBackground(Color.lightGray);
		handmatigButton.setBounds(276, 21, 242, 117);
		
		automatischButton = new JButton("Automatisch toekennen");
		automatischButton.setFont(font);
		automatischButton.setBackground(Color.lightGray);
		automatischButton.setBounds(21, 21, 242, 117);
		
		annulerenButton = new JButton("Annuleren");
		annulerenButton.setFont(font);
		annulerenButton.setBackground(Color.lightGray);
		annulerenButton.setBounds(107, 478, 350, 74);
		
		reservatieNaamTField = new JTextField("");
		reservatieNaamTField.setFont(font);
		reservatieNaamTField.setBounds(78, 30, 445, 26);
		
		SpinnerModel sModel = new SpinnerNumberModel(1, 1, voorstelling.zaalOpvragen().beschikbaarheidOpvragen(), 1);
		aantalPlaatsenSpinner = new JSpinner(sModel);
		aantalPlaatsenSpinner.setFont(font);
		aantalPlaatsenSpinner.setBounds(160, 77, 97, 26);
				
		reservatieLabel = new JLabel("Reservatie");
		reservatieLabel.setFont(new Font("Arial", Font.PLAIN, 26));
		reservatieLabel.setBounds(218, 9, 127, 33);
		
		voorstellingNaamLabel = new JLabel("Voorstelling: " + this.huidigeVoorstelling.voorstellingsNaamOpvragen());
		voorstellingNaamLabel.setFont(font);
		voorstellingNaamLabel.setBounds(9, 20, 515, 25);
		
		locatieLabel = new JLabel("Locatie: " + this.huidigeVoorstelling.zaalOpvragen().locatieOpvragen());
		locatieLabel.setFont(font);
		locatieLabel.setBounds(46, 58, 235, 25);
		
		tijdLabel = new JLabel("Tijd: " + this.huidigeVoorstelling.tijdOpvragen());
		tijdLabel.setFont(font);
		tijdLabel.setBounds(285, 58, 230, 25);
		
		beschikbaarLabel = new JLabel("Beschikbaar: " + this.huidigeVoorstelling.zaalOpvragen().beschikbaarheidOpvragen());
		beschikbaarLabel.setFont(font);
		beschikbaarLabel.setBounds(285, 75, 186, 25);
		
		reservatieNaamLabel = new JLabel("Naam: ");
		reservatieNaamLabel.setFont(font);
		reservatieNaamLabel.setBounds(9, 29, 75, 25);
		
		reservatieNummerLabel = new JLabel("Reservatienummer: " + this.reservatienummerBepalen());
		reservatieNummerLabel.setFont(font);
		reservatieNummerLabel.setBounds(9, 120, 263, 25);
		
		aantalPlaatsenLabel = new JLabel("Aantal plaatsen: ");
		aantalPlaatsenLabel.setFont(font);
		aantalPlaatsenLabel.setBounds(9, 75, 153, 25);
		
		//Set GUI
		this.add(voorstellingPanel);
		this.add(reservatiePanel);
		this.add(plaatsToekenningPanel);
		this.add(annulerenButton);
		this.add(reservatieLabel);
						
		voorstellingPanel.add(voorstellingNaamLabel);
		voorstellingPanel.add(locatieLabel);
		voorstellingPanel.add(tijdLabel);
		
		reservatiePanel.add(reservatieNaamTField);
		reservatiePanel.add(aantalPlaatsenSpinner);
		reservatiePanel.add(reservatieNaamLabel);
		reservatiePanel.add(aantalPlaatsenLabel);
		reservatiePanel.add(beschikbaarLabel);
		reservatiePanel.add(reservatieNummerLabel);
		
		plaatsToekenningPanel.add(handmatigButton);
		plaatsToekenningPanel.add(automatischButton);
										
		//Actionlisteners
		handmatigButton.addActionListener(this);
		automatischButton.addActionListener(this);
		annulerenButton.addActionListener(this);
		
		this.initialiseren();
	}
	
	private void initialiseren()
	{
		this.setTitle("Reservatiemaker");
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setIconImage(RW2000Image);
		this.setSize(579, 602);
		this.setResizable(false);
		this.setLocation(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width/2 - this.getWidth()/2, GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height/2 - this.getHeight()/2);
		this.setAlwaysOnTop(true);
		this.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
		this.setUndecorated(true);
	}	
	
	private int reservatienummerBepalen()
	{
		if(this.huidigeVoorstelling.reservatieOpvragen().size() != 0)
		{
			return this.huidigeVoorstelling.reservatieOpvragen().get(this.huidigeVoorstelling.reservatieOpvragen().size() - 1).reservatieNummerOpvragen() + 1;
		}
		else
		{
			return 100;
		}
	}
	
	public void hideAllWindows()
	{
		if(this.plaatsToewijsWindow != null)
		{
			plaatsToewijsWindow.hideAllWindows();
			plaatsToewijsWindow.setVisible(false);
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
		else if(b == automatischButton)
		{
			if(this.reservatieNaamTField.getText().equals(""))
			{
				this.reservatieNaamTField.requestFocus(true);
			}
			else
			{
				Reservatie reservatie = new Reservatie(this.reservatieNaamTField.getText(), this.reservatienummerBepalen());
				if(this.plaatsToewijsWindow == null)
				{
					this.plaatsToewijsWindow = new PlaatsToewijsWindow(RW2000, this.huidigeVoorstelling, reservatie, (int)this.aantalPlaatsenSpinner.getValue(), this.parent);
				}
				this.plaatsToewijsWindow.automatischPlaatsToewijzen();
				this.plaatsToewijsWindow.setVisible(true);
				this.setVisible(false);
			}
		}
		else if(b == handmatigButton)
		{
			if(this.reservatieNaamTField.getText().equals(""))
			{
				this.reservatieNaamTField.requestFocus(true);
			}
			else
			{
				Reservatie reservatie = new Reservatie(this.reservatieNaamTField.getText(), this.reservatienummerBepalen());
				if(this.plaatsToewijsWindow == null)
				{
					this.plaatsToewijsWindow = new PlaatsToewijsWindow(RW2000, this.huidigeVoorstelling, reservatie, (int)this.aantalPlaatsenSpinner.getValue(), this.parent);
				}
				this.plaatsToewijsWindow.setVisible(true);
				this.setVisible(false);
			}
		}
	}
}
