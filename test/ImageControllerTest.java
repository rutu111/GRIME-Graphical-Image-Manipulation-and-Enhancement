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

/**
 * ' This is a test class to test the controller in isolation.
 */
public class ImageControllerTest {


  /**
   * This is a MockModel class used to test the controller in isolation that implements the
   * Operations interface from the model. The mockmodel is used to make sure the controller
   * transcends inputs to the model and receives the outputs from the model.
   */
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

  //LOAD
  @Test
  public void testLoad() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    int width = 1024;
    int height = 768;
    Reader in = new StringReader("load koala.ppm koala\nexit");
    StringBuilder log = new StringBuilder(); //log for mock model
    MockModel mockModel = new MockModel(log);
    ImageController imageController = new ImageController(mockModel, in, out);
    imageController.run();
    String expectedOutput =
        "Received inputs for createBuilderThreeChannel: " + width + " " + height + "\n";
    assertEquals(expectedOutput, mockModel.log.toString()); //inputs reached the model correctly
  }

  //should not give an output
  @Test
  public void testLoadNonPPMFile() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    Reader in = new StringReader("load koala.jpg koala\nexit");
    StringBuilder log = new StringBuilder(); //log for mock model
    MockModel mockModel = new MockModel(log);
    ImageController imageController = new ImageController(mockModel, in, out);
    imageController.run();
    String expectedOutput = "";
    assertEquals(expectedOutput, mockModel.log.toString()); //inputs reached the model correctly
  }


  //VERTICAL FLIP
  @Test
  public void testVerticalFlip() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    String newImageName = "koala-vertical";
    Reader in = new StringReader("vertical-flip koala koala-vertical\nexit");
    StringBuilder log = new StringBuilder(); //log for mock model
    MockModel mockModel = new MockModel(log);
    ImageController imageController = new ImageController(mockModel, in, out);
    imageController.run();
    String expectedOutput =
        "Received inputs for verticalFlip: " + imageName + " " + newImageName + "\n";
    assertEquals(expectedOutput, mockModel.log.toString()); //inputs reached the model correctly
  }

  //HORIZONTAL FLIP
  @Test
  public void testHorizontalFlip() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    String newImageName = "koala-horizontal";
    Reader in = new StringReader("horizontal-flip koala koala-horizontal\nexit");
    StringBuilder log = new StringBuilder(); //log for mock model
    MockModel mockModel = new MockModel(log);
    ImageController imageController = new ImageController(mockModel, in, out);
    imageController.run();
    String expectedOutput =
        "Received inputs for horizontalFlip: " + imageName + " " + newImageName + "\n";
    assertEquals(expectedOutput, mockModel.log.toString()); //inputs reached the model correctly
  }


  //BRIGHTEN
  @Test
  public void testBrighten() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    String newImageName = "koala-brighten";
    double increment = 10;
    Reader in = new StringReader("brighten 10 koala koala-brighten\nexit");
    StringBuilder log = new StringBuilder(); //log for mock model
    MockModel mockModel = new MockModel(log);
    ImageController imageController = new ImageController(mockModel, in, out);
    imageController.run();
    String expectedOutput =
        "Received inputs for brighten: " + imageName + " " + newImageName + " " + increment +"\n";
    assertEquals(expectedOutput, mockModel.log.toString()); //inputs reached the model correctly
  }

  //GREYSCALE VISUALIZE:value
  @Test
  public void testGreyScaleValue() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    String newImageName = "koala-greyscale-value";
    String measure = "value";
    Reader in = new StringReader("greyscale value-component koala koala-greyscale-value\nexit");
    StringBuilder log = new StringBuilder(); //log for mock model
    MockModel mockModel = new MockModel(log);
    ImageController imageController = new ImageController(mockModel, in, out);
    imageController.run();
    String expectedOutput =
        "Received inputs for visualizeValueIntensityLuma: " + imageName + " " + newImageName + " "
            + measure + "\n";
    assertEquals(expectedOutput, mockModel.log.toString()); //inputs reached the model correctly
  }

  //GREYSCALE VISUALIZE:luma
  @Test
  public void testGreyScaleLuma() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    String newImageName = "koala-greyscale-luma";
    String measure = "luma";
    Reader in = new StringReader("greyscale luma-component koala koala-greyscale-luma\nexit");
    StringBuilder log = new StringBuilder(); //log for mock model
    MockModel mockModel = new MockModel(log);
    ImageController imageController = new ImageController(mockModel, in, out);
    imageController.run();
    String expectedOutput =
        "Received inputs for visualizeValueIntensityLuma: " + imageName + " " + newImageName + " "
            + measure + "\n";
    assertEquals(expectedOutput, mockModel.log.toString()); //inputs reached the model correctly
  }

  //GREYSCALE VISUALIZE:intensity
  @Test
  public void testGreyScaleIntensity() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    String newImageName = "koala-greyscale-intensity";
    String measure = "intensity";
    Reader in = new StringReader("greyscale intensity-component koala koala-greyscale-intensity\nexit");
    StringBuilder log = new StringBuilder(); //log for mock model
    MockModel mockModel = new MockModel(log);
    ImageController imageController = new ImageController(mockModel, in, out);
    imageController.run();
    String expectedOutput =
        "Received inputs for visualizeValueIntensityLuma: " + imageName + " " + newImageName + " "
            + measure + "\n";
    assertEquals(expectedOutput, mockModel.log.toString()); //inputs reached the model correctly
  }

  //GREYSCALE VISUALIZE:Red
  @Test
  public void testGreyScaleRed() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    String newImageName = "koala-greyscale-red";
    String component = "red";
    Reader in = new StringReader("greyscale red-component koala koala-greyscale-red\nexit");
    StringBuilder log = new StringBuilder(); //log for mock model
    MockModel mockModel = new MockModel(log);
    ImageController imageController = new ImageController(mockModel, in, out);
    imageController.run();
    String expectedOutput =
        "Received inputs for visIndividualComponent: " + imageName + " " + newImageName + " "
            + component + "\n";
    assertEquals(expectedOutput, mockModel.log.toString()); //inputs reached the model correctly
  }

  @Test
  public void testGreyScaleGreen() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    String newImageName = "koala-greyscale-green";
    String component = "green";
    Reader in = new StringReader("greyscale green-component koala koala-greyscale-green\nexit");
    StringBuilder log = new StringBuilder(); //log for mock model
    MockModel mockModel = new MockModel(log);
    ImageController imageController = new ImageController(mockModel, in, out);
    imageController.run();
    String expectedOutput =
        "Received inputs for visIndividualComponent: " + imageName + " " + newImageName + " "
            + component + "\n";
    assertEquals(expectedOutput, mockModel.log.toString()); //inputs reached the model correctly
  }

  @Test
  public void testGreyScaleBlue() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    String newImageName = "koala-greyscale-blue";
    String component = "blue";
    Reader in = new StringReader("greyscale blue-component koala koala-greyscale-blue\nexit");
    StringBuilder log = new StringBuilder(); //log for mock model
    MockModel mockModel = new MockModel(log);
    ImageController imageController = new ImageController(mockModel, in, out);
    imageController.run();
    String expectedOutput =
        "Received inputs for visIndividualComponent: " + imageName + " " + newImageName + " "
            + component + "\n";
    assertEquals(expectedOutput, mockModel.log.toString()); //inputs reached the model correctly
  }
}

