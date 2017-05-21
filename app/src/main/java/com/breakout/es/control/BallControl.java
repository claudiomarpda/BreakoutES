package com.breakout.es.control;

import android.content.Context;

import com.breakout.es.model.Ball;
import com.breakout.es.model.Defender;
import com.breakout.es.model.Point;
import com.breakout.es.model.Space;
import com.breakout.es.util.SoundManager;

import java.security.SecureRandom;

/**
 * Created by mz on 20/05/17.
 */

public class BallControl implements Runnable {

    private static final float BALL_DOWN = -2f;
    private static final float BALL_ACCELERATION = 1.25f;
    private float BALL_SPEED = 0.005f;

    private Ball ball;
    private int ballDirectionY;
    private int ballDirectionX;
    private Point defenderPosition;
    private boolean gameOver;
    private boolean beginFromRightSide;
    private int numberOfDefenses;
    private SoundManager soundManager;

    public BallControl(Point defenderPosition, Context context) {
        ball = new Ball();
        ballDirectionY = 1;
        ballDirectionX = 1;
        this.defenderPosition = defenderPosition;
        gameOver = false;
        beginFromRightSide = new SecureRandom().nextBoolean();
        numberOfDefenses = 0;
        soundManager = new SoundManager(context);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Side bounds collision
            if (ball.getPosition().x < Space.LEFT_BOUND || ball.getPosition().x > Space.RIGHT_BOUND) {
                changeBallDirectionX();
                if(ball.getPosition().y > Space.LOW_BOUND) {
                    soundManager.playWallCollision();
                }
            }

            // Up bound collision
            if (ball.getPosition().y > Space.UP_BOUND) {
                changeBallDirectionY();
                soundManager.playWallTopCollision();
            }
            // Low bound position
            // If there is no collision with the defender, game over
            if (ball.getPosition().y < Space.LOW_BOUND) {
                if (!gameOver) {
                    if (wasDefended()) {
                        soundManager.playDefenderCollision();
                        changeBallDirectionY();
                        defenseUpdate();
                    } else {
                        gameOver = true;
                    }
                }
            }
            // Game is already over. Ends the thread loop after waiting the ball to leave the screen.
            // This condition allows the ball to be drawn util it leaves the screen.
            if (ball.getPosition().y < BALL_DOWN) {
                break;
            }

            // Can have two types of initial direction
            if (!beginFromRightSide) {
                changeBallDirectionY();
                changeBallDirectionX();
                beginFromRightSide = true;
            }
            ball.setPosition(new Point(
                    ball.getPosition().x + (BALL_SPEED * ballDirectionX),
                    ball.getPosition().y + (BALL_SPEED * ballDirectionY)));

        }
    }

    private void defenseUpdate() {
        numberOfDefenses++;
        if(numberOfDefenses % 2 == 0) {
            BALL_SPEED *= BALL_ACCELERATION;
            soundManager.playPowerUp();
        }
        else {
            soundManager.playDefenderCollision();
        }
    }

    private void changeBallDirectionX() {
        ballDirectionX *= -1;
    }

    private void changeBallDirectionY() {
        ballDirectionY *= -1;
    }

    public float[] getCurrentBallVertices() {
        return new float[]{ball.getPosition().x, ball.getPosition().y,
                Ball.RGB, Ball.RGB, Ball.RGB};
    }

    public void setDefenderPosition(Point point) {
        defenderPosition = point;
    }

    /**
     * Checks if the defender position collides with the ball.
     */
    private boolean wasDefended() {
        if (ball.getPosition().x <= (defenderPosition.x + Defender.HALF_LENGTH) &&
                ball.getPosition().x >= (defenderPosition.x - Defender.HALF_LENGTH)) {
            return true;
        }
        return false;
    }
}