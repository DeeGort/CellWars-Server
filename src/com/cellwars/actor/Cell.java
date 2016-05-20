package com.cellwars.actor;

import com.cellwars.scene.Rules;
import com.sun.javafx.geom.Vec2d;

/**
 * Created by Tam�s on 2015-04-27.
 */
public class Cell extends AElement {

    protected boolean dead;

    public Cell(Vec2d position) {
        super(position);
        body.setRadius(Rules.getRules().getCellRadius());
        dead = false;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }
}
