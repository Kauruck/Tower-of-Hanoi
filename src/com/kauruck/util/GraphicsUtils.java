package com.kauruck.util;

import java.awt.*;

public class GraphicsUtils {

    public static boolean movedAcrossScree(int x, int y, int width, int height){
        GraphicsDevice[] gds = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        for(GraphicsDevice gd : gds){
            int gdWidth = gd.getDisplayMode().getWidth();
            int gdHeight = gd.getDisplayMode().getHeight();
            int a = x + y;
        }
        return false;
    }
}
