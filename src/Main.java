import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;
public class Main extends BasicGame
{
	
	long totalTime;

	public Main()
	{
		super("The Game was lost by me");
		
		totalTime = 0;
	}
 
	@Override
	public void init(GameContainer gc) throws SlickException
	{
		
	}
 
	@Override
	public void update(GameContainer gc, int delta) throws SlickException
	{
		totalTime += delta;
	}
 
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		g.resetTransform();
		
		g.setColor(new Color((float)(Math.sin(totalTime/30.0)+1)/2, (float)(Math.sin(totalTime/35.0)+1)/2, (float)(Math.sin(totalTime/25.0)+1)/2));
		
		g.fill(new Rectangle(0, 0, 800, 600));
		
		g.translate(400, 300);
		
		g.scale((float) (Math.sin(totalTime/200.0)*1.5+3), (float)(Math.sin(totalTime/200.0)*1.5+3));
		
		g.rotate(0, 0, (float) (Math.sin(totalTime/100.0)*30));
		
		g.setColor(new Color((float)(-Math.sin(totalTime/30.0)+1)/2, (float)(-Math.sin(totalTime/35.0)+1)/2, (float)(-Math.sin(totalTime/25.0)+1)/2));		
		
		g.drawString("Get to work!", -55, -10);
		
	}
 
	public static void main(String[] args) throws SlickException
	{
		AppGameContainer app = new AppGameContainer(new Main());
 
		app.setDisplayMode(800, 600, false);
		app.start();
	}
}