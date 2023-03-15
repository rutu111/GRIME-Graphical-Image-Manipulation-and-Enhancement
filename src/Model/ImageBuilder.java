package Model;

/**
 * Image builder abstract class. This builds a
 * TypeOfImage where the matrix contains
 * TypeOfImageObjects. Any class that extends
 * this abstract class must provide an object P which
 * implements TypeofImageObject.
 *
 * @param <P> is an type of TypeofImageObject.
 */
public abstract class ImageBuilder<P extends TypeofImageObject> {

  protected final int width;
  protected final int height;
  protected TypeofImageObject[][] pixels;

  /**
   * Builder takes in width and height of the image.
   *
   * @param width  is the width of the image.
   * @param height is the height of the image.
   */
  protected ImageBuilder(int width, int height) {
    this.width = width;
    this.height = height;
  }

  /**
   * The following method inputs a TyepofImageObject
   * at a specific position in the matrix.
   *
   * @param width  is the width of the image.
   * @param height is the height of the image.
   * @param pixel  is the TyepofImageObject to input.
   */
  public abstract void addPixelAtPosition(int width, int height, P pixel);


  /**
   * This method builds the image of type TypeOfImage.
   *
   * @return an TypeOfImage object.
   */
  public abstract TypeOfImage buildImage();


}
