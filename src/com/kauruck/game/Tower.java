package com.kauruck.game;

import com.kauruck.game.sprites.PlateSprite;

import java.util.Stack;
import java.util.stream.Stream;

public class Tower {

    private final Stack<PlateSprite> plateSprites = new Stack<>();
    private final String name;
    private int x = 100;
    private int y = 100;

    public Tower(int x, int y) {
        this.x = x;
        this.y = y;
        name = "";
    }

    public Tower(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    @Override
    public String toString() {
        return name + ":" + plateSprites.size();
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

    public boolean isEmpty(){
        return plateSprites.isEmpty();
    }

    public boolean canAddPlateSprite(PlateSprite plateSprite){
        if(plateSprites.isEmpty())
            return true;
        return  plateSprites.lastElement().getSize() >= plateSprite.getSize();
    }

    public PlateSprite getFirst(){
        if(plateSprites.isEmpty())
            return null;
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
