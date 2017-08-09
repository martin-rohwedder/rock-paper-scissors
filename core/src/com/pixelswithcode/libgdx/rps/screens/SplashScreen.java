package com.pixelswithcode.libgdx.rps.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.pixelswithcode.libgdx.rps.RPSGame;
import com.pixelswithcode.libgdx.rps.utils.Globals;

public class SplashScreen extends ScreenAdapter {

    private final String TAG = SplashScreen.class.getSimpleName();
    private final RPSGame GAME;
    private boolean hasDisposed;

    private boolean animationsDone;

    // Textures
    private Texture backgroundTexture;
    private Texture programmerCreditsTexture;
    private Texture musicCreditsTexture;
    private Texture libgdxLogoTexture;

    public SplashScreen(final RPSGame game) {
        this.GAME = game;
        this.hasDisposed = true;
        this.animationsDone = false;
    }

    @Override
    public void show() {
        this.hasDisposed = false;
        this.animationsDone = false;

        loadAssets();
        getAssets();

        setupSplashScreen();
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY) || Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            animationsDone = true;
        }

        if (animationsDone) {
            GAME.setScreen(GAME.loadingScreen);
        }
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        if (!hasDisposed) {
            Gdx.app.log(TAG, "Objects Disposed");
            GAME.assetManager.clear();

            hasDisposed = true;
        }
    }

    private void loadAssets() {
        GAME.assetManager.load(Globals.CREDITS_PATH + "background-white.png", Texture.class);
        GAME.assetManager.load(Globals.CREDITS_PATH + "programmer-credits.png", Texture.class);
        GAME.assetManager.load(Globals.CREDITS_PATH + "music-credits.png", Texture.class);
        GAME.assetManager.load(Globals.CREDITS_PATH + "libgdx-logo.png", Texture.class);

        GAME.assetManager.finishLoading();
    }

    private void getAssets() {
        this.backgroundTexture = GAME.assetManager.get(Globals.CREDITS_PATH + "background-white.png", Texture.class);
        this.programmerCreditsTexture = GAME.assetManager.get(Globals.CREDITS_PATH + "programmer-credits.png", Texture.class);
        this.musicCreditsTexture = GAME.assetManager.get(Globals.CREDITS_PATH + "music-credits.png", Texture.class);
        this.libgdxLogoTexture = GAME.assetManager.get(Globals.CREDITS_PATH + "libgdx-logo.png", Texture.class);
    }

    private void setupSplashScreen() {
        GAME.stage.clear();

        Table backgroundTable = new Table();
        backgroundTable.setFillParent(true);
        backgroundTable.align(Align.bottomLeft);
        backgroundTable.add(new Image(this.backgroundTexture)).width(64).height(64);

        Table libgdxLogoTable = new Table();
        libgdxLogoTable.setFillParent(true);
        libgdxLogoTable.align(Align.bottomLeft);
        libgdxLogoTable.add(new Image(this.libgdxLogoTexture)).width(64).height(64);
        libgdxLogoTable.addAction(Actions.sequence(
                Actions.alpha(0),
                Actions.delay(0.2f),
                Actions.fadeIn(0.5f),
                Actions.delay(1f),
                Actions.fadeOut(0.5f)
        ));

        Table programmerCreditsTable = new Table();
        programmerCreditsTable.setFillParent(true);
        programmerCreditsTable.align(Align.bottomLeft);
        programmerCreditsTable.add(new Image(this.programmerCreditsTexture)).width(64).height(64);
        programmerCreditsTable.addAction(Actions.sequence(
                Actions.alpha(0),
                Actions.delay(2.4f),
                Actions.fadeIn(0.5f),
                Actions.delay(1f),
                Actions.fadeOut(0.5f)
        ));

        Table musicCreditsTable = new Table();
        musicCreditsTable.setFillParent(true);
        musicCreditsTable.align(Align.bottomLeft);
        musicCreditsTable.add(new Image(this.musicCreditsTexture)).width(64).height(64);
        musicCreditsTable.addAction(Actions.sequence(
                Actions.alpha(0),
                Actions.delay(4.6f),
                Actions.fadeIn(0.5f),
                Actions.delay(1f),
                Actions.fadeOut(0.5f),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        animationsDone = true;
                    }
                })
        ));

        GAME.stage.addActor(backgroundTable);
        GAME.stage.addActor(libgdxLogoTable);
        GAME.stage.addActor(programmerCreditsTable);
        GAME.stage.addActor(musicCreditsTable);
    }
}
