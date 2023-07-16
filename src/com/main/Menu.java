package com.main;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu extends MouseAdapter {

    public boolean active;
    //buttons
    private Rectangle playBtn;
    private String playTxt = "Play";
    private boolean pHighlight = false;

    private Rectangle quitBtn;
    private String quitTxt = "Quit";
    private boolean qHighlight = false;

    private Font font;

    public Menu(Game game) {

        active = true;

        game.start();

        int width, height, x, y;

        width = 300;
        height = 150;


        y = Game.HEIGHT / 2 - height / 2;
        x = Game.WIDTH / 4 - width / 2;
        playBtn = new Rectangle(x, y, width, height);

        x = Game.WIDTH * 3/4 - width / 2;
        quitBtn = new Rectangle(x, y, width, height);

        font = new Font("Roboto", Font.PLAIN, 100);
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.setFont(font);

        g.setColor(Color.BLACK);
        if (pHighlight) {
            g.setColor(Color.WHITE);
        }
        g2d.fill(playBtn);

        g.setColor(Color.BLACK);
        if (qHighlight) {
            g.setColor(Color.WHITE);
        }
        g2d.fill(quitBtn);

        g.setColor(Color.WHITE);
        g2d.draw(playBtn);
        g2d.draw(quitBtn);

        int strWidth = g.getFontMetrics(font).stringWidth(playTxt);
        int strHeight = g.getFontMetrics(font).getHeight();


        g.setColor(Color.GREEN);
        g.drawString(playTxt, (int) (playBtn.getX() + playBtn.getWidth() / 2 - strWidth / 2),
                (int) (playBtn.getY() + playBtn.getHeight() / 2 + strHeight / 4));


        g.setColor(Color.RED);
        g.drawString(quitTxt, (int) (quitBtn.getX() + quitBtn.getWidth() / 2 - strWidth / 2),
                (int) (quitBtn.getY() + quitBtn.getHeight() / 2 + strHeight / 4));

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point p = e.getPoint();

        if (playBtn.contains(p)) {
            active = false;
        } else if (quitBtn.contains(p)) {
            System.exit(0);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Point p = e.getPoint();

        pHighlight = playBtn.contains(p);
        qHighlight = quitBtn.contains(p);
    }
}
