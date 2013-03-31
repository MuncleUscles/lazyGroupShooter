package gameengine;

import org.newdawn.slick.geom.Vector2f;

public abstract class Entity extends CircleObject{
	
	public Entity(Vector2f position, float radius) {
		super(position, radius);
	}

	private double aimDirection;
	
	private boolean isShooting;
	
	private float movementSpeed;
	
	private double health;

	public double getAimDirection() {
		return aimDirection;
	}

	public void setAimDirection(double aimDirection) {
		this.aimDirection = aimDirection;
	}

	

	public float getMovementSpeed() {
		return movementSpeed;
	}

	public void setMovementSpeed(float movementSpeed) {
		this.movementSpeed = movementSpeed;
	}
	
	public Vector2f getAimVector()
	{
		Vector2f aimVector = new Vector2f(1, 0);
		
		aimVector.setTheta(getAimDirection());
		
		aimVector.scale(getRadius());
		
		return aimVector;
	}

	public boolean isShooting() {
		return isShooting;
	}

	public void setShooting(boolean isShooting) {
		this.isShooting = isShooting;
	}
	
	public abstract double getDamage();

	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
	}
	
	
	
}
