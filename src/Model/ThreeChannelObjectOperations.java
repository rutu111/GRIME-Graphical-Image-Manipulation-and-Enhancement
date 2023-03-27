package Model;

import static java.lang.Math.min;

import java.awt.Component;
import java.lang.reflect.Field;

/**
 * The following class contains implements that are common for all 3 channel objects.
 */
public abstract class ThreeChannelObjectOperations extends CommonOperations {

  /**
   * Constructor represents a TypeOfImage containing TypeofImageObject objects.
   *
   * @param pixels matrix of TypeofImageObject.
   * @param width  the width of the matrix.
   * @param height the height of the matrix.
   */
  protected ThreeChannelObjectOperations(TypeofImageObject[][] pixels, int width, int height) {
    super(pixels, width, height);
  }


  @Override
  public TypeOfImage combineGreyScaleToRGB(TypeOfImage image2,
      TypeOfImage image3) throws IllegalArgumentException {
    TypeofImageObject[][] new_image;
    //check if height and width of all images is the same
    //if yes, set height and width of RBG the same
    //if no, throw exception
    if (this.getPixels().length == image2.getPixels().length
        & image2.getPixels().length == image3.getPixels().length
        & this.getPixels().length == image3.getPixels().length &
        this.getPixels()[0].length == image2.getPixels()[0].length
        & image2.getPixels()[0].length == image3.getPixels()[0].length
        & this.getPixels()[0].length == image3.getPixels()[0].length) {
      new_image = getMatrix(this.getWidth(), this.getHeight());
      if (this.getPixels()[0][0].equals(null) & image2.getPixels()[0][0].equals(null)
          & image3.getPixels()[0][0].equals(null)) {
        for (int i = 0; i < this.getWidth(); i++) {
          for (int j = 0; j < this.getHeight(); j++) {
            new_image[i][j] = getObject(this.getPixels()[i][j].getChanne11(),
                image2.getPixels()[i][j].getChanne12(),
                image3.getPixels()[i][j].getChanne13(),
                (this.getPixels()[i][j].hasAlpha() + image2.getPixels()[i][j].hasAlpha()
                    + image3.getPixels()[i][j].hasAlpha()) / 3);
          }
        }
      } else {
        for (int i = 0; i < this.getWidth(); i++) {
          for (int j = 0; j < this.getHeight(); j++) {
            new_image[i][j] = getObject(this.getPixels()[i][j].getChanne11(),
                image2.getPixels()[i][j].getChanne12(),
                image3.getPixels()[i][j].getChanne13(), this.getPixels()[i][j].hasAlpha());
          }
        }
      }
        } else {
          throw new IllegalArgumentException("All images must be of the same size!");
        }
        return getOImage(new_image, this.getWidth(), this.getHeight());
      }



  @Override
  public TypeOfImage brighten(double increment) {
    TypeofImageObject[][] newPixels = getMatrix(this.width, this.height);
    for (int i = 0; i < this.width; i++) {
      for (int j = 0; j < this.height; j++) {
        TypeofImageObject oldPixel = this.pixels[i][j];
        //Add increment value to RGB values and clamp to [0, 255]
        Integer r = (int) min(255, Math.max(0, oldPixel.getChanne11() + increment));
        Integer g = (int) min(255, Math.max(0, oldPixel.getChanne12() + increment));
        Integer b = (int) min(255, Math.max(0, oldPixel.getChanne13() + increment));
        newPixels[i][j] = getObject(r, g, b, oldPixel.hasAlpha());
      }
    }
    return getOImage(newPixels, this.width, this.height);
  }

  @Override
  public TypeOfImage visIndividualComponent(ComponentRGB channel)
      throws NoSuchFieldException, IllegalAccessException {
    Field field = getField(channel);
    TypeofImageObject[][] new_image = getMatrix(this.width, this.height);
    for (int i = 0; i < this.getWidth(); i++) {
      for (int j = 0; j < this.getHeight(); j++) {
        TypeofImageObject object = this.getPixels()[i][j];
        new_image[i][j] = getObject((Integer) field.get(object),
            (Integer) field.get(object), (Integer) field.get(object), object.hasAlpha());
      }
    }
    return getOImage(new_image, this.getWidth(), this.getHeight());
  }

