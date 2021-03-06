package com.cellwars.server;

import com.cellwars.actor.Cooky;
import com.cellwars.actor.Mine;
import com.cellwars.scene.Player;
import com.cellwars.scene.Rules;
import com.cellwars.scene.Scene;
import com.cellwars.xml.jaxb.JaxbCreateRules;
import com.cellwars.xml.jaxb.JaxbRulesLoader;
import com.sun.javafx.geom.Vec2d;
import javafx.concurrent.Task;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by Tamás on 2015-04-13.
 */
public class Server {

    private Scene scene;
    private List<Player> players;
    private ServerConnection serverConnection;
    private CommandLine commandLine;
    private boolean waitLoading;
    private int player = 0;


    public Server(CommandLine commandLine) {
        this.commandLine = commandLine;
    }

    public void start() {
        new Thread(task).start();
    }

    protected Task task = new Task<Void>() {

        @Override
        protected Void call() {

            serverConnection = new ServerConnection();

            if (createServer()) {

                initRules();
                createScene();
                createMap();
                preparePlayers();
                spawningCookies();
                spawningMines();

                while(true) {
                    waitForClient(player);
                    loadPlayer(player);
                    sendRules(player);
                    loadPlayers(player);
                    gameLoop(player);
                    player++;
                }
            }

            return null;
        }

        private boolean createServer() {
            try {
                commandLine.print("Starting server...");
                serverConnection.startServer(3000);
                return true;

            } catch (IOException e) {
                commandLine.print(e.getMessage());

                return false;
            }
        }

        private void initRules() {
            commandLine.print("Initialize rules...");

            if (new File("rules.xml").isFile()) {
                JaxbRulesLoader loadRules = new JaxbRulesLoader("rules.xml");
                loadRules.load();
            } else {
                JaxbCreateRules createRules = new JaxbCreateRules("rules.xml");
                createRules.save();
                JaxbRulesLoader loadRules = new JaxbRulesLoader("rules.xml");
                loadRules.load();
            }

            /*if (new File("rules.xml").isFile()) {
                DomRulesLoader loadRules = new DomRulesLoader("rules.xml");
                loadRules.load();
            } else {
                DomCreateRules createRules = new DomCreateRules("rules.xml");
                createRules.save();
                DomRulesLoader loadRules = new DomRulesLoader("rules.xml");
                loadRules.load();
            }*/
        }

        private void createScene() {
            commandLine.print("Creating scene...");

            scene = new Scene();
        }

        private void createMap() {
            commandLine.print("Creating map...");
            Rules.getRules().setMap(0, 0, 1024, 768);
        }

        private void preparePlayers() {

            commandLine.print("Preparing players...");
            players = new ArrayList<>();
            scene.preparePlayes(players);

        }

        private void spawningCookies() {
            commandLine.print("Spawning cookies...");

            scene.initCookies();
        }

        private void spawningMines() {
            commandLine.print("Spawning mines...");

            scene.initMines();
        }


        private void waitForClient(int player) {
            try {
                commandLine.print("Waiting for clients...");
                serverConnection.waitForClient();
                commandLine.print("Connection established...");
                serverConnection.connectionEstablished(player);
                waitLoading = true;

            } catch (IOException e) {
                commandLine.print(e.getMessage());
            }
        }

        private void loadPlayer(int player) {
            try {
                //  <=
                String [] playerinfo = serverConnection.getMessage(player).split(":");
                players.stream().filter(p -> p.getName().equals(playerinfo[1])).forEach(p -> playerinfo[1] = playerinfo[1] + "-noob");

                players.add(new Player(playerinfo[1], Color.web(playerinfo[2])));

            } catch (IOException e) {
                commandLine.print(e.getMessage());
            }
        }


        private void sendRules(int player) {
            try {
                commandLine.print("Sending rules to clients...");

                //  =>
                serverConnection.sendMessage(player,
                        "RULES",
                        Rules.getRules().getMap().getX() + " " + Rules.getRules().getMap().getY() + " " + Rules.getRules().getMap().getWidth() + " " + Rules.getRules().getMap().getHeight(),
                        Double.toString(Rules.getRules().getCellRadius()),
                        Double.toString(Rules.getRules().getPackageRadius())
                );

            } catch (IOException e) {
                commandLine.print(e.getMessage());
            }
        }

        private void loadPlayers(int player) {
            try {
                commandLine.print("Loading players...");


                scene.spawnPlayers(player);

                //  <= NULL
                serverConnection.getMessage(player);
                //  =>
                serverConnection.sendMessage(player,
                        "CELL",
                        players.get(player).getName(),
                        Double.toString(players.get(player).getCell().getPosition().x),
                        Double.toString(players.get(player).getCell().getPosition().y)
                );
            } catch (IOException e) {
                commandLine.print(e.getMessage());
            }
        }


        private void gameLoop(int player) {
            commandLine.print("Play!");

            Task gameloop = new Task<Void>() {

                @Override
                protected Void call() {
                    while (true) {
                        while(waitLoading)
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                        }

                        try {

                            //  <=  =>
                            serverConnection.sendMessage(player, interpret(serverConnection.getMessage(player)));

                            scene.pickupCooky();
                            scene.pickupMine();
                            scene.attackPlayer();

                        } catch (IOException e) {
                            commandLine.print(e.getMessage());
                        }
                    }
                }
            };
            waitLoading = false;
            new Thread(gameloop).start();
        }

        private synchronized String interpret(String clientMessage) {
            try {
                String[] request = clientMessage.split(":");
                if (request.length > 0) {
                        switch (request[0]) {
                            case "MOVE":
                                scene.move(request[1], new Vec2d(Double.parseDouble(request[2]), Double.parseDouble(request[3])));
                                break;
                            case "NULL":
                                break;
                            case "GETPLAYERS":
                                StringJoiner sj = new StringJoiner(":");
                                sj.add(new String("PLAYERS"));
                                for (Player p : players) {
                                    sj.add(p.getName())
                                            .add(Double.toString(p.getCell().getPosition().x))
                                            .add(Double.toString(p.getCell().getPosition().y))
                                            .add(p.getColor().toString())
                                            .add(Double.toString(p.getCell().getRadius()))
                                            .add(Boolean.toString(p.getCell().isDead()));
                                    if (p.getCell().isDead())
                                        p.getCell().setDead(false);
                                }
                                return sj.toString();
                            case "GETCOOKIES":
                                StringJoiner sj1 = new StringJoiner(":");
                                sj1.add(new String("COOKIES"));
                                for (Cooky c : scene.getCookies()) {
                                    sj1.add(Integer.toString(c.getId()));
                                    sj1.add(Double.toString(c.getPosition().x));
                                    sj1.add(Double.toString(c.getPosition().y));
                                }
                                return sj1.toString();
                            case "GETMINES":
                                StringJoiner sj2 = new StringJoiner(":");
                                sj2.add(new String("MINES"));
                                for (Mine b : scene.getMines()) {
                                    sj2.add(Integer.toString(b.getId()));
                                    sj2.add(Double.toString(b.getPosition().x));
                                    sj2.add(Double.toString(b.getPosition().y));
                                }
                                return sj2.toString();
                        }
                }

                commandLine.print(clientMessage);

                return "OK";
            } catch (Scene.NoPlayersInitialized | Scene.InvalidLocation | Scene.InvalidPlayer e) {
                commandLine.print(e.getMessage());
                return "ERROR:" + e.getMessage();
            }
        }


    };

    public void stop() {
        System.exit(0);
    }

}
