package sokoban;

import java.io.IOException;
import java.net.URISyntaxException;

public class Level {
  public final Tile[] grid;
  public final int[] crateGrid;
  public final int[] markerGrid;
  private final LevelData data;
  public int rows;
  public int cols;
  public int playerRow;
  public int playerCol;
  private boolean solved;


  public Level(LevelData data) throws URISyntaxException, IOException {
    this.data = data;
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

  public boolean isSolved() {
    return solved;
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

    boolean solved = true;

    for (int i = 0; i < crateGrid.length; i++) {
      if (crateGrid[i] != markerGrid[i]) {
        solved = false;
        break;
      }
    }

    this.solved = solved;
  }

  void reset() {
    rows = data.rows;
    cols = data.cols;
    playerRow = data.playerRow;
    playerCol = data.playerCol;
    solved = false;

    System.arraycopy(data.grid, 0, grid, 0, data.grid.length);
    System.arraycopy(data.crateGrid, 0, crateGrid, 0, data.crateGrid.length);
    System.arraycopy(data.markerGrid, 0, markerGrid, 0, data.markerGrid.length);
  }
}
