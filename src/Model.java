import java.lang.reflect.Field;
import java.security.KeyException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class Model {

  private HashMap<String, TypeOfImage> imageHashMap = new HashMap<String, TypeOfImage>();

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
    //check if height and width of all images is the same
    //if yes, set height and width of RBG the same
    //if no, throw exception
    TypeofImageObject[][] new_image;
    if (image1.getPixels().length == image2.getPixels().length
        & image2.getPixels().length == image3.getPixels().length
        & image1.getPixels().length == image3.getPixels().length &
        image1.getPixels()[0].length == image2.getPixels()[0].length
        & image2.getPixels()[0].length == image3.getPixels()[0].length
        & image1.getPixels()[0].length == image3.getPixels()[0].length) {
      new_image = new threeChannelObject[image1.getPixels()[0].length][image1.getPixels().length];
      for (int i = 0; i < new_image[0].length; i++) {
        for (int j = 0; j < new_image.length; j++) {
          new_image[i][j] = new threeChannelObject(image1.getPixels()[i][j].getChanne11(),
              image2.getPixels()[i][j].getChanne12(), image3.getPixels()[i][j].getChanne13());
        }
      }
    } else {
      throw new IllegalArgumentException("All images must be of the same size!");
    }
    imageHashMap.put(newImageName,
        new threeChannelImage(new_image, image1.getWidth(), image1.getHeight()));
  }

  public threeChannelImage.threeChannelImageBuilder createBuilder(int width, int height) {
    threeChannelImage.threeChannelImageBuilder testBuilder = new threeChannelImage.threeChannelImageBuilder(
        width, height);

    return testBuilder; //saves to hashamp.
  }

  public void createImage(TypeOfImage image, String nameOfObject) {
    imageHashMap.put(nameOfObject, image);
  }

  public void horizontalFlip(String imageName, String newImageName)
      throws NoSuchElementException {
    if (!imageHashMap.containsKey(imageName)) {
      throw new NoSuchElementException("Image: " + imageName + "does not exist.");
    }
    TypeOfImage image = imageHashMap.get(imageName);
    TypeofImageObject[][] flippedImage = image.getPixels().clone();
    TypeofImageObject temp;
    //flip the columns
    for (int i = 0; i < flippedImage.length; i++) {
      for (int j = 0; j < flippedImage[i].length / 2; j++) {
        temp = flippedImage[i][j];
        flippedImage[i][j] = flippedImage[i][flippedImage.length - 1 - j];
        flippedImage[i][flippedImage.length - 1 - j] = temp;
      }
    }
    imageHashMap.put(newImageName,
        new threeChannelImage(flippedImage, flippedImage[0].length, flippedImage.length));
  }

  public void verticalFlip(String imageName, String newImageName)
      throws NoSuchElementException {
    if (!imageHashMap.containsKey(imageName)) {
      throw new NoSuchElementException("Image: " + imageName + "does not exist.");
    }
    TypeOfImage image = imageHashMap.get(imageName);
    //flip the rows
    TypeofImageObject[][] flippedImage = image.getPixels().clone();
    for (int i = 0; i < image.getHeight() / 2; ++i) {
      TypeofImageObject[] tempRow = flippedImage[i];
      flippedImage[i] = flippedImage[image.getHeight() - i - 1];
      flippedImage[image.getHeight() - i - 1] = tempRow;
    }
    imageHashMap.put(newImageName,
        new threeChannelImage(flippedImage, flippedImage[0].length, flippedImage.length));
  }

  public void visIndividualComponent(String imageName, String newImageName, Component channel)
      throws NoSuchFieldException, IllegalAccessException, NoSuchElementException {
    if (!imageHashMap.containsKey(imageName)) {
      throw new NoSuchElementException("Image: " + imageName + "does not exist.");
    }
    TypeOfImage image = imageHashMap.get(imageName);
    Field field = threeChannelObject.class.getField(channel.toString());
    TypeofImageObject[][] new_image = new threeChannelObject[image.getWidth()][image.getHeight()];
    for (int i = 0; i < image.getWidth(); i++) {
      for (int j = 0; j < image.getHeight(); j++) {
        TypeofImageObject object = image.getPixels()[i][j];
        new_image[i][j] = new threeChannelObject((Integer) field.get(object),
            (Integer) field.get(object), (Integer) field.get(object));
      }
    }
    imageHashMap.put(newImageName,
        new threeChannelImage(new_image, image.getWidth(), image.getHeight()));
  }


  public void brighten(String imageName, String newImageName, double increment)
      throws NoSuchElementException {
    if (!imageHashMap.containsKey(imageName)) {
      throw new NoSuchElementException("Image: " + imageName + "does not exist.");
    }
    TypeOfImage image = imageHashMap.get(imageName);
    TypeofImageObject[][] newPixels = new threeChannelObject[image.getWidth()][image.getHeight()];
    for (int i = 0; i < image.getWidth(); i++) {
      for (int j = 0; j < image.getHeight(); j++) {
        TypeofImageObject oldPixel = image.getPixels()[i][j];
        //Add increment value to RGB values and clamp to [0, 255]
        int r = (int) Math.min(255, Math.max(0, oldPixel.getChanne11() + increment));
        int g = (int) Math.min(255, Math.max(0, oldPixel.getChanne12() + increment));
        int b = (int) Math.min(255, Math.max(0, oldPixel.getChanne13() + increment));
        newPixels[i][j] = new threeChannelObject(r, g, b);
      }
    }
    imageHashMap.put(newImageName,
        new threeChannelImage(newPixels, image.getWidth(), image.getHeight()));
  }

  public void visualizeValueIntensityLuma(String imageName, String newImageName,
      MeasurementType measure)
      throws NoSuchElementException {
    if (!imageHashMap.containsKey(imageName)) {
      throw new NoSuchElementException("Image: " + imageName + "does not exist.");
    }
    TypeOfImage image = imageHashMap.get(imageName);
    TypeofImageObject[][] new_image = new threeChannelObject[image.getWidth()][image.getHeight()];
    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        switch (measure) {
          case value:
            int value = Math.max(Math.max(image.getPixels()[i][j].getChanne11(),
                image.getPixels()[i][j].getChanne12()), image.getPixels()[i][j].getChanne13());
            new_image[i][j] = new threeChannelObject(value, value, value);
            //intensity
          case intensity:
            int intensity =
                (image.getPixels()[i][j].getChanne11() + image.getPixels()[i][j].getChanne12()
                    + image.getPixels()[i][j].getChanne13()) / 3;
            new_image[i][j] = new threeChannelObject(intensity, intensity, intensity);
            //luma
          case luma:
            double luma = 0.2126 * image.getPixels()[i][j].getChanne11()
                + 0.7152 * image.getPixels()[i][j].getChanne12() +
                0.0722 * image.getPixels()[i][j].getChanne13();
            new_image[i][j] = new threeChannelObject((int) luma, (int) luma, (int) luma);
        }
      }
    }
    imageHashMap.put(newImageName,
        new threeChannelImage(new_image, image.getWidth(), image.getHeight()));
  }

  public void splitInto3Images(String imageName, String newImageName1, String newImageName2,
      String newImageName3)
      throws NoSuchElementException, NoSuchFieldException, IllegalAccessException {
    if (!imageHashMap.containsKey(imageName)) {
      throw new NoSuchElementException("Image: " + imageName + "does not exist.");
    }
    //creates a hashmap having channels as keys and greyscale image as values.
    //loop through the value of components and put the greyscale image in hashmap.

    visIndividualComponent(imageName, newImageName1, Component.Red);
    visIndividualComponent(imageName, newImageName2, Component.Green);
    visIndividualComponent(imageName, newImageName3, Component.Blue);

  }


  public TypeofImageObject getPixelAtPosition(TypeOfImage image, int width, int height) {
    if (width < 0 || width >= image.getWidth() || height < 0 || height >= image.getHeight()) {
      throw new IndexOutOfBoundsException("Position out of bounds.");
    }
    return image.getPixels()[image.getWidth()][image.getHeight()];
  }

  public TypeOfImage getObject(String imageName) throws NoSuchElementException {
    if (!imageHashMap.containsKey(imageName)) {
      throw new NoSuchElementException("Image: " + imageName + "does not exist.");
    }
    return imageHashMap.get(imageName);
  }

}
