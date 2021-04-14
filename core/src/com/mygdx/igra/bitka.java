package com.mygdx.igra;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;

import static com.mygdx.igra.ScenaIgre.mc;

public class bitka implements Screen {
    final Igra game;

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Texture characterImage;
    private Texture zombieImage;
    boolean flag = true;
    boolean buttonFlag = false;
    boolean flagZ = false;

    private Rectangle Character;
    private Rectangle Zombie;

    private Stage stage;
    private Label zombieHealth;
    private Label characterHealth;
    private Skin skin;

    static zombi zombi = new zombi();
    private int fakeID;
    boolean bposebenNapad = false;

    public bitka(Igra game, zombi Zombi, int id) {


        this.game = game;
        batch = new SpriteBatch();

        // zombi tuki dobi vse lastnosti podanega zombija, razen id ker ga nima
        zombi = Zombi;
        System.out.println("1: " + zombi.x + "2: " + Zombi.x);
        fakeID = id;


        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 900);


        // SCENE2D
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setSize(400, 400);
        //table.setFillParent(true);
        table.setPosition(1270, 275);
        table.align(Align.left | Align.bottom);

        Table charTable = new Table();
        charTable.setSize(400, 400);
        charTable.setPosition(150, 275);
        charTable.align(Align.left | Align.bottom);


        skin = new Skin(Gdx.files.internal("uiskin.json"));

        Button napad = new TextButton("   OBICAJEN NAPAD    ", skin);
        Button posebenNapad = new TextButton("   POSEBEN NAPAD     ", skin);

        napad.setPosition(600, 250);
        napad.setSize(150, 25);
        napad.setTransform(true);
        napad.scaleBy(1.5f);

        posebenNapad.setPosition(600, 160);
        posebenNapad.setSize(150, 25);
        posebenNapad.setTransform(true);
        posebenNapad.scaleBy(1.5f);

        napad.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("OGMOMGOMG");
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Image", "touchDown");
                buttonFlag = true;
                return true;
            }
        });
        posebenNapad.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("OGMOMGOMG");
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Image", "touchDown");
                buttonFlag = true;
                bposebenNapad = true;
                return true;
            }
        });


        zombieHealth = new Label("HEALTH: " + zombi.health, new Label.LabelStyle(game.font, Color.WHITE));
        characterHealth = new Label("HEALTH: " + mc.health, new Label.LabelStyle(game.font, Color.WHITE));


        zombieHealth.setFontScale(2f);
        table.add(zombieHealth).align(Align.right).row();

        characterHealth.setFontScale(2f);
        charTable.add(characterHealth).align(Align.left).row();


        stage.addActor(napad);
        stage.addActor(posebenNapad);
        stage.addActor(table);
        stage.addActor(charTable);


        characterImage = new Texture(Gdx.files.internal("character.png"));
        zombieImage = new Texture(Gdx.files.internal("zombie1.png"));


        Character = new Rectangle();
        Character.x = 25;
        Character.y = 600;
        Character.width = 400;
        Character.height = 400;

        Zombie = new Rectangle();
        Zombie.x = 1175;
        Zombie.y = 600;
        Zombie.width = 400;
        Zombie.height = 400;


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(0.5f, 0.8f, 0.5f, 1);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        camera.position.set(800, 600, 0);


        if (!zombi.isAlive) {
            // PRIKAZE SE NAPIS ZMAGA
            mc.level++;
            mc.score += 100;
            mc.health = 10 * mc.level;
            dispose();
            game.setScreen(new ScenaIgre(game, zombi, fakeID));
        }


        if (!mc.isAlive) {
            // GAME OVER
            dispose();
            game.setScreen(new GameOver(game));
        }


        batch.begin();
        batch.draw(characterImage, Character.x, Character.y + 0, 400, 400);
        batch.draw(zombieImage, Zombie.x + 0, Zombie.y + 0, 400, 400);
        batch.end();


        // CE UPORABNIK PRITISNE GUMB SE FLAG POSTAVI NA TRUE, PREDPOSTAVIMO DA JE TO BILO LIHKAR STORJENO

        if (flag && buttonFlag) {
            Character.x += 500 * Gdx.graphics.getDeltaTime();
        }
        // CE CHARACTER OVERLAPA ZOMBIE SE USTAVI, ODŠTEJE SE HEALTH ZOMBIJU
        if (Character.overlaps(Zombie) && flag) {
            flag = false;
            buttonFlag = false;
            if ( bposebenNapad ) zombi.health -= 2 * mc.damage;
            else zombi.health -= mc.damage;
            bposebenNapad = false;

        }
        // SE TELEPORTIRA NAZAJ, NAPAD OPRAVLJEN
        if (!flag) {
            Character.x = 25;
            flagZ = true;
            if (zombi.health <= 0) zombi.isAlive = false;
        }

        // CASE SE USTAVI ZA 1S, POTEM NAPAD ZOMBIJA: ZOMBIE SE NAJPREJ ZACNE POMIKATI LEVO
        if (flagZ) {
            Zombie.x -= 500 * Gdx.graphics.getDeltaTime();
        }
        // KO OVERLAPA CHARACTER SE USTAVI, ODŠTEJE SE HEALTH CHARECTERJU
        if (Zombie.overlaps(Character) && flagZ) {
            flagZ = false;
            mc.health -= zombi.damage;
        }
        // TELEPORTIRA SE NAZAJ, NAPAD JE OPRAVLJEN
        if (!flagZ) {
            Zombie.x = 1175;
            flag = true;
            if (mc.health <= 0) mc.isAlive = false;
        }


        // scene2d

        zombieHealth.setText("HEALTH: " + zombi.health);
        characterHealth.setText("HEALTH: " + mc.health);
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
        skin.dispose();
    }
}
