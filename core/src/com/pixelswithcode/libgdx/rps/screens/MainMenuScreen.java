package com.pixelswithcode.libgdx.rps.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.pixelswithcode.libgdx.rps.RPSGame;
import com.pixelswithcode.libgdx.rps.utils.GameModes;
import com.pixelswithcode.libgdx.rps.utils.GameSceens;
import com.pixelswithcode.libgdx.rps.utils.Globals;

public class MainMenuScreen extends ScreenAdapter {

    private final String TAG = MainMenuScreen.class.getSimpleName();

    private final RPSGame GAME;
    private boolean hasDisposed;

    // Is Game starting?
    private boolean hasGameStarted;
    private boolean isDialogVisible;

    // Current help page
    private int currentHelpPage;

    //Music and sounds
    private Music backgroundMusic;
    private Sound clickOneSound;
    private Sound clickTwoSound;
    private Sound clickThreeSound;
    private Sound selectionFanfareSound;
    private Sound resetSound;

    //Main menu Textures
    private Texture backgroundTexture;
    private Texture gameTitleTexture;
    private Texture storyBtnNormalTexture;
    private Texture storyBtnClickedTexture;
    private Texture versusBtnNormalTexture;
    private Texture versusBtnClickedTexture;
    private Texture quitBtnNormalTexture;
    private Texture quitBtnClickedTexture;
    private Texture settingsBtnNormalTexture;
    private Texture settingsBtnClickedTexture;
    private Texture helpBtnNormalTexture;
    private Texture helpBtnClickedTexture;

    private Texture resetStoryDialogTexture;
    private Texture yesBtnTexture;
    private Texture noBtnTexture;

    //Settings textures
    private Texture settingsDialogTopTexture;
    private Texture settingsDialogBottomTexture;
    private Texture settingsDialogCloseBtnTexture;
    private Texture settingsToggleOnTexture;
    private Texture settingsToggleOffTexture;

    // Help Textures
    private Texture helpDialogOneTexture;
    private Texture helpDialogTwoTexture;
    private Texture helpDialogThreeTexture;
    private Texture helpDialogBottomTexture;
    private Texture helpDialogBackBtnTexture;
    private Texture helpDialogForwardBtnTexture;
    private Texture helpDialogCloseBtnTexture;

    // Select Player Textures
    private Texture selectPlayerDialogTexture;
    private Texture playerOneIconUnlockedTexture;
    private Texture playerTwoIconUnlockedTexture;
    private Texture playerThreeIconUnlockedTexture;
    private Texture playerFourIconUnlockedTexture;
    private Texture playerFiveIconUnlockedTexture;
    private Texture playerSixIconUnlockedTexture;
    private Texture playerTwoIconLockedTexture;
    private Texture playerThreeIconLockedTexture;
    private Texture playerFourIconLockedTexture;
    private Texture playerFiveIconLockedTexture;
    private Texture playerSixIconLockedTexture;
    private Texture playerOneSelectedIconTexture;
    private Texture playerTwoSelectedIconTexture;

    //Dialogs
    private Table resetStoryDialogTable;

    public MainMenuScreen(final RPSGame game) {
        this.GAME = game;
        this.hasDisposed = true;
    }

    @Override
    public void show() {
        Gdx.app.log(TAG, "Screen showed");
        this.hasDisposed = false;
        this.hasGameStarted = false;
        this.isDialogVisible = false;
        this.currentHelpPage = 1;
        GAME.playerOneSelection = 0;
        GAME.playerTwoSelection = 0;

        getAssets();

        this.backgroundMusic.setVolume(0.5f);
        this.backgroundMusic.setLooping(true);
        if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("music_on")) {
            this.backgroundMusic.play();
        }
        else {
            this.backgroundMusic.stop();
        }

