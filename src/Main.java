import Controller.ImageController;
import Model.Model;

public class Main {
  public static void main(String[] args) {

    Model model = new Model();
    ImageController controller = new ImageController(model);
    controller.run();
  }
}
