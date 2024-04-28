import java.awt.*;
import java.awt.image.BufferedImage;

public class Figures {
    BufferedImage buffer;
    Graphics gBuffer;
    Lines lines;



    Figures(BufferedImage buffer){
        this.buffer=buffer;
        this.gBuffer=buffer.createGraphics();
        lines = new Lines(buffer);
    }

    public void putPixel(int x,int y, Color color, int pixelSize){
        lines.putPixel(x,y,color,pixelSize);
    }

}