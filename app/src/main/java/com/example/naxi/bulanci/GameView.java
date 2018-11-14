package com.example.naxi.bulanci;

import android.content.Context;
import android.view.View;

public class GameView extends View {

    public GameView(Context context)
    {
        super(context);
        Initialization();
    }

    GameLoop GameLoop;

    private void Initialization()
    {
        GameLoop = new GameLoop(this);

        Thread LoopThread = new Thread(GameLoop);
        LoopThread.start();

    }
}
