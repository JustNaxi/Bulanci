package com.example.naxi.bulanci.GameObjects.Guns;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.MediaPlayer;

import com.example.naxi.bulanci.GameObjects.Bullet;
import com.example.naxi.bulanci.GameObjects.IEntity;
import com.example.naxi.bulanci.GameView;
import com.example.naxi.bulanci.MainActivity;
import com.example.naxi.bulanci.R;

public class GunPistol implements IGun
{
    private int MaxShots = 4;
    private int Shots =  MaxShots;
    private int Delay = 0;

    private int Speed = 10;
    private GameView GameView;

    private boolean IsShots = true;
    private IEntity Holder;

    private Bitmap[] Images;

    private int[] CenterX;
    private int[] CenterY;

    public GunPistol(GameView gw, IEntity holder)
    {
        GameView = gw;
        Holder = holder;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Images = new Bitmap[4];
        CenterX = new int[4];
        CenterY = new int[4];
        Images[0] = BitmapFactory.decodeResource(gw.getResources(), R.drawable.gundown, options);
        CenterX[0] = -17; CenterY[0] = 0;
        Images[1] = BitmapFactory.decodeResource(gw.getResources(), R.drawable.gunup, options);
        CenterX[1] = 25; CenterY[1] = 25;
        Images[2] = BitmapFactory.decodeResource(gw.getResources(), R.drawable.gunleft, options);
        CenterX[2] = 20; CenterY[2] = 0;
        Images[3] = BitmapFactory.decodeResource(gw.getResources(), R.drawable.gunright, options);
        CenterX[3] = 0; CenterY[3] = 0;


    }

    public boolean Shot( int positionX, int positionY, int moveX, int moveY)
    {
        if ((Delay<1)&&(IsShots==false)) {Shots = MaxShots; IsShots = true;}
        if ((Delay<1))
        {
            Shots--;
            Delay = 20;
            GameView.bullets.add(new Bullet(GameView, positionX, positionY, moveX*Speed, moveY*Speed, Holder));


            GameView.SoundPool.play(GameView.gunSounds[0],0,0.03f,0,0,1);
        }

        if ((Shots==0)&& IsShots) {Delay = 30*10; IsShots=false; }

        return false;
    }

    public void Update( int positionX, int positionY, int moveX, int moveY)
    {
        if (Delay>0) Delay--;
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
