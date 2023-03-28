package Controller;

import Controller.Commands.Blur;
import Controller.Commands.Brighten;
import Controller.Commands.Combine;
import Controller.Commands.Dither;
import Controller.Commands.HorizontalFlip;
import Controller.Commands.Load;
import Controller.Commands.RGBSplit;
import Controller.Commands.Save;
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
import java.util.HashMap;
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
  boolean go_script = true;


  private Map<String, Function<String[], CommandDesignOperations>> commandMap;



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

    //String[] commands = new String[0];
    commandMap = new HashMap<>();
    commandMap.put("load",l ->  new Load(l));
    commandMap.put("brighten", l -> new Brighten(l));
    commandMap.put("vertical-flip", l -> new VerticalFlip(l));
    commandMap.put("horizontal-flip", l -> new HorizontalFlip(l));
    commandMap.put("greyscale", l -> new ValueIntensityLumaAndVisualizeComponent(l));
    commandMap.put("rgb-split", l -> new RGBSplit(l));
    commandMap.put("rgb-combine", l -> new Combine(l));
    commandMap.put("filter-blur", l -> new Blur(l));
    commandMap.put("filter-sharpen", l -> new Sharpen(l));
    commandMap.put("transform-greyscale", l -> new TransformGreyscale(l));
    commandMap.put("transform-sepia", l -> new Sepia(l));
    commandMap.put("dither", l -> new Dither(l));
    commandMap.put("save", l -> new Save(l));
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
            if (!filename.split("\\.")[1].equals("txt")) {
              throw new IllegalArgumentException("Only txt files are accepted as script files!");
            }
            readScriptFile(filename);
            if (go_script) {
              view.printOutput("Script file ran successfully \n");
            }
          } else if (commandParts[0].equals("exit")) {
            if (commandParts.length != 1) {
              throw new IllegalArgumentException("Invalid command format.");
            }
            go = false;
            view.printOutput("Exit the program \n");
          } else {
            commandExecutionNew(commandParts);
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
      go_script = true;
      while (scanner.hasNextLine() & go_script)  {
        String line = scanner.nextLine().trim();
        if (!line.isEmpty() && !line.startsWith("//") && !line.startsWith("#")) {
          String[] words = line.split("\\s+");
          commandExecutionNew(words);
        }
      }
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("File not found");
    }catch (NoSuchFieldException e) {
      throw e;
    } catch (IllegalAccessException e) {
      throw  e;
    }
  }

  public void commandExecutionNew(String[] commands)
      throws IOException, NoSuchFieldException, IllegalAccessException {
    if (commands.length == 0 || commands[0].trim().isEmpty()) {
      view.printError("Please enter appropriate command. \n");
      return;
    }
    try {
      CommandDesignOperations c;
      Function<String[], CommandDesignOperations> cmd = commandMap.getOrDefault(commands[0], null);
        if (cmd == null) {
          go_script = false;
          throw new IllegalArgumentException("Invalid command: " + commands[0]);
        } else {
          c = cmd.apply(commands);
          c.go(this.model, this.view); //execute the command
        }
      } catch (IllegalArgumentException e) {
      view.printError("Error: " + e.getMessage() + "\n");
    } catch (NoSuchFieldException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }

  }
}



