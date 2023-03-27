package Controller.Commands;

import Controller.CommandDesignOperations;
import Model.ComponentRGB;
import Model.Operations;

public class Dither implements CommandDesignOperations {

  private ComponentRGB component;

  private String imageName;

  private String updatedImageName;

  public Dither(String imageName, String updatedImageName, ComponentRGB component) {
    this.imageName =  imageName;
    this.updatedImageName = updatedImageName;
    this.component = component;
  }
  @Override
  public void go(Operations m) throws NoSuchFieldException, IllegalAccessException {
    m.dither(imageName, updatedImageName, component);
  }
}
