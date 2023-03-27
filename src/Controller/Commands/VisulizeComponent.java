package Controller.Commands;

import Controller.CommandDesignOperations;
import Model.ComponentRGB;
import Model.Operations;

public class VisulizeComponent implements CommandDesignOperations {

  private ComponentRGB component;

  private String imageName;

  private String updatedImageName;

  public VisulizeComponent(String imageName, String updatedImageName, ComponentRGB component) {
    this.imageName = imageName;
    this.updatedImageName = updatedImageName;
    this.component = component;
  }

  @Override
  public void go(Operations m) throws NoSuchFieldException, IllegalAccessException {
      m.visIndividualComponent(imageName, updatedImageName, component);
  }
}
