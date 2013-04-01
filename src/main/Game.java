package main;
import gameengine.BasicEnemy;
import gameengine.GameEngine;
import gameengine.Player;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.SpriteSheet;
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
		
		GameEngine.setBgroundmap(new TiledMap("res/background.tmx"));
		
		GameEngine.setAndInitializeMap( new TiledMap("res/desert.tmx"));
		
		
		
	}
 
	@Override
	public void update(GameContainer gc, int delta) throws SlickException
	{
		GameEngine.update(gc.getInput(), delta);
		
	}
 
	@Override
	public void render(GameContainer gc, Graphics g)
	{
		g.resetTransform();
		
		GameEngine.draw(g);
		
	}		
	public static void main(String[] args) throws SlickException
	{
		AppGameContainer container = new AppGameContainer(new Game(), WINDOW_WIDTH, WINDOW_HEIGHT, false);
		container.start();
	}
	
	
}
