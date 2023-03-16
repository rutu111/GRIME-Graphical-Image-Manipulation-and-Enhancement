package Model;

import static java.lang.Math.min;

import java.lang.reflect.Field;

/**
 * The following class represents an RGB image of type integers. This extends
 * ThreeChannelObjectOperations because this is a 3 channel image.
 * <p>
 * The matrix represented in this class is of TypeofImageObject which contains object of type
 * RGBIntegerObject. This matrix can be created through the builder (inner class) or directly
 * through the constructor (constructor is private so object can only be created from inside the
 * class or through bulder).
 */
public class RGBIntegerImage extends ThreeChannelObjectOperations<Integer> {

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
  protected TypeofImageObject getObject(Integer value1, Integer value2, Integer value3) {
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
  public TypeOfImage brighten(double increment) {
    TypeofImageObject[][] newPixels = new RGBIntegerObject[this.width][this.height];
    for (int i = 0; i < this.width; i++) {
      for (int j = 0; j < this.height; j++) {
        TypeofImageObject oldPixel = this.pixels[i][j];
        //Add increment value to RGB values and clamp to [0, 255]
        Integer r = (int) min(255, Math.max(0, oldPixel.getChanne11() + increment));
        Integer g = (int) min(255, Math.max(0, oldPixel.getChanne12() + increment));
        Integer b = (int) min(255, Math.max(0, oldPixel.getChanne13() + increment));
        newPixels[i][j] = new RGBIntegerObject(r, g, b);
      }
    }
    return new RGBIntegerImage(newPixels, this.width, this.height);
  }

  @Override
  public TypeOfImage visualizeValueIntensityLuma(MeasurementType measure)
      throws NoSuchFieldException, IllegalAccessException {
    TypeofImageObject[][] new_image = new RGBIntegerObject[this.getWidth()][this.getHeight()];
    for (int i = 0; i < this.getWidth(); i++) {
      for (int j = 0; j < this.getHeight(); j++) {
        if (measure.toString().equals("value")) {
          Integer value = Math.max(this.getPixels()[i][j].getChanne11(),
              Math.max(this.getPixels()[i][j].getChanne12(),
                  this.getPixels()[i][j].getChanne13()));
          new_image[i][j] = new RGBIntegerObject(value, value, value);
        }
        //intensity
        if (measure.toString().equals("intensity")) {
          Integer intensity =
              (this.getPixels()[i][j].getChanne11() + this.getPixels()[i][j].getChanne12()
                  + this.getPixels()[i][j].getChanne13()) / 3;
          new_image[i][j] = new RGBIntegerObject(intensity, intensity, intensity);
        }
        //luma
        if (measure.toString().equals("luma")) {
          double luma = 0.2126 * this.getPixels()[i][j].getChanne11()
              + 0.7152 * this.getPixels()[i][j].getChanne12() +
              0.0722 * this.getPixels()[i][j].getChanne13();
          new_image[i][j] = new RGBIntegerObject((int) luma, (int) luma, (int) luma);
        }
      }
    }
    return new RGBIntegerImage(new_image, this.getWidth(), this.getHeight());
  }


  @Override
  public TypeOfImage visIndividualComponent(ComponentRGB channel)
      throws NoSuchFieldException, IllegalAccessException {
    Field field = RGBIntegerObject.class.getDeclaredField(channel.toString());
    TypeofImageObject[][] new_image = new RGBIntegerObject[this.getWidth()][this.getHeight()];
    for (int i = 0; i < this.getWidth(); i++) {
      for (int j = 0; j < this.getHeight(); j++) {
        TypeofImageObject object = this.getPixels()[i][j];
        new_image[i][j] = new RGBIntegerObject((Integer) field.get(object),
            (Integer) field.get(object), (Integer) field.get(object));
      }
    }
    return new RGBIntegerImage(new_image, this.getWidth(), this.getHeight());
  }


  /**
   * The following is a builder class that builds a RGBIntegerImage containing objects of type
   * RGBIntegerObject.
   */
  public static class RGBIntegerImageBuilder extends ImageBuilder<RGBIntegerObject> {


    /**
     * The constructor takes in a width and height and creates a default matrix of that size in the
     * constructor. The addPixelByPosition method is used subsequently to manipulate the default
     * matrix created.
     *
     * @param width  width of the matrix..
     * @param height height of the matrix.
     * @throws IllegalArgumentException
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


