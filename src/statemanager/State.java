package statemanager;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public abstract class State {
	
	public abstract void update(GameContainer gc, int delta);
	public abstract void draw(Graphics g);

}
