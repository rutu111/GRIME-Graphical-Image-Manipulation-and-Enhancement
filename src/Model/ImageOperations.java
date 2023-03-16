package Model;

public interface ImageOperations {

  TypeOfImage horizontalFlip();

  TypeOfImage verticalFlip();

  TypeOfImage brighten(double increment);

  TypeOfImage visIndividualComponent(ComponentRGB channel)
      throws NoSuchFieldException, IllegalAccessException;

  TypeOfImage visualizeValueIntensityLuma(MeasurementType measure)
      throws NoSuchFieldException, IllegalAccessException;

  TypeOfImage combineGreyScaleToRGB(TypeOfImage imageName2, TypeOfImage imageName3) throws IllegalArgumentException;


}
