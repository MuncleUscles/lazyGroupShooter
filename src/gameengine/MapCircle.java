package gameengine;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class MapCircle extends CircleObject {

	public MapCircle(Vector2f position, float radius) {
		super(position, radius);
		setVelocity(new Vector2f(0, 0));
		
		
	}
	
	@Override
	public boolean isStatic() {
		return true;
	}
	
	@Override
	public void draw(Graphics g) {


		g.setColor(Color.green);		
		//g.fillOval(getPosition().getX() - getRadius(), getPosition().getY() - getRadius(), getRadius()*2 , getRadius()*2);
		
	}

}
