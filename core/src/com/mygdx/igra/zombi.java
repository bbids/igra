package com.mygdx.igra;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class zombi {

    Texture zombiImage = new Texture(Gdx.files.internal("zombie1.png"));
    Rectangle zombi;
    boolean isAlive;
    float x;
    float y;
    //int height;
    //int width;
    int level;
    int health;
    int damage;
    int id; // id kot lastnost
    static int globalID = 0; // id za pomoč - identifakcija vseh zombijev


    public zombi(float x, float y, int level) {
        this.x = x;
        this.y = y;
        zombi = new Rectangle();
        zombi.x = x;
        zombi.y = y;
        zombi.height = 64 * 8f;
        zombi.width = 64 * 8f;
        zombi.setSize(zombi.height, zombi.width);
        zombi.setPosition(zombi.x + (64 - zombi.width) / 2f, zombi.y + (64 - zombi.height) / 2f);

        this.isAlive = true;
        this.level = level;
        this.health = 20 * level;
        this.damage = 5 * level;

        id = globalID + 1;
        globalID++;

    }

    public zombi() {
        isAlive = false;
        this.x = 0;
        this.y = 0;
        // height = 0;
        //width = 0;
        this.level = 0;
        this.health = 0;
        this.damage = 0;
    }
}
