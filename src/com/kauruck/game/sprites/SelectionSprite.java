package com.kauruck.game.sprites;

import com.kauruck.game.Tower;
import com.kauruck.graphics.sprites.Sprite;

import java.awt.*;

public class SelectionSprite extends Sprite {

    private Tower target;
    private int width = 0;
    private int height = 0;
    private final Color color;
    private boolean hidden = false;

    public SelectionSprite(Color color) {
        this.color = color;
    }

    public static final int PADDING = 20;

    public void moveTo(Tower target){
        if(target == null){
            hidden = true;
            this.target = null;
            return;
        }
        hidden = false;

        this.target = target;
        this.setX(target.getX()+ PADDING/2);
        this.setY(target.getY() - target.getHeight() + PADDING);
        height = target.getHeight()+ PADDING/2;
        width = target.getWidth()+ PADDING/2;
    }

    public Tower getTarget() {
        return target;
    }

    @Override
    public void draw(Graphics2D g) {
        if(!hidden) {
            g.setColor(color);
            g.drawRect(this.getX() - this.width / 2 - PADDING / 2, this.getY() - PADDING / 2, width, height);
        }

    }
}
