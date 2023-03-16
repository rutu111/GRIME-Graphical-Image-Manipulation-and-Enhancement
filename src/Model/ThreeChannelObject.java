package Model;

import java.lang.reflect.Field;

public abstract class ThreeChannelObject <T> extends CommonOperations {

  protected ThreeChannelObject(TypeofImageObject[][] pixels, int width, int height) {
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
      new_image = getMatrix(this.getWidth(),this.getHeight());
      for (int i = 0; i < this.getWidth(); i++) {
        for (int j = 0; j < this.getHeight(); j++) {
          new_image[i][j] = getObject((T) this.getPixels()[i][j].getChanne11(),
              (T) image2.getPixels()[i][j].getChanne12(),
              (T) image3.getPixels()[i][j].getChanne13());
        }
      }
    } else {
      throw new IllegalArgumentException("All images must be of the same size!");
    }
    return getOImage(new_image, this.getWidth(), this.getHeight());
  }


  protected abstract TypeofImageObject getObject(T  value1, T value2, T value3);




}
