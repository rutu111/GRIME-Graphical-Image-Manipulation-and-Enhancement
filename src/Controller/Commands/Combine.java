package Controller.Commands;

import Controller.CommandDesignOperations;
import Model.Operations;
import View.ViewI;
import java.io.IOException;

public class Combine implements CommandDesignOperations {

  private String imageName1;
  private String imageName2;
  private String imageName3;
  private String updatedimageName;

  public Combine(String[] commands) throws IllegalArgumentException{
    if (commands.length != 5) {
      throw new IllegalArgumentException("Invalid command format.");
    }
    this.imageName1 = commands[2];
    this.imageName2 = commands[3];
    this.imageName3 = commands[4];
    this.updatedimageName = commands[1];
  }
  @Override
  public void go(Operations m, ViewI view) throws IOException {
    m.combineGreyScaleToRGB(imageName1, imageName2, imageName3, updatedimageName);
    view.printOutput(
        "Image '" + updatedimageName + " was created by combining greyscale images: '"
            + imageName1 + " '" + imageName2 + "' and '" + imageName3 + "'" + "\n");
  }
}
