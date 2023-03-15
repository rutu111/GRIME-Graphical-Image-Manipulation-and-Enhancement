import static org.junit.Assert.assertEquals;

import Controller.ImageController;
import Model.Component;
import Model.MeasurementType;
import Model.Operations;
import Model.TypeOfImage;
import Model.threeChannelImage.threeChannelImageBuilder;
import java.io.Reader;
import java.io.StringReader;
import java.util.NoSuchElementException;
import org.junit.Test;

/**'
 * This is a test class to test the controller in isolation
 */
public class ImageControllerTest {


  public class MockModel implements Operations {

    public StringBuilder log;

    public MockModel(StringBuilder log) {
      this.log = log;
    }

    @Override
    public void horizontalFlip(String imageName, String newImageName) {
      log.append("Received inputs for horizontalFlip: " + imageName + " " + newImageName + "\n");
    }

    @Override
    public void verticalFlip(String imageName, String newImageName) {
      log.append("Received inputs for verticalFlip: " + imageName + " " + newImageName + "\n");

    }

    @Override
    public void brighten(String imageName, String newImageName, double increment) {
      log.append("Received inputs for brighten: " + imageName + " " + newImageName + " " + increment
          + "\n");

    }

    @Override
    public void visIndividualComponent(String imageName, String newImageName, Component channel)
        throws NoSuchFieldException, IllegalAccessException {
      log.append(
          "Received inputs for visIndividualComponent: " + imageName + " " + newImageName + " "
              + channel + "\n");


    }

    @Override
    public void visualizeValueIntensityLuma(String imageName, String newImageName,
        MeasurementType measure) throws NoSuchFieldException, IllegalAccessException {
      log.append(
          "Received inputs for visualizeValueIntensityLuma: " + imageName + " " + newImageName + " "
              + measure + "\n");

    }

    @Override
    public void splitInto3Images(String imageName, String newImageName1, String newImageName2,
        String newImageName3) throws NoSuchFieldException, IllegalAccessException {
      log.append("Received inputs for splitInto3Images: " + imageName + " " + newImageName1 + " "
          + newImageName2 + " "
          + newImageName3 + "\n");

    }

    @Override
    public TypeOfImage getObject(String imageName) {
      log.append("Received inputs for getObject: " + imageName + "\n");
      return null;
    }

    @Override
    public int numberOfImagesInModel() {
      log.append("Received inputs for numberOfImagesInModel" + "\n");
      return 0;
    }

    @Override
    public void combineGreyScaleToRGB(String imageName1, String imageName2, String imageName3,
        String newImageName) throws IllegalArgumentException, NoSuchElementException {
      log.append("Received inputs for combineGreyScaleToRGB: " + imageName1 + " " + imageName2 + " "
          + imageName3 + " "
          + newImageName + "\n");

    }

    @Override
    public threeChannelImageBuilder createBuilderThreeChannel(int width, int height) {
      log.append("Received inputs for createBuilderThreeChannel: " + width + " " + height + "\n");
      return null;
    }

    @Override
    public void createImageThreeChannel(threeChannelImageBuilder image, String nameOfObject) {
      log.append("Received inputs for createImageThreeChannel:" + nameOfObject + "\n");
    }

  }

  @Test
  public void testHorizontalFlip() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "test-image";
    String newImageName = "updated-image";
    Reader in = new StringReader("horizontal-flip koala koala-horizontal\nexit");
    StringBuilder log = new StringBuilder(); //log for mock model
    MockModel mockModel = new MockModel(log);
    ImageController imageController = new ImageController(mockModel, in, out);
    imageController.run();
    mockModel.horizontalFlip(imageName, newImageName);
    String expectedOutput =
        "Received inputs for createBuilderThreeChannel: " + 1024 + " " + 768 + "\n"
            + "Received inputs for horizontalFlip: " + imageName + " " + newImageName + "\n";
    assertEquals(expectedOutput, mockModel.log.toString()); //inputs reached the model correctly
  }

  @Test
  public void testVerticalFlip() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "test-image";
    String newImageName = "updated-image";
    Reader in = new StringReader("load koala.ppm koala\nexit");
    StringBuilder log = new StringBuilder(); //log for mock model
    MockModel mockModel = new MockModel(log);
    ImageController imageController = new ImageController(mockModel, in, out);
    imageController.run();
    mockModel.verticalFlip(imageName, newImageName);
    String expectedOutput =
        "Received inputs for createBuilderThreeChannel: " + 1024 + " " + 768 + "\n"
            + "Received inputs for verticalFlip: " + imageName + " " + newImageName + "\n";
    assertEquals(expectedOutput, mockModel.log.toString()); //inputs reached the model correctly
  }
}

