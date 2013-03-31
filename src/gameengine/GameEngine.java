package gameengine;
import java.util.ArrayList;
import java.util.Iterator;

import main.Game;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

import collision.Collider;


public class GameEngine {
	
	private static ArrayList<GameObject> objects;
	
	private static ArrayList<TempVisualObject> tempVisuals;
	
	private static ArrayList<Entity> entities;
	
	private static Player player;
	
	private static TiledMap map;
	

	
	static 
	{
		objects = new ArrayList<GameObject>();
		tempVisuals = new ArrayList<TempVisualObject>();
		entities = new  ArrayList<Entity>();
	}
	
	public static void addEntity(Entity entity)
	{
		objects.add(entity);
		entities.add(entity);
	}
	
	public static void killEntity(Entity entity)
	{
		objects.remove(entity);
		entities.remove(entity);
	}
	
	public static void removeEntity(Entity entity)
	{
		objects.remove(entity);
		entities.remove(entity);
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
		//temp visuals
		processTempVisuals(delta);
		
		
		//process input
		processInput(input);
		
		//AI set velocity, aim and shoot
		processAI();
		
		//Resolve shooting
		resolveShooting();
		
		
		//Resolve movement
		resolveMovement(delta);
		
		//Move
		move(delta);
	}
	
	public static void processInput(Input input)
	{		
		//Player Movement
		player.setVelocity(new Vector2f(0, 0));
		
		
		if(input.isKeyDown(Input.KEY_W)  || input.isKeyDown(Input.KEY_UP))
		{
			player.getVelocity().add(new Vector2f(0, -1));
		}
		if(input.isKeyDown(Input.KEY_S)  || input.isKeyDown(Input.KEY_DOWN))
		{
			player.getVelocity().add(new Vector2f(0, 1));
		}
		if(input.isKeyDown(Input.KEY_A)  || input.isKeyDown(Input.KEY_LEFT))
		{
			player.getVelocity().add(new Vector2f(-1, 0));
		}
		if(input.isKeyDown(Input.KEY_D)  || input.isKeyDown(Input.KEY_RIGHT))
		{
			player.getVelocity().add(new Vector2f(1, 0));
		}
		
		player.getVelocity().normalise().scale(player.getMovementSpeed());
		
		
		//Mouse aim
		int mx = input.getMouseX();
		int my = input.getMouseY();
		
		Vector2f mVector = new Vector2f(mx - Game.WINDOW_WIDTH/2, my - Game.WINDOW_HEIGHT/2);
		
		
		double aimAngle = mVector.getTheta();
		
		player.setAimDirection((float) aimAngle);

		
		//Shooting
		
		if(input.isMousePressed(input.MOUSE_LEFT_BUTTON) )
		{
			player.setShooting(true);
		}
		
	}
	
	public static void draw(Graphics g)
	{
		g.translate(Math.round(-player.getPosition().x + Game.WINDOW_WIDTH/2), Math.round(-player.getPosition().y + Game.WINDOW_HEIGHT/2));
		
		map.render(0, 0);
		
		for(int i=0; i<objects.size(); i++)
		{
			
			objects.get(i).draw(g);
		}
		
		for(int i=0; i<tempVisuals.size(); i++)
		{
			
			tempVisuals.get(i).draw(g);
		}
	}
	
	public static void processTempVisuals(int delta)
	{
		Iterator<TempVisualObject> it = tempVisuals.iterator();
		while (it.hasNext()) {
			
			TempVisualObject o = it.next();
			
			o.advanceTime(delta);
			
		    if (o.getExistanceTime() <= 0 ) {
		        it.remove();
		        
		    }
		}
	}
	
	public static void processAI()
	{
		for(int i=0; i<entities.size(); i++)
		{
			
			//Basic enemies
			if(entities.get(i).type().equals("BasicEnemy"))
			{
				BasicEnemy enemy = (BasicEnemy) entities.get(i);
				
				Vector2f aim = player.getPosition().copy().sub(enemy.getPosition());
				enemy.setAimDirection(aim.getTheta());
				enemy.setVelocity(aim.normalise().scale(enemy.getMovementSpeed()));				
			}
		}
	}
	
	public static void resolveShooting()
	{
		for(int i=0; i<entities.size(); i++)
		{
			Entity entity = entities.get(i);
			
			
			
			if(entity.isShooting())
			{
				
				
				//Actually calculate something
				
				Vector2f shotEnd = new Vector2f(1,0);
				shotEnd.setTheta(entity.getAimDirection());
				
				GameObject target = Collider.shoot(objects, entity, entity.getPosition(), shotEnd);
				
				//Hit something
				if(target != null)
				{
					if(target.hit(entity.getDamage()))
					{
						killEntity((Entity) target);
					}
				}
				
				//Visual
				
				shotEnd.scale(Game.WINDOW_WIDTH);
				shotEnd.add(entity.getPosition());
				
				Shot shot = new Shot(entity.getPosition(), shotEnd);
				tempVisuals.add(shot);
				
				entity.setShooting(false);
			}
		}
	}
	
	public static void resolveMovement(int delta)
	{
		//Collider.doCollisions(objects, delta);
		Collider.resolveAll(objects);
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
		addEntity(player);
	}

	

	public static TiledMap getMap() {
		return map;
	}

	public static void setMap(TiledMap map) {
		GameEngine.map = map;
	}
	
	

}
