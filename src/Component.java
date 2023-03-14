public enum Component {
  red("red"),
  blue("blue"),
  green("green");

  private final String channel;

  Component(String channel) {
    this.channel = channel;
  }

  @Override
  public String toString() {
    return channel;
  }
}