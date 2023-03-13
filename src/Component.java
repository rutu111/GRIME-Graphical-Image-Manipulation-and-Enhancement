public enum Component {
  Red("red"),
  Blue("blue"),
  Green("green");

  private final String channel;

  Component(String channel) {
    this.channel = channel;
  }

  @Override
  public String toString() {
    return channel;
  }
}