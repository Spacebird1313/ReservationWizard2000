package GUI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class LoginWindow extends JFrame implements ActionListener, KeyListener
{
	private static final long serialVersionUID = 1L;
	private JButton loginButton;
	private JTextField loginNameTextField;
	private JPasswordField passTextField;
	private JLabel lNameLabel, passLabel, loginStateLabel;
	private ReservationWizard2000GUI parent;
	
	public LoginWindow(ReservationWizard2000GUI parent)
	{
		this.parent = parent;
		
		this.initialiseren();
		
		this.setLayout(null);
		
		Font font = new Font("Arial", Font.PLAIN, 20);
	
		loginNameTextField = new JTextField(10);
		loginNameTextField.setFont(font);
		loginNameTextField.setBounds(181, 61, 178, 31);
		
		lNameLabel = new JLabel("Gebruikersnaam: ");
		lNameLabel.setFont(font);
		lNameLabel.setBounds(16, 64, 159, 22);
		
		passTextField = new JPasswordField(10);
		passTextField.setFont(font);
		passTextField.setBounds(181, 110, 178, 31);
		
		passLabel = new JLabel("Wachtwoord: ");
		passLabel.setFont(font);
		passLabel.setBounds(50, 115, 125, 22);
		
		loginButton = new JButton("Login");
		loginButton.setFont(font);
		loginButton.setBackground(Color.lightGray);
		loginButton.setBounds(111, 198, 178, 52);
		
		loginStateLabel = new JLabel("<html><CENTER>Login gefaald!</CENTER><CENTER>Controleer de gebruikersnaam en wachtwoord.</CENTER></html>");
		loginStateLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		loginStateLabel.setForeground(Color.RED);
		loginStateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		loginStateLabel.setBounds(24, 152, 332, 36);
		loginStateLabel.setVisible(false);
		
		//Set GUI
		this.add(loginNameTextField);
		this.add(lNameLabel);
		this.add(passTextField);
		this.add(passLabel);
		this.add(loginButton);
		this.add(loginStateLabel);
		
		//Actionlisteners
		loginButton.addActionListener(this);
		
		loginNameTextField.requestFocus();
		
		passTextField.addKeyListener(this);
	}
	
	private void initialiseren()
	{
		Image LoginIcon = Toolkit.getDefaultToolkit().getImage("pictures/LoginIcon.png");
		this.setTitle("Login");
		this.setSize(400, 300);
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
				loginNameTextField.setText("");
				loginNameTextField.requestFocus();
				passTextField.setText("");
				loginStateLabel.setVisible(false);
			}
		});
	}
	
	public void actionPerformed(ActionEvent e)
	{
		JButton b = (JButton)e.getSource();
		
		if(b == loginButton)
		{
			if(this.parent.inloggen(loginNameTextField.getText(), String.valueOf(passTextField.getPassword())))
			{
				this.setVisible(false);
				this.parent.loggedInMode();
			}
			else
			{
				passTextField.setText("");
				passTextField.requestFocus();
				loginStateLabel.setVisible(true);
			}
		}	
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		loginStateLabel.setVisible(false);
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}
	
	
}
