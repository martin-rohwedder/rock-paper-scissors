package com.pixelswithcode.libgdx.rps;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.pixelswithcode.libgdx.rps.screens.BattleScreen;
import com.pixelswithcode.libgdx.rps.screens.LoadingScreen;
import com.pixelswithcode.libgdx.rps.screens.MainMenuScreen;
import com.pixelswithcode.libgdx.rps.screens.SplashScreen;
import com.pixelswithcode.libgdx.rps.utils.GameModes;
import com.pixelswithcode.libgdx.rps.utils.GameSceens;
import com.pixelswithcode.libgdx.rps.utils.Globals;

public class RPSGame extends Game {

	private final String TAG = RPSGame.class.getSimpleName();

	public SpriteBatch batch;
	public AssetManager assetManager;
	public Stage stage;

	public GameModes currentGameMode;
	public int playerOneSelection;
	public int playerTwoSelection;

	public GameSceens currentGameScreen;

	public LoadingScreen loadingScreen;
	public SplashScreen splashScreen;
	public MainMenuScreen mainMenuScreen;
	public BattleScreen battleScreen;

	@Override
	public void create () {
		this.batch = new SpriteBatch();
		this.assetManager = new AssetManager();
		this.stage = new Stage(new FitViewport(Globals.WORLD_WIDTH, Globals.WORLD_HEIGHT, new OrthographicCamera()), this.batch);

		//Setup Preferences
		setupSettingPreferences();
		setupGamePreferences();

		this.currentGameMode = GameModes.STORY_MODE;
		this.playerOneSelection = 0;
		this.playerTwoSelection = 0;

		this.loadingScreen = new LoadingScreen(this);
		this.splashScreen = new SplashScreen(this);
		this.mainMenuScreen = new MainMenuScreen(this);
		this.battleScreen = new BattleScreen(this);

		this.currentGameScreen = GameSceens.MAIN_MENU_SCREEN;
		setScreen(this.splashScreen);

		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(stage);
		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(35 / 255f, 35 / 255f, 35 / 255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		super.render();

		this.stage.act(Gdx.graphics.getDeltaTime());
		this.stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		this.stage.getViewport().update(width, height, true);
	}

	@Override
	public void dispose () {
		super.dispose();

		this.batch.dispose();
		this.assetManager.dispose();
		this.stage.dispose();

		Gdx.app.log(TAG, "Objects Disposed");
	}

	private void setupSettingPreferences() {
		Preferences settingsPreferences = Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME);

		// Create default settings if they doesn't exists
		if (!settingsPreferences.contains("prefs_created")) {
			settingsPreferences.putBoolean("music_on", true);
			settingsPreferences.putBoolean("sound_on", true);

			settingsPreferences.putBoolean("prefs_created", true);

			//Save preferences
			settingsPreferences.flush();
		}
	}

	private void setupGamePreferences() {
		Preferences gamePreferences = Gdx.app.getPreferences(Globals.GAME_PREFS_NAME);

		// Create default settings if they doesn't exists
		if (!gamePreferences.contains("prefs_created")) {
			gamePreferences.putBoolean("player_two_unlocked", false);
			gamePreferences.putBoolean("player_three_unlocked", false);
			gamePreferences.putBoolean("player_four_unlocked", false);
			gamePreferences.putBoolean("player_five_unlocked", false);
			gamePreferences.putBoolean("player_six_unlocked", false);

			gamePreferences.putBoolean("prefs_created", true);

			gamePreferences.flush();
		}
	}

	public void resetGamePreferences() {
		Preferences gamePreferences = Gdx.app.getPreferences(Globals.GAME_PREFS_NAME);

		gamePreferences.putBoolean("player_two_unlocked", false);
		gamePreferences.putBoolean("player_three_unlocked", false);
		gamePreferences.putBoolean("player_four_unlocked", false);
		gamePreferences.putBoolean("player_five_unlocked", false);
		gamePreferences.putBoolean("player_six_unlocked", false);

		gamePreferences.putBoolean("prefs_created", true);

		gamePreferences.flush();
	}
}
