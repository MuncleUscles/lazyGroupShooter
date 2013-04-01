package gameengine;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public class BasicEnemy extends Entity {
	
	private Color fillColor, aimColor;
	
	private Image image;
	
	private double aimChange;
	
	public BasicEnemy(Vector2f position, float radius)
	{
		super(position, radius);
		setVelocity(new Vector2f());
		setHealth(10);
		setFiringDelay(100);
	}
	
	

	@Override
	public void draw(Graphics g) {
		
		g.pushTransform();
		
		g.setColor(fillColor);		
		//g.fillOval(getPosition().getX() - getRadius(), getPosition().getY() - getRadius(), getRadius()*2 , getRadius()*2);
		
		
		Vector2f aimVector = new Vector2f(0, -1).scale(getRadius()).add(getPosition());		
		
		g.rotate(getPosition().getX(), getPosition().getY(), (float) (getAimDirection() + 90));
		
		image.drawCentered(getPosition().getX(), getPosition().getY());
		
		g.setColor(aimColor);
		//g.drawLine(getPosition().getX(), getPosition().getY(), aimVector.getX(), aimVector.getY());
		
		
		
		g.popTransform();
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
		return "BasicEnemy";
	}



	public Image getImage() {
		return image;
	}



	public void setImage(Image image) {
		this.image = image;
	}
	
	



	@Override
	public double getDamage() {
		return 3;
	}



	@Override
	public double getRange() {
		return 15;
	}
	
	public void doAI(Player player)
	{
		float distance = this.getPosition().copy().sub(player.getPosition()).length();
		
		if(distance < 300)
		{
		
			Vector2f aim = player.getPosition().copy().sub(this.getPosition());
			this.setAimDirection(aim.getTheta());
			
			if(aim.length() <= player.getRadius() + this.getRadius() + this.getRange())
			{
				this.shoot();
			}
			
			this.setVelocity(aim.normalise().scale(this.getMovementSpeed()));	
		}
		else
		{
			aimChange += (-0.5 + Math.random()*1);
			
			if(aimChange < -2) aimChange = -2;
			else if(aimChange > 2) aimChange = 2;
			
			setAimDirection(getAimDirection() + aimChange);
			this.setVelocity(this.getAimVector().copy().normalise().scale(this.getMovementSpeed()/4));
		}
	}

}
