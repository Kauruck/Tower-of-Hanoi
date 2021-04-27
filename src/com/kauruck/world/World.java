package com.kauruck.world;

import com.kauruck.graphics.RenderThread;
import com.kauruck.util.BasicQueue;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class World {

    Queue<Layer> layers = new BasicQueue<>();

    RenderThread parentRenderer;

    public RenderThread getParentRenderer() {
        return parentRenderer;
    }

    public void setParentRenderer(RenderThread parentRenderer) {
        this.parentRenderer = parentRenderer;
    }

    public void render(Graphics2D g){
        //TODO Add option to disable
        g.drawString("FPS: " + parentRenderer.getFps(), 20, 20);
        //Render all the layers
        layers.forEach(current -> current.render(g));
    }

    public void push(Layer layer){
        layers.add(layer);
    }

    public void pop(Layer layer){
        layers.remove(layer);
    }
}
