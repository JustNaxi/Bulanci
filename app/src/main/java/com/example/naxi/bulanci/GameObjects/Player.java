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

import com.example.naxi.bulanci.GameObjects.Guns.GunPistol;
import com.example.naxi.bulanci.GameObjects.Guns.IGun;
import com.example.naxi.bulanci.GameView;
import com.example.naxi.bulanci.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Player {

    GameView gameView;

    private IGun gun = new GunPistol();

    private int kills = 0;
    private int deaths = 0;
    private int positionX = 50;
    private int positionY = 50;

    private boolean moving = false;
    private int moveX = 1;
    private int moveY = 0;


    private int skinCenterX = 32;
    private int skinCenterY = 47;

    private Rect CollisionMask = new Rect(-37,-20,37,20);


    Bitmap[] image;
    int imageDirection = 0;



    private ArrayList<IGun> gunList = new ArrayList<IGun>();

    public Player(GameView gw)
    {
        gameView = gw;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        image = new Bitmap[4];
        image[0] = BitmapFactory.decodeResource(gw.getResources(), R.drawable.bulanekdown, options);
        image[1] = BitmapFactory.decodeResource(gw.getResources(), R.drawable.bulanekup, options);
        image[2] = BitmapFactory.decodeResource(gw.getResources(), R.drawable.bulanekleft, options);
        image[3] = BitmapFactory.decodeResource(gw.getResources(), R.drawable.bulanekright, options);
    }


    public void Update()
    {
        if (moving)
        {
            positionX += moveX*5;
            positionY += moveY*5;
        }

        gun.Update();
        gun.Shot(gameView,positionX, positionY,moveX, moveY);
    }


    public void SetDirection(int x, int y)
    {
        moveX = x;
        moveY = y;

        if (x==1) imageDirection = 3; else
        if (x==-1) imageDirection = 2; else
        if (y==1) imageDirection = 0; else
        if (y==-1) imageDirection = 1;
    }

    public void Move(boolean is)
    {
        moving = is;
    }

    public void Draw(Canvas canvas)
    {
        Paint paint = new Paint();
        ColorFilter filter = new PorterDuffColorFilter(Color.argb(100,255,255,255), PorterDuff.Mode.SRC_ATOP);
        paint.setColorFilter(filter);

        canvas.drawBitmap(image[imageDirection], null, new Rect((int)((positionX-skinCenterX)*gameView.ScalingX),(int)((positionY-skinCenterY)*gameView.ScalingY),(int)(((positionX-skinCenterX)+image[imageDirection].getWidth())*gameView.ScalingX), (int)(((positionY-skinCenterY)+image[imageDirection].getHeight())*gameView.ScalingY)), paint);


        Paint border = new Paint();
        border.setColor(Color.WHITE);
        border.setStrokeWidth(5);
        border.setStyle(Paint.Style.STROKE);
        //canvas.drawRect(new Rect((int)(positionX*gameView.ScalingX),(int)(positionY*gameView.ScalingY),(int)((positionX+image[imageDirection].getWidth())*gameView.ScalingX), (int)((positionY+image[imageDirection].getHeight())*gameView.ScalingY)),border);

    }



}
