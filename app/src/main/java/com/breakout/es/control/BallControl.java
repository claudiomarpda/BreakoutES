package com.breakout.es.control;

import com.breakout.es.model.Ball;
import com.breakout.es.model.Defender;
import com.breakout.es.model.Point;
import com.breakout.es.model.Space;

/**
 * Created by mz on 20/05/17.
 */

public class BallControl implements Runnable {

    private static float BALL_SPEED = 0.01f;
    private static float BALL_DOWN = -2f;

    private Ball ball;
    private int ballDirectionY;
    private int ballDirectionX;
    private Point defenderPosition;

    public BallControl(Point defenderPosition) {
        ball = new Ball();
        ballDirectionY = 1;
        ballDirectionX = 1;
        this.defenderPosition = defenderPosition;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (ball.getPosition().x < Space.LEFT_BOUND || ball.getPosition().x > Space.RIGHT_BOUND) {
                updateBallDirectionX();
            }
            if (ball.getPosition().y > Space.UP_BOUND) {
                updateBallDirectionY();
            }
            if (ball.getPosition().y < Space.LOW_BOUND) {
                if (wasDefended()) {
                    updateBallDirectionY();
                }
            }
            // game is already over
            // ends the thread loop after waiting the ball to leave the screen
            if (ball.getPosition().y < BALL_DOWN) {
                break;
            }

            // update ball position
            ball.setPosition(new Point(
                    ball.getPosition().x * ballDirectionX,
                    ball.getPosition().y + (BALL_SPEED * ballDirectionY)));
        }
    }

    private void updateBallDirectionX() {
        ballDirectionX *= -1;
    }

    private void updateBallDirectionY() {
        ballDirectionY *= -1;
    }

    public float[] getCurrentBallVertices() {
        return new float[]{ball.getPosition().x, ball.getPosition().y,
                Ball.RGB, Ball.RGB, Ball.RGB};
    }

    public void setDefenderPosition(Point point) {
        defenderPosition = point;
    }

    private boolean wasDefended() {
        if (ball == null || defenderPosition == null) {
            return false;
        }
        if (ball.getPosition().x <= (defenderPosition.x + Defender.HALF_LENGTH) &&
                ball.getPosition().x >= (defenderPosition.x - Defender.HALF_LENGTH)) {
            return true;
        }
        return false;
    }
}
