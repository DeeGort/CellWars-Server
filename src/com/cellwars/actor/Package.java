package com.cellwars.actor;

import com.cellwars.scene.Rules;
import com.sun.javafx.geom.Vec2d;
import javafx.scene.shape.Circle;

/**
 * Created by Tamás on 2015-05-24.
 */
public class Package extends AElement {
    protected int id;

    public Package(int id, Vec2d position) {
        super(position);
        this.id = id;
        body.setRadius(Rules.PACKAGERADIUS);
    }

    public int getId() {
        return id;
    }

    public Circle getBody() {
        return body;
    }
}
