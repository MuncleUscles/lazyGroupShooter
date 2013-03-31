package gameengine;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class Shot extends TempVisualObject{
	
	private Vector2f end;
	
	private static int SHOT_TIME = 100;
	
	public Shot(Vector2f start, Vector2f end)
	{
		this.setExistanceTime(SHOT_TIME);
		
		this.setPosition(start);
		
		this.end = end;
	}

	@Override
	public void draw(Graphics g) {
		
		g.setColor(Color.red);
		
		g.drawLine(getPosition().x, getPosition().y, end.x, end.y);
		
		
		
	}

	@Override
	public String type() {
		return "Shot";
	}

	@Override
	public String collisionType() {
		return "Line";
	}

	@Override
	public boolean hit(double damage) {
		// TODO Auto-generated method stub
		return false;
	}

	

}
