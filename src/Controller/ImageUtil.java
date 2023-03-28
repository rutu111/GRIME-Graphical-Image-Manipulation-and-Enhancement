package Controller;

import Model.Operations;
import Model.RGBIntegerAlphaImage.RGBIntegerAlphaImageBuilder;
import Model.RGBIntegerAlphaObject;
import Model.RGBIntegerImage.RGBIntegerImageBuilder;
import Model.RGBIntegerObject;
import Model.TypeOfImage;
import Model.TypeofImageObject;
import java.awt.color.ColorSpace;
import java.awt.color.ICC_ColorSpace;
import java.awt.color.ICC_Profile;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.imageio.ImageIO;


/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 * Feel free to change this method as required.
 */
public class ImageUtil {

  /**
   * Read an image file in the PPM format and print the colors. We return ModelRGB type for now,
   * when the scope is expanded we can make the abstract class type to be returned.
   *
   * @param filename the path of the file.
   */
  public static void readPPM(Operations model, String filename, String nameOfTheObject)
      throws FileNotFoundException {
    Scanner sc;
    sc = new Scanner(new FileInputStream(filename));

    int width;
    int height;
    int maxValue;

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
    if (!sc.hasNextLine()) {
      throw new IllegalStateException("Error: Invalid file: Cannot be empty");
    }
    String token;

    token = sc.next();
    if (!token.equals("P3") || token.isEmpty()) {
      throw new IllegalStateException(
          "Error: Invalid PPM file: plain RAW file should begin with P3");
    }
    try {
      width = sc.nextInt();
      height = sc.nextInt();
      maxValue = sc.nextInt();

      if (width <= 0 | height <= 0) {
        throw new IllegalStateException("Error: Width and Height cannot be zero or negative");
      }

      if (maxValue != 255) {
        throw new IllegalStateException("Error: Invalid maxValue: " + maxValue);
      }

      RGBIntegerImageBuilder builderObject = new RGBIntegerImageBuilder(width, height);

      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          int r = sc.nextInt();
          int g = sc.nextInt();
          int b = sc.nextInt();
          builderObject.addPixelAtPosition(j, i, new RGBIntegerObject(r, g, b));

        }
      }

