package gameengine;
import java.util.ArrayList;

import main.Game;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;


public class GameEngine {
	
	private static ArrayList<GameObject> objects;
	
	private static Player player;
	
	private static TiledMap map;
	
	static 
	{
		objects = new ArrayList<GameObject>();
	}
	
	public static void addObject(GameObject object)
	{
		objects.add(object);
	}
	
	public static void removeObject(GameObject object)
	{
		objects.remove(object);
	}
	

	public static void update(Input input, int delta)
	{
		//player.setAimDirection((float) (player.getAimDirection()+0.05));
		processInput(input);
		
		//AI set velocity, aim and shoot
		
		//Resolve shooting
		
		//Resolve movement
		resolveMovement(delta);
		
		//Move
		move(delta);
	}
	
	public static void processInput(Input input)
	{
		//Movement
		if(input.isKeyDown(Input.KEY_W)  || input.isKeyDown(Input.KEY_UP))
		{
			player.setVelocity(new Vector2f(0, -1).scale(player.getMovementSpeed()));
		}
		else if(input.isKeyDown(Input.KEY_S)  || input.isKeyDown(Input.KEY_DOWN))
		{
			player.setVelocity(new Vector2f(0, 1).scale(player.getMovementSpeed()));
		}
		else if(input.isKeyDown(Input.KEY_A)  || input.isKeyDown(Input.KEY_LEFT))
		{
			player.setVelocity(new Vector2f(-1, 0).scale(player.getMovementSpeed()));
		}
		if(input.isKeyDown(Input.KEY_D)  || input.isKeyDown(Input.KEY_RIGHT))
		{
			player.setVelocity(new Vector2f(1, 0).scale(player.getMovementSpeed()));
		}
		
		//Mouse aim
		int mx = input.getMouseX();
		int my = input.getMouseY();
		
		Vector2f mVector = new Vector2f(mx - Game.WINDOW_WIDTH/2, my - Game.WINDOW_HEIGHT/2);
		
		
		double aimAngle = mVector.getTheta();
		
		player.setAimDirection((float) aimAngle);
		
		
	}
	
	public static void draw(Graphics g)
	{
		g.translate(-player.getPosition().x + Game.WINDOW_WIDTH/2, -player.getPosition().y + Game.WINDOW_HEIGHT/2);
		
		map.render(0, 0);
		
		for(int i=0; i<objects.size(); i++)
		{
			
			objects.get(i).draw(g);
		}
	}
	
	public static void resolveMovement(int delta)
	{
		
	}
	
	public static void move(int delta)
	{
		for(int i=0; i<objects.size(); i++)
		{
			objects.get(i).move(delta);
		}
	}

	public static Player getPlayer() {
		return player;
	}

	public static void setPlayer(Player player) {
		GameEngine.player = player;
		
		addObject(player);
		
		System.out.println(objects.size());
	}

	

	public static TiledMap getMap() {
		return map;
	}

	public static void setMap(TiledMap map) {
		GameEngine.map = map;
	}
	
	

}
