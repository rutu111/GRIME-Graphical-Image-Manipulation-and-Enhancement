public class threeChannelObject implements TypeofImageObject{

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
}
