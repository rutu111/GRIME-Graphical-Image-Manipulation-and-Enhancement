import static org.junit.Assert.*;

import com.sun.source.tree.AssertTree;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;

public class ModelTest {

  Model model = new Model();

  threeChannelImage.threeChannelImageBuilder testImage1;

  threeChannelImage.threeChannelImageBuilder expectedImageBrightenby10;
  @Before
  public void setUp() {
    int width = 5;
    int height = 5;

    testImage1 = model.createBuilder(width, height);
    int c = 0;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        testImage1.addPixelAtPosition(j, i, new threeChannelObject(c, c, c));
        c+=1;
      }
    }

    c = 0;
    expectedImageBrightenby10 = model.createBuilder(width, height);
    int increment = 10;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        expectedImageBrightenby10.addPixelAtPosition(j, i, new threeChannelObject(c+increment, c+increment, c+increment));
        c+=1;
      }
    }
  }

  @Test
  public void testIfBrightenWorks() {
    model.createImage(testImage1.buildImage(), "test+image");
    model.createImage(expectedImageBrightenby10.buildImage(), "expected+imageBrightenBy10");
    //model.getObject("test+image");
    //model.getObject("expected+imageBrightenBy10");
    model.brighten("test+image", "test-bright", 10);
    assertFalse(model.getObject("test+image").equals(model.getObject("expected+imageBrightenBy10")));
    assertTrue(model.getObject("test-bright").equals(model.getObject("expected+imageBrightenBy10")));
  }


  @Test
  public void testThatHashMapWorks() {
    //before anything is added
    assertEquals(model.numberOfImagesInModel(), 0);
    model.createImage(testImage1.buildImage(), "test+image");
    assertEquals(model.numberOfImagesInModel(), 1);
    model.createImage(expectedImageBrightenby10.buildImage(), "expected+imageBrightenBy10");
    assertEquals(model.numberOfImagesInModel(), 2);
  }

  @Test
  public void testExceptionWhenObjectDoesNotExistInHashMap() {
    assertThrows(NoSuchElementException.class, () -> model.getObject("test+image"));
  }

}