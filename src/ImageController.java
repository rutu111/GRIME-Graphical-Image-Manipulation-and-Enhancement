import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class ImageController {
  private final Model model;

  public ImageController(Model model) {
    this.model = model;
  }

  public void run() {
    boolean go = true;
    while (go) {
      try {
        Scanner scanner = new Scanner(System.in);
        String command;
        command = scanner.nextLine().trim();
        String[] commandParts = command.split(" ");
        if (commandParts.length == 0) {
          return;
        }else {
          if (commandParts[0].equals("run")) {
            if (commandParts.length != 2) {
              throw new IllegalArgumentException("Invalid command format.");
            }
            String filename = commandParts[1];
            readScriptFile(filename);
            System.out.println("Script file ran successfully");
          } else if (commandParts[0].equals("exit")) {
            if (commandParts.length != 1) {
              throw new IllegalArgumentException("Invalid command format.");
            }
            go = false;
            System.out.println("Exit the program");
          } else {
            commandExecution(commandParts);
          }
        }
      }catch(Exception e){
        System.out.println("Error: " + e.getMessage());
      }
    }
  }

  public void commandExecution(String[] commands){
    //to run even if the nextline is blank
    if (commands.length == 0 || commands[0].trim().isEmpty()) {
      return;
    }
    switch (commands[0]) {
      case "load":
        try {
          if (commands.length != 3) {
            throw new IllegalArgumentException("Invalid command format.");
          }
          String imagePath = commands[1];
          String imageName = commands[2];
          ImageUtil.readPPM(this.model, imagePath, imageName);
          System.out.println("Loaded image '" + imageName + "' from '" + imagePath + "'");
        } catch (FileNotFoundException e) {
          System.out.println("File not found: " + commands[1]);
        }
        break;
      case "brighten": {
        if (commands.length != 4) {
          throw new IllegalArgumentException("Invalid command format.");
        }
        double increment = Integer.parseInt(commands[1]);
        String imageName = commands[2];
        String updatedImageName = commands[3];
        model.brighten(imageName, updatedImageName, increment);
        System.out.println(
            "Image brightened '" + imageName + "'stored as'" + updatedImageName + "'");
      }
        break;
      case "vertical-flip": {
        if (commands.length != 3) {
          throw new IllegalArgumentException("Invalid command format.");
        }
        String imageName = commands[1];
        String updatedImageName = commands[2];
        model.verticalFlip(imageName,
            updatedImageName);
        System.out.println(
            "Image vertically flipped'" + imageName + "'stored as'" + updatedImageName + "'");
      }
        break;
      case "horizontal-flip": {
        if (commands.length != 3) {
          throw new IllegalArgumentException("Invalid command format.");
        }
        String imageName = commands[1];
        String updatedImageName = commands[2];
        model.horizontalFlip(imageName,
            updatedImageName);
        System.out.println(
            "Image horizontally flipped'" + imageName + "'stored as'" + updatedImageName + "'");
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
            model.visIndividualComponent(imageName, updatedImageName,
                Component.valueOf(componentParts[0]));
          } else {
            model.visualizeValueIntensityLuma(imageName, updatedImageName,
                MeasurementType.valueOf(componentParts[0]));
          }
          System.out.println(
              "Image '" + imageName + "'stored as greyscale'" + updatedImageName + "'");
        } catch (NoSuchFieldException | IllegalAccessException e) {
          throw new RuntimeException(e);
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
          model.splitInto3Images(imageName, updatedimageName1, updatedimageName2,
              updatedimageName3);
          System.out.println(
              "Image '" + imageName + "'has been split into greyscale images: '"
                  + updatedimageName1 + "'" + updatedimageName2 + "'and" + updatedimageName3 + "'");
        } catch (NoSuchFieldException | IllegalAccessException e) {
          throw new RuntimeException(e);
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
        model.combineGreyScaleToRGB(imageName1, imageName2, imageName3, updatedimageName);
        System.out.println(
            "Image '" + updatedimageName + "was created by combining greyscale images: '"
                + imageName1 + "'" + imageName2 + "'and" + imageName3 + "'");
        break;
      case "save":
        try {
          if (commands.length != 3) {
            throw new IllegalArgumentException("Invalid command format.");
          }
          String imagePath = commands[1];
          String imageName = commands[2];
          ImageUtil.writePPM(this.model, imagePath, imageName);
        } catch (FileNotFoundException e) {
          System.out.println("File path does not exist.");
        }
        break;
      default:
        //throws exception when the command is invalid
        throw new IllegalArgumentException("Invalid command: " + commands[0]);
    }
  }
  public void readScriptFile(String filename){
    try{
      File file = new File(filename);
      Scanner scanner = new Scanner(file);

      while(scanner.hasNextLine()){
        String line = scanner.nextLine().trim();
        if (!line.isEmpty() && !line.startsWith("//") && !line.startsWith("#")) { // Ignore comments and empty lines
          String[] words = line.split("\\s+");
          commandExecution(words);
        }
      }
    }catch(FileNotFoundException e){
      System.out.println("File not found");
    }

  }
}



