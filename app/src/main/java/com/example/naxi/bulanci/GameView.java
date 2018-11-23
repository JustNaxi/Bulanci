package com.example.naxi.bulanci;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.naxi.bulanci.GameObjects.Bonus;
import com.example.naxi.bulanci.GameObjects.Bullet;
import com.example.naxi.bulanci.GameObjects.Enemy;
import com.example.naxi.bulanci.GameObjects.MyMap;
import com.example.naxi.bulanci.GameObjects.Player;


import java.util.ArrayList;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    GameLoop loop;
    Sensor proximitySensor;
    GameActivity GameActivity;
    GameController GameController;

    public MyMap map;
    public Player player;

    public int ScreenHeight;
    public int ScreenWidth;
    public int GameWindowHeight = 640;
    public int GameWindowWidth = 1100;
    public float ScalingX;
    public float ScalingY;


    private Bitmap point;
    private boolean touch = false;

    public GameView(GameActivity ga)
    {
        super(ga);
        Initialization();



        Bundle pack = ga.getIntent().getExtras();

        map = new MyMap(pack.getString("mapName"), this);

        GameActivity=ga;
        GameController = new GameController(this, pack.getInt("time",20)*30);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;


        point = BitmapFactory.decodeResource(this.getResources(), R.drawable.point, options);



        SensorManager sm = (SensorManager)ga.getSystemService(Context.SENSOR_SERVICE);
        proximitySensor = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sm.registerListener(sel,proximitySensor,SensorManager.SENSOR_DELAY_NORMAL);


        for (int i = 0; i<pack.getInt("enemyCount",3);i++)
            EnemyList.add(new Enemy(this));

        player = new Player(this, pack.getInt("colorR", 0),pack.getInt("colorG", 0),pack.getInt("colorB", 0));





        getHolder().addCallback(this);

        loop = new GameLoop(getHolder(),this);

        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        loop.running = true;
        loop.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
            boolean retry = true;
            while (retry)
            {
                try {
                    loop.running = false;
                    loop.join();
                }catch(InterruptedException e)
                {

                }
                retry = false;

            }
    }


    private void Initialization()
    {

        ScreenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        ScreenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

        ScalingX = ScreenWidth / 1100f;
        ScalingY = ScreenHeight / 640f;


    }

    public ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    public ArrayList<Bullet> destroybullets = new ArrayList<Bullet>();

    public ArrayList<Bonus> Bonuses = new ArrayList<Bonus>();
    public ArrayList<Bonus> DestroyBonuses = new ArrayList<Bonus>();

    public ArrayList<Enemy> EnemyList = new ArrayList<Enemy>();

    public void update()
    {


        player.Update();

        for(Enemy enemy : EnemyList) enemy.Update();

        for(Bonus bonus : Bonuses) bonus.Update();
        for(Bonus bonus : DestroyBonuses) Bonuses.remove(bonus);

        for(Bullet bullet : bullets)
        {
            bullet.Update();
            if ((bullet.positionX>1500)||(bullet.positionX<0)||(bullet.positionY>1500)||(bullet.positionY<0)) destroybullets.add(bullet);
        }

        for(Bullet bullet : destroybullets) bullets.remove(bullet);

        GameController.Update();

        destroybullets.clear();

    }


    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);

        map.DrawBackground(canvas);
        player.Draw(canvas);

        for(Enemy enemy : EnemyList) enemy.Draw(canvas);
        for(Bullet bullet : bullets) bullet.Draw(canvas);
        for(Bonus bonus : Bonuses) bonus.Draw(canvas);


        map.DrawForeground(canvas);

        if (touch)
            canvas.drawBitmap(point, null, new Rect((int)(xDown),(int)(yDown),(int)(xDown+20), (int)(yDown+20)), null);



        /*
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setTextSize(25);

        canvas.drawText(ScalingX+"  "+ScalingY,100,100,paint);
        */
    }



    //====================================================



    float xDown;
    float yDown;

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {

        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            {
                xDown = event.getX();
                yDown = event.getY();

                //Toast.makeText(GameActivity,xDown+"  "+yDown, Toast.LENGTH_LONG).show();

                return true;
            }
            case MotionEvent.ACTION_MOVE:
            {

                float distanceX = xDown - event.getX();
                float distanceY = yDown - event.getY();

                if (((distanceX*distanceX)>10000)||((distanceY*distanceY)>10000))
                {
                    if ((distanceX * distanceX) > (distanceY * distanceY)) {
                        if (distanceX > 0)
                            player.SetDirection(-1, 0);
                        else
                            player.SetDirection(1, 0);
                    } else {
                        if (distanceY > 0)
                            player.SetDirection(0, -1);
                        else
                            player.SetDirection(0, 1);
                    }
                    player.Move(true);
                    touch = true;

                }
                else
                    player.Move(false);
                    touch=true;

                break;
            }
            case MotionEvent.ACTION_UP:
            {
                player.Move(false);
                touch=false;
                break;
            }

        }
        return super.onTouchEvent(event);
    }


    public void EndGame()
    {
        GameActivity.finish();
    }





    SensorEventListener sel = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event)
        {
            if (event.sensor.getType()==Sensor.TYPE_PROXIMITY)
            {
                if (event.values[0]>=-4 && event.values[0]<=4)
                {
                    player.Shot(true);
                }
                else
                {
                    player.Shot(false);
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}
