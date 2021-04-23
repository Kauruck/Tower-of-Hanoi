package com.kauruck.world;

import com.kauruck.graphics.RenderThread;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class World {

    private List<Layer> layers = new ArrayList<>();

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
}
