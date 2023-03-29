package Controller;

import View.ViewI;
import Model.Operations;
import java.io.IOException;

/**
 * This is an inferface for the command design pattern.
 */
public interface CommandDesignOperations {

  /**
   * The following method calls the respective operation
   * on the object using the model instance.
   * @param m Model instance.
   * @param view View instance.
   * @throws NoSuchFieldException if field not found.
   * @throws IllegalAccessException if illegal access.
   * @throws IOException if IO exceptions.
   */
  void go(Operations m, ViewI view) throws NoSuchFieldException, IllegalAccessException, IOException;

}
