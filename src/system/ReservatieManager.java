package system;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;

public class ReservatieManager
{
	private ReservationWizard2000 RW2000;
	private SaveLoadObserver observer;
	private ArrayList<Voorstelling> voorstellingen = new ArrayList<Voorstelling>();
	private ArrayList<Zaal> zalen = new ArrayList<Zaal>();
	private String ReservatiesFileLocatie = "data/Reservaties.RWRes";
	private String ZalenFileLocatie = "data/Zalen.RWZaal";
	
	public ReservatieManager(ReservationWizard2000 RW2000)
	{
		this.RW2000 = RW2000;
		this.observer = new SaveLoadObserver();
	}
	
	public Observable observerableOpvragen()
	{
		return this.observer;
	}
	
	public void voorstellingToevoegen(Voorstelling voorstelling)
	{
		this.voorstellingen.add(voorstelling);
		saveReservaties();
	}
	
	public void voorstellingVerwijderen(Voorstelling voorstelling)
	{
		this.voorstellingen.remove(voorstelling);
		saveReservaties();
	}
	
	public ArrayList<Voorstelling> voorstellingenLaden()
	{
		loadReservaties();
		return voorstellingen;
	}
	
	public boolean saveReservaties()
	{
		try
		{
			FileOutputStream file = new FileOutputStream(ReservatiesFileLocatie);
			ObjectOutputStream reservaties = new ObjectOutputStream(file);
			reservaties.writeObject(this.voorstellingen);
			reservaties.close();
			observer.setChanged();
			observer.notifyObservers("save");
			if(RW2000.debugMode())
				System.out.println("Reservaties succesvol opgeslagen.");
			return true;
		}
		catch(Exception ex)
		{
			if(RW2000.debugMode())
			{
				System.out.println("Fout bij het opslagen van de reservaties! ");
				ex.printStackTrace();
			}
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public boolean loadReservaties()
	{
		try
		{
			FileInputStream file = new FileInputStream(ReservatiesFileLocatie);
			ObjectInputStream reservaties = new ObjectInputStream(file);
			this.voorstellingen = (ArrayList<Voorstelling>) reservaties.readObject();
			reservaties.close();
			observer.setChanged();
			observer.notifyObservers("load");
			if(RW2000.debugMode())
				System.out.println("Reservaties succesvol geladen.");
			return true;
		}
		catch(Exception ex)
		{
			if(RW2000.debugMode())
			{
				System.out.println("Fout bij het laden van de reservaties! ");
				ex.printStackTrace();
			}
			return false;
		}
	}
	
	public void zaalToevoegen(Zaal zaal)
	{
		this.zalen.add(zaal);
		saveZalen();
	}
	
	public void zaalVerwijderen(Zaal zaal)
	{
		this.zalen.remove(zaal);
		saveZalen();
	}
	
	public ArrayList<Zaal> zalenLaden()
	{
		loadZalen();
		return zalen;
	}
	
	public boolean saveZalen()
	{
		try
		{
			FileOutputStream file = new FileOutputStream(ZalenFileLocatie);
			ObjectOutputStream zalen = new ObjectOutputStream(file);
			zalen.writeObject(this.zalen);
			zalen.close();
			if(RW2000.debugMode())
				System.out.println("Zalen succesvol opgeslagen.");
			return true;
		}
		catch(Exception ex)
		{
			if(RW2000.debugMode())
			{
				System.out.println("Fout bij het opslagen van de zalen! ");
				ex.printStackTrace();
			}
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public boolean loadZalen()
	{
		try
		{
			FileInputStream file = new FileInputStream(ZalenFileLocatie);
			ObjectInputStream zalen = new ObjectInputStream(file);
			this.zalen = (ArrayList<Zaal>) zalen.readObject();
			zalen.close();
			if(RW2000.debugMode())
				System.out.println("Zalen succesvol geladen.");
			return true;
		}
		catch(Exception ex)
		{
			if(RW2000.debugMode())
			{
				System.out.println("Fout bij het laden van de zalen! ");
				ex.printStackTrace();
			}
			return false;
		}
	}
}
