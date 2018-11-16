package com.example.naxi.bulanci;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.naxi.bulanci.GameObjects.Bullet;
import com.example.naxi.bulanci.GameObjects.Player;

import java.util.ArrayList;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    GameLoop loop;

    public GameView(Context context)
    {
        super(context);
        Initialization();

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



    Player player;

    int ScreenHeight;
    int ScreenWidth;
    public float ScalingX;
    public float ScalingY;

    private void Initialization()
    {

        ScreenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        ScreenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

        ScalingX = ScreenWidth / 1100f;
        ScalingY = ScreenHeight / 640f;

        player = new Player(this);

    }

    public ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    public ArrayList<Bullet> destroybullets = new ArrayList<Bullet>();

    public void update()
    {
        player.Update();

        for(Bullet bullet : bullets)
        {
            bullet.Update();
            if ((bullet.positionX>1500)||(bullet.positionX<0)||(bullet.positionY>1500)||(bullet.positionY<0)) destroybullets.add(bullet);
        }

        for(Bullet bullet : destroybullets) bullets.remove(bullet);
        destroybullets.clear();

    }


    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);

        player.Draw(canvas);

        for(Bullet bullet : bullets) bullet.Draw(canvas);


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

                }
                else
                    player.Move(false);

                break;
            }
            case MotionEvent.ACTION_UP:
            {
                player.Move(false);
                break;
            }

        }
        return super.onTouchEvent(event);
    }
}
