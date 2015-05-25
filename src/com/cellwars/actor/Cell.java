package com.cellwars.actor;

import com.cellwars.scene.Rules;
import com.sun.javafx.geom.Vec2d;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Created by Tam�s on 2015-04-27.
 */
public class Cell extends AElement {

    protected boolean dead;

    public Cell(Vec2d position) {
        super(position);
        body.setRadius(Rules.CELLRADIUS);
        dead = false;
    }

    public void moveTo(Vec2d newPosition) {

        Timeline t = new Timeline();
        t.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(1000),
                        new KeyValue(body.centerXProperty(), newPosition.x, Interpolator.EASE_OUT),
                        new KeyValue(body.centerYProperty(), newPosition.y, Interpolator.EASE_OUT)
                ));
        t.play();
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }
}
