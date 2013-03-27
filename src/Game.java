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
	
	private TiledMap map;
	
	long totalTime;

	public Game()
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
		
		SpriteSheet sheet = new SpriteSheet("res/tmw_desert_spacing.png",32,32);
		map = new TiledMap("res/desert.tmx");
		
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
	public void render(GameContainer gc, Graphics g)
	{
		map.render(0, 0);
		g.resetTransform();
	}		
	public static void main(String[] args) throws SlickException
	{
		AppGameContainer container = new AppGameContainer(new Game(), 1280, 1280, false);
		container.start();
	}
}