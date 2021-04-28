package com.kauruck.game;

import com.kauruck.game.sprites.PlateSprite;

import java.util.Stack;
import java.util.stream.Stream;

public class Tower {

    private Stack<PlateSprite> plateSprites = new Stack<>();
    private int x = 100;
    private int y = 100;

    public Tower(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void addPlate(PlateSprite plateSprite){
        if(canAddPlateSprite(plateSprite)) {
            plateSprite.setY(TowelLogic.calcHeight(plateSprites));
            plateSprite.setX(this.getX());
            plateSprites.add(plateSprite);
        }
    }

    public boolean canAddPlateSprite(PlateSprite plateSprite){
        if(plateSprites.isEmpty())
            return true;
        return  plateSprites.lastElement().getSize() <= plateSprite.getSize();
    }

    public PlateSprite getFirst(){
        return plateSprites.firstElement();
    }

    public PlateSprite removeFirst(){
        return plateSprites.pop();
    }

    public Stream<PlateSprite> getStream(){
        return plateSprites.stream();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
