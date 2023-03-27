package Controller;

import Controller.Commands.Blur;
import Controller.Commands.Brighten;
import Controller.Commands.Combine;
import Controller.Commands.Dither;
import Controller.Commands.HorizontalFlip;
import Controller.Commands.RGBSplit;
import Controller.Commands.Sepia;
import Controller.Commands.Sharpen;
import Controller.Commands.ValueIntensityLuma;
import Controller.Commands.VerticalFlip;
import Controller.Commands.VisulizeComponent;
import Model.ComponentRGB;
import Model.MeasurementType;
import Model.Operations;
import View.View;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * This is the controller class. It interacts with the view (command line), tells the model what to
 * do and returns output to the user.
 */
public class ImageController {

  final Readable in;
  private final Operations model;
  private View view;

  /**
   * This constructor creates a controller object.
   *
   * @param model takes the model instance as input.
   * @param in    input stream.
   */
  public ImageController(Operations model, Readable in, View view) {
    this.model = model;
    this.in = in;
    this.view = view;

  }

  /**
   * Run method should run the command based on user input.
   */
  public void run() throws IOException {
    view.printWelcomeMessage();
    boolean go = true;
    Scanner scanner = new Scanner(this.in);
    while (go) {
      try {
        String command;
        command = scanner.nextLine().trim();
        String[] commandParts = command.split(" ");
        if (commandParts.length == 0) {
          return;
        } else {
          if (commandParts[0].equals("run")) {
            if (commandParts.length != 2) {
              throw new IllegalArgumentException("Invalid command format.");
            }
            String filename = commandParts[1];
            readScriptFile(filename);
            view.printOutput("Script file ran successfully \n");
          } else if (commandParts[0].equals("exit")) {
            if (commandParts.length != 1) {
              throw new IllegalArgumentException("Invalid command format.");
            }
            go = false;
            view.printOutput("Exit the program \n");
          } else {
            commandExecution(commandParts);
          }
        }
      } catch (IllegalArgumentException e) {
        view.printError("Error: " + e.getMessage() + "\n");
      } catch (Exception e) {
        view.printError("Error: " + e.getMessage() + "\n");
      }
    }
    scanner.close();
  }

