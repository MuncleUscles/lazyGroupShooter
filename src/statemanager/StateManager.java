package statemanager;

import gameengine.GameState;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class StateManager {
	
	private static GameState gameState;
	private static MainMenuState mainMenuState;
	private static State activeState;
	
	static {
		gameState = new GameState();
		try {
			gameState.setBgroundmap(new TiledMap("res/levels/background.tmx"));
			gameState.setAndInitializeMap( new TiledMap("res/levels/desert.tmx"));
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		mainMenuState = new MainMenuState();
	}
	
	
	public static void setState(String stateName)
	{
		if("gameState".equals(stateName))
		{
			activeState = gameState;
		}
		else if("mainMenuState".equals(stateName))
		{
			activeState = mainMenuState;
		}
	}
	
	public static State getActiveState()
	{
		return activeState;
	}

	public static GameState getGameState() {
		return gameState;
	}

	public static MainMenuState getMainMenuState() {
		return mainMenuState;
	}

}
