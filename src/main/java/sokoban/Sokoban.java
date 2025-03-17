package sokoban;

import dev.gamekit.core.Application;
import dev.gamekit.core.IO;
import dev.gamekit.core.Input;
import dev.gamekit.core.Renderer;
import dev.gamekit.core.Window;
import dev.gamekit.scene.Scene;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;

import static dev.gamekit.utils.MathUtils.toInt;
import static sokoban.Settings.TILE_SIZE;

public class Sokoban extends Scene {
  private static final Color CLEAR_COLOR = new Color(0xff2b2b2b);
  private static final BufferedImage HELP_IMG = IO.loadImageResource("sokoban.png");
  private static final BufferedImage COMPLETE_IMG = IO.loadImageResource("sokoban-thanks.png");
  private static final BufferedImage FLOOR_TILE = IO.loadImageResource("tiles/floor.png");
  private static final BufferedImage WALL_TILE = IO.loadImageResource("tiles/wall.png");
  private static final BufferedImage CRATE_TILE = IO.loadImageResource("tiles/crate.png");
  private static final BufferedImage CRATE_SET_TILE = IO.loadImageResource("tiles/crate-set.png");
  private static final BufferedImage MARKER_TILE = IO.loadImageResource("tiles/marker.png");

  Level level;
  Player player;
  int tileOffsetLeft = 0;
  int tileOffsetTop = 0;

  public Sokoban() {
    super("Level");
  }

  public static void main(String[] args) {
    Application app = new Application("Sokoban") { };
    Window.getInstance().maximize();
    app.loadScene(new Sokoban());
    app.run();
  }

  @Override
  public void onStart() {
    super.onStart();

    try {
      level = new Level(LevelData.LEVEL_1);
      tileOffsetLeft = toInt(TILE_SIZE * level.cols / 2.0) - TILE_SIZE / 2 - 256;
      tileOffsetTop = toInt(TILE_SIZE * level.rows / 2.0) - TILE_SIZE / 2;
      player = new Player(level, tileOffsetLeft, tileOffsetTop);
      addChild(player);
    } catch (URISyntaxException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void onUpdate() {
    super.onUpdate();

    if (Input.isKeyJustReleased(Input.KEY_SPACE)) {
      level.reset();
      player.reset();
    }
  }

  @Override
  public void onRender() {
    Renderer.setColor(CLEAR_COLOR);
    Renderer.clear();

    Renderer.drawImage(HELP_IMG, -400, 50, 518, 800);

    if (level != null) {
      for (int r = 0; r < level.rows; r++) {
        for (int c = 0; c < level.cols; c++) {
          int x = c * TILE_SIZE - tileOffsetLeft;
          int y = r * TILE_SIZE - tileOffsetTop;
          int idx = r * level.cols + c;
          Tile tile = level.grid[idx];
          int crateTile = level.crateGrid[idx];
          int markerTile = level.markerGrid[idx];

          if (tile == Tile.FLOOR) {
            Renderer.drawImage(FLOOR_TILE, x, -y, TILE_SIZE, TILE_SIZE);
          } else if (tile == Tile.WALL) {
            Renderer.drawImage(WALL_TILE, x, -y, TILE_SIZE, TILE_SIZE);
          }

          if (markerTile == 1) {
            Renderer.drawImage(MARKER_TILE, x, -y, TILE_SIZE, TILE_SIZE);
          }

          if (crateTile == 1) {
            Renderer.drawImage(
              markerTile == 1 ? CRATE_SET_TILE : CRATE_TILE,
              x, -y, TILE_SIZE, TILE_SIZE
            );
          }
        }
      }
    }

    super.onRender();

    if (level.isSolved())
      Renderer.drawImage(COMPLETE_IMG, 0, 0, 1500, 556);
  }
}
