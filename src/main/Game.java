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
	
	
	
	public static int WINDOW_WIDTH = 800;
	public static int WINDOW_HEIGHT = 600;
	
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
		
		gc.getGraphics().setAntiAlias(true);
		
		GameEngine.setMap( new TiledMap("res/desert.tmx"));
		
		//Centred player spawn
		
		Player player = new Player(new Vector2f(WINDOW_WIDTH/2, WINDOW_HEIGHT/2), 25);
		player.setAimColor(new Color(255, 0, 0));
		player.setFillColor(new Color(0, 255, 0, 50));
		player.setImage(new Image("res/images/cowboy.png"));
		
		player.setMovementSpeed(5f);
		GameEngine.setPlayer(player);

		//Random Enemy Spawn
		
		for(int i=0; i<10; i++)
		{
			BasicEnemy enemy = new BasicEnemy(new Vector2f((float) Math.random()*WINDOW_WIDTH,(float) Math.random()*WINDOW_HEIGHT), 25);
			enemy.setAimColor(new Color(255, 0, 0));
			enemy.setFillColor(new Color(0, 0, 255, 50));
			enemy.setImage(new Image("res/images/zombie.png"));
			
			
			enemy.setMovementSpeed(4f);
			
			GameEngine.addEntity(enemy);
		}
		
		
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
