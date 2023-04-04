import static java.lang.Math.min;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import model.MeasurementType;
import model.ComponentRGB;
import model.Model;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;
import model.RGBIntegerImage.RGBIntegerImageBuilder;
import model.RGBIntegerAlphaImage.RGBIntegerAlphaImageBuilder;
import model.RGBIntegerObject;
import model.RGBIntegerAlphaObject;
import model.TypeofImageObject;
import model.TypeOfImage;

/**
 * This is a test class that is used to test the Model class.
 */
public class ModelTest {

  Model model = new Model();

  RGBIntegerImageBuilder testImage1;
  RGBIntegerImageBuilder testImage2;
  RGBIntegerImageBuilder expectedImage;

  int width;
  int height;
  Integer c;


  /**
   * This method is used to setup all the images required for
   * testing purposes.
   */
  @Before
  public void setUp() {
    width = 5;
    height = 5;

    testImage1 = new RGBIntegerImageBuilder(width, height);
    c = 0;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        testImage1.addPixelAtPosition(j, i, new RGBIntegerObject(c, c, c));
        c += 1;
      }
    }
  }

  /**
   * This is a method used to test if the brighten method in
   * the model works.
   */
  @Test
  public void testIfBrightenWorks() {

    c = 0;
    expectedImage = new RGBIntegerImageBuilder(width, height);
    int increment = 10;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        expectedImage.addPixelAtPosition(j, i,
                new RGBIntegerObject(((int) Math.min(255, Math.max(0, c + increment))),
                        ((int) Math.min(255, Math.max(0, c + increment))),
                        (int) Math.min(255, Math.max(0, c + increment))));
        c += 1;
      }
    }
    //before brighterning
    model.addImageToModel(testImage1.buildImage(), "test+image");
    model.addImageToModel(expectedImage.buildImage(), "expected+imageBrightenBy10");
    assertFalse(
            model.getObject("test+image").equals(model.getObject("expected+imageBrightenBy10")));

    //after brighterning
    model.brighten("test+image", "test-bright", 10);
    assertTrue(
            model.getObject("test-bright").equals(model.getObject("expected+imageBrightenBy10")));
  }

  /**
   * This is a method used to test if the brighten method works
   * by performing darken using the
   * increment value in the model works.
   */
  @Test
  public void testIfDarkenWorks() {

    c = 0;
    expectedImage = new RGBIntegerImageBuilder(width, height);
    int increment = -10;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        expectedImage.addPixelAtPosition(j, i,
                new RGBIntegerObject(((int) Math.min(255, Math.max(0, c + increment))),
                        ((int) Math.min(255, Math.max(0, c + increment))),
                        (int) Math.min(255, Math.max(0, c + increment))));
        c += 1;
      }
    }
    //before
    model.addImageToModel(testImage1.buildImage(), "test+image");
    model.addImageToModel(expectedImage.buildImage(), "expected+imageDarkenBy10");
    model.getObject("test+image");
    model.getObject("expected+imageDarkenBy10");

    assertFalse(model.getObject("test+image").equals(model.getObject("expected+imageDarkenBy10")));

    //after
    model.brighten("test+image", "test-darken", -10);
    assertTrue(model.getObject("test-darken").equals(model.getObject("expected+imageDarkenBy10")));
  }

  /**
   * This is a method used to test if exception is thrown
   * when then name of image not present in hashmap.
   */
  @Test(expected = NoSuchElementException.class)
  public void testIfBrightenThrowsException() {

    c = 0;
    expectedImage = new RGBIntegerImageBuilder(width, height);
    int increment = 10;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        expectedImage.addPixelAtPosition(j, i,
                new RGBIntegerObject(((int) Math.min(255, Math.max(0, c + increment))),
                        ((int) Math.min(255, Math.max(0, c + increment))),
                        (int) Math.min(255, Math.max(0, c + increment))));
        c += 1;
      }
    }
    //before brighterning
    model.addImageToModel(testImage1.buildImage(), "test+image");
    model.addImageToModel(expectedImage.buildImage(), "expected+imageBrightenBy10");
    assertFalse(
            model.getObject("test+image").equals(model.getObject("expected+imageBrightenBy10")));

    //after brighterning
    model.brighten("test+image", "test-bright", 10);
    assertTrue(
            model.getObject("test-bright01").equals(model.getObject("expected+imageBrightenBy10")));
  }

  /**
   * This is a method to test exception thrown when rgb
   * values are negative.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testForBrightenNegRGB() {

    c = -1;
    expectedImage = new RGBIntegerImageBuilder(width, height);
    int increment = 10;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        expectedImage.addPixelAtPosition(j, i,
                new RGBIntegerObject(((int) Math.min(255, Math.max(0, c + increment))),
                        ((int) Math.min(255, Math.max(0, c + increment))),
                        (int) Math.min(255, Math.max(0, c + increment))));
      }
    }
    testImage1 = new RGBIntegerImageBuilder(width, height);
    c = -1;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        testImage1.addPixelAtPosition(j, i, new RGBIntegerObject(c, c, c));
      }
    }

    //before
    model.addImageToModel(testImage1.buildImage(), "test+image");
    model.addImageToModel(expectedImage.buildImage(), "expected+imageBrightenBy10");
    assertFalse(
            model.getObject("test+image").equals(model.getObject("expected+imageBrightenBy10")));

    //after
    model.brighten("test+image", "test-brighten", 10);
    assertTrue(
            model.getObject("test-brighten").equals(model.getObject("expected+imageBrightenBy10")));
  }

  /**
   * Test to check if brighten works for an all black image.
   */
  @Test
  public void testForBrightenAllBlack() {

    c = 0;
    expectedImage = new RGBIntegerImageBuilder(width, height);
    int increment = 10;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        expectedImage.addPixelAtPosition(j, i,
                new RGBIntegerObject(((int) Math.min(255, Math.max(0, c + increment))),
                        ((int) Math.min(255, Math.max(0, c + increment))),
                        (int) Math.min(255, Math.max(0, c + increment))));
      }
    }
    testImage1 = new RGBIntegerImageBuilder(width, height);
    c = 0;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        testImage1.addPixelAtPosition(j, i, new RGBIntegerObject(c, c, c));
      }
    }

    //before
    model.addImageToModel(testImage1.buildImage(), "test+image");
    model.addImageToModel(expectedImage.buildImage(), "expected+imageBrightenBy10");
    assertFalse(
            model.getObject("test+image").equals(model.getObject("expected+imageBrightenBy10")));

    //after
    model.brighten("test+image", "test-brighten", 10);
    assertTrue(
            model.getObject("test-brighten").equals(model.getObject("expected+imageBrightenBy10")));
  }

  /**
   * This method is used to test if the darken method works to darken
   * an all balck image or if
   * the value is the same.
   */
  @Test
  public void testForDarkenAllBlack() {

    c = 0;
    expectedImage = new RGBIntegerImageBuilder(width, height);
    int increment = -10;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        expectedImage.addPixelAtPosition(j, i,
                new RGBIntegerObject(((int) Math.min(255, Math.max(0, c + increment))),
                        ((int) Math.min(255, Math.max(0, c + increment))),
                        (int) Math.min(255, Math.max(0, c + increment))));
      }
    }
    testImage2 = new RGBIntegerImageBuilder(width, height);
    c = 0;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        testImage1.addPixelAtPosition(j, i, new RGBIntegerObject(c, c, c));
      }
    }

    //before
    model.addImageToModel(testImage2.buildImage(), "test+image");
    model.addImageToModel(expectedImage.buildImage(), "expected+imageDarkenBlackBy10");
    assertTrue(
            model.getObject("test+image").equals(model.getObject("expected+imageDarkenBlackBy10")));

    //after
    model.brighten("test+image", "test-brighten", -10);
    assertTrue(
            model.getObject("test-brighten").equals(model.getObject("expected+imageDarkenBlackBy10")));
  }

  /**
   * This method is used to test if the brighten method works
   * for all white image.
   */
  @Test
  public void testForBrightenAllWhite() {

    c = 255;
    expectedImage = new RGBIntegerImageBuilder(width, height);
    Integer increment = 10;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        expectedImage.addPixelAtPosition(j, i,
                new RGBIntegerObject(((Integer) Math.min(255, Math.max(0, c + increment))),
                        ((Integer) Math.min(255, Math.max(0, c + increment))),
                        (Integer) Math.min(255, Math.max(0, c + increment))));
      }
    }
    testImage2 = new RGBIntegerImageBuilder(width, height);
    c = 255;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        testImage1.addPixelAtPosition(j, i, new RGBIntegerObject(c, c, c));
      }
    }

    //before
    model.addImageToModel(testImage1.buildImage(), "test+image");
    model.addImageToModel(expectedImage.buildImage(), "expected+imageBrightenBy10");
    assertTrue(model.getObject("test+image").equals(model.getObject("expected+imageBrightenBy10")));

    //after
    model.brighten("test+image", "test-brighten", 10);
    assertTrue(
            model.getObject("test-brighten").equals(model.getObject("expected+imageBrightenBy10")));
  }

  /**
   * This method is used to test if the darken method works
   * for all white image.
   */
  @Test
  public void testForDarkenAllWhite() {
    c = 255;
    expectedImage = new RGBIntegerImageBuilder(width, height);
    double increment = -10;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Integer r = (int) min(255, Math.max(0, c + increment));
        Integer g = (int) min(255, Math.max(0, c + increment));
        Integer b = (int) min(255, Math.max(0, c + increment));
        expectedImage.addPixelAtPosition(j, i, new RGBIntegerObject(r, g, b));

      }
    }
    testImage1 = new RGBIntegerImageBuilder(width, height);
    c = 255;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        testImage1.addPixelAtPosition(j, i, new RGBIntegerObject(c, c, c));
      }
    }
    //before
    model.addImageToModel(testImage1.buildImage(), "test+image");
    model.addImageToModel(expectedImage.buildImage(), "expected+imageBrightenBy10");
    assertFalse(
            model.getObject("test+image").equals(model.getObject("expected+imageBrightenBy10")));

    //after
    model.brighten("test+image", "test-brighten", -10);
    assertTrue(
            model.getObject("test-brighten").equals(model.getObject("expected+imageBrightenBy10")));
  }

  /**
   * test case when the rgb values are greater than 255.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testForRGBValuesGreaterThan255() {

    c = 260;
    expectedImage = new RGBIntegerImageBuilder(width, height);
    int increment = 10;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        expectedImage.addPixelAtPosition(j, i,
                new RGBIntegerObject(((int) Math.min(255, Math.max(0, c + increment))),
                        ((int) Math.min(255, Math.max(0, c + increment))),
                        (int) Math.min(255, Math.max(0, c + increment))));
      }
    }
    testImage1 = new RGBIntegerImageBuilder(width, height);
    c = 260;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        testImage1.addPixelAtPosition(j, i, new RGBIntegerObject(c, c, c));
      }
    }
    //before
    model.addImageToModel(testImage1.buildImage(), "test+image");
    model.addImageToModel(expectedImage.buildImage(), "expected+imageBrightenBy10");
    assertFalse(
            model.getObject("test+image").equals(model.getObject("expected+imageBrightenBy10")));

    //after
    model.brighten("test+image", "test-brighten", 10);
    assertTrue(
            model.getObject("test-brighten").equals(model.getObject("expected+imageBrightenBy10")));
  }

  /**
   * test case when the increment is a decimal value.
   */
  @Test
  public void testForWhenIncrementisDouble() {

    c = 0;
    expectedImage = new RGBIntegerImageBuilder(width, height);
    double increment = 50.25;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        expectedImage.addPixelAtPosition(j, i,
                new RGBIntegerObject(((int) Math.min(255, Math.max(0, c + increment))),
                        ((int) Math.min(255, Math.max(0, c + increment))),
                        (int) Math.min(255, Math.max(0, c + increment))));
        c += 1;
      }
    }

    //before
    model.addImageToModel(testImage1.buildImage(), "test+image");
    model.addImageToModel(expectedImage.buildImage(), "expected+imageBrightenBy10");
    assertFalse(
            model.getObject("test+image").equals(model.getObject("expected+imageBrightenBy10")));

    //after
    model.brighten("test+image", "test-brighten", 50.25);
    assertTrue(
            model.getObject("test-brighten").equals(model.getObject("expected+imageBrightenBy10")));
  }


  /**
   * test case if the visualize value works.
   *
   * @throws NoSuchFieldException   throws exception when key not in hashmap
   * @throws IllegalAccessException illegal access exception
   */
  @Test
  public void testvisualizeValue() throws NoSuchFieldException, IllegalAccessException {
    c = 0;
    expectedImage = new RGBIntegerImageBuilder(width, height);
    int increment = 10;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        expectedImage.addPixelAtPosition(j, i,
                new RGBIntegerObject(Math.max(c, Math.max(c, c)), Math.max(c, Math.max(c, c)),
                        Math.max(c, Math.max(c, c))));
        c += 1;
      }
    }
    //before
    model.addImageToModel(testImage1.buildImage(), "test+image");
    model.addImageToModel(expectedImage.buildImage(), "expected+imageValue");
    assertTrue(
            model.getObject("test+image").equals(model.getObject("expected+imageValue")));

    //after
    model.visualizeValueIntensityLuma("test+image", "test-value", MeasurementType.value);
    assertTrue(
            model.getObject("test-value").equals(model.getObject("expected+imageValue")));
  }

  /**
   * test case to check if intensity of rgb image works.
   *
   * @throws NoSuchFieldException   handles exception
   * @throws IllegalAccessException handles exception.
   */
  @Test
  public void testIntensity() throws NoSuchFieldException, IllegalAccessException {
    c = 0;
    expectedImage = new RGBIntegerImageBuilder(width, height);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        expectedImage.addPixelAtPosition(j, i,
                new RGBIntegerObject((c + c + c) / 3, (c + c + c) / 3, (c + c + c) / 3));
        c += 1;
      }
    }
    //before
    model.addImageToModel(testImage1.buildImage(), "test+image");
    model.addImageToModel(expectedImage.buildImage(), "expected+imageValue");
    assertTrue(
            model.getObject("test+image").equals(model.getObject("expected+imageValue")));

    //after
    model.visualizeValueIntensityLuma("test+image", "test-intensity", MeasurementType.intensity);
    assertTrue(
            model.getObject("test-intensity").equals(model.getObject("expected+imageValue")));
  }

  /**
   * test case to check if luma of rgb image works.
   *
   * @throws NoSuchFieldException   handles exception
   * @throws IllegalAccessException handles exception.
   */
  @Test
  public void testLuma() throws NoSuchFieldException, IllegalAccessException {
    c = 0;
    expectedImage = new RGBIntegerImageBuilder(width, height);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        double luma = 0.2126 * c + 0.7152 * c + 0.0722 * c;
        expectedImage.addPixelAtPosition(j, i,
                new RGBIntegerObject((int) luma, (int) luma, (int) luma));
        c += 1;
      }
    }
    //before
    model.addImageToModel(testImage1.buildImage(), "test+image");
    model.addImageToModel(expectedImage.buildImage(), "expected+imageValue");

    //after
    model.visualizeValueIntensityLuma("test+image", "test-luma", MeasurementType.luma);
    assertTrue(
            model.getObject("test-luma").equals(model.getObject("expected+imageValue")));
  }

  /**
   * test case to check if red component of rgb image works.
   *
   * @throws NoSuchFieldException   handles exception
   * @throws IllegalAccessException handles exception.
   */
  @Test
  public void testVisualizeRed() throws NoSuchFieldException, IllegalAccessException {
    c = 0;
    int c2 = 1;
    int c3 = 2;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        testImage1.addPixelAtPosition(j, i, new RGBIntegerObject(c, c2, c3));
      }
    }

    model.addImageToModel(testImage1.buildImage(), "RGBImage");
    expectedImage = new RGBIntegerImageBuilder(width, height);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        expectedImage.addPixelAtPosition(j, i,
                new RGBIntegerObject(c, c, c));
      }
    }
    //before
    model.addImageToModel(testImage1.buildImage(), "test+image");
    model.addImageToModel(expectedImage.buildImage(), "expected+imageValue");
    //after
    model.visIndividualComponent("test+image", "test-red", ComponentRGB.red);
    assertTrue(
            model.getObject("test-red").equals(model.getObject("expected+imageValue")));
  }

  /**
   * test case to check if green component of rgb image works.
   *
   * @throws NoSuchFieldException   handles exception
   * @throws IllegalAccessException handles exception.
   */
  @Test
  public void testVisualizeGreen() throws NoSuchFieldException, IllegalAccessException {
    c = 0;
    int c2 = 1;
    int c3 = 2;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        testImage1.addPixelAtPosition(j, i, new RGBIntegerObject(c, c2, c3));
      }
    }

    model.addImageToModel(testImage1.buildImage(), "RGBImage");
    expectedImage = new RGBIntegerImageBuilder(width, height);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        expectedImage.addPixelAtPosition(j, i,
                new RGBIntegerObject(c2, c2, c2));
      }
    }
    //before
    model.addImageToModel(testImage1.buildImage(), "test+image");
    model.addImageToModel(expectedImage.buildImage(), "expected+imageValue");
    //after
    model.visIndividualComponent("test+image", "test-green", ComponentRGB.green);
    assertTrue(
            model.getObject("test-green").equals(model.getObject("expected+imageValue")));
  }

  /**
   * test case to check if blue component of rgb image works.
   *
   * @throws NoSuchFieldException   handles exception
   * @throws IllegalAccessException handles exception.
   */
  @Test
  public void testVisualizeBlue() throws NoSuchFieldException, IllegalAccessException {
    c = 0;
    int c2 = 1;
    int c3 = 2;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        testImage1.addPixelAtPosition(j, i, new RGBIntegerObject(c, c2, c3));
      }
    }

    model.addImageToModel(testImage1.buildImage(), "RGBImage");
    expectedImage = new RGBIntegerImageBuilder(width, height);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        expectedImage.addPixelAtPosition(j, i,
                new RGBIntegerObject(c3, c3, c3));
      }
    }
    //before
    model.addImageToModel(testImage1.buildImage(), "test+image");
    model.addImageToModel(expectedImage.buildImage(), "expected+imageValue");
    //after
    model.visIndividualComponent("test+image", "test-blue", ComponentRGB.blue);
    assertTrue(
            model.getObject("test-blue").equals(model.getObject("expected+imageValue")));
  }

  /**
   * test case to check to combine greyscale to rgb.
   */
  @Test
  public void testCombineThreeGreyScales() {
    RGBIntegerImageBuilder testImageR = new RGBIntegerImageBuilder(width, height);

    c = 0;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        testImage1.addPixelAtPosition(j, i, new RGBIntegerObject(c, c, c));
      }
    }

    RGBIntegerImageBuilder testImageG = new RGBIntegerImageBuilder(width, height);
    c = 1;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        testImage1.addPixelAtPosition(j, i, new RGBIntegerObject(c, c, c));
      }
    }

    RGBIntegerImageBuilder testImageB = new RGBIntegerImageBuilder(width, height);
    c = 2;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        testImage1.addPixelAtPosition(j, i, new RGBIntegerObject(c, c, c));
      }
    }

    c = 0;
    expectedImage = new RGBIntegerImageBuilder(width, height);
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        new RGBIntegerObject(testImageR.buildImage().getPixels()[i][j].getChanne11(),
                testImageR.buildImage().getPixels()[i][j].getChanne12(),
                testImageR.buildImage().getPixels()[i][j].getChanne13());
        c += 1;
      }
    }
    //before
    model.addImageToModel(testImageR.buildImage(), "testR");
    model.addImageToModel(testImageG.buildImage(), "testG");
    model.addImageToModel(testImageB.buildImage(), "testB");
    model.addImageToModel(expectedImage.buildImage(), "expected+imageValue");

    model.combineGreyScaleToRGB("testR", "testG", "testB", "RGBNewImage");
    assertTrue(
            model.getObject("RGBNewImage").equals(model.getObject("expected+imageValue")));
  }


  /**
   * test case to create a three channel image for rgb image.
   */
  @Test
  public void testSplitIntoThreeChannels() throws NoSuchFieldException, IllegalAccessException {
    c = 0;
    int c2 = 1;
    int c3 = 2;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        testImage1.addPixelAtPosition(j, i, new RGBIntegerObject(c, c2, c3));
      }
    }

    model.addImageToModel(testImage1.buildImage(), "RGBNewImage");
    model.splitInto3Images("RGBNewImage", "testR",
            "testG", "testB");
    RGBIntegerImageBuilder expectedtestImageR = new RGBIntegerImageBuilder(width, height);
    c = 0;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        expectedtestImageR.addPixelAtPosition(j, i, new RGBIntegerObject(c, c, c));
      }
    }

    RGBIntegerImageBuilder expectedtestImageG = new RGBIntegerImageBuilder(width, height);
    c = 1;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        expectedtestImageG.addPixelAtPosition(j, i, new RGBIntegerObject(c, c, c));
      }
    }

    RGBIntegerImageBuilder expectedtestImageB = new RGBIntegerImageBuilder(width, height);
    c = 2;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        expectedtestImageB.addPixelAtPosition(j, i, new RGBIntegerObject(c, c, c));
      }
    }


    //before
    model.addImageToModel(expectedtestImageR.buildImage(), "newtestR");
    model.addImageToModel(expectedtestImageG.buildImage(), "newtestG");
    model.addImageToModel(expectedtestImageB.buildImage(), "newtestB");


    assertTrue(
            model.getObject("testR").equals(model
                    .getObject("newtestR")));
    assertTrue(
            model.getObject("testG").equals(model
                    .getObject("newtestG")));
    assertTrue(
            model.getObject("testB").equals(model
                    .getObject("newtestB")));
  }

  /**
   * test case to check if horizontal flip works.
   */
  @Test
  public void testIfHorizontalWorks() {
    c = 0;
    expectedImage = new RGBIntegerImageBuilder(width, height);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        expectedImage.addPixelAtPosition(j, i, new RGBIntegerObject(c, c, c));
        c += 1;
      }
    }
    TypeOfImage newImage = expectedImage.buildImage();
    TypeofImageObject[][] flippedImage = new RGBIntegerObject[newImage.getWidth()]
            [newImage.getHeight()];
    for (int col = 0; col < newImage.getPixels()[0].length; col++) {
      for (int row = 0; row < newImage.getPixels().length / 2; row++) {
        TypeofImageObject temp = newImage.getPixels()[row][col];
        flippedImage[row][col] = newImage.getPixels()[newImage.getPixels().length - row - 1][col];
        flippedImage[flippedImage.length - row - 1][col] = temp;
      }
    }
