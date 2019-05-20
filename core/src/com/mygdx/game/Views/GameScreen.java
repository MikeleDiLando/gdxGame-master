package com.mygdx.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Control;
import com.mygdx.game.Entity;
import com.mygdx.game.Enums.EnityState;
import com.mygdx.game.Main;
import com.mygdx.game.Media;
import com.mygdx.game.Models.Bird;
import com.mygdx.game.box2d.Box2DWorld;
import com.mygdx.game.map.Island;
import com.mygdx.game.map.Tile;

import java.util.ArrayList;
import java.util.Collections;

public class GameScreen implements Screen {
    private Main parent;
    // Island
    Control control;
    Box2DWorld box2D;

    // Display Size
    private int displayW;
    private int displayH;

    // Temp x and y co-ords
    int x, y;

    // For Movement
    int direction_x, direction_y;
    int speed = 1;

    // Island
    Island island;

    // TIME
    float time;


//    private B2dModel model;
//    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;
//    private KeyboardController controller;
//    private AtlasRegion playerTex;
    private SpriteBatch batch;
//    private TextureAtlas atlas;


    public GameScreen(Main main) {
        parent = main;
        Media.load_assets();
        batch = new SpriteBatch();


        // CAMERA
        displayW = Gdx.graphics.getWidth();
        displayH = Gdx.graphics.getHeight();

        // For 800x600 we will get 266*200
        int h = (int) (displayH/Math.floor(displayH/160));
        int w = (int) (displayW/(displayH/ (displayH/Math.floor(displayH/160))));

        camera = new OrthographicCamera(w,h);

        // Used to capture Keyboard Input
        control = new Control(displayW, displayH, camera);
        Gdx.input.setInputProcessor(control);

        // Box2D
        box2D = new Box2DWorld();

        // Island
        island = new Island(box2D);

        int count = 0;
        for (int i = 0; i <= 10; i++) {
            island.entities.add(new Bird(island.centreTile.pos, box2D, EnityState.IDLE));
            count++;
        }


        // HashMap of Entities for collisions
        box2D.populateEntityMap(island.entities);

        control.reset = true;

//        controller = new KeyboardController();
//        model = new B2dModel(controller,cam,parent.assMan);
//        debugRenderer = new Box2DDebugRenderer(true,true,true,true,true,true);
//        atlas = parent.assMan.manager.get("images/game.atlas");
//        playerTex = atlas.findRegion("player");
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void show() {
//        Gdx.input.setInputProcessor(controller);
    }

    @Override
    public void render(float delta) {
//        model.logicStep(delta);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

//
        // GAME LOGIC
        // Reset the direction values
        direction_x=0;
        direction_y=0;

        if(control.down)  direction_y = -1 ;
        if(control.up)    direction_y = 1 ;
        if(control.left)  direction_x = -1;
        if(control.right) direction_x = 1;

        camera.position.x += direction_x * speed;
        camera.position.y += direction_y * speed;


        // Tick all entities
        for(Entity e: island.entities){
            e.tick(Gdx.graphics.getDeltaTime());
            e.currentTile = island.chunk.getTile(e.body.getPosition());
            e.tick(Gdx.graphics.getDeltaTime(), island.chunk);
        }

        camera.update();

        Collections.sort(island.entities);

        // GAME DRAW
        batch.setProjectionMatrix(camera.combined);
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        batch.begin();
        // Draw all tiles in the chunk / chunk rows
        for(ArrayList<Tile> row : island.chunk.tiles){
            for(Tile tile : row){
                batch.draw(tile.texture, tile.pos.x, tile.pos.y, tile.size, tile.size);
                if (tile.secondaryTexture != null) batch.draw(tile.secondaryTexture, tile.pos.x, tile.pos.y, tile.size, tile.size);
            }
        }

        // Draw all entities
        for(Entity e: island.entities){
            e.draw(batch);
        }

        batch.end();

        box2D.tick(camera, control);
        island.clearRemovedEntities(box2D);

        time += Gdx.graphics.getDeltaTime();
        if(time > 3){
            System.out.println(Gdx.graphics.getFramesPerSecond());
            time = 0;
        }
        control.processedClick = true;
    }

    private void resetGameState() {
        island.reset(box2D);

        for(int i = 0; i < MathUtils.random(20); i++){
            island.entities.add(new Bird(new Vector3(MathUtils.random(100),MathUtils.random(100),0), box2D, EnityState.FLYING));
        }

        box2D.populateEntityMap(island.entities);
        control.reset = false;
    }


    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
        float aspectRation = (float) height / width;
        //camera = new OrthographicCamera(20f, 20f * aspectRation);
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        batch.dispose();
    }
}