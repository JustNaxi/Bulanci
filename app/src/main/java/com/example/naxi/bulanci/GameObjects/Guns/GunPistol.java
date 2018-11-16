package com.example.naxi.bulanci.GameObjects.Guns;

import com.example.naxi.bulanci.GameObjects.Bullet;
import com.example.naxi.bulanci.GameView;

public class GunPistol implements IGun
{
    private int maxShots = 4;
    private int shots =  maxShots;
    private int delay = 0;

    private int speed = 10;

    private boolean isShots = true;

    public boolean Shot(GameView gw, int positionX, int positionY, int moveX, int moveY)
    {
        if ((delay<1)&&(isShots==false)) {shots = maxShots; isShots = true;}
        if ((delay<1)) {shots--; delay = 20; gw.bullets.add(new Bullet(gw, positionX, positionY, moveX*speed, moveY*speed));}

        if ((shots==0)&& isShots) {delay = 30*10; isShots=false; }

        return false;
    }

    public void Update()
    {
        if (delay>0) delay--;
    }

    public int GetShotCount()
    {
        return shots;
    }
}
