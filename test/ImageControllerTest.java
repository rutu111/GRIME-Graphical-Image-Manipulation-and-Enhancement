import static org.junit.Assert.assertEquals;

import Controller.ImageController;
import Model.ComponentRGB;
import Model.MeasurementType;
import Model.Operations;
import Model.TypeOfImage;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.NoSuchElementException;
import org.junit.Test;

/**
 * This is a test class to test the controller in isolation.
 */
public class ImageControllerTest {


  /**
   * This is a MockModel class used to test the controller
   * in isolation that implements the
   * Operations interface from the model. The mockmodel is
   * used to make sure the controller
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
    public void visIndividualComponent(String imageName, String newImageName, ComponentRGB channel)
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
      log.append("Received inputs for combineGreyScaleToRGB: " + newImageName + " " + imageName1 + " " + imageName2 + " "
          + imageName3 + "\n");

    }



    @Override
    public void addImageToModel(TypeOfImage image, String nameOfObject) {
      log.append("Received inputs for createBuilderThreeChannel:" + nameOfObject + "\n");
    }

    @Override
    public boolean checkKeyInHashmap(String imageName) {
      return false;
    }

    @Override
    public void colorTransformationSepia(String imageName, String newImageName) {

    }

    @Override
    public void colorTransformationLuma(String imageName, String newImageName)
        throws NoSuchFieldException, IllegalAccessException {

    }

    @Override
    public void blur(String imageName, String newImageName) {

    }

    @Override
    public void sharpen(String imageName, String newImageName) {

    }

    @Override
    public void dither(String imageName, String newImageName) {

    }


  }

  /**
   * This method tests the load operation that reads the PPM file
   * from ImageUtil and give
   * appropriate logs when passed to the model.
   */
  @Test
  public void testLoad() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    Reader in = new StringReader("load koala.ppm koala\nexit");
    StringBuilder log = new StringBuilder(); //log for mock model
    MockModel mockModel = new MockModel(log);
    ImageController imageController = new ImageController(mockModel, in, out);
    imageController.run();
    String expectedOutput =
        "Received inputs for createBuilderThreeChannel:" + imageName + "\n";
    assertEquals(expectedOutput, mockModel.log.toString()); //inputs reached the model correctly
  }

  /**
   * This method is used to check if the output throws an exception
   * with appropriate message if
   * the file passed is a non-ppm file.
   * @throws Exception illegal argument exception
   */
  @Test
  public void testLoadNonPPMFile() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    Reader in = new StringReader("load koala.jpg koala\nexit");
    StringBuilder log = new StringBuilder(); //log for mock model
    MockModel mockModel = new MockModel(log);
    ImageController imageController = new ImageController(mockModel, in, out);
    imageController.run();
    String expectedOutput = "Error: Invalid file format: jpg\n"
                            + "Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }

  /**
   * This method is used to check if the output throws an
   * exception with appropriate message
   * if the command is incorrect.
   * @throws Exception illegal argument exception
   */
  @Test
  public void testInvalidLoadCommand() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    Reader in = new StringReader("load koala.ppm as koala\nexit");
    StringBuilder log = new StringBuilder(); //log for mock model
    MockModel mockModel = new MockModel(log);
    ImageController imageController = new ImageController(mockModel, in, out);
    imageController.run();
    String expectedOutput = "Error: Invalid command format.\n"
                            +"Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }

  /**
   * This method is used to check if the output throws an
   * exception with appropriate message
   * if the command is incorrect.
   * @throws Exception illegal argument exception
   */
  @Test
  public void testInvalidSpellingLoadCommand() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    Reader in = new StringReader("laodd koala.ppm koala\nexit");
    StringBuilder log = new StringBuilder(); //log for mock model
    MockModel mockModel = new MockModel(log);
    ImageController imageController = new ImageController(mockModel, in, out);
    imageController.run();
    String expectedOutput = "Error: Invalid command: laodd\n"
        +"Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }

  /**
   * This method is used to check if the output throws an
   * exception with appropriate message
   * if the value of the pixel is greater than 255.
   * @throws Exception illegal argument exception
   */
  @Test
  public void testInvalidPixelFileLoadCommand() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    Reader in = new StringReader("load less0.ppm koala\nexit");
    StringBuilder log = new StringBuilder(); //log for mock model
    MockModel mockModel = new MockModel(log);
    ImageController imageController = new ImageController(mockModel, in, out);
    imageController.run();
    String expectedOutput = "Error: RGB values can't be less negative!\n"
        +"Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }

  /**
   * This method is used to check if the output throws an
   * exception with appropriate message
   * if the value of the pixel is less than 0.
   * @throws Exception illegal argument exception
   */
  @Test
  public void testInvalidPixelFileLoadCommand2() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    Reader in = new StringReader("load greater255.ppm koala\nexit");
    StringBuilder log = new StringBuilder(); //log for mock model
    MockModel mockModel = new MockModel(log);
    ImageController imageController = new ImageController(mockModel, in, out);
    imageController.run();
    String expectedOutput = "Error: RGB values can't be above 255!\n"
        +"Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }

  /**
   * This method is used to check if the output throws an
   * exception with appropriate message
   * if the value of the pixel is less than 0.
   * @throws Exception illegal argument exception
   */
  @Test
  public void testInvalidMaxValueLoadCommand() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    Reader in = new StringReader("load invalidMaxval.ppm koala\nexit");
    StringBuilder log = new StringBuilder(); //log for mock model
    MockModel mockModel = new MockModel(log);
    ImageController imageController = new ImageController(mockModel, in, out);
    imageController.run();
    String expectedOutput = "Error: Invalid maxValue: -255\n"
        +"Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }

  /**
   * This method is used to check if the output throws an
   * exception with appropriate message
   * if the filename is incorrect.
   * @throws Exception illegal argument exception
   */
  @Test
  public void testInvalidFileLoad() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    Reader in = new StringReader("load koala.pmm koala\nexit");
    StringBuilder log = new StringBuilder(); //log for mock model
    MockModel mockModel = new MockModel(log);
    ImageController imageController = new ImageController(mockModel, in, out);
    imageController.run();
    String expectedOutput = "Error: Invalid file format: pmm\n"
        +"Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }

  /**
   * This method is used to check if the output throws an
   * exception with appropriate message
   * if an empty file is loaded.
   *
   * @throws Exception illegal argument exception
   */
  @Test
  public void testEmptyFileLoad() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    Reader in = new StringReader("load empty.ppm koala\nexit");
    StringBuilder log = new StringBuilder(); //log for mock model
    MockModel mockModel = new MockModel(log);
    ImageController imageController = new ImageController(mockModel, in, out);
    imageController.run();
    String expectedOutput = "Error: Invalid file: Cannot be empty\n"
        +"Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }

  /**
   * This method is used to check if the output throws an
   * exception with appropriate message
   * if a file with zero height and width is loaded.
   * @throws Exception illegal argument exception
   */
  @Test
  public void testZeroDimensionFileLoad() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load zeroDim.ppm koala\nexit");
    StringBuilder log = new StringBuilder(); //log for mock model
    MockModel mockModel = new MockModel(log);
    ImageController imageController = new ImageController(mockModel, in, out);
    imageController.run();
    String expectedOutput = "Error: Width and Height cannot be zero or negative\n"
        +"Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }

  /**
   * This method is used to check if the output throws an
   * exception with appropriate message
   * if a file with does not have one of the pixels channel
   * @throws Exception illegal argument exception
   */
  @Test
  public void testlessPixelFileLoad() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load lessPixels.ppm koala\nexit");
    StringBuilder log = new StringBuilder(); //log for mock model
    MockModel mockModel = new MockModel(log);
    ImageController imageController = new ImageController(mockModel, in, out);
    imageController.run();
    String expectedOutput = "Error: null\n"
        +"Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }

  /**
   * This method is used to check if the output throws an
   * exception with appropriate message
   * if the filename is incorrect.
   * @throws Exception illegal argument exception
   */
  @Test
  public void testInvalidCommandWithWhitespace() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    Reader in = new StringReader("load koala.ppm      koala\nexit");
    StringBuilder log = new StringBuilder(); //log for mock model
    MockModel mockModel = new MockModel(log);
    ImageController imageController = new ImageController(mockModel, in, out);
    imageController.run();
    String expectedOutput = "Error: Invalid command format.\n"
        +"Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }

  /**
   * This method is used to check if the command works after empty line
   * @throws Exception illegal argument exception
   */
  @Test
  public void testWithNextLineEmpty() throws Exception {
    StringBuffer out = new StringBuffer();

    Reader in = new StringReader(" \nexit");
    StringBuilder log = new StringBuilder(); //log for mock model
    MockModel mockModel = new MockModel(log);
    ImageController imageController = new ImageController(mockModel, in, out);
    imageController.run();
    String expectedOutput = "Please enter appropriate command. \n"
        +"Error: Invalid command: \n"
        +"Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }

  /**
   * This method is used to check if the output throws an
   * exception with appropriate message
   * if a file with zero height and width is loaded.
   * @throws Exception illegal argument exception
   */
  @Test
  public void testNoDimensionFileLoad() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load noDim.ppm koala\nexit");
    StringBuilder log = new StringBuilder(); //log for mock model
    MockModel mockModel = new MockModel(log);
    ImageController imageController = new ImageController(mockModel, in, out);
    imageController.run();
    String expectedOutput = "Error: String index out of range: 0\n"
        +"Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }

  /**
   * This is a test for the Controller for the vertical flip function.
   * @throws Exception handling different type of exceptions
   */
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

  /**
   * This is a test for the Controller for the vertical flip function.
   * @throws Exception handling different type of exceptions
   */
  @Test
  public void testInvalidCommandVerticalFlip() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    String newImageName = "koala-vertical";
    Reader in = new StringReader("vertical-flip koala as koala-vertical\nexit");
    StringBuilder log = new StringBuilder(); //log for mock model
    MockModel mockModel = new MockModel(log);
    ImageController imageController = new ImageController(mockModel, in, out);
    imageController.run();
    String expectedOutput = "Error: Invalid command format.\n"
        +"Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }

  /**
   * This is a test for the Controller for the horizontal flip function.
   * @throws Exception handling different type of exceptions
   */
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

  /**
   * This is a test for the Controller for the horizontal flip
   * function when function is invalid.
   * @throws Exception handling different type of exceptions
   */
  @Test
  public void testInvalidCommandHorizontalFlip() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    String newImageName = "koala-horizontal";
    Reader in = new StringReader("horizontal-flip koala as koala-horizontal\nexit");
    StringBuilder log = new StringBuilder(); //log for mock model
    MockModel mockModel = new MockModel(log);
    ImageController imageController = new ImageController(mockModel, in, out);
    imageController.run();
    String expectedOutput = "Error: Invalid command format.\n"
        +"Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }

  /**
   * This is a test for the Controller for the brighten function.
   * @throws Exception handling different type of exceptions
   */
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
        "Received inputs for brighten: " + imageName + " " + newImageName + " " + increment + "\n";
    assertEquals(expectedOutput, mockModel.log.toString()); //inputs reached the model correctly
  }

  /**
   * This is a test for the Controller for the brighten function
   * when the fucntion is invalid.
   * @throws Exception handling different type of exceptions
   */
  @Test
  public void testBrightenInvalidCommand() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    String newImageName = "koala-brighten";
    double increment = 10;
    Reader in = new StringReader("brighten 10 koala as koala-brighten\nexit");
    StringBuilder log = new StringBuilder(); //log for mock model
    MockModel mockModel = new MockModel(log);
    ImageController imageController = new ImageController(mockModel, in, out);
    imageController.run();
    String expectedOutput = "Error: Invalid command format.\n"
        +"Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }

  /**
   * This is a method to test Controller to make sure the Visualize
   * each value component for RGB
   * image works as expected.
   * @throws Exception handles any type of exception
   */
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

  /**
   * This is a method to test Controller to make sure the Visualize each
   * value component for RGB
   * image, the output should show and error for invalid command
   * @throws Exception handles any type of exception
   */
  @Test
  public void testGreyScaleValueInvalidCommand() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    String newImageName = "koala-greyscale-value";
    String measure = "value";
    Reader in = new StringReader("greyscale value-component koala as koala-greyscale-value\n"
        + "exit");
    StringBuilder log = new StringBuilder(); //log for mock model
    MockModel mockModel = new MockModel(log);
    ImageController imageController = new ImageController(mockModel, in, out);
    imageController.run();
    String expectedOutput = "Error: Invalid command format.\n"
        +"Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }

  /**
   * This is a method to test Controller to make sure the Visualize each
   * luma component for RGB
   * image works as expected.
   * @throws Exception handles any type of exception
   */
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

  /**
   * This is a method to test Controller to make sure the Visualize
   * each intensity component for RGB
   * image works as expected.
   * @throws Exception handles any type of exception
   */
  @Test
  public void testGreyScaleIntensity() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    String newImageName = "koala-greyscale-intensity";
    String measure = "intensity";
    Reader in = new StringReader(
        "greyscale intensity-component koala koala-greyscale-intensity\nexit");
    StringBuilder log = new StringBuilder(); //log for mock model
    MockModel mockModel = new MockModel(log);
    ImageController imageController = new ImageController(mockModel, in, out);
    imageController.run();
    String expectedOutput =
        "Received inputs for visualizeValueIntensityLuma: " + imageName + " " + newImageName + " "
            + measure + "\n";
    assertEquals(expectedOutput, mockModel.log.toString()); //inputs reached the model correctly
  }

  /**
   * This is a method to test Controller to make sure the
   * Visualize each red component for RGB
   * image works as expected.
   * @throws Exception handles any type of exception
   */
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

  /**
   * This is a method to test Controller to make sure the
   * Visualize each green component for RGB
   * image works as expected.
   * @throws Exception handles any type of exception
   */
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

  /**
   * This is a method to test Controller to make sure the
   * Visualize each blue component for RGB
   * image works as expected.
   * @throws Exception handles any type of exception
   */
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

  /**
   * This is a method to test Controller to make sure splitting
   * RGB into greyscale works as
   * expected.
   * image works as expected.
   * @throws Exception handles any type of exception
   */
  @Test
  public void testSplitRGB() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    String newImageNameR = "koala-greyscale-red";
    String newImageNameG = "koala-greyscale-green";
    String newImageNameB = "koala-greyscale-blue";

    Reader in = new StringReader("rgb-split koala koala-greyscale-red koala-greyscale-green "
        + "koala-greyscale-blue\nexit");
    StringBuilder log = new StringBuilder(); //log for mock model
    MockModel mockModel = new MockModel(log);
    ImageController imageController = new ImageController(mockModel, in, out);
    imageController.run();
    String expectedOutput = "Received inputs for splitInto3Images: " + imageName + " "
            + newImageNameR + " "
            + newImageNameG + " "
            + newImageNameB + "\n";
    assertEquals(expectedOutput, mockModel.log.toString()); //inputs reached the model correctly
  }

  /**
   * This is a method to test Controller to make sure combining
   * greyscale into RBG works as
   * expected.
   * image works as expected.
   * @throws Exception handles any type of exception
   */
  @Test
  public void testGreyScaleCombine() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageNameR = "koala-greyscale-red";
    String imageNameG = "koala-greyscale-green";
    String imageNameB = "koala-greyscale-blue";
    String newimageName = "koalaNew";

    Reader in = new StringReader("rgb-combine koalaNew koala-greyscale-red koala-greyscale-green "
        + "koala-greyscale-blue\nexit");
    StringBuilder log = new StringBuilder(); //log for mock model
    MockModel mockModel = new MockModel(log);
    ImageController imageController = new ImageController(mockModel, in, out);
    imageController.run();
    String expectedOutput = "Received inputs for combineGreyScaleToRGB: "
        + newimageName +" " + imageNameR + " " + imageNameG + " " + imageNameB + "\n";
    assertEquals(expectedOutput, mockModel.log.toString()); //inputs reached the model correctly
  }

  /**
   * This method is used to check if the output throws exception
   * with appropriate message if
   * command is incorrect.
   * @throws Exception handles exception
   */
  @Test
  public void testRunCommand() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("run script1.txt\nexit");
    StringBuilder log = new StringBuilder(); //log for mock model
    MockModel mockModel = new MockModel(log);
    ImageController imageController = new ImageController(mockModel, in, out);
    imageController.run();
    String expectedOutput = "Loaded image 'koalanew' from 'images/koala.ppm'\n"
    +"Image brightened 'koalanew' stored as 'koalanew-brighter'\n"
    +"Image koalanew-brighter saved as file: images/koalanew-brighter.ppm\n"
    +"Error: null\n"
    +"Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }

  /**
   * This method is used to check if the output throws exception
   * with appropriate message if
   * command is incorrect.
   * @throws Exception handles exception
   */
  @Test
  public void testRunInvalidCommand() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("run as script1.txt\nexit");
    StringBuilder log = new StringBuilder(); //log for mock model
    MockModel mockModel = new MockModel(log);
    ImageController imageController = new ImageController(mockModel, in, out);
    imageController.run();
    String expectedOutput = "Error: Invalid command format.\n"
        +"Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }

  /**
   * This method is used to check if the output gives an appropriate
   * message when saved.
   * @throws Exception illegal argument exception
   */
  @Test
  public void testSaveCommand() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    Reader in = new StringReader("save koala1.ppm koala\nexit");
    StringBuilder log = new StringBuilder(); //log for mock model
    MockModel mockModel = new MockModel(log);
    ImageController imageController = new ImageController(mockModel, in, out);
    imageController.run();
    String expectedOutput = "Image koala saved as file: koala1.ppm\n"
    +"Error: null\n"
    +"Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }

  /**
   * This method is used to check if the output throws exception
   * with appropriate message if
   * command is incorrect.
   * @throws Exception handles exception
   */
  @Test
  public void testSaveInvalidCommand() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    Reader in = new StringReader("save koala1.ppm as koala\nexit");
    StringBuilder log = new StringBuilder(); //log for mock model
    MockModel mockModel = new MockModel(log);
    ImageController imageController = new ImageController(mockModel, in, out);
    imageController.run();
    String expectedOutput = "Error: Invalid command format.\n"
        +"Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }

  /**
   * This method is used to check if the output throws exception
   * with appropriate message if
   * command is incorrect.
   * @throws Exception handles exception
   */
  @Test
  public void testSaveInvalidFilepath() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    Reader in = new StringReader("save allimages/koala1.ppm koala\nexit");
    StringBuilder log = new StringBuilder(); //log for mock model
    MockModel mockModel = new MockModel(log);
    ImageController imageController = new ImageController(mockModel, in, out);
    imageController.run();
    String expectedOutput = "Image koala saved as file: allimages/koala1.ppm\n"
        +"File path does not exist.\n"
        +"Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }

  /**
   * This method is used to check if the output throws
   * exception with appropriate message if
   * command is incorrect.
   * @throws Exception handles exception
   */
  @Test
  public void testSaveInvalidFiletype() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    Reader in = new StringReader("save images/koala1.jpg koala\nexit");
    StringBuilder log = new StringBuilder(); //log for mock model
    MockModel mockModel = new MockModel(log);
    ImageController imageController = new ImageController(mockModel, in, out);
    imageController.run();
    String expectedOutput = "Image koala saved as file: images/koala1.jpg\n"
    +"Error: Invalid file format: jpg\n"
    +"Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }

  /**
   * This method is used to check if the output throws
   * exception with appropriate message if
   * command is incorrect.
   * @throws Exception handles exception
   */
  @Test
  public void testSaveNestedFilepath() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala1";
    Reader in = new StringReader("save image/rgbimage/new/koala.ppm koala1\nexit");
    StringBuilder log = new StringBuilder(); //log for mock model
    MockModel mockModel = new MockModel(log);
    ImageController imageController = new ImageController(mockModel, in, out);
    imageController.run();
    String expectedOutput = "Image koala1 saved as file: image/rgbimage/new/koala.ppm\n"
    +"Error: null\n"
    +"Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }

  /**
   * This method is used to check if the output for exit command.
   * @throws Exception handles exception
   */
  @Test
  public void testExitcommand() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("exit");
    StringBuilder log = new StringBuilder(); //log for mock model
    MockModel mockModel = new MockModel(log);
    ImageController imageController = new ImageController(mockModel, in, out);
    imageController.run();
    String expectedOutput = "Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }

  /**
   * This method is used to check if the output throws exception
   * with appropriate message if
   * command is incorrect.
   * @throws Exception handles exception
   */
  @Test
  public void testExitInvalidcommand() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("exit program\nexit");
    StringBuilder log = new StringBuilder(); //log for mock model
    MockModel mockModel = new MockModel(log);
    ImageController imageController = new ImageController(mockModel, in, out);
    imageController.run();
    String expectedOutput = "Error: Invalid command format.\n"
        +"Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }
}



