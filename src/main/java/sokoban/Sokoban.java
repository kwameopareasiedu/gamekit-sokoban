package sokoban;

import dev.gamekit.core.Application;
import dev.gamekit.core.Renderer;
import dev.gamekit.scene.Scene;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class Sokoban extends Scene {
  private static int TILE_WIDTH = 40;

  Level level;

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
    } catch (URISyntaxException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void onRender() {
    super.onRender();
    Renderer.setColor(Color.BLACK);
    Renderer.clear();
  }
}
