package com.kauruck;

import com.kauruck.game.MainFrame;
import com.kauruck.game.TowelLogic;
import com.kauruck.game.sprites.PlateSprite;
import com.kauruck.graphics.RenderThread;
import com.kauruck.update.UpdateThread;
import com.kauruck.util.ThreadState;
import com.kauruck.world.Layer;
import com.kauruck.world.World;

import java.awt.*;

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
     * The thread for updating the mainLayer
     */
    public static UpdateThread mainUpdateThread;


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
        mainUpdateThread = new UpdateThread(mainLayer);
        mainWorld.push(mainLayer);

        //Add some tmp sprites
        //mainLayer.push(new PlateSprite(100, Color.GREEN));
        //Set the renderLayer for the towerLogic
        TowelLogic.renderLayer = mainLayer;
        TowelLogic.fill(TowelLogic.towerA,5, 100, 10, Color.RED, Color.GREEN);
        TowelLogic.fill(TowelLogic.towerB, 1,100,10,Color.RED, Color.GREEN);

        //Show it in the main renderer
        mainRenderThread.setCurrentWorld(mainWorld);
        mainUpdateThread.createAndRun();

    }

    /**
     * Function to stop the app
     */
    public static void stop(){
        System.out.println("Stopping the render thread");
        mainRenderThread.stop();
        int timeOut = 0;
        while (mainRenderThread.getState() != ThreadState.Stopped && timeOut < 500){
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
        System.out.println("Stopping the update thread");
        mainUpdateThread.stop();
        timeOut = 0;
        while (mainUpdateThread.getState() != ThreadState.Stopped && timeOut < 500){
            try {
                Thread.sleep(1);
            }
            catch (InterruptedException e){
                timeOut = 500;
            }
            timeOut ++;
        }
        if(timeOut < 500)
            System.out.println("Update thread successfully stopped");
        else
            System.out.println("Something went wrong while stopping the update thread");
        System.out.println("We are exiting");
        System.exit(0);
    }
}
