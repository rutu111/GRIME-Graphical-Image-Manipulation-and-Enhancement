package controller.commands;

import controller.CommandDesignOperations;
import model.Operations;
import view.ViewI;

import java.io.IOException;

/**
 * This class is for the command design pattern.
 */
public class Brighten implements CommandDesignOperations {

  private double increment;

  private String imageName;

  private String updatedImageName;


  /**
   * This constructor takes in a list a commands.
   *
   * @param commands a list of commands, typed by the user.
   * @throws IllegalArgumentException if number of arguments not as expected.
   */
  public Brighten(String[] commands) throws IllegalArgumentException {
    if (commands.length != 4) {
      throw new IllegalArgumentException("Invalid command format.");
    }

    this.imageName = commands[2];
    this.updatedImageName = commands[3];
    this.increment = Integer.parseInt(commands[1]);

  }

  @Override
  public void goCommand(Operations m, ViewI view) throws IOException {
    m.brighten(imageName, updatedImageName, increment);
    view.printOutput("Image brightened '" + imageName + "' stored as '" +
        updatedImageName + "'" + "\n");
  }
}
