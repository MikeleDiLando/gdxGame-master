package com.mygdx.game;

//The all of the Enumerators for the game
public class Enums {

    //The types of tiles that are available
    public enum TileType {
        GRASS,
        WATER,
        CLIFF
    }

    public enum EntityType {
        HERO,
        TREE,
        BIRD
    }

    public enum EnityState {
        NONE,
        IDLE,
        FEEDING,
        WALKING,
        FLYING,
        HOVERING,
        LANDING
    }
}
