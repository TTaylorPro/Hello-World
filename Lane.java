package com.example.travistaylor.shootertargets;

/**
 * Created by Travis Taylor on 9/29/2016.
 */
public class Lane {
    private Player one;
    private int playerPosition;
    private Enemy target;
    private int enemyPosition;
    private int enemyCount;
    /*Enemies will exist in a queue and be removed when they collide with something
      Only the farthest enemy from origin in a given lane can possibly collide or reach the other side
      Bullets work in a similar way
      For this demonstration, the only enemy needed can exist by itself
     */
    Lane previousLane;
    Lane nextLane;

    public Lane(){

    }

    public void addPlayer(){
        one=new Player();
    }

    public void addPlayer(Player existing){
        one=existing;
    }

    public void addEnemy(){
        target=new Enemy();
        enemyCount=1;
    }

    public void movePlayerUp(){}
    public void movePlayerDown(){}

    public Player getPlayer(){
        return one;
    }
    public int getEnemyCount(){
        return enemyCount;
    }
}
