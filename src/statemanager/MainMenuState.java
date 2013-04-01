package statemanager;

import main.Game;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class MainMenuState extends State {
	
	public MainMenuState()
	{
		
	}

	@Override
	public void update(GameContainer gc, int delta) {
		if(gc.getInput().isKeyPressed(Input.KEY_ESCAPE))
		{
			gc.exit();
		}
		
		if(gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON) )
		{
			Input input = gc.getInput();
			
			if(input.getMouseY() >= 100  && input.getMouseY() < 120)
			{
				try {
					StateManager.getGameState().setAndInitializeMap( new TiledMap("res/levels/desert.tmx"));
				} catch (SlickException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				StateManager.setState("gameState");
			}
			else if(input.getMouseY() >= 120  && input.getMouseY() < 140)
			{
				try {
					StateManager.getGameState().setAndInitializeMap( new TiledMap("res/levels/desert2.tmx"));
				} catch (SlickException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				StateManager.setState("gameState");
			}
			if(input.getMouseY() >= 140  && input.getMouseY() < 160)
			{
				gc.exit();
			}
			
			
		}
	}

	@Override
	public void draw(Graphics g) {
		
		g.setColor(Color.white);
		g.translate(100, 100);
		//g.scale(1.5f, 1.5f);
		g.drawString("Level 1", 0, 0);
		g.drawString("Level 2", 0, 20);
		g.drawString("Exit", 0, 40);
		
	}

}
