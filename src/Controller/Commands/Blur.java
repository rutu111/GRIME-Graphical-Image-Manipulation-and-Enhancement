package Controller.Commands;

import Controller.CommandDesignOperations;
import Model.Operations;

public class Blur implements CommandDesignOperations {

  private String imageName;

  private String updatedImageName;

  public Blur(String imageName, String updatedImageName) {
    this.imageName =  imageName;
    this.updatedImageName = updatedImageName;

  }
  @Override
  public void go(Operations m)  {
    m.blur(imageName,
        updatedImageName);
  }
}
