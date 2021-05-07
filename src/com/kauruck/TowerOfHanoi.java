package com.kauruck;

import com.kauruck.game.Logic;
import com.kauruck.game.MainFrame;
import com.kauruck.game.TowerLogic;
import com.kauruck.game.sprites.SelectionSprite;
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
     * The Layer where all the action happens
     */
    public static Layer mainLayer;

    /**
     * The layer for the cursor.
     * This keeps it always on top
     */
    public static Layer cursorLayer;

    /**
     * The thread for updating the mainLayer
     */
    public static UpdateThread mainUpdateThread;


    /**
     * The cursor for the game
     */
    public static SelectionSprite cursor;


    /**
     * The selected tower
     */
    public static SelectionSprite selection;

    /**
     * The number of plates to start with
     */
    public static final int START_PLATES = 10;


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

        //Add cursorlayer first so that it is ontop
        cursorLayer = new Layer();
        mainWorld.push(cursorLayer);

        //Create main Layer
        mainLayer = new Layer();
        mainUpdateThread = new UpdateThread(mainLayer);
        mainWorld.push(mainLayer);


        //Set the renderLayer for the towerLogic
        TowerLogic.renderLayer = mainLayer;
        //Add some tmp sprites
        //TowerLogic.fill(TowerLogic.towerA,5, 100, 10, Color.RED, Color.GREEN);
        //TowerLogic.fill(TowerLogic.towerB, 1,100,10,Color.RED, Color.GREEN);
        //Init the towers
        Logic.init();

        //Init cursor
        cursor = new SelectionSprite(Color.YELLOW);
        cursor.moveTo(TowerLogic.towerA);
        mainLayer.push(cursor);

        //Init selection
        selection = new SelectionSprite(Color.RED);
        cursorLayer.push(selection);

        //Added a logic holder to receive updates
        mainLayer.push(new Logic());

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
