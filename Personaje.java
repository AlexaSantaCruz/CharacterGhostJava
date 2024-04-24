import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Personaje extends JComponent{
    private BufferedImage buffer;
    private Graphics2D graPixel;

    public Personaje(){
        setPreferredSize(new Dimension(200, 200));
        buffer = new BufferedImage(400, 300, BufferedImage.TRANSLUCENT); // Cambiado el tamaño del buffer
        graPixel = buffer.createGraphics();

        //graPixel.setColor(Color.WHITE);
       // graPixel.fillRect(0, 0, 400, 400);
        drawLine(50, 50, 200, 150, java.awt.Color.RED);
        this.setBackground(new Color(0,0,0,0));
    }
    public void putPixel(int x, int y, Color c) {
        buffer.setRGB(x, y, c.getRGB()); // Actualizando el color en las coordenadas dadas
        repaint(); // Actualizando la ventana
    }
    public void drawLine(int x0, int y0, int x1, int y1, Color color) {
        if (x0 == x1) { // Si la línea es vertical
            drawVerticalLine(x0, y0, y1, color);
        } else {
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
    
    private void drawNonVerticalLineBresenham(int x0, int y0, int x1, int y1, Color color) {
        if (x0 > x1) { // Si x0 > x1, intercambiar los puntos para asegurar que x0 sea menor que x1
            int temp = x0;
            x0 = x1;
            x1 = temp;
            temp = y0;
            y0 = y1;
            y1 = temp;
        }
        int dx = x1 - x0;
        int dy = y1 - y0;
        int A = 2 * dy;
        int B = 2 * (dy - dx);
        int p = 2 * dy - dx;
        int y = y0;

        for (int x = x0; x <= x1; x++) {
            putPixel(x, y, color);
            if (p < 0) {
                p += A;
            } else {
                y++;
                p += B;
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(buffer, 0, 0, this); // Dibujando el buffer en la ventana
    
    }

}
