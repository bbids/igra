package com.mygdx.igra;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;

import static com.mygdx.igra.ScenaIgre.mc;

public class GameOver implements Screen {
    final Igra game;
    private OrthographicCamera camera;
    private Stage stage;

    public GameOver(Igra game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 900);

        stage = new Stage();

        Table table = new Table();
        //table.setSize(800, 800);
        table.setFillParent(true);
        //table.setPosition(800 , 450);
        //table.align(Align.left | Align.top);
        Label mainText;
        if ( mc.score >= 500 ) {
             mainText = new Label("ZMAGA!", new Label.LabelStyle(game.font, Color.WHITE));
        } else {
         mainText = new Label("GAME OVER!", new Label.LabelStyle(game.font, Color.WHITE)); }

        mainText.setFontScale(10f);
        table.add(mainText);


        stage.addActor(table);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(0, 0f, 0f, 1);


        camera.update();

        game.batch.setProjectionMatrix(camera.combined);


        if (Gdx.input.isTouched()) {

            dispose();
            Gdx.app.exit();
        }


        stage.act();
        stage.draw();
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

    }

    @Override
    public void dispose() {

    }
}
