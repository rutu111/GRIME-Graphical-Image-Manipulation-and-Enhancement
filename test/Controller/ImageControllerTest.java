package Controller;

import Model.Component;
import Model.MeasurementType;
import Model.Operations;
import Model.TypeOfImage;
import Model.threeChannelImage.threeChannelImageBuilder;
import java.util.NoSuchElementException;

public class ImageControllerTest {

  public class MockModel implements Operations {

    public StringBuilder log;

    public MockModel(StringBuilder log) {
      this.log = log;
    }

    @Override
    public void horizontalFlip(String imageName, String newImageName) {
      log.append("horizontalFlip: " + imageName + " " + newImageName + "\n");
    }

    @Override
    public void verticalFlip(String imageName, String newImageName) {
      log.append("verticalFlip: " + imageName + " " + newImageName + "\n");

    }

    @Override
    public void brighten(String imageName, String newImageName, double increment) {
      log.append("brighten: " + imageName + " " + newImageName + " " + increment  + "\n");

    }

    @Override
    public void visIndividualComponent(String imageName, String newImageName, Component channel)
        throws NoSuchFieldException, IllegalAccessException {
      log.append("visIndividualComponent: " + imageName + " " + newImageName + " " + channel  + "\n");


    }

    @Override
    public void visualizeValueIntensityLuma(String imageName, String newImageName,
        MeasurementType measure) throws NoSuchFieldException, IllegalAccessException {
      log.append("visualizeValueIntensityLuma: " + imageName + " " + newImageName + " " + measure +  "\n");

    }

    @Override
    public void splitInto3Images(String imageName, String newImageName1, String newImageName2,
        String newImageName3) throws NoSuchFieldException, IllegalAccessException {
      log.append("splitInto3Images: " + imageName + " " + newImageName1 + " " + newImageName2 +  " " + newImageName3 +  "\n");

    }

    @Override
    public TypeOfImage getObject(String imageName) {
      log.append("getObject: " + imageName + "\n");
      return null;
    }

    @Override
    public int numberOfImagesInModel() {
      log.append("numberOfImagesInModel" + "\n");
      return 0;
    }

    @Override
    public void combineGreyScaleToRGB(String imageName1, String imageName2, String imageName3,
        String newImageName) throws IllegalArgumentException, NoSuchElementException {
      log.append("combineGreyScaleToRGB: " + imageName1 + " " + imageName2 + " " + imageName3 +  " " + newImageName + "\n");

    }

    @Override
    public threeChannelImageBuilder createBuilderThreeChannel(int width, int height) {
      log.append("createBuilderThreeChannel: " + width + " " + height + "\n");
      return null;
    }

    @Override
    public void createImageThreeChannel(threeChannelImageBuilder image, String nameOfObject) {
      log.append("createImageThreeChannel:" + nameOfObject + "\n");
    }

  }
}