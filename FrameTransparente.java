import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import static java.awt.GraphicsDevice.WindowTranslucency.*;
import java.awt.Color; 




public class FrameTransparente extends JFrame {
    private Personaje persona;
    public FrameTransparente() {

        setBackground(new Color(0,0,0,0));
        setSize(new Dimension(800,800));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        persona = new Personaje();
        add(persona);

    }

    public static void main(String[] args) {
               // Determine what the GraphicsDevice can support.
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        boolean isPerPixelTranslucencySupported = gd.isWindowTranslucencySupported(PERPIXEL_TRANSLUCENT);

        // If translucent windows aren't supported, exit.
        if (!isPerPixelTranslucencySupported) {
            System.out.println("Per-pixel translucency is not supported");
            System.exit(0);
        }
        


        JFrame.setDefaultLookAndFeelDecorated(true);
        // Create the GUI on the event-dispatching thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                FrameTransparente demo = new FrameTransparente();

                // Display the window.
                demo.setVisible(true);
            }
        });
        // Crear una instancia de la ventana principal

    }
}