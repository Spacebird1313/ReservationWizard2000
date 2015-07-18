package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import system.Plaats;
import system.ReservationWizard2000;
import system.Voorstelling;
import system.Zaal;

public class VoorstellingWijzigWindow extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	final Image RW2000Image = Toolkit.getDefaultToolkit().getImage("pictures/RW2000.png");
	private ZaalSelectorWindow zaalSelectorWindow;
	private JPanel eigenschappenPanel, optiesPanel, zaalPreviewPanel, zaalPanel;
	private JTextField voorstellingNaamTField, tijdTField;
	private JSpinner duurSpinner;
	private JButton zaalLadenButton, voorstellingWijzigenButton;
	private JLabel voorstellingLabel, tijdLabel, duurLabel, zaalLabel, geenVoorstellingLabel;
	private ReservationWizard2000 RW2000;
	private boolean nieuweVoorstelling;
	private Voorstelling huidigeVoorstelling;
	private Zaal nieuweZaal;
	
	public VoorstellingWijzigWindow(ReservationWizard2000 RW2000)
	{
		this.RW2000 = RW2000;
		this.nieuweVoorstelling = true;
		this.huidigeVoorstelling = new Voorstelling();
		this.nieuweZaal = null;
		
		this.initialiseren();
	}
	
	public VoorstellingWijzigWindow(ReservationWizard2000 RW2000, Voorstelling voorstelling)
	{
		this.RW2000 = RW2000;
		this.nieuweVoorstelling = false;
		this.huidigeVoorstelling = voorstelling;
		this.nieuweZaal = null;
		
		this.initialiseren();
		
		this.voorstellingLaden(voorstelling);
	}
	
	private void initialiseren()
	{
		this.setTitle("Voorstelling beheren");
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setIconImage(RW2000Image);
		this.setSize(1071, 480);
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
				hideAllWindows();
			}
		});	
		
		this.setLayout(null);
		
		Font font = new Font("Arial", Font.PLAIN, 20);
		
		eigenschappenPanel = new JPanel();
		eigenschappenPanel.setBorder(BorderFactory.createTitledBorder("Voorstelling"));
		eigenschappenPanel.setBounds(12, 12, 474, 162);
		eigenschappenPanel.setLayout(null);
		
		optiesPanel = new JPanel();
		optiesPanel.setBorder(BorderFactory.createTitledBorder(""));
		optiesPanel.setBounds(12, 180, 474, 250);
		optiesPanel.setLayout(null);
		
		zaalPreviewPanel = new JPanel();
		zaalPreviewPanel.setBorder(BorderFactory.createTitledBorder("Zaalpreview"));
		zaalPreviewPanel.setBounds(497, 8, 546, 423);
		zaalPreviewPanel.setOpaque(true);
		zaalPreviewPanel.setLayout(null);
		
		zaalPanel = new JPanel();
		zaalPanel.setVisible(false);
		
		voorstellingNaamTField = new JTextField("");
		voorstellingNaamTField.setFont(font);
		voorstellingNaamTField.setBounds(123, 35, 343, 25);
		
		tijdTField = new JTextField("");
		tijdTField.setFont(font);
		tijdTField.setBounds(54, 74, 228, 25);
		
		SpinnerModel sModel = new SpinnerNumberModel(0, 0, 999, 1);
		duurSpinner = new JSpinner(sModel);
		duurSpinner.setFont(font);
		duurSpinner.setBounds(355, 74, 111, 25);
		
		zaalLadenButton = new JButton("Zaal laden");
		zaalLadenButton.setFont(font);
		zaalLadenButton.setBackground(Color.lightGray);
		zaalLadenButton.setBounds(14, 14, 445, 109);
		
		voorstellingWijzigenButton = new JButton("Voorstelling wijzigen");
		voorstellingWijzigenButton.setFont(font);
		voorstellingWijzigenButton.setBackground(Color.lightGray);
		voorstellingWijzigenButton.setBounds(14, 130, 445, 109);
		
		voorstellingLabel = new JLabel("Voorstelling: ");
		voorstellingLabel.setFont(font);
		voorstellingLabel.setBounds(9, 32, 120, 25);
		
		zaalLabel = new JLabel("Zaal: ");
		zaalLabel.setFont(font);
		zaalLabel.setBounds(9, 113, 457, 25);
		
		tijdLabel = new JLabel("Tijd: ");
		tijdLabel.setFont(font);
		tijdLabel.setBounds(9, 72, 50, 25);
		
		duurLabel = new JLabel("Duur: ");
		duurLabel.setFont(font);
		duurLabel.setBounds(299, 72, 60, 25);
		
		geenVoorstellingLabel = new JLabel("Geen voorstelling geselecteerd.");
		geenVoorstellingLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		geenVoorstellingLabel.setHorizontalAlignment(JLabel.CENTER);
		geenVoorstellingLabel.setBounds(84, 192, 390, 32);
		geenVoorstellingLabel.setVisible(true);
						
		//Set GUI
		this.add(eigenschappenPanel);
		this.add(optiesPanel);
		this.add(zaalPreviewPanel);
		
		optiesPanel.add(zaalLadenButton);
		optiesPanel.add(voorstellingWijzigenButton);
		
		eigenschappenPanel.add(voorstellingLabel);
		eigenschappenPanel.add(zaalLabel);
		eigenschappenPanel.add(tijdLabel);
		eigenschappenPanel.add(duurLabel);
		eigenschappenPanel.add(voorstellingNaamTField);
		eigenschappenPanel.add(tijdTField);
		eigenschappenPanel.add(duurSpinner);
		
		zaalPreviewPanel.add(geenVoorstellingLabel);
							
		//Actionlisteners
		voorstellingWijzigenButton.addActionListener(this);
		zaalLadenButton.addActionListener(this);
	}
	
	public void hideAllWindows()
	{
		if(this.zaalSelectorWindow != null)
		{
			this.zaalSelectorWindow.setVisible(false);
		}
	}
	
	private void voorstellingLaden(Voorstelling voorstelling)
	{
		if(voorstelling != null)
		{
			this.voorstellingNaamTField.setText(voorstelling.voorstellingsNaamOpvragen());
			this.tijdTField.setText(voorstelling.tijdOpvragen());
			this.duurSpinner.setValue(voorstelling.duurOpvragen());
			this.zaalLabel.setText("Zaal: " + voorstelling.zaalOpvragen().zaalNaamOpvragen());
			this.zaalPreviewGenereren(voorstelling.zaalOpvragen());
		}
		else
		{
			this.voorstellingNaamTField.setText("");
			this.tijdTField.setText("");
			this.duurSpinner.setValue(0);
			this.zaalLabel.setText("Zaal: ");
			this.zaalPreviewGenereren(null);
		}
	}
	
	private void zaalPreviewGenereren(Zaal zaal)
	{
		if(zaal != null)
		{
			int rijBreedte = zaal.plaatsenPerRij();
			int rijAantal = zaal.rijenOpvragen();
			this.zaalPreviewPanel.removeAll();
			this.zaalPanel = new JPanel(new GridLayout(rijAantal, rijBreedte));
			this.zaalPanel.setOpaque(true);
			this.zaalPanel.setBounds(5, 15, this.zaalPreviewPanel.getWidth() - 10, this.zaalPreviewPanel.getHeight() - 20);
			ImageIcon stoelBeschikbaarImage = new ImageIcon((new ImageIcon("pictures/StoelBeschikbaar.png").getImage().getScaledInstance(this.zaalPanel.getWidth()/rijBreedte, this.zaalPanel.getHeight()/rijAantal, Image.SCALE_SMOOTH)));
			ImageIcon stoelBezetImage = new ImageIcon((new ImageIcon("pictures/StoelBezet.png").getImage().getScaledInstance(zaalPanel.getWidth()/rijBreedte, zaalPanel.getHeight()/rijAantal, Image.SCALE_SMOOTH)));
			Plaats plaats = null;
			for (int i = 0; i < rijAantal; i++)
			{
				for	(int j = 0; j < rijBreedte; j++)
				{	
					plaats = zaal.plaatsOpvragen(i, j);
					if (plaats != null)
					{							
						switch(plaats.beschikbaarheidOpvragen())
						{
							case 0:
								zaalPanel.add(new JLabel(""));
								break;
							case 1:
								zaalPanel.add(new JLabel(stoelBeschikbaarImage));
								break;
							case 2:
								zaalPanel.add(new JLabel(stoelBezetImage));
								break;
							default:
								zaalPanel.add(new JLabel(""));	
								break;
						}
					}
				}
			}
			this.zaalPreviewPanel.add(zaalPanel);
			this.zaalPreviewPanel.updateUI();
		}
		else
		{
			this.zaalPreviewPanel.removeAll();
			this.zaalPreviewPanel.add(this.geenVoorstellingLabel);
			this.zaalPreviewPanel.updateUI();
		}
	}
	
	private void voorstellingWijzigen()
	{
		this.huidigeVoorstelling.voorstellingsEigenschappenWijzigen(this.voorstellingNaamTField.getText(), this.tijdTField.getText(), (int)this.duurSpinner.getValue());
		if(this.nieuweZaal != null)
		{
			this.huidigeVoorstelling.zaalWijzigen(this.nieuweZaal);
		}
		RW2000.saveReservaties();
	}
	
	private void nieuweVoorstellingOpslaan()
	{
		RW2000.voorstellingAanmaken(this.nieuweZaal, this.voorstellingNaamTField.getText(), this.tijdTField.getText(), (int)this.duurSpinner.getValue());
	}
	
	private void zaalGeselecteerd(Zaal zaal)
	{
		zaalPreviewGenereren(zaal);
		zaalLabel.setText("Zaal: " + zaal.zaalNaamOpvragen());
		this.nieuweZaal = zaal;
	}
	
	private void nieuweZaalSelecteren()
	{
		if(zaalSelectorWindow == null)
		{
			this.zaalSelectorWindow = new ZaalSelectorWindow(RW2000);
		}
		else
		{
			this.zaalSelectorWindow.dispose();
			this.zaalSelectorWindow = new ZaalSelectorWindow(RW2000);
		}
		this.zaalSelectorWindow.observerableOpvragen().addObserver(new Observer()
		{
			public void update(Observable source, Object object)
			{
				zaalGeselecteerd((Zaal)object);
			}
		});
		this.zaalSelectorWindow.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		JButton b = (JButton)e.getSource();
		if(b == voorstellingWijzigenButton)
		{
			if(this.voorstellingNaamTField.getText().equals("") == false)
			{
				if(this.tijdTField.getText().equals("") == false)
				{
					if(this.nieuweVoorstelling == true)
					{
						if(this.nieuweZaal != null)
						{
							this.nieuweVoorstellingOpslaan();
							this.hideAllWindows();
							this.setVisible(false);
						}
						else
						{
							JOptionPane pane = new JOptionPane("Er is geen zaal gekoppeld aan deze voorstelling! Selecteer eerst een zaal.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION);
							JDialog dialog = pane.createDialog("Geen zaal gekozen!");
							dialog.setIconImage(RW2000Image);	
							dialog.setAlwaysOnTop(true);
							dialog.setVisible(true);
						}
					}
					else
					{
						this.voorstellingWijzigen();
						this.hideAllWindows();
						this.setVisible(false);
					}
				}
				else
				{
					this.tijdTField.requestFocus();
				}
			}
			else
			{
				this.voorstellingNaamTField.requestFocus();
			}
		}
		else if(b == zaalLadenButton)
		{
			if(this.nieuweVoorstelling == true)
			{
				this.nieuweZaalSelecteren();
			}
			else
			{
				JOptionPane pane = new JOptionPane("Het wijzigen van de zaal zal alle gemaakte reservaties voor deze voorstelling verwijderen! Wilt u doorgaan? ", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
				JDialog dialog = pane.createDialog("Verlies van reservaties!");
				dialog.setIconImage(RW2000Image);	
				dialog.setAlwaysOnTop(true);
				dialog.setVisible(true);
				if(pane.getValue() != null)
				{
					if(((Integer)pane.getValue()).intValue() == JOptionPane.YES_OPTION)
					{
						this.nieuweZaalSelecteren();
					}
				}
			}
		}
	}
}
