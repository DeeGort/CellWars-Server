package test.scene;

import com.cellwars.actor.Cell;
import com.cellwars.actor.Cooky;
import com.cellwars.actor.Mine;
import com.cellwars.scene.Player;
import com.cellwars.scene.Rules;
import com.cellwars.scene.Scene;
import com.sun.javafx.geom.Vec2d;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertTrue;


public class SceneTest {

    private Scene scene;

    @Before
    public void setup() {
        scene = new Scene();
    }

    @Test
    public void testSpawnCell() {
        Random rand = new Random();
        for (int i = 0; i < 10000; i++) {
            Rules.getRules().setMap(rand.nextInt(2500), rand.nextInt(2500), rand.nextInt(2500) + 1, rand.nextInt(2500) + 1);
            Cell cell = scene.spawnCell();

            assertTrue(Rules.getRules().getMap().convertToRectange().contains(new Point2D(cell.getPosition().x, cell.getPosition().y)));
        }
    }

    @Test
    public void testSpawnCookies() {
        Random rand = new Random();
        for (int i = 0; i < 10000; i++) {
            Rules.getRules().setMap(rand.nextInt(2500), rand.nextInt(2500), rand.nextInt(2500) + 1, rand.nextInt(2500) + 1);
            Cooky cooky = scene.spawnCookies(rand.nextInt());

            assertTrue(Rules.getRules().getMap().convertToRectange().contains(new Point2D(cooky.getPosition().x, cooky.getPosition().y)));
        }
    }

    @Test
    public void testSpawnMines() {
        Random rand = new Random();
        for (int i = 0; i < 10000; i++) {
            Rules.getRules().setMap(rand.nextInt(2500), rand.nextInt(2500), rand.nextInt(2500) + 1, rand.nextInt(2500) + 1);
            Mine mine = scene.spawnMines(rand.nextInt());

            assertTrue(Rules.getRules().getMap().convertToRectange().contains(new Point2D(mine.getPosition().x, mine.getPosition().y)));
        }
    }

    @Test(expected = Scene.NoPlayersInitialized.class)
    public void testNoPlayersInitialized() throws Scene.InvalidPlayer, Scene.InvalidLocation, Scene.NoPlayersInitialized {
        Rules.getRules().setMap(0, 0, 500, 500);

        scene.move("Leves", new Vec2d(100, 100));
    }

    @Test(expected = Scene.InvalidPlayer.class)
    public void testInvalidPlayerMove() throws Scene.InvalidLocation, Scene.NoPlayersInitialized, Scene.InvalidPlayer {
        Rules.getRules().setMap(0, 0, 500, 500);

        scene.preparePlayes(Arrays.asList( new Player("Fozelek", Color.AQUAMARINE)));
        scene.spawnPlayers(0);

        scene.move("Leves", new Vec2d(100, 100));
    }

    @Test
    public void testValidPlayerMove() throws Scene.InvalidLocation, Scene.NoPlayersInitialized, Scene.InvalidPlayer {
        Rules.getRules().setMap(0, 0, 500, 500);

        scene.preparePlayes(Arrays.asList( new Player("Leves", Color.AQUAMARINE)));
        scene.spawnPlayers(0);

        scene.move("Leves", new Vec2d(100, 100));
    }

}