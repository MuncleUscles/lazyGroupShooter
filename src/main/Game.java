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
	
	
	
	long totalTime;
	
	public static int WINDOW_WIDTH = 1200;
	public static int WINDOW_HEIGHT = 1200;
	
	public static int FRAME_RATE = 60;
	public static double FRAME_TIME = 1000/FRAME_RATE;
	

	public Game()
	{
		super("Wild West: Zombies");
		
		totalTime = 0;
	}
 
	@Override
	public void init(GameContainer gc) throws SlickException
	{
		gc.getGraphics().setAntiAlias(true);
		
		GameEngine.setMap( new TiledMap("res/desert.tmx"));
		
		//Centred player spawn
		
		Player player = new Player(new Vector2f(640, 640), 25);
		player.setAimColor(new Color(255, 0, 0));
		player.setFillColor(new Color(0, 255, 0));
		
		player.setMovementSpeed(5f);
		
		//SpriteSheet sheet = new SpriteSheet("res/tmw_desert_spacing.png",32,32);	
		
		GameEngine.setPlayer(player);

		//Random Enemy Spawn
		Random r1 = new Random();
		int x1 = r1.nextInt(1230) + 25;
		
		Random r2 = new Random();
		int y1 = r2.nextInt(1230) + 25;
		
		BasicEnemy enem1 = new BasicEnemy(new Vector2f(x1, y1), 25);
		enem1.setAimColor(new Color(255, 0, 0));
		enem1.setFillColor(new Color(0, 0, 255));
		
		enem1.setMovementSpeed(0.02f);
		
		GameEngine.setEnemy1(enem1);
		
		r1 = new Random();
		int x2 = r1.nextInt(1230) + 25;
		
		r2 = new Random();
		int y2 = r2.nextInt(1230) + 25;		
		
		BasicEnemy enem2 = new BasicEnemy(new Vector2f(x2, y2), 25);
		enem2.setAimColor(new Color(255, 0, 0));
		enem2.setFillColor(new Color(0, 0, 255));
		
		enem2.setMovementSpeed(0.02f);
		
		GameEngine.setEnemy2(enem2);
		
		r1 = new Random();
		int x3 = r1.nextInt(1230) + 25;
		
		r2 = new Random();
		int y3 = r2.nextInt(1230) + 25;		
		
		BasicEnemy enem3 = new BasicEnemy(new Vector2f(x3, y3), 25);
		enem3.setAimColor(new Color(255, 0, 0));
		enem3.setFillColor(new Color(0, 0, 255));
		
		enem3.setMovementSpeed(0.02f);
		
		GameEngine.setEnemy3(enem3);
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
