package com.kauruck.game;

import com.kauruck.game.sprites.PlateSprite;
import com.kauruck.world.Layer;

import java.awt.*;
import java.util.Collection;

public class TowerLogic {

    public static Layer renderLayer;

    public static Tower towerA = new Tower(100, 200);

    public static Tower towerB = new Tower(300, 200);

    public static void fill(Tower tower, int n, int startSize, int stepSize, Color A, Color B){
        if(renderLayer == null)
            return;

        for(int i = 0; i < n; i++){
            int size = startSize - stepSize * i;
            PlateSprite current = new PlateSprite(size,i%2==0 ? A : B);
            renderLayer.push(current);
            tower.addPlate(current);
        }
    }

    public static int calcHeight(Collection<PlateSprite> list){
        int out = list.stream().mapToInt(current -> (int) (current.getSize() * PlateSprite.ASPECT_RATIO)).sum();

        return out;
    }

    public static boolean move(Tower a, Tower b){
        if(b.addPlate(a.getFirst())){
            a.removeFirst();
            return true;
        }
        return false;
    }

}
