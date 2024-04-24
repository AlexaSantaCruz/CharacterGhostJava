package PingPong2;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.swing.*;
import java.util.List;


public class Paddle{
    
    int id;/*paddle 1 or 2 */
    int yVelocity;
    private BufferedImage buffer;
    int x;
    int y;
    int width;
    int height;
    Graphics gBuffer;

    Paddle(int x, int y, int PADDLE_WIDTH, int PADDLE_HEIGHT, int id,BufferedImage buffer){
        
        this.x=x;
        this.y=y;
        width=PADDLE_WIDTH;
        height=PADDLE_HEIGHT;
        this.id=id;
        this.buffer = buffer;
        this.gBuffer=buffer.createGraphics();


    }

    public void KeyPressed(KeyEvent e){

    }
    public void KeyReleased(KeyEvent e){
        
    }

    public void setYDirection(int yDirection){

    }

    public void move(){

    }

    public void draw(){
        if(buffer==null){
            System.out.println("null");
            return;
        }else{
            if(id==1){
            gBuffer.setColor(Color.blue);
            int[] xPoints = {x, width, width, x};
            int[] yPoints = {y, y, height, height};
            drawRectangle(x,y, width, height,Color.blue);
            ScanLine(xPoints, yPoints, Color.blue);

        }else{
            gBuffer.setColor(Color.red);
            int[] xPoints = {x, width, width, x};
            int[] yPoints = {y, y, height, height};
            drawRectangle(x,y, width, height,Color.red);
            ScanLine(xPoints, yPoints, Color.red);

        }
        }
        
    }

    public void putPixel(int x, int y, Color c) {
        if (x >= 0 && x < buffer.getWidth() && y >= 0 && y < buffer.getHeight()) {
            buffer.setRGB(x, y, c.getRGB());
        }else{
            System.out.println("out of bounds");
        }    
    }
    
    public void drawRectangle(int x0, int y0, int x1, int y1, Color color) {
        drawLine(x0, y0, x1, y0, color); // Línea superior
        drawLine(x0, y0, x0, y1, color); // Línea izquierda
        drawLine(x1, y0, x1, y1, color); // Línea derecha
        drawLine(x0, y1, x1, y1, color); // Línea inferior
    }
    public void drawLine(int x0, int y0, int x1, int y1, Color color) {
        if (x0 == x1) 
        { // Si la línea es vertical
            drawVerticalLine(x0, y0, y1, color);
        }
        else 
        {
            drawNonVerticalLineBresenham(x0, y0, x1, y1, color);
        }
    }
    private void drawVerticalLine(int x, int startY, int endY, Color color) {
        int minY = Math.min(startY, endY);
        int maxY = Math.max(startY, endY);
        for (int y = minY; y <= maxY; y++) {
            putPixel(x, y, color);
        }
    }
    public void drawNonVerticalLineBresenham(int x0, int y0, int x1, int y1, Color color) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        int err = dx - dy;
        int e2;
    
        while (true) {
            putPixel(x0, y0, color);
    
            if (x0 == x1 && y0 == y1) {
                break;
            }
    
            e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x0 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }
        }
    }


    public void ScanLine(int[] xPoints, int[] yPoints, Color color) {

        // Calcular el punto mínimo en X,Y
        int minY = Arrays.stream(yPoints).min().getAsInt();
        int maxY = Arrays.stream(yPoints).max().getAsInt();

        // Iterar en el eje vertical
        for (int y = minY; y <= maxY; y++) {
            List<Integer> intersections = new ArrayList<>();
            for (int i = 0; i < xPoints.length; i++) {
                int x1 = xPoints[i];
                int y1 = yPoints[i];
                int x2 = xPoints[(i + 1) % xPoints.length];
                int y2 = yPoints[(i + 1) % yPoints.length];

                if ((y1 <= y && y2 >= y) || (y2 <= y && y1 >= y)) {
                    if (y1 != y2) {
                        int x = x1 + (y - y1) * (x2 - x1) / (y2 - y1);
                        intersections.add(x);
                    } else if (y == y1 && ((y1 == minY && y2 != minY) || (y1 == maxY && y2 != maxY))) {
                        intersections.add(x1);
                    }
                }
            }

            intersections.sort(Integer::compareTo);
            for (int i = 0; i < intersections.size(); i += 2) {
                int xStart = intersections.get(i);
                int xEnd = intersections.get(i + 1);
                for (int x = xStart; x <= xEnd; x++) {
                    putPixel(x, y, color);
                }
            }
        }
    }


}
