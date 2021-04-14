package com.mygdx.igra;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.mygdx.igra.ScenaIgre.mc;

public class character {

    Texture characterImage = new Texture(Gdx.files.internal("character.png"));
    Rectangle character;
    boolean isAlive;
    int level;
    int health;
    int damage;
    int score;

    Vector2 position;
    float speed;
    Vector2 velocity; // direction * speed



    public character(float x, float y, int level) {
        position = new Vector2(x, y);
        speed = 0;
        velocity = new Vector2();



        character = new Rectangle();
        character.x = x;
        character.y = y;
        character.width = 64;
        character.height = 64;


        this.isAlive = true;
        this.level = level;
        this.health = 15 * level;
        this.damage = 10 * level;
        this.score = 0;
    }


}
