package sokoban;

import java.io.IOException;
import java.net.URISyntaxException;

public class Level {
  public final int rows;
  public final int cols;
  public final int playerRow;
  public final int playerCol;
  public final Tile[] grid;
  public final int[] crateGrid;
  public final int[] markerGrid;

  public Level(LevelData data) throws URISyntaxException, IOException {
    rows = data.rows;
    cols = data.cols;
    playerRow = data.playerRow;
    playerCol = data.playerCol;

    grid = new Tile[data.grid.length];
    crateGrid = new int[data.crateGrid.length];
    markerGrid = new int[data.markerGrid.length];

    System.arraycopy(data.grid, 0, grid, 0, data.grid.length);
    System.arraycopy(data.crateGrid, 0, crateGrid, 0, data.crateGrid.length);
    System.arraycopy(data.markerGrid, 0, markerGrid, 0, data.markerGrid.length);
  }

  boolean isTileFree(int row, int col) {
    int idx = row * cols + col;
    return grid[idx] == Tile.FLOOR && crateGrid[idx] == 0;
  }

  boolean isTileCrate(int row, int col) {
    int idx = row * cols + col;
    return crateGrid[idx] == 1;
  }

  void swapCrateTiles(int fromRow, int fromCol, int toRow, int toCol) {
    int fromIdx = fromRow * cols + fromCol;
    int toIdx = toRow * cols + toCol;

    int temp = crateGrid[toIdx];
    crateGrid[toIdx] = crateGrid[fromIdx];
    crateGrid[fromIdx] = temp;
  }
}
