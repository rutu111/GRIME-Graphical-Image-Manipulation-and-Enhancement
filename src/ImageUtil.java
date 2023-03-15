import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;


/**
 * This class contains utility methods to read a PPM image from file and simply print its contents. Feel free to change this method
 *  as required.
 */
import java.io.PrintWriter;


/**
 * This class contains utility methods to read a PPM image from file and simply print its contents. Feel free to change this method
 *  as required.
 */
public class ImageUtil {

  /**
   * Read an image file in the PPM format and print the colors. We return ModelRGB type for now,
   * when the scope is expanded we can make the abstract class type to be returned.
   *
   * @param filename the path of the file.
   */
  public static void readPPM(Model model, String filename, String nameOfTheObject) throws FileNotFoundException {
    Scanner sc;
    sc = new Scanner(new FileInputStream(filename));

    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());
    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalStateException("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    //System.out.println("Width of image: "+width);
    int height = sc.nextInt();
    //System.out.println("Height of image: "+height);
    int maxValue = sc.nextInt();
    //System.out.println("Maximum value of a color in this file (usually 255): "+maxValue);

    threeChannelImage.threeChannelImageBuilder builderObject = model.createBuilderThreeChannel(width, height);

    int k = 0;
    //ModelRGB Image = null;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        //System.out.println("Color of pixel ("+j+","+i+"): "+ r+","+g+","+b);
        builderObject.addPixelAtPosition(j, i, new threeChannelObject(r, g, b));

      }
      model.createImageThreeChannel(builderObject, nameOfTheObject);
    }

  }


  public static void writePPM(Model model, String filePath, String imageName) throws FileNotFoundException {
    try (PrintWriter writer = new PrintWriter(filePath)) {

      TypeOfImage Image = model.getObject(imageName);

      // Write header
      writer.printf("P3" + System.lineSeparator());
      writer.printf(Image.getWidth() + " " + Image.getHeight() + System.lineSeparator());
      //writer.printf(Image.getMaxValue() + System.lineSeparator());
      int maxValue = 255;
      writer.printf(maxValue+ System.lineSeparator());

      // Write pixel data
      for (int y = 0; y < Image.getHeight(); y++) {
        for (int x = 0; x < Image.getWidth(); x++) {
          TypeofImageObject pixel = Image.getPixels()[x][y];
          writer.printf("%d", pixel.getChanne11());
          writer.println();
          writer.printf("%d", pixel.getChanne12());
          writer.println();
          writer.printf("%d", pixel.getChanne13());
          writer.println();
        }
      }
      System.out.println("Success");
    }catch(FileNotFoundException e){
      throw e;
    }
  }

}
