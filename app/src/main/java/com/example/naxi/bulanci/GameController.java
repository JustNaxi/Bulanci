package com.example.naxi.bulanci;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.naxi.bulanci.GameObjects.Bonus;

import java.util.Random;

public class GameController
{
    private int Time;
    private int NextBonusTime;
    private GameView GameView;

    private Bitmap bar;

    public GameController(GameView gw, int time)
    {
        GameView = gw;
        Time = time;
        NextBonusTime = new Random().nextInt(30*15)+30*5;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        bar = BitmapFactory.decodeResource(gw.getResources(), R.drawable.bartime, options);

    }

    public void Update()
    {

        Time--;
        NextBonusTime--;


        if (NextBonusTime<=0)
        {
            NextBonusTime = new Random().nextInt(30*10)+30*5;
            GameView.Bonuses.add(new Bonus(GameView));

        }

        if (Time<=0)
        {
            GameView.EndGame();
        }
    }

    private int barPositionX = 550-45;
    private int barPositionY = 640-41-10;

    public void DrawGUI(Canvas canvas)
    {

        canvas.drawBitmap(bar, null, new Rect((int)((barPositionX)*GameView.ScalingX),(int)((barPositionY)*GameView.ScalingY),(int)(((barPositionX)+bar.getWidth())*GameView.ScalingX), (int)((barPositionY+bar.getHeight())*GameView.ScalingY)), null);


        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setTextSize(30);

        canvas.drawText((int)(Time/30)+"",(barPositionX+14)*GameView.ScalingX,(barPositionY+27)*GameView.ScalingY,paint);
    }
}
