import static org.junit.Assert.*;

import com.sun.source.tree.AssertTree;
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

    testImage1 = model.createBuilder(width, height);
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
    expectedImage = model.createBuilder(width, height);
    int increment = 10;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        expectedImage.addPixelAtPosition(j, i, new threeChannelObject(c+increment, c+increment, c+increment));
        c+=1;
      }
    }
    //before brighterning
    model.createImage(testImage1.buildImage(), "test+image");
    model.createImage(expectedImage.buildImage(), "expected+imageBrightenBy10");
    assertFalse(model.getObject("test+image").equals(model.getObject("expected+imageBrightenBy10")));

    //after brighterning
    model.brighten("test+image", "test-bright", 10);
    assertTrue(model.getObject("test-bright").equals(model.getObject("expected+imageBrightenBy10")));
  }


  @Test
  public void testThatHashMapWorks() {

    c = 0;
    expectedImage = model.createBuilder(width, height);
    int increment = 10;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        expectedImage.addPixelAtPosition(j, i, new threeChannelObject(c+increment, c+increment, c+increment));
      }
    }

    //before anything is added
    assertEquals(model.numberOfImagesInModel(), 0);
    model.createImage(testImage1.buildImage(), "test+image");
    assertEquals(model.numberOfImagesInModel(), 1);
    model.createImage(expectedImage.buildImage(), "expected+imageBrightenBy10");
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
      defaultIamge = model.createBuilder(width, height);
    model.createImage(defaultIamge.buildImage(), "test+image");

    //image with all 0's
      c = 0;
    expectedImage = model.createBuilder(width, height);
      int increment = 10;
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          expectedImage.addPixelAtPosition(j, i, new threeChannelObject(c, c, c));
        }
      }
    }


}