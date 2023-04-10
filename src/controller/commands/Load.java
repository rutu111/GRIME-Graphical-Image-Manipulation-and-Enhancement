package controller.commands;

import controller.CommandDesignOperations;
import controller.ImageUtil;
import model.Operations;
import view.ViewI;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This class is for the command design pattern.
 */
public class Load implements CommandDesignOperations {

  public String imageName;
  private String imagePath;

  /**
   * This constructor takes in a list a commands.
   *
   * @param commands a list of commands, typed by the user.
   * @throws IllegalArgumentException if number of arguments not as expected.
   */
  public Load(String[] commands) throws IllegalArgumentException {
    if (commands.length != 3) {
      throw new IllegalArgumentException("Invalid command format.");
    }
    imagePath = commands[1];
    imageName = commands[2];
  }

  @Override
  public void goCommand(Operations m, ViewI view)
      throws IOException {
    try {
      String extension = imagePath.split("\\.")[1];
      if (!extension.equals("ppm") & !extension.equals("png") & !extension.equals("jpg")
          & !extension.equals("jpeg") &
          !extension.equals("bmp")) {
        throw new IllegalArgumentException(
            "Invalid file extension! This program only accepts PPM, JPG, PNG and BMP.");
      }
      if (imagePath.split("\\.")[1].equals("ppm")) {
        //if file is ppm, go to readPPM function.
        ImageUtil.readPPM(m, imagePath, imageName);
      } else {
        //else, go to imageIO function
        ImageUtil.imageIORead(m, imagePath, imageName);
      }
      view.printOutput("Loaded image '" + imageName + "' from '" + imagePath + "'" + "\n");
    } catch (FileNotFoundException e) {
      view.printOutput("File not found: " + imageName + "\n");
    } catch (IllegalArgumentException e) {
      view.printOutput( "Invalid file extension! This program only accepts PPM, JPG, PNG and BMP.");
    }  catch (IOException e) {
    view.printOutput(e.getMessage());
    } catch (ArrayIndexOutOfBoundsException e) {
    view.printOutput("Please provide a valid path");
  }
  }
}
