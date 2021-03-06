package com.breakout.es.control;

import com.breakout.es.model.Defender;
import com.breakout.es.model.Point;

/**
 * Created by mz on 20/05/17.
 */

public class DefenderControl {

    private Defender defender;

    public DefenderControl() {
        defender = new Defender();
    }

    public float[] getCurrentDefenderVertices() {
        return new float[]{
                defender.getPosition().x - Defender.HALF_LENGTH, defender.getPosition().y,
                Defender.RGB, Defender.RGB, Defender.RGB,
                defender.getPosition().x + Defender.HALF_LENGTH, defender.getPosition().y,
                Defender.RGB, Defender.RGB, Defender.RGB};
    }

    public void updateDefenderPosition(float normalizedX) {
        defender.setPosition(new Point(normalizedX, defender.getPosition().y));
    }

    public Point getDefenderPosition() {
        return defender.getPosition();
    }
}
