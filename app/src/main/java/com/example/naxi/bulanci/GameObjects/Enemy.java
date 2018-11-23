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

import com.example.naxi.bulanci.GameObjects.Guns.GunM4;
import com.example.naxi.bulanci.GameObjects.Guns.GunPistol;
import com.example.naxi.bulanci.GameObjects.Guns.GunShotgun;
import com.example.naxi.bulanci.GameObjects.Guns.IGun;
import com.example.naxi.bulanci.GameView;
import com.example.naxi.bulanci.R;

import java.util.Random;

public class Enemy implements IEntity
{
    private GameView GameView;

    public IGun Gun;

    private int Kills = 0;
    private int Deaths = 0;
    public int PositionX = 50;
    public int PositionY = 50;

    private boolean Shotting = false;
    private boolean Moving = false;
    private int MoveX = 1;
    private int MoveY = 0;
    private int Time = 0;
    private int RandomShotDelay = new Random().nextInt(30*5)+30*2;


    private int SkinCenterX = 32;
    private int SkinCenterY = 47;

    private Paint EnemyColor;

    public Rect CollisionMask = new Rect(-37,-20,37,20);


    private Bitmap[] Images;
    private int ImageDirection = 0;
    private int myGun;

    public Enemy(GameView gw)
    {
        GameView = gw;
        RandomGun();
        ChangeDirection();
        ChangeColor();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        Images = new Bitmap[4];
        Images[0] = BitmapFactory.decodeResource(gw.getResources(), R.drawable.bulanekdown, options);
        Images[1] = BitmapFactory.decodeResource(gw.getResources(), R.drawable.bulanekup, options);
        Images[2] = BitmapFactory.decodeResource(gw.getResources(), R.drawable.bulanekleft, options);
        Images[3] = BitmapFactory.decodeResource(gw.getResources(), R.drawable.bulanekright, options);

        Respawn();
    }

    private void RandomGun()
    {
        switch(new Random().nextInt(3))
        {
            case 0: Gun = new GunPistol(GameView, this); break;
            case 1: Gun = new GunM4(GameView, this); break;
            case 3: Gun = new GunShotgun(GameView, this); break;
            default : Gun = new GunShotgun(GameView, this);break;
        }
    }

    public void SetGun(int i)
    {
        myGun=i;
        switch(i)
        {
            case 0: Gun = new GunShotgun(GameView, this); break;
            case 1: Gun = new GunM4(GameView, this); break;
            default: Gun = new GunPistol(GameView, this); break;
        }
    }

    public void GiveKill()
    {
        Kills++;
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
        ChangeColor();
        Respawn();

    }

    private void Respawn()
    {
        Random rm = new Random();

        boolean isfree = false;
        while(!isfree)
        {
            PositionX = rm.nextInt(GameView.GameWindowWidth-100)+50;
            PositionY = rm.nextInt(GameView.GameWindowHeight-100)+50;
            isfree = true;

            for (Rect i : GameView.map.collisions)
            {
                if (
                        (PositionX - SkinCenterX < i.right) &&
                        (PositionX - SkinCenterX + Images[1].getWidth() > i.left) &&
                        (PositionY - SkinCenterY < i.bottom) &&
                        (PositionY - SkinCenterY + Images[1].getHeight() > i.top)
                )
                {
                    isfree=false;
                    break;
                }


            }
        }
    }


    public void Update()
    {
        if ((PositionX+MoveX*5>10) && (PositionX+MoveX*5<GameView.GameWindowWidth-10) && (PositionY+MoveY*5>10) && (PositionY+MoveY*5<GameView.GameWindowHeight-10))
        {
            PositionX += MoveX * 4;
            PositionY += MoveY * 4;

            for(Rect i : GameView.map.collisions)
            {
                if(
                        (PositionX-SkinCenterX<i.right)&&
                        (PositionX-SkinCenterX+Images[1].getWidth()>i.left)&&
                        (PositionY-SkinCenterY<i.bottom)&&
                        (PositionY-SkinCenterY+Images[1].getHeight()>i.top)
                )
                {
                    PositionX -= MoveX * 4;
                    PositionY -= MoveY * 4;
                    ChangeDirection();
                    break;
                }


            }

        }

        Gun.Update(PositionX, PositionY, MoveX, MoveY);
        if (RandomShotDelay>0) {RandomShotDelay--;} else
            {
                //if (Gun.Shot(PositionX, PositionY, MoveX, MoveY)) SetGun(myGun);
                Gun.Shot(PositionX, PositionY, MoveX, MoveY);
                RandomShotDelay=new Random().nextInt(30*5)+30*2;
            }
        if (Time>0) {Time--;} else {ChangeDirection();}

        CheckBulletCollision();

    }

    public void ChangeDirection()
    {
        Random rm = new Random();

        Time = rm.nextInt(40);

        switch(rm.nextInt(4))
        {
            case 0: MoveX = 1; MoveY = 0; ImageDirection = 3; break;
            case 1: MoveX = -1; MoveY = 0; ImageDirection = 2; break;
            case 2: MoveX = 0; MoveY = 1; ImageDirection = 0; break;
            case 3: MoveX = 0; MoveY = -1; ImageDirection = 1; break;
        }

    }

    private void ChangeColor()
    {
        EnemyColor = new Paint();
        Random rn = new Random(); //SRC_ATOP
        ColorFilter filter = new PorterDuffColorFilter(Color.argb(255,rn.nextInt(255),rn.nextInt(255),rn.nextInt(255)), PorterDuff.Mode.MULTIPLY);
        EnemyColor.setColorFilter(filter);
    }

    public void Draw(Canvas canvas)
    {
        canvas.drawBitmap(Images[ImageDirection], null, new Rect((int)((PositionX-SkinCenterX)*GameView.ScalingX),(int)((PositionY-SkinCenterY)*GameView.ScalingY),(int)(((PositionX-SkinCenterX)+Images[ImageDirection].getWidth())*GameView.ScalingX), (int)(((PositionY-SkinCenterY)+Images[ImageDirection].getHeight())*GameView.ScalingY)), EnemyColor);

        Gun.Draw(canvas, PositionX, PositionY, ImageDirection);
    }

}
