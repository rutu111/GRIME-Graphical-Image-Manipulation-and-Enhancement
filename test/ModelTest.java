import static org.junit.Assert.*;

import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;

public class ModelTest {

  Model model = new Model();

  threeChannelImage.threeChannelImageBuilder testImage1;

  threeChannelImage.threeChannelImageBuilder expectedImage;

  int width;
  int height;
  int c;


  @Before
  public void setUp() {
    width = 5;
    height = 5;

    testImage1 = model.createBuilderThreeChannel(width, height);
    c = 0;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        testImage1.addPixelAtPosition(j, i, new threeChannelObject(c, c, c));
        c += 1;
      }
    }
  }

  @Test
  public void testIfBrightenWorks() {

    c = 0;
    expectedImage = model.createBuilderThreeChannel(width, height);
    int increment = 10;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        expectedImage.addPixelAtPosition(j, i, new threeChannelObject(c+increment, c+increment, c+increment));
        c+=1;
      }
    }
    //before brighterning
    model.createImageThreeChannel(testImage1, "test+image");
    model.createImageThreeChannel(expectedImage, "expected+imageBrightenBy10");
    assertFalse(model.getObject("test+image").equals(model.getObject("expected+imageBrightenBy10")));

    //after brighterning
    model.brighten("test+image", "test-bright", 10);
    assertTrue(model.getObject("test-bright").equals(model.getObject("expected+imageBrightenBy10")));
  }


  @Test
  public void testThatHashMapWorks() {

    c = 0;
    expectedImage = model.createBuilderThreeChannel(width, height);
    int increment = 10;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        expectedImage.addPixelAtPosition(j, i, new threeChannelObject(c+increment, c+increment, c+increment));
        c+=1;
      }
    }

    //before anything is added
    assertEquals(model.numberOfImagesInModel(), 0);
    model.createImageThreeChannel(testImage1, "test+image");
    assertEquals(model.numberOfImagesInModel(), 1);
    model.createImageThreeChannel(expectedImage, "expected+imageBrightenBy10");
    assertEquals(model.numberOfImagesInModel(), 2);
  }

  @Test
  public void testExceptionWhenObjectDoesNotExistInHashMap() {
    assertThrows(NoSuchElementException.class, () -> model.getObject("test+image"));
  }

  @Test
  public void testIfDefaultimageGetsCreated() {
      //default image
    threeChannelImage.threeChannelImageBuilder defaultIamge;
      defaultIamge = model.createBuilderThreeChannel(width, height);
    model.createImageThreeChannel(defaultIamge, "default+image");

    //image with all 0's
      c = 0;
    expectedImage = model.createBuilderThreeChannel(width, height);
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          expectedImage.addPixelAtPosition(j, i, new threeChannelObject(c, c, c));
        }
      }
    model.createImageThreeChannel(expectedImage, "default+image-test");

    assertTrue(model.getObject("default+image").equals(model.getObject("default+image-test")));

  }


}