package com.example.travistaylor.shootertargets;

/**
 * Created by Travis Taylor on 9/29/2016.
 */
public class Enemy {
    int health;
    int speed;
    int damage;
    float[]color;

    public Enemy(){
        health=1;
        speed=0;
        damage=0;
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
}
