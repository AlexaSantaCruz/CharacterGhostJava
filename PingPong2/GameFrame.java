package PingPong2;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GameFrame extends JFrame{
    GamePanel panel;

    public GameFrame(){
       panel = new GamePanel();
       this.add(panel);
       this.setTitle("Pong IGS");
       this.setResizable(false);
       this.setBackground(Color.BLACK);
       this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
       this.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            // Destruir el objeto GameFrame
            panel.isPlaying=false;
            dispose();
            
        }
    });


       this.pack();
       this.setVisible(true);
       this.setLocationRelativeTo(null);

    }
    
}
