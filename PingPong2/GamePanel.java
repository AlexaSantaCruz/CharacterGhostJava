package PingPong2;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable{

    static final int GAME_WIDTH=1000;
	static final int GAME_HEIGHT = (int)(GAME_WIDTH * (0.5555));
    static final Dimension SCREEN_SIZE=new Dimension(GAME_WIDTH,GAME_HEIGHT);
    static final int BALL_DIAMETER=20;
    static final int PADDLE_WIDTH=25;
    static final int PADDLE_HEIGHT=100;

    Thread gameThread;
    Image image;
    Random random;
    Paddle paddle1;
    Paddle paddle2;
    Ball ball;
    Score score;
    public BufferedImage buffer;
    Graphics gBuffer;


    GamePanel(){

        if(buffer==null){
            buffer = new BufferedImage(GAME_WIDTH, GAME_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        }
        newPaddles();
        newBall();
        score=new Score(GAME_WIDTH,GAME_HEIGHT);
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(SCREEN_SIZE);

        gameThread = new Thread(this);
        gameThread.start();

    }

    

    public void newBall(){
        System.out.println("newBall");
        random = new Random();
		ball = new Ball((GAME_WIDTH/2)-(BALL_DIAMETER/2),random.nextInt(GAME_HEIGHT-BALL_DIAMETER),BALL_DIAMETER, buffer);
	
    }

    public void newPaddles(){

        paddle1=new Paddle(0,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,1, buffer);
        paddle2=new Paddle(GAME_WIDTH-PADDLE_WIDTH,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),GAME_WIDTH-1,PADDLE_HEIGHT,2, buffer);
    }

    public void paintComponent(Graphics g){
        image = createImage(getWidth(),getHeight());
        gBuffer=image.getGraphics();
        draw();
        g.drawImage(buffer, 0, 0, this);
    }

    public void draw(){
                ball.draw();

        paddle1.draw();
        paddle2.draw();
        Toolkit.getDefaultToolkit().sync();

    }

    public void move(){
		paddle1.move();
		paddle2.move();
        ball.move();
    }

    public void checkCollision(){
		//bounce ball off top & bottom window edges
		if(ball.y <=0) {
			ball.setYDirection(-ball.yVelocity);
		}
		if(ball.y >= GAME_HEIGHT-BALL_DIAMETER) {
			ball.setYDirection(-ball.yVelocity);
		}
        if(ball.x <=0) {
			score.player2++;
			newPaddles();
			newBall();
			System.out.println("Player 2: "+score.player2);
		}
		if(ball.x >= GAME_WIDTH-BALL_DIAMETER) {
			score.player1++;
			newPaddles();
			newBall();
			System.out.println("Player 1: "+score.player1);
		}
    }

    public void run(){
		//game loop
		long lastTime = System.nanoTime();
		double amountOfTicks =60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		while(true) {
			long now = System.nanoTime();
			delta += (now -lastTime)/ns;
			lastTime = now;
			if(delta >=1) {
				move();
				checkCollision();
				repaint();
				delta--;
			}
		}

  
    }

    public class AL extends KeyAdapter{
        public void KeyPressed(KeyEvent e){

        }
        public void KeyReleased(KeyEvent e){
            
        }
    }
}
