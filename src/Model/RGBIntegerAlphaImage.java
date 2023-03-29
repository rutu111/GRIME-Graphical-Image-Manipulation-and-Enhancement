package Model;


import java.lang.reflect.Field;

/**
 * The following class represents an RGBA image. This extends
 * ThreeChannelObjectOperations because this is a 3 channel image
 * (we do not any special operations on alpha).
 * <p>
 * The matrix represented in this class is of TypeofImageObject
 * which contains object of type RGBIntegerAlphaObjects.
 * This matrix can be created through the builder (inner class) or directly
 * through the constructor (constructor is private so object
 * can only be created from inside the
 * class or through bulder).
 */
public class RGBIntegerAlphaImage extends ThreeChannelObjectOperations  {

  /**
   * Creates a matrix containing RGBIntegerAlphaObjects.
   *
   * @param pixels matrix of type RGBIntegerAlphaObjects
   * @param width  width of the image.
   * @param height height of the image.
   */
  private RGBIntegerAlphaImage(TypeofImageObject[][] pixels, int width, int height) {
    super(pixels, width, height);
  }

  @Override
  protected Field getField(ComponentRGB channel) throws NoSuchFieldException {
    return RGBIntegerAlphaObject.class.getDeclaredField(channel.toString());
  }

  @Override
  protected TypeofImageObject[][] getMatrix(int width, int height) {
    return new RGBIntegerAlphaObject[this.getWidth()][this.getHeight()];
  }

  @Override
  protected TypeofImageObject getObject(int value1, int value2, int value3, Integer value4) {
    return new RGBIntegerAlphaObject(value1, value2, value3, value4);
  }

  @Override
  public TypeofImageObject[][] getPixels() {
    return this.pixels;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public TypeOfImage getOImage(TypeofImageObject[][] flippedImage, int width, int height) {
    return new RGBIntegerAlphaImage(flippedImage, width, height);
  }


  /**
   * The following is a builder class that builds a RGBIntegerAlphaImage
   * containing objects of type RGBIntegerAlphaObjects.
   */
  public static class RGBIntegerAlphaImageBuilder extends ImageBuilder<RGBIntegerAlphaObject> {


    /**
     * The constructor takes in a width and height and creates a default matrix
     * of that size in the constructor. The addPixelByPosition method
     * is used subsequently to manipulate the default matrix created.
     *
     * @param width  width of the matrix..
     * @param height height of the matrix.
     * @throws IllegalArgumentException
     */
    public RGBIntegerAlphaImageBuilder(int width, int height) throws IllegalArgumentException {
      super(width, height);
      if (width <= 0 | height <= 0) {
        throw new IllegalArgumentException("Width and height need to be greater then 0. ");
      }
      this.pixels = new RGBIntegerAlphaObject[width][height];
      for (int i = 0; i < width; i++) {
        for (int j = 0; j < height; j++) {
          this.pixels[i][j] = new RGBIntegerAlphaObject(0, 0, 0, 0); // Dummy RGB value

        }
      }
    }

    @Override
    public void addPixelAtPosition(int width, int height, RGBIntegerAlphaObject pixel) {
      if (this.pixels == null) {
        this.pixels = new RGBIntegerAlphaObject[width][height];
      }
      if (width >= 0 && width < this.width && height >= 0 && height < this.height) {
        this.pixels[width][height] = pixel;
      }
    }

    @Override
    public TypeOfImage buildImage() {
      return new RGBIntegerAlphaImage(this.pixels, width, height);
    }
  }

}
