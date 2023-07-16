package com.main;

import java.awt.*;

public class Paddle {

    private int x, y;
    private int vel = 0;
    private int speed = 10;
    private int width = 22;
    private int height = 85;
    private int score = 0;
    private Color color;
    private boolean isLeft;

    public Paddle(Color c, boolean isLeft) {
        color = c;

        this.isLeft = isLeft;

        //initialize position
        if(isLeft) {
            x = 0;
        } else {
            x = Game.WIDTH - width;
        }
        y = Game.HEIGHT / 2 - height / 2;
    }

    public int getScore() {
        return score;
    }

    public void addPoint() {
        score++;
    }

    public void draw(Graphics g) {
        //draw paddle
        g.setColor(color);
        g.fillRect(x, y, width, height);

        //draw score
        int xPos;
        String scoreText = Integer.toString(score);
        Font font = new Font("Roboto", Font.PLAIN, 50);

        int strWidth = g.getFontMetrics(font).stringWidth(scoreText) + 1;
        int padding = 25;

        if (isLeft) {
            xPos = Game.WIDTH/2 - padding - strWidth;
        } else {
            xPos  = Game.WIDTH/2 + padding;
        }

        g.setFont(font);
        g.drawString(scoreText, xPos, 50);
    }

    public void update(Ball ball) {
        //Update position
        y = Game.ensureRange(y += vel, 0, Game.HEIGHT - height);

        int ballX = ball.getX();
        int ballY = ball.getY();

        //Collision with ball

        if (isLeft) {
            if (ballX <= width && ballY + Ball.SIZE >= y && ballY <= y+height) {
                ball.changeXDirection();
            }
        } else {
            if (ballX + Ball.SIZE >= Game.WIDTH - width && ballY + Ball.SIZE >= y && ballY <= y + height) {
                ball.changeXDirection();
            }
        }
    }

    public void switchDirection(int direction) {
        vel = speed * direction;
    }

    public void stop() {
        vel = 0;
    }
}
