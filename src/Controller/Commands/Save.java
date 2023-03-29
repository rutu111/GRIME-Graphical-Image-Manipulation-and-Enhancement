package Controller.Commands;

import Controller.CommandDesignOperations;
import Controller.ImageUtil;
import Model.Operations;
import View.ViewI;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This class is for the command design pattern.
 */
public class Save implements CommandDesignOperations {

  private String imagePath;

  private String imageName;

  /**
   * This constructor takes in a list a commands.
   * @param commands a list of commands, typed by the user.
   * @throws IllegalArgumentException if number of arguments not as expected.
   */
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
      String extension = imagePath.split("\\.")[1];
      if(!extension.equals("ppm") | !extension.equals("png") | !extension.equals("jpg") | !extension.equals("jpeg") |
              !extension.equals("bmp")){
        throw new IllegalArgumentException("Invalid file extension!");
      }
      if (extension.equals("ppm")) {
        ImageUtil.writePPM(m, imagePath, imageName);
      }else{
        ImageUtil.imgeIOWrite(m, imagePath, imageName);
      }
      view.printOutput("Image " + imageName + " saved as file: " + imagePath + "\n");
    } catch (FileNotFoundException e) {
      view.printOutput("File path does not exist.\n");
    }
  }
}
