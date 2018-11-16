package com.example.naxi.bulanci.GameObjects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.naxi.bulanci.GameObjects.Guns.GunM4;
import com.example.naxi.bulanci.GameObjects.Guns.IGun;
import com.example.naxi.bulanci.GameView;
import com.example.naxi.bulanci.R;

import java.util.Random;

public class Bonus
{
    private GameView GameView;
    private int BonusType;
    private Bitmap Image;

    private int PositionX;
    private int PositionY;

    private int BonusCenterX = 31;
    private int BonusCenterY = 8;

    public Bonus(GameView gw)
    {
        GameView = gw;
        Respawn();

        Random rm = new Random();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        switch(rm.nextInt(2))
        {
            case 0: BonusType = 0; Image = BitmapFactory.decodeResource(gw.getResources(), R.drawable.shotgunbonus, options);  break;
            case 1: BonusType = 1; Image = BitmapFactory.decodeResource(gw.getResources(), R.drawable.m4bonus, options); break;
        }


    }

    private void Respawn()
    {
        Random rm = new Random();
        PositionX = rm.nextInt(GameView.GameWindowWidth-100)+50;
        PositionY = rm.nextInt(GameView.GameWindowHeight-100)+50;
    }


    public void Update()
    {
        CheckEntityCollision();
    }

    private void CheckEntityCollision()
    {
        boolean is = false;
        for(Enemy enemy : GameView.EnemyList)
        {
            if ((PositionX - BonusCenterX > enemy.PositionX + enemy.CollisionMask.left)&&(PositionX - BonusCenterX < enemy.PositionX + enemy.CollisionMask.right)&&(PositionY - BonusCenterY > enemy.PositionY + enemy.CollisionMask.top)&&(PositionY - BonusCenterY < enemy.PositionY + enemy.CollisionMask.bottom))
            {
                enemy.SetGun(BonusType);
                GameView.DestroyBonuses.remove(this);
                is = true;
                break;
            }
        }

        if (!is)
        {
            if ((PositionX - BonusCenterX > GameView.player.PositionX + GameView.player.CollisionMask.left)&&(PositionX - BonusCenterX < GameView.player.PositionX + GameView.player.CollisionMask.right)&&(PositionY - BonusCenterY > GameView.player.PositionY + GameView.player.CollisionMask.top)&&(PositionY - BonusCenterY < GameView.player.PositionY + GameView.player.CollisionMask.bottom))
            {
                GameView.player.SetGun(BonusType);
                GameView.DestroyBonuses.remove(this);
            }
        }
    }

    public void Draw(Canvas canvas)
    {
        canvas.drawBitmap(Image, null, new Rect((int)((PositionX-BonusCenterX)*GameView.ScalingX),(int)((PositionY-BonusCenterY)*GameView.ScalingY),(int)(((PositionX-BonusCenterX)+Image.getWidth())*GameView.ScalingX), (int)(((PositionY-BonusCenterY)+Image.getHeight())*GameView.ScalingY)), null);
    }



}
