package controller;

import controller.commands.Blur;
import controller.commands.Brighten;
import controller.commands.Combine;
import controller.commands.Dither;
import controller.commands.HorizontalFlip;
import controller.commands.Load;
import controller.commands.RGBSplit;
import controller.commands.Save;
import controller.commands.Sepia;
import controller.commands.Sharpen;
import controller.commands.TransformGreyscale;
import controller.commands.ValueIntensityLumaAndVisualizeComponent;
import controller.commands.VerticalFlip;
import model.Operations;
import model.ROModel;
import model.ROModelImpl;
import view.ImageProcessorUI;
import view.View;
import view.ViewGUI;
import view.ViewI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;

/**
 * This is the controller class. It interacts with the view class
 * (command line), tells the model
 * what to do and returns output to the user.
 */
public class ImageController implements ImageProcessCallbacks {

  final Readable in;
  private final Operations model;
  private ViewI view;
  boolean go_script = true;
  ImageProcessCallbacks callbacks;
  //hashmap of image operations (key) and lambda operation (value)
  //the lambda operation maps a list of strings to a CommandDesignOperations object.
  private Map<String, Function<String[], CommandDesignOperations>> commandMap;

  /**
   * This constructor creates a controller object.
   *
   * @param model takes the model instance as input.
   * @param in    input stream.
   * @param view  which is the view instance.
   */
  public ImageController(Operations model, Readable in, ViewI view) {
    this.model = model;
    this.in = in;
    this.view = view;
    commandMap = new HashMap<>();
    commandMap.put("load", l -> new Load(l));
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
  }

  /**
   * Thi function handles the command line interaction.
   * @throws IOException if one ocurs.
   */
  public void runCode() throws IOException {
    // no command-line arguments, run GUI mode
    GUIOperations();
    boolean go_code = true;
    Scanner scanner = new Scanner(this.in);
    while (go_code) {
      try {
        String command;
        command = scanner.nextLine().trim();
        String[] commandParts = command.split(" ");
        if (commandParts[0].equals("-file")) {
          if (commandParts.length != 2) {
            throw new IllegalArgumentException("Invalid command format.\n");
          }
          String filename = commandParts[1];
          if (!filename.split("\\.")[1].equals("txt")) {
            throw new IllegalArgumentException("Only txt files are accepted as script files!\n");
          }
          readScriptFile(filename);
          if (go_script) {
            view.printOutput("Script file ran successfully \n");
          }
        } else if (commandParts[0].equals("-text")) {
          if (commandParts.length != 1) {
            throw new IllegalArgumentException("Invalid command format.\n");
          }
          run();
        } else if (commandParts[0].equals("-exit")) {
            if (commandParts.length != 1) {
              throw new IllegalArgumentException("Invalid command format.");
            }
            view.printOutput("Exit the program \n");
            go_code = false;
          }
          else {
          view.printOutput("Please enter a valid command \n");
        }
      } catch (NoSuchFieldException e) {
        throw new RuntimeException(e);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      } catch (IllegalArgumentException e) {
        view.printOutput("Error: " + e.getMessage() + "\n");
      } catch (Exception e) {
        view.printOutput("Error: " + e.getMessage() + "\n");
      }

    }
    scanner.close();
  }

  /**
   * this is the first function called from the main
   * @param args
   * @throws IOException
   * @throws NoSuchFieldException
   * @throws IllegalAccessException
   */
  public void runMain(String[] args) throws IOException, NoSuchFieldException, IllegalAccessException {
    boolean guiMode = false;
    boolean textMode = false;
    boolean fileMode = false;
    String filePath = null;

    // Check for command-line arguments
    if (args.length > 0) {
      if (args[0].equals("-text")) {
        textMode = true;
      } else if (args[0].equals("-file")) {
        fileMode = true;
        if (args.length > 1) {
          filePath = args[1];
        } else {
          throw new IllegalArgumentException("File path not provided");
        }
      } else {
        view.printOutput("Please enter a valid command \n" );
      }
    } else {
      guiMode = true;
    }if (guiMode) {
      runCode();
      //GUIOperations();
    }else if (textMode) {
      run();
    } else if (fileMode) {
      if (!filePath.split("\\.")[1].equals("txt")) {
        view.printOutput("Only txt files are accepted as script files!\n");
      }
      readScriptFile(filePath);
      if (go_script) {
        view.printOutput("Script file ran successfully \n");
      }
    }
  }

