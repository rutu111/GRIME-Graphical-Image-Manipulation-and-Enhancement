package controller;

import controller.Commands.Blur;
import controller.Commands.Brighten;
import controller.Commands.Combine;
import controller.Commands.Dither;
import controller.Commands.HorizontalFlip;
import controller.Commands.Load;
import controller.Commands.RGBSplit;
import controller.Commands.Save;
import controller.Commands.Sepia;
import controller.Commands.Sharpen;
import controller.Commands.TransformGreyscale;
import controller.Commands.ValueIntensityLumaAndVisualizeComponent;
import controller.Commands.VerticalFlip;
import model.Operations;
import view.ViewI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

/**
 * This is the controller class. It interacts with the view class (command line), tells
 * the model what to do and returns output to the user.
 */
public class ImageController {

    final Readable in;
    private final Operations model;
    private ViewI view;
    boolean go_script = true;


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
    }


    /**
     * Run method should run the command based on user input.
     * 1. Runs: runs the script specified.
     * 2. exit the program if exit pressed
     * 3. otherwise, run script based on user typed input (not from script).
     */
    public void run() throws IOException {
        view.printWelcomeMessage();
        boolean go = true;
        Scanner scanner = new Scanner(this.in);

        //initiazing the hashmap for command design pattern.
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
     * For example, if command is "brighten" then it would load
     * the Brighten() object from the hashmap and pass the list of
     * commands typed by the yser to it,
     * The object would then do the neccessary computation and return an output
     * to the user.
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
}


