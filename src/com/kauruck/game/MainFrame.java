package com.kauruck.game;

import com.kauruck.TowerOfHanoi;
import com.kauruck.ui.RenderTargetPanel;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {
    private RenderTargetPanel panel;

    public MainFrame(int width, int height, String title){
        //Set the with and height and center it
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        //Set the title
        this.setTitle(title);

        //Create and add the RenderTargetPanel
        panel = new RenderTargetPanel();
        this.add(panel);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                TowerOfHanoi.stop();
            }
        });

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_1 -> TowerOfHanoi.cursor.moveTo(TowerLogic.towerA);
                    case KeyEvent.VK_2 -> TowerOfHanoi.cursor.moveTo(TowerLogic.towerB);
                    case KeyEvent.VK_ENTER -> {
                        if(TowerOfHanoi.selection.getTarget() == null)
                            TowerOfHanoi.selection.moveTo(TowerOfHanoi.cursor.getTarget());
                        else{
                            if(TowerLogic.move(TowerOfHanoi.selection.getTarget(),TowerOfHanoi.cursor.getTarget())){
                                TowerOfHanoi.selection.moveTo(null);
                            }
                        }
                    }

                    case KeyEvent.VK_ESCAPE -> TowerOfHanoi.selection.moveTo(null);
                }
            }
        });
    }


    public RenderTargetPanel getPanel() {
        return panel;
    }
}
