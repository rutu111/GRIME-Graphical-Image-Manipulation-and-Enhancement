package View;

import java.io.IOException;

public class View implements ViewI {

  final Appendable out;

  public View(Appendable out) {
    this.out = out;
  }


  /**
   * Prints the welcome message and the list of available commands.
   */
  public void printWelcomeMessage() {
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
    System.out.println("GREYSCALE TO RGB: rgb-combine newimageName imageNameR imageNameG imageNameB"
        + "\n");
  }



  /**
   * Prints the output of a command to the console.
   *
   * @param output the output of a command as a String.
   */
  public void printOutput(String output) throws IOException {
    this.out.append(output);
  }

  /**
   * Prints an error message to the console.
   *
   * @param message the error message as a String.
   */
  public void printError(String message) throws IOException {
    this.out.append(message);
  }
}