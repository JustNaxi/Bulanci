package com.example.naxi.bulanci;

import com.example.naxi.bulanci.GameObjects.Bonus;

import java.util.Random;

public class GameController
{
    private int Time;
    private int NextBonusTime;
    private GameView GameView;

    public GameController(GameView gw, int time)
    {
        GameView = gw;
        Time = time;
        NextBonusTime = new Random().nextInt(30*15)+30*5;
    }

    public void Update()
    {

        Time--;
        NextBonusTime--;


        if (NextBonusTime<=0)
        {
            NextBonusTime = new Random().nextInt(30*30)+30*5;
            GameView.Bonuses.add(new Bonus(GameView));

        }

        if (Time<=0)
        {
            GameView.EndGame();
        }
    }
}
