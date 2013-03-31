package gameengine;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class CircleObject extends GameObject {
	
	private float radius;
	
	public CircleObject(Vector2f position, float radius)
	{
		setPosition(position);
		setRadius(radius);
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}
	
	
	@Override
	public String collisionType()
	{
		return "Circle";
	}
	
	@Override
	public boolean isCollidable() {
		return true;
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String type() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hit(double damage) {
		// TODO Auto-generated method stub
		return false;
	}

}
