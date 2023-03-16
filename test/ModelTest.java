import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import Model.MeasurementType;
import Model.Component;
import Model.Model;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;
import Model.RGBIntegerImage.threeChannelImageBuilder;
import Model.RGBIntegerObject;

public class ModelTest {

  Model model = new Model();

  threeChannelImageBuilder   testImage1;
  threeChannelImageBuilder testImage2;
  threeChannelImageBuilder expectedImage;

  int width;
  int height;
  int c;


  @Before
  public void setUp() {
    width = 5;
    height = 5;

     testImage1 = new threeChannelImageBuilder(width, height);
    c = 0;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        testImage1.addPixelAtPosition(j, i, new RGBIntegerObject(c, c, c));
        c += 1;
      }
    }


  }

  @Test
  public void testIfBrightenWorks() {

    c = 0;
     expectedImage = new threeChannelImageBuilder(width, height);
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

  //darken the image
  @Test
  public void testIfDarkenWorks() {

    c = 0;
    expectedImage = new threeChannelImageBuilder(width, height);
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

  //throw exception when then name of image not present in hashmap
  @Test(expected = NoSuchElementException.class)
  public void testIfBrightenThrowsException() {

    c = 0;
    expectedImage = new threeChannelImageBuilder(width, height);
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

  //test case when the rgb values are negative
  //darken the image
  @Test(expected = IllegalArgumentException.class)
  public void testForBrightenNegRGB() {

    c = -1;
    expectedImage = new threeChannelImageBuilder(width, height);
    int increment = 10;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        expectedImage.addPixelAtPosition(j, i,
            new RGBIntegerObject(((int) Math.min(255, Math.max(0, c + increment))),
                ((int) Math.min(255, Math.max(0, c + increment))),
                (int) Math.min(255, Math.max(0, c + increment))));
      }
    }
    testImage1 =new threeChannelImageBuilder(width, height);
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

  //test case to brighten all black image
  @Test
  public void testForBrightenAllBlack() {

    c = 0;
    expectedImage =new threeChannelImageBuilder(width, height);
    int increment = 10;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        expectedImage.addPixelAtPosition(j, i,
            new RGBIntegerObject(((int) Math.min(255, Math.max(0, c + increment))),
                ((int) Math.min(255, Math.max(0, c + increment))),
                (int) Math.min(255, Math.max(0, c + increment))));
      }
    }
    testImage1 = new threeChannelImageBuilder(width, height);
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

  //test case to darken all black image
  @Test
  public void testForDarkenAllBlack() {

    c = 0;
    expectedImage = new threeChannelImageBuilder(width, height);
    int increment = -10;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        expectedImage.addPixelAtPosition(j, i,
            new RGBIntegerObject(((int) Math.min(255, Math.max(0, c + increment))),
                ((int) Math.min(255, Math.max(0, c + increment))),
                (int) Math.min(255, Math.max(0, c + increment))));
      }
    }
    testImage2 = new threeChannelImageBuilder(width, height);
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

  //test case to brighten all white image
  @Test
  public void testForBrightenAllWhite() {

    c = 255;
    expectedImage = new threeChannelImageBuilder(width, height);
    int increment = 10;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        expectedImage.addPixelAtPosition(j, i,
            new RGBIntegerObject(((int) Math.min(255, Math.max(0, c + increment))),
                ((int) Math.min(255, Math.max(0, c + increment))),
                (int) Math.min(255, Math.max(0, c + increment))));
      }
    }
    testImage2 = new threeChannelImageBuilder(width, height);
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

  // test case to darken all white image
  @Test
  public void testForDarkenAllWhite() {
    c = 255;
    expectedImage = new threeChannelImageBuilder(width, height);
    int increment = -10;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        expectedImage.addPixelAtPosition(j, i,
            new RGBIntegerObject(((int) Math.min(255, Math.max(0, c + increment))),
                ((int) Math.min(255, Math.max(0, c + increment))),
                (int) Math.min(255, Math.max(0, c + increment))));
      }
    }
    testImage1 = new threeChannelImageBuilder(width, height);
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

  //test case when the rgb values are greater than 255
  @Test(expected = IllegalArgumentException.class)
  public void testForRGBValuesGreaterThan255() {

    c = 260;
    expectedImage = new threeChannelImageBuilder(width, height);
    int increment = 10;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        expectedImage.addPixelAtPosition(j, i,
            new RGBIntegerObject(((int) Math.min(255, Math.max(0, c + increment))),
                ((int) Math.min(255, Math.max(0, c + increment))),
                (int) Math.min(255, Math.max(0, c + increment))));
      }
    }
    testImage1 = new threeChannelImageBuilder(width, height);
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

  //test case when the increment is a decimal value
  @Test
  public void testForWhenIncrementisDouble() {

    c = 0;
    expectedImage = new threeChannelImageBuilder(width, height);
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


  //test case if the value works
  @Test
  public void testvisualizeValue() throws NoSuchFieldException, IllegalAccessException {
    c = 0;
    expectedImage = new threeChannelImageBuilder(width, height);
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

  //An image pixel with RGB values (200, 200, 200).o/p:200
  //test case if intensity works
  //test case if the value works
  @Test
  public void testIntensity() throws NoSuchFieldException, IllegalAccessException {
    c = 0;
    expectedImage = new threeChannelImageBuilder(width, height);
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

  //check for luma
  @Test
  public void testLuma() throws NoSuchFieldException, IllegalAccessException {
    c = 0;
    expectedImage = new threeChannelImageBuilder(width, height);
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

  //check for Red
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
    expectedImage = new threeChannelImageBuilder(width, height);
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
    model.visIndividualComponent("test+image","test-red",Component.red);
    assertTrue(
        model.getObject("test-red").equals(model.getObject("expected+imageValue")));
  }

  //check for Green
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
    expectedImage = new threeChannelImageBuilder(width, height);
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
    model.visIndividualComponent("test+image","test-green",Component.green);
    assertTrue(
        model.getObject("test-green").equals(model.getObject("expected+imageValue")));
  }

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
    expectedImage = new threeChannelImageBuilder(width, height);
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
    model.visIndividualComponent("test+image","test-blue",Component.blue);
    assertTrue(
        model.getObject("test-blue").equals(model.getObject("expected+imageValue")));
  }

  //combineGreyScaleToRGB
  @Test
  public void testCombineThreeGreyScales() {
    threeChannelImageBuilder testImageR = new threeChannelImageBuilder(width, height);

    c = 0;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        testImage1.addPixelAtPosition(j, i, new RGBIntegerObject(c, c, c));
      }
    }

    threeChannelImageBuilder testImageG = new threeChannelImageBuilder(width, height);
    c = 1;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        testImage1.addPixelAtPosition(j, i, new RGBIntegerObject(c, c, c));
      }
    }

    threeChannelImageBuilder testImageB =new threeChannelImageBuilder(width, height);
    c = 2;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        testImage1.addPixelAtPosition(j, i, new RGBIntegerObject(c, c, c));
      }
    }

    c = 0;
    expectedImage = new threeChannelImageBuilder(width, height);
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

    model.combineGreyScaleToRGB("testR", "testG","testB","RGBNewImage" );
    assertTrue(
        model.getObject("RGBNewImage").equals(model.getObject("expected+imageValue")));
  }


  //createImageThreeChannel
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
    model.splitInto3Images("RGBNewImage","testR",
        "testG","testB");
    threeChannelImageBuilder expectedtestImageR = new threeChannelImageBuilder(width, height);
    c = 0;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        expectedtestImageR.addPixelAtPosition(j, i, new RGBIntegerObject(c, c, c));
      }
    }

    threeChannelImageBuilder expectedtestImageG = new threeChannelImageBuilder(width, height);
    c = 1;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        expectedtestImageG.addPixelAtPosition(j, i, new RGBIntegerObject(c, c, c));
      }
    }

    threeChannelImageBuilder expectedtestImageB = new threeChannelImageBuilder(width, height);
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

  //horizontal
  //horizontal img - horizontal
  //horizontal img - vertical

  //vertical
  //vertical img - horizontal
  //vertical img - vertical


  @Test
  public void testThatHashMapWorks() {

    c = 0;
    expectedImage = new threeChannelImageBuilder(width, height);
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

  @Test
  public void testExceptionWhenObjectDoesNotExistInHashMap() {
    assertThrows(NoSuchElementException.class, () -> model.getObject("test+image"));
  }

  @Test
  public void testIfDefaultimageGetsCreated() {
    //default image
    threeChannelImageBuilder defaultIamge;
    defaultIamge = new threeChannelImageBuilder(width, height);
    model.addImageToModel(defaultIamge.buildImage(), "default+image");

    //image with all 0's
    c = 0;
    expectedImage = new threeChannelImageBuilder(width, height);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        expectedImage.addPixelAtPosition(j, i, new RGBIntegerObject(c, c, c));
      }
    }
    model.addImageToModel(expectedImage.buildImage(), "default+image-test");

    assertTrue(model.getObject("default+image").equals(model.getObject("default+image-test")));

  }


}