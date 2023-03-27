package Controller.Commands;

import Controller.CommandDesignOperations;
import Model.Operations;

public class Sharpen implements CommandDesignOperations {

  private String imageName;

  private String updatedImageName;

  public Sharpen(String imageName, String updatedImageName) {
    this.imageName =  imageName;
    this.updatedImageName = updatedImageName;

  }
  @Override
  public void go(Operations m) {
    m.sharpen(imageName,
        updatedImageName);
  }
}
