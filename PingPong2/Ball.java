package PingPong2;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.swing.*;

public class Ball {
    
    Random random;
    int xVelocity;
    int yVelocity;
    int initialSpeed=2;
    int x;
    int y;
    int width;
    BufferedImage buffer;
    Graphics gBuffer;


    
    Ball(int x, int y, int width, BufferedImage buffer){
        this.x=x;
        this.y=y;
        this.width=width;
        this.buffer=buffer;
        this.gBuffer=buffer.createGraphics();
        
        random = new Random();
        
		int randomXDirection = random.nextInt(2);
		if(randomXDirection == 0)
			randomXDirection--;
		setXDirection(randomXDirection*initialSpeed);
		
		int randomYDirection = random.nextInt(2);
		if(randomYDirection == 0)
			randomYDirection--;
		setYDirection(randomYDirection*initialSpeed);

    }

    public void setXDirection(int randomXDirection){
		xVelocity = randomXDirection;

    }
    public void setYDirection(int randomYDirection){
		yVelocity = randomYDirection;

    }

    public void move(){
		x += xVelocity;
		y += yVelocity;
    }

    public void draw(){
        if(buffer==null){
            System.out.println("null");
            return;
        }else{
            // Limpia el buffer con un color de fondo (en este caso, negro)
            gBuffer.setColor(Color.BLACK);
            gBuffer.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
            
            // Dibuja el balón en su nueva posición
            drawCircleColor(x, y, width, Color.white);
    
            // Dibuja el buffer en el panel
            Graphics g = buffer.getGraphics();
            g.drawImage(buffer, 0, 0, null);
            g.dispose();
        }
    }
    
    

    public void putPixel(int x, int y, Color c) {
        if (x >= 0 && x < buffer.getWidth() && y >= 0 && y < buffer.getHeight()) {
            buffer.setRGB(x, y, c.getRGB());
        }else{
            /*do nothing */
        }    
    }

    private void drawCircleColor(int xc, int yc, int r, Color c) {
        int rSquared = r * r;

        // Iteramos sobre todos los puntos dentro de un cuadrado que rodea al círculo
        for (int x = xc - r; x <= xc + r; x++) {
            for (int y = yc - r; y <= yc + r; y++) {
                int distanceSquared = (x - xc) * (x - xc) + (y - yc) * (y - yc);

                // Verificamos si el punto está dentro del círculo comparando con el cuadrado del radio
                if (distanceSquared <= rSquared) {
                    putPixel(x, y, c);
                }
            }
        }
    }
}
