package com.kauruck.game;

import com.kauruck.TowerOfHanoi;
import com.kauruck.graphics.sprites.Sprite;
import com.kauruck.update.ICanReceiveTick;
import com.kauruck.util.BasicQueue;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Logic extends Sprite implements ICanReceiveTick {

    public static final Tower A = new Tower(100, 200, "A");
    public static final Tower B = new Tower(300, 200, "B");
    public static final Tower C = new Tower(500, 200, "C");

    public static final Queue<Call> calls = new BasicQueue<>();

    private static boolean solved = false;

    public static void next(){
        if(!solved){
            solve();
        }
        if(calls.isEmpty()){
            solved = false;
            return;
        }
        calls.remove().execute();
    }

    public static void init(){
        TowerLogic.fill(A, TowerOfHanoi.START_PLATES, 100, 10, Color.BLUE, Color.CYAN);
    }

   public static void solve(){
        move(TowerOfHanoi.START_PLATES, A, C);
        solved = true;
        //System.out.println("Done");
   }

   private static void move(int n, Tower a, Tower b){
        //System.out.println(n + " " + a + "->" + b);
        //Nothing to be done
       if(n == 0)
           return;
        //We can move that
        if(n == 1){
            calls.add(() -> TowerLogic.move(a, b));
            //System.out.println("Moving " + a + "->" + b);
            return;
        }
        //move state
        if(a == A && b == C)
            move(n -1, A, B);
        if(a == A && b == B)
            move(n - 1, A ,C);
        if(a == B && b == C)
            move(n -1, B, A);
        if(a == C && b == B)
            move(n - 1, C ,A);
        if(a == B && b == A)
            move(n - 1, B, C);
        if(a == C && b == A)
            move(n - 1, C, B);


        //Move last(biggest piece)
       calls.add(() ->TowerLogic.move(a, b));
       //System.out.println("Moving target " + a + "->" + b);

       if(a == A && b == B)
           move(n - 1, C, B);
       if(a == A && b== C)
           move(n - 1, B, C);
       if(a == B && b == C)
           move(n -1, A, C);
       if(a == C && b == B)
           move(n - 1, A ,B);
       if(a == B && b == A)
           move(n - 1, C, A);
       if(a == C && b == A)
           move(n - 1, B, A);


       //System.out.println("Exiting " + a + "&" + b);



   }

   //This is just because I don't wont to rewrite everything
    @Override
    public void draw(Graphics2D g) {

    }



    //Update Stuff
    float timePassed = 0;
    float timeStep = 100;

    @Override
    public void update(float deltaTime) {
        if(timePassed >= timeStep){
            Logic.next();
            timePassed = 0;
        }
        timePassed += deltaTime;

    }

    @FunctionalInterface
   private interface Call{
        void execute();
   }
}
