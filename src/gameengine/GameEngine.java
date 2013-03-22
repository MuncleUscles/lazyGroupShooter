package gameengine;
import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;


public class GameEngine {
	
	private static ArrayList<GameObject> objects;
	
	private static Player player;
	
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
		//Set player velocity, aim and shoot
		
		//AI set velocity, aim and shoot
		
		//Resolve shooting
		
		//Resolve movement
		
		//Move
	}
	
	public static void draw(Graphics g)
	{
		
		for(int i=0; i<objects.size(); i++)
		{
			
			objects.get(i).draw(g);
		}
	}
	
	public static void resolveMovement()
	{
		
	}
	
	public static void move()
	{
		
	}

	public static Player getPlayer() {
		return player;
	}

	public static void setPlayer(Player player) {
		GameEngine.player = player;
		
		addObject(player);
		
		System.out.println(objects.size());
	}
	
	

}
