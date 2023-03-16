package Model;

import java.util.NoSuchElementException;

public interface ImageOperations {

  TypeOfImage horizontalFlip();

  TypeOfImage verticalFlip();

  TypeOfImage brighten(double increment);

  TypeOfImage visIndividualComponent(Component channel)
      throws NoSuchFieldException, IllegalAccessException;

  TypeOfImage visualizeValueIntensityLuma(MeasurementType measure)
      throws NoSuchFieldException, IllegalAccessException;

  TypeOfImage combineGreyScaleToRGB(TypeOfImage imageName2, TypeOfImage imageName3) throws IllegalArgumentException;


}