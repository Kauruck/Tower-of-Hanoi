package com.kauruck.ui;

import com.kauruck.graphics.RenderThread;
import com.kauruck.util.GraphicsUtils;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.VolatileImage;

/**
 * This panel contains all necessary code to render an image to the screen
 * <br>
 * Use this with
 * @see com.kauruck.graphics.RenderThread
 */
public class RenderTargetPanel extends JPanel {
    //Frame
    VolatileImage toDraw;

    //Thread that renders to this panel
    RenderThread renderThread;

    //For detecting moving across screens
    String screenID = "1";


    /**
     * This is a panel a render thread can render to. The parent needs to be a jFrame
     */
    public RenderTargetPanel(){
        this.addComponentListener(new ComponentAdapter() {
            //This one forces a rebuild on the graphics thread when the size changed, so that it will also update in the image.
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                if(renderThread != null){
                    renderThread.rebuildImage();
                }
            }

        });

        this.addAncestorListener(new AncestorListener() {
            @Override
            public void ancestorAdded(AncestorEvent event) {

            }

            @Override
            public void ancestorRemoved(AncestorEvent event) {

            }

            //For detecting when we moved across a screen
            @Override
            public void ancestorMoved(AncestorEvent event) {
                if(!screenID.equals(getGraphicsConfiguration().getDevice().getIDstring())) {
                    if(renderThread != null)
                        renderThread.rebuildImage();
                    screenID = getGraphicsConfiguration().getDevice().getIDstring();
                }
            }
        });

    }

    public RenderThread getRenderThread() {
        return renderThread;
    }

    public void setRenderThread(RenderThread renderThread) {
        this.renderThread = renderThread;
    }

    /**
     * Sets the image for the frame
     * @param toDraw the image that should be drawn next
     */
    public void setToDraw(VolatileImage toDraw){
        this.toDraw = toDraw;
    }

    @Override
    protected void paintComponent(Graphics g) {
        //Just paint the image
        g.drawImage(toDraw, 0 ,0, null);
    }


}
