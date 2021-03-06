package com.pixelswithcode.libgdx.rps.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
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

public class BattleScreen extends ScreenAdapter {

    //Type enum
    private enum TYPE {
        NONE,
        ROCK,
        PAPER,
        SCISSORS
    }

    private final String TAG = BattleScreen.class.getSimpleName();

    private final RPSGame GAME;
    private boolean hasDisposed;

    private int currentRound;
    private int currentBoss;

    private int playerOneScore;
    private int playerTwoScore;

    private float selectIconDelay;

    private boolean gameOver;
    private boolean isGameDone;
    private boolean hasBothSelected;
    private boolean isRoundDone;
    private boolean hasNewRoundStarted;
    private TYPE playerOneSelectionType;
    private TYPE playerTwoSelectionType;

    //Sound and music
    private Music battleThemeMusic;
    private Music bossOneMusic;
    private Music bossTwoMusic;
    private Music bossThreeMusic;
    private Music bossFourMusic;
    private Music bossFiveMusic;
    private Sound winSound;
    private Sound loseSound;
    private Sound roundWinSound;
    private Sound giggleSound;
    private Sound drawSound;
    private Sound clickSound;
    private Sound playerIconSelectionSound;

    //Textures
    private Texture battleBackgroundTexture;
    private Texture playerOneTexture;
    private Texture playerTwoTexture;
    private Texture npcTexture;

    private Texture bossOneTexture;
    private Texture bossTwoTexture;
    private Texture bossThreeTexture;
    private Texture bossFourTexture;
    private Texture bossFiveTexture;

    private Texture score00Texture;
    private Texture score10Texture;
    private Texture score01Texture;
    private Texture score11Texture;
    private Texture score12Texture;
    private Texture score21Texture;
    private Texture score02Texture;
    private Texture score20Texture;

    private Texture iconNotReadyTexture;
    private Texture iconReadyTexture;
    private Texture iconPaperTexture;
    private Texture iconRockTexture;
    private Texture iconScissorsTexture;

    private Texture roundOneBoxTexture;
    private Texture roundTwoBoxTexture;
    private Texture roundThreeBoxTexture;
    private Texture roundFourBoxTexture;
    private Texture roundFiveBoxTexture;
    private Texture roundBossBoxTexture;

    private Texture playAgainDialogTexture;
    private Texture exitGameDialogTexture;
    private Texture yesBtnTexture;
    private Texture noBtnTexture;

    private Texture bossCompletedMessageTexture;
    private Texture beatenMessageTexture;

    // Boss Images
    private Image bossOneImage;
    private Image bossTwoImage;
    private Image bossThreeImage;
    private Image bossFourImage;
    private Image bossFiveImage;
    private Image npcImage;

    // Score Images
    private Image score00Image;
    private Image score10Image;
    private Image score01Image;
    private Image score11Image;
    private Image score12Image;
    private Image score21Image;
    private Image score02Image;
    private Image score20Image;

    // Icon Images
    private Image iconNotReadyP1Image;
    private Image iconReadyP1Image;
    private Image iconPaperP1Image;
    private Image iconRockP1Image;
    private Image iconScissorsP1Image;

    private Image iconNotReadyP2Image;
    private Image iconReadyP2Image;
    private Image iconPaperP2Image;
    private Image iconRockP2Image;
    private Image iconScissorsP2Image;

    private Image roundOneBoxImage;
    private Image roundTwoBoxImage;
    private Image roundThreeBoxImage;
    private Image roundFourBoxImage;
    private Image roundFiveBoxImage;
    private Image roundBossBoxImage;

    // Play Again Dialog
    private Table playAgainDialogTable;
    private Table exitGameDialogTable;

    public BattleScreen(final RPSGame game) {
        this.GAME = game;
        this.hasDisposed = true;
    }

