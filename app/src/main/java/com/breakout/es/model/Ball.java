package com.breakout.es.model;

/**
 * Created by mz on 20/05/17.
 */

public class Ball {

    public static final float RGB = 1.0f;
    private Point position;

    public Ball() {
        position = new Point(0f, -0.95f);
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

}
