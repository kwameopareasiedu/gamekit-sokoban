package sokoban;

import dev.gamekit.core.IO;
import dev.gamekit.core.Input;
import dev.gamekit.core.Renderer;
import dev.gamekit.scene.Prop;

import java.awt.image.BufferedImage;

import static sokoban.Settings.TILE_SIZE;

public class Player extends Prop {
  private static final MoveMode MOVE_MODE = MoveMode.FACE_AND_MOVE;
  private static final BufferedImage PLAYER_UP = IO.loadImage("tiles/player-up.png");
  private static final BufferedImage PLAYER_RIGHT = IO.loadImage("tiles/player-right.png");
  private static final BufferedImage PLAYER_DOWN = IO.loadImage("tiles/player-down.png");
  private static final BufferedImage PLAYER_LEFT = IO.loadImage("tiles/player-left.png");

  final Level level;
  final int offsetLeft;
  final int offsetTop;
  int row;
  int col;
  Facing facing = Facing.RIGHT;

  Player(Level level, int offsetLeft, int offsetTop) {
    super("Player");
    this.level = level;
    this.row = level.playerRow;
    this.col = level.playerCol;
    this.offsetLeft = offsetLeft;
    this.offsetTop = offsetTop;
  }

  @Override
  protected void onUpdate() {
    super.onUpdate();

    if (!level.isSolved()) {
      if (Input.isKeyJustPressed(Input.KEY_UP)) {
        move(Facing.UP, row - 1, col);
      } else if (Input.isKeyJustPressed(Input.KEY_RIGHT)) {
        move(Facing.RIGHT, row, col + 1);
      } else if (Input.isKeyJustPressed(Input.KEY_DOWN)) {
        move(Facing.DOWN, row + 1, col);
      } else if (Input.isKeyJustPressed(Input.KEY_LEFT)) {
        move(Facing.LEFT, row, col - 1);
      }
    }
  }

  void reset() {
    row = level.playerRow;
    col = level.playerCol;
    facing = Facing.RIGHT;
  }

  private void move(Facing facing, int row, int col) {
    if (this.facing != facing) {
      this.facing = facing;

      if (MOVE_MODE == MoveMode.FACE_BEFORE_MOVE)
        return;
    }


    if (level.isTileFree(row, col)) {
      this.row = row;
      this.col = col;
    } else if (level.isTileCrate(row, col)) {
      int newCrateRow = row + (row - this.row);
      int newCrateCol = col + (col - this.col);

      if (level.isTileFree(newCrateRow, newCrateCol)) {
        level.swapCrateTiles(row, col, newCrateRow, newCrateCol);

        this.row = row;
        this.col = col;

      }
    }

  }

  @Override
  protected void onRender() {
    super.onRender();

    int x = col * TILE_SIZE - offsetLeft;
    int y = row * TILE_SIZE - offsetTop;

    Renderer.drawImage(
      switch (facing) {
        case UP -> PLAYER_UP;
        case RIGHT -> PLAYER_RIGHT;
        case DOWN -> PLAYER_DOWN;
        case LEFT -> PLAYER_LEFT;
      },
      x, -y, TILE_SIZE, TILE_SIZE
    );
  }

  private enum Facing {
    UP, RIGHT, DOWN, LEFT
  }

  private enum MoveMode {
    FACE_AND_MOVE, FACE_BEFORE_MOVE
  }
}
