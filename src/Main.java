import gameengine.BasicEnemy;
import gameengine.GameEngine;
import gameengine.Player;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
public class Main extends BasicGame
{
	
	long totalTime;

	public Main()
	{
		super("The Game was lost by me");
		
		totalTime = 0;
	}
 
	@Override
	public void init(GameContainer gc) throws SlickException
	{
		Player player = new Player(new Vector2f(100, 100), 25);
		player.setAimColor(new Color(255, 0, 0));
		player.setFillColor(new Color(0, 255, 0));

		GameEngine.setPlayer(player);
		
		BasicEnemy enem1 = new BasicEnemy(new Vector2f(200, 200), 25);
		enem1.setAimColor(new Color(255, 0, 0));
		enem1.setFillColor(new Color(0, 0, 255));
		
		GameEngine.addObject(enem1);
	}
 
	@Override
	public void update(GameContainer gc, int delta) throws SlickException
	{
		totalTime += delta;
		
		GameEngine.update(gc.getInput(), delta);
	}
 
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		g.resetTransform();
		
		//--------Not Actually part of the game
		/*
		g.setColor(new Color((float)(Math.sin(totalTime/30.0)+1)/2, (float)(Math.sin(totalTime/35.0)+1)/2, (float)(Math.sin(totalTime/25.0)+1)/2));
		
		g.fill(new Rectangle(0, 0, 800, 600));
		
		g.translate(400, 300);
		
		g.scale((float) (Math.sin(totalTime/200.0)*1.5+3), (float)(Math.sin(totalTime/200.0)*1.5+3));
		
		g.rotate(0, 0, (float) (Math.sin(totalTime/100.0)*30));
		
		g.setColor(new Color((float)(-Math.sin(totalTime/30.0)+1)/2, (float)(-Math.sin(totalTime/35.0)+1)/2, (float)(-Math.sin(totalTime/25.0)+1)/2));		
		
		g.drawString("Get to work!", -55, -10);
		*/
		
		//------
		
		GameEngine.draw(g);
		
	}
 
	public static void main(String[] args) throws SlickException
	{
		AppGameContainer app = new AppGameContainer(new Main());
 
		app.setDisplayMode(800, 600, false);
		app.start();
	}
}