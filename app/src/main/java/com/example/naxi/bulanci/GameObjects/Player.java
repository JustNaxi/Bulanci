package com.example.naxi.bulanci.GameObjects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;

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

    private int kills = 0;
    private int deaths = 0;
    private int positionX = 50;
    private int positionY = 50;

    private boolean moving = false;
    private int moveX = 0;
    private int moveY = 0;


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
        canvas.drawBitmap(image[imageDirection], null, new Rect(positionX, positionY,positionX+(int)(image[imageDirection].getWidth()*gameView.ScalingX), positionY+(int)(image[imageDirection].getHeight()*gameView.ScalingY)), null);


    }



}
