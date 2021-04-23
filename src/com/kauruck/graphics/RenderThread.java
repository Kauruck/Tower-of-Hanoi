package com.kauruck.graphics;

import com.kauruck.exceptions.SingletonExitsException;
import com.kauruck.ui.RenderTargetPanel;
import com.kauruck.world.World;

import java.awt.*;
import java.awt.image.VolatileImage;

public class RenderThread implements Runnable{

    //The panel to render too
    private final RenderTargetPanel renderTargetPanel;

    //The state of the thread
    private RenderThreadState renderThreadState = RenderThreadState.Stopped;

    //Her comes the stuff for the fps
    float fps;

    private World currentWorld = null;

    /**
     * Handles rendering
     * @param renderTargetPanel The panel to render to
     */
    public RenderThread(RenderTargetPanel renderTargetPanel) {
        this.renderTargetPanel = renderTargetPanel;
    }

    /**
     * Stop the thread. It can not be restarted.
     * <br>
     * This does not immediately stop the thread.
     */
    public void stop(){
        renderThreadState =  RenderThreadState.Stopping;
    }

    /**
     * Weather the thread is running. This does not mean that all execution has stop. This could take some time more.
     * @return the state of running
     */
    public RenderThreadState getState() {
        return renderThreadState;
    }

    /**
     * Creates and starts a thread of this instance
     */
    public void createAndRun(){
        if(renderThreadState != RenderThreadState.Stopped)
            return;
        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * Get the FPS of this thread
     * @return The FPS
     */
    public float getFps() {
        return fps;
    }

    /**
     * The current world rendering
     * @return The current world
     */
    public World getCurrentWorld() {
        currentWorld.setParentRenderer(null);
        return currentWorld;
    }

    /**
     * Sets the world to render
     * @param currentWorld The world to render
     */
    public void setCurrentWorld(World currentWorld) {
        currentWorld.setParentRenderer(this);
        this.currentWorld = currentWorld;
    }

    @Override
    public void run() {

        //We are staring
        renderThreadState = RenderThreadState.Starting;
        //Setup
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd =  ge.getDefaultScreenDevice();
        VolatileImage volatileImage = gd.getDefaultConfiguration().createCompatibleVolatileImage(renderTargetPanel.getWidth(), renderTargetPanel.getHeight());

        //Setup done. We are running
        renderThreadState = RenderThreadState.Running;
        //Renderloop
        long startTime;
        while (renderThreadState == RenderThreadState.Running)
        {
            startTime = System.nanoTime();
            //Render only if we have a world
            if(currentWorld != null){
                //Check weather we need to recreate the image
                if(volatileImage == null){
                    volatileImage = gd.getDefaultConfiguration().createCompatibleVolatileImage(renderTargetPanel.getWidth(), renderTargetPanel.getHeight());
                }
                Graphics2D g = volatileImage.createGraphics();
                g.setColor(Color.BLACK);
                g.fillRect(0,0, volatileImage.getWidth(), volatileImage.getHeight());
                g.setColor(Color.WHITE);
                currentWorld.render(g);
                renderTargetPanel.setToDraw(volatileImage);
                renderTargetPanel.repaint();
            }
            long deltaTime = System.nanoTime() - startTime;
            fps = (float) (1e+9)/deltaTime;
        }

        //Stopping the thread
        renderThreadState = RenderThreadState.Stopping;
        //Cleanup goes her

        //Thread has stop
        renderThreadState = RenderThreadState.Stopped;
    }


}
