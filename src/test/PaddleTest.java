package test;

import com.main.Paddle;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class PaddleTest {

    @Test
    void getScore() {
        Paddle testPaddle = new Paddle(Color.BLACK, true);
        assertEquals(0, testPaddle.getScore());
    }
}