//    TypeOfImage flipped = new RGBIntegerImage(flippedImage,newImage.getWidth(),newImage.
//        getHeight());
    //before horizontal flip
    model.addImageToModel(testImage1.buildImage(), "test+image");
    model.addImageToModel(expectedImage.buildImage(), "expectedhori");
    assertTrue(
            model.getObject("test+image").equals(model.getObject
                    ("expectedhori")));

    //after horizontal flip
    model.horizontalFlip("test+image", "test-hori");
    assertFalse(
            model.getObject("test-hori").equals(model.getObject
                    ("expectedhori")));
  }


  /**
   * test case to check if horizontal flip for horizontal image works.
   */
  @Test
  public void testIfHorizontalonHoriImageWorks() {
    c = 0;
    int height1 = 5;
    int width1 = 10;
    testImage1 = new RGBIntegerImageBuilder(width1, height1);
    for (int i = 0; i < height1; i++) {
      for (int j = 0; j < width1; j++) {
        testImage1.addPixelAtPosition(j, i, new RGBIntegerObject(c, c, c));
        c += 1;
      }
    }
    expectedImage = new RGBIntegerImageBuilder(width1, height1);
    for (int i = 0; i < height1; i++) {
      for (int j = 0; j < width1; j++) {
        expectedImage.addPixelAtPosition(j, i, new RGBIntegerObject(c, c, c));
        c += 1;
      }
    }
    TypeOfImage newImage = expectedImage.buildImage();
    TypeofImageObject[][] flippedImage = new RGBIntegerObject[newImage.getWidth()]
            [newImage.getHeight()];
    for (int col = 0; col < newImage.getPixels()[0].length; col++) {
      for (int row = 0; row < newImage.getPixels().length / 2; row++) {
        TypeofImageObject temp = newImage.getPixels()[row][col];
        flippedImage[row][col] = newImage.getPixels()[newImage.getPixels().length - row - 1][col];
        flippedImage[flippedImage.length - row - 1][col] = temp;
      }
    }
//    TypeOfImage flipped = new RGBIntegerImage(flippedImage,newImage.getWidth(),newImage.
//        getHeight());
    //before horizontal flip
    model.addImageToModel(testImage1.buildImage(), "test+image");
    model.addImageToModel(expectedImage.buildImage(), "expectedhori");
    assertFalse(
            model.getObject("test+image").equals(model.getObject
                    ("expectedhori")));

    //after horizontal flip
    model.horizontalFlip("test+image", "test-hori");
    assertFalse(
            model.getObject("test-hori").equals(model.getObject
                    ("expectedhori")));
  }

  /**
   * test case to check if horizontal flip works for vertical image.
   */
  @Test
  public void horizontalFlipvertiImage() {
    c = 0;
    int height1 = 10;
    int width1 = 5;
    testImage1 = new RGBIntegerImageBuilder(width1, height1);
    for (int i = 0; i < height1; i++) {
      for (int j = 0; j < width1; j++) {
        testImage1.addPixelAtPosition(j, i, new RGBIntegerObject(c, c, c));
        c += 1;
      }
    }
    expectedImage = new RGBIntegerImageBuilder(width1, height1);
    for (int i = 0; i < height1; i++) {
      for (int j = 0; j < width1; j++) {
        expectedImage.addPixelAtPosition(j, i, new RGBIntegerObject(c, c, c));
        c += 1;
      }
    }
    TypeOfImage newImage = expectedImage.buildImage();
    TypeofImageObject[][] flippedImage = new RGBIntegerObject[newImage.getWidth()]
            [newImage.getHeight()];
    for (int col = 0; col < newImage.getPixels()[0].length; col++) {
      for (int row = 0; row < newImage.getPixels().length / 2; row++) {
        TypeofImageObject temp = newImage.getPixels()[row][col];
        flippedImage[row][col] = newImage.getPixels()[newImage.getPixels().length - row - 1][col];
        flippedImage[flippedImage.length - row - 1][col] = temp;
      }
    }
//    TypeOfImage flipped = new RGBIntegerImage(flippedImage,newImage.getWidth(),newImage.
//        getHeight());
    //before horizontal flip
    model.addImageToModel(testImage1.buildImage(), "test+image");
    model.addImageToModel(expectedImage.buildImage(), "expectedhori");
    assertFalse(
            model.getObject("test+image").equals(model.getObject
                    ("expectedhori")));

    //after horizontal flip
    model.horizontalFlip("test+image", "test-hori");
    assertFalse(
            model.getObject("test-hori").equals(model.getObject
                    ("expectedhori")));
  }

  /**
   * test case to check if vertical flip works.
   */
  @Test
  public void testIfVerticalFlipWorks() {
    c = 0;
    expectedImage = new RGBIntegerImageBuilder(width, height);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        expectedImage.addPixelAtPosition(j, i, new RGBIntegerObject(c, c, c));
        c += 1;
      }
    }
    TypeOfImage newImage = expectedImage.buildImage();
    TypeofImageObject[][] flippedImage = new RGBIntegerObject[newImage.getWidth()]
            [newImage.getHeight()];
    for (int row = 0; row < newImage.getPixels().length; row++) {
      for (int col = 0; col < newImage.getPixels()[row].length / 2; col++) {
        TypeofImageObject temp = newImage.getPixels()[row][col];
        flippedImage[row][col] = newImage.getPixels()[row][newImage.getPixels()
                [row].length - col - 1];
        flippedImage[row][flippedImage[row].length - col - 1] = temp;
      }
    }
