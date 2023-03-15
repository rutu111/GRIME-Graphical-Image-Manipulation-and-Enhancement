package Model;

public abstract class ImageBuilder <P, T extends ImageBuilder<P, T>>  {

  protected final int width;
  protected final int height;

  //create 2D matrix variable.
  protected ImageBuilder(int width, int height){
    this.width = width;
    this.height = height;
  }

  //this method takes in an object of type P.

   public abstract void addPixelAtPosition(int width, int height, P pixel);


   public abstract TypeOfImage buildImage();


}
