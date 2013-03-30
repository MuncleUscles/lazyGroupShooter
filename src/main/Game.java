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
	
	private Image zombie1;
	private Image zombie2;
	private Image zombie3;
	private Image cowboy;
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	private int x3;
	private int y3;
	float x = 615;
	float y = 615;
	

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
		
			Image zombie = new Image ("/afs/inf.ed.ac.uk/user/s12/s1225015/zombie.bmp");
			zombie1 = zombie;
			zombie2 = zombie;
			zombie3 = zombie;
			cowboy = new Image ("/afs/inf.ed.ac.uk/user/s12/s1225015/cowboy.bmp");
		
		//Centred player spawn
		
		Player player = new Player(new Vector2f(640, 640), 25);
		player.setAimColor(new Color(255, 0, 0));
		player.setFillColor(new Color(0, 255, 0));
		
		player.setMovementSpeed(5f);
		
		//SpriteSheet sheet = new SpriteSheet("res/tmw_desert_spacing.png",32,32);	
		
		GameEngine.setPlayer(player);

		//Random Enemy Spawn
		Random r1 = new Random();
		x1 = r1.nextInt(1230) + 25;
		
		Random r2 = new Random();
		y1 = r2.nextInt(1230) + 25;
		
		BasicEnemy enem1 = new BasicEnemy(new Vector2f(x1, y1), 25);
		enem1.setAimColor(new Color(255, 0, 0));
		enem1.setFillColor(new Color(0, 0, 255));
		
		enem1.setMovementSpeed(0.01f);
		
		GameEngine.setEnemy1(enem1);
		
		r1 = new Random();
		x2 = r1.nextInt(1230) + 25;
		
		r2 = new Random();
		y2 = r2.nextInt(1230) + 25;		
		
		BasicEnemy enem2 = new BasicEnemy(new Vector2f(x2, y2), 25);
		enem2.setAimColor(new Color(255, 0, 0));
		enem2.setFillColor(new Color(0, 0, 255));
		
		enem2.setMovementSpeed(0.01f);
		
		GameEngine.setEnemy2(enem2);
		
		r1 = new Random();
		x3 = r1.nextInt(1230) + 25;
		
		r2 = new Random();
		y3 = r2.nextInt(1230) + 25;		
		
		BasicEnemy enem3 = new BasicEnemy(new Vector2f(x3, y3), 25);
		enem3.setAimColor(new Color(255, 0, 0));
		enem3.setFillColor(new Color(0, 0, 255));
		
		enem3.setMovementSpeed(0.01f);
		
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
//		zombie1.draw(x1-15, y1-15);
//		zombie2.draw(x2-15, y2-15);
//		zombie3.draw(x3-15, y3-15);
//		cowboy.draw(615, 615);
		
	}		
	public static void main(String[] args) throws SlickException
	{
		AppGameContainer container = new AppGameContainer(new Game(), WINDOW_WIDTH, WINDOW_HEIGHT, false);
		container.start();
	}
}
