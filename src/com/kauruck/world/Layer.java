package com.kauruck.world;

import com.kauruck.graphics.sprites.Sprite;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Layer {

    private List<Sprite> toRender = new ArrayList<>();

    public void push(Sprite sprite){
        toRender.add(sprite);
    }

    public void pop(Sprite sprite){
        toRender.remove(sprite);
    }

    public void render(Graphics2D g){
        toRender.forEach(current -> current.draw(g));
    }

    public Collection<Sprite> getSprites(){
        return toRender;
    }
}
