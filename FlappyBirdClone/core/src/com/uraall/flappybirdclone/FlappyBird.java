package com.uraall.flappybirdclone;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;


public class FlappyBird extends ApplicationAdapter {
    SpriteBatch batch;
    Texture background;
    Texture[] bird;
    Texture bird_wings_up;
    int birdStateFlag = 0;
    float flyheight;
    double fallingSpeed = 0.0;
    int gameStateFlag = 0;
    Texture topTube;
    Texture bottomTube;
    int spaceBetweenTubes = 500;

    Random random;
    int tubeSpeed = 2;
    int tubesNumber = 5;
    float tubeX[] = new float[tubesNumber];
    float tubeShift[] = new float[tubesNumber];
    float distanceBetweenTubes;
    Circle birdCircle;
    ShapeRenderer shapeRenderer;
    Rectangle[] topTubeReactangles;
    Rectangle[] bottomTubeRectangless;


    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("background.png");
        bird_wings_up = new Texture("bird_wings_up.png");
        bird = new Texture[2];
        bird[0] = new Texture("bird_wings_up.png");
        bird[1] = new Texture("bird_wings_down.png");
        flyheight = Gdx.graphics.getHeight() / 2 - bird[0].getHeight() / 2;
        topTube = new Texture("top_tube.png");
        bottomTube = new Texture("bottom_tube.png");
        random = new Random();
        birdCircle = new Circle();
        shapeRenderer = new ShapeRenderer();
        topTubeReactangles = new Rectangle[tubesNumber];
        bottomTubeRectangless = new Rectangle[tubesNumber];

        distanceBetweenTubes = Gdx.graphics.getWidth() / 2;
        for (int i = 0; i < tubesNumber; i++) {
            tubeX[i] = Gdx.graphics.getWidth() / 2 - topTube.getWidth() / 2 + i * distanceBetweenTubes;
            tubeShift[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - spaceBetweenTubes - 700);
            topTubeReactangles[i] = new Rectangle();
            bottomTubeRectangless[i] = new Rectangle();

        }

    }

    @Override
    public void render() {
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        if (Gdx.input.justTouched()) {
            Gdx.app.log("Tap", "блядзь!");
            gameStateFlag = 1;
        }
        if (gameStateFlag == 1) {

            if (Gdx.input.justTouched()) {
                fallingSpeed = -25;
            }

            if (flyheight > 0 || fallingSpeed < 0) {
                fallingSpeed++;
                flyheight -= fallingSpeed;
            }


        } else {
            if (Gdx.input.justTouched()) {
                Gdx.app.log("Tap", "блядзь!");
                gameStateFlag = 1;
            }
        }
        for (int i = 0; i < tubesNumber; i++) {
            if (tubeX[i] < -topTube.getWidth()) {
                tubeX[i] = tubesNumber * distanceBetweenTubes;
            } else {
                tubeX[i] -= tubeSpeed;
            }
            tubeX[i] -= tubeSpeed;
            batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight() / 2 + spaceBetweenTubes / 2 + tubeShift[i]);
            batch.draw(bottomTube, tubeX[i], Gdx.graphics.getHeight() / 2 - spaceBetweenTubes / 2 - bottomTube.getHeight() + tubeShift[i]);
            topTubeReactangles[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 + spaceBetweenTubes / 2 + tubeShift[i], topTube.getWidth(), topTube.getHeight());
            bottomTubeRectangless[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 - spaceBetweenTubes / 2 - bottomTube.getHeight()+tubeShift[i], bottomTube.getWidth(), bottomTube.getHeight());
        }

        if (birdStateFlag == 0) {
            birdStateFlag = 1;
        } else {
            birdStateFlag = 0;
        }


        batch.draw(bird[birdStateFlag], Gdx.graphics.getWidth() / 2 - bird[birdStateFlag].getWidth() / 2, flyheight);
        batch.end();

        birdCircle.set(Gdx.graphics.getWidth() / 2,
                flyheight + bird[birdStateFlag].getHeight() / 2, bird[birdStateFlag].getWidth() / 2);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.CYAN);
        //shapeRenderer.circle(birdCircle.x, birdCircle.y, birdCircle.radius);

        for (int i = 0; i < tubesNumber; i++) {
           // shapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight() / 2 + spaceBetweenTubes / 2 + tubeShift[i], topTube.getWidth(), topTube.getHeight());
           // shapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight() / 2 - spaceBetweenTubes / 2 - bottomTube.getHeight()+tubeShift[i], bottomTube.getWidth(), bottomTube.getHeight());
        if(Intersector.overlaps(birdCircle, topTubeReactangles[i])||
            (Intersector.overlaps(birdCircle, bottomTubeRectangless[i]))){
            Gdx.app.log("Tap", "ТЫ ПРАЯБАЎ!!1");
            }
        }

        shapeRenderer.end();

    }

    @Override
    public void dispose() {

    }
}

