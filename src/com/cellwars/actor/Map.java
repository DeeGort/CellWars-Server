package com.cellwars.actor;

import javafx.scene.shape.Rectangle;

/**
 * Created by DeeGort on 2016-05-16.
 */
public class Map {
    private double x;
    private double y;
    private double width;
    private double height;

    public Map(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Map() {
        this.x = 0;
        this.y = 0;
        this.width = 0;
        this.height = 0;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Rectangle convertToRectange() {
        return new Rectangle(x, y, width, height);
    }
}