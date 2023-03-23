package Model;

import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * The following class is the main Model class
 * that the controller deals with.
 *
 * Each method signature throws an exception
 * if the object does nto exist
 * in the hashmap.
 */
public class Model implements Operations {

  /**
   * The following hashmap holds images as values and
   * their names as keys.
   */
  private HashMap<String, TypeOfImage> imageHashMap = new HashMap<String, TypeOfImage>();

  @Override
  public void addImageToModel(TypeOfImage image, String nameOfObject) {
    imageHashMap.put(nameOfObject, image);
  }

  @Override
  public void combineGreyScaleToRGB(String imageName1, String imageName2, String imageName3,
      String newImageName) throws IllegalArgumentException, NoSuchElementException {
    if (!imageHashMap.containsKey(imageName1)) {
      throw new NoSuchElementException("Image: " + imageName1 + "does not exist.");
    }
    if (!imageHashMap.containsKey(imageName2)) {
      throw new NoSuchElementException("Image: " + imageName2 + "does not exist.");
    }
    if (!imageHashMap.containsKey(imageName3)) {
      throw new NoSuchElementException("Image: " + imageName3 + "does not exist.");
    }

    TypeOfImage image1 = imageHashMap.get(imageName1);
    TypeOfImage image2 = imageHashMap.get(imageName2);
    TypeOfImage image3 = imageHashMap.get(imageName3);
    if (image1 instanceof ThreeChannelObjectOperations
        & image2 instanceof ThreeChannelObjectOperations
        & image3 instanceof ThreeChannelObjectOperations) {
      imageHashMap.put(newImageName, image1.combineGreyScaleToRGB(image2, image3));
    }
  }

  @Override
  public void verticalFlip(String imageName, String newImageName)
      throws NoSuchElementException {
    if (!imageHashMap.containsKey(imageName)) {
      throw new NoSuchElementException("Image: " + imageName + "does not exist.");
    }
    TypeOfImage image = imageHashMap.get(imageName);
    imageHashMap.put(newImageName, image.verticalFlip());
  }

  @Override
  public void horizontalFlip(String imageName, String newImageName)
      throws NoSuchElementException {
    if (!imageHashMap.containsKey(imageName)) {
      throw new NoSuchElementException("Image: " + imageName + "does not exist.");
    }
    TypeOfImage image = imageHashMap.get(imageName);
    imageHashMap.put(newImageName, image.horizontalFlip());
  }

  @Override
  public void visIndividualComponent(String imageName, String newImageName, ComponentRGB channel)
      throws NoSuchFieldException, IllegalAccessException, NoSuchElementException {
    if (!imageHashMap.containsKey(imageName)) {
      throw new NoSuchElementException("Image: " + imageName + "does not exist.");
    }
    TypeOfImage image = imageHashMap.get(imageName);
    if (image instanceof RGBIntegerImage) {
      imageHashMap.put(newImageName, image.visIndividualComponent(channel));
    }
  }

  @Override
  public void brighten(String imageName, String newImageName, double increment)
      throws NoSuchElementException {
    if (!imageHashMap.containsKey(imageName)) {
      throw new NoSuchElementException("Image: " + imageName + " does not exist.");
    }
    TypeOfImage image = imageHashMap.get(imageName);
    imageHashMap.put(newImageName, image.brighten(increment));
  }

  @Override
  public void visualizeValueIntensityLuma(String imageName, String newImageName,
      MeasurementType measure)
      throws NoSuchFieldException, IllegalAccessException {
    if (!imageHashMap.containsKey(imageName)) {
      throw new NoSuchElementException("Image: " + imageName + "does not exist.");
    }
    TypeOfImage image = imageHashMap.get(imageName);

    imageHashMap.put(newImageName, image.visualizeValueIntensityLuma(measure));
  }

  @Override
  public void splitInto3Images(String imageName, String newImageName1, String newImageName2,
      String newImageName3)
      throws NoSuchElementException, NoSuchFieldException, IllegalAccessException {
    if (!imageHashMap.containsKey(imageName)) {
      throw new NoSuchElementException("Image: " + imageName + "does not exist.");
    }
    //creates a hashmap having channels as keys and greyscale image as values.
    //loop through the value of components and put the greyscale image in hashmap.
    visIndividualComponent(imageName, newImageName1, ComponentRGB.red);
    visIndividualComponent(imageName, newImageName2, ComponentRGB.green);
    visIndividualComponent(imageName, newImageName3, ComponentRGB.blue);
  }

  @Override
  public TypeOfImage getObject(String imageName) throws NoSuchElementException {
    if (!imageHashMap.containsKey(imageName)) {
      throw new NoSuchElementException("Image: " + imageName + "does not exist.");
    }
    return imageHashMap.get(imageName);
  }

  @Override
  public int numberOfImagesInModel() {
    return imageHashMap.size();
  }

  @Override
  public boolean checkKeyInHashmap(String imageName) {
    return imageHashMap.containsKey(imageName);
  }

}