public class threeChannelObject implements TypeofImageObject {

  public final int red;
  public final int green;
  public final int blue;

  public threeChannelObject(int red, int green, int blue) throws IllegalArgumentException {
    this.red = red;
    this.green = green;
    this.blue = blue;
  }


  @Override
  public int getChanne11() {
    return this.red;
  }

  @Override
  public int getChanne12() {
    return this.green;
  }

  @Override
  public int getChanne13() {
    return this.blue;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof threeChannelObject)) {
      return false;
    }

    threeChannelObject other = (threeChannelObject) o;
    return this.getChanne11() == other.getChanne11() & this.getChanne12() == other.getChanne12()
        & this.getChanne13() == other.getChanne13();
  }
}
