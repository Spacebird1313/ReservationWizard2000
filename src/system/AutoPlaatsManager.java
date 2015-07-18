package system;

import java.util.ArrayList;

public class AutoPlaatsManager
{
	public AutoPlaatsManager()
	{
	}
	
	public Plaats[] automatischeReservatie(Voorstelling voorstelling, int aantal)
	{
		Plaats[][] plaatsen = voorstelling.zaalOpvragen().zaalLayoutOpvragen();
		Plaats[] selectie = new Plaats[aantal];
		ArrayList<ArrayList<Plaats>> bin = new ArrayList<ArrayList<Plaats>>();
		Plaats center;
		ArrayList<Plaats> chunk = new ArrayList<Plaats>();
		int checked[][] = new int[plaatsen.length][plaatsen[1].length];
		int doNotExecute = 0;
		for (int i = 0; i < voorstelling.zaalOpvragen().rijenOpvragen(); i++)
		{
			for (int j = 0; j < voorstelling.zaalOpvragen().plaatsenPerRij(); j++)
			{
				if (plaatsen[i][j].beschikbaarheidOpvragen() == 1)
				{
					if (checked[i][j] == 1)	
					{
						doNotExecute++;
					}
					if (doNotExecute == 0) 
					{
						center = plaatsen[i][j];
						chunk = new ArrayList<Plaats>();
						this.checkArea(plaatsen, center, chunk, checked);
						bin.add(chunk);
					}
					doNotExecute = 0;
				}
				
			}
		}
		int seq = 0;
		//check voor aantal plaatsen op eenzelfde rij op volgorde, beginnende van de voorste rij
		for (int i = voorstelling.zaalOpvragen().rijenOpvragen()-1; i >= 0 ; i--)
		{
			for (int j = 0; j < voorstelling.zaalOpvragen().plaatsenPerRij(); j++)
			{
				if (plaatsen[i][j].beschikbaarheidOpvragen() == 1)
				{
					if (seq >= aantal)
						return selectie;
					selectie[seq] = plaatsen[i][j];
					seq++;
				}
				else
				{
					if (seq >= aantal)
						return selectie;
					seq = 0;
				}
			}
			if (seq >= aantal)
				return selectie;
			seq = 0;
		}
		seq = 0;
		
		//Eerste plaatstoekenning gefaald, start volgende poging.
		ArrayList<Integer> sizes = new ArrayList<Integer>();
		ArrayList<ArrayList<Plaats>> temp = new ArrayList<ArrayList<Plaats>>();
		int theOne = -1, size = Integer.MAX_VALUE;
		int bestFit = -1;
		for (int i = 0; i < bin.size(); i++)
		{
			if (bin.get(i).size() >= aantal)
			{
				sizes.add(bin.get(i).size());
				temp.add(bin.get(i));
			}
		}
		for (int i = 0; i < sizes.size(); i++)
		{
			if (sizes.get(i) == aantal)
				theOne = i;
			if (sizes.get(i) < size)
			{
				bestFit = i;
				size = sizes.get(i);
			}
		}
		if (theOne != -1)
		{
			temp.get(theOne);
			for (int i = 0; i< temp.get(theOne).size(); i++)
			{
				if (seq >= aantal)
					return selectie;
				selectie[seq] =  temp.get(theOne).get(i);
				seq++;
			}
		}
		if (bestFit != -1)
		{
			temp.get(bestFit);
			for (int i = 0; i <= aantal; i++)
			{
				if (seq >= aantal)
					return selectie;
				selectie[seq] = temp.get(bestFit).get(i);
				seq++;
			}
		}
		
		//If you get to this point you most likely have almost no space left so you start combining Arrays in the bin that may or may be as far apart as needed.
		//This also works for VERY large reservations
		//We shall fill up the largest "holes" in the room first.
		//Nu begint het systeem met zijn laatste plaatstoekenningsmethode. Deze werkt voor zeer grote groepen of als de zaal redelijk vol zit.
		Integer[][] sortedIndexes = new Integer[2][bin.size()];
		for (int i = 0; i < bin.size(); i++)
		{
			sizes.add(bin.get(i).size());
			temp.add(bin.get(i));
		}
		for (int i = 0; i < bin.size(); i++) {
			sortedIndexes[0][i] = i;
		}
		sortedIndexes[1] = sizes.toArray(sortedIndexes[1]);
		bubbleSort(sortedIndexes);
		
		for (int i = 0; i < bin.size(); i++) 
		{
			for (int k = 0; k < sortedIndexes[1][i]; k++)
			{
				temp.get(sortedIndexes[0][i]);
				if (seq >= aantal)
					return selectie;
				selectie[seq] = temp.get(sortedIndexes[0][i]).get(k);
				seq++;
			}
			if (seq >= aantal)
				return selectie;
		}
		return null;		
	}
	
	private void bubbleSort(Integer arr[][])
	{
		int swap1 = 0;
		int swap2 = 0;
		
		for (int i = 0; i < ( arr[0].length - 1 ); i++) {
		      for (int j = 0; j < arr[0].length - i - 1; j++) {
		        if (arr[1][j] < arr[1][j+1]) /* For descending order use < */
		        {
		          swap1 = arr[0][j];
		          swap2 = arr[1][j];
		          arr[0][j] = arr[0][j+1];
		          arr[1][j] = arr[1][j+1];
		          arr[0][j+1] = swap1;
		          arr[1][j+1] = swap2;
		        }
		      }
		    }
	}
	
	private void checkArea(Plaats plaatsen[][], Plaats  center, ArrayList<Plaats> selectie, int[][] checked)
	{
		int i = center.rijOpvragen();
		int j = center.plaatsOpvragen();
		
		if (checked[i][j] != 1)
		{
			selectie.add(plaatsen[i][j]);
				checked[i][j] = 1;
					if (center.plaatsOpvragen() >= 1)
						if (checked[i][j-1] != 1)
							if (plaatsen[i][j-1].beschikbaarheidOpvragen() == 1)
							{
								checkArea(plaatsen, plaatsen[i][j-1], selectie, checked);
							}
			
					if (center.rijOpvragen() >= 1)	
						if (checked[i-1][j] != 1)
							if (plaatsen[i-1][j].beschikbaarheidOpvragen() == 1)
							{
								checkArea(plaatsen, plaatsen[i-1][j], selectie, checked);
							}
				
					if (center.plaatsOpvragen() < plaatsen[center.rijOpvragen()].length-1)
						if (checked[i][j+1] != 1)
							if (plaatsen[i][j+1].beschikbaarheidOpvragen() == 1)
							{
								checkArea(plaatsen, plaatsen[i][j+1], selectie, checked);
							}
	
					if (center.rijOpvragen() < plaatsen.length-1)
						if (checked[i+1][j] != 1)
							if (plaatsen[i+1][j].beschikbaarheidOpvragen() == 1)
							{
								checkArea(plaatsen, plaatsen[i+1][j], selectie, checked);
							}
			
		}
	}
}
