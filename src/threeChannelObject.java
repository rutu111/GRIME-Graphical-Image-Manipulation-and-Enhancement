public class threeChannelObject extends ImageBuilder<threeChannelObject> implements TypeofImageObject{

  final int red;
  final int green;
  final int blue;

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
