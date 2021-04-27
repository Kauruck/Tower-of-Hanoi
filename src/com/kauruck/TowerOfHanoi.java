package com.kauruck;

import com.kauruck.game.MainFrame;
import com.kauruck.graphics.RenderThread;
import com.kauruck.graphics.RenderThreadState;
import com.kauruck.graphics.sprites.BaseSprite;
import com.kauruck.util.BasicQueue;
import com.kauruck.world.Layer;
import com.kauruck.world.World;

import java.util.Queue;

public class TowerOfHanoi {

    /**
     * The main game window
     */
    public static MainFrame frame;

    /**
     * The main render thread for the game
     */
    public static RenderThread mainRenderThread;


    /**
     * The Main world in which all the action happens
     */
    public static World mainWorld;

    /**
     * The Layer where all the action hppens
     */
    public static Layer mainLayer;


    /**
     * Main Function
     * @param args Args form the command line
     */
    public static void main(String[] args){

        //Create the main game window with 500x500 and the title Tower of Hanoi
        frame = new MainFrame(500, 500, "Tower of Hanoi");
        //Show the main frame
        frame.setVisible(true);

        //Init render thread
        mainRenderThread = new RenderThread(frame.getPanel());
        //Start the thread
        mainRenderThread.createAndRun();

        //Create the world
        mainWorld = new World();

        //Create main Layer
        mainLayer = new Layer();
        mainWorld.push(mainLayer);

        //Add some tmp sprites
        mainLayer.push(new BaseSprite());

        //Show it in the main renderer
        mainRenderThread.setCurrentWorld(mainWorld);

    }

    /**
     * Function to stop the app
     */
    public static void stop(){
        System.out.println("Stopping the render thread");
        mainRenderThread.stop();
        int timeOut = 0;
        while (mainRenderThread.getState() != RenderThreadState.Stopped && timeOut < 500){
            try {
                Thread.sleep(1);
            }
            catch (InterruptedException e){
                timeOut = 500;
            }
            timeOut ++;
        }
        if(timeOut < 500)
            System.out.println("Render thread successfully stopped");
        else
            System.out.println("Something went wrong while stopping the render thread");
        System.out.println("We are exiting");
        System.exit(0);
    }
}
