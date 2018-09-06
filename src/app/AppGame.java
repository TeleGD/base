package app;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class AppGame extends StateBasedGame {

	public static final int PAGES_WELCOME = 0;
	public static final int PAGES_PAUSE = 1;
	public static final int TEST_WORLD = 2;

	public static final String [] TITLES = new String [] {
		"Accueil",
		"Pause",
		"TeleKingdom"
	};

	public AppGame (String name) {
		super (name);
	}

	@Override
	public void initStatesList (GameContainer container) {
		this.addState (new pages.Welcome (AppGame.PAGES_WELCOME));
		this.addState (new pages.Pause (AppGame.PAGES_PAUSE));
		this.addState (new test.World (AppGame.TEST_WORLD));
	}

}
