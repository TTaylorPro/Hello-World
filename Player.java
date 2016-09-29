package com.example.travistaylor.shootertargets;

/**
 * Created by Travis Taylor on 9/29/2016.
 */
public class Player {
    private int health;
    private int speed;
    private int damage;
    private int firerate;
    private float[]color;

    public Player(){
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

    public void changeFireRate(int n) {

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

    public int getFireRate(){
        return firerate;
    }
}
