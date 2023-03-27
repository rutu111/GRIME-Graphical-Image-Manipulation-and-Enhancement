package Controller.Commands;

import Controller.CommandDesignOperations;
import Model.Operations;

public class HorizontalFlip implements CommandDesignOperations {

  private String imageName;

  private String updatedImageName;

  public HorizontalFlip(String imageName, String updatedImageName) {
    this.imageName =  imageName;
    this.updatedImageName = updatedImageName;

  }
  @Override
  public void go(Operations m) {
    m.horizontalFlip(imageName,
        updatedImageName);
  }
}
