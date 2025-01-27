package model;

/**
 * Read only model created for interactions between the GUI and model.
 */
public interface ROModel {

  /**
   * This is a method gets the object by keyname from the hashmap if one exists.
   *
   * @param imageName name of the image.
   * @return TypeOfImage object.
   */
  TypeOfImage getObject(String imageName);

  /**
   * This is a method outputs the number of images in the hashmap.
   *
   * @return number of images in hashmap.
   */
  int numberOfImagesInModel();


  /**
   * This is a method checks if requested key is in the hashmap. If yes, then returns
   * true. Else, returns
   * false.
   *
   * @param imageName key to look for.
   * @return boolean.
   */
  boolean checkKeyInHashmap(String imageName);
}
