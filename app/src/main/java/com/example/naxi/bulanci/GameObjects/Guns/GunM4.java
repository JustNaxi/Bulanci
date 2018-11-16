 package com.example.naxi.bulanci.GameObjects.Guns;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.naxi.bulanci.GameObjects.Bullet;
import com.example.naxi.bulanci.GameObjects.IEntity;
import com.example.naxi.bulanci.GameView;
import com.example.naxi.bulanci.R;

public class GunM4 implements IGun
{
    private int MaxShots = 4;
    private int Shots =  MaxShots;
    private int Delay = 0;

    private int Speed = 10;
    private int Load =0;
    private GameView GameView;
    private IEntity Holder;

    private Bitmap[] Images;

    private int[] CenterX;
    private int[] CenterY;

    public GunM4(GameView gw, IEntity holder)
    {
        GameView = gw;
        Holder = holder;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;


        Images = new Bitmap[4];
        CenterX = new int[4];
        CenterY = new int[4];
        Images[0] = BitmapFactory.decodeResource(gw.getResources(), R.drawable.m4down, options);
        CenterX[0] = -17; CenterY[0] = 0;
        Images[1] = BitmapFactory.decodeResource(gw.getResources(), R.drawable.m4up, options);
        CenterX[1] = 30; CenterY[1] = 50;
        Images[2] = BitmapFactory.decodeResource(gw.getResources(), R.drawable.m4left, options);
        CenterX[2] = 60; CenterY[2] = 0;
        Images[3] = BitmapFactory.decodeResource(gw.getResources(), R.drawable.m4right, options);
        CenterX[3] = 0; CenterY[3] = 0;


    }

    public boolean Shot(int positionX, int positionY, int moveX, int moveY)
    {

        if (Delay<1) {Shots--; Delay = 5; Load = 3; GameView.bullets.add(new Bullet(GameView, positionX, positionY, moveX*Speed, moveY*Speed, Holder));}

        if (Shots<=0) return true;

        return false;
    }

    public void Update(int positionX, int positionY, int moveX, int moveY)
    {

        if (Delay>0) Delay--;

        if ((Load>0)&&(Delay<1)) {Load--; Delay = 5; if (Load==0) Delay = 15; GameView.bullets.add(new Bullet(GameView, positionX, positionY, moveX*Speed, moveY*Speed, Holder)); }
    }

    public int GetShotCount()
    {
        return Shots;
    }

    public void Draw(Canvas canvas, int positionX , int positionY , int rotation)
    {
        canvas.drawBitmap(Images[rotation], null, new Rect((int)((positionX-CenterX[rotation])*GameView.ScalingX),(int)((positionY-CenterY[rotation])*GameView.ScalingY),(int)(((positionX-CenterX[rotation])+Images[rotation].getWidth())*GameView.ScalingX), (int)(((positionY-CenterY[rotation])+Images[rotation].getHeight())*GameView.ScalingY)), null);
    }
    }

