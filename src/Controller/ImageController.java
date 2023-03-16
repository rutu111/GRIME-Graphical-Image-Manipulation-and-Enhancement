package Controller;

import Model.Component;
import Model.Operations;
import Model.MeasurementType;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class ImageController {
  private final Operations model;
  final Readable in;
  final Appendable out;

  public ImageController(Operations model, Readable in, Appendable out) {
    this.model = model;
    this.in = in;
    this.out = out;
  }

  public void run() throws IOException {
    Objects.requireNonNull(model);
    System.out.println("Welcome to image processing with PPM files! You can enter commands here. "
                      + "Type exit to exit the program anytime.\n");
    System.out.println("You can use the following commands to perform the following operations:\n");
    System.out.println("LOAD: load filepath imageName\n");
    System.out.println("SAVE: save filepath imageName\n");
    System.out.println("BRIGHTEN: brighten increment-value imageName newimageName\n");
    System.out.println("HORIZONTAL-FLIP: horizontal-flip imageName newimageName\n");
    System.out.println("VERTICAL-FLIP: vertical-flip imageName newimageName\n");
    System.out.println("GREYSCALE component: greyscale component-type imageName newimageName\n");
    System.out.println("RGB TO GREYSCALE: rgb-split imageName newimageNameR newimageNameG "
        + "newimageNameB\n");
    System.out.println("GREYSCALE TO RGB: rgb-combine imageNameR imageNameG imageNameB"
        + "newimageName \n");
    boolean go = true;
    Scanner scanner = new Scanner(this.in);
    while (go) {
      try {
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
            this.out.append("Script file ran successfully \n");
          } else if (commandParts[0].equals("exit")) {
            if (commandParts.length != 1) {
              throw new IllegalArgumentException("Invalid command format.");
            }
            go = false;
            this.out.append("Exit the program \n");
          } else {
            commandExecution(commandParts);
          }
        }
      }catch(Exception e){
        this.out.append("Error: " + e.getMessage() + "\n");
      }
    }
    scanner.close();
  }

  public void commandExecution(String[] commands) throws IOException{
    //to run even if the nextline is blank
    if (commands.length == 0 || commands[0].trim().isEmpty()) {
      this.out.append("Please enter appropriate command.");
    }
    switch (commands[0]) {
      case "load":
        try {
          if (commands.length != 3) {
            throw new IllegalArgumentException("Invalid command format.");
          }
          String imagePath = commands[1];
          String imageName = commands[2];
          if(!imagePath.split("\\.")[1].equals("ppm")){
            throw new IllegalArgumentException("Invalid file format: " + imagePath.split("\\.")[1]);
          }
          ImageUtil.readPPM(this.model, imagePath, imageName);
          this.out.append("Loaded image '" + imageName + "' from '" + imagePath + "'" + "\n");
        } catch (FileNotFoundException e) {
          this.out.append("File not found: " + commands[1] + "\n");
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
        this.out.append(
            "Image brightened '" + imageName + "'stored as'" + updatedImageName + "'" + "\n");
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
        this.out.append(
            "Image vertically flipped'" + imageName + "'stored as'" + updatedImageName + "'" + "\n");
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
        this.out.append(
            "Image horizontally flipped'" + imageName + "'stored as'" + updatedImageName + "'" + "\n");
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
          this.out.append(
              "Image '" + imageName + "'stored as greyscale'" + updatedImageName + "'" + "\n");

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
              "Image '" + imageName + "' has been split into greyscale images: '"
                  + updatedimageName1 + "'" + updatedimageName2 + "' and " + updatedimageName3 +
                  "' \n");
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
        this.out.append(
            "Image '" + updatedimageName + "was created by combining greyscale images: '"
                + imageName1 + "'" + imageName2 + "'and" + imageName3 + "'" + "\n");
        break;
      case "save":
        try {
          if (commands.length != 3) {
            throw new IllegalArgumentException("Invalid command format.");
          }
          String imagePath = commands[1];
          String imageName = commands[2];
          this.out.append("Image" + imageName + " saved as file:" + imagePath);
          if(!imagePath.split("\\.")[1].equals("ppm")){
            throw new IllegalArgumentException("Invalid file format: " + imagePath.split("\\.")[1]);
          }
          ImageUtil.writePPM(this.model, imagePath, imageName);
        } catch (FileNotFoundException e) {
          this.out.append("File path does not exist.\n");
        }
        break;
      default:
        //throws exception when the command is invalid
        throw new IllegalArgumentException("Invalid command: " + commands[0]);
    }
  }
  public void readScriptFile(String filename) throws IOException{
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
      throw e;
    }

  }
}



