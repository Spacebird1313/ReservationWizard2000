package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRootPane;
import javax.swing.SwingConstants;

import system.Gebruiker;
import system.ReservationWizard2000;

public class WijzigWachtwoordWindow extends JFrame implements ActionListener, KeyListener
{
	private static final long serialVersionUID = 1L;
	final Image RW2000Image = Toolkit.getDefaultToolkit().getImage("pictures/RW2000.png");
	private JButton wijzigPassButton;
	private JPasswordField oudPassTextField, nieuwPassTextField, bevestigNieuwPassTextField;
	private JLabel userNameLabel, oudPassLabel, nieuwPassLabel, bevestigNieuwPassLabel, checkStateLabel;
	private boolean changePassFree;
	private ReservationWizard2000 RW2000;
	private Gebruiker user;
	
	public WijzigWachtwoordWindow(ReservationWizard2000 RW2000, Gebruiker user, boolean changePassFree)
	{
		this.RW2000 = RW2000;
		this.user = user;
		this.changePassFree = changePassFree;
		
		this.initialiseren();
		
		this.setLayout(null);
		
		Font font = new Font("Arial", Font.PLAIN, 20);
		
		userNameLabel = new JLabel("Gebruiker: " + user.naamOpvragen());
		userNameLabel.setFont(font);
		userNameLabel.setBounds(112, 25, 300, 25);
		
		oudPassLabel = new JLabel("Oud wachtwoord: ");
		oudPassLabel.setFont(font);
		oudPassLabel.setBounds(49, 89, 165, 25);
		
		nieuwPassLabel = new JLabel("Nieuw wachtwoord: ");
		nieuwPassLabel.setFont(font);
		nieuwPassLabel.setBounds(34, 134, 180, 25);
		
		bevestigNieuwPassLabel = new JLabel("Bevestig wachtwoord: ");
		bevestigNieuwPassLabel.setFont(font);
		bevestigNieuwPassLabel.setBounds(12, 179, 210, 25);
		
		oudPassTextField = new JPasswordField(10);
		oudPassTextField.setFont(font);
		oudPassTextField.setBounds(228, 86, 178, 31);
		
		nieuwPassTextField = new JPasswordField(10);
		nieuwPassTextField.setFont(font);
		nieuwPassTextField.setBounds(228, 131, 178, 31);
		
		bevestigNieuwPassTextField = new JPasswordField(10);
		bevestigNieuwPassTextField.setFont(font);
		bevestigNieuwPassTextField.setBounds(228, 176, 178, 31);
		
		wijzigPassButton = new JButton("Wijzig wachtwoord");
		wijzigPassButton.setFont(new Font("Arial", Font.PLAIN, 18));
		wijzigPassButton.setBackground(Color.lightGray);
		wijzigPassButton.setBounds(126, 269, 208, 52);
		
		checkStateLabel = new JLabel("");
		checkStateLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		checkStateLabel.setForeground(Color.RED);
		checkStateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		checkStateLabel.setBounds(72, 221, 332, 36);
		checkStateLabel.setVisible(false);
		
		if(changePassFree)																//Admin mag paswoorden wijzigen zonder oorspronkelijke paswoord in te moeten vullen.
		{
			oudPassLabel.setVisible(false);
			oudPassTextField.setVisible(false);
		}
		
		//Set GUI
		this.add(userNameLabel);
		this.add(oudPassLabel);
		this.add(nieuwPassLabel);
		this.add(bevestigNieuwPassLabel);
		this.add(oudPassTextField);
		this.add(nieuwPassTextField);
		this.add(bevestigNieuwPassTextField);
		this.add(wijzigPassButton);
		this.add(checkStateLabel);
		
		//Actionlisteners
		wijzigPassButton.addActionListener(this);
		
		oudPassTextField.requestFocus();
		
		oudPassTextField.addKeyListener(this);
		nieuwPassTextField.addKeyListener(this);
		bevestigNieuwPassTextField.addKeyListener(this);
	}

	 private void initialiseren()
	{
		Image LoginIcon = Toolkit.getDefaultToolkit().getImage("pictures/LoginIcon.png");
		this.setTitle("Wachtwoord wijzigen");
		this.setSize(485, 371);
		this.setResizable(false);
		this.setIconImage(LoginIcon);
		this.setAlwaysOnTop(true);
		this.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
		this.setUndecorated(true);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setLocation(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width/2 - this.getWidth()/2, GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height/2 - this.getHeight()/2);
		this.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				oudPassTextField.setText("");
				oudPassTextField.requestFocus();
				nieuwPassTextField.setText("");
				bevestigNieuwPassTextField.setText("");
				checkStateLabel.setVisible(false);
			}
		});
	}
	
	public void actionPerformed(ActionEvent e)
	{
		JButton b = (JButton)e.getSource();	
		if(b == wijzigPassButton)
		{
			if(String.valueOf(nieuwPassTextField.getPassword()).length() > 3)
			{
				if(String.valueOf(nieuwPassTextField.getPassword()).equals(String.valueOf(bevestigNieuwPassTextField.getPassword())))
				{
					int authenticatieVereist;
					if(this.changePassFree == true)
					{
						authenticatieVereist = 0;
					}
					else
					{
						authenticatieVereist = 1;
					}
				
					if(this.RW2000.wachtwoordWijzigen(user.naamOpvragen(), String.valueOf(oudPassTextField.getPassword()), String.valueOf(nieuwPassTextField.getPassword()), authenticatieVereist))
					{
						JOptionPane pane = new JOptionPane("Het wachtwoord is succesvol gewijzigd.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION);
						JDialog dialog = pane.createDialog("Wachtwoord gewijzigd");
						dialog.setIconImage(RW2000Image);	
						dialog.setAlwaysOnTop(true);
						dialog.setVisible(true);
					
						this.setVisible(false);
					}
					else
					{
						checkStateLabel.setText("<html><CENTER>Wachtwoord wijzigen gefaald!</CENTER><CENTER>Het ingegeven wachtwoord is incorrect.</CENTER></html>");
						checkStateLabel.setVisible(true);
					}
				}
				else
				{
					checkStateLabel.setText("<html><CENTER>Wachtwoord wijzigen gefaald!</CENTER><CENTER>Het nieuwe ingegeven wachtwoord komt niet overeen.</CENTER></html>");
					checkStateLabel.setVisible(true);
				}
				oudPassTextField.setText("");
				oudPassTextField.requestFocus();
				nieuwPassTextField.setText("");
				bevestigNieuwPassTextField.setText("");
			}
			else
			{
				checkStateLabel.setText("<html><CENTER>Wachtwoord wijzigen gefaald!</CENTER><CENTER>Het ingegeven wachtwoord moet minstens 4 karakters lang zijn.</CENTER></html>");
				checkStateLabel.setVisible(true);
				oudPassTextField.setText("");
				oudPassTextField.requestFocus();
				nieuwPassTextField.setText("");
				bevestigNieuwPassTextField.setText("");
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		checkStateLabel.setVisible(false);
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}
	
	
}
