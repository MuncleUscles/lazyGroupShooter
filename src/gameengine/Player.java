package gameengine;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class Player extends Entity{
	
	private Color fillColor, aimColor;
	
	public Player(Vector2f position, float radius)
	{
		setPosition(position);
		setRadius(radius);
		setVelocity(new Vector2f());
	}
	
	

	@Override
	public void draw(Graphics g) {
		
		g.setColor(fillColor);		
		g.fillOval(getPosition().getX() - getRadius(), getPosition().getY() - getRadius(), getRadius()*2 , getRadius()*2);
		
		
		Vector2f aimVector = getAimVector().add(getPosition());		
		
		g.setColor(aimColor);
		g.drawLine(getPosition().getX(), getPosition().getY(), aimVector.getX(), aimVector.getY());
		
	}



	public Color getFillColor() {
		return fillColor;
	}



	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}



	public Color getAimColor() {
		return aimColor;
	}



	public void setAimColor(Color aimColor) {
		this.aimColor = aimColor;
	}



	@Override
	public String type() {
		return "Player";
	}

}
