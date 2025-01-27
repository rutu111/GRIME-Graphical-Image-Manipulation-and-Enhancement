package model;

import java.lang.reflect.Field;

import static java.lang.Math.min;

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
        & this.getPixels().length == image3.getPixels().length
        & this.getPixels()[0].length == image2.getPixels()[0].length
        & image2.getPixels()[0].length == image3.getPixels()[0].length
        & this.getPixels()[0].length == image3.getPixels()[0].length) {
      new_image = getMatrix(this.getWidth(), this.getHeight());
      if (this.getPixels()[0][0] == null & image2.getPixels()[0][0] == null
          & image3.getPixels()[0][0] == null) {
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
        if (oldPixel != null) {
          Integer r = (int) min(255, Math.max(0, oldPixel.getChanne11() + increment));
          Integer g = (int) min(255, Math.max(0, oldPixel.getChanne12() + increment));
          Integer b = (int) min(255, Math.max(0, oldPixel.getChanne13() + increment));
          newPixels[i][j] = getObject(r, g, b, oldPixel.hasAlpha());
        }
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
        if (object != null) {
          new_image[i][j] = getObject((Integer) field.get(object),
              (Integer) field.get(object), (Integer) field.get(object), object.hasAlpha());
        }
      }
    }
    return getOImage(new_image, this.getWidth(), this.getHeight());
  }

  @Override
  public TypeOfImage visualizeValueIntensityLuma(MeasurementType measure) {
    TypeofImageObject[][] new_image = getMatrix(this.width, this.height);
    for (int i = 0; i < this.getWidth(); i++) {
      for (int j = 0; j < this.getHeight(); j++) {
        if (measure.toString().equals("value")) {
          if (this.getPixels()[i][j] != null) {
            Integer value = Math.max(this.getPixels()[i][j].getChanne11(),
                Math.max(this.getPixels()[i][j].getChanne12(),
                    this.getPixels()[i][j].getChanne13()));
            new_image[i][j] = getObject(value, value, value, this.getPixels()[i][j].hasAlpha());
          }
        }
        //intensity
        if (measure.toString().equals("intensity")) {
          if (this.getPixels()[i][j] != null) {
            Integer intensity =
                (this.getPixels()[i][j].getChanne11() + this.getPixels()[i][j].getChanne12()
                    + this.getPixels()[i][j].getChanne13()) / 3;
            new_image[i][j] = getObject(intensity, intensity, intensity,
                this.getPixels()[i][j].hasAlpha());
          }
        }
        //luma
        if (measure.toString().equals("luma")) {
          if (this.getPixels()[i][j] != null) {
            double luma = 0.2126 * this.getPixels()[i][j].getChanne11()
                + 0.7152 * this.getPixels()[i][j].getChanne12()
                + 0.0722 * this.getPixels()[i][j].getChanne13();
            new_image[i][j] = getObject((int) luma, (int) luma, (int) luma,
                this.getPixels()[i][j].hasAlpha());
          }
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
        if (this.getPixels()[i][j] != null) {
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
    }
    return getOImage(new_image, this.getWidth(), this.getHeight());
  }

  @Override
  public TypeOfImage blur() {
    TypeofImageObject[][] newPixels = getMatrix(this.width, this.height);

    // Create a 3x3 Gaussian filter
    double[][] filter = {
        {1.0 / 16.0, 1.0 / 8.0, 1.0 / 16.0},
        {1.0 / 8.0, 1.0 / 4.0, 1.0 / 8.0},
        {1.0 / 16.0, 1.0 / 8.0, 1.0 / 16.0}
    };

    // Iterate over each pixel in the image
    for (int x = 0; x < this.width; x++) {
      for (int y = 0; y < this.height; y++) {
        // Apply the Gaussian filter to the pixel and its neighbors
        double r = 0;
        double g = 0;
        double b = 0;
        TypeofImageObject oldPixel = null;
        for (int i = -1; i <= 1; i++) {
          for (int j = -1; j <= 1; j++) {
            int xx = x + i;
            int yy = y + j;
            if (xx >= 0 && xx < this.width && yy >= 0 && yy < this.height) {
              oldPixel = this.pixels[xx][yy];
              double weight = filter[i + 1][j + 1];
              if (oldPixel != null) {
                r += weight * oldPixel.getChanne11();
                g += weight * oldPixel.getChanne12();
                b += weight * oldPixel.getChanne13();
              }
            }
          }
        }
        // Add the amount parameter to the filtered values and clamp to [0, 255]
        Integer r2 = (int) Math.min(255, Math.max(0, Math.round(r)));
        Integer g2 = (int) Math.min(255, Math.max(0, Math.round(g)));
        Integer b2 = (int) Math.min(255, Math.max(0, Math.round(b)));
        // Set the value of the corresponding pixel in the new image object
        if (oldPixel != null && r2 != null && g2 != null && b2 != null) {
          newPixels[x][y] = getObject(r2, g2, b2, oldPixel.hasAlpha());
        }
      }
    }

    return getOImage(newPixels, this.getWidth(), this.getHeight());
  }


  @Override
  public TypeOfImage sharpen() {
    TypeofImageObject[][] newPixels = getMatrix(this.width, this.height);

    // Create a 3x3 Laplacian filter
    double[][] filter = {
        {-1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0},
        {-1.0 / 8.0, 1.0 / 4.0, 1.0 / 4.0, 1.0 / 4.0, -1.0 / 8.0},
        {-1.0 / 8.0, 1.0 / 4.0, 1.0, 1.0 / 4.0, -1.0 / 8.0},
        {-1.0 / 8.0, 1.0 / 4.0, 1.0 / 4.0, 1.0 / 4.0, -1.0 / 8.0},
        {-1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0}
    };

    // Iterate over each pixel in the image
    for (int x = 0; x < this.width; x++) {
      for (int y = 0; y < this.height; y++) {
        // Apply the Laplacian filter to the pixel and its neighbors
        double r = 0;
        double g = 0;
        double b = 0;
        TypeofImageObject oldPixel = null;
        for (int i = -1; i <= 1; i++) {
          for (int j = -1; j <= 1; j++) {
            int xx = x + i;
            int yy = y + j;
            if (xx >= 0 && xx < this.width && yy >= 0 && yy < this.height) {
              oldPixel = this.pixels[xx][yy];
              double weight = filter[i + 1][j + 1];
              if (oldPixel != null) {
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
          if (oldPixel != null && r2 != null && g2 != null && b2 != null) {
            newPixels[x][y] = getObject(r2, g2, b2, oldPixel.hasAlpha());
          }
        }
      }
    }

    return getOImage(newPixels, this.getWidth(), this.getHeight());
  }

  @Override
  public TypeOfImage dither() throws NoSuchFieldException, IllegalAccessException {
    TypeOfImage greyScaleImage = visualizeValueIntensityLuma(MeasurementType.luma);
    TypeofImageObject[][] newPixels = getMatrix(this.width, this.height);

    for (int r = 0; r < greyScaleImage.getHeight(); r++) {
      for (int c = 0; c < greyScaleImage.getWidth(); c++) {
        TypeofImageObject oldPixel = greyScaleImage.getPixels()[c][r];
        if (oldPixel != null) {
          int oldColor = oldPixel.getChanne11();
          int newColor = (oldColor > 128) ? 255 : 0;
          newPixels[c][r] = getObject(newColor, newColor, newColor, oldPixel.hasAlpha());
          int error = oldColor - newColor;


          if (c + 1 < greyScaleImage.getWidth()) {
            // Add to pixel on the right
            TypeofImageObject rightPixel = greyScaleImage.getPixels()[c + 1][r];
            int rightError = (int) (error * 7.0 / 16.0);
            if (rightPixel != null) {
              int newRed = rightPixel.getChanne11() + rightError;
              newRed = Math.max(0, Math.min(255, newRed));
              TypeofImageObject updatedRightPixel = getObject(newRed, newRed, newRed,
                  rightPixel.hasAlpha());
              greyScaleImage.getPixels()[c + 1][r] = updatedRightPixel;
            }
          }

          if (r + 1 < greyScaleImage.getHeight() && c - 1 >= 0) {
            // Add to pixel on the next-row-left
            TypeofImageObject bottomLeftPixel = greyScaleImage.getPixels()[c - 1][r + 1];
            int bottomLeftError = (int) (error * 3.0 / 16.0);
            if (bottomLeftPixel != null) {
              int newRed = bottomLeftPixel.getChanne11() + bottomLeftError;
              newRed = Math.max(0, Math.min(255, newRed));
              TypeofImageObject updatedBottomLeftPixel = getObject(newRed, newRed, newRed,
                  bottomLeftPixel.hasAlpha());
              greyScaleImage.getPixels()[c - 1][r + 1] = updatedBottomLeftPixel;
            }
          }

          if (r + 1 < greyScaleImage.getHeight()) {
            // Add to pixel on the next-row
            TypeofImageObject bottomPixel = greyScaleImage.getPixels()[c][r + 1];
            int bottomError = (int) (error * 5.0 / 16.0);
            if (bottomPixel != null) {
              int newRed = bottomPixel.getChanne11() + bottomError;
              newRed = Math.max(0, Math.min(255, newRed));
              TypeofImageObject updatedBottomPixel = getObject(newRed, newRed, newRed,
                  bottomPixel.hasAlpha());
              greyScaleImage.getPixels()[c][r + 1] = updatedBottomPixel;
            }
          }
          if (r < greyScaleImage.getHeight() - 1 && c < greyScaleImage.getWidth() - 1) {
            // Add to pixel on the next-row-right
            TypeofImageObject bottomRightPixel = this.getPixels()[c + 1][r + 1];
            int bottomRightError = (int) (error * 1.0 / 16.0);
            if (bottomRightPixel != null) {
              int newRed = bottomRightPixel.getChanne11() + bottomRightError;
              newRed = Math.max(0, Math.min(255, newRed));
              TypeofImageObject updatedBottomRightPixel = getObject(newRed, newRed, newRed,
                  bottomRightPixel.hasAlpha());
              greyScaleImage.getPixels()[c + 1][r + 1] = updatedBottomRightPixel;
            }
          }
        }
      }
    }
    return greyScaleImage;
  }

  /**
   * Abstract method returns an object of the class extending it.
   *
   * @param value1 value of the first channel.
   * @param value2 value of the second channel.
   * @param value3 value of the third channel.
   * @param value4 value of the alpha channel, if one exists.
   * @return TypeofImageObject of type extending this class.
   */
  protected abstract TypeofImageObject getObject(int value1, int value2, int value3,
                                                 Integer value4);

  /**
   * This is a method returns all the fields of the object inside the matrix of the class
   * extending this abstract class.
   *
   * @param channel one of the values of enum ComponentRGB.
   * @return Field object
   * @throws NoSuchFieldException if field does not exist.
   */
  protected abstract Field getField(ComponentRGB channel) throws NoSuchFieldException;

}



