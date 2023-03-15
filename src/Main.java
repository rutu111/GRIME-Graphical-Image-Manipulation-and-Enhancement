import Controller.ImageController;
import Model.Model;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
  public static void main(String[] args) throws IOException {

    Model model = new Model();
    ImageController controller = new ImageController(model, new InputStreamReader(System.in),System.out);
    controller.run();
  }
}
