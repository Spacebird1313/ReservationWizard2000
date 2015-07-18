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
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import system.Plaats;
import system.Reservatie;
import system.ReservationWizard2000;
import system.Voorstelling;

public class ReservatieVerwijderenWindow extends JFrame implements ActionListener, ListSelectionListener
{
	private static final long serialVersionUID = 1L;
	final Image RW2000Image = Toolkit.getDefaultToolkit().getImage("pictures/RW2000.png");
	private JFrame parent;
	private JPanel eigenschappenPanel;
	private JScrollPane reservatiePane, stoelenPane;
	private JList<String> reservatieList, stoelenList;
	private JButton verwijderenButton, annulerenButton, vernieuwenButton;
	private JLabel reservatieLabel, reservatieNaamLabel, reservatieNummerLabel, aantalPlaatsenLabel, plaatsnummerLabel, bedragLabel;
	private ReservationWizard2000 RW2000;
	private Voorstelling huidigeVoorstelling;
	
	public ReservatieVerwijderenWindow(ReservationWizard2000 RW2000, JFrame parent, Voorstelling voorstelling)
	{
		this.RW2000 = RW2000;
		this.parent = parent;
		this.huidigeVoorstelling = voorstelling;
		
		this.setLayout(null);
		
		Font font = new Font("Arial", Font.PLAIN, 20);
		
		eigenschappenPanel = new JPanel();
		eigenschappenPanel.setBorder(BorderFactory.createTitledBorder("Eigenschappen"));
		eigenschappenPanel.setBounds(343, 38, 402, 232);
		eigenschappenPanel.setLayout(null);
		
		reservatiePane = new JScrollPane();
		reservatiePane.setBounds(12, 46, 232, 372);
		
		stoelenPane = new JScrollPane();
		stoelenPane.setBounds(153, 116, 242, 76);
		
		stoelenList = new JList<String>();
		stoelenList.setFont(font);
		stoelenList.setBounds(0, 0, 361, 84);
		
		verwijderenButton = new JButton("Verwijderen");
		verwijderenButton.setFont(font);
		verwijderenButton.setBackground(Color.lightGray);
		verwijderenButton.setBounds(343, 282, 402, 97);
		
		annulerenButton = new JButton("Annuleren");
		annulerenButton.setFont(font);
		annulerenButton.setBackground(Color.lightGray);
		annulerenButton.setBounds(343, 389, 402, 97);
		
		vernieuwenButton = new JButton("Vernieuwen");
		vernieuwenButton.setFont(font);
		vernieuwenButton.setBackground(Color.lightGray);
		vernieuwenButton.setBounds(12, 423, 314, 63);
		
		reservatieList = new JList<String>();
		reservatieList.setFont(font);
		reservatieList.setBounds(12, 46, 314, 372);
		
		reservatieLabel = new JLabel("Reservaties");
		reservatieLabel.setFont(font);
		reservatieLabel.setBounds(109, 17, 124, 25);
		
		reservatieNaamLabel = new JLabel("Naam: ");
		reservatieNaamLabel.setFont(font);
		reservatieNaamLabel.setBounds(9, 22, 388, 25);
		
		reservatieNummerLabel = new JLabel("Reservatienummer: ");
		reservatieNummerLabel.setFont(font);
		reservatieNummerLabel.setBounds(9, 53, 388, 25);
		
		aantalPlaatsenLabel = new JLabel("Plaatsen gereserveerd: ");
		aantalPlaatsenLabel.setFont(font);
		aantalPlaatsenLabel.setBounds(9, 84, 388, 25);
		
		plaatsnummerLabel = new JLabel("Plaatsnummers: ");
		plaatsnummerLabel.setFont(font);
		plaatsnummerLabel.setBounds(9, 116, 388, 25);
		
		bedragLabel = new JLabel("Te betalen: ");
		bedragLabel.setFont(font);
		bedragLabel.setBounds(9, 195, 388, 25);
		
		//Set GUI
		this.add(eigenschappenPanel);
		this.add(verwijderenButton);
		this.add(annulerenButton);
		this.add(vernieuwenButton);
		this.add(reservatiePane);
		this.add(reservatieLabel);
						
		eigenschappenPanel.add(stoelenPane);
		eigenschappenPanel.add(reservatieNaamLabel);
		eigenschappenPanel.add(reservatieNummerLabel);
		eigenschappenPanel.add(aantalPlaatsenLabel);
		eigenschappenPanel.add(plaatsnummerLabel);
		eigenschappenPanel.add(bedragLabel);
										
		//Actionlisteners
		verwijderenButton.addActionListener(this);
		annulerenButton.addActionListener(this);
		vernieuwenButton.addActionListener(this);
		
		this.initialiseren();
		
		this.reservatiesInladen();
		this.stoelenLijstGenereren(new Reservatie("", 0));
	}
	
	private void initialiseren()
	{
		new ReservatieWindow(RW2000, this.parent);
		this.setTitle("Reservatie verwijderen");
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setIconImage(RW2000Image);
		this.setSize(767, 536);
		this.setResizable(false);
		this.setLocation(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width/2 - this.getWidth()/2, GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height/2 - this.getHeight()/2);
		this.setAlwaysOnTop(true);
		this.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
		this.setUndecorated(true);
	}
	