      TypeOfImage image = builderObject.buildImage();
      model.addImageToModel(image, nameOfTheObject);
    } catch (Exception e) {
      throw e;
    }

  }


  /**
   * This method creates a new PPM file on save.
   *
   * @param model     model instance.
   * @param filePath  path to save the file.
   * @param imageName save of the image to save.
   * @throws FileNotFoundException if image object does not exist.
   */
  public static void writePPM(Operations model, String filePath, String imageName)
      throws FileNotFoundException {
    File file = new File(filePath);
    if (file.exists() && file.length() == 0) {
      throw new IllegalArgumentException("Error: Cannot save an empty file");
    }
    try (PrintWriter writer = new PrintWriter(filePath)) {

      TypeOfImage Image = model.getObject(imageName);

      // Write header
      writer.printf("P3" + System.lineSeparator());
      writer.printf(Image.getWidth() + " " + Image.getHeight() + System.lineSeparator());
      int maxValue = 255;
      writer.printf(maxValue + System.lineSeparator());

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
    } catch (FileNotFoundException e) {
      throw e;
    }
  }

  public static void imageIORead(Operations model, String filename, String nameOfTheObject)
      throws IOException {

    BufferedImage image = null;
    try {
      File file = new File(filename);
      if (file.exists() && file.length() == 0) {
        throw new IllegalArgumentException("Error: Cannot save an empty file");
      }
      //1. First check if the image needs to be converted to RGB
      image = ImageIO.read(file);
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("Error: File not found");
    }

    int width = image.getWidth();
    int height = image.getHeight();

    ColorSpace colorSpace = image.getColorModel().getColorSpace();
    if (colorSpace.getType() == ColorSpace.TYPE_CMYK) {
      try {
        // Get the ICC profile of the CMYK image
        ICC_Profile cmykProfile = ICC_Profile.getInstance(String.valueOf(image));

        // Create an ICC_ColorSpace object from the ICC profile
        ICC_ColorSpace cmykColorSpace = new ICC_ColorSpace(cmykProfile);

        // Create an RGB ColorSpace
        ColorSpace rgbColorSpace = ColorSpace.getInstance(ColorSpace.CS_sRGB);

        // Create a ColorConvertOp object for converting from CMYK to RGB
        ColorConvertOp cmykToRgb = new ColorConvertOp(cmykColorSpace, rgbColorSpace, null);

        // Convert the CMYK image to RGB
        image = cmykToRgb.filter(image, null);

      } catch (IOException e) {
        System.out.println("Error converting image: " + e.getMessage());
      }
    }

    ColorModel colorModel = image.getColorModel();
    //alpha builder here
    if (colorModel.hasAlpha()) {
      RGBIntegerAlphaImageBuilder builderObject = new RGBIntegerAlphaImageBuilder(width, height);

      if (image.getType() == BufferedImage.TYPE_BYTE_GRAY) {
        for (int y = 0; y < image.getHeight(); y++) {
          for (int x = 0; x < image.getWidth(); x++) {
            int pixel = image.getRGB(x, y);
            int alpha = (pixel >> 24) & 0xff; // extract alpha value
            int red = (pixel >> 8) & 0xff;
            int green = (pixel >> 8) & 0xff;
            int blue = (pixel >> 8) & 0xff;
            builderObject.addPixelAtPosition(x, y,
                new RGBIntegerAlphaObject(red, green, blue, alpha));

          }
        }
      } else {
        for (int y = 0; y < image.getHeight(); y++) {
          for (int x = 0; x < image.getWidth(); x++) {
            int pixel = image.getRGB(x, y);
            int alpha = (pixel >> 24) & 0xff;
            int red = (pixel >> 16) & 0xff;
            int green = (pixel >> 8) & 0xff;
            int blue = pixel & 0xff;
            builderObject.addPixelAtPosition(x, y,
                new RGBIntegerAlphaObject(red, green, blue, alpha));

          }
        }
      }
      TypeOfImage imageType = builderObject.buildImage();
      model.addImageToModel(imageType, nameOfTheObject);
    } else {
      RGBIntegerImageBuilder builderObject = new RGBIntegerImageBuilder(width, height);
      if (image.getType() == BufferedImage.TYPE_BYTE_GRAY) {
        for (int y = 0; y < image.getHeight(); y++) {
          for (int x = 0; x < image.getWidth(); x++) {
            int pixel = image.getRGB(x, y);
            int red = (pixel & 0xff);
            int green = (pixel & 0xff);
            int blue = (pixel & 0xff);
            builderObject.addPixelAtPosition(x, y, new RGBIntegerObject(red, green, blue));
          }
        }
      } else {
        for (int y = 0; y < image.getHeight(); y++) {
          for (int x = 0; x < image.getWidth(); x++) {
            int pixel = image.getRGB(x, y);
            int red = (pixel >> 16) & 0xff;
            int green = (pixel >> 8) & 0xff;
            int blue = pixel & 0xff;
            builderObject.addPixelAtPosition(x, y, new RGBIntegerObject(red, green, blue));
          }
        }
      }
      TypeOfImage imageType = builderObject.buildImage();
      model.addImageToModel(imageType, nameOfTheObject);
    }
  }

  public static void imgeIOWrite(Operations model, String filePath, String imageName)
      throws IOException {

    TypeOfImage ImageOutput = model.getObject(imageName);
    int width = ImageOutput.getWidth();
    int height = ImageOutput.getHeight();
    BufferedImage image;

    if (ImageOutput.getPixels()[0][0].hasAlpha() != null) {
      image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      for (int y = 0; y < ImageOutput.getHeight(); y++) {
        for (int x = 0; x < ImageOutput.getWidth(); x++) {
          TypeofImageObject pixel = ImageOutput.getPixels()[x][y];
          if (pixel != null) {
            int alpha = 255; // set alpha to 255 (fully opaque)
            int red = pixel.getChanne11();
            int green = pixel.getChanne12();
            int blue = pixel.getChanne13();

            int argb = (alpha << 24) | (red << 16) | (green << 8) | blue;
            image.setRGB(x, y, argb);
          }
        }
      }
    } else {
      image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      for (int y = 0; y < ImageOutput.getHeight(); y++) {
        for (int x = 0; x < ImageOutput.getWidth(); x++) {
          TypeofImageObject pixel = ImageOutput.getPixels()[x][y];
          if (pixel != null) {
            int red = pixel.getChanne11();
            int green = pixel.getChanne12();
            int blue = pixel.getChanne13();
            int rgb = (red << 16) | (green << 8) | blue;

            image.setRGB(x, y, rgb);
          }
        }
      }
    }

    File output = new File(filePath);
    String fileType = filePath.split("\\.")[1];
    if (output.exists() && output.length() == 0) {
      throw new IllegalArgumentException("Error: Cannot save an empty file");
    } else {
      ImageIO.write(image, fileType, output);
    }
  }


}
