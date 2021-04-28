package com.kauruck.game.sprites;

import com.kauruck.graphics.sprites.Sprite;
import com.kauruck.update.ICanReceiveTick;

import java.awt.*;

public class PlateSprite extends Sprite implements ICanReceiveTick {

    private final int size;
    private final Color color;
    public static final float ASPECT_RATIO = 0.25f;

    public PlateSprite(int size, Color drawColor){
        this.size = size;
        this.color = drawColor;
        this.setX(100);
        this.setY(100);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        int d = (int)(this.size * PlateSprite.ASPECT_RATIO);
        //Main body
        g.fillRect(this.getX() - (this.size / 2), this.getY() - (int)(this.size * PlateSprite.ASPECT_RATIO / 2), this.size, d);
        //Right cap
        g.fillOval(this.getX() + (this.size / 2) - d/2,this.getY() - (int)(this.size * PlateSprite.ASPECT_RATIO / 2), d, d);
        //Left cap
        g.fillOval(this.getX() - (this.size / 2) - d/2,this.getY() - (int)(this.size * PlateSprite.ASPECT_RATIO / 2), d, d);


        //This shows the center in red
        //g.setColor(Color.RED);
        //g.fillRect(this.getX() - 5, this.getY() - 5, 10,10);
    }

    public int getSize() {
        return size;
    }

    @Override
    public void update(float deltaTime) {
        //this.setY((int) (this.getY() + 0.05 * deltaTime));
    }
}
