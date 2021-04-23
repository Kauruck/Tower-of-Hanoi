package com.kauruck.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.VolatileImage;

/**
 * This panel contains all necessary code to render an image to the screen
 * <br>
 * Use this with
 * @see com.kauruck.graphics.RenderThread
 */
public class RenderTargetPanel extends JPanel {
    //Framebuffers
    VolatileImage toDrawA;
    VolatileImage toDrawB;

    /**
     * From which buffer should be drawn
     */
    boolean isA = true;



    public RenderTargetPanel(){

    }

    /**
     * Sets the image for the unused framebuffer
     * @param toDraw the image that should be drawn next
     */
    public void setToDraw(VolatileImage toDraw){
        if(isA)
            toDrawB = toDraw;
        else
            toDrawA = toDraw;
        isA = !isA;
    }

    @Override
    protected void paintComponent(Graphics g) {
        //Just paint the image that currently is not written to
        if(isA)
            g.drawImage(toDrawA, 0,0,null);
        else
            g.drawImage(toDrawB, 0 ,0, null);
    }


}
