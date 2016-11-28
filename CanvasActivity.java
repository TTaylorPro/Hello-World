package com.example.travistaylor.shootertargets;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.Color;

/**
 * Created by Travis Taylor on 10/2/2016.
 */
public class CanvasActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MySurfaceView mSurfaceView=new MySurfaceView(this);
        setContentView(mSurfaceView);
    }

    class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

        class ShooterThread extends Thread {
            private Lane mLane;
            private SurfaceHolder mSurfaceHolder;
            private boolean mRun = false;
            private final Object mRunLock = new Object();
            private int mCanvasHeight = 1;
            private int mCanvasWidth = 1;
            private long mLastTime;
            private int targetLag;
            private int playerLag;
            Paint paint=new Paint();

            public ShooterThread(SurfaceHolder surfaceHolder) {
                mSurfaceHolder = surfaceHolder;
                mLane = new Lane();
                mLane.addPlayer();
                mLastTime = System.currentTimeMillis();
                playerLag=0;
                targetLag=0;

            }

            public void setRunning(boolean b) {
                synchronized (mRunLock) {
                    mRun = b;
                }
            }

            public void setSurfaceSize(int width, int height) {
                // synchronized to make sure these all change atomically
                synchronized (mSurfaceHolder) {
                    mCanvasWidth = width;
                    mCanvasHeight = height;
                }

            }

            public void addBullet(){
                synchronized (mSurfaceHolder) {
                    if (playerLag == 0) {
                        mLane.addBullet();
                        playerLag+=mCanvasWidth/8;
                    }
                }
            }

            private void updatePhysics(){
                long now = System.currentTimeMillis();
                if (mLastTime > now) return;
                double elapsed = (now - mLastTime) / 1000.0;

                if(mLane.getBulletCount()>0&&mLane.getEnemyCount()>0){
                    if((((mCanvasWidth/8)*2)+mLane.getBulletPosition(0))>=(((mCanvasWidth/8)*7)-mLane.getEnemyPosition(0))){
                        mLane.popBullet();
                        mLane.popEnemy();
                    }
                }

                if(mLane.getEnemyCount()>0) {
                    mLane.decrementEnemyPosition((int) elapsed + 8);
                }

                if(mLane.getEnemyCount()>0){if((((mCanvasWidth / 8) * 7) - mLane.getEnemyPosition(0)<(mCanvasWidth / 8))&&mLane.getPlayer()!=null)mLane.popEnemy();}

                if(mLane.getEnemyCount()>0) {if (((mCanvasWidth / 8) * 7) - mLane.getEnemyPosition(0) < 0) mLane.popEnemy();}

                if(targetLag>0)targetLag-=((int)elapsed+8);

                if(targetLag==0){
                    if(((int)(Math.random()*7))+1==7){
                        mLane.addEnemy();

                    }
                    targetLag+=mCanvasWidth/8;
                }

                if(mLane.getBulletCount()>0) {
                    mLane.incrementBulletPosition((int) elapsed + 8);
                    if (((mCanvasWidth / 8) * 7) - mLane.getBulletPosition(0) < 0) mLane.popBullet();
                }

                if(playerLag>0)playerLag-=((int)elapsed+8);

                mLastTime = now;
            }

            private void doDraw(Canvas canvas) {
                paint.setColor(Color.BLACK);
                paint.setStrokeWidth(0);
                canvas.drawRect(0,0,mCanvasWidth,mCanvasHeight,paint);
                paint.setColor(Color.RED);
                if(mLane.getPlayer()!=null)canvas.drawRect(0,mCanvasHeight/3* mLane.getLaneOffset(),mCanvasWidth/8,mCanvasHeight/3,paint);
                paint.setColor(Color.YELLOW);
                if(mLane.getEnemyCount()>0){

                    for(int i=0;i<mLane.getEnemyCount();i++)canvas.drawRect(((mCanvasWidth/8)*7)-mLane.getEnemyPosition(i),mCanvasHeight/3* mLane.getLaneOffset(),((mCanvasWidth/8)*8)-mLane.getEnemyPosition(i),mCanvasHeight/3* (mLane.getLaneOffset()+1),paint);
                }
                paint.setColor(Color.BLUE);
                if(mLane.getBulletCount()>0){
                    for(int i=0;i<mLane.getBulletCount();i++)canvas.drawRect((mCanvasWidth/8)+mLane.getBulletPosition(i),mCanvasHeight/3* mLane.getLaneOffset(), ((mCanvasWidth/8)*2)+mLane.getBulletPosition(i),mCanvasHeight/3* (mLane.getLaneOffset()+1),paint);
                }
            }

            @Override
            public void run() {
                while(mRun){
                    Canvas c=null;
                    try{
                        c=mSurfaceHolder.lockCanvas(null);
                        synchronized (mSurfaceHolder) {
                            updatePhysics();

                            synchronized (mRunLock) {
                                if (mRun) doDraw(c);
                            }
                        }
                    } finally{
                        if (c != null) {
                            mSurfaceHolder.unlockCanvasAndPost(c);
                        }
                    }
                }
            }
        }

            private ShooterThread ex;

            public MySurfaceView(Context context) {
                super(context);
                SurfaceHolder holder = this.getHolder();
                holder.addCallback(this);
                ex = new ShooterThread(holder);

            }

            @Override
            public boolean onTouchEvent(MotionEvent e) {
                ex.addBullet();

                return true;
            }

            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                ex.setRunning(true);
                ex.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
                ex.setSurfaceSize(i1, i2);
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                boolean retry = true;
                ex.setRunning(false);
                while (retry) {
                    try {
                        ex.join();
                        retry = false;
                    } catch (InterruptedException e) {
                    }
                }

            }
        }
    }
