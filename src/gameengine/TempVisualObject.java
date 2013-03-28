package gameengine;

import org.newdawn.slick.Graphics;

public abstract class TempVisualObject extends GameObject {

	private int existanceTime;

	public int getExistanceTime() {
		return existanceTime;
	}

	public void setExistanceTime(int existanceTime) {
		this.existanceTime = existanceTime;
	}
	
	public int advanceTime(int delta)
	{
		return existanceTime -= delta;
		
	}
	

}
