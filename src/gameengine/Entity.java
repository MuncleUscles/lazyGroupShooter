package gameengine;

import org.newdawn.slick.geom.Vector2f;

public abstract class Entity extends CircleObject{
	
	private double aimDirection;
	
	private boolean isShot;
	
	private boolean isShooting;
	
	private float movementSpeed;

	public double getAimDirection() {
		return aimDirection;
	}

	public void setAimDirection(double aimDirection) {
		this.aimDirection = aimDirection;
	}

	public boolean isShot() {
		return isShot;
	}

	public void setShot(boolean isShot) {
		this.isShot = isShot;
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
	
}
