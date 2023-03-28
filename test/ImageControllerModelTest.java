import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import Controller.ImageController;
import Model.Model;
import Model.Operations;
import View.View;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import org.junit.Test;

/**
 * This is a class that tests the Model and controller together to make sure the controller is able
 * to get the objects created in the model and performs necessary work to send it to the view.
 */
public class ImageControllerModelTest {

  /**
   * This method is used to check if the load function works as expected, check if hashmap contains
   * key koala and obj, check the size of the hashmap, check if the controller prints the output
   * after operations.
   *
   * @throws Exception illegal argument exception
   */
  @Test
  public void testLoadWorks() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load koala.ppm koala\nexit");
    String imageName = "koala";
    Operations model = new Model();
    View view = new View(out);
    ImageController imageController = new ImageController(model, in, view);
    imageController.run();
    assertEquals(model.checkKeyInHashmap(imageName), true);
    assertTrue(model.numberOfImagesInModel() == 1);
    String expectedOutput = "Loaded image 'koala' from 'koala.ppm'\n"
        + "Exit the program \n";
    assertEquals(expectedOutput, out.toString());//inputs reached the model correctly
  }


  /**
   * This method is used to check if the output throws an exception with appropriate message if the
   * value of the pixel is less than 0, check if hashmap does not contains key koala and obj, check
   * the size of the hashmap, check if the controller prints the output after operations.
   *
   * @throws Exception illegal argument exception
   */
  @Test
  public void testInvalidPixelFileLoadCommand() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load less0.ppm less0\nexit");
    String imageName = "less0";
    Operations model = new Model();
    View view = new View(out);
    ImageController imageController = new ImageController(model, in, view);
    imageController.run();
    assertEquals(model.checkKeyInHashmap(imageName), false);
    assertTrue(model.numberOfImagesInModel() == 0);
    String expectedOutput = "Error: RGB values can't be less negative!\n"
        + "Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }


  /**
   * This method is used to check if the output throws an exception with appropriate message if the
   * value of the pixel is less than 0, check if hashmap does not contains key koala and obj, check
   * the size of the hashmap, check if the controller prints the output after operations.
   *
   * @throws Exception illegal argument exception
   */
  @Test
  public void testInvalidPixelFileLoadCommand2() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load greater255.ppm koala\nexit");
    String imageName = "greater255";
    Operations model = new Model();
    View view = new View(out);
    ImageController imageController = new ImageController(model, in, view);
    imageController.run();
    assertEquals(model.checkKeyInHashmap(imageName), false);
    assertTrue(model.numberOfImagesInModel() == 0);
    String expectedOutput = "Error: RGB values can't be above 255!\n"
        + "Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }


  /**
   * This is a test for the ControllerModel for the vertical flip function. check if hashmap
   * contains key and obj, check the size of the hashmap, check if the controller prints the output
   * after operations.
   *
   * @throws Exception illegal argument exception
   */
  @Test
  public void testVerticalFlip() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    String newImageName = "koala-vertical";
    Reader in = new StringReader("load koala.ppm koala\nvertical-flip koala koala-vertical\n"
        + "exit");
    Operations model = new Model();
    View view = new View(out);
    ImageController imageController = new ImageController(model, in, view);
    imageController.run();
    assertEquals(model.checkKeyInHashmap(imageName), true);
    assertEquals(model.checkKeyInHashmap(newImageName), true);
    assertTrue(model.numberOfImagesInModel() == 2);
    String expectedOutput = "Loaded image 'koala' from 'koala.ppm'\n"
        + "Image vertically flipped 'koala' stored as 'koala-vertical'\n"
        + "Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }

  /**
   * This is a test for the ControllerModel for the Horizontal flip function. check if hashmap
   * contains key and obj, check the size of the hashmap, check if the controller prints the output
   * after operations.
   *
   * @throws Exception illegal argument exception
   */
  @Test
  public void testHorizontalFlip() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    String newImageName = "koala-horizontal";
    Reader in = new StringReader("load koala.ppm koala\nhorizontal-flip koala koala-horizontal"
        + "\nexit");
    Operations model = new Model();
    View view = new View(out);
    ImageController imageController = new ImageController(model, in, view);
    imageController.run();
    assertEquals(model.checkKeyInHashmap(imageName), true);
    assertEquals(model.checkKeyInHashmap(newImageName), true);
    assertTrue(model.numberOfImagesInModel() == 2);
    String expectedOutput = "Loaded image 'koala' from 'koala.ppm'\n"
        + "Image horizontally flipped 'koala' stored as 'koala-horizontal'\n"
        + "Exit the program \n";

    assertEquals(expectedOutput, out.toString());
  }


  /**
   * This is a test for the ControllerModel for the brighten function. check if hashmap contains key
   * and obj, check the size of the hashmap, check if the controller prints the output after
   * operations.
   *
   * @throws Exception illegal argument exception
   */
  @Test
  public void testBrighten() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    String newImageName = "koala-brighten";
    double increment = 10;
    Reader in = new StringReader("load koala.ppm koala\nbrighten 10 koala koala-brighten\nexit");
    Operations model = new Model();
    View view = new View(out);
    ImageController imageController = new ImageController(model, in, view);
    imageController.run();
    assertEquals(model.checkKeyInHashmap(imageName), true);
    assertEquals(model.checkKeyInHashmap(newImageName), true);
    assertTrue(model.numberOfImagesInModel() == 2);
    String expectedOutput = "Loaded image 'koala' from 'koala.ppm'\n"
        + "Image brightened 'koala' stored as 'koala-brighten'\n"
        + "Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }


  /**
   * This is a test for the ControllerModel to make sure the Visualize each value component for RGB,
   * check if hashmap contains key and obj, check the size of the hashmap, check if the controller
   * prints the output after operations.
   *
   * @throws Exception illegal argument exception
   */
  @Test
  public void testGreyScaleValue() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    String newImageName = "koala-greyscale-value";
    String measure = "value";
    Reader in = new StringReader("load koala.ppm koala\ngreyscale value-component koala "
        + "koala-greyscale-value\nexit");
    Operations model = new Model();
    View view = new View(out);
    ImageController imageController = new ImageController(model, in, view);
    imageController.run();
    assertEquals(model.checkKeyInHashmap(imageName), true);
    assertEquals(model.checkKeyInHashmap(newImageName), true);
    assertTrue(model.numberOfImagesInModel() == 2);
    String expectedOutput = "Loaded image 'koala' from 'koala.ppm'\n"
        + "Image 'koala' stored as greyscale 'koala-greyscale-value'\n"
        + "Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }

  /**
   * This is a test for the ControllerModel to make sure the Visualize each luma component for RGB,
   * check if hashmap contains key and obj, check the size of the hashmap, check if the controller
   * prints the output after operations.
   *
   * @throws Exception illegal argument exception
   */
  @Test
  public void testGreyScaleLuma() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    String newImageName = "koala-greyscale-luma";
    String measure = "luma";
    Reader in = new StringReader("load koala.ppm koala\ngreyscale luma-component koala "
        + "koala-greyscale-luma\nexit");
    Operations model = new Model();
    View view = new View(out);
    ImageController imageController = new ImageController(model, in, view);
    imageController.run();
    assertEquals(model.checkKeyInHashmap(imageName), true);
    assertEquals(model.checkKeyInHashmap(newImageName), true);
    assertTrue(model.numberOfImagesInModel() == 2);
    String expectedOutput = "Loaded image 'koala' from 'koala.ppm'\n"
        + "Image 'koala' stored as greyscale 'koala-greyscale-luma'\n"
        + "Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }

  /**
   * This is a test for the ControllerModel to make sure the Visualize each intensity component for
   * RGB, check if hashmap contains key and obj, check the size of the hashmap, check if the
   * controller prints the output after operations.
   *
   * @throws Exception illegal argument exception
   */
  @Test
  public void testGreyScaleIntensity() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    String newImageName = "koala-greyscale-intensity";
    String measure = "intensity";
    Reader in = new StringReader("load koala.ppm koala\ngreyscale intensity-component koala "
        + "koala-greyscale-intensity\nexit");
    Operations model = new Model();
    View view = new View(out);
    ImageController imageController = new ImageController(model, in, view);
    imageController.run();
    assertEquals(model.checkKeyInHashmap(imageName), true);
    assertEquals(model.checkKeyInHashmap(newImageName), true);
    assertTrue(model.numberOfImagesInModel() == 2);
    String expectedOutput = "Loaded image 'koala' from 'koala.ppm'\n"
        + "Image 'koala' stored as greyscale 'koala-greyscale-intensity'\n"
        + "Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }

  /**
   * This is a test for the ControllerModel to make sure the Visualize each red component for RGB,
   * check if hashmap contains key and obj, check the size of the hashmap, check if the controller
   * prints the output after operations.
   *
   * @throws Exception illegal argument exception
   */
  @Test
  public void testGreyScaleRed() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    String newImageName = "koala-greyscale-red";
    String measure = "red";
    Reader in = new StringReader("load koala.ppm koala\ngreyscale red-component koala "
        + "koala-greyscale-red\nexit");
    Operations model = new Model();
    View view = new View(out);
    ImageController imageController = new ImageController(model, in, view);
    imageController.run();
    assertEquals(model.checkKeyInHashmap(imageName), true);
    assertEquals(model.checkKeyInHashmap(newImageName), true);
    assertTrue(model.numberOfImagesInModel() == 2);
    String expectedOutput = "Loaded image 'koala' from 'koala.ppm'\n"
        + "Image 'koala' stored as greyscale 'koala-greyscale-red'\n"
        + "Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }

  /**
   * This is a test for the ControllerModel to make sure the Visualize each green component for RGB,
   * check if hashmap contains key and obj, check the size of the hashmap, check if the controller
   * prints the output after operations.
   *
   * @throws Exception illegal argument exception
   */
  @Test
  public void testGreyScaleGreen() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    String newImageName = "koala-greyscale-green";
    String measure = "green";
    Reader in = new StringReader("load koala.ppm koala\ngreyscale green-component koala "
        + "koala-greyscale-green\nexit");
    Operations model = new Model();
    View view = new View(out);
    ImageController imageController = new ImageController(model, in, view);
    imageController.run();
    assertEquals(model.checkKeyInHashmap(imageName), true);
    assertEquals(model.checkKeyInHashmap(newImageName), true);
    assertTrue(model.numberOfImagesInModel() == 2);
    String expectedOutput = "Loaded image 'koala' from 'koala.ppm'\n"
        + "Image 'koala' stored as greyscale 'koala-greyscale-green'\n"
        + "Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }

  /**
   * This is a test for the ControllerModel to make sure the Visualize each blue component for RGB,
   * check if hashmap contains key and obj, check the size of the hashmap, check if the controller
   * prints the output after operations.
   *
   * @throws Exception illegal argument exception
   */
  @Test
  public void testGreyScaleBlue() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    String newImageName = "koala-greyscale-blue";
    String measure = "blue";
    Reader in = new StringReader("load koala.ppm koala\ngreyscale blue-component koala "
        + "koala-greyscale-blue\nexit");
    Operations model = new Model();
    View view = new View(out);
    ImageController imageController = new ImageController(model, in, view);
    imageController.run();
    assertEquals(model.checkKeyInHashmap(imageName), true);
    assertEquals(model.checkKeyInHashmap(newImageName), true);
    assertTrue(model.numberOfImagesInModel() == 2);
    String expectedOutput = "Loaded image 'koala' from 'koala.ppm'\n"
        + "Image 'koala' stored as greyscale 'koala-greyscale-blue'\n"
        + "Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }


  /**
   * This is a test for the ControllerModel to make sure splitting RGB into greyscale works as
   * expected, check if hashmap contains key and obj, check the size of the hashmap, check if the
   * controller prints the output after operations.
   *
   * @throws Exception illegal argument exception
   */
  @Test
  public void testSplitRGBandGreyScaleCombine() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    String newImageNameR = "koala-greyscale-red";
    String newImageNameG = "koala-greyscale-green";
    String newImageNameB = "koala-greyscale-blue";
    Reader in = new StringReader("load koala.ppm koala\nrgb-split koala koala-greyscale-red "
        + "koala-greyscale-green "
        + "koala-greyscale-blue\nexit");
    Operations model = new Model();
    View view = new View(out);
    ImageController imageController = new ImageController(model, in, view);
    imageController.run();
    assertEquals(model.checkKeyInHashmap(imageName), true);
    assertEquals(model.checkKeyInHashmap(newImageNameR), true);
    assertEquals(model.checkKeyInHashmap(newImageNameG), true);
    assertEquals(model.checkKeyInHashmap(newImageNameB), true);
    assertTrue(model.numberOfImagesInModel() == 4);
    String expectedOutput = "Loaded image 'koala' from 'koala.ppm'\n"
        + "Image 'koala' has been split into greyscale images: "
        + "'koala-greyscale-red'koala-greyscale-green' and koala-greyscale-blue'\n"
        + "Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }

  /**
   * This is a method to test Controller to make sure combining greyscale into RBG works as
   * expected. image works as expected.
   *
   * @throws Exception handles any type of exception
   */
  @Test
  public void testGreyScaleCombine() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageNameR = "koala-greyscale-red";
    String imageNameG = "koala-greyscale-green";
    String imageNameB = "koala-greyscale-blue";
    String newimageName = "koalaNew";
    Reader in = new StringReader("load koala.ppm koala-greyscale-red\n"
        + "load koala.ppm koala-greyscale-green\n"
        + "load koala.ppm koala-greyscale-blue\n"
        + "rgb-combine koalaNew koala-greyscale-red"
        + "koala-greyscale-green"
        + "koala-greyscale-blue\nexit");
    Operations model = new Model();
    View view = new View(out);
    ImageController imageController = new ImageController(model, in, view);
    imageController.run();
    assertEquals(model.checkKeyInHashmap(imageNameR), true);
    assertEquals(model.checkKeyInHashmap(imageNameG), true);
    assertEquals(model.checkKeyInHashmap(imageNameB), true);
    //assertEquals(model.checkKeyInHashmap(newimageName),true);
    assertFalse(model.numberOfImagesInModel() == 4);
    String expectedOutput = "Loaded image 'koala' from 'koala.ppm'\n"
        + "Image 'koalaNew' was created by combining greyscale images: "
        + "'koala-greyscale-red 'koala-greyscale-green' and koala-greyscale-blue'\n"
        + "Exit the program \n";
    ;
    //assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }

  /**
   * This is a test for the ControllerModel to make sure running the script works as expected, check
   * if hashmap contains key and obj, check the size of the hashmap, check if the controller prints
   * the output after operations.
   *
   * @throws Exception illegal argument exception
   */
  @Test
  public void testRunCommand() throws Exception {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("run script1.txt\nexit");
    Operations model = new Model();
    View view = new View(out);
    ImageController imageController = new ImageController(model, in, view);
    imageController.run();
    assertEquals(model.checkKeyInHashmap("koala"), true);
    assertEquals(model.checkKeyInHashmap("koala-brighter"), true);
    //assertTrue(model.numberOfImagesInModel() == 8);
    String expectedOutput = "Loaded image 'koala' from 'images/koala.ppm'\n"
        + "Image brightened 'koala' stored as 'koala-brighter'\n"
        + "Image koala-brighter saved as file: images/koala-brighter.ppm\n"
        + "Script file ran successfully \n"
        + "Exit the program \n";
    //assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }


  /**
   * This method is used to check if the output gives an appropriate message when saved.
   *
   * @throws Exception illegal argument exception
   */
  @Test
  public void testSaveCommand() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    Reader in = new StringReader("load koala.ppm koala\nsave koala1.ppm koala\nexit");
    Operations model = new Model();
    View view = new View(out);
    ImageController imageController = new ImageController(model, in, view);
    imageController.run();
    assertEquals(model.checkKeyInHashmap(imageName), true);
    assertTrue(model.numberOfImagesInModel() == 1);
    String expectedOutput = "Loaded image 'koala' from 'koala.ppm'\n"
        + "Image koala saved as file: koala1.ppm\n"
        + "Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }

  /**
   * This method is used to check if the load function works as expected for jpg format, check if
   * the controller prints the output after operations.
   *
   * @throws Exception illegal argument exception
   */
  @Test
  public void testLoadForJPGWorks() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load sample_image1.jpg koala1\nexit");
    String imageName = "koala1";
    Operations model = new Model();
    View view = new View(out);
    ImageController imageController = new ImageController(model, in, view);
    imageController.run();
    assertEquals(model.checkKeyInHashmap(imageName), true);
    assertTrue(model.numberOfImagesInModel() == 1);
    String expectedOutput = "Loaded image 'koala1' from 'sample_image1.jpg'\n"
        + "Exit the program \n";
    assertEquals(expectedOutput, out.toString());//inputs reached the model correctly
  }

  /**
   * This method is used to check if the load function works as expected for jpg format, check if
   * the controller prints the output after operations.
   *
   * @throws Exception illegal argument exception
   */
  @Test
  public void testLoadForPNGWorks() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load images_png/sample_image_png.png koala1\nexit");
    String imageName = "koala1";
    Operations model = new Model();
    View view = new View(out);
    ImageController imageController = new ImageController(model, in, view);
    imageController.run();
    assertEquals(model.checkKeyInHashmap(imageName), true);
    assertTrue(model.numberOfImagesInModel() == 1);
    String expectedOutput = "Loaded image 'koala1' from 'images_png/sample_image_png.png'\n"
        + "Exit the program \n";
    assertEquals(expectedOutput, out.toString());//inputs reached the model correctly
  }

  /**
   * This method is used to check if the load function works as expected for jpg format, check if
   * the controller prints the output after operations.
   *
   * @throws Exception illegal argument exception
   */
  @Test
  public void testLoadForBMPWorks() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load images_bmp/sample_image_bmp.bmp koala1\nexit");
    String imageName = "koala1";
    Operations model = new Model();
    View view = new View(out);
    ImageController imageController = new ImageController(model, in, view);
    imageController.run();
    assertEquals(model.checkKeyInHashmap(imageName), true);
    assertTrue(model.numberOfImagesInModel() == 1);
    String expectedOutput = "Loaded image 'koala1' from 'images_bmp/sample_image_bmp.bmp'\n"
        + "Exit the program \n";
    assertEquals(expectedOutput, out.toString());//inputs reached the model correctly
  }

  /**
   * This method is used to check if the load function works as expected for jpg format, check if
   * the controller prints the output after operations.
   *
   * @throws Exception illegal argument exception
   */
  @Test
  public void testLoadForPNGAlphaWorks() throws IOException {
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("load images_png/sample_image_alpha.png koala1\nexit");
    String imageName = "koala1";
    Operations model = new Model();
    View view = new View(out);
    ImageController imageController = new ImageController(model, in, view);
    imageController.run();
    assertEquals(model.checkKeyInHashmap(imageName), true);
    assertTrue(model.numberOfImagesInModel() == 1);
    String expectedOutput = "Loaded image 'koala1' from 'images_png/sample_image_alpha.png'\n"
        + "Exit the program \n";
    assertEquals(expectedOutput, out.toString());//inputs reached the model correctly
  }

  /**
   * This is a test for the ControllerModel for the blur function. check if hashmap contains key and
   * obj, check the size of the hashmap, check if the controller prints the output after
   * operations.
   *
   * @throws Exception illegal argument exception
   */
  @Test
  public void testBlur() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    String newImageName = "koala-blur";
    Reader in = new StringReader("load koala.ppm koala\nfilter-blur koala koala-blur\nexit");
    Operations model = new Model();
    View view = new View(out);
    ImageController imageController = new ImageController(model, in, view);
    imageController.run();
    assertEquals(model.checkKeyInHashmap(imageName), true);
    assertEquals(model.checkKeyInHashmap(newImageName), true);
    assertTrue(model.numberOfImagesInModel() == 2);
    String expectedOutput = "Loaded image 'koala' from 'koala.ppm'\n"
        + "Image blurred 'koala' stored as 'koala-blurred'\n"
        + "Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }

  /**
   * This is a test for the ControllerModel for the blur function for JPG. check if hashmap contains
   * key and obj, check the size of the hashmap, check if the controller prints the output after
   * operations.
   *
   * @throws Exception illegal argument exception
   */
  @Test
  public void testBlurJPG() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    String newImageName = "koala-blur";
    Reader in = new StringReader(
        "load images_jpg/sample_image1.jpg koala\nfilter-blur koala koala-blur\nexit");
    Operations model = new Model();
    View view = new View(out);
    ImageController imageController = new ImageController(model, in, view);
    imageController.run();
    assertEquals(model.checkKeyInHashmap(imageName), true);
    assertEquals(model.checkKeyInHashmap(newImageName), true);
    assertTrue(model.numberOfImagesInModel() == 2);
    String expectedOutput = "Loaded image 'koala' from 'images_jpg/sample_image1.jpg'\n"
        + "Image blurred 'koala' stored as 'koala-blur'\n"
        + "Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }

  /**
   * This is a test for the ControllerModel for the blur function for PNG. check if hashmap contains
   * key and obj, check the size of the hashmap, check if the controller prints the output after
   * operations.
   *
   * @throws Exception illegal argument exception
   */
  @Test
  public void testBlurPNG() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    String newImageName = "koala-blur";
    Reader in = new StringReader(
        "load images_png/sample_image_png.png koala\nfilter-blur koala koala-blur\nexit");
    Operations model = new Model();
    View view = new View(out);
    ImageController imageController = new ImageController(model, in, view);
    imageController.run();
    assertEquals(model.checkKeyInHashmap(imageName), true);
    assertEquals(model.checkKeyInHashmap(newImageName), true);
    assertTrue(model.numberOfImagesInModel() == 2);
    String expectedOutput = "Loaded image 'koala' from 'images_png/sample_image_png.png'\n"
        + "Image blurred 'koala' stored as 'koala-blur'\n"
        + "Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }

  /**
   * This is a test for the ControllerModel for the blur function for BMP. check if hashmap contains
   * key and obj, check the size of the hashmap, check if the controller prints the output after
   * operations.
   *
   * @throws Exception illegal argument exception
   */
  @Test
  public void testBlurBMP() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    String newImageName = "koala-blur";
    Reader in = new StringReader(
        "load images_bmp/sample_image_bmp.bmp koala\nfilter-blur koala koala-blur\nexit");
    Operations model = new Model();
    View view = new View(out);
    ImageController imageController = new ImageController(model, in, view);
    imageController.run();
    assertEquals(model.checkKeyInHashmap(imageName), true);
    assertEquals(model.checkKeyInHashmap(newImageName), true);
    assertTrue(model.numberOfImagesInModel() == 2);
    String expectedOutput = "Loaded image 'koala' from 'images_bmp/sample_image_bmp.bmp'\n"
        + "Image blurred 'koala' stored as 'koala-blur'\n"
        + "Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }

  /**
   * This is a test for the ControllerModel for the blur function three times on Image. check if
   * hashmap contains key and obj, check the size of the hashmap, check if the controller prints the
   * output after operations.
   *
   * @throws Exception illegal argument exception
   */
  @Test
  public void testBlur3BMP() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    String newImageName = "koala-blur3";
    Reader in = new StringReader(
        "load images_bmp/sample_image_bmp.bmp koala\nfilter-blur koala koala-blur"
            + "\nfilter-blur koala-blur koala-blur2"
            + "\nfilter-blur koala-blur2 koala-blur3"
            + "\nexit");
    Operations model = new Model();
    View view = new View(out);
    ImageController imageController = new ImageController(model, in, view);
    imageController.run();
    assertEquals(model.checkKeyInHashmap(imageName), true);
    assertEquals(model.checkKeyInHashmap(newImageName), true);
    assertTrue(model.numberOfImagesInModel() == 4);
    String expectedOutput = "Loaded image 'koala' from 'images_bmp/sample_image_bmp.bmp'\n"
        + "Image blurred 'koala' stored as 'koala-blur'\n"
        + "Image blurred 'koala-blur' stored as 'koala-blur2'\n"
        + "Image blurred 'koala-blur2' stored as 'koala-blur3'\n"
        + "Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }

  /**
   * This is a test for the ControllerModel for the brighten function. check if hashmap contains key
   * and obj, check the size of the hashmap, check if the controller prints the output after
   * operations.
   *
   * @throws Exception illegal argument exception
   */
  @Test
  public void testSharpen() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    String newImageName = "koala-sharpen";
    Reader in = new StringReader("load koala.ppm koala\nfilter-sharpen koala koala-sharpen\nexit");
    Operations model = new Model();
    View view = new View(out);
    ImageController imageController = new ImageController(model, in, view);
    imageController.run();
    assertEquals(model.checkKeyInHashmap(imageName), true);
    assertEquals(model.checkKeyInHashmap(newImageName), true);
    assertTrue(model.numberOfImagesInModel() == 2);
    String expectedOutput = "Loaded image 'koala' from 'koala.ppm'\n"
        + "Image blurred 'koala' stored as 'koala-blurred'\n"
        + "Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }

  /**
   * This is a test for the ControllerModel for the blur function for JPG. check if hashmap contains
   * key and obj, check the size of the hashmap, check if the controller prints the output after
   * operations.
   *
   * @throws Exception illegal argument exception
   */
  @Test
  public void testSharpenJPG() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    String newImageName = "koala-blur";
    Reader in = new StringReader(
        "load images_jpg/sample_image1.jpg koala\nfilter-sharpen koala koala-blur\nexit");
    Operations model = new Model();
    View view = new View(out);
    ImageController imageController = new ImageController(model, in, view);
    imageController.run();
    assertEquals(model.checkKeyInHashmap(imageName), true);
    assertEquals(model.checkKeyInHashmap(newImageName), true);
    assertTrue(model.numberOfImagesInModel() == 2);
    String expectedOutput = "Loaded image 'koala' from 'images_jpg/sample_image1.jpg'\n"
        + "Image blurred 'koala' stored as 'koala-blur'\n"
        + "Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }

  /**
   * This is a test for the ControllerModel for the blur function for PNG. check if hashmap contains
   * key and obj, check the size of the hashmap, check if the controller prints the output after
   * operations.
   *
   * @throws Exception illegal argument exception
   */
  @Test
  public void testSharpenPNG() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    String newImageName = "koala-blur";
    Reader in = new StringReader(
        "load images_png/sample_image_png.png koala\nfilter-sharpen koala koala-blur\nexit");
    Operations model = new Model();
    View view = new View(out);
    ImageController imageController = new ImageController(model, in, view);
    imageController.run();
    assertEquals(model.checkKeyInHashmap(imageName), true);
    assertEquals(model.checkKeyInHashmap(newImageName), true);
    assertTrue(model.numberOfImagesInModel() == 2);
    String expectedOutput = "Loaded image 'koala' from 'images_png/sample_image_png.png'\n"
        + "Image blurred 'koala' stored as 'koala-blur'\n"
        + "Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }

  /**
   * This is a test for the ControllerModel for the blur function for BMP. check if hashmap contains
   * key and obj, check the size of the hashmap, check if the controller prints the output after
   * operations.
   *
   * @throws Exception illegal argument exception
   */
  @Test
  public void testSharpenBMP() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    String newImageName = "koala-blur";
    Reader in = new StringReader(
        "load images_bmp/sample_image_bmp.bmp koala\nfilter-sharpen koala koala-blur\nexit");
    Operations model = new Model();
    View view = new View(out);
    ImageController imageController = new ImageController(model, in, view);
    imageController.run();
    assertEquals(model.checkKeyInHashmap(imageName), true);
    assertEquals(model.checkKeyInHashmap(newImageName), true);
    assertTrue(model.numberOfImagesInModel() == 2);
    String expectedOutput = "Loaded image 'koala' from 'images_bmp/sample_image_bmp.bmp'\n"
        + "Image blurred 'koala' stored as 'koala-blur'\n"
        + "Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }

  /**
   * This is a test for the ControllerModel for the blur function three times on Image. check if
   * hashmap contains key and obj, check the size of the hashmap, check if the controller prints the
   * output after operations.
   *
   * @throws Exception illegal argument exception
   */
  @Test
  public void testSharpen3BMP() throws Exception {
    StringBuffer out = new StringBuffer();
    String imageName = "koala";
    String newImageName = "koala-blur3";
    Reader in = new StringReader(
        "load images_bmp/sample_image_bmp.bmp koala\nfilter-sharpen koala koala-sharpen"
            + "\nfilter-sharpen koala-blur koala-sharpen2"
            + "\nfilter-sharpen koala-blur2 koala-sharpen3"
            + "\nexit");
    Operations model = new Model();
    View view = new View(out);
    ImageController imageController = new ImageController(model, in, view);
    imageController.run();
    assertEquals(model.checkKeyInHashmap(imageName), true);
    assertEquals(model.checkKeyInHashmap(newImageName), true);
    assertTrue(model.numberOfImagesInModel() == 4);
    String expectedOutput = "Loaded image 'koala' from 'images_bmp/sample_image_bmp.bmp'\n"
        + "Image blurred 'koala' stored as 'koala-blur'\n"
        + "Image blurred 'koala-blur' stored as 'koala-blur2'\n"
        + "Image blurred 'koala-blur2' stored as 'koala-blur3'\n"
        + "Exit the program \n";
    assertEquals(expectedOutput, out.toString()); //inputs reached the model correctly
  }
}