  protected abstract Field getField(ComponentRGB channel) throws NoSuchFieldException;

  @Override
  public TypeOfImage visualizeValueIntensityLuma(MeasurementType measure)
      throws NoSuchFieldException, IllegalAccessException {
    TypeofImageObject[][] new_image = getMatrix(this.width, this.height);
    for (int i = 0; i < this.getWidth(); i++) {
      for (int j = 0; j < this.getHeight(); j++) {
        if (measure.toString().equals("value")) {
          Integer value = Math.max(this.getPixels()[i][j].getChanne11(),
              Math.max(this.getPixels()[i][j].getChanne12(),
                  this.getPixels()[i][j].getChanne13()));
          new_image[i][j] = getObject(value, value, value, this.getPixels()[i][j].hasAlpha());
        }
        //intensity
        if (measure.toString().equals("intensity")) {
          Integer intensity =
              (this.getPixels()[i][j].getChanne11() + this.getPixels()[i][j].getChanne12()
                  + this.getPixels()[i][j].getChanne13()) / 3;
          new_image[i][j] = getObject(intensity, intensity, intensity,
              this.getPixels()[i][j].hasAlpha());
        }
        //luma
        if (measure.toString().equals("luma")) {
          double luma = 0.2126 * this.getPixels()[i][j].getChanne11()
              + 0.7152 * this.getPixels()[i][j].getChanne12() +
              0.0722 * this.getPixels()[i][j].getChanne13();
          new_image[i][j] = getObject((int) luma, (int) luma, (int) luma,
              this.getPixels()[i][j].hasAlpha());
        }
      }
    }
    return getOImage(new_image, this.getWidth(), this.getHeight());
  }

  @Override
  public TypeOfImage colorTransformationSepia() {
    //sepia matrix
    double[][] sepia = {{0.393, 0.769, 0.189}, {0.349, 0.686, 0.168}, {0.272, 0.534, 0.131}};
    TypeofImageObject[][] new_image = getMatrix(this.width, this.height);

    for (int i = 0; i < this.getWidth(); i++) {
      for (int j = 0; j < this.getHeight(); j++) {
        //get each pixel (3x1 matrix).
        double[][] result = new double[3][1];
        int[][] pixelValues = new int[3][1];
        pixelValues[0][0] = this.getPixels()[i][j].getChanne11();
        pixelValues[1][0] = this.getPixels()[i][j].getChanne12();
        pixelValues[2][0] = this.getPixels()[i][j].getChanne13();
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
        new_image[i][j] = getObject(r, g, b, this.getPixels()[i][j].hasAlpha());
      }
    }
    return getOImage(new_image, this.getWidth(), this.getHeight());
  }




  /**
   * Abstract method returns an object of the class extending it.
   *
   * @param value1 value of the first channel, of type T
   * @param value2 value of the second channel, of type T
   * @param value3 value of the third channel, of type T
   * @return
   */
  protected abstract TypeofImageObject getObject(int value1, int value2, int value3, Integer value4);

