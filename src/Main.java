import Model.Model;
import java.io.IOException;
import java.io.InputStreamReader;
import Model.Operations;
import View.View;
import Controller.ImageController;


public class Main {
  public static void main(String[] args) throws IOException {

    Operations model = new Model();
    View view = new View(System.out);
    ImageController controller = new ImageController(model, new InputStreamReader(System.in), view);
    controller.run();
  }
}
