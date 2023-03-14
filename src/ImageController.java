import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ImageController {
  static ImageUtil imageUtil = new ImageUtil();
  private Model model;
  public ImageController(Model model) {
    this.model = model;
  }



  public void run() {
    boolean go = true;
    while (go) {
    Scanner scanner = new Scanner(System.in);
    String command;
      command = scanner.nextLine();
      String[] commandParts = command.split(" ");
      String commandName = commandParts[0];

      if (commandName.equals("load")) {
        try {
          String imagePath = commandParts[1];
          String imageName = commandParts[2];
          imageUtil.readPPM(this.model, imagePath, imageName);
          System.out.println("Loaded image '" + imageName + "' from '" + imagePath + "'");
        } catch (FileNotFoundException e) {
          System.out.println("File not found: " + commandParts[1]);
        } catch (IndexOutOfBoundsException e) {
          throw e;
        }
      } else if (commandName.equals("brighten")) {
        try {
          double increment = Integer.parseInt(commandParts[1]);
          String imageName = commandParts[2];
          String updatedImageName = commandParts[3];
          model.brighten(imageName, updatedImageName, increment);
          System.out.println(
              "Image brightened '" + imageName + "'stored as'" + updatedImageName + "'");
        } catch (IndexOutOfBoundsException e) {
          throw e;
        }
      } else if (commandName.equals("vertical-flip")) {
        try {
          String imageName = commandParts[1];
          String updatedImageName = commandParts[2];
          model.verticalFlip(imageName, updatedImageName);
          System.out.println(
              "Image vertically flipped'" + imageName + "'stored as'" + updatedImageName + "'");
        } catch (IndexOutOfBoundsException e) {
          throw e;
        }
      } else if (commandName.equals("horizontal-flip")) {
        try {
          String imageName = commandParts[1];
          String updatedImageName = commandParts[2];
          model.horizontalFlip(imageName, updatedImageName);
          System.out.println(
              "Image horizontally flipped'" + imageName + "'stored as'" + updatedImageName + "'");
        } catch (IndexOutOfBoundsException e) {
          throw e;
        }
      } else if (commandName.equals("greyscale")) {
        try {
          String component = commandParts[1];
          String imageName = commandParts[2];
          String updatedImageName = commandParts[3];
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
        } catch (IndexOutOfBoundsException e) {
          throw e;
        } catch (NoSuchFieldException e) {
          throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
          throw new RuntimeException(e);
        }
      } else if (commandName.equals("rgb-split")) {
        try {
          String imageName = commandParts[1];
          String updatedimageName1 = commandParts[2];
          String updatedimageName2 = commandParts[3];
          String updatedimageName3 = commandParts[4];
          model.splitInto3Images(imageName, updatedimageName1, updatedimageName2,
              updatedimageName3);
          System.out.println(
              "Image '" + imageName + "'has been split into greyscale images: '" + updatedimageName1
                  + "'" + updatedimageName2 + "'and" + updatedimageName3 + "'");
        } catch (IndexOutOfBoundsException e) {
          throw e;
        } catch (NoSuchFieldException e) {
          throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
          throw new RuntimeException(e);
        }
      } else if (commandName.equals("rgb-combine")) {
        try {
          String updatedimageName = commandParts[1];
          String imageName1 = commandParts[2];
          String imageName2 = commandParts[3];
          String imageName3 = commandParts[4];
          model.combineGreyScaleToRGB(imageName1, imageName2, imageName3, updatedimageName);
          System.out.println(
              "Image '" + updatedimageName + "was created by combining greyscale images: '"
                  + imageName1 + "'" + imageName2 + "'and" + imageName3 + "'");
        } catch (IndexOutOfBoundsException e) {
          throw e;
        }
      } else if (commandName.equals("save")) {
        try {
          String imagePath = commandParts[1];
          String imageName = commandParts[2];
          imageUtil.writePPM(this.model, imagePath, imageName);
        } catch (FileNotFoundException e) {
          System.out.println("File path does not exist.");
        }
      } else if (commandName.equals("exit")){
        go = false;
        System.out.println("Exit from program");
      }
    }
  }
}



