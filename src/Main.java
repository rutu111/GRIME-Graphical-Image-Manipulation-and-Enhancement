import Controller.ImageController;
import Model.Model;
import java.io.InputStreamReader;

public class Main {
  public static void main(String[] args) {

    Model model = new Model();
    ImageController controller = new ImageController(model, new InputStreamReader(System.in),System.out);
    controller.run();
  }
}
