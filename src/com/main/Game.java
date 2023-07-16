package com.main;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;

public class Game extends Canvas implements Runnable {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = WIDTH * 9/16;

    public boolean running = false;
    public boolean win = false;
    private Thread gameThread;

    private Ball ball;
    private Paddle paddleLeft;
    private Paddle paddleRight;

    public Menu menu;


    public static void main(String[] args) {
        new Game();
    }

    public Game() {

        canvasSetup();

        new Window("PONG!!!", this);

        initialize();

        this.addKeyListener(new KeyInput(paddleLeft, paddleRight));
        this.addMouseListener(menu);
        this.addMouseMotionListener(menu);
        this.setFocusable(true);

    }

    public static int sign(double v) {
        if(v <= 0) {
            return -1;
        }
        return 1;
    }

    public static int ensureRange(int value, int min, int max) {
        return Math.min(Math.max(value, min), max);
    }

    private void initialize() {
        //initialize ball
        ball = new Ball();
        //initialize paddles
        paddleLeft = new Paddle(Color.GREEN, true);
        paddleRight = new Paddle(Color.RED, false);
        //initialize menu
        menu = new Menu(this);
    }

    private void canvasSetup() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
    }

    @Override
    public void run() {
        this.requestFocus();
        //game timer

        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running && !win) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                update();
                delta--;
            }
            if (running) {
                draw();
            }
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    private void draw() {
        //initialize drawing tools

        BufferStrategy buffer = this.getBufferStrategy();

        if (buffer == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = buffer.getDrawGraphics();

        //draw background
        drawBackground(g);

        if(menu.active) {
            menu.draw(g);
        }

        //draw ball
        ball.draw(g);

        //draw paddles and score
        paddleLeft.draw(g);
        paddleRight.draw(g);

        //DRAW
        g.dispose();
        buffer.show();
    }

    private void drawBackground(Graphics g) {
        //black
        g.setColor(Color.BLACK);
        g.fillRect(0,0,WIDTH, HEIGHT);

        //halves
        g.setColor(Color.WHITE);
        Graphics2D g2D = (Graphics2D) g;
        Stroke dottedLine = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{10}, 0);
        g2D.setStroke(dottedLine);
        g2D.drawLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT);
    }

    private void update() {
        if(!menu.active) {
            //update ball
            ball.update(paddleLeft, paddleRight);

            //update paddles
            paddleLeft.update(ball);
            paddleRight.update(ball);

            if (paddleRight.getScore() == 11 || paddleLeft.getScore() == 11) {
                win = true;
                try {
                    saveStatus();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void saveStatus() throws IOException{
        File file = new File("LastGame.txt");

        FileWriter writer = new FileWriter(file);

        writer.write("Player A score: " + paddleLeft.getScore() + "  vs  Player B score: " + paddleRight.getScore());

        writer.flush();
        writer.close();
    }

    public void start() {
        gameThread = new Thread(this);
        gameThread.start();
        running = true;
        win = false;
    }

    public void stop() {
        try {
            gameThread.join();
            running = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
