package Controller.Commands;

import Controller.CommandDesignOperations;
import Model.Operations;
import View.ViewI;
import java.io.IOException;

public class Sharpen implements CommandDesignOperations {

  private String imageName;

  private String updatedImageName;

  public Sharpen(String[] commands) throws IllegalArgumentException {
    if (commands.length != 3) {
      throw new IllegalArgumentException("Invalid command format.");
    }
    this.imageName =  commands[1];
    this.updatedImageName = commands[2];

  }
  @Override
  public void go(Operations m, ViewI view) throws IOException {
    m.sharpen(imageName,
        updatedImageName);
    view.printOutput(
        "Image sharpened '" + imageName + "' stored as '" + updatedImageName + "'" +
            "\n");
  }
}