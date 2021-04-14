package com.mygdx.igra;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.mygdx.igra.ScenaIgre.zombiji;

public class Igra extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    public boolean neki = true;


    @Override
    public void create() {
        batch = new SpriteBatch();


        //font, to si se poglej
        font = new BitmapFont();
        font.getData().setScale(0.5f);
        this.setScreen(new GlavniMeni(this));


    }

    public void render() {

        super.render(); //important!


    }


    public void dispose() {

        batch.dispose();
        font.dispose();

        for (zombi zombi : zombiji) {
            zombi.zombiImage.dispose();
        }
    }
}
