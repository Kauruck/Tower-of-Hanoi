package com.kauruck.update;

import com.kauruck.graphics.sprites.Sprite;
import com.kauruck.util.ThreadState;
import com.kauruck.world.Layer;

import java.util.concurrent.TimeUnit;

public class UpdateThread implements Runnable{

    //The currently running thread
    private Thread currentThread;

    //The state of the thread
    private ThreadState threadState = ThreadState.Stopped;

    //The layer which should be updated
    private Layer targetLayer;

    //FPS stuff
    float fps;
    long targetTime;

    public UpdateThread(Layer target){
        this.targetLayer = target;
        this.setMaxFPS(20);
    }


    public void createAndRun(){
        if(threadState != ThreadState.Stopped)
            return;
        currentThread = new Thread(this);
        currentThread.start();
    }

    public void stop(){
        this.threadState = ThreadState.Stopped;
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

    public ThreadState getState() {
        return threadState;
    }


    @Override
    public void run() {
        threadState = ThreadState.Starting;
        //Startup code goes here
        long deltaTimeNano = 0;
        float deltaTimeSecond = 0;
        long frameStartTime;
        //Now we are running
        threadState = ThreadState.Running;
        while (threadState == ThreadState.Running){
            frameStartTime = System.nanoTime();
            for(Sprite current : targetLayer.getSprites()){
                if(current instanceof ICanReceiveTick){
                    ((ICanReceiveTick)current).update(deltaTimeSecond);
                }
            }

            //FPS limiter
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
            //Update deltaTime
            deltaTimeNano = System.nanoTime() - frameStartTime;
            deltaTimeSecond = TimeUnit.MILLISECONDS.convert(deltaTimeNano, TimeUnit.NANOSECONDS);
        }
        threadState = ThreadState.Stopping;
        //Now we are closing

        //We are off
        threadState = ThreadState.Stopped;
    }
}
