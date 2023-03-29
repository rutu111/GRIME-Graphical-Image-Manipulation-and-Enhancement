package Controller.Commands;

import Controller.CommandDesignOperations;
import Model.MeasurementType;
import Model.Operations;
import View.ViewI;
import java.io.IOException;

/**
 * This class is for the command design pattern.
 */
public class TransformGreyscale implements CommandDesignOperations {

  private String imageName;

  private String updatedImageName;

  /**
   * This constructor takes in a list a commands.
   * @param commands a list of commands, typed by the user.
   * @throws IllegalArgumentException if number of arguments not as expected.
   */
  public TransformGreyscale(String[] commands)  throws  IllegalArgumentException{
    if (commands.length != 3) {
      throw new IllegalArgumentException("Invalid command format.");
    }
    this.imageName =  commands[1];
    this.updatedImageName = commands[2];

  }
  @Override
  public void go(Operations m, ViewI view) throws IOException {
    m.visualizeValueIntensityLuma(imageName, updatedImageName, MeasurementType.luma);
    view.printOutput(
        "Image has been colour transformed with luma '" + imageName + "' stored as '"
            + updatedImageName + "'" +
            "\n");
  }
}
