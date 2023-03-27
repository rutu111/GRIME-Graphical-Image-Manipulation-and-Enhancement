package Controller.Commands;

import Controller.CommandDesignOperations;
import Model.Operations;

public class Brighten implements CommandDesignOperations {

  private double increment;

  private String imageName;

  private String updatedImageName;

  public Brighten(String imageName, String updatedImageName, double increment) {
    this.imageName =  imageName;
    this.updatedImageName = updatedImageName;
    this.increment = increment;
  }

  @Override
  public void go(Operations m) {
    m.brighten(imageName, updatedImageName, increment);
  }
}
