package gameengine;
import java.util.ArrayList;
import java.util.Iterator;

import main.Game;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

import statemanager.State;
import statemanager.StateManager;

import collision.Collider;
import collision.ShootingResult;


public class GameState extends State {
	
	private int state;
	
	private static int STATE_GAME = 0;
	private static int STATE_WON = 1;
	private static int STATE_DEAD = 2;
	
	
	private ArrayList<GameObject> objects;
	
	private ArrayList<TempVisualObject> tempVisuals;
	
	private ArrayList<Entity> entities;
	
	private Player player;
	
	private TiledMap map, bgroundmap;

	private int enemiesLeft;
	

	
	public GameState()
	{
		objects = new ArrayList<GameObject>();
		tempVisuals = new ArrayList<TempVisualObject>();
		entities = new  ArrayList<Entity>();
		
		state = STATE_GAME;
	}
	
	public void addEntity(Entity entity)
	{
		objects.add(entity);
		entities.add(entity);
	}
	
	public void killEntity(Entity entity)
	{
		if(entity != player)
		{
			objects.remove(entity);
			entities.remove(entity);
		}
		
	}
	
	public void removeEntity(Entity entity)
	{
		objects.remove(entity);
		entities.remove(entity);
	}
	
	public void addObject(GameObject object)
	{
		objects.add(object);
	}
	
	public void removeObject(GameObject object)
	{
		objects.remove(object);
	}
	
	@Override
	public void update(GameContainer gc, int delta)
	{
		//temp visuals
		processTempVisuals(delta);
		
		
		//process input
		processInput(gc);
		
		//AI set velocity, aim and shoot
		processAI();
		
		//Resolve shooting
		resolveShooting(delta);
		
		
		//Resolve movement
		resolveMovement(delta);
		
		//Move
		move(delta);
		
		
		
		//States
		if(player.getHealth() <= 0)
			state = STATE_DEAD;
		
		int n = 0;
		for(int i=0; i<entities.size(); i++)
		{
			if(entities.get(i).type() == "BasicEnemy")
			{
				n++;
			}
		}
		enemiesLeft = n;
		
		if(enemiesLeft == 0)
			state = STATE_WON;
	}
	
	public void processInput(GameContainer gc)
	{		
		Input input = gc.getInput();
		
		if(state == STATE_GAME)
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
		else if(state == STATE_DEAD)
		{
			
		}
		else if(state == STATE_WON)
		{
			
		}
		
		if(input.isKeyDown(Input.KEY_R))
		{
			restart();
		}
		
		
		if(input.isKeyPressed(Input.KEY_ESCAPE))
		{
			StateManager.setState("mainMenuState");
		}
	}
	
	@Override
	public void draw(Graphics g)
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
		
		
		if(state == STATE_GAME)
		{
		
			//Enemies left	
			
			g.setColor(Color.white);
			if(enemiesLeft == 1)
				g.drawString(enemiesLeft+" Zombie Left", Game.WINDOW_WIDTH-200, 10);
			else
				g.drawString(enemiesLeft+" Zombies Left", Game.WINDOW_WIDTH-200, 10);
			
			//Player health
			
			g.setColor(Color.gray);
			g.drawRect(10, 10, 150, 30);
			
			g.setColor(Color.red);
			g.fillRect(11, 11, (float) (149*(player.getHealth()/player.getMaxHealth())), 29);
		}
		else if(state == STATE_DEAD)
		{
			g.setColor(Color.white);
			g.translate(Game.WINDOW_WIDTH/2, Game.WINDOW_HEIGHT/2);
			g.scale(3, 3);
			g.drawString("YOU'RE DEAD!", -50, -10);
			g.drawString("Press 'R' to retry", -75, 10);
		}
		else if(state == STATE_WON)
		{
			g.setColor(Color.white);
			g.translate(Game.WINDOW_WIDTH/2, Game.WINDOW_HEIGHT/2);
			g.scale(3, 3);
			g.drawString("YOU WON!", -50, -10);
			g.drawString("Press 'R' to retry", -75, 10);
		}
	}
	
	public  void processTempVisuals(int delta)
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
	
	public  void processAI()
	{
		for(int i=0; i<entities.size(); i++)
		{
			
			//Basic enemies
			if(entities.get(i).type().equals("BasicEnemy"))
			{
				BasicEnemy enemy = (BasicEnemy) entities.get(i);
				
				enemy.doAI(player);
			}
		}
	}
	
	public  void resolveShooting(int delta)
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
						if(result.target.hit(entity.getDamage()))
						{
							killEntity((Entity) result.target);
						}
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
	
	public  void resolveMovement(int delta)
	{
		//Collider.doCollisions(objects, delta);
		Collider.resolveAll(objects);
	}
	
	public  void move(int delta)
	{
		for(int i=0; i<objects.size(); i++)
		{
			objects.get(i).move(delta);
		}
	}

	public  Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
		
		addObject(player);
		addEntity(player);
	}

	

	public TiledMap getMap() {
		return map;
	}

	public void setAndInitializeMap(TiledMap map) {
		this.map = map;
		
		restart();
	}
	
	public void restart() {
		
		objects.clear();
		entities.clear();
		tempVisuals.clear();
		
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
        			try {
						enemy.setImage(new Image("res/images/zombie.png"));
					} catch (SlickException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        			
        			
        			
        			enemy.setMovementSpeed(4f);
        			
        			addEntity(enemy);
                }
                
                value = map.getTileProperty(tileID, "spawnPlayer", "false");
                if ("true".equals(value))
                {
                	Player player = new Player(new Vector2f(32*i+16, 32*j+16), 16);
            		player.setAimColor(new Color(255, 0, 0));
            		player.setFillColor(new Color(0, 255, 0, 50));
            		try {
						player.setImage(new Image("res/images/cowboy.png"));
					} catch (SlickException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            		
            		player.setMovementSpeed(5f);
            		setPlayer(player);


            		
                }
			}
		}
		
		state = STATE_GAME;
	}

	public TiledMap getBgroundmap() {
		return bgroundmap;
	}

	public void setBgroundmap(TiledMap bgroundmap) {
		this.bgroundmap = bgroundmap;
	}
	
	

}
