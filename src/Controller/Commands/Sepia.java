package Controller.Commands;

import Controller.CommandDesignOperations;
import Model.Operations;
import View.ViewI;
import java.io.IOException;

public class Sepia implements CommandDesignOperations {

  private String imageName;

  private String updatedImageName;

  public Sepia(String[] commands) {
    if (commands.length != 3) {
      throw new IllegalArgumentException("Invalid command format.");
    }
    this.imageName =  commands[1];
    this.updatedImageName = commands[2];
  }

  @Override
  public void go(Operations m, ViewI view) throws IOException {
    m.colorTransformationSepia(imageName,
        updatedImageName);
    view.printOutput(
        "Image has been provided with sepia tone '" + imageName + "' stored as '"
            + updatedImageName + "'" +
            "\n");
  }
}
