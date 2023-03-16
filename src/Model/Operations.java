package Model;

import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * This is an interface for the Model. It contains signatures
 * for helper methods and a few other
 * methods too.
 */
public interface Operations {

  /**
   * This methods flips image horizontally.
   *
   * @param imageName    name of the object to perform the operation on.
   * @param newImageName name of the object after operation is performed.
   */
  void horizontalFlip(String imageName, String newImageName);

  /**
   * This methods flips image vertically.
   *
   * @param imageName    name of the object to perform the operation on.
   * @param newImageName name of the object after operation is performed.
   */
  void verticalFlip(String imageName, String newImageName);

  /**
   * This methods brightens/darkens the image.
   *
   * @param imageName    name of the object to perform the operation on.
   * @param newImageName name of the object after operation is performed.
   * @param increment    amount by which you want to brighten or darken.
   */
  void brighten(String imageName, String newImageName, double increment);

  /**
   * This method visualizes individual RGB components.
   *
   * @param imageName    name of the object to perform the operation on.
   * @param newImageName name of the object after operation is performed.
   * @param channel      component naame
   * @throws NoSuchFieldException   if field does nto exist.
   * @throws IllegalAccessException for illegal acces.
   */
  void visIndividualComponent(String imageName, String newImageName, ComponentRGB channel)
      throws NoSuchFieldException, IllegalAccessException;

  /**
   * This method visualizes values, intensite and luma.
   *
   * @param imageName    name of the object to perform the operation on.
   * @param newImageName name of the object after operation is performed.
   * @param measure      component naame
   * @throws NoSuchFieldException   if field does nto exist.
   * @throws IllegalAccessException for illegal acces.
   */
  void visualizeValueIntensityLuma(String imageName, String newImageName,
      MeasurementType measure)
      throws NoSuchFieldException, IllegalAccessException;

  /**
   * This methods splits RGB image into 3 greyscale images.
   *
   * @param imageName     RGB image to split.
   * @param newImageName1 greyscale image 1
   * @param newImageName2 greyscale image 2
   * @param newImageName3 greyscale image 3
   * @throws NoSuchFieldException   if field does nto exist.
   * @throws IllegalAccessException for illegal acces.
   */
  void splitInto3Images(String imageName, String newImageName1, String newImageName2,
      String newImageName3) throws NoSuchFieldException, IllegalAccessException;

  /**
   * This methodw gets the object by keyname from the hashmap if one exists.
   *
   * @param imageName name of the image.
   * @return TypeOfImage object.
   */
  TypeOfImage getObject(String imageName);

  /**
   * This methods outputs the number of images in the hashmap.
   *
   * @return number of images in hashmap.
   */
  int numberOfImagesInModel();

  /**
   * The following method combines 3 greyscale images into 1 RGB image
   *
   * @param imageName1   greyscale image 1
   * @param imageName2   greyscale image 1
   * @param imageName3   greyscale image 1
   * @param newImageName resultant RGB image.
   * @throws NoSuchFieldException   if field does nto exist.
   * @throws IllegalAccessException for illegal acces.
   */
  void combineGreyScaleToRGB(String imageName1, String imageName2, String imageName3,
      String newImageName) throws IllegalArgumentException, NoSuchElementException;

  /**
   * This methods adds an image to the hashmap.
   *
   * @param image        to add.
   * @param nameOfObject name of the image.
   */
  void addImageToModel(TypeOfImage image,
      String nameOfObject);

  boolean checkKeyInHashmap(String imageName);
}
