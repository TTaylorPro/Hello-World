package com.example.travistaylor.shootertargets;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Travis Taylor on 9/29/2016.
 */
public class Lane {
    private Player one;
    private Queue<Enemy> targets=new LinkedList<>();
    private int enemyCount;
    private int laneOffset;
    private Queue<Bullet> shots=new LinkedList<>();
    private int bulletCount;
    private Queue<Enemy> storeTargets=new LinkedList<>();
    private Queue<Bullet> storeShots=new LinkedList<>();
    int storage;
    /*Enemies will exist in a queue and be removed when they collide with something
      Only the farthest enemy from origin in a given lane can possibly collide or reach the other side
      Bullets work in a similar way
      For this demonstration, the only enemy needed can exist by itself
     */
    Lane previousLane;
    Lane nextLane;

    public Lane(){

        if(previousLane!=null)laneOffset=previousLane.getLaneOffset()+1;
        storage=0;
    }

    public void addPlayer(){
        one=new Player();
    }

    public void addPlayer(Player existing){
        one=existing;
    }

    public void addEnemy(){
        //if(enemyCount>0)popEnemy();
        targets.add(new Enemy());
        enemyCount++;
    }
    public void popEnemy(){
        targets.remove();
        enemyCount--;
    }
    public void movePlayerUp(){}
    public void movePlayerDown(){}
    public void addBullet(){
        shots.add(new Bullet());
        bulletCount++;
    }
    public void popBullet(){
        shots.remove();
        bulletCount--;
    }

    public Player getPlayer(){
        return one;
    }
    public int getEnemyCount(){return enemyCount;}
    public int getEnemyPosition(int n){
            for (int i = 0; i < n; i++) storeTargets.add(targets.remove());
            storage = targets.peek().getPosition();
            while (targets.peek() != null) storeTargets.add(targets.remove());
            while (storeTargets.peek() != null) {
                targets.add(storeTargets.remove());
            }
            return storage;
    }
    public void decrementEnemyPosition(int n){
        while(targets.peek()!=null){
            targets.peek().decrementPosition(n);
            storeTargets.add(targets.remove());
        }
        while(storeTargets.peek()!=null){
            targets.add(storeTargets.remove());
        }
    }

    public int getBulletCount(){return bulletCount;}
    public int getBulletPosition(int n){
        for (int i = 0; i < n; i++) storeShots.add(shots.remove());
        storage = shots.peek().getPosition();
        while (shots.peek() != null) storeShots.add(shots.remove());
        while (storeShots.peek() != null) {
            shots.add(storeShots.remove());
        }
        return storage;
    }

    public void incrementBulletPosition(int n){
        while(shots.peek()!=null){
            shots.peek().incrementPosition(n);
            storeShots.add(shots.remove());
        }
        while(storeShots.peek()!=null){
            shots.add(storeShots.remove());
        }
    }


    public int getLaneOffset(){return laneOffset;}
}
