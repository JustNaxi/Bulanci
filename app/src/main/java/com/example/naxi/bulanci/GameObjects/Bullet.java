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

    private GameView GameView;


    private Bitmap image;


    public Bullet(GameView gw ,int positionX, int positionY, int moveX, int moveY, IEntity creator)
    {
        Creator = creator;
        GameView = gw;
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

        for (Rect i : GameView.map.collisions)
        {
            if ((positionX>i.left)&&(positionX<i.right)&&(positionY>i.top)&&(positionY<i.bottom))
            {
                GameView.destroybullets.add(this);
                break;
            }


        }
    }

    public void Draw(Canvas canvas)
    {
        canvas.drawBitmap(image, null, new Rect((int)((positionX-skinCenterX)*GameView.ScalingX),(int)((positionY-skinCenterY)*GameView.ScalingY),(int)(((positionX-skinCenterX)+image.getWidth())*GameView.ScalingX), (int)(((positionY-skinCenterY)+image.getHeight())*GameView.ScalingY)), null);
    }





}
