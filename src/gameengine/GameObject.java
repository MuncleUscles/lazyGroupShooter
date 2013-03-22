package gameengine;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;


public abstract class GameObject {
	
	private Vector2f position;
	
	private Vector2f velocity;
	
	private boolean isCollidable;
	
	public abstract void draw(Graphics g);

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f coordinates) {
		this.position = coordinates;
	}


	public Vector2f getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2f velocity) {
		this.velocity = velocity;
	}

	public boolean isCollidable() {
		return isCollidable;
	}

	public void setCollidable(boolean isCollidable) {
		this.isCollidable = isCollidable;
	}
	
	

}
