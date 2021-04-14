package com.mygdx.igra;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;


public class ScenaIgre implements Screen {
    final Igra game;


    static zombi zombi1 = new zombi(3000f, 630f, 1);

    {
        zombiji.add(zombi1);
    }

    static zombi zombi2 = new zombi(3500f, 630f, 1);

    {
        zombiji.add(zombi2);
    }

    static zombi zombi3 = new zombi(4000f, 630f, 2);

    {
        zombiji.add(zombi3);
    }

    static zombi zombi4 = new zombi(4500f, 630f, 3);

    {
        zombiji.add(zombi4);
    }

    static zombi zombi5 = new zombi(5000f, 630f, 3);

    {
        zombiji.add(zombi5);
    }

    static ArrayList<zombi> zombiji = new ArrayList<zombi>();
    static ArrayList<Rectangle> zid = new ArrayList<>();


    private static OrthographicCamera camera;
    private TiledMap tiledMap = new TmxMapLoader().load("map.tmx");
    private TiledMapRenderer tiledMapRenderer;
    private final TiledMapTileLayer collisionLayer = (TiledMapTileLayer) tiledMap.getLayers().get(0);

    static character mc = new character(1375, 1000f, 1);
    public Stage stage;
    Label xLabel, yLabel;


    private MapObjects objects;

    boolean lapping = true;

    public ScenaIgre(final Igra game, String temp) {
        this.game = game;

    }

    public ScenaIgre(final Igra game, zombi zombi, int id) {
        this.game = game;

        // loop zombijev - tisti ki se ujema z id pridobi lastnosti zombija .. :d
        for (zombi zambi : zombiji) {
            if (zambi.id == id) {
                zambi = zombi;
            }
        }
        create();


    }

    public ScenaIgre(final Igra game) {
        this.game = game;


        create();
    }

    public void create() {
        // glavna kamera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 900);
        //viewport = new FitViewport(1600, 900, new OrthographicCamera());


        // mapa
        //tiledMap = new TmxMapLoader().load("map.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        objects = tiledMap.getLayers().get("ObjectLayer").getObjects();
        for (MapObject object : objects) {
            RectangleMapObject rectangleObject
                    = (RectangleMapObject) object;
            Rectangle r = rectangleObject.getRectangle();
            zid.add(r);


        }


        //collisionLayer = (TiledMapTileLayer)tiledMap.getLayers().get(0);


        // scene2d
        stage = new Stage();

        Table table = new Table();
        table.setSize(400, 400);
        table.setPosition(25, 450);
        table.align(Align.left | Align.top);
        //table.top();
        //table.setFillParent(true); // size of stage

        Label scoreLabel = new Label("SCORE: " + mc.score + " ", new Label.LabelStyle(game.font, Color.WHITE));
        Label levelLabel = new Label("LEVEL: " + mc.level, new Label.LabelStyle(game.font, Color.WHITE));
        Label healthLabel = new Label("HEALTH: " + mc.health, new Label.LabelStyle(game.font, Color.WHITE));
        xLabel = new Label("X: " + mc.character.x, new Label.LabelStyle(game.font, Color.WHITE));
        yLabel = new Label("Y: " + mc.character.y, new Label.LabelStyle(game.font, Color.WHITE));


        scoreLabel.setFontScale(2f);
        levelLabel.setFontScale(2f);
        healthLabel.setFontScale(2f);
        xLabel.setFontScale(2f);
        yLabel.setFontScale(2f);

        // table.add(scoreLabel).expandX();
        table.add(scoreLabel).align(Align.left).row();
        table.add(levelLabel).align(Align.left).row();
        table.add(healthLabel).align(Align.left).row();
        table.add(xLabel).align(Align.left).row();
        table.add(yLabel).align(Align.left);


        stage.addActor(table);


    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InProc(game)); // event input
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // clear screen
        // glavna kamera
        camera.position.set(mc.position.x + mc.character.width / 2, mc.position.y + mc.character.height / 2, 0);


        camera.update();

        if (mc.score >= 500 ) {
            dispose();
            game.setScreen(new GameOver(game));
        }

        // user input

        mc.speed = 9;
        mc.velocity.set(0, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            mc.velocity.set(-mc.speed, 0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            mc.velocity.set(mc.speed, 0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            mc.velocity.set(0, mc.speed);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            mc.velocity.set(0, -mc.speed);
        }


        // preden se zračuna vektor ( točka ) se čekira, če bo ta ( naslednja ) točka collidala z zidom
        Vector2 position2 = new Vector2();
        position2.set(mc.position.x, mc.position.y);
        position2.add(mc.velocity);

        float tempX = mc.character.x;
        float tempY = mc.character.y;

        mc.character.x = position2.x;
        mc.character.y = position2.y;

        for (Rectangle zid : zid) {
            if (zid.overlaps(mc.character)) {
                lapping = false;
            }
        }

        if (lapping) { // lahko naprej

            mc.character.x = mc.position.x;
            mc.character.y = mc.position.y;
            mc.position.add(mc.velocity);

        } else {
            mc.character.x = tempX;
            mc.character.y = tempY;

        }
        lapping = true;


        // mapa
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();


        // narisi character
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(mc.characterImage, mc.position.x, mc.position.y);

        // narisi vse zombije ( žive )
        for (zombi zombi : zombiji)
            if (zombi.isAlive) {
                game.batch.draw(zombi.zombiImage, zombi.x, zombi.y);
            }
        game.batch.end();

        // preveri ce character overlappa z zombijem
        for (zombi zambi : zombiji) {
            if (zambi.isAlive && mc.character.overlaps(zambi.zombi)) {
                //dispose();
                game.setScreen(new bitka(game, zambi, zambi.id));

            }

        }



        //Gdx.input.setInputProcessor(new InProc(game));
        if (game.neki) {
            prikaziMeje();
        }


        // scene2d

        xLabel.setText("X: " + mc.character.x);
        yLabel.setText("Y: " + mc.character.y);
        stage.act();
        stage.draw();


    }


    public void prikaziMeje() {
        ShapeRenderer sr = new ShapeRenderer();

        sr.setProjectionMatrix(camera.combined);
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.RED);

        // risanje + odstrani vse zombije, ki niso vec zivi, iz lista
        int idx = 0;
        boolean b = false;
        int i = 0;
        for (zombi zombi : zombiji) {
            sr.rect(zombi.zombi.getX(), zombi.zombi.getY(), zombi.zombi.getWidth(), zombi.zombi.getHeight());// narisi rect

            if (!zombi.isAlive) {
                idx = i;
                b = true;
            }
            i++;
        }
        if (b) {
            zombiji.remove(idx);
            b = false;
        }

        sr.end();
        sr.dispose();

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
        mc.characterImage.dispose();

        //  for ( zombi zombi : zombiji ) {
        //  zombi.zombiImage.dispose(); }
        //
    }

}