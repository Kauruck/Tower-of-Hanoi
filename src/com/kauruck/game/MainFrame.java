package com.kauruck.game;

import com.kauruck.TowerOfHanoi;
import com.kauruck.ui.RenderTargetPanel;

import javax.swing.*;
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
    }


    public RenderTargetPanel getPanel() {
        return panel;
    }
}
