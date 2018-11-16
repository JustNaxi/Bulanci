package com.example.naxi.bulanci.GameObjects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.support.annotation.NonNull;

import com.example.naxi.bulanci.GameObjects.Guns.GunM4;
import com.example.naxi.bulanci.GameObjects.Guns.GunPistol;
import com.example.naxi.bulanci.GameObjects.Guns.GunShotgun;
import com.example.naxi.bulanci.GameObjects.Guns.IGun;
import com.example.naxi.bulanci.GameView;
import com.example.naxi.bulanci.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class Player implements IEntity {

    GameView GameView;

    private IGun gun;

    private int Kills = 0;
    private int Deaths = 0;
    private int PositionX = 50;
    private int PositionY = 50;

    private boolean Shotting = false;
    private boolean Moving = false;
    private int MoveX = 1;
    private int MoveY = 0;


    private int SkinCenterX = 32;
    private int SkinCenterY = 47;

    private Rect CollisionMask = new Rect(-37,-20,37,20);


    private Bitmap[] Images;
    private int ImageDirection = 0;

    private Paint PlayerPaint;


    public Player(GameView gw)
    {
        GameView = gw;

        PlayerPaint = new Paint();
        ColorFilter filter = new PorterDuffColorFilter(Color.argb(100,255,0,0), PorterDuff.Mode.SRC_ATOP); //Mode MULTIPLY dělá částečnou neviditelnost
        PlayerPaint.setColorFilter(filter);

        gun = new GunShotgun(gw, this);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        Images = new Bitmap[4];
        Images[0] = BitmapFactory.decodeResource(gw.getResources(), R.drawable.bulanekdown, options);
        Images[1] = BitmapFactory.decodeResource(gw.getResources(), R.drawable.bulanekup, options);
        Images[2] = BitmapFactory.decodeResource(gw.getResources(), R.drawable.bulanekleft, options);
        Images[3] = BitmapFactory.decodeResource(gw.getResources(), R.drawable.bulanekright, options);
    }


    public void Update()
    {
        if (Moving)
        {
            if ((PositionX+MoveX*5>10) && (PositionX+MoveX*5<GameView.GameWindowWidth-10) && (PositionY+MoveY*5>10) && (PositionY+MoveY*5<GameView.GameWindowHeight-10))
            {
                PositionX += MoveX * 5;
                PositionY += MoveY * 5;
            }
        }

        gun.Update(PositionX, PositionY, MoveX, MoveY);
        if (Shotting) {gun.Shot(PositionX, PositionY, MoveX, MoveY);}

        CheckBulletCollision();
    }

    private void CheckBulletCollision()
    {
        for(Bullet bullet : GameView.bullets)
        {
            if (bullet.Creator!=this && bullet.positionX>PositionX+CollisionMask.left && bullet.positionX<PositionX+CollisionMask.right && bullet.positionY>PositionY+CollisionMask.top && bullet.positionY<PositionY+CollisionMask.bottom)
            {
                KillMe();
                GameView.destroybullets.add(bullet);
                break;
            }
        }
    }

    private void KillMe()
    {
        Deaths++;
        Respawn();

    }

    public void GiveKill()
    {
        Kills++;
    }

    private void Respawn()
    {
        Random rm = new Random();
        PositionX = rm.nextInt(GameView.GameWindowWidth-100)+50;
        PositionY = rm.nextInt(GameView.GameWindowHeight-100)+50;
    }


    public void SetDirection(int x, int y)
    {
        MoveX = x;
        MoveY = y;

        if (x==1) ImageDirection = 3; else
        if (x==-1) ImageDirection = 2; else
        if (y==1) ImageDirection = 0; else
        if (y==-1) ImageDirection = 1;
    }

    public void Move(boolean is)
    {
        Moving = is;
    }


    public void Shot(boolean shotting)
    {
        this.Shotting = shotting;
    }

    public void Draw(Canvas canvas)
    {


        canvas.drawBitmap(Images[ImageDirection], null, new Rect((int)((PositionX-SkinCenterX)*GameView.ScalingX),(int)((PositionY-SkinCenterY)*GameView.ScalingY),(int)(((PositionX-SkinCenterX)+Images[ImageDirection].getWidth())*GameView.ScalingX), (int)(((PositionY-SkinCenterY)+Images[ImageDirection].getHeight())*GameView.ScalingY)), PlayerPaint);

        gun.Draw(canvas, PositionX, PositionY, ImageDirection);

        Paint border = new Paint();
        border.setColor(Color.WHITE);
        border.setStrokeWidth(5);
        border.setStyle(Paint.Style.STROKE);
        //canvas.drawRect(new Rect((int)(positionX*gameView.ScalingX),(int)(positionY*gameView.ScalingY),(int)((positionX+image[imageDirection].getWidth())*gameView.ScalingX), (int)((positionY+image[imageDirection].getHeight())*gameView.ScalingY)),border);

    }



}
