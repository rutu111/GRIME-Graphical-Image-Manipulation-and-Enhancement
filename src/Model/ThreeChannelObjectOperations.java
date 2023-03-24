package Model;

import static java.lang.Math.min;

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
    Field field = RGBIntegerObject.class.getDeclaredField(channel.toString());
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
        new_image[i][j] = getObject((int) result[0][0], (int) result[1][0], (int) result[2][0],
            this.getPixels()[i][j].hasAlpha());
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
    int[][] filter = {
        {1/16, 1/8, 1/16},
        {1/8, 1/4, 1/8},
        {1/16, 1/8, 1/16}
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

        // Set the value of the corresponding pixel in the new image object
        newPixels[x][y] = getObject((int) r, (int) g, (int) b, oldPixel.hasAlpha());
      }
    }

    return getOImage(newPixels, this.getWidth(), this.getHeight());
  }


  public TypeOfImage sharpen(){
    TypeofImageObject[][] newPixels = getMatrix(this.width, this.height);

    // Create a 3x3 Laplacian filter
    int[][] filter = {
        {-1/8, -1/8, -1/8, -1/8, -1/8},
        {-1/8, 1/4, 1/4, 1/4, -1/8},
        {-1/8, 1/4, 1, 1/4, -1/8},
        {-1/8, 1/4, 1/4, 1/4, -1/8},
        {-1/8, -1/8, -1/8, -1/8, -1/8}
    };

    // Iterate over each pixel in the image
    for (int x = 0; x < this.width; x++) {
      for (int y = 0; y < this.height; y++) {
        // Apply the Laplacian filter to the pixel and its neighbors
        double r = 0, g = 0, b = 0;
        TypeofImageObject oldPixel = null;
        for (int i = 0; i <= 2; i++) {
          for (int j = 0; j <= 2; j++) {
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

  public TypeOfImage dither() {
    TypeofImageObject[][] newPixels = getMatrix(this.width, this.height);
    double[][] errors = new double[this.width][this.height];

    for (int y = 0; y < this.height; y++) {
      for (int x = 0; x < this.width; x++) {
        TypeofImageObject oldPixel = this.pixels[x][y];


        // Compute the error for each channel
        double oldChannelValue_1 = oldPixel.getChanne11();
        double newChannelValue_1 = Math.floor(oldChannelValue_1 * 256.0 / 255.0);
        double quantizationError_1 = oldChannelValue_1 - newChannelValue_1 * 255.0 / 256.0;
        errors[x][y] += quantizationError_1 * quantizationError_1;
        double newValues_1 = newChannelValue_1 * 255.0 / 256.0;

        // Compute the error for each channel
        double oldChannelValue_2 = oldPixel.getChanne12();
        double newChannelValue_2 = Math.floor(oldChannelValue_2 * 256.0 / 255.0);
        double quantizationError_2 = oldChannelValue_2 - newChannelValue_2 * 255.0 / 256.0;
        errors[x][y] += quantizationError_2 * quantizationError_2;
        double newValues_2 = newChannelValue_2 * 255.0 / 256.0;

        // Compute the error for each channel
        double oldChannelValue_3 = oldPixel.getChanne13();
        double newChannelValue_3 = Math.floor(oldChannelValue_3 * 256.0 / 255.0);
        double quantizationError_3 = oldChannelValue_3 - newChannelValue_3 * 255.0 / 256.0;
        errors[x][y] += quantizationError_3 * quantizationError_3;
        double newValues_3 = newChannelValue_1 * 255.0 / 256.0;


        // Set the value of the corresponding pixel in the new image object
        newPixels[x][y] = getObject((int) newValues_1, (int) newValues_2, (int) newValues_3,
            oldPixel.hasAlpha());

        // Propagate the error to neighboring pixels
        if (x < this.width - 1) {
          errors[x + 1][y] += 7.0 / 16.0 * errors[x][y];
          if (y < this.height - 1) {
            errors[x + 1][y + 1] += 1.0 / 16.0 * errors[x][y];
          }
        }
        if (y < this.height - 1) {
          errors[x][y + 1] += 5.0 / 16.0 * errors[x][y];
          if (x > 0) {
            errors[x - 1][y + 1] += 3.0 / 16.0 * errors[x][y];
          }
        }
      }
    }

    return getOImage(newPixels, this.getWidth(), this.getHeight());
  }







}



