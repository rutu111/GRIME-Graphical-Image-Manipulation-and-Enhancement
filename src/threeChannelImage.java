public class threeChannelImage implements TypeOfImage {

  //private constructor that takes in a matrix of type RGB, width and height.
  private TypeofImageObject[][] pixels;
  private int width;
  private int height;

  public threeChannelImage(TypeofImageObject[][] pixels, int width, int height) {
    this.pixels = pixels;
    this.width = width;
    this.height = height;
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


  public static class threeChannelImageBuilder extends ImageBuilder<threeChannelObject, threeChannelImageBuilder> {

    private TypeofImageObject[][] pixels;

    public threeChannelImageBuilder(int width, int height) {
      super(width, height);
      //here create a 2D matrix using width and height given with dummy values of type RGB. (default)
      // Initialize the 2D matrix with dummy RGB values
      this.pixels = new threeChannelObject[width][height];
      for (int i = 0; i < width; i++) {
        for (int j = 0; j < height; j++) {
          this.pixels[i][j] = new threeChannelObject(0, 0, 0); // Dummy RGB value

        }
      }
    }


    //this method takes in an object of type RGB.
    @Override
    public void addPixelAtPosition(int width, int height, threeChannelObject pixel) {
      if (this.pixels == null) {
        this.pixels = new threeChannelObject[width][height];
      }
      if (width >= 0 && width < this.width && height >= 0 && height < this.height) {
        this.pixels[width][height] = pixel;
      }
//      System.out.println(this.pixels[width][height].red);
//      System.out.println(this.pixels[width][height].green);
//      System.out.println(this.pixels[width][height].blue);
//      System.out.println('\n');
    }


    //create an object of the outer class, passing it the matrix of type RGB, height and width.
    @Override
    public TypeOfImage buildImage() {
      return new threeChannelImage(this.pixels, width, height);
    }

  }

}


