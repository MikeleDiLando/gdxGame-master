package com.mygdx.game.Models;

import com.mygdx.game.Entity;
import com.mygdx.game.Enums.EntityType;
import com.mygdx.game.Media;
import com.mygdx.game.Rumble;
import com.mygdx.game.box2d.Box2DHelper;
import com.mygdx.game.box2d.Box2DWorld;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;

public class Tree extends Entity {

    public Tree(Vector3 pos, Box2DWorld box2d){
        super();
        type = EntityType.TREE;
        width = 8;
        height = 8;
        this.pos = pos;
        texture = Media.tree;
        body = Box2DHelper.createBody(box2d.world, width/2, height/2, width/4, 0, pos, BodyDef.BodyType.StaticBody);
        sensor = Box2DHelper.createSensor(box2d.world, width, height*.85f, width/2, height/3, pos, BodyDef.BodyType.DynamicBody);
        hashcode = sensor.getFixtureList().get(0).hashCode();
    }

    @Override
    public void interact(Entity entity){
//        if(entity.inventory != null){
//            entity.inventory.addEntity(this);
//            remove = true;
//            Rumble.rumble(1, .2f);
//        }
    }
}