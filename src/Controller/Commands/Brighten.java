package Controller.Commands;

import Controller.CommandDesignOperations;
import Model.Operations;
import View.ViewI;
import java.io.IOException;

public class Brighten implements CommandDesignOperations {

  private double increment;

  private String imageName;

  private String updatedImageName;


  public Brighten(String[] commands) throws IllegalArgumentException{
    if (commands.length != 4) {
      throw new IllegalArgumentException("Invalid command format.");
    }

    this.imageName =  commands[2];
    this.updatedImageName = commands[3];
    this.increment = Integer.parseInt(commands[1]);

  }

  @Override
  public void go(Operations m, ViewI view) throws IOException {
    m.brighten(imageName, updatedImageName, increment);
    view.printOutput("Image brightened '" + imageName + "' stored as '" + updatedImageName + "'" + "\n");
  }
}
