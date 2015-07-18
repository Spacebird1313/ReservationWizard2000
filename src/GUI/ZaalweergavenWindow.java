package GUI;

import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Iterator;
import java.util.Observable;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;

import system.Plaats;
import system.Reservatie;
import system.ReservationWizard2000;
import system.Voorstelling;

public class ZaalweergavenWindow extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	final Image RW2000Image = Toolkit.getDefaultToolkit().getImage("pictures/RW2000.png");
	private JPanel zaalPreviewPanel, zaalPanel;
	private GridButton geselecteerdePlaatsButton;
	private DelegatedObservable observer;
	private int rijBreedte, rijAantal;
	private int[] geselecteerdeIndex;
	
	public ZaalweergavenWindow(ReservationWizard2000 RW2000)
	{
		this.geselecteerdeIndex = new int[2];
		this.geselecteerdeIndex[0] = -1;
		this.geselecteerdeIndex[1] = -1;
		this.rijBreedte = 0;
		this.rijAantal = 0;
		this.geselecteerdePlaatsButton = null;
		this.observer = new DelegatedObservable();
		
		this.initialiseren();
		
		zaalPreviewPanel = new JPanel();
		zaalPreviewPanel.setBorder(BorderFactory.createTitledBorder("Zaalpreview"));
		zaalPreviewPanel.setBounds(0, 0, 1090, 732);
		zaalPreviewPanel.setOpaque(true);
		zaalPreviewPanel.setLayout(null);
		
		zaalPanel = new JPanel();
		this.zaalPanel.setBounds(5, 15, this.zaalPreviewPanel.getWidth() - 5, this.zaalPreviewPanel.getHeight() - 15);
		zaalPanel.setLayout(null);
		zaalPanel.setVisible(false);
						
		//Set GUI
		this.add(zaalPreviewPanel);
	}
		
		
	private void initialiseren()
	{
		this.setTitle("Zaalweergaven");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setIconImage(RW2000Image);
		this.setSize(1100, 765);
		this.setResizable(false);
		this.setLocation(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width - this.getWidth(), GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height - this.getHeight());
		this.setAlwaysOnTop(true);
		this.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
		this.setUndecorated(true);
	}
	
	public void zaalPreviewGenereren(Voorstelling voorstelling, Reservatie reservatie, boolean editMode)
	{
		if(voorstelling != null)
		{
			zaalPreviewPanel.setBorder(BorderFactory.createTitledBorder("Zaalpreview: " + voorstelling.zaalOpvragen().zaalNaamOpvragen()));
			this.rijBreedte = voorstelling.zaalOpvragen().plaatsenPerRij();
			this.rijAantal = voorstelling.zaalOpvragen().rijenOpvragen();
			this.zaalPreviewPanel.removeAll();
			this.zaalPanel = new JPanel(new GridLayout(rijAantal, rijBreedte));
			this.zaalPanel.setOpaque(true);
			this.zaalPanel.setBounds(5, 15, this.zaalPreviewPanel.getWidth() - 5, this.zaalPreviewPanel.getHeight() - 15);
			ImageIcon stoelBeschikbaarImage = new ImageIcon((new ImageIcon("pictures/StoelBeschikbaar.png").getImage().getScaledInstance(this.zaalPanel.getWidth()/rijBreedte, this.zaalPanel.getHeight()/rijAantal, Image.SCALE_SMOOTH)));
			ImageIcon stoelBezetImage = new ImageIcon((new ImageIcon("pictures/StoelBezet.png").getImage().getScaledInstance(zaalPanel.getWidth()/rijBreedte, zaalPanel.getHeight()/rijAantal, Image.SCALE_SMOOTH)));
			ImageIcon stoelGeselecteerdImage = new ImageIcon((new ImageIcon("pictures/StoelSelecteren.png").getImage().getScaledInstance(this.zaalPanel.getWidth()/rijBreedte, this.zaalPanel.getHeight()/rijAantal, Image.SCALE_SMOOTH)));
			Plaats plaats = null;
			GridButton gridButton = null;
			for (int i = 0; i < rijAantal; i++)
			{
				for	(int j = 0; j < rijBreedte; j++)
				{	
					plaats = voorstelling.zaalOpvragen().plaatsOpvragen(i, j);
					if (plaats != null)
					{							
						switch(plaats.beschikbaarheidOpvragen())
						{
							case 0:
								gridButton = new GridButton(i, j);
								if(editMode == false)
								{
									gridButton.setBorderPainted(false);
								}
								else
								{
									gridButton.addActionListener(this);
								}
								gridButton.setContentAreaFilled(false);
								zaalPanel.add(gridButton);
								break;
							case 1:
								gridButton = new GridButton(i, j);
								gridButton.setContentAreaFilled(false);
								gridButton.setIcon(stoelBeschikbaarImage);
								gridButton.addActionListener(this);
								zaalPanel.add(gridButton);
								break;
							case 2:
								if(reservatie != null)
								{
									int gereserveerd = 0;
									Iterator<Plaats> iterator = reservatie.plaatsenOpvragen().iterator();
									while(iterator.hasNext() && gereserveerd == 0)
									{
										Plaats reservatiePlaats = iterator.next();
										if(plaats == reservatiePlaats)
										{
											gereserveerd = 1;
										}
									}
									if(gereserveerd == 1)
									{
										gridButton = new GridButton(i, j);
										gridButton.setContentAreaFilled(false);
										gridButton.setIcon(stoelGeselecteerdImage);
										gridButton.addActionListener(this);
										zaalPanel.add(gridButton);
									}
									else
									{
										gridButton = new GridButton(i, j);
										if(editMode == false)
										{
											gridButton.setBorderPainted(false);
										}
										else
										{
											gridButton.addActionListener(this);
										}
										gridButton.setContentAreaFilled(false);
										gridButton.setIcon(stoelBezetImage);
										zaalPanel.add(gridButton);
									}
								}
								else
								{
									gridButton = new GridButton(i, j);
									if(editMode == false)
									{
										gridButton.setBorderPainted(false);
									}
									else
									{
										gridButton.addActionListener(this);
									}
									gridButton.setContentAreaFilled(false);
									gridButton.setIcon(stoelBezetImage);
									zaalPanel.add(gridButton);
								}
										
								break;
							default:
								gridButton = new GridButton(i, j);
								if(editMode == false)
								{
									gridButton.setBorderPainted(false);
								}
								else
								{
									gridButton.addActionListener(this);
								}
								zaalPanel.add(gridButton);
						}
					}
				}
			}
			this.zaalPreviewPanel.add(zaalPanel);
			this.zaalPreviewPanel.updateUI();
		}
		else
		{
			zaalPreviewPanel.setBorder(BorderFactory.createTitledBorder("Zaalpreview"));
			this.zaalPreviewPanel.removeAll();
			this.zaalPreviewPanel.updateUI();
		}
	}
	
	public Observable observerableOpvragen()
	{
		return observer;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		GridButton b = (GridButton)e.getSource();
		this.geselecteerdeIndex[0] = b.rijOpvragen();
		this.geselecteerdeIndex[1] = b.kolomOpvragen();
		this.geselecteerdePlaatsButton = b;
		this.observer.setChanged();
		this.observer.notifyObservers(this.geselecteerdeIndex);
	}
	
	public void selectieToestandWijzigen(int gewijzigdeToestand)
	{
		ImageIcon stoelBeschikbaarImage = new ImageIcon((new ImageIcon("pictures/StoelBeschikbaar.png").getImage().getScaledInstance(this.zaalPanel.getWidth()/this.rijBreedte, this.zaalPanel.getHeight()/this.rijAantal, Image.SCALE_SMOOTH)));
		ImageIcon stoelBezetImage = new ImageIcon((new ImageIcon("pictures/StoelBezet.png").getImage().getScaledInstance(zaalPanel.getWidth()/this.rijBreedte, zaalPanel.getHeight()/this.rijAantal, Image.SCALE_SMOOTH)));
		ImageIcon stoelGeselecteerdImage = new ImageIcon((new ImageIcon("pictures/StoelSelecteren.png").getImage().getScaledInstance(this.zaalPanel.getWidth()/this.rijBreedte, this.zaalPanel.getHeight()/this.rijAantal, Image.SCALE_SMOOTH)));
		switch(gewijzigdeToestand)
		{
			case 0:
				this.geselecteerdePlaatsButton.setIcon(null);
				break;
			case 1:
				this.geselecteerdePlaatsButton.setIcon(stoelBeschikbaarImage);
				break;
			case 2:
				this.geselecteerdePlaatsButton.setIcon(stoelBezetImage);
				break;
			case 3:
				this.geselecteerdePlaatsButton.setIcon(stoelGeselecteerdImage);
				break;
		}
		this.geselecteerdePlaatsButton.updateUI();
	}
	
	private class GridButton extends JButton
	{
		private static final long serialVersionUID = 1L;
		private int rij;
		private int kolom;
		
		public GridButton(int rij, int kolom)
		{
			this.rij = rij;
			this.kolom = kolom;
		}
		
		public int rijOpvragen()
		{
			return rij;
		}
		
		public int kolomOpvragen()
		{
			return kolom;
		}
	}
}
