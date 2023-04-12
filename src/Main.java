import controller.ImageController;
import model.Model;
import model.Operations;
import view.View;

import java.io.IOException;
import java.io.InputStreamReader;


/**
 * This is the class where all the commands are run and the view, model and
 * controller are initialized.
 */
public class Main {
  /**
   * This is the method main used to run the program.
   */
  public static void main(String[] args) throws IOException, NoSuchFieldException,
      IllegalAccessException {
    Operations model = new Model();
    View view = new View(System.out);
    ImageController controller = new ImageController(model, new InputStreamReader(System.in), view);
    try {
      controller.runMain(args);
    } catch (IOException e) {
      view.printOutput("Error: " + e.getMessage() + "\n");
    } catch (ArrayIndexOutOfBoundsException e) {
      view.printOutput("Please provide a valid path");
    } catch (IllegalArgumentException e) {
      view.printOutput("Please provide a valid path");
    }
  }
}
