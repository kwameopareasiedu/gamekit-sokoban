package sokoban;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Objects;

public class Level {
  public final int rows;
  public final int cols;
  public final Tile[] tileGrid;
  public final int[] startCrateGrid;
  public final int[] endCrateGrid;

  public Level(String levelFilePath) throws URISyntaxException, IOException {
    URL fileUrl = Level.class.getClassLoader().getResource(levelFilePath);
    URI fileUri = Objects.requireNonNull(fileUrl).toURI();
    File file = new File(fileUri);
    BufferedReader reader = Files.newBufferedReader(file.toPath());

    rows = Integer.parseInt(reader.readLine());
    cols = Integer.parseInt(reader.readLine());
    tileGrid = new Tile[rows * cols];
    startCrateGrid = new int[rows * cols];
    endCrateGrid = new int[rows * cols];
    reader.readLine();

    for (int r = 0; r < rows; r++) {
      String line = reader.readLine();
      String[] tileCodes = line.split(",");

      for (int c = 0; c < cols; c++) {
        tileGrid[r * cols + c] = Tile.parse(tileCodes[c]);
      }
    }

    reader.readLine();

    for (int r = 0; r < rows; r++) {
      String line = reader.readLine();
      String[] cratePresence = line.split(",");

      for (int c = 0; c < cols; c++) {
        startCrateGrid[r * cols + c] = Integer.parseInt(cratePresence[c]);
      }
    }

    reader.readLine();

    for (int r = 0; r < rows; r++) {
      String line = reader.readLine();
      String[] cratePresence = line.split(",");

      for (int c = 0; c < cols; c++) {
        endCrateGrid[r * cols + c] = Integer.parseInt(cratePresence[c]);
      }
    }

    reader.close();
  }
}
