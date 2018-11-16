package com.example.naxi.bulanci.GameObjects.Guns;

import android.graphics.Canvas;

import com.example.naxi.bulanci.GameView;

public interface IGun {

    public boolean Shot(int positionX, int positionY, int moveX, int moveY);

    public void Update(int positionX, int positionY, int moveX, int moveY);

    public int GetShotCount();

    public void Draw(Canvas canvas, int positionX , int positionY , int rotation);

    //Shot (GameView) - přidá do game viewu střeli které poletí po mapě - zároveň bude odečítat střeli atd...
    //Update - bude nahrazovat timer - mezi střelami nebo u hlavní zbraně main delay
    //GetShotCount

}
