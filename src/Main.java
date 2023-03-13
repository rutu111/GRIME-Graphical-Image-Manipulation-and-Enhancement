public class Main {
  public Main() {
  }

  public static void main(String[] args) {
    Model model = new Model();
    ImageController controller = new ImageController(model);
    controller.run();
  }
}
