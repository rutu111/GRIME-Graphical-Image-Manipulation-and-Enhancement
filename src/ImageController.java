import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ImageController {

  //create instance of ImageUtil class
  static ImageUtil imageUtil = new ImageUtil();
  static TypeOfImage currentImage = null;

  private Model model;
  //creating hashmap here

  public ImageController(Model model) {
    this.model = model;
  }

  //model.buildImage(width, height);


  public void run() {
    while (true) {
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
          //identify the type based on the type of image
          //put imageName and currentImage object in hashmap
          System.out.println("Loaded image '" + imageName + "' from '" + imagePath + "'");
        } catch (FileNotFoundException e) {
          System.out.println("File not found: " + commandParts[1]);
        } catch (IndexOutOfBoundsException e) {
          System.out.println("Usage: load image-path image-name");
        }
      } else if (commandName.equals("brighten")) {
        try {
          double increment = Integer.parseInt(commandParts[1]);
          String imageName = commandParts[2];
          String updatedImageName = commandParts[3];
          //currentImage - should be taken from the hashmap (hashmap.get(imageName.value()))
          //TypeOfImage updatedImage = currentImage.brighten(increment);
          model.brighten(imageName, updatedImageName, increment);
          //upadtedImageName, upadtedImage added to hashmap
          System.out.println(
              "Image brightened '" + imageName + "'stored as'" + updatedImageName + "'");
        } catch (IndexOutOfBoundsException e) {
          System.out.println("Usage: brighten increment imageName updatedImageName");
        }
      } else if (commandName.equals("vertical-flip")) {
        try {
          String imageName = commandParts[1];
          String updatedImageName = commandParts[2];
          //currentImage - should be taken from the hashmap (hashmap.get(imageName.value()))
          model.verticalFlip(imageName,
              updatedImageName);          //upadtedImageName, upadtedImage added to hashmap
          System.out.println(
              "Image vertically flipped'" + imageName + "'stored as'" + updatedImageName + "'");
        } catch (IndexOutOfBoundsException e) {
          System.out.println("Usage: vertical-flip imageName updatedImageName");
        }
      } else if (commandName.equals("horizontal-flip")) {
        try {
          String imageName = commandParts[1];
          String updatedImageName = commandParts[2];
          //currentImage - should be taken from the hashmap (hashmap.get(imageName.value()))
          model.horizontalFlip(imageName,
              updatedImageName);           //upadtedImageName, upadtedImage added to hashmap
          System.out.println(
              "Image horizontally flipped'" + imageName + "'stored as'" + updatedImageName + "'");
        } catch (IndexOutOfBoundsException e) {
          System.out.println("Usage: horizontal-flip imageName updatedImageName");
        }
      } else if (commandName.equals("greyscale")) {
        try {
          String component = commandParts[1];
          String imageName = commandParts[2];
          String updatedImageName = commandParts[3];
          //currentImage - should be taken from the hashmap (hashmap.get(imageName.value()))
          String[] componentParts = component.split("-");
          if (componentParts[0].equals("red") || componentParts[0].equals("green")
              || componentParts[0].equals("blue")) {
            model.visIndividualComponent(imageName, updatedImageName,
                Component.valueOf(componentParts[0]));
          } else {
            model.visualizeValueIntensityLuma(imageName, updatedImageName,
                MeasurementType.valueOf(componentParts[0]));
          }
          //upadtedImageName, upadtedImage added to hashmap
          System.out.println(
              "Image horizontally flipped'" + imageName + "'stored as'" + updatedImageName + "'");
        } catch (IndexOutOfBoundsException e) {
          System.out.println("Usage: greyscale value-component imageName updatedImageName");
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
          //currentImage - should be taken from the hashmap (hashmap.get(imageName.value()))
          model.splitInto3Images(imageName, updatedimageName1, updatedimageName2,
              updatedimageName3);//need to create modelgrey-depends on design
        } catch (IndexOutOfBoundsException e) {
          System.out.println(
              "Usage: rgb-split imageName updatedimageName1 updatedimageName2 updatedimageName3");
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

        } catch (IndexOutOfBoundsException e) {
          System.out.println(
              "Usage: rgb-combine updatedimageName imageName1 imageName2 imageName3");
        }
      } else if (commandName.equals("save")) {
        try {
          String imagePath = commandParts[1];
          String imageName = commandParts[2];
          imageUtil.writePPM(this.model, imagePath, imageName);
        } catch (FileNotFoundException e) {
          System.out.println("File path does not exist.");
        }
      }
      //scanner.close();
    }
  }
}



