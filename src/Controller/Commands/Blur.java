package Controller.Commands;

import Controller.CommandDesignOperations;
import Model.Operations;
import View.ViewI;
import java.io.IOException;

public class Blur implements CommandDesignOperations {

  private String imageName;

  private String updatedImageName;

  public Blur(String[] commands) throws  IllegalArgumentException{
    if (commands.length != 3) {
      throw new IllegalArgumentException("Invalid command format.");
    };
    this.imageName =  commands[1];
    this.updatedImageName = commands[2];

  }
  @Override
  public void go(Operations m, ViewI view) throws IOException {
    m.blur(imageName,
        updatedImageName);
    view.printOutput(
        "Image blurred '" + imageName + "' stored as '" + updatedImageName + "'" +
            "\n");
  }
}
