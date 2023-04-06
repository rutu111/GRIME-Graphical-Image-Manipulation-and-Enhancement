package model;


import java.lang.reflect.Field;

/**
 * The following class represents an RGB image of type integers.
 * This extends
 * ThreeChannelObjectOperations because this is a 3 channel image.
 *
 * <p>The matrix represented in this class is of TypeofImageObject which
 * contains object of type
 * RGBIntegerObject. This matrix can be created through the builder (inner
 * class) or directly
 * through the constructor (constructor is private so object can only
 * be created from inside the
 * class or through bulder).
 */
public class RGBIntegerImage extends ThreeChannelObjectOperations {

  /**
   * Creates a matrix containing RGBIntegerObjects.
   *
   * @param pixels matrix of type RGBIntegerObjects
   * @param width  width of the image.
   * @param height height of the image.
   */
  private RGBIntegerImage(TypeofImageObject[][] pixels, int width, int height) {
    super(pixels, width, height);
  }

  @Override
  protected TypeofImageObject[][] getMatrix(int width, int height) {
    return new RGBIntegerObject[this.getWidth()][this.getHeight()];
  }

  @Override
  protected TypeofImageObject getObject(int value1, int value2, int value3, Integer value4) {
    return new RGBIntegerObject(value1, value2, value3);
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
    return new RGBIntegerImage(flippedImage, width, height);
  }

  @Override
  protected Field getField(ComponentRGB channel) throws NoSuchFieldException {
    return RGBIntegerObject.class.getDeclaredField(channel.toString());
  }

  /**
   * The following is a builder class that builds a RGBIntegerImage
   * containing objects of type
   * RGBIntegerObject.
   */
  public static class RGBIntegerImageBuilder extends ImageBuilder<RGBIntegerObject> {


    /**
     * The constructor takes in a width and height and creates a default
     * matrix of that size in the
     * constructor. The addPixelByPosition method is used subsequently to
     * manipulate the default
     * matrix created.
     *
     * @param width  width of the matrix.
     * @param height height of the matrix.
     * @throws IllegalArgumentException throws illegal argument exception
     */
    public RGBIntegerImageBuilder(int width, int height) throws IllegalArgumentException {
      super(width, height);
      if (width <= 0 | height <= 0) {
        throw new IllegalArgumentException("Width and height need to be greater then 0. ");
      }
      this.pixels = new RGBIntegerObject[width][height];
      for (int i = 0; i < width; i++) {
        for (int j = 0; j < height; j++) {
          this.pixels[i][j] = new RGBIntegerObject(0, 0, 0); // Dummy RGB value

        }
      }
    }

    @Override
    public void addPixelAtPosition(int width, int height, RGBIntegerObject pixel) {
      if (this.pixels == null) {
        this.pixels = new RGBIntegerObject[width][height];
      }
      if (width >= 0 && width < this.width && height >= 0 && height < this.height) {
        this.pixels[width][height] = pixel;
      }
    }


    @Override
    public TypeOfImage buildImage() {
      return new RGBIntegerImage(this.pixels, width, height);
    }
  }

}


