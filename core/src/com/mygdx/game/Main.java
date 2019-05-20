package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.mygdx.game.Controllers.AssetsManager;
import com.mygdx.game.Models.AppPreferences;
import com.mygdx.game.Views.GameScreen;
import com.mygdx.game.Views.MenuScreen;
import com.mygdx.game.Views.PreferencesScreen;

public class Main extends Game {
	private PreferencesScreen preferencesScreen;
	private MenuScreen menuScreen;
	private GameScreen gameScreen;
	private AppPreferences preferences;
	public AssetsManager assMan = new AssetsManager();
	private Music playingSong;

	public final static int MENU = 0;
	public final static int PREFERENCES = 1;
	public final static int GAME = 2;

	@Override
	public void create () {
		menuScreen = new MenuScreen(this);
		preferences = new AppPreferences();
		setScreen(menuScreen);

		// tells our asset manger that we want to load the images set in loadImages method
		assMan.queueAddMusic();
		// tells the asset manager to load the images and wait until finished loading.
		assMan.manager.finishLoading();
		// loads the 2 sounds we use
		playingSong = assMan.manager.get("music/Rolemusic_-_pl4y1ng.mp3");
		playingSong.play();
	}

	public void changeScreen(int screen){
		switch(screen){
			case MENU:
				if(menuScreen == null) menuScreen = new MenuScreen(this);
				this.setScreen(menuScreen);
				break;
			case PREFERENCES:
				if(preferencesScreen == null) preferencesScreen = new PreferencesScreen(this);
				this.setScreen(preferencesScreen);
				break;
			case GAME:
				if(gameScreen == null) gameScreen = new GameScreen(this);
				this.setScreen(gameScreen);
				break;
		}
	}

	public AppPreferences getPreferences(){
		return this.preferences;
	}

	@Override
	public void dispose(){
		playingSong.dispose();
		assMan.manager.dispose();
	}
}
