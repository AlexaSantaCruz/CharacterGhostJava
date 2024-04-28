import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import PingPong2.*;

public class Personaje extends JComponent implements Runnable {
    private BufferedImage buffer; // Buffer de dibujo en segundo plano
    private BufferedImage visibleBuffer; // Buffer visible en primer plano
    private Graphics2D graPixel; // Gráficos para dibujar en el buffer
    private Thread characterThread;
    int i=0;
    int j=30;
    int eyes=0;
    int eyes2=25;
    int count=0;
    boolean activateEyes=false;
    boolean showDialog=false;
    boolean welcome=true;
    boolean game=false;
    boolean vaciarPapelera=false;
    boolean hablar=false;
    boolean pruebaTexto=false;
    Figures fig;
    AlphabetBitmap alphabet;
    GameFrame frameJuego;

    

    public Personaje() {
        buffer = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
        fig=new Figures(buffer);
        alphabet=new AlphabetBitmap(fig);
        setPreferredSize(new Dimension(800, 800));
        visibleBuffer = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
        graPixel = buffer.createGraphics();

        setBackground(new Color(0, 0, 0, 0));
        characterThread = new Thread(this);
        characterThread.start();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) { // Verificar si el clic fue con el botón derecho
                    // Crear el menú emergente
                    JPopupMenu popupMenu = new JPopupMenu();
                    JMenuItem menuItem1 = new JMenuItem("Jugar");
                    JMenuItem menuItem2 = new JMenuItem("Vaciar papelera");
                    JMenuItem menuItem3 = new JMenuItem("Prueba de texto");
                    JMenuItem menuItem4 = new JMenuItem("Hablar");

                                // Crear ActionListener para los elementos de menú
                                ActionListener menuItem1Listener = new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent event) {
                                        System.out.println("Opcion 1 seleccionada");

                                        game=true;
                                        dialog();

                                    }
                                };
                                ActionListener menuItem2Listener = new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent event) {
                                        vaciarPapelera=true;
                                        dialog();
                                    }
                                };
                                ActionListener menuItem3Listener = new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent event) {
                                        pruebaTexto=true;
                                        dialog();
                                    }
                                }; 
                                ActionListener menuItem4Listener = new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent event) {
                                        hablar=true;
                                        dialog();
                                    }
                                };
            
                                                    // Asignar ActionListener a los elementos de menú
                    menuItem1.addActionListener(menuItem1Listener);
                    menuItem2.addActionListener(menuItem2Listener);
                    menuItem3.addActionListener(menuItem3Listener);
                    menuItem4.addActionListener(menuItem4Listener);


                    // Agregar los elementos de menú al menú emergente
                    popupMenu.add(menuItem1);
                    popupMenu.add(menuItem2);
                    popupMenu.add(menuItem3);
                    popupMenu.add(menuItem4);


                    // Mostrar el menú emergente en la posición del clic
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    
    }


    @Override
    public void run() {
        while (true) {
            draw(); // Dibujar en el buffer

            swapBuffers(); // Cambiar los buffers
            repaint(); // Redibujar el componente
            try {

                Thread.sleep(1000 / 60); // Controlar la velocidad de actualización (60 fps)
                count++;
                if(count%200==0){
                    activateEyes=true;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(visibleBuffer, 0, 0, this);
    }

    void dialog(){
        /*here are predeterminated text for this robot */
        /*and predetermined points of the dialog*/
        int xPointsDialog[]={100,700,700,100};
        int yPointsDialog[]={500,500,700,700};
        if(welcome){
            /*Say hi to the user, put a text saying hello and then dissapears when the uses clicks on character */

            ScanLine(xPointsDialog, yPointsDialog, Color.white);
            alphabet.drawWord(150 ,550 ,"hola bienvenido, haz click derecho", Color.black);
            alphabet.drawWord(150, 600, "sobre mi para ver mis funciones", Color.black);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    welcome=false;
                }
            });
        }

        if(game){
            frameJuego=null;
            ScanLine(xPointsDialog, yPointsDialog, Color.white);
            alphabet.drawWord(150 ,550 ,"claro, deja abro el juego por ti", Color.black);
            alphabet.drawWord(150, 600, "debes tener un jugador 2", Color.black);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    game=false;
                    if(frameJuego==null){
                    frameJuego = new GameFrame();
                    frameJuego.setLocationRelativeTo(null);
                    frameJuego.setVisible(true);
                    }
                }
            });  
        }

        if(vaciarPapelera){
            ScanLine(xPointsDialog, yPointsDialog, Color.white);
            alphabet.drawWord(150 ,550 ,"claro, te ayudo con eso", Color.black);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //pera deja subo esto a github, no vaya a ser el diablo
                    //que uso mal el cmd xD
                    vaciarPapelera=false;

                }
            });  

        }

    }

    void drawAnimationAntenna(){
        /*Antenna */

                if(i<=30){
                drawLineWweight(400, 210, 380+i, 110, Color.black, 7);
                i++;
            }else{
                drawLineWweight(400, 210, 380+j, 110, Color.black, 7);
                j--;
                if(j==0){
                    i=0;
                    j=30;

                }
            }

        
    }

    void drawAnimationEyes(){
        if(activateEyes){
        if(eyes<=25){
            drawLineWweight(370, 300, 370, 270+eyes, Color.black, 10);
            drawLineWweight(440, 300, 440, 270+eyes, Color.black, 10);
            eyes++;
        }else{
            drawLineWweight(370, 300, 370, 270+eyes2, Color.black, 10);
            drawLineWweight(440, 300, 440, 270+eyes2, Color.black, 10);
            eyes2--;
            if(eyes2==0){
                eyes=0;
                eyes2=30;
                activateEyes=false;
                count=0;
            }
        }
    }else{
        drawLineWweight(370, 300, 370, 270, Color.black, 10);
        drawLineWweight(440, 300, 440, 270, Color.black, 10);


    }
    }
    void draw() {
        
        graPixel.setComposite(AlphaComposite.Clear);
        graPixel.fillRect(0, 0, (buffer.getWidth()), (buffer.getHeight()));
        graPixel.setComposite(AlphaComposite.SrcOver);
        /*Head! */
        int xPoints[]={300,500,490,310};
        int yPoints[]={200,200,400,400};
        ScanLine(xPoints, yPoints, Color.black);
        int xPointsScreen[]={320,480,470,330};
        int yPointsScreen[]={220,220,380,380};
        ScanLine(xPointsScreen, yPointsScreen, Color.gray);
        /*eyes and mouth */
        int pointMouth1X[]={385,405,425};
        int pointMouthY[]={340,360,340};
 
        ScanLine(pointMouth1X, pointMouthY, Color.black);

        drawAnimationEyes();
        drawAnimationAntenna();

        /*Body */
        int pointsNeckX[]={380,420,420,380};
        int pointsNeckY[]={400,400,430,430};
        ScanLine(pointsNeckX, pointsNeckY, Color.black);

        int pointsBodyX[]={300,500,490,310};
        int pointsBodyY[]={430,430,700,700};
        ScanLine(pointsBodyX, pointsBodyY, Color.blue);

        int pointsNeckX1[]={380,400,420};
        int pointsNeckY1[]={430,450,430};
        ScanLine(pointsNeckX1, pointsNeckY1, Color.black);


        /*Details on clothes */
        drawLineWweight(430, 430, 360, 470, Color.white, 10);
        drawLineWweight(372, 430, 392, 450, Color.white, 15);
        drawLineWweight(365, 464, 365, 550, Color.white, 10);
        drawLineWweight(430, 545, 365, 545, Color.white, 10);
        drawLineWweight(425, 545, 425, 595, Color.white, 10);
        drawLineWweight(365, 590, 425, 590, Color.white, 10);
        drawLineWweight(365, 585, 365, 700, Color.white, 10);
        drawCircleColor(400, 567, 5, Color.white);

        dialog();
         // Copia el buffer de dibujo en el buffer visible
         Graphics2D g2d = visibleBuffer.createGraphics();
         g2d.drawImage(buffer, 0, 0, null);
         g2d.dispose();
        
    }
    private void swapBuffers() {
        BufferedImage temp = buffer;
        buffer = visibleBuffer;
        visibleBuffer = temp;
        graPixel = buffer.createGraphics();
        fig.buffer=buffer;
        fig.lines.bufferedImage=buffer;

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

    public void drawParable(int x0, int y0, int a, int b, Color color) {
        double x, y;
        for (x = -a; x <= a; x += 0.01) {
            y = b * x * x / (a * a);
            putPixel((int) (x + x0), (int) (y + y0), color);
        }
    }
    

    public void drawLineWweight(int x0, int y0, int x1, int y1, Color color, int ancho) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = (x0 < x1) ? 1 : -1;
        int sy = (y0 < y1) ? 1 : -1;
        int err = dx - dy;
        boolean isHorizontal = dy < dx; //Pendiente

        while (true) {
            for (int i = -ancho / 2; i <= ancho / 2; i++) {
                if (isHorizontal) {
                    putPixel(x0, y0 + i, color);
                } else {
                    putPixel(x0 + i, y0, color);
                }
            }

            if (x0 == x1 && y0 == y1) break;

            int e2 = 2 * err;
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



    /*PAINT METHODS FOR CHARACTER */
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

    public void drawRectangle(int x0, int y0, int x1, int y1, Color color) {
        drawLine(x0, y0, x1, y0, color); // Línea superior
        drawLine(x0, y0, x0, y1, color); // Línea izquierda
        drawLine(x1, y0, x1, y1, color); // Línea derecha
        drawLine(x0, y1, x1, y1, color); // Línea inferior
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