    @Override
    public void show() {
        this.hasDisposed = false;
        Gdx.app.log(TAG, "Screen showed");

        if (GAME.currentGameMode == GameModes.STORY_MODE) {
            if (Gdx.app.getPreferences(Globals.GAME_PREFS_NAME).contains("current_round")) {
                this.currentRound = Gdx.app.getPreferences(Globals.GAME_PREFS_NAME).getInteger("current_round");
            }
            else {
                Gdx.app.getPreferences(Globals.GAME_PREFS_NAME).putInteger("current_round", 1);
                Gdx.app.getPreferences(Globals.GAME_PREFS_NAME).flush();
                this.currentRound = Gdx.app.getPreferences(Globals.GAME_PREFS_NAME).getInteger("current_round");
            }
        }
        else {
            this.currentRound = 0;
        }

        this.currentBoss = getCurrentBoss();
        this.selectIconDelay = 1f;
        this.playerOneScore = 0;
        this.playerTwoScore = 0;
        this.gameOver = false;
        this.isGameDone = false;
        this.hasBothSelected = false;
        this.isRoundDone = false;
        this.hasNewRoundStarted = true;
        this.playerOneSelectionType = TYPE.NONE;
        this.playerTwoSelectionType = TYPE.NONE;

        getAssets();

        setupBattleScreen();

        //Start music
        this.battleThemeMusic.setVolume(0.5f);
        this.battleThemeMusic.setLooping(true);

        if (currentRound != 6) {
            if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("music_on")) {
                this.battleThemeMusic.play();
            }
        }
        else {
            battleThemeMusic.stop();
            playBossMusic();
        }
    }

    @Override
    public void render(float delta) {
        if (!gameOver) {
            switch (GAME.currentGameMode) {
                case STORY_MODE:
                    playStoryMode();
                    break;
                case VERSUS_MODE:
                    playVersusMode();
                    break;
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                hasNewRoundStarted = false;
                exitGameDialogTable.setVisible(true);
            }
        }
        else {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY) || Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                GAME.currentGameScreen = GameSceens.MAIN_MENU_SCREEN;
                GAME.setScreen(GAME.loadingScreen);
            }
        }
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        if (!this.hasDisposed) {
            this.battleThemeMusic.stop();
            this.battleThemeMusic.dispose();
            this.bossOneMusic.stop();
            this.bossOneMusic.dispose();
            this.bossTwoMusic.stop();
            this.bossTwoMusic.dispose();
            this.bossThreeMusic.stop();
            this.bossThreeMusic.dispose();
            this.bossFourMusic.stop();
            this.bossFourMusic.dispose();
            this.bossFiveMusic.stop();
            this.bossFiveMusic.dispose();

            this.winSound.stop();
            this.winSound.dispose();
            this.loseSound.stop();
            this.loseSound.dispose();
            this.roundWinSound.stop();
            this.roundWinSound.dispose();
            this.giggleSound.stop();
            this.giggleSound.dispose();
            this.drawSound.stop();
            this.drawSound.dispose();
            this.clickSound.stop();
            this.clickSound.dispose();
            this.playerIconSelectionSound.stop();
            this.playerIconSelectionSound.dispose();

            this.battleBackgroundTexture.dispose();
            this.playerOneTexture.dispose();
            this.playerTwoTexture.dispose();
            this.npcTexture.dispose();

            this.bossOneTexture.dispose();
            this.bossTwoTexture.dispose();
            this.bossThreeTexture.dispose();
            this.bossFourTexture.dispose();
            this.bossFiveTexture.dispose();

            this.score00Texture.dispose();
            this.score01Texture.dispose();
            this.score10Texture.dispose();
            this.score11Texture.dispose();
            this.score12Texture.dispose();
            this.score21Texture.dispose();
            this.score02Texture.dispose();
            this.score20Texture.dispose();

            this.iconNotReadyTexture.dispose();
            this.iconReadyTexture.dispose();
            this.iconPaperTexture.dispose();
            this.iconRockTexture.dispose();
            this.iconScissorsTexture.dispose();

            this.roundOneBoxTexture.dispose();
            this.roundTwoBoxTexture.dispose();
            this.roundThreeBoxTexture.dispose();
            this.roundFourBoxTexture.dispose();
            this.roundFiveBoxTexture.dispose();
            this.roundBossBoxTexture.dispose();

            this.playAgainDialogTexture.dispose();
            this.exitGameDialogTexture.dispose();
            this.yesBtnTexture.dispose();
            this.noBtnTexture.dispose();

            this.bossCompletedMessageTexture.dispose();
            this.beatenMessageTexture.dispose();

            GAME.assetManager.clear();

            this.hasDisposed = true;

            Gdx.app.log(TAG, "Objects has been disposed!");
        }
    }

    private void getAssets() {
        //Music and sounds
        this.battleThemeMusic = GAME.assetManager.get(Globals.MUSIC_PATH + "battle-theme.ogg", Music.class);
        this.bossOneMusic = GAME.assetManager.get(Globals.MUSIC_PATH + "boss_battles/boss1.ogg", Music.class);
        this.bossTwoMusic = GAME.assetManager.get(Globals.MUSIC_PATH + "boss_battles/boss2.ogg", Music.class);
        this.bossThreeMusic = GAME.assetManager.get(Globals.MUSIC_PATH + "boss_battles/boss3.ogg", Music.class);
        this.bossFourMusic = GAME.assetManager.get(Globals.MUSIC_PATH + "boss_battles/boss4.ogg", Music.class);
        this.bossFiveMusic = GAME.assetManager.get(Globals.MUSIC_PATH + "boss_battles/boss5.ogg", Music.class);

        this.winSound = GAME.assetManager.get(Globals.SOUND_PATH + "win-jingle.wav", Sound.class);
        this.loseSound = GAME.assetManager.get(Globals.SOUND_PATH + "lose-jingle.wav", Sound.class);
        this.roundWinSound = GAME.assetManager.get(Globals.SOUND_PATH + "round-win-jingle.wav", Sound.class);
        this.giggleSound = GAME.assetManager.get(Globals.SOUND_PATH + "giggle.wav", Sound.class);
        this.drawSound = GAME.assetManager.get(Globals.SOUND_PATH + "draw.wav", Sound.class);
        this.clickSound = GAME.assetManager.get(Globals.SOUND_PATH + "click1.wav", Sound.class);
        this.playerIconSelectionSound = GAME.assetManager.get(Globals.SOUND_PATH + "player-icon-selection.wav", Sound.class);

        //Sprites
        this.battleBackgroundTexture = GAME.assetManager.get(Globals.BATTLE_PATH + "battle-arena-background.png", Texture.class);

        if (GAME.currentGameMode == GameModes.VERSUS_MODE) {
            this.playerOneTexture = GAME.assetManager.get(Globals.BATTLE_PATH + getPlayerTexturePath(true), Texture.class);
            this.playerTwoTexture = GAME.assetManager.get(Globals.BATTLE_PATH + getPlayerTexturePath(false), Texture.class);
        }
        else {
            this.playerOneTexture = GAME.assetManager.get(Globals.BATTLE_PATH + "person1-left.png", Texture.class);
            this.playerTwoTexture = GAME.assetManager.get(Globals.BATTLE_PATH + "person1-right.png");
        }

        this.bossOneTexture = GAME.assetManager.get(Globals.BATTLE_PATH + "person2-right.png");
        this.bossTwoTexture = GAME.assetManager.get(Globals.BATTLE_PATH + "person3-right.png");
        this.bossThreeTexture = GAME.assetManager.get(Globals.BATTLE_PATH + "person4-right.png");
        this.bossFourTexture = GAME.assetManager.get(Globals.BATTLE_PATH + "person5-right.png");
        this.bossFiveTexture = GAME.assetManager.get(Globals.BATTLE_PATH + "person6-right.png");

        this.npcTexture = GAME.assetManager.get(Globals.BATTLE_PATH + "story-npc.png", Texture.class);

        this.score00Texture = GAME.assetManager.get(Globals.BATTLE_PATH + "score-00.png", Texture.class);
        this.score01Texture = GAME.assetManager.get(Globals.BATTLE_PATH + "score-01.png", Texture.class);
        this.score10Texture = GAME.assetManager.get(Globals.BATTLE_PATH + "score-10.png", Texture.class);
        this.score11Texture = GAME.assetManager.get(Globals.BATTLE_PATH + "score-11.png", Texture.class);
        this.score12Texture = GAME.assetManager.get(Globals.BATTLE_PATH + "score-12.png", Texture.class);
        this.score21Texture = GAME.assetManager.get(Globals.BATTLE_PATH + "score-21.png", Texture.class);
        this.score02Texture = GAME.assetManager.get(Globals.BATTLE_PATH + "score-02.png", Texture.class);
        this.score20Texture = GAME.assetManager.get(Globals.BATTLE_PATH + "score-20.png", Texture.class);

        this.iconNotReadyTexture = GAME.assetManager.get(Globals.BATTLE_PATH + "icon-not-ready.png", Texture.class);
        this.iconReadyTexture = GAME.assetManager.get(Globals.BATTLE_PATH + "icon-ready.png", Texture.class);
        this.iconPaperTexture = GAME.assetManager.get(Globals.BATTLE_PATH + "icon-paper.png", Texture.class);
        this.iconRockTexture = GAME.assetManager.get(Globals.BATTLE_PATH + "icon-rock.png", Texture.class);
        this.iconScissorsTexture = GAME.assetManager.get(Globals.BATTLE_PATH + "icon-scissors.png", Texture.class);

        this.roundOneBoxTexture = GAME.assetManager.get(Globals.BATTLE_PATH + "round-box-1.png", Texture.class);
        this.roundTwoBoxTexture = GAME.assetManager.get(Globals.BATTLE_PATH + "round-box-2.png", Texture.class);
        this.roundThreeBoxTexture = GAME.assetManager.get(Globals.BATTLE_PATH + "round-box-3.png", Texture.class);
        this.roundFourBoxTexture = GAME.assetManager.get(Globals.BATTLE_PATH + "round-box-4.png", Texture.class);
        this.roundFiveBoxTexture = GAME.assetManager.get(Globals.BATTLE_PATH + "round-box-5.png", Texture.class);
        this.roundBossBoxTexture = GAME.assetManager.get(Globals.BATTLE_PATH + "round-box-boss.png", Texture.class);

        this.playAgainDialogTexture = GAME.assetManager.get(Globals.BATTLE_PATH + "play-again-dialog.png", Texture.class);
        this.exitGameDialogTexture = GAME.assetManager.get(Globals.BATTLE_PATH + "exit-game-dialog.png", Texture.class);
        this.yesBtnTexture = GAME.assetManager.get(Globals.BATTLE_PATH + "yes-btn.png", Texture.class);
        this.noBtnTexture = GAME.assetManager.get(Globals.BATTLE_PATH + "no-btn.png", Texture.class);

        this.bossCompletedMessageTexture = GAME.assetManager.get(Globals.BATTLE_PATH + "boss-completed-message.png", Texture.class);
        this.beatenMessageTexture = GAME.assetManager.get(Globals.BATTLE_PATH + "boss-not-completed-message.png", Texture.class);
    }

    private String getPlayerTexturePath(boolean isPlayerOne) {
        String playerTexturePath = "";

        if (isPlayerOne) {
            switch (GAME.playerOneSelection) {
                case 1:
                    playerTexturePath = "person1-left.png";
                    break;
                case 2:
                    playerTexturePath = "person2-left.png";
                    break;
                case 3:
                    playerTexturePath = "person3-left.png";
                    break;
                case 4:
                    playerTexturePath = "person4-left.png";
                    break;
                case 5:
                    playerTexturePath = "person5-left.png";
                    break;
                case 6:
                    playerTexturePath = "person6-left.png";
                    break;
            }
        } else {
            switch (GAME.playerTwoSelection) {
                case 1:
                    playerTexturePath = "person1-right.png";
                    break;
                case 2:
                    playerTexturePath = "person2-right.png";
                    break;
                case 3:
                    playerTexturePath = "person3-right.png";
                    break;
                case 4:
                    playerTexturePath = "person4-right.png";
                    break;
                case 5:
                    playerTexturePath = "person5-right.png";
                    break;
                case 6:
                    playerTexturePath = "person6-right.png";
                    break;
            }
        }

        return playerTexturePath;
    }

    private void setupBattleScreen() {
        GAME.stage.clear();

        Table backgroundTable = new Table();
        backgroundTable.setFillParent(true);
        backgroundTable.align(Align.bottomLeft);
        backgroundTable.add(new Image(this.battleBackgroundTexture)).width(64).height(64);

        Table scoreTable = new Table();
        scoreTable.setFillParent(true);
        scoreTable.align(Align.bottom);

        this.score00Image = new Image(this.score00Texture);
        this.score01Image = new Image(this.score01Texture);
        this.score10Image = new Image(this.score10Texture);
        this.score11Image = new Image(this.score11Texture);
        this.score12Image = new Image(this.score12Texture);
        this.score21Image = new Image(this.score21Texture);
        this.score02Image = new Image(this.score02Texture);
        this.score20Image = new Image(this.score20Texture);
        showScoreImage();

        Stack scoreStack = new Stack();
        scoreStack.add(score00Image);
        scoreStack.add(score01Image);
        scoreStack.add(score10Image);
        scoreStack.add(score11Image);
        scoreStack.add(score12Image);
        scoreStack.add(score21Image);
        scoreStack.add(score02Image);
        scoreStack.add(score20Image);

        scoreTable.add(scoreStack).width(64).height(9);

        Table personsTable = new Table();
        personsTable.setFillParent(true);
        personsTable.align(Align.bottom);
        personsTable.add(new Image(this.playerOneTexture)).width(this.playerOneTexture.getWidth()).height(this.playerOneTexture.getHeight()).padBottom(16).padRight(28);

        if (GAME.currentGameMode == GameModes.VERSUS_MODE) {
            personsTable.add(new Image(this.playerTwoTexture)).width(this.playerTwoTexture.getWidth()).height(this.playerTwoTexture.getHeight()).padBottom(16);
        }
        else {
            this.npcImage = new Image(this.npcTexture);
            this.bossOneImage = new Image(this.bossOneTexture);
            this.bossTwoImage = new Image(this.bossTwoTexture);
            this.bossThreeImage = new Image(this.bossThreeTexture);
            this.bossFourImage = new Image(this.bossFourTexture);
            this.bossFiveImage = new Image(this.bossFiveTexture);

            showBossImage();

            Stack personsStack = new Stack();
            personsStack.add(this.npcImage);
            personsStack.add(this.bossOneImage);
            personsStack.add(this.bossTwoImage);
            personsStack.add(this.bossThreeImage);
            personsStack.add(this.bossFourImage);
            personsStack.add(this.bossFiveImage);
            personsTable.add(personsStack).width(5).height(15).padBottom(16);
        }

        Table iconsPlayerOneTable = new Table();
        iconsPlayerOneTable.setFillParent(true);
        iconsPlayerOneTable.align(Align.bottom);

        this.iconNotReadyP1Image = new Image(this.iconNotReadyTexture);
        this.iconReadyP1Image = new Image(this.iconReadyTexture);
        this.iconReadyP1Image.setVisible(false);
        this.iconPaperP1Image = new Image(this.iconPaperTexture);
        this.iconPaperP1Image.setVisible(false);
        this.iconRockP1Image = new Image(this.iconRockTexture);
        this.iconRockP1Image.setVisible(false);
        this.iconScissorsP1Image = new Image(this.iconScissorsTexture);
        this.iconScissorsP1Image.setVisible(false);

        Stack iconsP1Stack = new Stack();
        iconsP1Stack.add(iconNotReadyP1Image);
        iconsP1Stack.add(iconReadyP1Image);
        iconsP1Stack.add(iconPaperP1Image);
        iconsP1Stack.add(iconRockP1Image);
        iconsP1Stack.add(iconScissorsP1Image);

        iconsPlayerOneTable.add(iconsP1Stack).width(11).height(10).padBottom(36).padRight(32);

        //Player 2 icons table
        Table iconsPlayerTwoTable = new Table();
        iconsPlayerTwoTable.setFillParent(true);
        iconsPlayerTwoTable.align(Align.bottom);

        this.iconNotReadyP2Image = new Image(this.iconNotReadyTexture);
        this.iconReadyP2Image = new Image(this.iconReadyTexture);
        this.iconReadyP2Image.setVisible(false);
        this.iconPaperP2Image = new Image(this.iconPaperTexture);
        this.iconPaperP2Image.setVisible(false);
        this.iconRockP2Image = new Image(this.iconRockTexture);
        this.iconRockP2Image.setVisible(false);
        this.iconScissorsP2Image = new Image(this.iconScissorsTexture);
        this.iconScissorsP2Image.setVisible(false);

        Stack iconsP2Stack = new Stack();
        iconsP2Stack.add(iconNotReadyP2Image);
        iconsP2Stack.add(iconReadyP2Image);
        iconsP2Stack.add(iconPaperP2Image);
        iconsP2Stack.add(iconRockP2Image);
        iconsP2Stack.add(iconScissorsP2Image);

        iconsPlayerTwoTable.add(iconsP2Stack).width(11).height(10).padBottom(36).padLeft(31);

        //Round Boxes
        this.roundOneBoxImage = new Image(this.roundOneBoxTexture);
        this.roundTwoBoxImage = new Image(this.roundTwoBoxTexture);
        this.roundThreeBoxImage = new Image(this.roundThreeBoxTexture);
        this.roundFourBoxImage = new Image(this.roundFourBoxTexture);
        this.roundFiveBoxImage = new Image(this.roundFiveBoxTexture);
        this.roundBossBoxImage = new Image(this.roundBossBoxTexture);

        Stack roundBoxStack = new Stack();
        roundBoxStack.add(this.roundOneBoxImage);
        roundBoxStack.add(this.roundTwoBoxImage);
        roundBoxStack.add(this.roundThreeBoxImage);
        roundBoxStack.add(this.roundFourBoxImage);
        roundBoxStack.add(this.roundFiveBoxImage);
        roundBoxStack.add(this.roundBossBoxImage);

        //Set the appropiate round box image visible.
        showRoundImage();

        Table roundBoxTable = new Table();
        roundBoxTable.setFillParent(true);
        roundBoxTable.align(Align.top);
        roundBoxTable.add(roundBoxStack).width(39).height(11).padTop(5);

        //Play Again Dialog Table
        this.playAgainDialogTable = new Table();
        this.playAgainDialogTable.setFillParent(true);
        this.playAgainDialogTable.align(Align.top);
        this.playAgainDialogTable.add(new Image(this.playAgainDialogTexture)).width(38).height(25).padBottom(1).padTop(10).colspan(2).row();

        ImageButton.ImageButtonStyle yesBtnStyle = new ImageButton.ImageButtonStyle();
        yesBtnStyle.up = new TextureRegionDrawable(new TextureRegion(this.yesBtnTexture));
        yesBtnStyle.down = new TextureRegionDrawable(new TextureRegion(this.yesBtnTexture));
        ImageButton playAgainYesBtn = new ImageButton(yesBtnStyle);
        playAgainYesBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log(TAG, "Yes, we want to play again!");
                if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                    clickSound.play(1f);
                }

                isGameDone = false;
                isRoundDone = false;
                hasNewRoundStarted = true;
                playerOneScore = 0;
                playerTwoScore = 0;
                playAgainDialogTable.setVisible(false);
                setupBattleScreen();
            }
        });

        ImageButton.ImageButtonStyle noBtnStyle = new ImageButton.ImageButtonStyle();
        noBtnStyle.up = new TextureRegionDrawable(new TextureRegion(this.noBtnTexture));
        noBtnStyle.down = new TextureRegionDrawable(new TextureRegion(this.noBtnTexture));
        ImageButton playAgainNoBtn = new ImageButton(noBtnStyle);
        playAgainNoBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                    clickSound.play(1f);
                }

                GAME.stage.addAction(Actions.sequence(
                        Actions.delay(0.2f),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                GAME.currentGameScreen = GameSceens.MAIN_MENU_SCREEN;
                                GAME.setScreen(GAME.loadingScreen);
                            }
                        })
                ));
            }
        });

        this.playAgainDialogTable.add(playAgainYesBtn).width(16).height(9).padRight(2);
        this.playAgainDialogTable.add(playAgainNoBtn).width(16).height(9);
        this.playAgainDialogTable.setVisible(false);

        //Exit game Dialog Table
        this.exitGameDialogTable = new Table();
        this.exitGameDialogTable.setFillParent(true);
        this.exitGameDialogTable.align(Align.top);
        this.exitGameDialogTable.add(new Image(this.exitGameDialogTexture)).width(38).height(25).padBottom(1).padTop(10).colspan(2).row();

        ImageButton exitGameYesBtn = new ImageButton(yesBtnStyle);
        exitGameYesBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log(TAG, "Game exited!");
                if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                    clickSound.play(1f);
                }

                GAME.stage.addAction(Actions.sequence(
                        Actions.delay(0.2f),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                GAME.currentGameScreen = GameSceens.MAIN_MENU_SCREEN;
                                GAME.setScreen(GAME.loadingScreen);
                            }
                        })
                ));
            }
        });

        ImageButton exitGameNoBtn = new ImageButton(noBtnStyle);
        exitGameNoBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                    clickSound.play(1f);
                }

                hasNewRoundStarted = true;
                exitGameDialogTable.setVisible(false);
            }
        });

        this.exitGameDialogTable.add(exitGameYesBtn).width(16).height(9).padRight(2);
        this.exitGameDialogTable.add(exitGameNoBtn).width(16).height(9);
        this.exitGameDialogTable.setVisible(false);

        GAME.stage.addActor(backgroundTable);
        GAME.stage.addActor(personsTable);
        GAME.stage.addActor(iconsPlayerOneTable);
        GAME.stage.addActor(iconsPlayerTwoTable);
        GAME.stage.addActor(scoreTable);
        GAME.stage.addActor(roundBoxTable);
        GAME.stage.addActor(this.playAgainDialogTable);
        GAME.stage.addActor(this.exitGameDialogTable);
    }

    private void showBossImage() {
        this.npcImage.setVisible(false);
        this.bossOneImage.setVisible(false);
        this.bossTwoImage.setVisible(false);
        this.bossThreeImage.setVisible(false);
        this.bossFourImage.setVisible(false);
        this.bossFiveImage.setVisible(false);

        if (currentRound < 6) {
            this.npcImage.setVisible(true);
        }
        else {
            if (Gdx.app.getPreferences(Globals.GAME_PREFS_NAME).getBoolean("player_five_unlocked")) {
                this.bossFiveImage.setVisible(true);
            }
            else if (Gdx.app.getPreferences(Globals.GAME_PREFS_NAME).getBoolean("player_four_unlocked")) {
                this.bossFourImage.setVisible(true);
            }
            else if (Gdx.app.getPreferences(Globals.GAME_PREFS_NAME).getBoolean("player_three_unlocked")) {
                this.bossThreeImage.setVisible(true);
            }
            else if (Gdx.app.getPreferences(Globals.GAME_PREFS_NAME).getBoolean("player_two_unlocked")) {
                this.bossTwoImage.setVisible(true);
            }
            else {
                this.bossOneImage.setVisible(true);
            }
        }
    }

    private void showRoundImage() {
        this.roundOneBoxImage.setVisible(false);
        this.roundTwoBoxImage.setVisible(false);
        this.roundThreeBoxImage.setVisible(false);
        this.roundFourBoxImage.setVisible(false);
        this.roundFiveBoxImage.setVisible(false);
        this.roundBossBoxImage.setVisible(false);

        switch (currentRound) {
            case 1:
                this.roundOneBoxImage.setVisible(true);
                break;
            case 2:
                this.roundTwoBoxImage.setVisible(true);
                break;
            case 3:
                this.roundThreeBoxImage.setVisible(true);
                break;
            case 4:
                this.roundFourBoxImage.setVisible(true);
                break;
            case 5:
                this.roundFiveBoxImage.setVisible(true);
                break;
            case 6:
                this.roundBossBoxImage.setVisible(true);
                break;
        }
    }

    private void showScoreImage() {
        this.score00Image.setVisible(false);
        this.score10Image.setVisible(false);
        this.score01Image.setVisible(false);
        this.score11Image.setVisible(false);
        this.score21Image.setVisible(false);
        this.score12Image.setVisible(false);
        this.score02Image.setVisible(false);
        this.score20Image.setVisible(false);

        if (playerOneScore == 0 && playerTwoScore == 0) {
            this.score00Image.setVisible(true);
        } else if (playerOneScore == 1 && playerTwoScore == 0) {
            this.score10Image.setVisible(true);
        } else if (playerOneScore == 0 && playerTwoScore == 1) {
            this.score01Image.setVisible(true);
        } else if (playerOneScore == 1 && playerTwoScore == 1) {
            this.score11Image.setVisible(true);
        } else if (playerOneScore == 2 && playerTwoScore == 1) {
            this.score21Image.setVisible(true);
        } else if (playerOneScore == 1 && playerTwoScore == 2) {
            this.score12Image.setVisible(true);
        } else if (playerOneScore == 0 && playerTwoScore == 2) {
            this.score02Image.setVisible(true);
        } else if (playerOneScore == 2 && playerTwoScore == 0) {
            this.score20Image.setVisible(true);
        }
    }

    // 1 = Player One won the round
    // 2 = Player Two won the round
    // 3 = The round was a draw
    private int whoWonTheRound() {
        int roundWonBy = 3;

        if (this.playerOneSelectionType == TYPE.ROCK) {
            if (this.playerTwoSelectionType == TYPE.PAPER) {
                roundWonBy = 2;
            } else if (this.playerTwoSelectionType == TYPE.SCISSORS) {
                roundWonBy = 1;
            }
        } else if (this.playerOneSelectionType == TYPE.PAPER) {
            if (this.playerTwoSelectionType == TYPE.SCISSORS) {
                roundWonBy = 2;
            } else if (this.playerTwoSelectionType == TYPE.ROCK) {
                roundWonBy = 1;
            }
        } else if (this.playerOneSelectionType == TYPE.SCISSORS) {
            if (this.playerTwoSelectionType == TYPE.ROCK) {
                roundWonBy = 2;
            } else if (this.playerTwoSelectionType == TYPE.PAPER) {
                roundWonBy = 1;
            }
        }

        return roundWonBy;
    }

    private void playVersusMode() {
        if (this.playerOneScore == 2 || this.playerTwoScore == 2) {
            isGameDone = true;
        }

        if (!isGameDone) {
            if (hasNewRoundStarted) {
                if (!isRoundDone) {
                    if (this.playerOneSelectionType == TYPE.NONE) {
                        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
                            this.playerOneSelectionType = TYPE.ROCK;
                            if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                                playerIconSelectionSound.play(0.5f);
                            }
                        } else if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
                            this.playerOneSelectionType = TYPE.PAPER;
                            if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                                playerIconSelectionSound.play(0.5f);
                            }
                        } else if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
                            this.playerOneSelectionType = TYPE.SCISSORS;
                            if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                                playerIconSelectionSound.play(0.5f);
                            }
                        }
                    }
                    if (this.playerTwoSelectionType == TYPE.NONE) {
                        if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
                            this.playerTwoSelectionType = TYPE.ROCK;
                            if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                                playerIconSelectionSound.play(0.5f);
                            }
                        } else if (Gdx.input.isKeyJustPressed(Input.Keys.N)) {
                            this.playerTwoSelectionType = TYPE.PAPER;
                            if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                                playerIconSelectionSound.play(0.5f);
                            }
                        } else if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
                            this.playerTwoSelectionType = TYPE.SCISSORS;
                            if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                                playerIconSelectionSound.play(0.5f);
                            }
                        }
                    }

                    if (hasBothSelected) {
                        switch (this.playerOneSelectionType) {
                            case ROCK:
                                GAME.stage.addAction(Actions.sequence(
                                        Actions.delay(this.selectIconDelay),
                                        Actions.run(new Runnable() {
                                            @Override
                                            public void run() {
                                                iconRockP1Image.setVisible(true);
                                                isRoundDone = true;
                                            }
                                        })
                                ));
                                break;
                            case PAPER:
                                GAME.stage.addAction(Actions.sequence(
                                        Actions.delay(this.selectIconDelay),
                                        Actions.run(new Runnable() {
                                            @Override
                                            public void run() {
                                                iconPaperP1Image.setVisible(true);
                                                isRoundDone = true;
                                            }
                                        })
                                ));
                                break;
                            case SCISSORS:
                                GAME.stage.addAction(Actions.sequence(
                                        Actions.delay(this.selectIconDelay),
                                        Actions.run(new Runnable() {
                                            @Override
                                            public void run() {
                                                iconScissorsP1Image.setVisible(true);
                                                isRoundDone = true;
                                            }
                                        })
                                ));
                                break;
                        }
                        switch (this.playerTwoSelectionType) {
                            case ROCK:
                                GAME.stage.addAction(Actions.sequence(
                                        Actions.delay(this.selectIconDelay),
                                        Actions.run(new Runnable() {
                                            @Override
                                            public void run() {
                                                iconRockP2Image.setVisible(true);
                                                isRoundDone = true;
                                            }
                                        })
                                ));
                                break;
                            case PAPER:
                                GAME.stage.addAction(Actions.sequence(
                                        Actions.delay(this.selectIconDelay),
                                        Actions.run(new Runnable() {
                                            @Override
                                            public void run() {
                                                iconPaperP2Image.setVisible(true);
                                                isRoundDone = true;
                                            }
                                        })
                                ));
                                break;
                            case SCISSORS:
                                GAME.stage.addAction(Actions.sequence(
                                        Actions.delay(this.selectIconDelay),
                                        Actions.run(new Runnable() {
                                            @Override
                                            public void run() {
                                                iconScissorsP2Image.setVisible(true);
                                                isRoundDone = true;
                                            }
                                        })
                                ));
                                break;
                        }
                    }
                    //If both has not selected
                    else {
                        if (this.playerOneSelectionType != TYPE.NONE) {
                            this.iconReadyP1Image.setVisible(true);
                        }
                        if (this.playerTwoSelectionType != TYPE.NONE) {
                            this.iconReadyP2Image.setVisible(true);
                        }
                        if (this.playerOneSelectionType != TYPE.NONE && this.playerTwoSelectionType != TYPE.NONE) {
                            hasBothSelected = true;
                        }
                    }
                } else {
                    isRoundDone = false;

                    GAME.stage.addAction(Actions.sequence(
                            Actions.delay(this.selectIconDelay + 0.5f),
                            Actions.run(new Runnable() {
                                @Override
                                public void run() {
                                    //Player one won the round
                                    if (whoWonTheRound() == 1) {
                                        playerOneScore++;
                                        if (playerOneScore < 2) {
                                            if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                                                giggleSound.play(1f);
                                            }
                                        }
                                    }
                                    //Player Two won the round
                                    else if (whoWonTheRound() == 2) {
                                        playerTwoScore++;
                                        if (playerTwoScore < 2) {
                                            if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                                                giggleSound.play(1f);
                                            }
                                        }
                                    }
                                    //It was a draw
                                    else {
                                        if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                                            drawSound.play(1f);
                                        }
                                    }

                                    playerOneSelectionType = TYPE.NONE;
                                    playerTwoSelectionType = TYPE.NONE;
                                    hasBothSelected = false;
                                    Gdx.app.log(TAG, "Score P1: " + playerOneScore);
                                    Gdx.app.log(TAG, "Score P2: " + playerTwoScore);
                                    setupBattleScreen();
                                }
                            })
                    ));
                }
            }
        } else {
            // Game is done
            if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                winSound.play(1f);
            }

            playerOneScore = 0;
            playerTwoScore = 0;
            isGameDone = false;
            hasNewRoundStarted = false;

            GAME.stage.addAction(Actions.sequence(
                    Actions.delay(2f),
                    Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            iconNotReadyP1Image.setVisible(false);
                            iconReadyP1Image.setVisible(false);
                            iconRockP1Image.setVisible(false);
                            iconPaperP1Image.setVisible(false);
                            iconScissorsP1Image.setVisible(false);

                            iconNotReadyP2Image.setVisible(false);
                            iconReadyP2Image.setVisible(false);
                            iconRockP2Image.setVisible(false);
                            iconPaperP2Image.setVisible(false);
                            iconScissorsP2Image.setVisible(false);

                            playAgainDialogTable.setVisible(true);
                        }
                    })
            ));
        }
    }

    private void playStoryMode() {
        if (this.playerOneScore == 2 || this.playerTwoScore == 2) {
            isGameDone = true;
        }

        if (!isGameDone) {
            if (hasNewRoundStarted) {
                if (!isRoundDone) {
                    if (this.playerOneSelectionType == TYPE.NONE) {
                        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
                            this.playerOneSelectionType = TYPE.ROCK;
                            if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                                playerIconSelectionSound.play(0.5f);
                            }
                        } else if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
                            this.playerOneSelectionType = TYPE.PAPER;
                            if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                                playerIconSelectionSound.play(0.5f);
                            }
                        } else if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
                            this.playerOneSelectionType = TYPE.SCISSORS;
                            if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                                playerIconSelectionSound.play(0.5f);
                            }
                        }
                    }
                    if (this.playerTwoSelectionType == TYPE.NONE) {
                        int selection = MathUtils.random(0, 3);

                        if (selection == 0) {
                            playerTwoSelectionType = TYPE.ROCK;
                        } else if (selection == 1) {
                            playerTwoSelectionType = TYPE.PAPER;
                        } else if (selection == 2) {
                            playerTwoSelectionType = TYPE.SCISSORS;
                        }
                    }

                    if (hasBothSelected) {
                        switch (this.playerOneSelectionType) {
                            case ROCK:
                                GAME.stage.addAction(Actions.sequence(
                                        Actions.delay(this.selectIconDelay),
                                        Actions.run(new Runnable() {
                                            @Override
                                            public void run() {
                                                iconRockP1Image.setVisible(true);
                                                isRoundDone = true;
                                            }
                                        })
                                ));
                                break;
                            case PAPER:
                                GAME.stage.addAction(Actions.sequence(
                                        Actions.delay(this.selectIconDelay),
                                        Actions.run(new Runnable() {
                                            @Override
                                            public void run() {
                                                iconPaperP1Image.setVisible(true);
                                                isRoundDone = true;
                                            }
                                        })
                                ));
                                break;
                            case SCISSORS:
                                GAME.stage.addAction(Actions.sequence(
                                        Actions.delay(this.selectIconDelay),
                                        Actions.run(new Runnable() {
                                            @Override
                                            public void run() {
                                                iconScissorsP1Image.setVisible(true);
                                                isRoundDone = true;
                                            }
                                        })
                                ));
                                break;
                        }
                        switch (this.playerTwoSelectionType) {
                            case ROCK:
                                GAME.stage.addAction(Actions.sequence(
                                        Actions.delay(this.selectIconDelay),
                                        Actions.run(new Runnable() {
                                            @Override
                                            public void run() {
                                                iconRockP2Image.setVisible(true);
                                                isRoundDone = true;
                                            }
                                        })
                                ));
                                break;
                            case PAPER:
                                GAME.stage.addAction(Actions.sequence(
                                        Actions.delay(this.selectIconDelay),
                                        Actions.run(new Runnable() {
                                            @Override
                                            public void run() {
                                                iconPaperP2Image.setVisible(true);
                                                isRoundDone = true;
                                            }
                                        })
                                ));
                                break;
                            case SCISSORS:
                                GAME.stage.addAction(Actions.sequence(
                                        Actions.delay(this.selectIconDelay),
                                        Actions.run(new Runnable() {
                                            @Override
                                            public void run() {
                                                iconScissorsP2Image.setVisible(true);
                                                isRoundDone = true;
                                            }
                                        })
                                ));
                                break;
                        }
                    }
                    //If both has not selected
                    else {
                        if (this.playerOneSelectionType != TYPE.NONE) {
                            this.iconReadyP1Image.setVisible(true);
                        }
                        if (this.playerTwoSelectionType != TYPE.NONE) {
                            this.iconReadyP2Image.setVisible(true);
                        }
                        if (this.playerOneSelectionType != TYPE.NONE && this.playerTwoSelectionType != TYPE.NONE) {
                            hasBothSelected = true;
                        }
                    }
                } else {
                    //Round is done bracket
                    isRoundDone = false;

                    GAME.stage.addAction(Actions.sequence(
                            Actions.delay(this.selectIconDelay + 0.5f),
                            Actions.run(new Runnable() {
                                @Override
                                public void run() {
                                    //Player one won the round
                                    if (whoWonTheRound() == 1) {
                                        playerOneScore++;
                                        if (playerOneScore < 2) {
                                            if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                                                giggleSound.play(1f);
                                            }
                                        }
                                    }
                                    //Player Two won the round
                                    else if (whoWonTheRound() == 2) {
                                        playerTwoScore++;
                                        if (playerTwoScore < 2) {
                                            if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                                                giggleSound.play(1f);
                                            }
                                        }
                                    }
                                    //It was a draw
                                    else {
                                        if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                                            drawSound.play(1f);
                                        }
                                    }

                                    playerOneSelectionType = TYPE.NONE;
                                    playerTwoSelectionType = TYPE.NONE;
                                    hasBothSelected = false;
                                    //hasNewRoundStarted = false;
                                    Gdx.app.log(TAG, "Score P1: " + playerOneScore);
                                    Gdx.app.log(TAG, "Score P2: " + playerTwoScore);
                                    setupBattleScreen();
                                }
                            })
                    ));
                }
            }
        } else {
            currentRound++;

            Gdx.app.log(TAG, "Current Round is: " + currentRound);

            // Game is done
            if (playerTwoScore == 2) {
                battleThemeMusic.stop();
                stopBossMusic();

                if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                    loseSound.play(1f);
                }

                gameOver = true;

                setupBossNotCompletedScreen();
            }
            else if (playerOneScore == 2 && currentRound > 6)
            {
                //battleThemeMusic.stop();
                stopBossMusic();

                if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                    winSound.play(1f);
                }

                switch (currentBoss) {
                    case 1:
                        currentBoss++;
                        Gdx.app.getPreferences(Globals.GAME_PREFS_NAME).putBoolean("player_two_unlocked", true);
                        Gdx.app.getPreferences(Globals.GAME_PREFS_NAME).flush();
                        break;
                    case 2:
                        currentBoss++;
                        Gdx.app.getPreferences(Globals.GAME_PREFS_NAME).putBoolean("player_three_unlocked", true);
                        Gdx.app.getPreferences(Globals.GAME_PREFS_NAME).flush();
                        break;
                    case 3:
                        currentBoss++;
                        Gdx.app.getPreferences(Globals.GAME_PREFS_NAME).putBoolean("player_four_unlocked", true);
                        Gdx.app.getPreferences(Globals.GAME_PREFS_NAME).flush();
                        break;
                    case 4:
                        currentBoss++;
                        Gdx.app.getPreferences(Globals.GAME_PREFS_NAME).putBoolean("player_five_unlocked", true);
                        Gdx.app.getPreferences(Globals.GAME_PREFS_NAME).flush();
                        break;
                    case 5:
                        currentBoss++;
                        Gdx.app.getPreferences(Globals.GAME_PREFS_NAME).putBoolean("player_six_unlocked", true);
                        Gdx.app.getPreferences(Globals.GAME_PREFS_NAME).flush();
                        break;
                }

                gameOver = true;

                Gdx.app.getPreferences(Globals.GAME_PREFS_NAME).putInteger("current_round", 1);
                Gdx.app.getPreferences(Globals.GAME_PREFS_NAME).flush();

                setupBossCompletedScreen();
            }
            else if (playerOneScore == 2 && currentRound <= 6) {
                if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("sound_on")) {
                    roundWinSound.play(1f);
                }

                playerOneScore = 0;
                playerTwoScore = 0;

                if (currentRound == 6) {
                    battleThemeMusic.stop();
                    playBossMusic();
                }

                Gdx.app.getPreferences(Globals.GAME_PREFS_NAME).putInteger("current_round", currentRound);
                Gdx.app.getPreferences(Globals.GAME_PREFS_NAME).flush();

                setupBattleScreen();
            }

            isGameDone = false;
            isRoundDone = false;
            hasNewRoundStarted = true;
        }
    }

    private int getCurrentBoss() {
        int boss = 1;

        if (Gdx.app.getPreferences(Globals.GAME_PREFS_NAME).getBoolean("player_five_unlocked")) {
            boss = 5;
        }
        else if (Gdx.app.getPreferences(Globals.GAME_PREFS_NAME).getBoolean("player_four_unlocked")) {
            boss = 4;
        }
        else if (Gdx.app.getPreferences(Globals.GAME_PREFS_NAME).getBoolean("player_three_unlocked")) {
            boss = 3;
        }
        else if (Gdx.app.getPreferences(Globals.GAME_PREFS_NAME).getBoolean("player_two_unlocked")) {
            boss = 2;
        }

        return boss;
    }

    private void setupBossCompletedScreen() {
        GAME.stage.clear();

        Table bossCompletedMessageTable = new Table();
        bossCompletedMessageTable.setFillParent(true);
        bossCompletedMessageTable.align(Align.bottomLeft);
        bossCompletedMessageTable.add(new Image(this.bossCompletedMessageTexture)).width(64).height(64);

        GAME.stage.addActor(bossCompletedMessageTable);
    }

    private void setupBossNotCompletedScreen() {
        GAME.stage.clear();

        Table bossNotCompletedMessageTable = new Table();
        bossNotCompletedMessageTable.setFillParent(true);
        bossNotCompletedMessageTable.align(Align.bottomLeft);
        bossNotCompletedMessageTable.add(new Image(this.beatenMessageTexture)).width(64).height(64);

        GAME.stage.addActor(bossNotCompletedMessageTable);
    }

    private void playBossMusic() {
        Gdx.app.log(TAG, "Boss Music played");

        switch (currentBoss) {
            case 1:
                if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("music_on")) {
                    bossOneMusic.setLooping(true);
                    bossOneMusic.setVolume(0.5f);
                    bossOneMusic.play();
                }
                break;
            case 2:
                if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("music_on")) {
                    bossTwoMusic.setLooping(true);
                    bossTwoMusic.setVolume(0.5f);
                    bossTwoMusic.play();
                }
                break;
            case 3:
                if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("music_on")) {
                    bossThreeMusic.setLooping(true);
                    bossThreeMusic.setVolume(0.5f);
                    bossThreeMusic.play();
                }
                break;
            case 4:
                if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("music_on")) {
                    bossFourMusic.setLooping(true);
                    bossFourMusic.setVolume(0.5f);
                    bossFourMusic.play();
                }
                break;
            case 5:
                if (Gdx.app.getPreferences(Globals.SETTINGS_PREFS_NAME).getBoolean("music_on")) {
                    bossFiveMusic.setLooping(true);
                    bossFiveMusic.setVolume(0.5f);
                    bossFiveMusic.play();
                }
                break;
        }
    }

    private void stopBossMusic() {
        bossOneMusic.stop();
        bossTwoMusic.stop();
        bossThreeMusic.stop();
        bossFourMusic.stop();
        bossFiveMusic.stop();
    }
}
