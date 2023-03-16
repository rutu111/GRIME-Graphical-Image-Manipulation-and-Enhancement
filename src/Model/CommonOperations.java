package Model;

public abstract class CommonOperations implements TypeOfImage {

  protected TypeofImageObject[][] pixels;
  protected int width;
  protected int height;

  protected CommonOperations(TypeofImageObject[][] pixels, int width, int height) {
    this.pixels = pixels;
    this.width = width;
    this.height = height;
  }

  public TypeOfImage verticalFlip() {
    TypeofImageObject[][] flippedImage = getMatrix(this.width, this.height);
    //flip the columns
    for(int row = 0; row < this.pixels.length; row++){
      for(int col = 0; col < this.pixels[row].length/ 2; col++) {
        TypeofImageObject temp = this.pixels[row][col];
        flippedImage[row][col] = this.pixels[row][this.pixels[row].length - col - 1];
        flippedImage[row][flippedImage[row].length - col - 1] = temp;
      }
    }
    return getOImage(flippedImage, this.width, this.height);

  }

  public TypeOfImage horizontalFlip() {
    TypeofImageObject[][] flippedImage = getMatrix(this.width, this.height);
    for(int col = 0;col < this.pixels[0].length; col++){
      for(int row = 0; row < this.pixels.length/2; row++) {
        TypeofImageObject temp = this.pixels[row][col];
        flippedImage[row][col] = this.pixels[this.pixels.length - row - 1][col];
        flippedImage[flippedImage.length - row - 1][col] = temp;
      }
    }
    return getOImage(flippedImage, this.width, this.height);
  }

  @Override
  public abstract TypeOfImage brighten(double increment);

  @Override
  public abstract TypeOfImage visIndividualComponent(ComponentRGB channel)
      throws NoSuchFieldException, IllegalAccessException;

  @Override
  public abstract TypeOfImage visualizeValueIntensityLuma(MeasurementType measure)
      throws NoSuchFieldException, IllegalAccessException;

  public abstract TypeOfImage getOImage(TypeofImageObject[][] flippedImage, int width, int height);
  protected abstract TypeofImageObject[][] getMatrix(int width, int height);




}
