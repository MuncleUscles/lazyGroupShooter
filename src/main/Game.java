package main;
import gameengine.BasicEnemy;
import gameengine.GameState;
import gameengine.Player;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.SpriteSheet;

import statemanager.StateManager;

import java.util.Random;
public class Game extends BasicGame
{
	
	
	
	public static int WINDOW_WIDTH = 1000;
	public static int WINDOW_HEIGHT = 800;
	
	public static int FRAME_RATE = 60;
	public static double FRAME_TIME = 1000/FRAME_RATE;
	

	

	public Game()
	{
		super("Wild West: Zombies");
	}
 
	@Override
	public void init(GameContainer gc) throws SlickException
	{
		//gc.setMinimumLogicUpdateInterval(100);
		gc.setShowFPS(false);
		gc.getGraphics().setAntiAlias(true);
		
		
		StateManager.setState("mainMenuState");
		
		
	}
 
	@Override
	public void update(GameContainer gc, int delta) throws SlickException
	{
		
		
		
		//GameState.update(gc.getInput(), delta);°
		StateManager.getActiveState().update(gc, delta);
		
	}
 
	@Override
	public void render(GameContainer gc, Graphics g)
	{
		g.resetTransform();
		
		//GameState.draw(g);
		StateManager.getActiveState().draw(g);
	}		
	public static void main(String[] args) throws SlickException
	{
		AppGameContainer container = new AppGameContainer(new Game(), WINDOW_WIDTH, WINDOW_HEIGHT, false);
		container.start();
	}
	
	
}
