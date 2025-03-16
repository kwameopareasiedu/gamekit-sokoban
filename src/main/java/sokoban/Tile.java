package sokoban;

public enum Tile {
  FLOOR, WALL;

  static Tile parse(String rep) {
    return switch (rep.toLowerCase()) {
      case "w" -> WALL;
      case "f" -> FLOOR;
      default -> throw new IllegalArgumentException("Invalid tile");
    };
  }
}