        setupMainMenuScreen();
    }

    @Override
    public void render(float delta) {
        if (hasGameStarted) {
            GAME.stage.addAction(Actions.sequence(
                    Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            backgroundMusic.stop();
                        }
                    }),
                    Actions.delay(1f),
                    Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            GAME.currentGameScreen = GameSceens.BATTLE_SCREEN;
                            GAME.setScreen(GAME.loadingScreen);
                        }
                    })
            ));
        }
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        if (!this.hasDisposed) {
            this.backgroundMusic.stop();
            this.backgroundMusic.dispose();
            this.clickOneSound.stop();
            this.clickOneSound.dispose();
            this.clickTwoSound.stop();
            this.clickTwoSound.dispose();
            this.clickThreeSound.stop();
            this.clickThreeSound.dispose();
            this.selectionFanfareSound.stop();
            this.selectionFanfareSound.dispose();
            this.resetSound.stop();
            this.resetSound.dispose();

            this.backgroundTexture.dispose();
            this.gameTitleTexture.dispose();
            this.storyBtnNormalTexture.dispose();
            this.storyBtnClickedTexture.dispose();
            this.versusBtnNormalTexture.dispose();
            this.storyBtnClickedTexture.dispose();
            this.quitBtnNormalTexture.dispose();
            this.quitBtnClickedTexture.dispose();
            this.settingsBtnNormalTexture.dispose();
            this.settingsBtnClickedTexture.dispose();
            this.helpBtnNormalTexture.dispose();
            this.helpBtnClickedTexture.dispose();

            this.resetStoryDialogTexture.dispose();
            this.yesBtnTexture.dispose();
            this.noBtnTexture.dispose();

            this.settingsDialogTopTexture.dispose();
            this.settingsDialogBottomTexture.dispose();
            this.settingsDialogCloseBtnTexture.dispose();
            this.settingsToggleOnTexture.dispose();
            this.settingsToggleOffTexture.dispose();

            this.helpDialogOneTexture.dispose();
            this.helpDialogTwoTexture.dispose();
            this.helpDialogThreeTexture.dispose();
            this.helpDialogBottomTexture.dispose();
            this.helpDialogBackBtnTexture.dispose();
            this.helpDialogForwardBtnTexture.dispose();
            this.helpDialogCloseBtnTexture.dispose();

            this.selectPlayerDialogTexture.dispose();
            this.playerOneIconUnlockedTexture.dispose();
            this.playerTwoIconUnlockedTexture.dispose();
            this.playerThreeIconUnlockedTexture.dispose();
            this.playerFourIconUnlockedTexture.dispose();
            this.playerFiveIconUnlockedTexture.dispose();
            this.playerSixIconUnlockedTexture.dispose();
            this.playerTwoIconLockedTexture.dispose();
            this.playerThreeIconLockedTexture.dispose();
            this.playerFourIconLockedTexture.dispose();
            this.playerFiveIconLockedTexture.dispose();
            this.playerSixIconLockedTexture.dispose();
            this.playerOneSelectedIconTexture.dispose();
            this.playerTwoSelectedIconTexture.dispose();

            GAME.assetManager.clear();

            this.hasDisposed = true;

            Gdx.app.log(TAG, "Objects has been disposed!");
        }
    }

    private void getAssets() {
        this.backgroundMusic = GAME.assetManager.get(Globals.MUSIC_PATH + "eric-skiff-chibi-ninja.mp3", Music.class);
        this.clickOneSound = GAME.assetManager.get(Globals.SOUND_PATH + "click1.wav", Sound.class);
        this.clickTwoSound = GAME.assetManager.get(Globals.SOUND_PATH + "click2.wav", Sound.class);
        this.clickThreeSound = GAME.assetManager.get(Globals.SOUND_PATH + "click3.wav", Sound.class);
        this.selectionFanfareSound = GAME.assetManager.get(Globals.SOUND_PATH + "selection_fanfare.wav", Sound.class);
        this.resetSound = GAME.assetManager.get(Globals.SOUND_PATH + "reset.wav", Sound.class);

        // Main Menu sprites
        this.backgroundTexture = GAME.assetManager.get(Globals.MAIN_PATH + "main-menu-background.png", Texture.class);
        this.gameTitleTexture = GAME.assetManager.get(Globals.MAIN_PATH + "game-title.png", Texture.class);
        this.storyBtnNormalTexture = GAME.assetManager.get(Globals.MAIN_PATH + "story-btn-normal.png", Texture.class);
        this.storyBtnClickedTexture = GAME.assetManager.get(Globals.MAIN_PATH + "story-btn-clicked.png", Texture.class);
        this.versusBtnNormalTexture = GAME.assetManager.get(Globals.MAIN_PATH + "versus-btn-normal.png", Texture.class);
        this.versusBtnClickedTexture = GAME.assetManager.get(Globals.MAIN_PATH + "versus-btn-clicked.png", Texture.class);
        this.quitBtnNormalTexture = GAME.assetManager.get(Globals.MAIN_PATH + "quit-btn-normal.png", Texture.class);
        this.quitBtnClickedTexture = GAME.assetManager.get(Globals.MAIN_PATH + "quit-btn-clicked.png", Texture.class);
        this.settingsBtnNormalTexture = GAME.assetManager.get(Globals.MAIN_PATH + "settings-btn-normal.png", Texture.class);
        this.settingsBtnClickedTexture = GAME.assetManager.get(Globals.MAIN_PATH + "settings-btn-clicked.png", Texture.class);
        this.helpBtnNormalTexture = GAME.assetManager.get(Globals.MAIN_PATH + "help-btn-normal.png", Texture.class);
        this.helpBtnClickedTexture = GAME.assetManager.get(Globals.MAIN_PATH + "help-btn-clicked.png", Texture.class);

        this.resetStoryDialogTexture = GAME.assetManager.get(Globals.MAIN_PATH + "reset-story-dialog.png", Texture.class);
        this.yesBtnTexture = GAME.assetManager.get(Globals.MAIN_PATH + "yes-btn.png", Texture.class);
        this.noBtnTexture = GAME.assetManager.get(Globals.MAIN_PATH + "no-btn.png", Texture.class);

        // Settings screen sprites
        this.settingsDialogTopTexture = GAME.assetManager.get(Globals.SETTINGS_PATH + "settings-dialog-top.png", Texture.class);
        this.settingsDialogBottomTexture = GAME.assetManager.get(Globals.SETTINGS_PATH + "settings-dialog-bottom.png", Texture.class);
        this.settingsDialogCloseBtnTexture = GAME.assetManager.get(Globals.SETTINGS_PATH + "settings-dialog-close-btn.png", Texture.class);
        this.settingsToggleOnTexture = GAME.assetManager.get(Globals.SETTINGS_PATH + "toggle-button-on.png", Texture.class);
        this.settingsToggleOffTexture = GAME.assetManager.get(Globals.SETTINGS_PATH + "toggle-button-off.png", Texture.class);

        this.helpDialogOneTexture = GAME.assetManager.get(Globals.HELP_PATH + "help-dialog-1.png", Texture.class);
        this.helpDialogTwoTexture = GAME.assetManager.get(Globals.HELP_PATH + "help-dialog-2.png", Texture.class);
        this.helpDialogThreeTexture = GAME.assetManager.get(Globals.HELP_PATH + "help-dialog-3.png", Texture.class);
        this.helpDialogBottomTexture = GAME.assetManager.get(Globals.HELP_PATH + "help-dialog-bottom.png", Texture.class);
        this.helpDialogBackBtnTexture = GAME.assetManager.get(Globals.HELP_PATH + "help-dialog-back-btn.png", Texture.class);
        this.helpDialogForwardBtnTexture = GAME.assetManager.get(Globals.HELP_PATH + "help-dialog-forward-btn.png", Texture.class);
        this.helpDialogCloseBtnTexture = GAME.assetManager.get(Globals.HELP_PATH + "help-dialog-close-btn.png", Texture.class);

        // Select Player Screen sprites
        this.selectPlayerDialogTexture = GAME.assetManager.get(Globals.SELECT_PATH + "select-person-dialog.png");
        this.playerOneIconUnlockedTexture = GAME.assetManager.get(Globals.SELECT_PATH + "select-person1-unlocked.png");
        this.playerTwoIconUnlockedTexture = GAME.assetManager.get(Globals.SELECT_PATH + "select-person2-unlocked.png");
        this.playerThreeIconUnlockedTexture = GAME.assetManager.get(Globals.SELECT_PATH + "select-person3-unlocked.png");
        this.playerFourIconUnlockedTexture = GAME.assetManager.get(Globals.SELECT_PATH + "select-person4-unlocked.png");
        this.playerFiveIconUnlockedTexture = GAME.assetManager.get(Globals.SELECT_PATH + "select-person5-unlocked.png");
        this.playerSixIconUnlockedTexture = GAME.assetManager.get(Globals.SELECT_PATH + "select-person6-unlocked.png");
        this.playerTwoIconLockedTexture = GAME.assetManager.get(Globals.SELECT_PATH + "select-person2-locked.png");
        this.playerThreeIconLockedTexture = GAME.assetManager.get(Globals.SELECT_PATH + "select-person3-locked.png");
        this.playerFourIconLockedTexture = GAME.assetManager.get(Globals.SELECT_PATH + "select-person4-locked.png");
        this.playerFiveIconLockedTexture = GAME.assetManager.get(Globals.SELECT_PATH + "select-person5-locked.png");
        this.playerSixIconLockedTexture = GAME.assetManager.get(Globals.SELECT_PATH + "select-person6-locked.png");
        this.playerOneSelectedIconTexture = GAME.assetManager.get(Globals.SELECT_PATH + "player-one-selection-icon.png");
        this.playerTwoSelectedIconTexture = GAME.assetManager.get(Globals.SELECT_PATH + "player-two-selection-icon.png");
    }

    private void setupMainMenuScreen() {
        GAME.stage.clear();

        Table backgroundTable = new Table();
        backgroundTable.setFillParent(true);
        backgroundTable.align(Align.bottomLeft);
        backgroundTable.add(new Image(this.backgroundTexture)).width(64).height(64);

        Table mainButtonsTable = new Table();
        mainButtonsTable.setFillParent(true);
        mainButtonsTable.align(Align.top);
        mainButtonsTable.add(new Image(this.gameTitleTexture)).width(52).height(14).padTop(3).row();

        ImageButton.ImageButtonStyle storyButtonStyle = new ImageButton.ImageButtonStyle();
        storyButtonStyle.up = new TextureRegionDrawable(new TextureRegion(this.storyBtnNormalTexture));
        storyButtonStyle.over = new TextureRegionDrawable(new TextureRegion(this.storyBtnClickedTexture));
        storyButtonStyle.down = new TextureRegionDrawable(new TextureRegion(this.storyBtnClickedTexture));
        ImageButton storyButton = new ImageButton(storyButtonStyle);
        storyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isDialogVisible) {
                    Gdx.app.log(TAG, "Story Button Clicked");

                    if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                        clickOneSound.play(1f);
                    }

                    // If last player is not unlocked. Continue with story
                    if (!Gdx.app.getPreferences(Globals.GAME_PREFS_NAME).getBoolean("player_six_unlocked")) {
                        GAME.stage.addAction(Actions.sequence(
                                Actions.delay(0.2f),
                                Actions.run(new Runnable() {
                                    @Override
                                    public void run() {
                                        GAME.currentGameMode = GameModes.STORY_MODE;
                                        GAME.currentGameScreen = GameSceens.BATTLE_SCREEN;
                                        GAME.setScreen(GAME.loadingScreen);
                                    }
                                })
                        ));
                    }
                    // Else ask player to reset game and try story again
                    else {
                        resetStoryDialogTable.setVisible(true);
                        isDialogVisible = true;
                    }
                }
            }
        });

        mainButtonsTable.add(storyButton).width(49).height(11).padTop(6).row();

        ImageButton.ImageButtonStyle versusButtonStyle = new ImageButton.ImageButtonStyle();
        versusButtonStyle.up = new TextureRegionDrawable(new TextureRegion(this.versusBtnNormalTexture));
        versusButtonStyle.over = new TextureRegionDrawable(new TextureRegion(this.versusBtnClickedTexture));
        versusButtonStyle.down = new TextureRegionDrawable(new TextureRegion(this.versusBtnClickedTexture));
        ImageButton versusButton = new ImageButton(versusButtonStyle);
        versusButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isDialogVisible) {
                    Gdx.app.log(TAG, "Versus Button Clicked");
                    if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                        clickOneSound.play(1f);
                    }

                    GAME.currentGameMode = GameModes.VERSUS_MODE;

                    setupPlayerSelectScreen();
                }
            }
        });

        mainButtonsTable.add(versusButton).width(49).height(11).padTop(2).row();

        Table bottomButtonsTable = new Table();
        bottomButtonsTable.setFillParent(true);
        bottomButtonsTable.align(Align.bottomLeft);

        ImageButton.ImageButtonStyle quitButtonStyle = new ImageButton.ImageButtonStyle();
        quitButtonStyle.up = new TextureRegionDrawable(new TextureRegion(this.quitBtnNormalTexture));
        quitButtonStyle.over = new TextureRegionDrawable(new TextureRegion(this.quitBtnClickedTexture));
        quitButtonStyle.down = new TextureRegionDrawable(new TextureRegion(this.quitBtnClickedTexture));
        ImageButton quitButton = new ImageButton(quitButtonStyle);
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isDialogVisible) {
                    Gdx.app.log(TAG, "Quit Button Clicked");
                    if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                        clickOneSound.play(1f);
                    }

                    GAME.stage.addAction(Actions.sequence(
                            Actions.delay(0.2f),
                            Actions.run(new Runnable() {
                                @Override
                                public void run() {
                                    Gdx.app.exit();
                                }
                            })
                    ));
                }
            }
        });

        bottomButtonsTable.add(quitButton).width(11).height(11).padBottom(2).padLeft(2);

        ImageButton.ImageButtonStyle settingsButtonStyle = new ImageButton.ImageButtonStyle();
        settingsButtonStyle.up = new TextureRegionDrawable(new TextureRegion(this.settingsBtnNormalTexture));
        settingsButtonStyle.over = new TextureRegionDrawable(new TextureRegion(this.settingsBtnClickedTexture));
        settingsButtonStyle.down = new TextureRegionDrawable(new TextureRegion(this.settingsBtnClickedTexture));
        ImageButton settingsButton = new ImageButton(settingsButtonStyle);
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isDialogVisible) {
                    Gdx.app.log(TAG, "Settings Button Clicked");
                    if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                        clickOneSound.play(1f);
                    }

                    setupSettingsScreen();
                }
            }
        });

        bottomButtonsTable.add(settingsButton).width(11).height(11).padBottom(2).padLeft(14);

        ImageButton.ImageButtonStyle helpButtonStyle = new ImageButton.ImageButtonStyle();
        helpButtonStyle.up = new TextureRegionDrawable(new TextureRegion(this.helpBtnNormalTexture));
        helpButtonStyle.over = new TextureRegionDrawable(new TextureRegion(this.helpBtnClickedTexture));
        helpButtonStyle.down = new TextureRegionDrawable(new TextureRegion(this.helpBtnClickedTexture));
        ImageButton helpButton = new ImageButton(helpButtonStyle);
        helpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isDialogVisible) {
                    Gdx.app.log(TAG, "Help Button Clicked");
                    if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                        clickOneSound.play(1f);
                    }

                    setupHelpScreen();
                }
            }
        });

        bottomButtonsTable.add(helpButton).width(11).height(11).padBottom(2).padLeft(13);

        // Reset dialog Table and buttons
        ImageButton.ImageButtonStyle yesResetBtnStyle = new ImageButton.ImageButtonStyle();
        yesResetBtnStyle.up = new TextureRegionDrawable(new TextureRegion(this.yesBtnTexture));
        yesResetBtnStyle.down = new TextureRegionDrawable(new TextureRegion(this.yesBtnTexture));
        ImageButton yesResetBtn = new ImageButton(yesResetBtnStyle);
        yesResetBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log(TAG, "Story has been reset");
                if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                    resetSound.play(1f);
                }

                //Reset Game
                GAME.resetGamePreferences();

                resetStoryDialogTable.setVisible(false);
                isDialogVisible = false;
            }
        });

        ImageButton.ImageButtonStyle noResetBtnStyle = new ImageButton.ImageButtonStyle();
        noResetBtnStyle.up = new TextureRegionDrawable(new TextureRegion(this.noBtnTexture));
        noResetBtnStyle.down = new TextureRegionDrawable(new TextureRegion(this.noBtnTexture));
        ImageButton noResetBtn = new ImageButton(noResetBtnStyle);
        noResetBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log(TAG, "Story has not been reset");
                if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                    clickOneSound.play(1f);
                }

                resetStoryDialogTable.setVisible(false);
                isDialogVisible = false;
            }
        });

        this.resetStoryDialogTable = new Table();
        this.resetStoryDialogTable.setFillParent(true);
        this.resetStoryDialogTable.align(Align.top);
        this.resetStoryDialogTable.add(new Image(this.resetStoryDialogTexture)).width(38).height(25).padTop(12).padBottom(1).colspan(2).row();
        this.resetStoryDialogTable.add(yesResetBtn).width(16).height(9).padRight(1);
        this.resetStoryDialogTable.add(noResetBtn).width(16).height(9).padLeft(1);
        this.resetStoryDialogTable.setVisible(false);


        GAME.stage.addActor(backgroundTable);
        GAME.stage.addActor(mainButtonsTable);
        GAME.stage.addActor(bottomButtonsTable);
        GAME.stage.addActor(this.resetStoryDialogTable);
    }

    private void setupSettingsScreen() {
        GAME.stage.clear();

        Table backgroundTable = new Table();
        backgroundTable.setFillParent(true);
        backgroundTable.align(Align.bottomLeft);
        backgroundTable.add(new Image(this.backgroundTexture)).width(64).height(64);

        Table settingsDialogTable = new Table();
        settingsDialogTable.setFillParent(true);
        settingsDialogTable.align(Align.center);
        settingsDialogTable.add(new Image(this.settingsDialogTopTexture)).width(58).height(48).colspan(2).row();
        settingsDialogTable.add(new Image(this.settingsDialogBottomTexture)).width(49).height(9);

        ImageButton.ImageButtonStyle closeButtonStyle = new ImageButton.ImageButtonStyle();
        closeButtonStyle.up = new TextureRegionDrawable(new TextureRegion(this.settingsDialogCloseBtnTexture));
        closeButtonStyle.down = new TextureRegionDrawable(new TextureRegion(this.settingsDialogCloseBtnTexture));
        ImageButton closeButton = new ImageButton(closeButtonStyle);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log(TAG, "Closed settings dialog");
                if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                    clickOneSound.play(1f);
                }

                setupMainMenuScreen();
            }
        });

        settingsDialogTable.add(closeButton).width(9).height(9);

        Table toogleButtonsTable = new Table();
        toogleButtonsTable.setFillParent(true);
        toogleButtonsTable.align(Align.right);

        //Toggle Styles
        final ImageButton.ImageButtonStyle toggleOnButtonStyle = new ImageButton.ImageButtonStyle();
        toggleOnButtonStyle.up = new TextureRegionDrawable(new TextureRegion(this.settingsToggleOnTexture));
        toggleOnButtonStyle.down = new TextureRegionDrawable(new TextureRegion(this.settingsToggleOnTexture));
        final ImageButton.ImageButtonStyle toggleOffButtonStyle = new ImageButton.ImageButtonStyle();
        toggleOffButtonStyle.up = new TextureRegionDrawable(new TextureRegion(this.settingsToggleOffTexture));
        toggleOffButtonStyle.down = new TextureRegionDrawable(new TextureRegion(this.settingsToggleOffTexture));

        final ImageButton toggleSoundButton = new ImageButton(toggleOnButtonStyle);
        if (!Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
            toggleSoundButton.setStyle(toggleOffButtonStyle);
        }
        toggleSoundButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                    clickTwoSound.play(1f);
                }

                if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                    Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).putBoolean("sound_on", false);
                    Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).flush();

                    toggleSoundButton.setStyle(toggleOffButtonStyle);
                }
                else {
                    Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).putBoolean("sound_on", true);
                    Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).flush();

                    clickTwoSound.play(1f);

                    toggleSoundButton.setStyle(toggleOnButtonStyle);
                }

                Gdx.app.log(TAG, "Sound Toggled");
            }
        });

        final ImageButton toggleMusicButton = new ImageButton(toggleOnButtonStyle);
        if (!Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("music_on")) {
            toggleMusicButton.setStyle(toggleOffButtonStyle);
        }
        toggleMusicButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("music_on")) {
                    Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).putBoolean("music_on", false);
                    Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).flush();

                    backgroundMusic.stop();

                    toggleMusicButton.setStyle(toggleOffButtonStyle);
                }
                else {
                    Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).putBoolean("music_on", true);
                    Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).flush();

                    backgroundMusic.play();

                    toggleMusicButton.setStyle(toggleOnButtonStyle);
                }

                if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                    clickTwoSound.play(1f);
                }

                Gdx.app.log(TAG, "Music Toggled");
            }
        });

        toogleButtonsTable.add(toggleSoundButton).width(15).height(7).padBottom(5).padRight(10).row();
        toogleButtonsTable.add(toggleMusicButton).width(15).height(7).padBottom(14).padRight(10);

        GAME.stage.addActor(backgroundTable);
        GAME.stage.addActor(settingsDialogTable);
        GAME.stage.addActor(toogleButtonsTable);
    }

    private void setupPlayerSelectScreen() {
        GAME.stage.clear();

        Table backgroundTable = new Table();
        backgroundTable.setFillParent(true);
        backgroundTable.align(Align.bottomLeft);
        backgroundTable.add(new Image(this.backgroundTexture)).width(64).height(64);

        Table selectPlayerDialogTable = new Table();
        selectPlayerDialogTable.setFillParent(true);
        selectPlayerDialogTable.align(Align.center);
        selectPlayerDialogTable.add(new Image(this.selectPlayerDialogTexture)).width(58).height(57);

        // Selection icon tables
        final Table playerOneSelectionTable = new Table();
        playerOneSelectionTable.setFillParent(true);
        playerOneSelectionTable.align(Align.bottomLeft);
        playerOneSelectionTable.add(new Image(this.playerOneSelectedIconTexture)).width(6).height(8);
        playerOneSelectionTable.setVisible(false);

        final Table playerTwoSelectionTable = new Table();
        playerTwoSelectionTable.setFillParent(true);
        playerTwoSelectionTable.align(Align.bottomLeft);
        playerTwoSelectionTable.add(new Image(this.playerTwoSelectedIconTexture)).width(6).height(8);
        playerTwoSelectionTable.setVisible(false);

        // Player icons table
        Table playerIconsTable = new Table();
        playerIconsTable.setFillParent(true);
        playerIconsTable.align(Align.center);

        // Styles
        ImageButton.ImageButtonStyle player1IconStyle = new ImageButton.ImageButtonStyle();
        player1IconStyle.up = new TextureRegionDrawable(new TextureRegion(this.playerOneIconUnlockedTexture));
        player1IconStyle.down = new TextureRegionDrawable(new TextureRegion(this.playerOneIconUnlockedTexture));

        ImageButton.ImageButtonStyle player2IconStyle = new ImageButton.ImageButtonStyle();
        player2IconStyle.up = new TextureRegionDrawable(new TextureRegion(this.playerTwoIconUnlockedTexture));
        player2IconStyle.down = new TextureRegionDrawable(new TextureRegion(this.playerTwoIconUnlockedTexture));
        player2IconStyle.disabled = new TextureRegionDrawable(new TextureRegion(this.playerTwoIconLockedTexture));

        ImageButton.ImageButtonStyle player3IconStyle = new ImageButton.ImageButtonStyle();
        player3IconStyle.up = new TextureRegionDrawable(new TextureRegion(this.playerThreeIconUnlockedTexture));
        player3IconStyle.down = new TextureRegionDrawable(new TextureRegion(this.playerThreeIconUnlockedTexture));
        player3IconStyle.disabled = new TextureRegionDrawable(new TextureRegion(this.playerThreeIconLockedTexture));

        ImageButton.ImageButtonStyle player4IconStyle = new ImageButton.ImageButtonStyle();
        player4IconStyle.up = new TextureRegionDrawable(new TextureRegion(this.playerFourIconUnlockedTexture));
        player4IconStyle.down = new TextureRegionDrawable(new TextureRegion(this.playerFourIconUnlockedTexture));
        player4IconStyle.disabled = new TextureRegionDrawable(new TextureRegion(this.playerFourIconLockedTexture));

        ImageButton.ImageButtonStyle player5IconStyle = new ImageButton.ImageButtonStyle();
        player5IconStyle.up = new TextureRegionDrawable(new TextureRegion(this.playerFiveIconUnlockedTexture));
        player5IconStyle.down = new TextureRegionDrawable(new TextureRegion(this.playerFiveIconUnlockedTexture));
        player5IconStyle.disabled = new TextureRegionDrawable(new TextureRegion(this.playerFiveIconLockedTexture));

        ImageButton.ImageButtonStyle player6IconStyle = new ImageButton.ImageButtonStyle();
        player6IconStyle.up = new TextureRegionDrawable(new TextureRegion(this.playerSixIconUnlockedTexture));
        player6IconStyle.down = new TextureRegionDrawable(new TextureRegion(this.playerSixIconUnlockedTexture));
        player6IconStyle.disabled = new TextureRegionDrawable(new TextureRegion(this.playerSixIconLockedTexture));

        // Icons buttons
        ImageButton player1Button = new ImageButton(player1IconStyle);
        player1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!hasGameStarted) {
                    if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                        clickTwoSound.play(1f);
                    }

                    if (GAME.playerOneSelection == 0) {
                        playerOneSelectionTable.setPosition(7, 40);
                        playerOneSelectionTable.setVisible(true);
                        GAME.playerOneSelection = 1;
                        Gdx.app.log(TAG, "Player One Selected");
                    } else if (GAME.playerTwoSelection == 0) {
                        playerTwoSelectionTable.setPosition(15, 40);
                        playerTwoSelectionTable.setVisible(true);
                        GAME.playerTwoSelection = 1;
                        Gdx.app.log(TAG, "Player Two Selected");

                        hasGameStarted = true;
                        if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                            selectionFanfareSound.play(1f);
                        }
                    }
                }
            }
        });
        final ImageButton player2Button = new ImageButton(player2IconStyle);
        player2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!player2Button.isDisabled()) {
                    if (!hasGameStarted) {
                        if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                            clickTwoSound.play(1f);
                        }

                        if (GAME.playerOneSelection == 0) {
                            playerOneSelectionTable.setPosition(25, 40);
                            playerOneSelectionTable.setVisible(true);
                            GAME.playerOneSelection = 2;
                            Gdx.app.log(TAG, "Player One Selected");
                        } else if (GAME.playerTwoSelection == 0) {
                            playerTwoSelectionTable.setPosition(33, 40);
                            playerTwoSelectionTable.setVisible(true);
                            GAME.playerTwoSelection = 2;
                            Gdx.app.log(TAG, "Player Two Selected");

                            hasGameStarted = true;
                            if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                                selectionFanfareSound.play(1f);
                            }
                        }
                    }
                }
            }
        });
        final ImageButton player3Button = new ImageButton(player3IconStyle);
        player3Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!player3Button.isDisabled()) {
                    if (!hasGameStarted) {
                        if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                            clickTwoSound.play(1f);
                        }

                        if (GAME.playerOneSelection == 0) {
                            playerOneSelectionTable.setPosition(43, 40);
                            playerOneSelectionTable.setVisible(true);
                            GAME.playerOneSelection = 3;
                            Gdx.app.log(TAG, "Player One Selected");
                        } else if (GAME.playerTwoSelection == 0) {
                            playerTwoSelectionTable.setPosition(51, 40);
                            playerTwoSelectionTable.setVisible(true);
                            GAME.playerTwoSelection = 3;
                            Gdx.app.log(TAG, "Player Two Selected");

                            hasGameStarted = true;
                            if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                                selectionFanfareSound.play(1f);
                            }
                        }
                    }
                }
            }
        });
        final ImageButton player4Button = new ImageButton(player4IconStyle);
        player4Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!player4Button.isDisabled()) {
                    if (!hasGameStarted) {
                        if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                            clickTwoSound.play(1f);
                        }

                        if (GAME.playerOneSelection == 0) {
                            playerOneSelectionTable.setPosition(7, 18);
                            playerOneSelectionTable.setVisible(true);
                            GAME.playerOneSelection = 4;
                            Gdx.app.log(TAG, "Player One Selected");
                        } else if (GAME.playerTwoSelection == 0) {
                            playerTwoSelectionTable.setPosition(15, 18);
                            playerTwoSelectionTable.setVisible(true);
                            GAME.playerTwoSelection = 4;
                            Gdx.app.log(TAG, "Player Two Selected");

                            hasGameStarted = true;
                            if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                                selectionFanfareSound.play(1f);
                            }
                        }
                    }
                }
            }
        });
        final ImageButton player5Button = new ImageButton(player5IconStyle);
        player5Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!player5Button.isDisabled()) {
                    {
                        if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                            clickTwoSound.play(1f);
                        }

                        if (GAME.playerOneSelection == 0) {
                            playerOneSelectionTable.setPosition(25, 18);
                            playerOneSelectionTable.setVisible(true);
                            GAME.playerOneSelection = 5;
                            Gdx.app.log(TAG, "Player One Selected");
                        } else if (GAME.playerTwoSelection == 0) {
                            playerTwoSelectionTable.setPosition(33, 18);
                            playerTwoSelectionTable.setVisible(true);
                            GAME.playerTwoSelection = 5;
                            Gdx.app.log(TAG, "Player Two Selected");

                            hasGameStarted = true;
                            if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                                selectionFanfareSound.play(1f);
                            }
                        }
                    }
                }
            }
        });
        final ImageButton player6Button = new ImageButton(player6IconStyle);
        player6Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!player6Button.isDisabled()) {
                    if (!hasGameStarted) {
                        if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                            clickTwoSound.play(1f);
                        }

                        if (GAME.playerOneSelection == 0) {
                            playerOneSelectionTable.setPosition(43, 18);
                            playerOneSelectionTable.setVisible(true);
                            GAME.playerOneSelection = 6;
                            Gdx.app.log(TAG, "Player One Selected");
                        } else if (GAME.playerTwoSelection == 0) {
                            playerTwoSelectionTable.setPosition(51, 18);
                            playerTwoSelectionTable.setVisible(true);
                            GAME.playerTwoSelection = 6;
                            Gdx.app.log(TAG, "Player Two Selected");

                            hasGameStarted = true;
                            if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                                selectionFanfareSound.play(1f);
                            }
                        }
                    }
                }
            }
        });

        // Disable locked players
        if (!Gdx.app.getPreferences(Globals.GAME_PREFS_NAME).getBoolean("player_two_unlocked")) {
            player2Button.setDisabled(true);
        }
        if (!Gdx.app.getPreferences(Globals.GAME_PREFS_NAME).getBoolean("player_three_unlocked")) {
            player3Button.setDisabled(true);
        }
        if (!Gdx.app.getPreferences(Globals.GAME_PREFS_NAME).getBoolean("player_four_unlocked")) {
            player4Button.setDisabled(true);
        }
        if (!Gdx.app.getPreferences(Globals.GAME_PREFS_NAME).getBoolean("player_five_unlocked")) {
            player5Button.setDisabled(true);
        }
        if (!Gdx.app.getPreferences(Globals.GAME_PREFS_NAME).getBoolean("player_six_unlocked")) {
            player6Button.setDisabled(true);
        }

        playerIconsTable.add(player1Button).width(16).height(20).padRight(2).padBottom(2).padTop(8);
        playerIconsTable.add(player2Button).width(16).height(20).padRight(2).padBottom(2).padTop(8);
        playerIconsTable.add(player3Button).width(16).height(20).padBottom(2).padTop(8).row();
        playerIconsTable.add(player4Button).width(16).height(20).padRight(2);
        playerIconsTable.add(player5Button).width(16).height(20).padRight(2);
        playerIconsTable.add(player6Button).width(16).height(20);

        Table closeButtonTable = new Table();
        closeButtonTable.setFillParent(true);
        closeButtonTable.align(Align.bottomRight);

        ImageButton.ImageButtonStyle closeBtnStyle = new ImageButton.ImageButtonStyle();
        closeBtnStyle.up = new TextureRegionDrawable(new TextureRegion(this.settingsDialogCloseBtnTexture));
        closeBtnStyle.down = new TextureRegionDrawable(new TextureRegion(this.settingsDialogCloseBtnTexture));
        ImageButton closeSelectDialogBtn = new ImageButton(closeBtnStyle);
        closeSelectDialogBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!hasGameStarted) {
                    Gdx.app.log(TAG, "Select Player Screen closed");
                    if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                        clickOneSound.play(1f);
                    }

                    GAME.playerOneSelection = 0;
                    GAME.playerTwoSelection = 0;
                    hasGameStarted = false;

                    setupMainMenuScreen();
                }
            }
        });
        closeButtonTable.add(closeSelectDialogBtn).width(9).height(9).padRight(1).padBottom(1);

        GAME.stage.addActor(backgroundTable);
        GAME.stage.addActor(selectPlayerDialogTable);
        GAME.stage.addActor(playerIconsTable);
        GAME.stage.addActor(closeButtonTable);
        GAME.stage.addActor(playerOneSelectionTable);
        GAME.stage.addActor(playerTwoSelectionTable);
    }

    private void setupHelpScreen() {
        GAME.stage.clear();

        Table backgroundTable = new Table();
        backgroundTable.setFillParent(true);
        backgroundTable.align(Align.bottomLeft);
        backgroundTable.add(new Image(this.backgroundTexture)).width(64).height(68);

        Table helpDialogTable = new Table();
        helpDialogTable.setFillParent(true);
        helpDialogTable.align(Align.center);

        final Image helpDialogOneImage = new Image(this.helpDialogOneTexture);
        final Image helpDialogTwoImage = new Image(this.helpDialogTwoTexture);
        helpDialogTwoImage.setVisible(false);
        final Image helpDialogThreeImage = new Image(this.helpDialogThreeTexture);
        helpDialogThreeImage.setVisible(false);

        Stack helpDialogStack = new Stack();
        helpDialogStack.add(helpDialogOneImage);
        helpDialogStack.add(helpDialogTwoImage);
        helpDialogStack.add(helpDialogThreeImage);
        helpDialogTable.add(helpDialogStack).width(58).height(48).colspan(4).row();

        helpDialogTable.add(new Image(this.helpDialogBottomTexture)).width(35).height(9);

        // Help Button Styles
        ImageButton.ImageButtonStyle backButtonStyle = new ImageButton.ImageButtonStyle();
        backButtonStyle.up = new TextureRegionDrawable(new TextureRegion(this.helpDialogBackBtnTexture));
        backButtonStyle.down = new TextureRegionDrawable(new TextureRegion(this.helpDialogBackBtnTexture));
        ImageButton backButton = new ImageButton(backButtonStyle);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (currentHelpPage == 2) {
                    helpDialogTwoImage.setVisible(false);
                    helpDialogOneImage.setVisible(true);

                    if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                        clickOneSound.play(1f);
                    }

                    currentHelpPage--;
                }
                else if (currentHelpPage == 3) {
                    helpDialogThreeImage.setVisible(false);
                    helpDialogTwoImage.setVisible(true);

                    if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                        clickOneSound.play(1f);
                    }

                    currentHelpPage--;
                }
                else {
                    if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                        clickThreeSound.play(1f);
                    }
                }
            }
        });

        ImageButton.ImageButtonStyle forwardButtonStyle = new ImageButton.ImageButtonStyle();
        forwardButtonStyle.up = new TextureRegionDrawable(new TextureRegion(this.helpDialogForwardBtnTexture));
        forwardButtonStyle.down = new TextureRegionDrawable(new TextureRegion(this.helpDialogForwardBtnTexture));
        ImageButton forwardButton = new ImageButton(forwardButtonStyle);
        forwardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (currentHelpPage == 1) {
                    helpDialogTwoImage.setVisible(true);
                    helpDialogOneImage.setVisible(false);

                    if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                        clickOneSound.play(1f);
                    }

                    currentHelpPage++;
                }
                else if (currentHelpPage == 2) {
                    helpDialogThreeImage.setVisible(true);
                    helpDialogTwoImage.setVisible(false);

                    if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                        clickOneSound.play(1f);
                    }

                    currentHelpPage++;
                }
                else {
                    if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                        clickThreeSound.play(1f);
                    }
                }
            }
        });

        ImageButton.ImageButtonStyle closeButtonStyle = new ImageButton.ImageButtonStyle();
        closeButtonStyle.up = new TextureRegionDrawable(new TextureRegion(this.helpDialogCloseBtnTexture));
        closeButtonStyle.down = new TextureRegionDrawable(new TextureRegion(this.helpDialogCloseBtnTexture));
        ImageButton closeButton = new ImageButton(closeButtonStyle);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                    clickOneSound.play(1f);
                }

                currentHelpPage = 1;

                setupMainMenuScreen();
            }
        });

        helpDialogTable.add(backButton).width(8).height(9);
        helpDialogTable.add(forwardButton).width(7).height(9);
        helpDialogTable.add(closeButton).width(8).height(9);

        GAME.stage.addActor(backgroundTable);
        GAME.stage.addActor(helpDialogTable);
    }
}
