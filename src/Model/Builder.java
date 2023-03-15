package Model;

public interface Builder {

  public  void addPixelAtPosition(int width, int height, TypeofImageObject pixel);

  public  TypeOfImage buildImage();
}
