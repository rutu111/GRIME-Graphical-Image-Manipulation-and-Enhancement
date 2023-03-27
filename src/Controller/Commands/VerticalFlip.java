package Controller.Commands;

import Controller.CommandDesignOperations;
import Model.Operations;

public class VerticalFlip implements CommandDesignOperations {

  private String imageName;

  private String updatedImageName;

  public VerticalFlip(String imageName, String updatedImageName) {
    this.imageName =  imageName;
    this.updatedImageName = updatedImageName;

  }

  @Override
  public void go(Operations m) {
    m.verticalFlip(imageName, updatedImageName);
  }
}
