package com.kauruck.graphics;

import com.kauruck.exceptions.SingletonExitsException;
import com.kauruck.ui.RenderTargetPanel;
import com.kauruck.world.World;

import java.awt.*;
import java.awt.image.VolatileImage;
import java.util.concurrent.TimeUnit;

public class RenderThread implements Runnable{

    //The panel to render too
    private final RenderTargetPanel renderTargetPanel;

    //The state of the thread
    private RenderThreadState renderThreadState = RenderThreadState.Stopped;

    //Her comes the stuff for the fps
    float fps;
    long targetTime;

    //The currently active thread
    Thread currentThread = null;

    //Weather the image should be rebuild
    boolean rebuild = false;

    //The current graphics device
    GraphicsDevice gd;


    private World currentWorld = null;

    /**
     * Handles rendering
     * @param renderTargetPanel The panel to render to
     */
    public RenderThread(RenderTargetPanel renderTargetPanel) {
        this.renderTargetPanel = renderTargetPanel;
        renderTargetPanel.setRenderThread(this);
        this.setMaxFPS(60);
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
        currentThread = new Thread(this);
        currentThread.start();
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

    /**
     * Sets the maximal framerate of the renderer. The actual will be lower.
     * @param maxFPS The maximal framerate
     */
    public void setMaxFPS(int maxFPS){
        this.targetTime = ((long) 1e9)/(maxFPS);
    }

    /**
     * Gets the max framerate of the render. This may differ form the set one.
     * This will be lower then the actual one.
     * @return The maximal framerate
     */
    public int getMaxFPS(){
        return (int) ((targetTime / ((long) 1e9)));
    }

    /**
     * Forces the image to be rebuild.
     * Uses this when the size changes.
     */
    public void rebuildImage(){
        rebuild = true;
        gd = renderTargetPanel.getGraphicsConfiguration().getDevice();
    }

    @Override
    public void run() {

        //We are staring
        renderThreadState = RenderThreadState.Starting;
        //Setup
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        gd =  ge.getDefaultScreenDevice();
        VolatileImage volatileImage = gd.getDefaultConfiguration().createCompatibleVolatileImage(renderTargetPanel.getWidth(), renderTargetPanel.getHeight());


        //Setup done. We are running
        renderThreadState = RenderThreadState.Running;
        //Renderloop
        long frameStartTime;
        while (renderThreadState == RenderThreadState.Running) {
            frameStartTime = System.nanoTime();
            //Rendering the current world
            if (currentWorld != null) {
                //Check weather we need to recreate the image
                if (volatileImage == null || rebuild) {
                    volatileImage = gd.getDefaultConfiguration().createCompatibleVolatileImage(renderTargetPanel.getWidth(), renderTargetPanel.getHeight());
                }
                Graphics2D g = volatileImage.createGraphics();
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, volatileImage.getWidth(), volatileImage.getHeight());
                g.setColor(Color.WHITE);
                currentWorld.render(g);
                renderTargetPanel.setToDraw(volatileImage);
            }
            renderTargetPanel.repaint();
            long currentTime = System.nanoTime();
            long frameDeltaTime = currentTime - frameStartTime;

            if(frameDeltaTime == 0){
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    this.stop();
                    return;
                }
            }else {
                long waitTime = targetTime - frameDeltaTime;
                if (waitTime > 0) {
                    long delayMS = 0;
                    if (waitTime > 999999) {
                        delayMS = waitTime / 1000000;
                        waitTime = waitTime % 1000000;
                    }

                    if (waitTime > 0) {
                        try {
                            Thread.sleep(delayMS, (int) waitTime);
                        } catch (InterruptedException e) {
                            this.stop();
                            return;
                        }
                    }
                }
            }


            fps = (float) ((1e9)/(System.nanoTime() - frameStartTime));
        }


        //Stopping the thread
        renderThreadState = RenderThreadState.Stopping;
        //Cleanup goes her

        //Thread has stop
        renderThreadState = RenderThreadState.Stopped;
    }


}
