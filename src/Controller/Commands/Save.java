package Controller.Commands;

import Controller.CommandDesignOperations;
import Controller.ImageUtil;
import Model.Operations;
import View.ViewI;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Save implements CommandDesignOperations {

  private String imagePath;

  private String imageName;

  public Save(String[] commands) {
    if (commands.length != 3) {
      throw new IllegalArgumentException("Invalid command format.");
    }
     imagePath = commands[1];
     imageName = commands[2];
  }
  @Override
  public void go(Operations m, ViewI view)
      throws NoSuchFieldException, IllegalAccessException, IOException {
    try {
      if (imagePath.split("\\.")[1].equals("ppm")) {
        ImageUtil.writePPM(m, imagePath, imageName);
      }else {
        ImageUtil.imgeIOWrite(m, imagePath, imageName);
      }
      view.printOutput("Image " + imageName + " saved as file: " + imagePath + "\n");
    } catch (FileNotFoundException e) {
      view.printError("File path does not exist.\n");
    }
  }
}
