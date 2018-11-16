package com.example.naxi.bulanci.GameObjects.Guns;

import com.example.naxi.bulanci.GameView;

public interface IGun {

    public boolean Shot(GameView gw, int positionX, int positionY, int moveX, int moveY);

    public void Update();

    public int GetShotCount();

    //Shot (GameView) - přidá do game viewu střeli které poletí po mapě - zároveň bude odečítat střeli atd...
    //Update - bude nahrazovat timer - mezi střelami nebo u hlavní zbraně main delay
    //GetShotCount

}
