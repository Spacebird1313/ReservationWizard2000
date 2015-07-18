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
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import system.Gebruiker;
import system.ReservationWizard2000;
import system.Voorstelling;
import system.Zaal;

public class AdminPanel extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	final Image RW2000Image = Toolkit.getDefaultToolkit().getImage("pictures/RW2000.png");
	private LoketPanel loketWindow;
	private VoorstellingSelectorWindow voorstellingSelectorWindow;
	private ZaalSelectorWindow zaalSelectorWindow;
	private VoorstellingWijzigWindow voorstellingWijzigWindow;
	private ZaalWijzigWindow zaalWijzigWindow;
	private GebruikerSelectorWindow gebruikerSelectorWindow;
	private WijzigWachtwoordWindow wijzigWachtwoordWindow;
	private GebruikerAanmakenWindow gebruikerAanmakenWindow;
	private JPanel instellingenPanel;
	private JButton voorstellingenButton, zalenButton, reservatieButton, gebruikersbeheerButton;
	private ReservationWizard2000 RW2000;
	
	public AdminPanel(ReservationWizard2000 RW2000)
	{
		this.RW2000 = RW2000;
		
		this.setLayout(null);
			
		Font font = new Font("Arial", Font.PLAIN, 20);
		
		instellingenPanel = new JPanel();
		instellingenPanel.setBorder(BorderFactory.createTitledBorder("Instellingen"));
		instellingenPanel.setBounds(12, 12, 373, 320);
		instellingenPanel.setLayout(null);
		
		voorstellingenButton = new JButton("Voorstellingen beheren");
		voorstellingenButton.setFont(font);
		voorstellingenButton.setBackground(Color.lightGray);
		voorstellingenButton.setBounds(14, 20, 345, 68);
		
		zalenButton = new JButton("Zalen beheren");
		zalenButton.setFont(font);
		zalenButton.setBackground(Color.lightGray);
		zalenButton.setBounds(14, 94, 345, 68);
		
		reservatieButton = new JButton("Reservaties");
		reservatieButton.setFont(font);
		reservatieButton.setBackground(Color.lightGray);
		reservatieButton.setBounds(14, 168, 345, 68);
		
		gebruikersbeheerButton = new JButton("Gebruikersbeheer");
		gebruikersbeheerButton.setFont(font);
		gebruikersbeheerButton.setBackground(Color.lightGray);
		gebruikersbeheerButton.setBounds(14, 242, 345, 68);
						
		//Set GUI
		this.add(instellingenPanel);
		
		instellingenPanel.add(voorstellingenButton);
		instellingenPanel.add(zalenButton);
		instellingenPanel.add(reservatieButton);
		instellingenPanel.add(gebruikersbeheerButton);
							
		//Actionlisteners
		voorstellingenButton.addActionListener(this);
		zalenButton.addActionListener(this);
		reservatieButton.addActionListener(this);
		gebruikersbeheerButton.addActionListener(this);

		this.initialiseren();
	}
		
		
	private void initialiseren()
	{
		this.setTitle("Reservation Wizard 2000 - Admin");
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setIconImage(RW2000Image);
		this.setSize(413, 385);
		this.setResizable(false);
		this.setLocation(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width - this.getWidth(), GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height - this.getHeight());
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
	
	public void hideAllWindows()
	{
		if(loketWindow != null)
		{
			loketWindow.hideAllWindows();
			loketWindow.setVisible(false);
		}
		if(voorstellingSelectorWindow != null)
		{
			voorstellingSelectorWindow.setVisible(false);
		}
		if(voorstellingWijzigWindow != null)
		{
			voorstellingWijzigWindow.hideAllWindows();
			voorstellingWijzigWindow.setVisible(false);
		}
		if(zaalSelectorWindow != null)
		{
			zaalSelectorWindow.setVisible(false);
		}
		if(zaalWijzigWindow != null)
		{
			zaalWijzigWindow.hideAllWindows();
			zaalWijzigWindow.setVisible(false);
		}
		if(gebruikerSelectorWindow != null)
		{
			gebruikerSelectorWindow.setVisible(false);
		}
		if(wijzigWachtwoordWindow != null)
		{
			wijzigWachtwoordWindow.setVisible(false);
		}
		if(gebruikerAanmakenWindow != null)
		{
			gebruikerAanmakenWindow.setVisible(false);
		}
	}
	
	private void voorstellingWijzigen(Voorstelling voorstelling)
	{
		if(voorstelling == null)
		{
			this.voorstellingWijzigWindow = new VoorstellingWijzigWindow(this.RW2000);
			this.voorstellingWijzigWindow.setVisible(true);
		}
		else
		{
			this.voorstellingWijzigWindow = new VoorstellingWijzigWindow(this.RW2000, voorstelling);
			this.voorstellingWijzigWindow.setVisible(true);
		}
	}
	
	private void voorstellingVerwijderen(Voorstelling voorstelling)
	{
		if(voorstelling != null)
		{
			JOptionPane voorstellingVerwijderenPane = new JOptionPane("Wilt u de voorstelling: " + voorstelling.voorstellingsNaamOpvragen() + " echt verwijderen? Dit kan niet ongedaan gemaakt worden!", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
			JDialog voorstellingVerwijderendialog = voorstellingVerwijderenPane.createDialog("Voorstelling verwijderen");
			voorstellingVerwijderendialog.setIconImage(RW2000Image);	
			voorstellingVerwijderendialog.setAlwaysOnTop(true);
			voorstellingVerwijderendialog.setVisible(true);
			if(voorstellingVerwijderenPane.getValue() != null)
			{
				if(((Integer)voorstellingVerwijderenPane.getValue()).intValue() == JOptionPane.YES_OPTION)
				{
					this.RW2000.voorstellingVerwijderen(voorstelling);
				}
			}
		}
	}
	
	private void zaalWijzigen(Zaal zaal)
	{
		if(zaal == null)
		{
			this.zaalWijzigWindow = new ZaalWijzigWindow(this.RW2000);
			this.zaalWijzigWindow.setVisible(true);
		}
		else
		{
			this.zaalWijzigWindow = new ZaalWijzigWindow(this.RW2000, zaal);
			this.zaalWijzigWindow.setVisible(true);
		}
	}
	
	private void zaalVerwijderen(Zaal zaal)
	{
		if(zaal != null)
		{
			JOptionPane zaalVerwijderenPane = new JOptionPane("Wilt u de zaal: " + zaal.zaalNaamOpvragen() + " echt verwijderen? Dit kan niet ongedaan gemaakt worden! Gekoppelde voorstellingen worden niet beïnvloed.", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
			JDialog zaalVerwijderendialog = zaalVerwijderenPane.createDialog("Zaal verwijderen");
			zaalVerwijderendialog.setIconImage(RW2000Image);	
			zaalVerwijderendialog.setAlwaysOnTop(true);
			zaalVerwijderendialog.setVisible(true);
			if(zaalVerwijderenPane.getValue() != null)
			{
				if(((Integer)zaalVerwijderenPane.getValue()).intValue() == JOptionPane.YES_OPTION)
				{
					this.RW2000.zaalVerwijderen(zaal);
				}
			}
		}
	}
	
	public void gebruikersWachtwoordWijzigen(Gebruiker gebruiker)
	{
		if(gebruiker.naamOpvragen().equals(this.RW2000.getCurrentLogedIn().naamOpvragen()) == false)
		{
			if(this.wijzigWachtwoordWindow != null)
			{
				this.wijzigWachtwoordWindow.dispose();
			}
			this.wijzigWachtwoordWindow = new WijzigWachtwoordWindow(this.RW2000, gebruiker, true);
			this.wijzigWachtwoordWindow.setVisible(true);
		}
		else
		{
			JOptionPane pane = new JOptionPane("Wijzig het wachtwoord van dit profiel in het hoofdpaneel.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION);
			JDialog dialog = pane.createDialog("Wachtwoord kan niet gewijzigd worden.");
			dialog.setIconImage(RW2000Image);	
			dialog.setAlwaysOnTop(true);
			dialog.setVisible(true);
		}
	}
	
	public void gebruikerVerwijderen(Gebruiker gebruiker)
	{
		if(gebruiker.naamOpvragen().equals(this.RW2000.getCurrentLogedIn().naamOpvragen()) == false)
		{
			JOptionPane gebruikerVerwijderenPane = new JOptionPane("Wilt u de gebruiker: " + gebruiker.naamOpvragen() + " echt verwijderen? Dit kan niet ongedaan gemaakt worden!", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
			JDialog gebruikerVerwijderendialog = gebruikerVerwijderenPane.createDialog("Gebruiker verwijderen");
			gebruikerVerwijderendialog.setIconImage(RW2000Image);	
			gebruikerVerwijderendialog.setAlwaysOnTop(true);
			gebruikerVerwijderendialog.setVisible(true);
			if(gebruikerVerwijderenPane.getValue() != null)
			{
				if(((Integer)gebruikerVerwijderenPane.getValue()).intValue() == JOptionPane.YES_OPTION)
				{
					this.RW2000.gebruikerVerwijderen(gebruiker.naamOpvragen());
				}
			}
		}
		else
		{
			JOptionPane pane = new JOptionPane("Dit profiel is ingelogd en kan niet verwijderd worden.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION);
			JDialog dialog = pane.createDialog("Profiel kan niet verwijderd worden.");
			dialog.setIconImage(RW2000Image);	
			dialog.setAlwaysOnTop(true);
			dialog.setVisible(true);
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		JButton b = (JButton)e.getSource();
		if(b == voorstellingenButton)
		{
			Object[] opties = {"Nieuwe voorstelling aanmaken", "Bestaande voorstelling wijzigen", "Voorstelling verwijderen", "Annuleren"};
			JOptionPane pane = new JOptionPane("Wat wilt u doen?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_CANCEL_OPTION, null, opties);
			JDialog dialog = pane.createDialog("Voorstellingen beheren");
			dialog.setIconImage(RW2000Image);	
			dialog.setAlwaysOnTop(true);
			dialog.setVisible(true);
			if(pane.getValue() != null)
			{
				if(pane.getValue().equals(opties[0]))
				{
					this.hideAllWindows();
					this.voorstellingWijzigen(null);
				}
				else if(pane.getValue().equals(opties[1]))
				{
					this.hideAllWindows();					
					this.voorstellingSelectorWindow = new VoorstellingSelectorWindow(this.RW2000);
					this.voorstellingSelectorWindow.observerableOpvragen().addObserver(new Observer()
					{
						public void update(Observable source, Object object)
						{
							voorstellingWijzigen((Voorstelling)object);
						}
					});
					this.voorstellingSelectorWindow.setVisible(true);
				}
				else if(pane.getValue().equals(opties[2]))
				{
					this.hideAllWindows();					
					this.voorstellingSelectorWindow = new VoorstellingSelectorWindow(this.RW2000);
					this.voorstellingSelectorWindow.observerableOpvragen().addObserver(new Observer()
					{
						public void update(Observable source, Object object)
						{
							voorstellingVerwijderen((Voorstelling)object);
						}
					});
					this.voorstellingSelectorWindow.setVisible(true);
				}
			}
		}
		else if(b == zalenButton)
		{
			Object[] opties = {"Nieuwe zaal aanmaken", "Bestaande zaal wijzigen", "Zaal verwijderen", "Annuleren"};
			JOptionPane pane = new JOptionPane("Wat wilt u doen?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_CANCEL_OPTION, null, opties);
			JDialog dialog = pane.createDialog("Zalen beheren");
			dialog.setIconImage(RW2000Image);	
			dialog.setAlwaysOnTop(true);
			dialog.setVisible(true);
			if(pane.getValue() != null)
			{
				if(pane.getValue().equals(opties[0]))
				{
					this.hideAllWindows();
					this.zaalWijzigen(null);
				}
				else if(pane.getValue().equals(opties[1]))
				{
					this.hideAllWindows();					
					this.zaalSelectorWindow = new ZaalSelectorWindow(this.RW2000);
					this.zaalSelectorWindow.observerableOpvragen().addObserver(new Observer()
					{
						public void update(Observable source, Object object)
						{
							zaalWijzigen((Zaal)object);
						}
					});
					this.zaalSelectorWindow.setVisible(true);
				}
				else if(pane.getValue().equals(opties[2]))
				{
					this.hideAllWindows();					
					this.zaalSelectorWindow = new ZaalSelectorWindow(this.RW2000);
					this.zaalSelectorWindow.observerableOpvragen().addObserver(new Observer()
					{
						public void update(Observable source, Object object)
						{
							zaalVerwijderen((Zaal)object);
						}
					});
					this.zaalSelectorWindow.setVisible(true);
				}
			}
		}
		else if(b == reservatieButton)
		{
			this.hideAllWindows();
			if(loketWindow == null)
			{
				this.loketWindow = new LoketPanel(RW2000);
			}
			else
			{
				this.loketWindow.dispose();
				this.loketWindow = new LoketPanel(RW2000);
			}
			this.loketWindow.setVisible(true);
		}
		else if(b == gebruikersbeheerButton)
		{
			Object[] opties = {"Nieuwe gebruiker aanmaken", "Gebruikerswachtwoord wijzigen", "Gebruiker verwijderen", "Annuleren"};
			JOptionPane pane = new JOptionPane("Wat wilt u doen?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_CANCEL_OPTION, null, opties);
			JDialog dialog = pane.createDialog("Gebruikersbeheer");
			dialog.setIconImage(RW2000Image);	
			dialog.setAlwaysOnTop(true);
			dialog.setVisible(true);
			if(pane.getValue() != null)
			{
				if(pane.getValue().equals(opties[0]))
				{
					this.hideAllWindows();
					if(this.gebruikerAanmakenWindow == null)
					{
						this.gebruikerAanmakenWindow = new GebruikerAanmakenWindow(RW2000);
					}
					else
					{
						this.gebruikerAanmakenWindow.dispose();
						this.gebruikerAanmakenWindow = new GebruikerAanmakenWindow(RW2000);
					}
					this.gebruikerAanmakenWindow.setVisible(true);
				}
				else if(pane.getValue().equals(opties[1]))
				{
					this.hideAllWindows();					
					this.gebruikerSelectorWindow = new GebruikerSelectorWindow(this.RW2000);
					this.gebruikerSelectorWindow.observerableOpvragen().addObserver(new Observer()
					{
						public void update(Observable source, Object object)
						{
							gebruikersWachtwoordWijzigen((Gebruiker)object);
						}
					});
					this.gebruikerSelectorWindow.setVisible(true);
				}
				else if(pane.getValue().equals(opties[2]))
				{
					this.hideAllWindows();					
					this.gebruikerSelectorWindow = new GebruikerSelectorWindow(this.RW2000);
					this.gebruikerSelectorWindow.observerableOpvragen().addObserver(new Observer()
					{
						public void update(Observable source, Object object)
						{
							gebruikerVerwijderen((Gebruiker)object);
						}
					});
					this.gebruikerSelectorWindow.setVisible(true);
				}
			}
		}
	}
}
