package model;

/**
 * The following class has implementations for operations
 * that are common for any TypeOfImageObject.
 * It implements the operations in the TypeOfImage interface.
 */
public abstract class CommonOperations implements TypeOfImage {

  protected TypeofImageObject[][] pixels;
  protected int width;
  protected int height;

  /**
   * Constructor represents a TypeOfImage containing TypeofImageObject objects.
   *
   * @param pixels matrix of TypeofImageObject.
   * @param width  the width of the matrix.
   * @param height the height of the matrix.
   */
  protected CommonOperations(TypeofImageObject[][] pixels, int width, int height) {
    this.pixels = pixels;
    this.width = width;
    this.height = height;
  }

  @Override
  public TypeOfImage verticalFlip() {
    TypeofImageObject[][] flippedImage = getMatrix(this.width, this.height);
    //flip the columns
    for (int row = 0; row < this.pixels.length; row++) {
      for (int col = 0; col < this.pixels[row].length / 2; col++) {
        TypeofImageObject temp = this.pixels[row][col];
        flippedImage[row][col] = this.pixels[row][this.pixels[row].length - col - 1];
        flippedImage[row][flippedImage[row].length - col - 1] = temp;
      }
    }
    return getOImage(flippedImage, this.width, this.height);

  }

  @Override
  public TypeOfImage horizontalFlip() {
    TypeofImageObject[][] flippedImage = getMatrix(this.width, this.height);
    for (int col = 0; col < this.pixels[0].length; col++) {
      for (int row = 0; row < this.pixels.length / 2; row++) {
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
  public abstract TypeOfImage visualizeValueIntensityLuma(MeasurementType measure);

  /**
   * Abstract method creates an object of the class that extends this class.
   *
   * @param flippedImage matrix of TypeofImageObject.
   * @param width        width of matrix.
   * @param height       height of matrix.
   * @return an object of type TypeOfImage.
   */
  protected abstract TypeOfImage getOImage(TypeofImageObject[][] flippedImage,
      int width, int height);

  /**
   * Abstract method creates a matrix of the class that extends this class.
   *
   * @param width  width of the matrix.
   * @param height height of the matrix.
   * @return a matrix of TypeofImageObject.
   */
  protected abstract TypeofImageObject[][] getMatrix(int width, int height);

  @Override
  public boolean equals(Object o) {
    // Fast path for pointer equality:
    if (this == o) { //backward compatibility with default equals
      return true;
    }
    // Check if the type of o is the same as the type of "this"
    if (!(o instanceof TypeOfImage)) {
      return false;
    }
    TypeOfImage other = (TypeOfImage) o;
    if (this.getWidth() == other.getWidth() & this.getHeight() == other.getHeight()) {
      for (int i = 0; i < this.getWidth(); i++) {
        for (int j = 0; j < this.getHeight(); j++) {
          if (!this.getPixels()[i][j].equals(other.getPixels()[i][j])) {
            return false;
          }
        }
      }
    } else {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

}
