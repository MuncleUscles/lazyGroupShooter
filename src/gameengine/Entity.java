package gameengine;

import org.newdawn.slick.geom.Vector2f;

public abstract class Entity extends CircleObject{
	
	public Entity(Vector2f position, float radius) {
		super(position, radius);
	}

	private double aimDirection;
	
	private boolean isShooting;
	
	private float movementSpeed;
	
	private double maxHealth;
	
	private double health;
	
	private int firingDelay;
	
	private int timeLeftUntilShooting;

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

	public void shoot() {
		
		if(timeLeftUntilShooting <=0)
		{	
			this.isShooting = true;
			timeLeftUntilShooting = firingDelay;
		}
	}
	
	public void setShooting(boolean shooting)
	{
		this.isShooting = shooting;
	}
	
	public void updateTime(int delta)
	{
		timeLeftUntilShooting-=delta;
	}
	
	public abstract double getDamage();

	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
		this.setMaxHealth(health);
	}
	
	public abstract double getRange();

	public int getFiringDelay() {
		return firingDelay;
	}

	public void setFiringDelay(int firingDelay) {
		this.firingDelay = firingDelay;
	}

	public int getTimeLeftUntilShooting() {
		return timeLeftUntilShooting;
	}

	public void setTimeLeftUntilShooting(int timeLeftUntilShooting) {
		this.timeLeftUntilShooting = timeLeftUntilShooting;
	}
	
	@Override
	public boolean hit(double damage) {
		
		health -= damage;
		
		return (health <= 0);
	}

	public double getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(double maxHealth) {
		this.maxHealth = maxHealth;
	}
	
}
