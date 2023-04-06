package controller.commands;

import controller.CommandDesignOperations;
import model.ComponentRGB;
import model.MeasurementType;
import model.Operations;
import view.ViewI;

import java.io.IOException;

/**
 * This class is for the command design pattern.
 */
public class ValueIntensityLumaAndVisualizeComponent implements CommandDesignOperations {

  private MeasurementType measure = null;

  private ComponentRGB component = null;

  private String imageName;

  private String updatedImageName;


  /**
   * This constructor takes in a list a commands.
   *
   * @param commands a list of commands, typed by the user.
   * @throws IllegalArgumentException if number of arguments not as expected.
   */
  public ValueIntensityLumaAndVisualizeComponent(String[] commands)
      throws IllegalArgumentException {
    if (commands.length != 4) {
      throw new IllegalArgumentException("Invalid command format.");
    }
    this.imageName = commands[2];
    this.updatedImageName = commands[3];

    //go to appropiate class based on component requested.
    String component = commands[1];
    String[] componentParts = component.split("-");
    if (componentParts[1].equals("red") || componentParts[1].equals("green")
        || componentParts[1].equals("blue")) {
      this.component = ComponentRGB.valueOf(componentParts[1]);
    } else {
      this.measure = MeasurementType.valueOf(componentParts[1]);
    }
  }

  @Override
  public void goCommand(Operations m, ViewI view)
      throws IOException, NoSuchFieldException, IllegalAccessException {
    if (measure != null) {
      m.visualizeValueIntensityLuma(imageName, updatedImageName, measure);
    }
    if (component != null) {
      m.visIndividualComponent(imageName, updatedImageName, component);
    }
    view.printOutput(
        "Image '" + imageName + "' stored as greyscale '" + updatedImageName + "'" + "\n");
  }
}
