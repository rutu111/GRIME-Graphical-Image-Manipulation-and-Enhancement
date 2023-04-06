package controller;

import model.Operations;
import model.RGBIntegerAlphaImage.RGBIntegerAlphaImageBuilder;
import model.RGBIntegerAlphaObject;
import model.RGBIntegerImage.RGBIntegerImageBuilder;
import model.RGBIntegerObject;
import model.TypeOfImage;
import model.TypeofImageObject;

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
import java.util.NoSuchElementException;
import java.util.Scanner;
import javax.imageio.ImageIO;


/**
 * This class contains utility methods to read a PPM image from
 * file and simply print its contents.
 * Feel free to change this method as required.
 */
public class ImageUtil {

  /**
   * Read an image file in the PPM format and print the colors.
   * We return ModelRGB type for now,
   * when the scope is expanded we can make the abstract class
   * type to be returned.
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
      throw new IllegalStateException("Invalid file: Cannot be empty");
    }
    String token;

    token = sc.next();
    if (!token.equals("P3") || token.isEmpty()) {
      throw new IllegalStateException(
          "Invalid PPM file: plain RAW file should begin with P3");
    }
    try {
      width = sc.nextInt();
      height = sc.nextInt();
      maxValue = sc.nextInt();

      if (width <= 0 | height <= 0) {
        throw new IllegalStateException("Width and Height cannot be zero or negative");
      }

      if (maxValue != 255) {
        throw new IllegalStateException("Invalid maxValue: " + maxValue);
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
      throws FileNotFoundException, IllegalArgumentException {

    try (PrintWriter writer = new PrintWriter(filePath)) {

      TypeOfImage image = model.getObject(imageName);

      // Write header
      writer.printf("P3" + System.lineSeparator());
      writer.printf(image.getWidth() + " " + image.getHeight() + System.lineSeparator());
      int maxValue = 255;
      writer.printf(maxValue + System.lineSeparator());

      // Write pixel data
      for (int y = 0; y < image.getHeight(); y++) {
        for (int x = 0; x < image.getWidth(); x++) {
          TypeofImageObject pixel = image.getPixels()[x][y];
          if (pixel != null) {
            writer.printf("%d", pixel.getChanne11());
            writer.println();
            writer.printf("%d", pixel.getChanne12());
            writer.println();
            writer.printf("%d", pixel.getChanne13());
            writer.println();
          }
        }
      }
    } catch (FileNotFoundException e) {
      throw e;
    } catch (IllegalArgumentException e) {
      throw e;
    }
  }

  /**
   * The following function is used to read an image for formats other
   * than PPM. Example: png, jpg
   * and bmp.
   *
   * @param model           model instance.
   * @param filename        name of the file to read the image from.
   * @param nameOfTheObject name to call the image read.
   * @throws IOException if IO exception occurs.
   */
  public static void imageIORead(Operations model, String filename, String nameOfTheObject)
      throws IOException {

    BufferedImage image = null;
    try {
      File file = new File(filename);
      if (file.exists() && file.length() == 0) {
        throw new IllegalArgumentException(" Cannot save an empty file");
      }
      //1. First check if the image needs to be converted to RGB
      image = ImageIO.read(file);
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("Error: File not found");
    }

    int width = image.getWidth();
    int height = image.getHeight();

    ColorSpace colorSpace = image.getColorModel().getColorSpace();
    //if colour palette is CMYK then convert it to rgb
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
    //if image has alpha channel, then read it using the RGBIntegerAlphaImageBuilder.
    if (colorModel.hasAlpha()) {
      RGBIntegerAlphaImageBuilder builderObject = new RGBIntegerAlphaImageBuilder(width, height);

      if (image.getType() == BufferedImage.TYPE_BYTE_GRAY) {
        //if has alpha channel and is a greyscale image.
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
        //if has alpha channel and is a not greyscale image.
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
      // if image does not have an alpha channel, then read it using the RGBIntegerImageBuilder.
      RGBIntegerImageBuilder builderObject = new RGBIntegerImageBuilder(width, height);
      if (image.getType() == BufferedImage.TYPE_BYTE_GRAY) {
        //if does not have alpha channel and is greyscale.
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
        //if does not have alpha channel and is not greyscale.
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

  /**
   * The following code is used to write an image file for formats
   * that are not PPM. Example: PNG,
   * jpg and bmp.
   *
   * @param model     model instance.
   * @param filePath  path where you want to save the image.
   * @param imageName name of the image you want to write.
   * @throws IOException if IOexception occurs..
   */
  public static void imgeIOWrite(Operations model, String filePath, String imageName)
      throws IOException {

    TypeOfImage imageOutput;

    try {
      imageOutput = model.getObject(imageName);
    } catch (NoSuchElementException e) {
      throw e;
    }

    try {
      int width = imageOutput.getWidth();
      int height = imageOutput.getHeight();
      BufferedImage image;
      String fileTypeStore = filePath.split("\\.")[1];
      if (imageOutput.getPixels()[0][0].hasAlpha() != null & fileTypeStore.equals("ppm")
          | imageOutput.getPixels()[0][0].hasAlpha() != null & fileTypeStore.equals("bmp")
          | imageOutput.getPixels()[0][0].hasAlpha() != null & fileTypeStore.equals("jpg")
          | imageOutput.getPixels()[0][0].hasAlpha() != null & fileTypeStore.equals("jpeg")
          | imageOutput.getPixels()[0][0].hasAlpha() == null) {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < imageOutput.getHeight(); y++) {
          for (int x = 0; x < imageOutput.getWidth(); x++) {
            TypeofImageObject pixel = imageOutput.getPixels()[x][y];
            if (pixel != null) {
              int red = pixel.getChanne11();
              int green = pixel.getChanne12();
              int blue = pixel.getChanne13();
              int rgb = (red << 16) | (green << 8) | blue;

              image.setRGB(x, y, rgb);
            }
          }
        }
      } else {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        //saving for image with alpha channel
        for (int y = 0; y < imageOutput.getHeight(); y++) {
          for (int x = 0; x < imageOutput.getWidth(); x++) {
            TypeofImageObject pixel = imageOutput.getPixels()[x][y];
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
      }

      File output = new File(filePath);
      String fileType = filePath.split("\\.")[1];
      ImageIO.write(image, fileType, output);

    } catch (Exception e) {
      throw e;
    }

  }
}
