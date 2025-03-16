package sokoban;

import dev.gamekit.core.IO;
import dev.gamekit.core.Input;
import dev.gamekit.core.Renderer;
import dev.gamekit.scene.Prop;

import java.awt.image.BufferedImage;

public class Player extends Prop {
  private static final int TILE_SIZE = 60;
  private static final BufferedImage PLAYER_UP = IO.loadImage("tiles/player-up.png");
  private static final BufferedImage PLAYER_RIGHT = IO.loadImage("tiles/player-right.png");
  private static final BufferedImage PLAYER_DOWN = IO.loadImage("tiles/player-down.png");
  private static final BufferedImage PLAYER_LEFT = IO.loadImage("tiles/player-left.png");

  final int offsetLeft;
  final int offsetTop;

  int row;
  int col;
  BufferedImage image = PLAYER_RIGHT;

  Player(int row, int col, int offsetLeft, int offsetTop) {
    super("Player");
    this.row = row;
    this.col = col;
    this.offsetLeft = offsetLeft;
    this.offsetTop = offsetTop;
  }

  @Override
  protected void onUpdate() {
    super.onUpdate();

    if (Input.isKeyJustPressed(Input.KEY_UP)) {
      image = PLAYER_UP;
    } else if (Input.isKeyJustPressed(Input.KEY_RIGHT)) {
      image = PLAYER_RIGHT;
    } else if (Input.isKeyJustPressed(Input.KEY_DOWN)) {
      image = PLAYER_DOWN;
    } else if (Input.isKeyJustPressed(Input.KEY_LEFT)) {
      image = PLAYER_LEFT;
    }
  }

  @Override
  protected void onRender() {
    super.onRender();

    int x = col * TILE_SIZE - offsetLeft;
    int y = row * TILE_SIZE - offsetTop;
    Renderer.drawImage(image, x, -y, TILE_SIZE, TILE_SIZE);
  }
}