  public TypeOfImage blur() {
    TypeofImageObject[][] newPixels = getMatrix(this.width, this.height);

    // Create a 3x3 Gaussian filter
    double[][] filter = {
        {1.0/16.0, 1.0/8.0, 1.0/16.0},
        {1.0/8.0, 1.0/4.0, 1.0/8.0},
        {1.0/16.0, 1.0/8.0, 1.0/16.0}
    };

    // Iterate over each pixel in the image
    for (int x = 0; x < this.width; x++) {
      for (int y = 0; y < this.height; y++) {
        // Apply the Gaussian filter to the pixel and its neighbors
        double r = 0, g = 0, b = 0;
        TypeofImageObject oldPixel = null;
        for (int i = -1; i <= 1; i++) {
          for (int j = -1; j <= 1; j++) {
            int xx = x + i;
            int yy = y + j;
            if (xx >= 0 && xx < this.width && yy >= 0 && yy < this.height) {
              oldPixel = this.pixels[xx][yy];
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
        newPixels[x][y] = getObject(r2, g2, b2, oldPixel.hasAlpha());
      }
    }

    return getOImage(newPixels, this.getWidth(), this.getHeight());
  }


  public TypeOfImage sharpen(){
    TypeofImageObject[][] newPixels = getMatrix(this.width, this.height);

    // Create a 3x3 Laplacian filter
    double[][] filter = {
        {-1.0/8.0, -1.0/8.0, -1.0/8.0, -1.0/8.0, -1.0/8.0},
        {-1.0/8.0, 1.0/4.0, 1.0/4.0, 1.0/4.0, -1.0/8.0},
        {-1.0/8.0, 1.0/4.0, 1.0, 1.0/4.0, -1.0/8.0},
        {-1.0/8.0, 1.0/4.0, 1.0/4.0, 1.0/4.0, -1.0/8.0},
        {-1.0/8.0, -1.0/8.0, -1.0/8.0, -1.0/8.0, -1.0/8.0}
    };

    // Iterate over each pixel in the image
    for (int x = 0; x < this.width; x++) {
      for (int y = 0; y < this.height; y++) {
        // Apply the Laplacian filter to the pixel and its neighbors
        double r = 0, g = 0, b = 0;
        TypeofImageObject oldPixel = null;
        for (int i = -1; i <= 1; i++) {
          for (int j = -1; j <= 1; j++) {
            int xx = x + i;
            int yy = y + j;
            if (xx >= 0 && xx < this.width && yy >= 0 && yy < this.height) {
              oldPixel = this.pixels[xx][yy];
              double weight = filter[i + 1][j + 1];
              r += weight * oldPixel.getChanne11();
              g += weight * oldPixel.getChanne12();
              b += weight * oldPixel.getChanne13();
            }
          }
        }

        // Add the amount parameter to the filtered values and clamp to [0, 255]
        Integer r2 = (int) Math.min(255, Math.max(0, r));
        Integer g2 = (int) Math.min(255, Math.max(0, g));
        Integer b2 = (int) Math.min(255, Math.max(0, b));

        // Set the value of the corresponding pixel in the new image object
        newPixels[x][y] = getObject(r2, g2, b2, oldPixel.hasAlpha());
      }
    }

    return getOImage(newPixels, this.getWidth(), this.getHeight());
  }

  public TypeOfImage dither(ComponentRGB channel) throws
      NoSuchFieldException, IllegalAccessException {
    double[][] errors = new double[this.width][this.height];
    Field field = getField(channel);
    TypeofImageObject[][] newPixels = getMatrix(this.width, this.height);
    for (int c = 0; c < this.getWidth(); c++) {
      for (int r = 0; r < this.getHeight(); r++) {
        TypeofImageObject oldPixel = this.getPixels()[c][r];
        Integer oldColor = (Integer) field.get(oldPixel);
        int newColor = (oldColor > 127) ? 255 : 0;
        newPixels[c][r] = getObject(newColor,newColor,newColor, oldPixel.hasAlpha());
        int error = oldColor - newColor;
        if (c < this.width - 1) {
          // Add to pixel on the right
          errors[c+1][r] += (7.0 / 16.0) * error;
        }
        if (r < this.height - 1 && c > 0) {
          // Add to pixel on the next-row-left
          errors[c - 1][r + 1] += (3.0 / 16.0) * error;
        }
        if (r < this.height - 1) {
          // Add to pixel below in next row
          errors[c][r+1] += (5.0 / 16.0) * error;
        }
        if (r < this.height - 1 && c < this.width - 1) {
          // Add to pixel on the next-row-right
          errors[c + 1][r + 1] += (1.0 / 16.0) * error;
        }
      }
    }
    // propagate error to neighboring pixels
    for (int c = 0; c < this.width; c++) {
      for (int r = 0; r < this.height; r++) {
        TypeofImageObject newPixel = newPixels[c][r];
        int channelRGB = (Integer) field.get(newPixel);

        // add error to neighboring pixels
        channelRGB += errors[c][r];
        // truncate pixel color values to [0, 255]
        channelRGB = Math.min(Math.max(channelRGB, 0), 255);
        // update pixel color
        newPixels[c][r] = getObject(channelRGB, channelRGB, channelRGB, newPixel.hasAlpha());
      }
    }


    return getOImage(newPixels, this.getWidth(), this.getHeight());
  }


}



