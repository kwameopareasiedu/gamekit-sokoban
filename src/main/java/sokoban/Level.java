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
  public final Tile[] grid;
  public final int[] crateGrid;
  public final int[] markerGrid;

  public Level(String levelFilePath) throws URISyntaxException, IOException {
    URL fileUrl = Level.class.getClassLoader().getResource(levelFilePath);
    URI fileUri = Objects.requireNonNull(fileUrl).toURI();
    File file = new File(fileUri);
    BufferedReader reader = Files.newBufferedReader(file.toPath());

    rows = Integer.parseInt(reader.readLine());
    cols = Integer.parseInt(reader.readLine());
    grid = new Tile[rows * cols];
    crateGrid = new int[rows * cols];
    markerGrid = new int[rows * cols];
    reader.readLine();

    for (int r = 0; r < rows; r++) {
      String line = reader.readLine();
      String[] tileRow = line.split(",");

      for (int c = 0; c < cols; c++) {
        grid[r * cols + c] = Tile.parse(tileRow[c]);
      }
    }

    reader.readLine();

    for (int r = 0; r < rows; r++) {
      String line = reader.readLine();
      String[] crateRow = line.split(",");

      for (int c = 0; c < cols; c++) {
        crateGrid[r * cols + c] = Integer.parseInt(crateRow[c]);
      }
    }

    reader.readLine();

    for (int r = 0; r < rows; r++) {
      String line = reader.readLine();
      String[] markerRow = line.split(",");

      for (int c = 0; c < cols; c++) {
        markerGrid[r * cols + c] = Integer.parseInt(markerRow[c]);
      }
    }

    reader.close();
  }
}
