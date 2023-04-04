import model.Model;

import java.io.IOException;
import java.io.InputStreamReader;

import model.Operations;
import view.View;
import controller.ImageController;


/**
 * This is the class where all the commands are run and the view, model and controller are initialized.
 */
public class Main {
    /**
     * This is the method main used to run the program.
     */
    public static void main(String[] args) throws IOException {

        Operations model = new Model();
        View view = new View(System.out);
        ImageController controller = new ImageController(model, new InputStreamReader(System.in), view);
        controller.run();
    }
}