//    TypeOfImage flipped = new RGBIntegerImage(flippedImage,newImage.getWidth(),newImage.
//        getHeight());
    //before horizontal flip
    model.addImageToModel(testImage1.buildImage(), "test+image");
    model.addImageToModel(expectedImage.buildImage(), "expectedverti");
    assertTrue(
            model.getObject("test+image").equals(model.getObject
                    ("expectedverti")));

    //after horizontal flip
    model.horizontalFlip("test+image", "test-verti");
    assertFalse(
            model.getObject("test-verti").equals(model.getObject
                    ("expectedverti")));
  }


  /**
   * test case to check if vertical flip works on horizontal image.
   */
  @Test
  public void testIfVerticalFliponHoriImageWorks() {
    c = 0;
    int height1 = 5;
    int width1 = 10;
    testImage1 = new RGBIntegerImageBuilder(width1, height1);
    for (int i = 0; i < height1; i++) {
      for (int j = 0; j < width1; j++) {
        testImage1.addPixelAtPosition(j, i, new RGBIntegerObject(c, c, c));
        c += 1;
      }
    }
    expectedImage = new RGBIntegerImageBuilder(width1, height1);
    for (int i = 0; i < height1; i++) {
      for (int j = 0; j < width1; j++) {
        expectedImage.addPixelAtPosition(j, i, new RGBIntegerObject(c, c, c));
        c += 1;
      }
    }
    TypeOfImage newImage = expectedImage.buildImage();
    TypeofImageObject[][] flippedImage = new RGBIntegerObject[newImage.getWidth()]
            [newImage.getHeight()];
    for (int row = 0; row < newImage.getPixels().length; row++) {
      for (int col = 0; col < newImage.getPixels()[row].length / 2; col++) {
        TypeofImageObject temp = newImage.getPixels()[row][col];
        flippedImage[row][col] = newImage.getPixels()[row][newImage.getPixels()
                [row].length - col - 1];
        flippedImage[row][flippedImage[row].length - col - 1] = temp;
      }
    }
//    TypeOfImage flipped = new RGBIntegerImage(flippedImage,newImage.getWidth(),newImage.
//        getHeight());
    //before horizontal flip
    model.addImageToModel(testImage1.buildImage(), "test+image");
    model.addImageToModel(expectedImage.buildImage(), "expectedverti");
    assertFalse(
            model.getObject("test+image").equals(model.getObject
                    ("expectedverti")));

    //after horizontal flip
    model.horizontalFlip("test+image", "test-verti");
    assertFalse(
            model.getObject("test-verti").equals(model.getObject
                    ("expectedverti")));
  }

  /**
   * test case to check if vertical flip on vertical image works.
   */
  @Test
  public void testIfVerticalFlipWorksonVertiImage() {
    c = 0;
    int height1 = 10;
    int width1 = 5;
    testImage1 = new RGBIntegerImageBuilder(width1, height1);
    for (int i = 0; i < height1; i++) {
      for (int j = 0; j < width1; j++) {
        testImage1.addPixelAtPosition(j, i, new RGBIntegerObject(c, c, c));
        c += 1;
      }
    }
    expectedImage = new RGBIntegerImageBuilder(width1, height1);
    for (int i = 0; i < height1; i++) {
      for (int j = 0; j < width1; j++) {
        expectedImage.addPixelAtPosition(j, i, new RGBIntegerObject(c, c, c));
        c += 1;
      }
    }
    TypeOfImage newImage = expectedImage.buildImage();
    TypeofImageObject[][] flippedImage = new RGBIntegerObject[newImage.getWidth()]
            [newImage.getHeight()];
    for (int row = 0; row < newImage.getPixels().length; row++) {
      for (int col = 0; col < newImage.getPixels()[row].length / 2; col++) {
        TypeofImageObject temp = newImage.getPixels()[row][col];
        flippedImage[row][col] = newImage.getPixels()[row][newImage.getPixels()
                [row].length - col - 1];
        flippedImage[row][flippedImage[row].length - col - 1] = temp;
      }
    }
//    TypeOfImage flipped = new RGBIntegerImage(flippedImage,newImage.getWidth(),newImage.
//        getHeight());
    //before horizontal flip
    model.addImageToModel(testImage1.buildImage(), "test+image");
    model.addImageToModel(expectedImage.buildImage(), "expectedverti");
    assertFalse(
            model.getObject("test+image").equals(model.getObject
                    ("expectedverti")));

    //after horizontal flip
    model.horizontalFlip("test+image", "test-verti");
    assertFalse(
            model.getObject("test-verti").equals(model.getObject
                    ("expectedverti")));
  }

  /**
   * test case to check if the hashmap adds keys and values.
   */
  @Test
  public void testThatHashMapWorks() {

    c = 0;
    expectedImage = new RGBIntegerImageBuilder(width, height);
    int increment = 10;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        expectedImage.addPixelAtPosition(j, i,
                new RGBIntegerObject((Math.max(0, c + increment)),
                        (Math.max(0, c + increment)),
                        (Math.max(0, c + increment))));
        c += 1;
      }
    }
    //before anything is added
    assertEquals(model.numberOfImagesInModel(), 0);
    model.addImageToModel(testImage1.buildImage(), "test+image");
    assertEquals(model.numberOfImagesInModel(), 1);
    model.addImageToModel(expectedImage.buildImage(), "expected+imageBrightenBy10");
    assertEquals(model.numberOfImagesInModel(), 2);
  }

  /**
   * test case to check when object does not exist in hashmap.
   */
  @Test
  public void testExceptionWhenObjectDoesNotExistInHashMap() {
    assertThrows(NoSuchElementException.class, () -> model.getObject("test+image"));
  }

  /**
   * test case to check if default image gets created.
   */
  @Test
  public void testIfDefaultimageGetsCreated() {
    //default image
    RGBIntegerImageBuilder defaultIamge;
    defaultIamge = new RGBIntegerImageBuilder(width, height);
    model.addImageToModel(defaultIamge.buildImage(), "default+image");

    //image with all 0's
    c = 0;
    expectedImage = new RGBIntegerImageBuilder(width, height);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        expectedImage.addPixelAtPosition(j, i, new RGBIntegerObject(c, c, c));
      }
    }
    model.addImageToModel(expectedImage.buildImage(), "default+image-test");

    assertTrue(model.getObject("default+image").equals(model.getObject("default+image-test")));
  }

  /**
   * test case to check if the blur operation works for an image.
   */
  @Test
  public void testIfBlurImageWorks() {
    c = 0;
    int height1 = 5;
    int width1 = 5;
    testImage1 = new RGBIntegerImageBuilder(width1, height1);
    for (int i = 0; i < height1; i++) {
      for (int j = 0; j < width1; j++) {
        testImage1.addPixelAtPosition(j, i, new RGBIntegerObject(c, c, c));
        c += 1;
      }
    }
    expectedImage = new RGBIntegerImageBuilder(width1, height1);
    for (int i = 0; i < height1; i++) {
      for (int j = 0; j < width1; j++) {
        expectedImage.addPixelAtPosition(j, i, new RGBIntegerObject(c, c, c));
        c += 1;
      }
    }
    // Create a 3x3 Gaussian filter
    double[][] filter = {
            {1.0 / 16.0, 1.0 / 8.0, 1.0 / 16.0},
            {1.0 / 8.0, 1.0 / 4.0, 1.0 / 8.0},
            {1.0 / 16.0, 1.0 / 8.0, 1.0 / 16.0}
    };
    TypeOfImage newImage = expectedImage.buildImage();
    for (int x = 0; x < width1; x++) {
      for (int y = 0; y < height1; y++) {
        double r = 0, g = 0, b = 0;
        TypeofImageObject oldPixel;
        for (int i = -1; i <= 1; i++) {
          for (int j = -1; j <= 1; j++) {
            int xx = x + i;
            int yy = y + j;
            if (xx >= 0 && xx < newImage.getWidth() && yy >= 0 && yy < newImage.getHeight()) {
              oldPixel = newImage.getPixels()[xx][yy];
              double weight = filter[i + 1][j + 1];
              r += weight * oldPixel.getChanne11();
              g += weight * oldPixel.getChanne12();
              b += weight * oldPixel.getChanne13();
            }
          }
        }
        // Add the amount parameter to the filtered values and clamp to [0, 255]
        int r2 = (int) Math.min(255, Math.max(0, Math.round(r)));
        int g2 = (int) Math.min(255, Math.max(0, Math.round(g)));
        int b2 = (int) Math.min(255, Math.max(0, Math.round(b)));
        // Set the value of the corresponding pixel in the new image object
        expectedImage.addPixelAtPosition(width1, height1, new RGBIntegerObject(r2, g2, b2));
      }
    }
    //before blur
    model.addImageToModel(testImage1.buildImage(), "test+image");
    model.addImageToModel(expectedImage.buildImage(), "expected+image+blur");
    assertFalse(
            model.getObject("test+image").equals(model.getObject
                    ("expected+image+blur")));

    //after blur
    model.blur("test+image", "test-blur");
    assertFalse(
            model.getObject("test-blur").equals(model.getObject
                    ("expected+image+blur")));
  }

  /**
   * test case to check if the sharpen operation works for an image.
   */
  @Test
  public void testIfSharpenImageWorks() {
    c = 0;
    int height1 = 5;
    int width1 = 5;
    testImage1 = new RGBIntegerImageBuilder(width1, height1);
    for (int i = 0; i < height1; i++) {
      for (int j = 0; j < width1; j++) {
        testImage1.addPixelAtPosition(j, i, new RGBIntegerObject(c, c, c));
        c += 1;
      }
    }
    expectedImage = new RGBIntegerImageBuilder(width1, height1);
    for (int i = 0; i < height1; i++) {
      for (int j = 0; j < width1; j++) {
        expectedImage.addPixelAtPosition(j, i, new RGBIntegerObject(c, c, c));
        c += 1;
      }
    }
    double[][] filter = {
            {-1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0},
            {-1.0 / 8.0, 1.0 / 4.0, 1.0 / 4.0, 1.0 / 4.0, -1.0 / 8.0},
            {-1.0 / 8.0, 1.0 / 4.0, 1.0, 1.0 / 4.0, -1.0 / 8.0},
            {-1.0 / 8.0, 1.0 / 4.0, 1.0 / 4.0, 1.0 / 4.0, -1.0 / 8.0},
            {-1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0}
    };
    TypeOfImage newImage = expectedImage.buildImage();
    for (int x = 0; x < width1; x++) {
      for (int y = 0; y < height1; y++) {
        double r = 0, g = 0, b = 0;
        TypeofImageObject oldPixel;
        for (int i = -1; i <= 1; i++) {
          for (int j = -1; j <= 1; j++) {
            int xx = x + i;
            int yy = y + j;
            if (xx >= 0 && xx < newImage.getWidth() && yy >= 0 && yy < newImage.getHeight()) {
              oldPixel = newImage.getPixels()[xx][yy];
              double weight = filter[i + 1][j + 1];
              r += weight * oldPixel.getChanne11();
              g += weight * oldPixel.getChanne12();
              b += weight * oldPixel.getChanne13();
            }
          }
        }
        // Add the amount parameter to the filtered values and clamp to [0, 255]
        int r2 = (int) Math.min(255, Math.max(0, Math.round(r)));
        int g2 = (int) Math.min(255, Math.max(0, Math.round(g)));
        int b2 = (int) Math.min(255, Math.max(0, Math.round(b)));
        // Set the value of the corresponding pixel in the new image object
        expectedImage.addPixelAtPosition(x, y, new RGBIntegerObject(r2, g2, b2));
      }
    }
    //before sharpen
    model.addImageToModel(testImage1.buildImage(), "test+image");
    model.addImageToModel(expectedImage.buildImage(), "expected+image+sharpen");
    assertFalse(
            model.getObject("test+image").equals(model.getObject
                    ("expected+image+sharpen")));
    //after sharpen
    model.sharpen("test+image", "test-sharpen");
    assertFalse(
            model.getObject("test-sharpen").equals(model.getObject
                    ("expected+image+sharpen")));
  }

  /**
   * test case to check if the greyscale operation works for an image.
   */
  @Test
  public void testIfGreyScaleImageWorks() {
    c = 0;
    expectedImage = new RGBIntegerImageBuilder(width, height);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        double luma = 0.2126 * c + 0.7152 * c + 0.0722 * c;
        expectedImage.addPixelAtPosition(j, i,
                new RGBIntegerObject((int) luma, (int) luma, (int) luma));
        c += 1;
      }
    }
    //before
    model.addImageToModel(testImage1.buildImage(), "test+image");
    model.addImageToModel(expectedImage.buildImage(), "expected+imageValue");

    //after
    model.visualizeValueIntensityLuma("test+image", "test-luma", MeasurementType.luma);
    assertTrue(
            model.getObject("test-luma").equals(model.getObject("expected+imageValue")));
  }

  /**
   * test case to check if the greyscale operation works for an image.
   */
  @Test
  public void testIfSepiaToneImageWorks() {
    c = 0;
    int height1 = 5;
    int width1 = 5;
    testImage1 = new RGBIntegerImageBuilder(width1, height1);
    for (int i = 0; i < height1; i++) {
      for (int j = 0; j < width1; j++) {
        testImage1.addPixelAtPosition(j, i, new RGBIntegerObject(c, c, c));
        c += 1;
      }
    }
    expectedImage = new RGBIntegerImageBuilder(width1, height1);
    for (int i = 0; i < height1; i++) {
      for (int j = 0; j < width1; j++) {
        expectedImage.addPixelAtPosition(j, i, new RGBIntegerObject(c, c, c));
        c += 1;
      }
    }
    double[][] sepia = {{0.393, 0.769, 0.189}, {0.349, 0.686, 0.168}, {0.272, 0.534, 0.131}};
    for (int i = 0; i < height1; i++) {
      for (int j = 0; j < width1; j++) {
        //get each pixel (3x1 matrix).
        double[][] result = new double[3][1];
        int[][] pixelValues = new int[3][1];
        pixelValues[0][0] = expectedImage.buildImage().getPixels()[i][j].getChanne11();
        pixelValues[1][0] = expectedImage.buildImage().getPixels()[i][j].getChanne12();
        pixelValues[2][0] = expectedImage.buildImage().getPixels()[i][j].getChanne13();
        for (int m = 0; m < 3; m++) {
          for (int n = 0; n < 1; n++) {
            for (int k = 0; k < 3; k++) {
              result[m][n] += sepia[m][k] * pixelValues[k][n];
            }
          }
        }
        Integer r = (int) min(255, Math.max(0, result[0][0]));
        Integer g = (int) min(255, Math.max(0, result[1][0]));
        Integer b = (int) min(255, Math.max(0, result[2][0]));
        expectedImage.addPixelAtPosition(j, i, new RGBIntegerObject(r, g, b));
      }
    }
    //before
    model.addImageToModel(testImage1.buildImage(), "test+image");
    model.addImageToModel(expectedImage.buildImage(), "expected+imageValue");

    //after
    model.colorTransformationSepia("test+image", "test-sepia");
    assertFalse(
            model.getObject("test-sepia").equals(model.getObject("expected+imageValue")));
  }


  /**
   * test case to check if the blur operation works for an image.
   */
  @Test
  public void testAlphaImageBuild() {
    //default image
    RGBIntegerAlphaImageBuilder defaultIamge;
    defaultIamge = new RGBIntegerAlphaImageBuilder(width, height);
    model.addImageToModel(defaultIamge.buildImage(), "default+image");

    //image with all 0's
    c = 0;
    RGBIntegerAlphaImageBuilder expectedImage2 = new RGBIntegerAlphaImageBuilder(width, height);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        expectedImage2.addPixelAtPosition(j, i, new RGBIntegerAlphaObject(c, c, c, 0));
      }
    }
    model.addImageToModel(expectedImage2.buildImage(), "default+image-test");
    assertTrue(model.getObject("default+image").equals(model.getObject("default+image-test")));
  }


}