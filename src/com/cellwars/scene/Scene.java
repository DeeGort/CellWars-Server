package com.cellwars.scene;

import com.cellwars.actor.Cell;
import com.cellwars.actor.Cooky;
import com.cellwars.actor.Mine;
import com.sun.javafx.geom.Vec2d;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Tam�s on 2015-04-27.
 */
public class Scene {

    private List<Player> players;
    private List<Cooky> cookies;
    private List<Mine> mines;

    public class NoPlayersInitialized extends Exception {
        public NoPlayersInitialized(String message) {
            super(message);
        }
    }

    public class InvalidPlayer extends Exception {
        public InvalidPlayer(String message) {
            super(message);
        }
    }

    public class InvalidLocation extends Exception {
        public InvalidLocation(String message) {
            super(message);
        }
    }

    public void preparePlayes(List<Player> players){
        this.players = players;
    }

    public void initCookies() {
        cookies = new ArrayList<>();
        for (int i = 0; i != Rules.getRules().getMaxCooky(); i++) {
            cookies.add(spawnCookies(i));
        }
    }

    public void initMines() {
        mines = new ArrayList<>();
        for (int i = 0; i != Rules.getRules().getMaxMine(); i++) {
            mines.add(spawnMines(i));
        }
    }

    private Vec2d spawn() {
        Random random = new Random();

        int maxPosX = (int) (Rules.getRules().getMap().getX() + Rules.getRules().getMap().getWidth());
        int minPosX = (int) (Rules.getRules().getMap().getX());
        int maxPosY = (int) (Rules.getRules().getMap().getY() + Rules.getRules().getMap().getHeight());
        int minPosY = (int) (Rules.getRules().getMap().getY());

        float x = random.nextInt(maxPosX - minPosX) + minPosX;
        float y = random.nextInt(maxPosY - minPosY) + minPosY;

        return new Vec2d(x, y);
    }

    public Cell spawnCell() {
        return new Cell(spawn());
    }

    public Cooky spawnCookies(int id) {
        return new Cooky(id, spawn());
    }

    public Mine spawnMines(int id) {
        return new Mine(id, spawn());
    }

    public void spawnPlayers(int index) {
        players.get(index).initCell(spawnCell());
    }

    public void move(String playerName, Vec2d pos) throws NoPlayersInitialized, InvalidPlayer, InvalidLocation {

        if (players == null)
            throw new NoPlayersInitialized("No players initialized");

        Player player = null;
        for(Player p : players) {
            if (p.getName().equals(playerName)) {
                player = p;
                break;
            }
        }
        if (player == null)
            throw new InvalidPlayer("Invalid player");

        Point2D p2d = new Point2D(pos.x, pos.y);
        if (!Rules.getRules().getMap().convertToRectange().contains(p2d))
            throw new InvalidLocation("Invalid location");

        moveTo(player.getCell(), pos);
    }


    public void pickupCooky() {
        for (int i = 0; i != players.size(); i++)
            for (int j = 0; j != cookies.size(); j++)
                if (players.get(i).getCell().collide(cookies.get(j))) {
                    cookies.get(j).setPosition(spawn());
                    players.get(i).getCell().increaseRadius(Rules.getRules().getIncSize());
                }
    }

    public void pickupMine() {
        for (int i = 0; i != players.size(); i++)
            for (int j = 0; j != mines.size(); j++)
                if (players.get(i).getCell().collide(mines.get(j))) {
                    mines.get(j).setPosition(spawn());
                    players.get(i).getCell().decreaseRadius(Rules.getRules().getIncSize());
                }
    }

    public void attackPlayer() {
        for (int i = 0; i != players.size(); i++)
            for (int j = 0; j != players.size(); j++)
                if (i != j) {
                    if (players.get(i).getCell().collide(players.get(j).getCell())) {
                        if (players.get(i).getCell().getRadius() > players.get(j).getCell().getRadius()) {
                            players.get(j).initCell(new Cell(spawn()));
                            players.get(i).getCell().increaseRadius(Rules.getRules().getIncSize());
                            players.get(j).getCell().setDead(true);
                        }
                        else if (players.get(i).getCell().getRadius() < players.get(j).getCell().getRadius()) {
                            players.get(i).initCell(new Cell(spawn()));
                            players.get(j).getCell().increaseRadius(Rules.getRules().getIncSize());
                            players.get(i).getCell().setDead(true);
                        }
                    }
                }
    }

    public void moveTo(Cell cell, Vec2d newPosition) {

        Timeline t = new Timeline();
        t.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(1000),
                        new KeyValue(cell.getBody().centerXProperty(), newPosition.x, Interpolator.EASE_OUT),
                        new KeyValue(cell.getBody().centerYProperty(), newPosition.y, Interpolator.EASE_OUT)
                ));
        t.play();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Cooky> getCookies() {
        return cookies;
    }

    public List<Mine> getMines() {
        return mines;
    }
}
