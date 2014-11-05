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

    private ImageButton playButton;
    private ImageButton pauseButton;
    private ImageButton stopButton;
    private ImageButton recordButton;

    private ButtonRenderer() {
        playButton = createButton("play", "play_dark", "play_dark",
                buttonSize, buttonSize);
        pauseButton = createButton("pause", "pause_dark", "pause_dark",
                buttonSize, buttonSize);
        stopButton = createButton("stop", "stop_dark", "stop_dark",
                buttonSize, buttonSize);
        recordButton = createButton("record", "record_dark", "record_dark",
                buttonSize, buttonSize);
        addButtons(playButton, pauseButton, stopButton, recordButton);
    }

    public void refreshButtons() {
        clearButtons();
        playButton = createButton("play", "play_dark", "play_dark",
                buttonSize, buttonSize);
        pauseButton = createButton("pause", "pause_dark", "pause_dark",
                buttonSize, buttonSize);
        stopButton = createButton("stop", "stop_dark", "stop_dark",
                buttonSize, buttonSize);
        recordButton = createButton("record", "record_dark", "record_dark",
                buttonSize, buttonSize);
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

    public void clearButtons() {
        for (int i = 0; i < buttons.size(); i++) {
            ImageButton button = buttons.get(i);
            button.clear();
            button.remove();
            buttons.remove(i);
        }
        playButton = null;
        pauseButton = null;
        stopButton = null;
        recordButton = null;
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

    private ImageButton createButton(String iconName, String downIconName, String checkedIconName,
            int width, int height) {
        Sprite defaultSprite = MobileBss.TEXTURES.createSprite(iconName);
        defaultSprite.setSize(width, height);
        SpriteDrawable defaultDrawable = new SpriteDrawable(defaultSprite);

        Sprite downSprite = MobileBss.TEXTURES.createSprite(downIconName);
        downSprite.setSize(width, height);
        SpriteDrawable downDrawable = new SpriteDrawable(downSprite);

        Sprite checkedSprite = MobileBss.TEXTURES.createSprite(checkedIconName);
        checkedSprite.setSize(width, height);
        SpriteDrawable checkedDrawable = new SpriteDrawable(checkedSprite);

        return new ImageButton(defaultDrawable, downDrawable, checkedDrawable);
    }
}
