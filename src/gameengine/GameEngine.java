package gameengine;
import java.util.ArrayList;
import java.util.Iterator;

import main.Game;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

import collision.Collider;
import collision.ShootingResult;


public class GameEngine {
	
	private static ArrayList<GameObject> objects;
	
	private static ArrayList<TempVisualObject> tempVisuals;
	
	private static ArrayList<Entity> entities;
	
	private static Player player;
	
	private static TiledMap map, bgroundmap;
	

	
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
		resolveShooting(delta);
		
		
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
			player.shoot();
		}
		
	}
	
	public static void draw(Graphics g)
	{
		
		
		g.translate(Math.round(-player.getPosition().x + Game.WINDOW_WIDTH/2), Math.round(-player.getPosition().y + Game.WINDOW_HEIGHT/2));
		
		bgroundmap.render(-Game.WINDOW_WIDTH/2, -Game.WINDOW_HEIGHT/2);
		
		map.render(0, 0, 2);
		
		for(int i=0; i<objects.size(); i++)
		{
			
			objects.get(i).draw(g);
		}
		
		for(int i=0; i<tempVisuals.size(); i++)
		{
			
			tempVisuals.get(i).draw(g);
		}
		
		
		//GUI stuff
		g.resetTransform();
		
		//Enemies left
		int n = 0;
		for(int i=0; i<entities.size(); i++)
		{
			if(entities.get(i).type() == "BasicEnemy")
			{
				n++;
			}
		}
		
		g.setColor(Color.white);
		if(n == 1)
			g.drawString(n+" Zombie Left", Game.WINDOW_WIDTH-200, 10);
		else
			g.drawString(n+" Zombies Left", Game.WINDOW_WIDTH-200, 10);
		
		//Player health
		
		g.setColor(Color.gray);
		g.drawRect(10, 10, 150, 30);
		
		g.setColor(Color.red);
		g.fillRect(11, 11, (float) (149*(player.getHealth()/player.getMaxHealth())), 29);
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
				
				float distance = enemy.getPosition().copy().sub(player.getPosition()).length();
				
				if(distance < 300)
				{
				
					Vector2f aim = player.getPosition().copy().sub(enemy.getPosition());
					enemy.setAimDirection(aim.getTheta());
					
					if(aim.length() <= player.getRadius() + enemy.getRadius() + enemy.getRange())
					{
						enemy.shoot();
					}
					
					enemy.setVelocity(aim.normalise().scale(enemy.getMovementSpeed()));	
				}
				else
				{
					enemy.setAimDirection(enemy.getAimDirection() + (-5 + Math.random()*5));
					enemy.setVelocity(enemy.getAimVector().copy().normalise().scale(enemy.getMovementSpeed()/2));
				}
			}
		}
	}
	
	public static void resolveShooting(int delta)
	{
		for(int i=0; i<entities.size(); i++)
		{
			Entity entity = entities.get(i);
			
			
			
			if(entity.isShooting())
			{
				
				
				//Actually calculate something
				
				Vector2f shotEnd = new Vector2f(1,0);
				shotEnd.setTheta(entity.getAimDirection());
				
				ShootingResult result = Collider.shoot(objects, entity, entity.getPosition(), shotEnd);
				
				//Hit something
				if(entity.type() == "Player")
				{
					if(result.target != null)
					{
						if(result.target.hit(entity.getDamage()))
						{
							killEntity((Entity) result.target);
						}
						shotEnd.scale((float) result.t);
					}
					else
					{
						shotEnd.scale(Game.WINDOW_WIDTH);
					}
					
					shotEnd.add(entity.getPosition());
					
					Shot shot = new Shot(entity.getPosition(), shotEnd);
					tempVisuals.add(shot);
				}
				else if(entity.type() == "BasicEnemy")
				{
					
					
					if(result.target != null && result.t<=entity.getRange())
					{
						result.target.hit(entity.getDamage());
						
						shotEnd.scale((float) result.t);
					}
					else
					{
						shotEnd.scale(0);
					}
					
					shotEnd.add(entity.getPosition());
					
					Shot shot = new Shot(entity.getPosition(), shotEnd);
					//tempVisuals.add(shot);
				}
				
				//Visual
				
				
				
				
				entity.setShooting(false);
			}
			
			entity.updateTime(delta);
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

	public static void setAndInitializeMap(TiledMap map) throws SlickException {
		GameEngine.map = map;
		
		//Collision
		for(int i=0; i<map.getWidth(); i++)
		{
			for(int j=0; j<map.getHeight(); j++)
			{
				int tileID = map.getTileId(i, j, 0);
                String value = map.getTileProperty(tileID, "blocked", "false");
                if ("true".equals(value))
                {
                    MapCircle c = new MapCircle(new Vector2f(32*i+16, 32*j+16), 16);
                    addObject(c);
                }
			}
		}
		
		//Spawn
		for(int i=0; i<map.getWidth(); i++)
		{
			for(int j=0; j<map.getHeight(); j++)
			{
				int tileID = map.getTileId(i, j, 1);
				
                String value = map.getTileProperty(tileID, "spawnBasicZombie", "false");
                if ("true".equals(value))
                {
                	BasicEnemy enemy = new BasicEnemy(new Vector2f(32*i+16, 32*j+16), 16);
        			enemy.setAimColor(new Color(255, 0, 0));
        			enemy.setFillColor(new Color(0, 0, 255, 50));
        			enemy.setImage(new Image("res/images/zombie.png"));
        			
        			
        			enemy.setMovementSpeed(4f);
        			
        			GameEngine.addEntity(enemy);
                }
                
                value = map.getTileProperty(tileID, "playerSpawn", "false");
                if ("true".equals(value))
                {
                	Player player = new Player(new Vector2f(32*i+16, 32*j+16), 16);
            		player.setAimColor(new Color(255, 0, 0));
            		player.setFillColor(new Color(0, 255, 0, 50));
            		player.setImage(new Image("res/images/cowboy.png"));
            		
            		player.setMovementSpeed(5f);
            		GameEngine.setPlayer(player);
                }
			}
		}
	}

	public static TiledMap getBgroundmap() {
		return bgroundmap;
	}

	public static void setBgroundmap(TiledMap bgroundmap) {
		GameEngine.bgroundmap = bgroundmap;
	}
	
	

}
