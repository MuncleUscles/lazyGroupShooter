package gameengine;
import java.util.ArrayList;
import java.util.Iterator;

import main.Game;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;


public class GameEngine {
	
	private static ArrayList<GameObject> objects;
	
	private static ArrayList<TempVisualObject> tempVisuals;
	
	private static ArrayList<Entity> entities;
	
	private static Player player;
	
	private static BasicEnemy enem1;
	
	private static BasicEnemy enem2;
	
	private static BasicEnemy enem3;
	
	private static TiledMap map;
	
	private static Image zombie1;
	
	private static Image zombie2;
	
	private static Image zombie3;
	
	private static Image cowboy;;
	
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
		
		
		//player.setAimDirection((float) (player.getAimDirection()+0.05));
		processInput(input);
		
		//AI set velocity, aim and shoot
		
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
//		cowboy.setRotation((float) aimAngle);
		
		//Enemy Aiming
		Vector2f e1Vector = new Vector2f(player.getPosition().getX() - enem1.getPosition().getX(), player.getPosition().getY() - enem1.getPosition().getY());
		double e1AimAngle = e1Vector.getTheta();
		enem1.setAimDirection((float) e1AimAngle);
//		zombie1.setRotation((float) e1AimAngle);
		
		Vector2f e2Vector = new Vector2f(player.getPosition().getX() - enem2.getPosition().getX(), player.getPosition().getY() - enem2.getPosition().getY());
		double e2AimAngle = e2Vector.getTheta();
		enem2.setAimDirection((float) e2AimAngle);
//		zombie2.setRotation((float) e2AimAngle);
		
		Vector2f e3Vector = new Vector2f(player.getPosition().getX() - enem3.getPosition().getX(), player.getPosition().getY() - enem3.getPosition().getY());
		double e3AimAngle = e3Vector.getTheta();
		enem3.setAimDirection((float) e3AimAngle);
//		zombie3.setRotation((float) e3AimAngle);
		
		//Enemy Movement
		enem1.setVelocity(e1Vector.scale(enem1.getMovementSpeed()));
//		zombie1.setVelocity(e1Vector.scale(enem1.getMovementSpeed()));
		
		enem2.setVelocity(e2Vector.scale(enem2.getMovementSpeed()));
//		zombie2.setVelocity(e2Vector.scale(enem2.getMovementSpeed()));
		
		enem3.setVelocity(e3Vector.scale(enem3.getMovementSpeed()));
//		zombie3.setVelocity(e3Vector.scale(enem3.getMovementSpeed()));
		
		
		//Shooting
		
		if(input.isMousePressed(input.MOUSE_LEFT_BUTTON) )
		{
			player.setShooting(true);
		}
		
	}
	
	public static void draw(Graphics g)
	{
		g.translate(-player.getPosition().x + Game.WINDOW_WIDTH/2, -player.getPosition().y + Game.WINDOW_HEIGHT/2);
		
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
				shotEnd.scale(Game.WINDOW_WIDTH);
				shotEnd.add(entity.getPosition());
				
				//Visual
				
				Shot shot = new Shot(entity.getPosition(), shotEnd);
				tempVisuals.add(shot);
				
				entity.setShooting(false);
			}
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
		addEntity(player);
		
	}
	public static void setEnemy1(BasicEnemy enemy) {
		GameEngine.enem1 = enemy;
		
		addObject(enemy);
		addEntity(enemy);
		
	}
	public static void setEnemy2(BasicEnemy enemy) {
		GameEngine.enem2 = enemy;
		
		addObject(enemy);
		addEntity(enemy);
		
	}
	public static void setEnemy3(BasicEnemy enemy) {
		GameEngine.enem3 = enemy;
		
		addObject(enemy);
		addEntity(enemy);
		
	}
	

	

	public static TiledMap getMap() {
		return map;
	}

	public static void setMap(TiledMap map) {
		GameEngine.map = map;
	}
	
	

}
