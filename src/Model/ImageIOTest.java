package Model;

import java.awt.color.ColorSpace;
import java.awt.color.ICC_ColorSpace;
import java.awt.color.ICC_Profile;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageIOTest {


  // check the number of channels (alpha or not)
  //check if greyscale -> create rgb
  //check colour pallet -> rgb
  public static void main(String[] args) throws IOException {
    BufferedImage image = null;
    File file = new File("bmp.bmp");

    //1. First check if the image needs to be converted to RGB
    image = ImageIO.read(file);
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
      if (image.getType() == BufferedImage.TYPE_BYTE_GRAY) {
        for (int y = 0; y < image.getHeight(); y++) {
          for (int x = 0; x < image.getWidth(); x++) {
            int pixel = image.getRGB(x, y);
            int alpha = (pixel >> 24) & 0xff; // extract alpha value
            int red = (pixel >> 8) & 0xff;
            int green = (pixel >> 8) & 0xff;
            int blue = (pixel >> 8) & 0xff;
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
          }
        }
      }
      ;
    } else {
      //RGBInt builder here
      if (image.getType() == BufferedImage.TYPE_BYTE_GRAY) {
        for (int y = 0; y < image.getHeight(); y++) {
          for (int x = 0; x < image.getWidth(); x++) {
            int pixel = image.getRGB(x, y);
            int red = (pixel & 0xff);
            int green = (pixel & 0xff);
            int blue = (pixel & 0xff);
          }
        }
      } else {
        for (int y = 0; y < image.getHeight(); y++) {
          for (int x = 0; x < image.getWidth(); x++) {
            int pixel = image.getRGB(x, y);
            int red = (pixel >> 16) & 0xff;
            int green = (pixel >> 8) & 0xff;
            int blue = pixel & 0xff;
          }
        }
      }

    }
  }

}

/*
 ImageInputStream iis = ImageIO.createImageInputStream(file);
    Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
    if (readers.hasNext()) {
      ImageReader reader = readers.next();
      String formatName = reader.getFormatName();
      if (formatName.equalsIgnoreCase("bmp") | formatName.equalsIgnoreCase("png")) {
        image = ImageIO.read(file);
 */


/*
int width = matrix[0].length;
  int height = matrix.length;
  BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

  for (int y = 0; y < height; y++) {
    for (int x = 0; x < width; x++) {
    int pixelValue = matrix[y][x];
    image.setRGB(x, y, pixelValue);
    }
    }


    File output = new File("output.png");
    ImageIO.write(image, "png", output);

    */
