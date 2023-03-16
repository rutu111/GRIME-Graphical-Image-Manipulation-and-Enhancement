package Model;

import java.util.NoSuchElementException;

public interface Operations {

  public void horizontalFlip(String imageName, String newImageName);

  public void verticalFlip(String imageName, String newImageName);

  public void brighten(String imageName, String newImageName, double increment);

  public void visIndividualComponent(String imageName, String newImageName, ComponentRGB channel)
      throws NoSuchFieldException, IllegalAccessException;

  public void visualizeValueIntensityLuma(String imageName, String newImageName,
      MeasurementType measure)
      throws NoSuchFieldException, IllegalAccessException;

  public void splitInto3Images(String imageName, String newImageName1, String newImageName2,
      String newImageName3) throws NoSuchFieldException, IllegalAccessException;

  TypeOfImage getObject(String imageName);

  int numberOfImagesInModel();

  void combineGreyScaleToRGB(String imageName1, String imageName2, String imageName3,
      String newImageName) throws IllegalArgumentException, NoSuchElementException;

  void addImageToModel(TypeOfImage image,
      String nameOfObject);

  Set<String> returnKeys();
}
