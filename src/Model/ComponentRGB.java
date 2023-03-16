package Model;

/**
 * This is an enum to represent rgb components.
 */
public enum ComponentRGB {
  red("red"),
  green("green"),
  blue("blue");

  private final String channel;

  ComponentRGB(String channel) {
    this.channel = channel;
  }

  @Override
  public String toString() {
    return channel;
  }
}