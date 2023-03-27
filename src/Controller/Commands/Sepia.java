package Controller.Commands;

import Controller.CommandDesignOperations;
import Model.Operations;

public class Sepia implements CommandDesignOperations {

  private String imageName;

  private String updatedImageName;

  public Sepia(String imageName, String updatedImageName) {
    this.imageName =  imageName;
    this.updatedImageName = updatedImageName;
  }

  @Override
  public void go(Operations m) {
    m.colorTransformationSepia(imageName,
        updatedImageName);
  }
}
