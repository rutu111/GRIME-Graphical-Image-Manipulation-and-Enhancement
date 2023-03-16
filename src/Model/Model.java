package Model;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Set;

public class Model implements  Operations {

  private HashMap<String, TypeOfImage> imageHashMap = new HashMap<String, TypeOfImage>();



  public void addImageToModel(TypeOfImage image, String nameOfObject) {
    imageHashMap.put(nameOfObject, image);
  }

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
    imageHashMap.put(newImageName, image1.combineGreyScaleToRGB(image2, image3));
  }

  public void verticalFlip(String imageName, String newImageName)
      throws NoSuchElementException {
    if (!imageHashMap.containsKey(imageName)) {
      throw new NoSuchElementException("Image: " + imageName + "does not exist.");
    }
    TypeOfImage image = imageHashMap.get(imageName);
    imageHashMap.put(newImageName, image.verticalFlip());
  }

  public void horizontalFlip(String imageName, String newImageName)
      throws NoSuchElementException {
    if (!imageHashMap.containsKey(imageName)) {
      throw new NoSuchElementException("Image: " + imageName + "does not exist.");
    }
    TypeOfImage image = imageHashMap.get(imageName);
    imageHashMap.put(newImageName, image.horizontalFlip());
  }

  public void visIndividualComponent(String imageName, String newImageName, Component channel)
      throws NoSuchFieldException, IllegalAccessException, NoSuchElementException {
    if (!imageHashMap.containsKey(imageName)) {
      throw new NoSuchElementException("Image: " + imageName + "does not exist.");
    }
    TypeOfImage image = imageHashMap.get(imageName);
    imageHashMap.put(newImageName, image.visIndividualComponent(channel));
  }


  public void brighten(String imageName, String newImageName, double increment)
      throws NoSuchElementException {
    if (!imageHashMap.containsKey(imageName)) {
      throw new NoSuchElementException("Image: " + imageName + " does not exist.");
    }
    TypeOfImage image = imageHashMap.get(imageName);
    imageHashMap.put(newImageName, image.brighten(increment));
  }


  public void visualizeValueIntensityLuma(String imageName, String newImageName,
      MeasurementType measure)
      throws  NoSuchFieldException, IllegalAccessException {
    if (!imageHashMap.containsKey(imageName)) {
      throw new NoSuchElementException("Image: " + imageName + "does not exist.");
    }
    TypeOfImage image = imageHashMap.get(imageName);

    imageHashMap.put(newImageName, image.visualizeValueIntensityLuma(measure));
  }

  public void splitInto3Images(String imageName, String newImageName1, String newImageName2,
      String newImageName3)
      throws NoSuchElementException, NoSuchFieldException, IllegalAccessException {
    if (!imageHashMap.containsKey(imageName)) {
      throw new NoSuchElementException("Image: " + imageName + "does not exist.");
    }
    //creates a hashmap having channels as keys and greyscale image as values.
    //loop through the value of components and put the greyscale image in hashmap.

    visIndividualComponent(imageName, newImageName1, Component.red);
    visIndividualComponent(imageName, newImageName2, Component.green);
    visIndividualComponent(imageName, newImageName3, Component.blue);

  }



  public TypeOfImage getObject(String imageName) throws NoSuchElementException {
    if (!imageHashMap.containsKey(imageName)) {
      throw new NoSuchElementException("Image: " + imageName + "does not exist.");
    }
    return imageHashMap.get(imageName);
  }

  public int numberOfImagesInModel() {
    return imageHashMap.size();
  }

}