	private void reservatiesInladen()
	{
		if(this.huidigeVoorstelling != null)
		{
			this.remove(reservatiePane);
			ArrayList<Reservatie> reservaties = this.huidigeVoorstelling.reservatieOpvragen();
			DefaultListModel<String> listModel = new DefaultListModel<String>();
			Iterator<Reservatie> iterator = reservaties.iterator();
			while(iterator.hasNext())
			{
				Reservatie reservatie = iterator.next();
				listModel.addElement(reservatie.reservatieNummerOpvragen() + " - " + reservatie.reservatieNaamOpvragen());
			}
			this.reservatieList = new JList<String>(listModel);
			this.reservatieList.setFont(new Font("Arial", Font.PLAIN, 18));
			this.reservatieList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			this.reservatieList.setLayoutOrientation(JList.VERTICAL);
			this.reservatieList.setVisibleRowCount(-1);
			this.reservatieList.addListSelectionListener(this);
			this.reservatiePane = new JScrollPane(reservatieList);
			this.reservatiePane.setBounds(12, 46, 314, 372);
			this.add(reservatiePane);
			this.reservatieList.updateUI();
		}
	}
	
	private void stoelenLijstGenereren(Reservatie reservatie)
	{
		this.eigenschappenPanel.remove(stoelenPane);
		ArrayList<Plaats> plaatsen = reservatie.plaatsenOpvragen();
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		int i = 1;
		Iterator<Plaats> iterator = plaatsen.iterator();
		while(iterator.hasNext())
		{
			Plaats plaats = iterator.next();
			listModel.addElement("Plaats " + i + " - Rij " + (this.huidigeVoorstelling.zaalOpvragen().rijenOpvragen() - plaats.rijOpvragen()) + " Plaats " + (plaats.plaatsOpvragen() + 1));
			i++;
		}
		this.stoelenList = new JList<String>(listModel);
		this.stoelenList.setFont(new Font("Arial", Font.PLAIN, 18));
		this.stoelenList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.stoelenList.setLayoutOrientation(JList.VERTICAL);
		this.stoelenList.setVisibleRowCount(-1);
		this.stoelenPane = new JScrollPane(stoelenList);
		this.stoelenPane.setBounds(153, 116, 242, 76);
		this.eigenschappenPanel.add(stoelenPane);
		this.eigenschappenPanel.updateUI();
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e)
	{
		if(e.getValueIsAdjusting() == false)
		{
			if(this.reservatieList.getSelectedIndex() == -1)
			{
				this.reservatieNaamLabel.setText("Naam: ");
				this.reservatieNummerLabel.setText("Reservatienummer: ");
				this.aantalPlaatsenLabel.setText("Plaatsen gereserveerd: ");
				this.bedragLabel.setText("Te betalen: ");
			}
			else
			{
				Reservatie reservatie = this.huidigeVoorstelling.reservatieOpvragen().get(this.reservatieList.getSelectedIndex());
				this.reservatieNaamLabel.setText("Naam: " + reservatie.reservatieNaamOpvragen());
				this.reservatieNummerLabel.setText("Reservatienummer: " + reservatie.reservatieNummerOpvragen());
				this.aantalPlaatsenLabel.setText("Plaatsen gereserveerd: " + reservatie.plaatsenOpvragen().size());
				if(reservatie.betaalStatusOpvragen() == false)
				{
					this.bedragLabel.setText("Te betalen: € " + reservatie.rekeningOpvragen());
				}
				else
				{
					this.bedragLabel.setText("Te betalen: € 0.00 - Betaald");
				}	
				this.stoelenLijstGenereren(reservatie);
			}
		}
	}

	public void actionPerformed(ActionEvent e)
	{
		JButton b = (JButton)e.getSource();
		if(b == verwijderenButton)
		{
			if(this.reservatieList.isSelectionEmpty() == false)
			{
				
				JOptionPane pane = new JOptionPane("Weet u zeker de reservatie: \"" + this.huidigeVoorstelling.reservatieOpvragen().get(this.reservatieList.getSelectedIndex()).reservatieNaamOpvragen() + "\" wilt verwijderen?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
				JDialog dialog = pane.createDialog("Reservatie verwijderen");
				dialog.setIconImage(RW2000Image);	
				dialog.setAlwaysOnTop(true);
				dialog.setVisible(true);
				if(pane.getValue() != null)
				{
					if(((Integer)pane.getValue()).intValue() == JOptionPane.YES_OPTION)
					{
						this.plaatsenBeschikbaarMaken(this.huidigeVoorstelling.reservatieOpvragen().get(this.reservatieList.getSelectedIndex()));
						this.huidigeVoorstelling.reservatieOpvragen().remove(this.reservatieList.getSelectedIndex());
						this.RW2000.saveReservaties();
						this.setVisible(false);	
					}
				}
			}
		}
		else if(b == annulerenButton)
		{
			this.setVisible(false);
		}
		else if(b == vernieuwenButton)
		{
			this.reservatiesInladen();
			this.reservatieNaamLabel.setText("Naam: ");
			this.reservatieNummerLabel.setText("Reservatienummer: ");
			this.aantalPlaatsenLabel.setText("Plaatsen gereserveerd: ");
			this.bedragLabel.setText("Te betalen: ");
			this.stoelenLijstGenereren(new Reservatie("", 0));
		}
	}
	
	private void plaatsenBeschikbaarMaken(Reservatie reservatie)
	{
		Iterator<Plaats> iterator = reservatie.plaatsenOpvragen().iterator();
		while(iterator.hasNext())
		{
			Plaats plaats = iterator.next();
			plaats.wijzigBeschikbaarheid(1);
		}
	}
}
