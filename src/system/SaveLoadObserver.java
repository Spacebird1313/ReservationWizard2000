package system;

import java.util.Observable;

public class SaveLoadObserver extends Observable
{
	public void clearChanged()
	{
		super.clearChanged();
	}
	
	public void setChanged()
	{
		super.setChanged();
	}
}
