package com.mygdx.igra;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;

public class GlavniMeni implements Screen {
    final Igra game;
    private OrthographicCamera camera;
    private Stage stage;


    private ParticleEffect pe;


    public GlavniMeni(final Igra game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 900);

        pe = new ParticleEffect();
        pe.load(Gdx.files.internal("blood.pfx"), Gdx.files.internal(""));
        pe.getEmitters().first().setPosition(200, 0);
        pe.start();


        stage = new Stage();

        Table table = new Table();
        //table.setSize(800, 800);
        table.setFillParent(true);
        //table.setPosition(800 , 450);
        //table.align(Align.left | Align.top);

        Label mainText = new Label("KLIKNI KJERKOLI ZA ZACETEK!", new Label.LabelStyle(game.font, Color.WHITE));

        mainText.setFontScale(2f);
        table.add(mainText);


        stage.addActor(table);


    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InProc(game));
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(0, 0f, 0f, 1);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);


        pe.update(Gdx.graphics.getDeltaTime());
        game.batch.begin();
        pe.draw(game.batch);
        game.batch.end();
        if (pe.isComplete())
            pe.reset();


        if (Gdx.input.isTouched()) {
            game.setScreen(new ScenaIgre(game));
            dispose();
        }

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
        pe.dispose();
        stage.dispose();
    }
}
