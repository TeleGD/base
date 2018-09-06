package app;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public abstract class AppWorld extends BasicGameState {

	private int ID;

	public AppWorld (int ID) {
		this.ID = ID;
	}

	@Override
	public int getID () {
		return this.ID;
	}

	@Override
	public void update (GameContainer container, StateBasedGame game, int delta) {
		AppInput appInput = (AppInput) container.getInput ();
		AppGame appGame = (AppGame) game;
		if (appInput.isKeyDown (AppInput.KEY_ESCAPE)) {
			this.pause (container, game);
			appGame.enterState (AppGame.PAGES_PAUSE, new FadeOutTransition (), new FadeInTransition ());
		}
	}

	public void play (GameContainer container, StateBasedGame game) {}

	public void pause (GameContainer container, StateBasedGame game) {}

	public void resume (GameContainer container, StateBasedGame game) {}

}