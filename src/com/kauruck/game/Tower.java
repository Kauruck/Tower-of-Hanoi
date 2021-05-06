package com.kauruck.game;

import com.kauruck.game.sprites.PlateSprite;

import java.util.Stack;
import java.util.stream.Stream;

public class Tower {

    private final Stack<PlateSprite> plateSprites = new Stack<>();
    private int x = 100;
    private int y = 100;

    public Tower(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean addPlate(PlateSprite plateSprite){
        if(canAddPlateSprite(plateSprite)) {
            plateSprite.setY(this.getY() - TowerLogic.calcHeight(plateSprites));
            plateSprite.setX(this.getX());
            plateSprites.push(plateSprite);
            return true;
        }
        return false;
    }

    public boolean canAddPlateSprite(PlateSprite plateSprite){
        if(plateSprites.isEmpty())
            return true;
        return  plateSprites.lastElement().getSize() >= plateSprite.getSize();
    }

    public PlateSprite getFirst(){
        return plateSprites.lastElement();
    }

    public PlateSprite removeFirst(){
        if(plateSprites.isEmpty())
            return null;
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

    public int getHeight(){
        if(plateSprites.isEmpty())
            return 0;
        return TowerLogic.calcHeight(plateSprites) + (int)(plateSprites.firstElement().getSize() * PlateSprite.ASPECT_RATIO * 0.5);
    }

    public int getWidth(){
        if(plateSprites.isEmpty())
            return 0;
        return plateSprites.firstElement().getSize() + (int)(plateSprites.firstElement().getSize() * PlateSprite.ASPECT_RATIO);
    }
}
