package Model;

/**
 * The following interface represents an image.
 * Common properties that images would have are 1. Tha
 * matrix, 2. The width and 3. The height.
 * This interface has getters for all of these.
 */
public interface TypeOfImage {

  /**
   * This method fetches the image matrix.
   *
   * @return a matrix of TypeofImageObject objects.
   */
  TypeofImageObject[][] getPixels();

  /**
   * This method gets the height of the image.
   *
   * @return the height of the image matrix.
   */
  int getHeight();

  /**
   * This method gets the width of the image.
   *
   * @return the width of the image matrix.
   */
  int getWidth();

}
