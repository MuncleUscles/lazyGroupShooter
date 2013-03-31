package gameengine;

import main.Game;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;


public abstract class GameObject {
	
	private Vector2f position;
	
	private Vector2f velocity;
	
	public abstract void draw(Graphics g);
	
	
	public void move(int delta)
	{
		position.add(velocity.scale((float) (delta/Game.FRAME_TIME)));
		velocity = new Vector2f();
	}
	
	public void move(double t)
	{
		position.add(velocity.copy().scale((float) t));
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f coordinates) {
		this.position = coordinates;
	}


	public Vector2f getVelocity() {
		return velocity;
	}
	
	public Vector2f getVelocity(int delta)
	{
		return velocity.copy().scale((float) (delta/Game.FRAME_TIME));
	}
	
	

	public void setVelocity(Vector2f velocity) {
		this.velocity = velocity;
	}

	public abstract boolean isCollidable();

	
	
	public abstract String type();
	
	public abstract String collisionType();
	
	public abstract boolean hit(double damage);
	
	public abstract boolean isStatic();

}
