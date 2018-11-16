package com.example.naxi.bulanci.GameObjects;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.naxi.bulanci.GameView;
import com.example.naxi.bulanci.R;

public class Bullet
{
    public int positionX;
    public int positionY;

    private int moveX;
    private int moveY;

    private int skinCenterX = 2;
    private int skinCenterY = 2;

    public IEntity Creator;

    private GameView gameView;


    private Bitmap image;


    public Bullet(GameView gw ,int positionX, int positionY, int moveX, int moveY, IEntity creator)
    {
        Creator = creator;
        gameView = gw;
        this.positionX=positionX;
        this.positionY=positionY;

        this.moveX=moveX;
        this.moveY=moveY;


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        image =BitmapFactory.decodeResource(gw.getResources(), R.drawable.kulka, options);
    }


    public void Update()
    {
        positionX+=moveX;
        positionY+=moveY;
    }

    public void Draw(Canvas canvas)
    {
        canvas.drawBitmap(image, null, new Rect((int)((positionX-skinCenterX)*gameView.ScalingX),(int)((positionY-skinCenterY)*gameView.ScalingY),(int)(((positionX-skinCenterX)+image.getWidth())*gameView.ScalingX), (int)(((positionY-skinCenterY)+image.getHeight())*gameView.ScalingY)), null);
    }





}
