package Model;
import java.lang.reflect.Field;

public class RGBIntegerImage extends CommonOperations  {

  public RGBIntegerImage(TypeofImageObject[][] pixels, int width, int height) {
    super(pixels, width, height);

  }

  public TypeofImageObject[][] getPixels() {
    return this.pixels;
  }

  public int getHeight() {
    return this.height;
  }

  public int getWidth() {
    return this.width;
  }

  @Override
  public boolean equals(Object o) {
    // Fast path for pointer equality:
    if (this == o) { //backward compatibility with default equals
      return true;
    }
    // Check if the type of o is the same as the type of "this"
    if (!(o instanceof TypeOfImage)) {
      return false;
    }
    TypeOfImage other = (TypeOfImage) o;
    if (this.getWidth() == other.getWidth() & this.getHeight() == other.getHeight()) {
      for (int i = 0; i < this.getWidth(); i++) {
        for (int j = 0; j < this.getHeight(); j++) {
          if (!this.getPixels()[i][j].equals(other.getPixels()[i][j])) {
            return false;
          }
        }
      }
    } else {
      return false;
    }
    return true;
  }

  /*
  @Override
  public TypeOfImage horizontalFlip() {
    TypeofImageObject[][] flippedImage = this.getPixels().clone();
    for(int col = 0;col < flippedImage[0].length; col++){
      for(int row = 0; row < flippedImage.length/2; row++) {
        TypeofImageObject temp = flippedImage[row][col];
        flippedImage[row][col] = flippedImage[flippedImage.length - row - 1][col];
        flippedImage[flippedImage.length - row - 1][col] = temp;
      }
    }
    return new threeChannelImage(flippedImage, this.getWidth(), this.getHeight());
  }


  @Override
  public TypeOfImage verticalFlip() {
    TypeofImageObject[][] flippedImage = this.getPixels().clone();
    //flip the columns
    for(int row = 0; row < flippedImage.length; row++){
      for(int col = 0; col < flippedImage[row].length / 2; col++) {
        TypeofImageObject temp = flippedImage[row][col];
        flippedImage[row][col] = flippedImage[row][flippedImage[row].length - col - 1];
        flippedImage[row][flippedImage[row].length - col - 1] = temp;
      }
    }
    return new threeChannelImage(flippedImage, this.getWidth(), this.getHeight());
  }

   */


  @Override
  public TypeOfImage getOImage(TypeofImageObject[][] flippedImage, int width, int height) {
    return new RGBIntegerImage(flippedImage, width, height);
  }

  @Override
  public TypeOfImage brighten(double increment) {
    TypeofImageObject[][] newPixels = new RGBIntegerObject[this.width][this.height];
    for (int i = 0; i < this.width; i++) {
      for (int j = 0; j < this.height; j++) {
        TypeofImageObject oldPixel = this.pixels[i][j];
        //Add increment value to RGB values and clamp to [0, 255]
        int r = (int) Math.min(255, Math.max(0, oldPixel.getChanne11() + increment));
        int g = (int) Math.min(255, Math.max(0, oldPixel.getChanne12() + increment));
        int b = (int) Math.min(255, Math.max(0, oldPixel.getChanne13() + increment));
        newPixels[i][j] = new RGBIntegerObject(r, g, b);
      }
    }
    return new RGBIntegerImage(newPixels, this.width, this.height);
  }

  @Override
  public TypeOfImage visualizeValueIntensityLuma(MeasurementType measure) throws  NoSuchFieldException, IllegalAccessException  {
    TypeofImageObject[][] new_image = new RGBIntegerObject[this.getWidth()][this.getHeight()];
    for (int i = 0; i < this.getWidth(); i++) {
      for (int j = 0; j < this.getHeight(); j++) {
        if(measure.toString().equals("value")) {
          int value = Math.max(this.getPixels()[i][j].getChanne11(),
              Math.max(this.getPixels()[i][j].getChanne12(),
                  this.getPixels()[i][j].getChanne13()));
          new_image[i][j] = new RGBIntegerObject(value, value, value);
        }
        //intensity
        if(measure.toString().equals("intensity")) {
          int intensity = (this.getPixels()[i][j].getChanne11() + this.getPixels()[i][j].getChanne12()
              + this.getPixels()[i][j].getChanne13()) / 3;
          new_image[i][j] = new RGBIntegerObject(intensity, intensity, intensity);
        }
        //luma
        if(measure.toString().equals("luma")){
          double luma = 0.2126 * this.getPixels()[i][j].getChanne11()
              + 0.7152 * this.getPixels()[i][j].getChanne12() +
              0.0722 * this.getPixels()[i][j].getChanne13();
          new_image[i][j] = new RGBIntegerObject((int) luma, (int) luma, (int) luma);
        }
      }
    }
    return new RGBIntegerImage(new_image, this.getWidth(), this.getHeight());
  }

  @Override
  public TypeOfImage visIndividualComponent(Component channel)
      throws NoSuchFieldException, IllegalAccessException {
    Field field = RGBIntegerObject.class.getDeclaredField(channel.toString());
    TypeofImageObject[][] new_image = new RGBIntegerObject[this.getWidth()][this.getHeight()];
    for (int i = 0; i < this.getWidth(); i++) {
      for (int j = 0; j < this.getHeight(); j++) {
        TypeofImageObject object = this.getPixels()[i][j];
        new_image[i][j] = new RGBIntegerObject((Integer) field.get(object),
            (Integer) field.get(object), (Integer) field.get(object));
      }
    }
    return new RGBIntegerImage(new_image, this.getWidth(), this.getHeight());
  }


  public threeChannelImageBuilder createBuilderThreeChannel (int width, int height) {
    return new threeChannelImageBuilder(width, height);
  }

  public static class threeChannelImageBuilder extends ImageBuilder <RGBIntegerObject> {


    public threeChannelImageBuilder(int width, int height) throws IllegalArgumentException {
      super(width, height);
      if (width <= 0 | height <= 0) {
        throw new IllegalArgumentException("Width and height need to be greater then 0. ");
      }
      //here create a 2D matrix using width and height given with dummy values of type RGB. (default)
      // Initialize the 2D matrix with dummy RGB values
      this.pixels = new RGBIntegerObject[width][height];
      for (int i = 0; i < width; i++) {
        for (int j = 0; j < height; j++) {
          this.pixels[i][j] = new RGBIntegerObject(0, 0, 0); // Dummy RGB value

        }
      }
    }


    //this method takes in an object of type RGB.
    @Override
    public void addPixelAtPosition(int width, int height, RGBIntegerObject pixel) {
      if (this.pixels == null) {
        this.pixels = new RGBIntegerObject[width][height];
      }
      if (width >= 0 && width < this.width && height >= 0 && height < this.height) {
        this.pixels[width][height] = pixel;
      }
    }


    //create an object of the outer class, passing it the matrix of type RGB, height and width.
    @Override
    public TypeOfImage buildImage() {
      return new RGBIntegerImage(this.pixels, width, height);
    }
  }

}


