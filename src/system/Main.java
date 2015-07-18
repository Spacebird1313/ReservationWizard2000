package system;

import GUI.ReservationWizard2000GUI;

public class Main
{
	private static boolean debug = false;														//Zet debugmode true-false
	private static boolean showGUI = true;														//Zet GUI true-false
	
	private static ReservationWizard2000 RW2000;
	public static void main(String[] args)
	{
		RW2000 = new ReservationWizard2000(debug);
		
		if(showGUI)
			new ReservationWizard2000GUI(RW2000);
		
		if(debug)
			new DebuggerConsole(RW2000);
	}
	
}
