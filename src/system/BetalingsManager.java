package system;

import java.io.FileWriter;
import java.util.ArrayList;

public class BetalingsManager {
		
	private ReservationWizard2000 RW2000;
	private String BetalingFileLocatie = "prints/";
	
	public BetalingsManager(ReservationWizard2000 RW2000)
	{
		this.RW2000 = RW2000;
	}
	
	public boolean betalen(Reservatie reservatie, double bedrag)
	{
		if(bedrag == reservatie.rekeningOpvragen())
		{
			reservatie.betaalStatusAanpassen(true);
			return true;
		}
		return false;
	}
	
	public boolean rekeningAfdrukken(Voorstelling voorstelling, Reservatie reservatie)
	{
		String ticket;
		ArrayList<Plaats> plaatsen = new ArrayList<Plaats>();
		String plaats = "";
		
		ticket = "--------------------------------------------------------\n"
				+ "Reservatie\n"
				+ "Naam van de reservatie: " +reservatie.reservatieNaamOpvragen() +"\n"
				+ "Voorstelling: " + voorstelling.voorstellingsNaamOpvragen() +"\n"
				+ "Zaal: " + voorstelling.zaalOpvragen().zaalNaamOpvragen() +"\n"
				+ "Starttijd: " + voorstelling.tijdOpvragen() +"\n"
				+ "Duur: " +voorstelling.duurOpvragen() +"\n"
				+ "---\n";
		plaatsen = reservatie.plaatsenOpvragen(); 
		for (int i = 1; i <= plaatsen.size(); i++)
		{
			plaats = plaats + "Plaats " + i + ": " + "rij: " +plaatsen.get(i-1).rijOpvragen() +", plaats: " + plaatsen.get(i-1).rijOpvragen() + ", prijs: " + plaatsen.get(i-1).prijsOpvragen() +"\n";
		}
		ticket = ticket + plaats + "---\n";
		ticket = ticket + "Totale prijs: " + reservatie.rekeningOpvragen() +"\n"
				+ "--------------------------------------------------------\n";
		try
		{
			FileWriter file = new FileWriter(BetalingFileLocatie + voorstelling.voorstellingsNaamOpvragen() + "-" + reservatie.reservatieNummerOpvragen() + ".txt");
			file.write(ticket);
			file.close();
			
			if(RW2000.debugMode())
				System.out.println("Rekeningafdrukken succesvol.");
			return true;
		}
		catch(Exception ex)
		{
			if(RW2000.debugMode())
			{
				System.out.println("Rekeningafdrukken mislukt.");
				ex.printStackTrace();
			}
			return false;
		}

	}
}
