package com.example.travistaylor.shootertargets;

/**
 * Created by Travis Taylor on 9/29/2016.
 */
public class Enemy {
    private int health;
    private int speed;
    private int damage;
    private float[]color;
    private int position;

    public Enemy(){
        health=1;
        speed=0;
        damage=0;
        position=0;

    }

    public void changeHealth(int n){
        health+=n;
    }

    public void changeDamage(int n){
        damage+=n;
    }

    public void changeSpeed(int n){
        speed+=n;
    }

    public int getHealth(){
        return health;
    }

    public int getDamage(){
        return damage;
    }

    public int getSpeed(){
        return speed;
    }

    public void decrementPosition(int n){position+=n;}

    public int getPosition(){return position;}
}
