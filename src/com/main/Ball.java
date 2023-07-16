package com.main;

import java.awt.*;

public class Ball {

    public static final int SIZE = 16;

    private int x, y;
    private int xVel, yVel; // values of 1 or -1
    private int speed = 5;

    public Ball() {
        reset();
    }

    public int getX() { return x; }

    public int getY() { return y; }

    private void reset() {
        //initial position
        x = Game.WIDTH / 2 - SIZE / 2;
        y = Game.HEIGHT / 2 - SIZE / 2;

        //initial velocity
        xVel = Game.sign(Math.random() * 2.0 - 1);
        yVel = Game.sign(Math.random() * 2.0 - 1);
    }

    public void changeYDirection() { yVel *= -1; }

    public void changeXDirection() {
        xVel *= -1;
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, SIZE, SIZE);
    }

    public void update(Paddle paddleLeft, Paddle paddleRight) {
        //Update movement
        x += xVel * speed;
        y += yVel * speed;

        //Collisions
        if (y + SIZE >= Game.HEIGHT || y <= 0) {
            changeYDirection();
        }

        //  With walls
        //Right wall
        if (x+SIZE >= Game.WIDTH) {
            paddleLeft.addPoint();
            reset();
        }
        //Left wall
        if (x <= 0) {
            paddleRight.addPoint();
            reset();
        }
    }
}
