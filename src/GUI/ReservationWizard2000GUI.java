package GUI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import system.ReservationWizard2000;

public class ReservationWizard2000GUI extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	final Image RW2000Image = Toolkit.getDefaultToolkit().getImage("pictures/RW2000.png");
	private JFrame backgroundFrame, loginWindow, wijzigWachtwoordWindow;
	private LoketPanel loketPanel;
	private AdminPanel adminPanel;
	private JPanel loginPanel;
	private JButton loginButton, uitlogButton, afsluitButton, helpButton, userPanelButton, userSettingsButton;
	private JLabel backgroundPicture, notLoggedInLabel, usernameLabel;
	private ReservationWizard2000 RW2000;
	
	public ReservationWizard2000GUI(ReservationWizard2000 RW2000)
	{		
		//Declaraties
		this.RW2000 = RW2000;
		
		this.setLayout(null);
		
		Font font = new Font("Arial", Font.PLAIN, 20);
		
		loginPanel = new JPanel();
		loginPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
		loginPanel.setBounds(322, 20, 236, 80);
		loginPanel.setLayout(null);
		
		notLoggedInLabel = new JLabel("<html><CENTER>Niet ingelogd,</CENTER><CENTER>gelieve eerst in te loggen.</CENTER></html>");
		notLoggedInLabel.setFont(font);
		notLoggedInLabel.setBounds(3, 15, 236, 48);
		notLoggedInLabel.setHorizontalAlignment(SwingConstants.CENTER);
		notLoggedInLabel.setVisible(false);
		
		usernameLabel = new JLabel("");
		usernameLabel.setFont(font);
		usernameLabel.setBounds(0, 0, 236, 80);
		usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		usernameLabel.setVisible(false);
		
		afsluitButton = new JButton("Afsluiten");
		afsluitButton.setFont(font);
		afsluitButton.setBackground(Color.lightGray);
		afsluitButton.setBounds(322, 194, 236, 80);
		
		loginButton = new JButton("Aanmelden");
		loginButton.setFont(font);
		loginButton.setBackground(Color.lightGray);
		loginButton.setBounds(322, 108, 236, 80);
		loginButton.setVisible(false);
		
		uitlogButton = new JButton("Afmelden");
		uitlogButton.setFont(font);
		uitlogButton.setBackground(Color.lightGray);
		uitlogButton.setBounds(322, 108, 236, 80);
		uitlogButton.setVisible(false);
		
		helpButton = new JButton("Help");
		helpButton.setFont(font);
		helpButton.setBackground(Color.lightGray);
		helpButton.setBounds(40, 108, 236, 80);
		helpButton.setVisible(false);
		
		userPanelButton = new JButton("Open gebruikerspaneel");
		userPanelButton.setFont(new Font("Arial", Font.PLAIN, 18));
		userPanelButton.setBackground(Color.lightGray);
		userPanelButton.setBounds(40, 20, 236, 80);
		userPanelButton.setVisible(false);
		
		userSettingsButton = new JButton("Wijzig wachtwoord");
		userSettingsButton.setFont(font);
		userSettingsButton.setBackground(Color.lightGray);
		userSettingsButton.setBounds(40, 194, 236, 80);
		userSettingsButton.setVisible(false);
		
		backgroundPicture = new JLabel(new ImageIcon((new ImageIcon("pictures/RW2000.png").getImage().getScaledInstance(259, 259, Image.SCALE_SMOOTH))));
		backgroundPicture.setBounds(12, 12, 288, 259);
		backgroundPicture.setVisible(false);
		
		//Set GUI
		this.add(loginPanel);
		this.add(afsluitButton);
		this.add(loginButton);
		this.add(uitlogButton);
		this.add(helpButton);
		this.add(userPanelButton);
		this.add(userSettingsButton);
		this.add(backgroundPicture);
		
		loginPanel.add(notLoggedInLabel);
		loginPanel.add(usernameLabel);
				
		//Actionlisteners
		afsluitButton.addActionListener(this);
		loginButton.addActionListener(this);
		uitlogButton.addActionListener(this);
		helpButton.addActionListener(this);
		userPanelButton.addActionListener(this);
		userSettingsButton.addActionListener(this);		
		
		this.initialiseren();
		
		this.loginMode();	
	}
	
	private void initialiseren()
	{
		this.backgroundFrame = new BackgroundFrame();
		this.backgroundFrame.setVisible(true);
		this.setTitle("Reservation Wizard 2000 - The Ultimate Reservation Experience");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setIconImage(RW2000Image);
		this.setSize(586, 332);
		this.setResizable(false);
		this.setLocation(backgroundFrame.getWidth() - this.getWidth(), 0);
		this.setAlwaysOnTop(true);
		this.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
		this.setUndecorated(true);
		this.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				JOptionPane pane = new JOptionPane("Weet u zeker dat u Reservation Wizard 2000 wilt afsluiten?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
				JDialog dialog = pane.createDialog("Reservation Wizard 2000 afsluiten");
				dialog.setIconImage(RW2000Image);	
				dialog.setAlwaysOnTop(true);
				dialog.setVisible(true);
				if(pane.getValue() != null)
				{
					if(((Integer)pane.getValue()).intValue() == JOptionPane.YES_OPTION)
					{
						RW2000.shutdown();
					}
				}
			}
		});	
	}
	
	public void loginMode()
	{
		this.usernameLabel.setVisible(false);
		this.uitlogButton.setVisible(false);
		this.userSettingsButton.setVisible(false);
		this.userPanelButton.setVisible(false);
		this.helpButton.setVisible(false);
		
		this.hideAllWindows();		
		
		this.loginButton.setVisible(true);
		this.notLoggedInLabel.setVisible(true);
		this.backgroundPicture.setVisible(true);
		
		this.setVisible(true);
		
		this.loginWindow = new LoginWindow(this);
		loginWindow.setVisible(true);
	}
	
	public void loggedInMode()
	{
		this.loginButton.setVisible(false);
		this.notLoggedInLabel.setVisible(false);
		this.backgroundPicture.setVisible(false);
		
		String userLevel;
		
		switch(this.RW2000.getCurrentLogedIn().userLevelOpvragen())
		{
			case 1:
				userLevel = "gebruiker";
				this.loketPanel = new LoketPanel(this.RW2000);
				break;
			case 2:
				userLevel = "loket";
				this.loketPanel = new LoketPanel(this.RW2000);
				break;
			case 3:
				userLevel = "admin";
				this.adminPanel = new AdminPanel(this.RW2000);
				break;
			default:
				userLevel = "gebruiker";
				this.loketPanel = new LoketPanel(this.RW2000);
				break;
		}
		
		this.usernameLabel.setText("<html><CENTER>Welkom " + RW2000.getCurrentLogedIn().naamOpvragen() + ".</CENTER><CENTER>Ingelogd als " + userLevel + ".</CENTER></html>");
		this.usernameLabel.setVisible(true);
		this.uitlogButton.setVisible(true);
		this.userSettingsButton.setVisible(true);
		this.userPanelButton.setVisible(true);
		this.helpButton.setVisible(true);
		
		this.setVisible(true);
	}
	
	private void hideAllWindows()
	{
		if(this.loketPanel != null)
		{
			this.loketPanel.hideAllWindows();
			this.loketPanel.setVisible(false);
		}
		
		if(this.adminPanel != null)
		{
			this.adminPanel.hideAllWindows();
			this.adminPanel.setVisible(false);
		}
		
		if(this.wijzigWachtwoordWindow != null)
		{
			this.wijzigWachtwoordWindow.setVisible(false);
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		JButton b = (JButton)e.getSource();
		if(b == loginButton)
		{
			this.loginWindow.setVisible(true);
		}
		else if(b == uitlogButton)
		{
			this.RW2000.uitloggen();
			this.loginMode();
		}
		else if(b == afsluitButton)
		{
			JOptionPane pane = new JOptionPane("Weet u zeker dat u Reservation Wizard 2000 wilt afsluiten?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
			JDialog dialog = pane.createDialog("Reservation Wizard 2000 afsluiten");
			dialog.setIconImage(RW2000Image);	
			dialog.setAlwaysOnTop(true);
			dialog.setVisible(true);
			if(pane.getValue() != null)
			{
				if(((Integer)pane.getValue()).intValue() == JOptionPane.YES_OPTION)
				{
					RW2000.shutdown();
				}
			}
		}
		else if(b == helpButton)
		{
			new HelpDialog(RW2000Image);
		}
		else if(b == userPanelButton)
		{
			switch(this.RW2000.getCurrentLogedIn().userLevelOpvragen())
			{
				case 1:
					this.loketPanel.setVisible(true);
					break;
				case 2:
					this.loketPanel.setVisible(true);
					break;
				case 3:
					this.adminPanel.setVisible(true);
					break;
				default:
					this.loketPanel.setVisible(true);
					break;
			}	
		}
		else if(b == userSettingsButton)
		{
			if(this.wijzigWachtwoordWindow != null)
			{
				this.wijzigWachtwoordWindow.dispose();
			}
			this.wijzigWachtwoordWindow = new WijzigWachtwoordWindow(this.RW2000, this.RW2000.getCurrentLogedIn(), false);
			this.wijzigWachtwoordWindow.setVisible(true);
		}
	}
	
	public boolean inloggen(String naam, String pass)
	{
		return RW2000.inloggen(naam, pass);
	}
}