package com.cellwars.scene;

import com.cellwars.actor.Map;
import javafx.scene.shape.Rectangle;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Tam�s on 2015-04-27.
 */

@XmlRootElement
public class Rules {
    private Map map;
    private float cellRadius;
    private float packageRadius;
    private int maxCooky;
    private int maxMine;
    private float incSize;

    private static Rules instance = null;

    protected Rules() {
        map = new Map(0, 0, 0, 0);
    }

    public static void initRules(Rules rules) {
        getRules().map = rules.getMap();
        getRules().cellRadius = rules.getCellRadius();
        getRules().packageRadius = rules.getPackageRadius();
        getRules().maxCooky = rules.getMaxCooky();
        getRules().maxMine = rules.getMaxMine();
        getRules().incSize = rules.getIncSize();
    }

    public static Rules getRules() {
        if(instance == null) {
            synchronized (Rules.class) {
                if (instance == null)
                    instance = new Rules();
            }
        }
        return instance;
    }

    public Map getMap() {
        return map;
    }

    public Rectangle getRectangleMap() {
        return new Rectangle(map.getX(), map.getY(), map.getWidth(), map.getHeight());
    }

    @XmlElement
    public void setMap(Map map) {
        this.map = map;
    }

    public void setMap(double x, double y, double width, double height) {
        map.setX(x);
        map.setY(y);
        map.setWidth(width);
        map.setHeight(height);
    }

    public float getCellRadius() {
        return cellRadius;
    }

    @XmlElement
    public void setCellRadius(float cellRadius) {
        this.cellRadius = cellRadius;
    }

    public float getPackageRadius() {
        return packageRadius;
    }

    @XmlElement
    public void setPackageRadius(float packageRadius) {
        this.packageRadius = packageRadius;
    }

    public int getMaxCooky() {
        return maxCooky;
    }

    @XmlElement
    public void setMaxCooky(int maxCooky) {
        this.maxCooky = maxCooky;
    }

    public int getMaxMine() {
        return maxMine;
    }

    @XmlElement
    public void setMaxMine(int maxMine) {
        this.maxMine = maxMine;
    }

    public float getIncSize() {
        return incSize;
    }

    @XmlElement
    public void setIncSize(float incSize) {
        this.incSize = incSize;
    }

}

