package com.cellwars.actor;

import com.sun.javafx.geom.Vec2d;
import javafx.scene.shape.Circle;

/**
 * Created by Tam�s on 2015-04-27.
 */
public abstract class AElement {

    protected Circle body;

    public AElement(Vec2d position) {
        body = new Circle();
        body.centerXProperty().set(position.x);
        body.centerYProperty().set(position.y);
    }


    public Vec2d getPosition() {
        return new Vec2d(body.getCenterX(), body.getCenterY());
    }

    public void setPosition(Vec2d position) {
        body.centerXProperty().set(position.x);
        body.centerYProperty().set(position.y);
    }

    public Circle getBody() {
        return body;
    }

    public double getRadius() {
        return body.getRadius();
    }

    public void increaseRadius(double r) {
        body.setRadius(body.getRadius() + r);
    }

    public void decreaseRadius(double r) {
        body.setRadius(body.getRadius() - r);
    }

    public boolean collide(AElement c) {
        return body.intersects(c.getBody().getBoundsInParent());
    }

}
