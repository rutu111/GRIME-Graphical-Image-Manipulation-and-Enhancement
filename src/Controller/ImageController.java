package Controller;

import Controller.Commands.Blur;
import Controller.Commands.Brighten;
import Controller.Commands.Combine;
import Controller.Commands.Dither;
import Controller.Commands.HorizontalFlip;
import Controller.Commands.Load;
import Controller.Commands.RGBSplit;
import Controller.Commands.Sepia;
import Controller.Commands.Sharpen;
import Controller.Commands.TransformGreyscale;
import Controller.Commands.ValueIntensityLumaAndVisualizeComponent;
import Controller.Commands.VerticalFlip;
import Model.ComponentRGB;
import Model.MeasurementType;
import Model.Operations;
import View.ViewI;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

/**
 * This is the controller class. It interacts with the view (command line), tells the model what to
 * do and returns output to the user.
 */
public class ImageController {

  final Readable in;
  private final Operations model;
  private ViewI view;

  private Map<String, Function<List, CommandDesignOperations>> commandMap



  /**
   * This constructor creates a controller object.
   *
   * @param model takes the model instance as input.
   * @param in    input stream.
   */
  public ImageController(Operations model, Readable in, ViewI view) {
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


    /*
    commandMap = new HashMap<>();
    //commandMap.put("load", new LoadCommand());
    commandMap.put("brighten", l -> new Brighten(commands));



    commandMap.put("vertical-flip", new VerticalFlip());
    commandMap.put("horizontal-flip", new HorizontalFlip());
    commandMap.put("greyscale", new GreyscaleCommand());
    commandMap.put("rgb-split", new RGBSplit());
    commandMap.put("rgb-combine", new Combine());
    commandMap.put("filter-blur", new Blur());
    commandMap.put("filter-sharpen", new Sharpen();
    commandMap.put("filter-sepia", new Sepia());
    commandMap.put("transform-greyscale", new ValueIntensityLuma());
    commandMap.put("dither", new Dither());

     */

    CommandDesignOperations cmd = null;
    switch (commands[0]) {
      case "load":
         cmd = new Load(commands);
        break;
      case "brighten":
        cmd = new Brighten(commands);
      break;
      case "vertical-flip":
        cmd = new VerticalFlip(commands);
        break;
      case "horizontal-flip":
        cmd = new HorizontalFlip(commands);
      break;
      case "greyscale":
        cmd = new ValueIntensityLumaAndVisualizeComponent(commands);
        break;
      case "rgb-split":
        cmd = new RGBSplit(commands);
        break;
      case "rgb-combine":
        cmd = new Combine(commands);
        break;
      case "filter-blur":
        cmd = new Blur(commands);
      break;
      case "filter-sharpen":
        cmd = new Sharpen(commands);
      break;
      case "transform-greyscale": {
        cmd = new TransformGreyscale(commands);
      }
      break;
      case "transform-sepia": {
        cmd = new Sepia(commands);
      }
      break;
      case "dither":
          cmd = new Dither(commands);
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
        throw new IllegalArgumentException("Invalid command: " + commands[0]);

    }
    if (cmd != null) {
      try {
        cmd.go(this.model, this.view); //execute the command
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

  public void commandExecutionNew(String[] commands)
      throws IOException, NoSuchFieldException, IllegalAccessException {
    //to run even if the nextline is blank
    if (commands.length == 0 || commands[0].trim().isEmpty()) {
      view.printError("Please enter appropriate command. \n");
      return;
    }
    try {
      if (commands.length != 5) {
        throw new IllegalArgumentException("Invalid command format.");
      }
    } catch (IllegalArgumentException e) {
      view.printError("Error: " + e.getMessage() + "\n");
    }

  }
}



