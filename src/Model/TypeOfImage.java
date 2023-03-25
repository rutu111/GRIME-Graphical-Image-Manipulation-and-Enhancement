package Model;

import java.awt.Component;

/**
 * The following interface represents an image.
 * Common properties that images would have are 1. Tha
 * matrix, 2. The width and 3. The height.
 * This interface has getters for all of these.
 *
 * The interface contains image operations such as
 * Horizontalflip, verticalFlip etc.
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

  /**
   * The following method horizontally flips the image.
   *
   * @return Image object of type TypeOfImage.
   */
  TypeOfImage horizontalFlip();

  /**
   * The following method vertically flips the image.
   *
   * @return Image object of type TypeOfImage.
   */
  TypeOfImage verticalFlip();

  /**
   * Brightens or darkens the image based on the increment value.
   *
   * @param increment a double.
   * @return Image object of type TypeOfImage.
   */
  TypeOfImage brighten(double increment);

  /**
   * Method to visualize individual components RGB.
   *
   * @param channel of type Component (enum).
   * @return Image object of type TypeOfImage.
   * @throws NoSuchFieldException   if enum not found.
   * @throws IllegalAccessException if illegal access.
   */
  TypeOfImage visIndividualComponent(ComponentRGB channel)
      throws NoSuchFieldException, IllegalAccessException;

  /**
   * Method for intensity, value and luma.
   *
   * @param measure enum.
   * @return Image object of type TypeOfImage.
   * @throws NoSuchFieldException   if enum not found.
   * @throws IllegalAccessException if illegal access.
   */
  TypeOfImage visualizeValueIntensityLuma(MeasurementType measure)
      throws NoSuchFieldException, IllegalAccessException;

  /**
   * Method combines 3 greyscale images into an RGB image.
   *
   * @param imageName2 second image.
   * @param imageName3 third image.
   * @return Image object of type TypeOfImage.
   * @throws IllegalArgumentException throw if images not of same dimensions.
   */
  TypeOfImage combineGreyScaleToRGB(TypeOfImage imageName2, TypeOfImage imageName3)
      throws IllegalArgumentException;

  TypeOfImage blur();
  TypeOfImage sharpen();

  TypeOfImage dither(ComponentRGB channel) throws NoSuchFieldException, IllegalAccessException;

  TypeOfImage colorTransformationSepia();

}
