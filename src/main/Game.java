package main;
import gameengine.BasicEnemy;
import gameengine.GameEngine;
import gameengine.Player;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.SpriteSheet;
public class Game extends BasicGame
{
	
	
	
	long totalTime;
	
	public static int WINDOW_WIDTH = 800;
	public static int WINDOW_HEIGHT = 600;
	
	public static int FRAME_RATE = 60;
	public static double FRAME_TIME = 1000/FRAME_RATE;

	public Game()
	{
		super("The Game was lost by me");
		
		totalTime = 0;
	}
 
	@Override
	public void init(GameContainer gc) throws SlickException
	{
		gc.getGraphics().setAntiAlias(true);
		
		GameEngine.setMap( new TiledMap("res/desert.tmx"));
		
		Player player = new Player(new Vector2f(100, 100), 25);
		player.setAimColor(new Color(255, 0, 0));
		player.setFillColor(new Color(0, 255, 0));
		
		player.setMovementSpeed(5f);
		
		//SpriteSheet sheet = new SpriteSheet("res/tmw_desert_spacing.png",32,32);	
		
		GameEngine.setPlayer(player);
		
		BasicEnemy enem1 = new BasicEnemy(new Vector2f(200, 200), 25);
		enem1.setAimColor(new Color(255, 0, 0));
		enem1.setFillColor(new Color(0, 0, 255));
		
		GameEngine.addEntity(enem1);
	}
 
	@Override
	public void update(GameContainer gc, int delta) throws SlickException
	{
		totalTime += delta;
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