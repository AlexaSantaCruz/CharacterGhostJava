import java.awt.image.BufferedImage;
import javax.swing.*;

import java.awt.Color;
import java.awt.Graphics;

public class Lines {
    BufferedImage bufferedImage;
    Graphics graPixel;
    
    public Lines(BufferedImage bufferedImage) {


        // Create a larger BufferedImage
        this.bufferedImage = bufferedImage;
        graPixel = bufferedImage.createGraphics();

    }
    

    public void putPixel(int x, int y,Color color, int pixelSize) {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        for (int i = 0; i < pixelSize; i++) {
            for (int j = 0; j < pixelSize; j++) {
                int pixelX = x + i;
                int pixelY = y + j;

                // Ensure the coordinates are within the bounds of the image.
                if (pixelX >= 0 && pixelX < width && pixelY >= 0 && pixelY < height) {
                    bufferedImage.setRGB(pixelX, pixelY, color.getRGB());
                }
            }
        }
    }
    public int getPixel(int x, int y){
       return bufferedImage.getRGB(x,y);
    }
}
