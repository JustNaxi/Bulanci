package com.example.naxi.bulanci;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.Toast;

public class GameLoop extends Thread {

    GameView GameView;
    SurfaceHolder surfaceHolder;
    Canvas canvas;
    public boolean running = true;

    public GameLoop(SurfaceHolder holder, GameView gw)
    {
        super();
        GameView = gw;
        surfaceHolder = holder;
    }

    @Override
    public void run()
    {
        long loopTime = 1000/30;

        while(running)
        {
            long startTime = System.nanoTime();
            canvas = null;
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    Log.d("screen", canvas.getHeight()+" "+canvas.getWidth());
                    this.GameView.update();
                    this.GameView.draw(canvas);
                }
            }catch(Exception e)
            {

            }finally
            {
                if (canvas!=null)
                {
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);

                    }catch(Exception e)
                    {

                    }
                }
            }




            long endTime = (System.nanoTime()-startTime)/1000000;

            long waitTime = loopTime - endTime;

            try {
                this.sleep(waitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

    }

}