  /**
   * This method has the list of commands that can be inputted by the user in command line or run
   * through a script.
   *
   * @param commands string array of commands.
   * @throws IOException thrown when there's error in input and output.
   */
  public void commandExecution(String[] commands)
      throws IOException, NoSuchFieldException, IllegalAccessException {
    //to run even if the nextline is blank
    if (commands.length == 0 || commands[0].trim().isEmpty()) {
      view.printError("Please enter appropriate command. \n");
      return;
    }
    CommandDesignOperations cmd = null;
    switch (commands[0]) {
      case "load":
        try {
          if (commands.length != 3) {
            throw new IllegalArgumentException("Invalid command format.");
          }
          String imagePath = commands[1];
          String imageName = commands[2];
          if (imagePath.split("\\.")[1].equals("ppm")) {
            ImageUtil.readPPM(this.model, imagePath, imageName);
          } else {
            ImageUtil.imageIORead(this.model, imagePath, imageName);
          }
          view.printOutput("Loaded image '" + imageName + "' from '" + imagePath + "'" + "\n");
        } catch (IllegalArgumentException e) {
          view.printError("Error: " + e.getMessage() + "\n");
        } catch (FileNotFoundException e) {
          view.printError("File not found: " + commands[1] + "\n");
        }
        break;
      case "brighten": {
        if (commands.length != 4) {
          throw new IllegalArgumentException("Invalid command format.");
        }
        double increment = Integer.parseInt(commands[1]);
        String imageName = commands[2];
        String updatedImageName = commands[3];
        cmd = new Brighten(imageName, updatedImageName, increment);
        view.printOutput(
            "Image brightened '" + imageName + "' stored as '" + updatedImageName + "'" + "\n");
      }
      break;
      case "vertical-flip": {
        if (commands.length != 3) {
          throw new IllegalArgumentException("Invalid command format.");
        }
        String imageName = commands[1];
        String updatedImageName = commands[2];
        cmd = new VerticalFlip(imageName, updatedImageName);
        view.printOutput(
            "Image vertically flipped '" + imageName + "' stored as '" + updatedImageName + "'" + "\n");
      }
      break;
      case "horizontal-flip": {
        if (commands.length != 3) {
          throw new IllegalArgumentException("Invalid command format.");
        }
        String imageName = commands[1];
        String updatedImageName = commands[2];
        cmd = new HorizontalFlip(imageName, updatedImageName);
        view.printOutput(
            "Image horizontally flipped '" + imageName + "' stored as '" + updatedImageName + "'" +
                "\n");
      }
      break;
      case "greyscale":
        try {
          if (commands.length != 4) {
            throw new IllegalArgumentException("Invalid command format.");
          }
          String component = commands[1];
          String imageName = commands[2];
          String updatedImageName = commands[3];
          String[] componentParts = component.split("-");
          if (componentParts[0].equals("red") || componentParts[0].equals("green")
              || componentParts[0].equals("blue")) {
            cmd = new VisulizeComponent(imageName, updatedImageName, ComponentRGB.valueOf(componentParts[0]));
          } else {
            cmd = new ValueIntensityLuma(imageName, updatedImageName, MeasurementType.valueOf(componentParts[0]));
          }
          view.printOutput(
              "Image '" + imageName + "' stored as greyscale '" + updatedImageName + "'" + "\n");

        } catch (IllegalArgumentException e) {
          view.printError("Error: " + e.getMessage() + "\n");
        }
        break;
      case "rgb-split":
        try {
          if (commands.length != 5) {
            throw new IllegalArgumentException("Invalid command format.");
          }
          String imageName = commands[1];
          String updatedimageName1 = commands[2];
          String updatedimageName2 = commands[3];
          String updatedimageName3 = commands[4];
          cmd = new RGBSplit(imageName, updatedimageName1, updatedimageName2,
              updatedimageName3);
          view.printOutput(
              "Image '" + imageName + "' has been split into greyscale images: '"
                  + updatedimageName1 + "'" + updatedimageName2 + "' and " + updatedimageName3 +
                  "'" + "\n");
        } catch (IllegalArgumentException e) {
          view.printError("Error: " + e.getMessage() + "\n");
        }
        break;
      case "rgb-combine":
        if (commands.length != 5) {
          throw new IllegalArgumentException("Invalid command format.");
        }
        String updatedimageName = commands[1];
        String imageName1 = commands[2];
        String imageName2 = commands[3];
        String imageName3 = commands[4];
        cmd = new Combine(imageName1, imageName2, imageName3, updatedimageName);
        view.printOutput(
            "Image '" + updatedimageName + " was created by combining greyscale images: '"
                + imageName1 + " '" + imageName2 + "' and '" + imageName3 + "'" + "\n");
        break;
      case "filter-blur": {
        if (commands.length != 3) {
          throw new IllegalArgumentException("Invalid command format.");
        }
        String imageName = commands[1];
        String updatedImageName = commands[2];
        cmd = new Blur(imageName,
            updatedImageName);
        view.printOutput(
            "Image blurred '" + imageName + "' stored as '" + updatedImageName + "'" +
                "\n");
      }
      break;
      case "filter-sharpen": {
        if (commands.length != 3) {
          throw new IllegalArgumentException("Invalid command format.");
        }
        String imageName = commands[1];
        String updatedImageName = commands[2];
        cmd = new Sharpen(imageName,
            updatedImageName);
        view.printOutput(
            "Image sharpened '" + imageName + "' stored as '" + updatedImageName + "'" +
                "\n");
      }
      break;
      case "transform-greyscale": {
        if (commands.length != 3) {
          throw new IllegalArgumentException("Invalid command format.");
        }
        String imageName = commands[1];
        String updatedImageName = commands[2];
        cmd = new ValueIntensityLuma(imageName, updatedImageName, MeasurementType.luma);

        view.printOutput(
            "Image has been colour transformed with luma '" + imageName + "' stored as '"
                + updatedImageName + "'" +
                "\n");
      }
      break;
      case "transform-sepia": {
        if (commands.length != 3) {
          throw new IllegalArgumentException("Invalid command format.");
        }
        String imageName = commands[1];
        String updatedImageName = commands[2];
        cmd = new Sepia(imageName,
            updatedImageName);
        view.printOutput(
            "Image has been provided with sepia tone '" + imageName + "' stored as '"
                + updatedImageName + "'" +
                "\n");
      }
      break;
      case "dither": {
        if (commands.length != 4) {
          throw new IllegalArgumentException("Invalid command format.");
        }
        String component = commands[1];
        String imageName = commands[2];
        String updatedImageName = commands[3];
        String[] componentParts = component.split("-");
        if (componentParts[0].equals("red") || componentParts[0].equals("green")
            || componentParts[0].equals("blue")) {
          cmd = new Dither(imageName,
              updatedImageName, ComponentRGB.valueOf(componentParts[0]));
          view.printOutput(
              "Image has been dithered '" + imageName + "' stored as '"
                  + updatedImageName + "'" +
                  "\n");
        }
      }
      break;
      case "save":
        try {
          if (commands.length != 3) {
            throw new IllegalArgumentException("Invalid command format.");
          }
          String imagePath = commands[1];
          String imageName = commands[2];
          view.printOutput("Image " + imageName + " saved as file: " + imagePath + "\n");
          if (imagePath.split("\\.")[1].equals("ppm")) {
            ImageUtil.writePPM(this.model, imagePath, imageName);
          } else {
            ImageUtil.imgeIOWrite(this.model, imagePath, imageName);
          }
        } catch (IllegalArgumentException e) {
          view.printError("Error: " + e.getMessage() + "\n");
        } catch (FileNotFoundException e) {
          view.printError("File path does not exist.\n");
        }
        break;
      default:
        //throws exception when the command is invalid
        throw new IllegalArgumentException("Invalid command: " + commands[0] + "\n");

    }
    if (cmd != null) {
      try {
        cmd.go(this.model); //execute the command
        cmd = null;
      } catch (NoSuchFieldException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
    }
  }

  /**
   * This methods is used read the script.txt file.
   *
   * @param filename file to save
   * @throws IOException throws IO exception.
   */
  public void readScriptFile(String filename)
      throws IOException, NoSuchFieldException, IllegalAccessException {
    try {
      File file = new File(filename);
      Scanner scanner = new Scanner(file);

      while (scanner.hasNextLine()) {
        String line = scanner.nextLine().trim();
        if (!line.isEmpty() && !line.startsWith("//") && !line.startsWith("#")) {
          String[] words = line.split("\\s+");
          commandExecution(words);
        }
      }
    } catch (FileNotFoundException | NoSuchFieldException | IllegalAccessException e) {
      throw e;
    }
  }
}
