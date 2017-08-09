package com.pixelswithcode.libgdx.rps.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.pixelswithcode.libgdx.rps.RPSGame;
import com.pixelswithcode.libgdx.rps.utils.Globals;

public class LoadingScreen implements Screen {

    private final String TAG = LoadingScreen.class.getSimpleName();

    private final RPSGame GAME;
    private boolean hasDisposed;

    private Texture backgroundTexture;
    private Texture loadingBarTexture;

    public LoadingScreen(final RPSGame game) {
        this.GAME = game;
        this.hasDisposed = true;
    }

    @Override
    public void show() {
        this.hasDisposed = false;
        Gdx.app.log(TAG, "Screen showed");

        //Load loading assets
        GAME.assetManager.load(Globals.LOADING_PATH + "loading-background.png", Texture.class);
        GAME.assetManager.load(Globals.LOADING_PATH + "loading-bar.png", Texture.class);
        GAME.assetManager.finishLoading();

        this.backgroundTexture = GAME.assetManager.get(Globals.LOADING_PATH + "loading-background.png", Texture.class);
        this.loadingBarTexture = GAME.assetManager.get(Globals.LOADING_PATH + "loading-bar.png", Texture.class);

        setupeLoadingScreen();

        loadAssets();
    }

    @Override
    public void render(float delta) {
        Gdx.app.log(TAG, "Loaded: " + GAME.assetManager.getProgress() * 100);

        if (GAME.assetManager.update()) {
            switch (GAME.currentGameScreen) {
                case MAIN_MENU_SCREEN:
                    GAME.setScreen(GAME.mainMenuScreen);
                    break;
                case BATTLE_SCREEN:
                    GAME.setScreen(GAME.battleScreen);
                    break;
            }
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        if (!this.hasDisposed) {
            this.backgroundTexture.dispose();
            this.loadingBarTexture.dispose();

            GAME.assetManager.unload(Globals.LOADING_PATH + "loading-background.png");
            GAME.assetManager.unload(Globals.LOADING_PATH + "loading-bar.png");

            this.hasDisposed = true;

            Gdx.app.log(TAG, "Objects has been disposed!");
        }
    }

    private void setupeLoadingScreen() {
        GAME.stage.clear();

        Table backgroundTable = new Table();
        backgroundTable.setFillParent(true);
        backgroundTable.align(Align.bottomLeft);
        backgroundTable.add(new Image(this.backgroundTexture)).width(64).height(64);

        Table loadingBarTable = new Table();
        loadingBarTable.setFillParent(true);
        loadingBarTable.align(Align.center);
        loadingBarTable.add(new Image(this.loadingBarTexture)).width(48).height(12);

        GAME.stage.addActor(backgroundTable);
        GAME.stage.addActor(loadingBarTable);
    }

    private void loadAssets() {
        switch (GAME.currentGameScreen) {
            case MAIN_MENU_SCREEN:
                queueMainMenuAssets();
                break;
            case BATTLE_SCREEN:
                queueBattleScreenAssets();
                break;
        }
    }

    private void queueMainMenuAssets() {
        // Main Menu Screen
        GAME.assetManager.load(Globals.MAIN_PATH + "main-menu-background.png", Texture.class);
        GAME.assetManager.load(Globals.MAIN_PATH + "game-title.png", Texture.class);
        GAME.assetManager.load(Globals.MAIN_PATH + "help-btn-normal.png", Texture.class);
        GAME.assetManager.load(Globals.MAIN_PATH + "help-btn-clicked.png", Texture.class);
        GAME.assetManager.load(Globals.MAIN_PATH + "settings-btn-normal.png", Texture.class);
        GAME.assetManager.load(Globals.MAIN_PATH + "settings-btn-clicked.png", Texture.class);
        GAME.assetManager.load(Globals.MAIN_PATH + "quit-btn-normal.png", Texture.class);
        GAME.assetManager.load(Globals.MAIN_PATH + "quit-btn-clicked.png", Texture.class);
        GAME.assetManager.load(Globals.MAIN_PATH + "story-btn-normal.png", Texture.class);
        GAME.assetManager.load(Globals.MAIN_PATH + "story-btn-clicked.png", Texture.class);
        GAME.assetManager.load(Globals.MAIN_PATH + "versus-btn-normal.png", Texture.class);
        GAME.assetManager.load(Globals.MAIN_PATH + "versus-btn-clicked.png", Texture.class);

        // Select Screen
        GAME.assetManager.load(Globals.SELECT_PATH + "select-person-dialog.png", Texture.class);
        GAME.assetManager.load(Globals.SELECT_PATH + "select-person1-unlocked.png", Texture.class);
        GAME.assetManager.load(Globals.SELECT_PATH + "select-person2-unlocked.png", Texture.class);
        GAME.assetManager.load(Globals.SELECT_PATH + "select-person3-unlocked.png", Texture.class);
        GAME.assetManager.load(Globals.SELECT_PATH + "select-person4-unlocked.png", Texture.class);
        GAME.assetManager.load(Globals.SELECT_PATH + "select-person5-unlocked.png", Texture.class);
        GAME.assetManager.load(Globals.SELECT_PATH + "select-person6-unlocked.png", Texture.class);
        GAME.assetManager.load(Globals.SELECT_PATH + "select-person2-locked.png", Texture.class);
        GAME.assetManager.load(Globals.SELECT_PATH + "select-person3-locked.png", Texture.class);
        GAME.assetManager.load(Globals.SELECT_PATH + "select-person4-locked.png", Texture.class);
        GAME.assetManager.load(Globals.SELECT_PATH + "select-person5-locked.png", Texture.class);
        GAME.assetManager.load(Globals.SELECT_PATH + "select-person6-locked.png", Texture.class);
        GAME.assetManager.load(Globals.SELECT_PATH + "player-one-selection-icon.png", Texture.class);
        GAME.assetManager.load(Globals.SELECT_PATH + "player-two-selection-icon.png", Texture.class);

        //Help Screen
        GAME.assetManager.load(Globals.HELP_PATH + "help-dialog-1.png", Texture.class);
        GAME.assetManager.load(Globals.HELP_PATH + "help-dialog-2.png", Texture.class);
        GAME.assetManager.load(Globals.HELP_PATH + "help-dialog-3.png", Texture.class);
        GAME.assetManager.load(Globals.HELP_PATH + "help-dialog-back-btn.png", Texture.class);
        GAME.assetManager.load(Globals.HELP_PATH + "help-dialog-forward-btn.png", Texture.class);
        GAME.assetManager.load(Globals.HELP_PATH + "help-dialog-close-btn.png", Texture.class);
        GAME.assetManager.load(Globals.HELP_PATH + "help-dialog-bottom.png", Texture.class);

        // Settings Screen
        GAME.assetManager.load(Globals.SETTINGS_PATH + "settings-dialog-top.png", Texture.class);
        GAME.assetManager.load(Globals.SETTINGS_PATH + "settings-dialog-bottom.png", Texture.class);
        GAME.assetManager.load(Globals.SETTINGS_PATH + "settings-dialog-close-btn.png", Texture.class);
        GAME.assetManager.load(Globals.SETTINGS_PATH + "toggle-button-on.png", Texture.class);
        GAME.assetManager.load(Globals.SETTINGS_PATH + "toggle-button-off.png", Texture.class);

        // Sound and Music
        GAME.assetManager.load(Globals.MUSIC_PATH + "eric-skiff-chibi-ninja.mp3", Music.class);
        GAME.assetManager.load(Globals.SOUND_PATH + "click1.wav", Sound.class);
        GAME.assetManager.load(Globals.SOUND_PATH + "click2.wav", Sound.class);
        GAME.assetManager.load(Globals.SOUND_PATH + "click3.wav", Sound.class);
        GAME.assetManager.load(Globals.SOUND_PATH + "selection_fanfare.wav", Sound.class);
    }

    private void queueBattleScreenAssets() {
        //Sprites
        GAME.assetManager.load(Globals.BATTLE_PATH + "battle-arena-background.png", Texture.class);
        GAME.assetManager.load(Globals.BATTLE_PATH + "boss-completed-message.png", Texture.class);
        GAME.assetManager.load(Globals.BATTLE_PATH + "boss-not-completed-message.png", Texture.class);

        GAME.assetManager.load(Globals.BATTLE_PATH + "icon-ready.png", Texture.class);
        GAME.assetManager.load(Globals.BATTLE_PATH + "icon-not-ready.png", Texture.class);
        GAME.assetManager.load(Globals.BATTLE_PATH + "icon-paper.png", Texture.class);
        GAME.assetManager.load(Globals.BATTLE_PATH + "icon-rock.png", Texture.class);
        GAME.assetManager.load(Globals.BATTLE_PATH + "icon-scissors.png", Texture.class);

        GAME.assetManager.load(Globals.BATTLE_PATH + "person1-left.png", Texture.class);
        GAME.assetManager.load(Globals.BATTLE_PATH + "person2-left.png", Texture.class);
        GAME.assetManager.load(Globals.BATTLE_PATH + "person3-left.png", Texture.class);
        GAME.assetManager.load(Globals.BATTLE_PATH + "person4-left.png", Texture.class);
        GAME.assetManager.load(Globals.BATTLE_PATH + "person5-left.png", Texture.class);
        GAME.assetManager.load(Globals.BATTLE_PATH + "person6-left.png", Texture.class);
        GAME.assetManager.load(Globals.BATTLE_PATH + "person1-right.png", Texture.class);
        GAME.assetManager.load(Globals.BATTLE_PATH + "person2-right.png", Texture.class);
        GAME.assetManager.load(Globals.BATTLE_PATH + "person3-right.png", Texture.class);
        GAME.assetManager.load(Globals.BATTLE_PATH + "person4-right.png", Texture.class);
        GAME.assetManager.load(Globals.BATTLE_PATH + "person5-right.png", Texture.class);
        GAME.assetManager.load(Globals.BATTLE_PATH + "person6-right.png", Texture.class);
        GAME.assetManager.load(Globals.BATTLE_PATH + "story-npc.png", Texture.class);

        GAME.assetManager.load(Globals.BATTLE_PATH + "round-box-1.png", Texture.class);
        GAME.assetManager.load(Globals.BATTLE_PATH + "round-box-2.png", Texture.class);
        GAME.assetManager.load(Globals.BATTLE_PATH + "round-box-3.png", Texture.class);
        GAME.assetManager.load(Globals.BATTLE_PATH + "round-box-4.png", Texture.class);
        GAME.assetManager.load(Globals.BATTLE_PATH + "round-box-5.png", Texture.class);
        GAME.assetManager.load(Globals.BATTLE_PATH + "round-box-boss.png", Texture.class);

        GAME.assetManager.load(Globals.BATTLE_PATH + "score-00.png", Texture.class);
        GAME.assetManager.load(Globals.BATTLE_PATH + "score-10.png", Texture.class);
        GAME.assetManager.load(Globals.BATTLE_PATH + "score-01.png", Texture.class);
        GAME.assetManager.load(Globals.BATTLE_PATH + "score-11.png", Texture.class);
        GAME.assetManager.load(Globals.BATTLE_PATH + "score-12.png", Texture.class);
        GAME.assetManager.load(Globals.BATTLE_PATH + "score-21.png", Texture.class);
        GAME.assetManager.load(Globals.BATTLE_PATH + "score-02.png", Texture.class);
        GAME.assetManager.load(Globals.BATTLE_PATH + "score-20.png", Texture.class);

        GAME.assetManager.load(Globals.BATTLE_PATH + "play-again-dialog.png", Texture.class);
        GAME.assetManager.load(Globals.BATTLE_PATH + "exit-game-dialog.png", Texture.class);
        GAME.assetManager.load(Globals.BATTLE_PATH + "yes-btn.png", Texture.class);
        GAME.assetManager.load(Globals.BATTLE_PATH + "no-btn.png", Texture.class);

        // Sound and music
        GAME.assetManager.load(Globals.MUSIC_PATH + "battle-theme.ogg", Music.class);
        GAME.assetManager.load(Globals.SOUND_PATH + "win-jingle.wav", Sound.class);
        GAME.assetManager.load(Globals.SOUND_PATH + "lose-jingle.wav", Sound.class);
        GAME.assetManager.load(Globals.SOUND_PATH + "giggle.wav", Sound.class);
        GAME.assetManager.load(Globals.SOUND_PATH + "draw.wav", Sound.class);
        GAME.assetManager.load(Globals.SOUND_PATH + "click1.wav", Sound.class);
    }
}
