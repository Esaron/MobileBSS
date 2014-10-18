package com.jdn.mobilebss.view;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.jdn.mobilebss.MobileBss;

public class ButtonRenderer {
    private static ButtonRenderer renderer;
    private static Stage stage = new Stage(new ScreenViewport());
    private static SpriteBatch batch = new SpriteBatch();
    public static int buttonSize = MobileBss.WIDTH/6;

    public static ButtonRenderer getInstance() {
        if (renderer == null) {
            renderer = new ButtonRenderer();
        }
        return renderer;
    }

    private List<ImageButton> buttons = new ArrayList<ImageButton>();

    private ImageButton playButton = createButton("play.png", "play_dark.png", "play_dark.png",
            buttonSize, buttonSize);
    private ImageButton pauseButton = createButton("pause.png", "pause_dark.png", "pause_dark.png",
            buttonSize, buttonSize);
    private ImageButton stopButton = createButton("stop.png", "stop_dark.png", "stop_dark.png",
            buttonSize, buttonSize);
    private ImageButton recordButton = createButton("record.png", "record_dark.png", "record_dark.png",
            buttonSize, buttonSize);

    private ButtonRenderer() {
        addButtons(playButton, pauseButton, stopButton, recordButton);
    }

    public List<ImageButton> getButtons() {
        return buttons;
    }

    public ImageButton getPlayButton() {
        return playButton;
    }

    public ImageButton getPauseButton() {
        return pauseButton;
    }

    public ImageButton getStopButton() {
        return stopButton;
    }

    public ImageButton getRecordButton() {
        return recordButton;
    }

    public void addButtons(ImageButton... buttons) {
        for (ImageButton button : buttons) {
            addButton(button);
        }
    }

    public void addButton(ImageButton button) {
        buttons.add(button);
        stage.addActor(button);
        button.setPosition(buttonSize * this.buttons.size(), 0);
        button.setSize(buttonSize, buttonSize);
        button.pack();
    }

    public void resize(int width, int height) {
        buttonSize = width/6;
        stage.getViewport().update(width, height, true);
        for (ImageButton button : buttons) {
            button.setSize(buttonSize, buttonSize);
            button.pack();
        }
    }

    public void render(float delta, GL20 gl) {
        stage.act(delta);
        batch.begin();
        stage.draw();
        batch.end();
    }

    public Stage getStage() {
        return stage;
    }

    private ImageButton createButton(String iconPath, String downIconPath, String checkedIconPath,
            int width, int height) {
        Sprite defaultSprite = new Sprite(new Texture(Gdx.files.internal(iconPath)));
        defaultSprite.setSize(width, height);
        SpriteDrawable defaultDrawable = new SpriteDrawable(defaultSprite);

        Sprite downSprite = new Sprite(new Texture(Gdx.files.internal(downIconPath)));
        downSprite.setSize(width, height);
        SpriteDrawable downDrawable = new SpriteDrawable(downSprite);

        Sprite checkedSprite = new Sprite(new Texture(Gdx.files.internal(checkedIconPath)));
        checkedSprite.setSize(width, height);
        SpriteDrawable checkedDrawable = new SpriteDrawable(checkedSprite);

        return new ImageButton(defaultDrawable, downDrawable, checkedDrawable);
    }
}
