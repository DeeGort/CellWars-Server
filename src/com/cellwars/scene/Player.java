package com.cellwars.scene;

import com.cellwars.actor.Cell;
import javafx.scene.paint.Color;

/**
 * Created by Tam�s on 2015-04-27.
 */
public class Player {

    private String name;
    private Color color;
    private Cell cell;

    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public Cell getCell() {
        return cell;
    }

    public void initCell(Cell cell) {
        this.cell = cell;
    }
}
