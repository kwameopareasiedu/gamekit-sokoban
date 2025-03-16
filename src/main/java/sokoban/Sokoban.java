package sokoban;

import dev.gamekit.core.Application;
import dev.gamekit.core.IO;
import dev.gamekit.core.Renderer;
import dev.gamekit.scene.Scene;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;

import static dev.gamekit.utils.MathUtils.toInt;

public class Sokoban extends Scene {
  private static final int TILE_SIZE = 80;
  private static final Color CLEAR_COLOR = new Color(0xff536c6c);
  private static final BufferedImage FLOOR_TILE = IO.loadImage("tiles/floor.png");
  private static final BufferedImage WALL_TILE = IO.loadImage("tiles/wall.png");
  private static final BufferedImage CRATE_TILE = IO.loadImage("tiles/crate.png");
  private static final BufferedImage CRATE_SET_TILE = IO.loadImage("tiles/crate-set.png");
  private static final BufferedImage MARKER_TILE = IO.loadImage("tiles/marker.png");

  Level level;
  int[] crateGrid;
  int[] markerGrid;

  public Sokoban() {
    super("Level");
  }

  public static void main(String[] args) {
    Application app = new Application("Sokoban") { };
    app.loadScene(new Sokoban());
    app.run();
  }

  @Override
  public void onStart() {
    super.onStart();

    try {
      level = new Level("level-1.txt");
      crateGrid = new int[level.crateGrid.length];
      markerGrid = new int[level.crateGrid.length];

      System.arraycopy(level.crateGrid, 0, crateGrid, 0, level.crateGrid.length);
      System.arraycopy(level.markerGrid, 0, markerGrid, 0, level.crateGrid.length);
    } catch (URISyntaxException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void onRender() {
    super.onRender();
    Renderer.setColor(CLEAR_COLOR);
    Renderer.clear();

    if (level != null) {
      int tileOffsetLeft = toInt(TILE_SIZE * level.cols / 2.0) - TILE_SIZE / 2;
      int tileOffsetTop = toInt(TILE_SIZE * level.rows / 2.0);

      for (int r = 0; r < level.rows; r++) {
        for (int c = 0; c < level.cols; c++) {
          int x = c * TILE_SIZE - tileOffsetLeft;
          int y = r * TILE_SIZE - tileOffsetTop;
          int idx = r * level.cols + c;
          Tile tile = level.grid[idx];
          int tileCrate = crateGrid[idx];
          int tileMarker = markerGrid[idx];

          if (tile == Tile.FLOOR) {
            Renderer.drawImage(FLOOR_TILE, x, -y, TILE_SIZE, TILE_SIZE);
          } else if (tile == Tile.WALL) {
            Renderer.drawImage(WALL_TILE, x, -y, TILE_SIZE, TILE_SIZE);
          }

          if (tileMarker == 1) {
            Renderer.drawImage(MARKER_TILE, x, -y, TILE_SIZE, TILE_SIZE);
          }

          if (tileCrate == 1) {
            Renderer.drawImage(
              tileMarker == 1 ? CRATE_SET_TILE : CRATE_TILE,
              x, -y, TILE_SIZE, TILE_SIZE
            );
          }
        }
      }
    }
  }
}