  /**
   * Run method should run the command based on user input. 1. Runs:
   * runs the script specified. 2.
   * exit the program if exit pressed 3. otherwise, run script based
   * on user typed input (not from
   * script).
   */
  public void run() throws IOException {
    view.printWelcomeMessage();
    boolean go = true;
    Scanner scanner = new Scanner(this.in);

    //initiazing the hashmap for command design pattern.
    while (go) {
      try {
        String command;
        command = scanner.nextLine().trim();
        String[] commandParts = command.split(" ");
        if (commandParts.length == 0) {
          return;
        } else {
           if (commandParts[0].equals("exit")) {
            if (commandParts.length != 1) {
              throw new IllegalArgumentException("Invalid command format.");
            }
            view.printOutput("Exit text mode \n");
            return;
          } else {
            commandExecutionNew(commandParts);
          }
        }
      } catch (IllegalArgumentException e) {
        view.printOutput("Error: " + e.getMessage() + "\n");
      } catch (Exception e) {
        view.printOutput("Error: " + e.getMessage() + "\n");
      }
    }
    scanner.close();
  }


  /**
   * This is a methods used read the script.txt file.
   *
   * @param filename file to save
   * @throws IOException throws IO exception.
   */
  public void readScriptFile(String filename)
          throws IOException, NoSuchFieldException, IllegalAccessException {
    try {
      File file = new File(filename);
      if (file.exists() && file.length() == 0) {
        throw new IllegalAccessException("script file cannot be empty");
      }
      Scanner scanner = new Scanner(file);
      go_script = true;
      while (scanner.hasNextLine() & go_script) {
        String line = scanner.nextLine().trim();
        if (!line.isEmpty() && !line.startsWith("//") && !line.startsWith("#")) {
          String[] words = line.split("\\s+");
          commandExecutionNew(words);
        }
      }
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("File not found");
    } catch (NoSuchFieldException e) {
      throw e;
    } catch (IllegalAccessException e) {
      throw e;
    }
  }

  /**
   * This method fetches the appropiate object from the hashamp.
   * For example, if command is
   * "brighten" then it would load the Brighten() object from the
   * hashmap and pass the list of
   * commands typed by the yser to it, The object would then do
   * the neccessary computation and
   * return an output to the user.
   *
   * @param commands list of commands provided by user
   * @throws IOException            if IOexception occurs.
   * @throws NoSuchFieldException   if field not found.
   * @throws IllegalAccessException if illegal access.
   */
  public void commandExecutionNew(String[] commands)
          throws IOException, NoSuchFieldException, IllegalAccessException {
    if (commands.length == 0 || commands[0].trim().isEmpty()) {
      view.printOutput("Please enter appropriate command. \n");
      return;
    }
    try {
      CommandDesignOperations c;
      //line below returns the "value" based on the key (operation) passed.
      Function<String[], CommandDesignOperations> cmd = commandMap.getOrDefault(commands[0], null);
      if (cmd == null) {
        go_script = false;
        throw new IllegalArgumentException("Invalid command: " + commands[0]);
      } else {
        c = cmd.apply(commands); //pass list (commands) to the "value" (lambda functuon)'
        c.goCommand(this.model, this.view); //execute the command
      }
    } catch (IllegalArgumentException e) {
      go_script = false;
      view.printOutput("Error: " + e.getMessage() + "\n");
    } catch (NoSuchFieldException | IllegalAccessException e) {
      go_script = false;
      throw new RuntimeException(e);
    }
  }

  /**
   * The following method is used to create a GUI object and work with it.
   */
  public void GUIOperations() {
    //open up the window
    callbacks = new ImageController(model, in, view);
    ROModel modelRO = new ROModelImpl(this.model);
    ImageProcessorUI GUI = new ImageProcessorUI(modelRO, callbacks, "");

  }

  /**
   * The following method is a callback method from the GUI.
   * When buttons are clicked, they pass a list of commands to this method
   * and a view object. The function then goes and fetches the appropiate
   * lambda function (same as the function above) passed on the operation.
   * @param actionCommands list of commands mimicking user input when button clickec..
   * @param viewGUI viewGUI object.
   * @throws IOException if one occurs.
   */
  @Override
  public void executeFeatures(String[] actionCommands, ViewI viewGUI) throws IOException {

    try {
      CommandDesignOperations c;

      Function<String[], CommandDesignOperations> cmd = commandMap.getOrDefault(actionCommands[0], null);
      if (cmd == null) {
        go_script = false;
        throw new NullPointerException("Invalid command: " + actionCommands[0]);
      } else {
        try {
          c = cmd.apply(actionCommands);
          c.goCommand(this.model, viewGUI); //execute the command
          //System.out.println("Success");7
        } catch (NoSuchFieldException | IllegalAccessException | IOException ex) {
          throw new RuntimeException(ex);
        }
      }
    } catch (IllegalArgumentException e) {
      go_script = false;
      viewGUI.printOutput("Error: " + e.getMessage() + "\n");
      throw e;
    } catch (NoSuchElementException e) {
      viewGUI.printOutput("Error: " + e.getMessage() + "\n");
      throw e;
    }
  }


}





