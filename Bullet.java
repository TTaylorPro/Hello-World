package com.example.travistaylor.shootertargets;

/**
 * Created by Travis Taylor on 11/10/2016.
 * Incomplete
 */
public class Bullet {
    private int position;

    public Bullet(){
        position=0;
    }

    public void incrementPosition(int n){position+=n;}

    public int getPosition(){return position;}
}
