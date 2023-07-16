package test;

import com.main.Ball;
import com.main.Game;

import static org.junit.jupiter.api.Assertions.*;

class BallTest {

    @org.junit.jupiter.api.Test
    void getX() {
        Ball testBall = new Ball();
        assertEquals(Game.WIDTH / 2 - Ball.SIZE / 2, testBall.getX());
    }

    @org.junit.jupiter.api.Test
    void getY() {
        Ball testBall = new Ball();
        assertEquals(Game.HEIGHT / 2 - Ball.SIZE / 2, testBall.getY());
    }
}