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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRootPane;
import javax.swing.JTextField;

import system.ReservationWizard2000;

public class GebruikerAanmakenWindow  extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	final Image RW2000Image = Toolkit.getDefaultToolkit().getImage("pictures/RW2000.png");
	private JPanel gebruikerPanel;
	private JButton aanmakenButton;
	private JLabel gebruikerLabel, permissieniveauLabel, nieuwPassLabel, bevestigPassLabel;
	private JTextField gebruikersnaamTField;
	private JComboBox<String> userLevelComboBox;
	private JPasswordField passTextField, bevestigPassTextField;
	private ReservationWizard2000 RW2000;
	
	public GebruikerAanmakenWindow(ReservationWizard2000 RW2000)
	{
		this.RW2000 = RW2000;
		
		this.setLayout(null);
		
		Font font = new Font("Arial", Font.PLAIN, 20);
		
		gebruikerPanel = new JPanel();
		gebruikerPanel.setBorder(BorderFactory.createTitledBorder("Gebruiker aanmaken"));
		gebruikerPanel.setBounds(12, 12, 416, 254);
		gebruikerPanel.setLayout(null);
		
		aanmakenButton = new JButton("Gebruiker aanmaken");
		aanmakenButton.setFont(new Font("Arial", Font.PLAIN, 18));
		aanmakenButton.setBackground(Color.lightGray);
		aanmakenButton.setBounds(112, 272, 208, 52);
		
		gebruikersnaamTField = new JTextField("");
		gebruikersnaamTField.setFont(font);
		gebruikersnaamTField.setBounds(212, 28, 178, 25);
		
		passTextField = new JPasswordField(10);
		passTextField.setFont(font);
		passTextField.setBounds(212, 144, 178, 31);
		
		bevestigPassTextField = new JPasswordField(10);
		bevestigPassTextField.setFont(font);
		bevestigPassTextField.setBounds(212, 192, 178, 31);
		
		String[] userLevels = {"Gebruiker", "Loket", "Admin"};
		userLevelComboBox = new JComboBox<String>(userLevels);
		userLevelComboBox.setSelectedIndex(0);
		userLevelComboBox.setFont(new Font("Arial", Font.PLAIN, 18));
		userLevelComboBox.setBounds(212, 76, 178, 31);
				
		gebruikerLabel = new JLabel("Gebruiker:");
		gebruikerLabel.setFont(font);
		gebruikerLabel.setBounds(108, 31, 98, 25);
		
		permissieniveauLabel = new JLabel("Permissieniveau:");
		permissieniveauLabel.setFont(font);
		permissieniveauLabel.setBounds(53, 78, 154, 25);
		
		nieuwPassLabel = new JLabel("Nieuw wachtwoord:");
		nieuwPassLabel.setFont(font);
		nieuwPassLabel.setBounds(33, 147, 174, 25);
		
		bevestigPassLabel = new JLabel("Bevestig wachtwoord:");
		bevestigPassLabel.setFont(font);
		bevestigPassLabel.setBounds(12, 195, 194, 25);
		
		//Set GUI
		this.add(gebruikerPanel);
		this.add(aanmakenButton);
						
		gebruikerPanel.add(gebruikersnaamTField);
		gebruikerPanel.add(passTextField);
		gebruikerPanel.add(bevestigPassTextField);
		gebruikerPanel.add(userLevelComboBox);
		gebruikerPanel.add(gebruikerLabel);
		gebruikerPanel.add(permissieniveauLabel);
		gebruikerPanel.add(nieuwPassLabel);
		gebruikerPanel.add(bevestigPassLabel);
										
		//Actionlisteners
		aanmakenButton.addActionListener(this);
		
		this.initialiseren();
	}
	
	private void initialiseren()
	{
		this.setTitle("Reservatiemaker");
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setIconImage(RW2000Image);
		this.setSize(460, 371);
		this.setResizable(false);
		this.setLocation(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width/2 - this.getWidth()/2, GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height/2 - this.getHeight()/2);
		this.setAlwaysOnTop(true);
		this.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
		this.setUndecorated(true);
	}	
	
	public void actionPerformed(ActionEvent e)
	{
		JButton b = (JButton)e.getSource();
		if(b == aanmakenButton)
		{
			if(this.gebruikersnaamTField.getText().equals("") == false)
			{
				if(String.valueOf(passTextField.getPassword()).length() > 3)
				{
					if(String.valueOf(passTextField.getPassword()).equals(String.valueOf(bevestigPassTextField.getPassword())))
					{
						if(this.RW2000.gebruikerToevoegen(this.gebruikersnaamTField.getText(), String.valueOf(passTextField.getPassword()), (this.userLevelComboBox.getSelectedIndex() + 1)))
						{
							JOptionPane pane = new JOptionPane("De gebuiker: " + this.gebruikersnaamTField.getText() + " is aangemaakt.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION);
							JDialog dialog = pane.createDialog("Gebruiker aangemaakt");
							dialog.setIconImage(RW2000Image);	
							dialog.setAlwaysOnTop(true);
							dialog.setVisible(true);
					
							this.setVisible(false);
						}
						else
						{
							JOptionPane pane = new JOptionPane("De opgegeven gebruiker bestaat reeds! Kies een andere naam voor deze gebruiker.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION);
							JDialog dialog = pane.createDialog("Gebruiker bestaat reeds");
							dialog.setIconImage(RW2000Image);	
							dialog.setAlwaysOnTop(true);
							dialog.setVisible(true);
						
							gebruikersnaamTField.setText("");
							gebruikersnaamTField.requestFocus();
							passTextField.setText("");
							bevestigPassTextField.setText("");
						}
					}
					else
					{
						JOptionPane pane = new JOptionPane("De ingegeven wachtwoorden komen niet overeen.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION);
						JDialog dialog = pane.createDialog("Gebruiker kan niet aangemaakt worden");
						dialog.setIconImage(RW2000Image);	
						dialog.setAlwaysOnTop(true);
						dialog.setVisible(true);
					
						passTextField.setText("");
						passTextField.requestFocus();
						bevestigPassTextField.setText("");
					}
				}
				else
				{
					JOptionPane pane = new JOptionPane("Het ingegeven wachtwoord moet minstens 4 karakters lang zijn.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION);
					JDialog dialog = pane.createDialog("Gebruiker kan niet aangemaakt worden");
					dialog.setIconImage(RW2000Image);	
					dialog.setAlwaysOnTop(true);
					dialog.setVisible(true);
				
					passTextField.setText("");
					passTextField.requestFocus();
					bevestigPassTextField.setText("");
				}
			}
		}
	}
}