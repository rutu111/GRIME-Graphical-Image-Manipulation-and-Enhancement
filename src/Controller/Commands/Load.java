package Controller.Commands;

import Controller.CommandDesignOperations;
import Controller.ImageUtil;
import Model.Operations;
import View.ViewI;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Load implements CommandDesignOperations {

  private String imagePath;
  private String imageName;

  public Load(String[] commands) throws IllegalArgumentException{
    if (commands.length != 3) {
      throw new IllegalArgumentException("Invalid command format.");
    }
    imagePath = commands[1];
    imageName = commands[2];
  }

  @Override
  public void go(Operations m, ViewI view)
      throws IOException {
    try {
      if (imagePath.split("\\.")[1].equals("ppm")) {
        ImageUtil.readPPM(m, imagePath, imageName);
      } else {
        ImageUtil.imageIORead(m, imagePath, imageName);
      }
      view.printOutput("Loaded image '" + imageName + "' from '" + imagePath + "'" + "\n");
    } catch (
        FileNotFoundException e) {
      view.printError("File not found: " + imageName + "\n");
    }
  }
}
